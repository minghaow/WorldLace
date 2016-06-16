package nanshen.dao;

import nanshen.data.Sku.SkuComment;

import java.util.List;

/**
 * @author WANG Minghao
 */
public interface SkuCommentDao {

    SkuComment insert(SkuComment skuComment);

    List<SkuComment> getByItemId(long itemId);

    List<SkuComment> getByUserId(long userId);

    boolean remove(long id);

}
