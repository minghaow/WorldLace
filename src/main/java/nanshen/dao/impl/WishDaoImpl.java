package nanshen.dao.impl;

import nanshen.dao.WishDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.Wish.Wish;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

/**
 * @author WANG Minghao
 */
@Repository
public class WishDaoImpl extends BaseDao implements WishDao {

    @Override
    public Wish insert(Wish wish) {
        return dao.insert(wish);
    }

    @Override
    public Wish get(long wishId) {
        return dao.fetch(Wish.class, wishId);
    }
    @Override
    public Wish getByUserId(long userId) {
        Condition cnd = Cnd.where("userId", "=", userId);
        return dao.fetch(Wish.class, cnd);
    }

    @Override
    public boolean update(Wish wish) {
        return dao.update(wish) == 1;
    }

    @Override
    public boolean remove(long wishId) {
        return dao.delete(Wish.class, wishId) == 1;
    }

}
