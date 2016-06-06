package nanshen.service;

import nanshen.data.Cart;
import nanshen.data.ExecResult;

/**
 * Cart related services
 *
 * @author WANG Minghao
 */
public interface CartService {

    /**
     * Add item to the cart
     *
     * @param userId user id
     * @param itemId goods id
     * @param count the goods count
     * @return ExecResult
     */
    ExecResult<Long> addSku(long userId, long itemId, long count);

    /**
     * Add item to the cart
     *
     * @param userId user id
     * @param skuId the goods id of the cart
     * @return ExecInfo
     */
    ExecResult<Long> removeSku(long userId, long skuId);

    /**
     * Add item count to the cart goods
     *
     * @param userId user id
     * @param goodsId the goods id of the cart
     * @return ExecResult with cart goods count
     */
    ExecResult<Long> addSkuCount(long userId, long goodsId);

    /**
     * Minus item count to the cart goods
     *
     * @param userId user id
     * @param goodsId the goods id of the cart
     * @return ExecResult with cart goods count
     */
    ExecResult<Long> minusSkuCount(long userId, long goodsId);

    /**
     * Minus item count to the cart goods
     *
     * @param userId user id
     * @param skuId the goods id of the cart
     * @param count the target goods count
     * @return ExecResult with cart goods count
     */
    ExecResult<Long> setSkuCount(long userId, long skuId, long count);

    /**
     * get all the cart info
     *
     * @param userId user id
     * @return ExecInfo
     */
    Cart getByUserId(long userId);

    /**
     * Delete the goods in cart
     *
     * @param userId user id
     * @param goodsId the goods id of the cart
     * @return ExecResult with cart goods count
     */
    ExecResult<Long> deleteGoods(long userId, long goodsId);
}
