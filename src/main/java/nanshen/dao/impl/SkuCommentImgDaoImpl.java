package nanshen.dao.impl;

import nanshen.dao.SkuCommentImgDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.Sku.SkuCommentImg;
import org.nutz.dao.Chain;
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
public class SkuCommentImgDaoImpl extends BaseDao implements SkuCommentImgDao {
    @Override
    public SkuCommentImg insert(SkuCommentImg skuCommentImg) {
        return dao.insert(skuCommentImg);
    }

    @Override
    public List<SkuCommentImg> getByItemId(long itemId) {
        Condition condition = Cnd.where("itemId", "=", itemId);
        return dao.query(SkuCommentImg.class, condition);
    }

    @Override
    public List<SkuCommentImg> getByUserId(long userId) {
        Condition condition = Cnd.where("userId", "=", userId);
        return dao.query(SkuCommentImg.class, condition);
    }

    @Override
    public boolean remove(long id) {
        return dao.delete(SkuCommentImg.class, id) == 1;
    }

    @Override
    public boolean updateImgCommentId(long userId, long skuId, List<Long> imgList, long commentId) {
        Chain chn = Chain
                .make("commentId", commentId);
        Condition cnd = Cnd
                .where("id", "in", imgList)
                .and("userId", "=", userId)
                .and("skuId", "=", skuId);
        return 1 == dao.update(SkuCommentImg.class, chn, cnd);
    }
}
