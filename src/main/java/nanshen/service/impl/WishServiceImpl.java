package nanshen.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import nanshen.constant.TimeConstants;
import nanshen.dao.OrderDao;
import nanshen.dao.OrderGoodsDao;
import nanshen.dao.WishDao;
import nanshen.dao.WishGoodsDao;
import nanshen.data.Sku.SkuDetail;
import nanshen.data.Sku.SkuItem;
import nanshen.data.SystemUtil.ExecResult;
import nanshen.data.Wish.Wish;
import nanshen.data.Wish.WishGoods;
import nanshen.service.SkuService;
import nanshen.service.WishService;
import nanshen.service.common.ScheduledService;
import nanshen.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Wishlist related service implementation
 *
 * @author WANG Minghao
 */
@Service
public class WishServiceImpl extends ScheduledService implements WishService {

    @Autowired
    private WishDao wishDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private WishGoodsDao wishGoodsDao;

    @Autowired
    private OrderGoodsDao orderGoodsDao;

    @Autowired
    private SkuService skuService;

    /** userId to user Cart cache */
    private final LoadingCache<Long, Wish> userCache = CacheBuilder.newBuilder()
            .softValues()
            .expireAfterWrite(TimeConstants.HALF_HOUR_IN_SECONDS, TimeUnit.SECONDS)
            .build(
                    new CacheLoader<Long, Wish>() {
                        @Override
                        public Wish load(Long userId) throws Exception {
                            Wish wish = wishDao.getByUserId(userId);
                            if (wish != null) {
                                wish.setGoodsList(wishGoodsDao.getByCartId(wish.getId()));
                                return wish;
                            }
                            return wishDao.insert(new Wish(userId));
                        }
                    });

    @Override
    public void update() {
        long startTime = System.currentTimeMillis();

        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println("[CartService] Update in " + totalTime + "ms");
    }

    @Override
    public int updatePeriod() {
        return TimeConstants.HOUR_IN_SECONDS;
    }

    @Override
    @Transactional
    public ExecResult<Long> addSku(long userId, long skuId, long count) {
        Wish cart = getCartByUserId(userId);
        ExecResult<Long> execResult = skuAlreadyHaveThenAdd(cart, skuId, count);
        if (!execResult.isSucc()) {
            SkuDetail skuDetail = skuService.getSkuDetail(skuId);
            SkuItem skuItem = skuService.getSkuItemInfo(skuDetail.getItemId());
            if (null != wishGoodsDao.insert(new WishGoods(cart.getId(), count, null, 0, skuDetail.getOriginPrice(),
                    skuDetail.getPrice(), null, skuItem.getSubTitle(), skuItem.getTitle(), userId, skuDetail.getId(),
                    skuDetail.getItemId(), skuDetail.getOption1(), skuDetail.getOption2(), skuDetail.getOptionId()))) {
                cart.setGoodsCount(cart.getGoodsCount() + count);
                cart.setTotalPrice(cart.getTotalPrice() + count * skuDetail.getPrice());
                wishDao.update(cart);
                userCache.invalidate(userId);
                return ExecResult.succ(cart.getGoodsCount());
            }
            return ExecResult.fail("添加商品失败");
        }
        return execResult;
    }

    @Override
    @Transactional
    public ExecResult<Long> removeSku(long userId, long skuId) {
        Wish cart = getCartByUserId(userId);
        List<WishGoods> cartGoodsList = cart.getGoodsList();
        for (WishGoods goods : cartGoodsList) {
            if (goods.getSkuId() == skuId) {
                wishGoodsDao.remove(goods.getId());
                cart.setGoodsCount(cart.getGoodsCount() - goods.getCount());
                wishDao.update(cart);
                userCache.invalidate(cart.getUserId());
                return ExecResult.succ(cart.getGoodsCount());
            }
        }
        return ExecResult.fail("抱歉，没有在您的购物车找到该商品");
    }

    @Override
    public Wish getByUserId(long userId) {
        try {
            return userCache.get(userId);
        } catch (ExecutionException e) {
            LogUtils.warning("CartService: get user cart info error!", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean clearCartCache() {
        userCache.invalidateAll();
        return true;
    }

    private Wish getCartByUserId(long userId) {
        Wish cart = null;
        try {
            cart = userCache.get(userId);
        } catch (Exception e) {
            LogUtils.warning("CartService: get user cart info error!", e);
        }
        if (cart == null) {
            cart = wishDao.insert(new Wish(userId));
        }
        return cart;
    }

    @Transactional
    private ExecResult<Long> skuAlreadyHaveThenAdd(Wish cart, long skuId, long count) {
        List<WishGoods> cartGoodsList = cart.getGoodsList();
        for (WishGoods goods : cartGoodsList) {
            if (goods.getSkuId() == skuId) {
                goods.setCount(goods.getCount() + count);
                cart.setGoodsCount(cart.getGoodsCount() + count);
                wishDao.update(cart);
                wishGoodsDao.update(goods);
                userCache.invalidate(cart.getUserId());
                return ExecResult.succ(cart.getGoodsCount());
            }
        }
        return ExecResult.fail("更新购物车失败");
    }

}
