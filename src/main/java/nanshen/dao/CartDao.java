package nanshen.dao;

import nanshen.data.Cart;

/**
 * @author WANG Minghao
 */
public interface CartDao {

    Cart insert(Cart cart);

    Cart get(long cartId);

    Cart getByUserId(long userId);

    boolean update(Cart cart);

    boolean remove(long cartId);

}
