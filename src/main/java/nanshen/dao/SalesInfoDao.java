package nanshen.dao;

import nanshen.data.Sku.SalesInfo;

import java.util.List;

/**
 * @author WANG Minghao
 */
public interface SalesInfoDao {

    SalesInfo insert(SalesInfo salesInfo);

    SalesInfo get(long skuId);

    List<SalesInfo> getBySkuItemId(long skuItemId);

    List<SalesInfo> get(List<Long> skuIdList);

    boolean update(SalesInfo salesInfo);

    boolean remove(long skuId);
}
