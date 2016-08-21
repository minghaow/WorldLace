package nanshen.dao;

import nanshen.data.PublicationStatus;
import nanshen.data.Sku.SkuDetail;
import nanshen.data.Sku.SkuItem;
import nanshen.data.SystemUtil.PageInfo;

import java.util.Date;
import java.util.List;

/**
 * @author WANG Minghao
 */
public interface SkuDetailDao {

    SkuDetail insert(SkuDetail skuDetail);

    List<SkuDetail> getByItemId(long itemId);

    SkuDetail get(long skuId);

    boolean update(SkuItem skuId);

    boolean remove(long skuId);

    List<SkuDetail> getAll();

    List<SkuDetail> getAll(PublicationStatus status);

    List<SkuDetail> getAll(PublicationStatus status, PageInfo pageInfo);

    boolean remove(long skuId, long operatorId);

    long getCnt(PublicationStatus status);

    long getCnt(PublicationStatus status, Date startDate);

    List<SkuDetail> get(List<Long> skuIdList);
}
