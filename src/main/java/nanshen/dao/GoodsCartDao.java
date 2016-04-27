package nanshen.dao;

import nanshen.data.Goods;

/**
 * @author WANG Minghao
 */
public interface GoodsCartDao {

    Goods insert(Goods goods);

    Goods get(long goodsId);

    Goods getByUserId(long userId);

    boolean update(Goods goods);

    boolean remove(long goodsId);

}
