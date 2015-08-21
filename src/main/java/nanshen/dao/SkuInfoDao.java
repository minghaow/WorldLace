package nanshen.dao;

import nanshen.data.PageInfo;
import nanshen.data.PublicationStatus;
import nanshen.data.SkuInfo;

import java.util.Date;
import java.util.List;

/**
 * @author WANG Minghao
 */
public interface SkuInfoDao {

    SkuInfo insert(SkuInfo skuInfo);

    SkuInfo get(long skuId);

    boolean update(SkuInfo skuId);

    boolean remove(long skuId);

    List<SkuInfo> getAll();

    List<SkuInfo> getAll(PublicationStatus status);

    List<SkuInfo> getAll(PublicationStatus status, PageInfo pageInfo);

    boolean remove(long skuId, long operatorId);

    long getCnt(PublicationStatus status);

    long getCnt(PublicationStatus status, Date startDate);

    List<SkuInfo> getByLookId(long lookId);
}
