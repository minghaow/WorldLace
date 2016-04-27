package nanshen.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import nanshen.constant.TimeConstants;
import nanshen.dao.CartDao;
import nanshen.dao.CartGoodsDao;
import nanshen.dao.OrderDao;
import nanshen.data.*;
import nanshen.service.CartService;
import nanshen.service.SkuService;
import nanshen.service.common.ScheduledService;
import nanshen.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Cart related service implementation
 *
 * @author WANG Minghao
 */
@Service
public class CartServiceImpl extends ScheduledService implements CartService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CartGoodsDao cartGoodsDao;

    @Autowired
    private SkuService skuService;

    /** 买手ID到买手信息的缓存 */
    private final LoadingCache<Long, Cart> userCache = CacheBuilder.newBuilder()
            .softValues()
            .expireAfterWrite(TimeConstants.HALF_HOUR_IN_SECONDS, TimeUnit.SECONDS)
            .build(
                    new CacheLoader<Long, Cart>() {
                        @Override
                        public Cart load(Long userId) throws Exception {
                            Cart cart = cartDao.getByUserId(userId);
                            if (cart != null) {
                                cart.setGoodsList(cartGoodsDao.getByCartId(cart.getId()));
                            }
                            return cart;
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
    public ExecResult<Long> addSku(long userId, long skuId, long count) {
        Cart cart = null;
        try {
            cart = userCache.get(userId);
        } catch (Exception e) {
            LogUtils.warning("CartService: get user cart info error!", e);
        }
        if (cart == null) {
            cart = cartDao.insert(new Cart(userId));
        }
        ExecResult<Long> execResult = skuAlreadyHaveThenAdd(cart, skuId, count);
        if (!execResult.isSucc()) {
            SkuDetail skuDetail = skuService.getSkuDetail(skuId);
            SkuItem skuItem = skuService.getSkuItemInfo(skuDetail.getItemId());
            if (null != cartGoodsDao.insert(new CartGoods(cart.getId(), count, null, 0, skuDetail.getOriginPrice(),
                    skuDetail.getPrice(), null, skuItem.getSubTitle(), skuItem.getTitle(), userId, skuDetail.getId()))) {
                cart.setGoodsCount(cart.getGoodsCount() + count);
                cartDao.update(cart);
                userCache.invalidate(userId);
                return ExecResult.succ(cart.getGoodsCount());
            }
            return ExecResult.fail("添加商品失败");
        }
        return execResult;
    }

    @Override
    public ExecInfo removeSku(long userId, long goodsCartId) {
        return null;
    }

    @Override
    public Cart getByUserId(long userId) {
        try {
            return userCache.get(userId);
        } catch (ExecutionException e) {
            LogUtils.warning("CartService: get user cart info error!", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cart createOrder(long userId, List<Long> idList) {
        return null;
    }

    private ExecResult<Long> skuAlreadyHaveThenAdd(Cart cart, long skuId, long count) {
        List<CartGoods> cartGoodsList = cart.getGoodsList();
        for (CartGoods goods : cartGoodsList) {
            if (goods.getSkuId() == skuId) {
                goods.setCount(goods.getCount() + count);
                cart.setGoodsCount(cart.getGoodsCount() + count);
                cartDao.update(cart);
                cartGoodsDao.update(goods);
                userCache.invalidate(cart.getUserId());
                return ExecResult.succ(cart.getGoodsCount());
            }
        }
        return ExecResult.fail("更新购物车失败");
    }

}
