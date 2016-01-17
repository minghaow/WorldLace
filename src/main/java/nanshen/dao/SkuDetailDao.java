package nanshen.dao;

import nanshen.data.PageInfo;
import nanshen.data.PublicationStatus;
import nanshen.data.SkuDetail;
import nanshen.data.SkuInfo;

import java.util.Date;
import java.util.List;

/**
 * @author WANG Minghao
 */
public interface SkuDetailDao {

    SkuDetail insert(SkuDetail skuDetail);

    SkuDetail get(long skuId);

    boolean update(SkuInfo skuId);

    boolean remove(long skuId);

    List<SkuDetail> getAll();

    List<SkuDetail> getAll(PublicationStatus status);

    List<SkuDetail> getAll(PublicationStatus status, PageInfo pageInfo);

    boolean remove(long skuId, long operatorId);

    long getCnt(PublicationStatus status);

    long getCnt(PublicationStatus status, Date startDate);

}
