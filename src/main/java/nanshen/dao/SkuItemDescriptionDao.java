package nanshen.dao;

import nanshen.data.Sku.SkuItemDescription;

/**
 * @author WANG Minghao
 */
public interface SkuItemDescriptionDao {

    SkuItemDescription insert(long itemId);

    boolean update(SkuItemDescription skuItemDescription);

    SkuItemDescription get(long itemId);

}
