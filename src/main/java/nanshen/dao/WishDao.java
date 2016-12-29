package nanshen.dao;

import nanshen.data.Wish.Wish;

/**
 * @author WANG Minghao
 */
public interface WishDao {

    Wish insert(Wish wish);

    Wish get(long wishId);

    Wish getByUserId(long userId);

    boolean update(Wish wish);

    boolean remove(long wishId);

}
