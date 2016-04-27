package nanshen.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import nanshen.constant.TimeConstants;
import nanshen.dao.CartDao;
import nanshen.dao.GoodsCartDao;
import nanshen.dao.OrderDao;
import nanshen.data.Cart;
import nanshen.data.ExecInfo;
import nanshen.data.Goods;
import nanshen.service.CartService;
import nanshen.service.common.ScheduledService;
import nanshen.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private GoodsCartDao goodsCartDao;

    private Map<Long, Cart> userIdCartMap = new HashMap<Long, Cart>();

    /** 买手ID到买手信息的缓存 */
    private final LoadingCache<Long, Cart> userCache = CacheBuilder.newBuilder()
            .softValues()
            .expireAfterWrite(TimeConstants.HALF_HOUR_IN_SECONDS, TimeUnit.SECONDS)
            .build(
                    new CacheLoader<Long, Cart>() {
                        @Override
                        public Cart load(Long userId) throws Exception {
                            return cartDao.getByUserId(userId);
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
    public ExecInfo addSku(long userId, long skuId, long count) {
        Cart cart = null;
        try {
            cart = userCache.get(userId);
        } catch (ExecutionException e) {
            LogUtils.warning("CartService: get user cart info error!", e);
            throw new RuntimeException(e);
        }
        if (cart == null) {
            cart = cartDao.insert(new Cart(userId));
        }
        if (!skuAlreadyHaveThenAdd(cart, skuId, count)) {
//            SkuDetail
//            goodsCartDao.insert(new Goods(cart.getId(), count));
        }
        return ExecInfo.fail("");
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

    private boolean skuAlreadyHaveThenAdd(Cart cart, long skuId, long count) {
        List<Goods> cartGoodsList = cart.getGoodsList();
        for (Goods goods : cartGoodsList) {
            if (goods.getSkuId() == skuId) {
                goods.setCount(goods.getCount() + count);
                return goodsCartDao.update(goods);
            }
        }
        return false;
    }

}
