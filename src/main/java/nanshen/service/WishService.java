package nanshen.service;

import nanshen.data.SystemUtil.ExecResult;
import nanshen.data.Wish.Wish;

/**
 * Wish related services
 *
 * @author WANG Minghao
 */
public interface WishService {

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
     * get all the cart info
     *
     * @param userId user id
     * @return ExecInfo
     */
    Wish getByUserId(long userId);

    /**
     * clear cart cache
     *
     * @return boolean
     */
    boolean clearCartCache();
}
