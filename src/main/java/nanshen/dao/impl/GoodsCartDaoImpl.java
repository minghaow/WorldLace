package nanshen.dao.impl;

import nanshen.dao.GoodsCartDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.Goods;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

/**
 * @author WANG Minghao
 */
@Repository
public class GoodsCartDaoImpl extends BaseDao implements GoodsCartDao {

    @Override
    public Goods insert(Goods goods) {
        return dao.insert(goods);
    }

    @Override
    public Goods get(long goodsId) {
        return dao.fetch(Goods.class, goodsId);
    }

    @Override
    public Goods getByUserId(long userId) {
        Condition cnd = Cnd.where("userId", "=", userId);
        return dao.fetch(Goods.class, cnd);
    }

    @Override
    public boolean update(Goods goods) {
        return dao.update(goods) == 1;
    }

    @Override
    public boolean remove(long goodsId) {
        return dao.delete(Goods.class, goodsId) == 1;
    }

}
