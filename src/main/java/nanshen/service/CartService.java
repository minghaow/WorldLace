package nanshen.service;

import nanshen.data.Cart;
import nanshen.data.ExecInfo;

import java.util.List;

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
     * @return ExecInfo
     */
    ExecInfo addSku(long userId, long itemId, long count);

    /**
     * Add item to the cart
     *
     * @param userId user id
     * @param goodsCartId the goods id of the cart
     * @return ExecInfo
     */
    ExecInfo removeSku(long userId, long goodsCartId);

    /**
     * get all the cart info
     *
     * @param userId user id
     * @return ExecInfo
     */
    Cart getByUserId(long userId);

    /**
     * get all the cart info
     *
     * @param userId user id
     * @param idList goods id list
     * @return ExecInfo
     */
    Cart createOrder(long userId, List<Long> idList);

}
