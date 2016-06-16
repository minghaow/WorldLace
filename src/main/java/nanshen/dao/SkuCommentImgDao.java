package nanshen.dao;

import nanshen.data.Sku.SkuCommentImg;

import java.util.List;

/**
 * @author WANG Minghao
 */
public interface SkuCommentImgDao {

    SkuCommentImg insert(SkuCommentImg skuCommentImg);

    List<SkuCommentImg> getByItemId(long itemId);

    List<SkuCommentImg> getByUserId(long userId);

    boolean remove(long id);

    boolean updateImgCommentId(long userId, long skuId, List<Long> imgList, long commentId);
}
