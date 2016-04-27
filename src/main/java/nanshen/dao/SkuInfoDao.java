package nanshen.dao;

import nanshen.data.PageInfo;
import nanshen.data.PublicationStatus;
import nanshen.data.SkuItem;

import java.util.Date;
import java.util.List;

/**
 * @author WANG Minghao
 */
public interface SkuInfoDao {

    SkuItem insert(SkuItem skuItem);

    SkuItem get(long skuId);

    boolean update(SkuItem skuId);

    boolean remove(long skuId);

    List<SkuItem> getAll();

    List<SkuItem> getAll(PublicationStatus status);

    List<SkuItem> getAll(PublicationStatus status, PageInfo pageInfo);

    boolean remove(long skuId, long operatorId);

    long getCnt(PublicationStatus status);

    long getCnt(PublicationStatus status, Date startDate);

    List<SkuItem> getByLookId(long lookId);
}
