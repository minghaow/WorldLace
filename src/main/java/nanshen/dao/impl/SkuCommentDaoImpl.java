package nanshen.dao.impl;

import nanshen.dao.SkuCommentDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.Sku.SkuComment;
import nanshen.data.Sku.SkuCommentImg;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * NanShen
 *
 * @Author WANG Minghao
 */
@Repository
public class SkuCommentDaoImpl extends BaseDao implements SkuCommentDao {
    @Override
    public SkuComment insert(SkuComment skuComment) {
        return dao.insert(skuComment);
    }

    @Override
    public List<SkuComment> getByItemId(long itemId) {
        Condition condition = Cnd.where("itemId", "=", itemId);
        return dao.query(SkuComment.class, condition);
    }

    @Override
    public List<SkuComment> getByUserId(long userId) {
        Condition condition = Cnd.where("userId", "=", userId);
        return dao.query(SkuComment.class, condition);
    }

    @Override
    public boolean remove(long id) {
        return dao.delete(SkuCommentImg.class, id) == 1;
    }
}
