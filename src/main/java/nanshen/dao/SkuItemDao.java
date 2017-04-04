package nanshen.dao;

import nanshen.data.PublicationStatus;
import nanshen.data.Sku.SituationType;
import nanshen.data.Sku.SkuItem;
import nanshen.data.Sku.StoreType;
import nanshen.data.SystemUtil.PageInfo;

import java.util.Date;
import java.util.List;

/**
 * @author WANG Minghao
 */
public interface SkuItemDao {

    SkuItem insert(SkuItem skuItem);

    SkuItem get(long itemId);

    boolean update(SkuItem itemId);

    boolean remove(long itemId);

    List<SkuItem> getAll();

    List<SkuItem> getAll(PublicationStatus status);

    List<SkuItem> getAll(PublicationStatus status, PageInfo pageInfo);

    List<SkuItem> getAll(StoreType storeType, PageInfo pageInfo);

    List<SkuItem> getAll(StoreType type, SituationType situationType, PageInfo pageInfo);

    boolean remove(long skuId, long operatorId);

    long getCnt(PublicationStatus status);

    long getCnt(PublicationStatus status, Date startDate);

    List<SkuItem> getByLookId(long itemId);

    List<SkuItem> getAll(PageInfo pageInfo);

}
