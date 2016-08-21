package nanshen.data.Sku;

import nanshen.data.CustomerReview.CustomerReview;
import org.nutz.dao.entity.annotation.Table;

import java.util.List;

/**
 * SkuItemComplex, item
 *
 * @author WANG Minghao
 */
@Table("SkuItemComplex")
public class SkuItemComplex {

    /** sku item id */
    private long skuItemId;

    /** origin sku item info */
    private SkuItem skuItem;

    /** sales info */
    private SalesInfo salesInfo;

    /** image url list, default for 1 to 2 images */
    private List<String> imgUrlList;

    /** sku list, default for 1 to 2 skus */
    private List<SkuTag> skuTagList;

    /** sku detail */
    private List<SkuDetail> skuDetailList;

    /** customer review */
    private List<CustomerReview> customerReviewList;

    public SkuItemComplex(long skuItemId, List<CustomerReview> customerReviewList, SalesInfo salesInfo,
                          List<SkuDetail> skuDetailList, SkuItem skuItem) {
        this.customerReviewList = customerReviewList;
        this.salesInfo = salesInfo;
        this.skuDetailList = skuDetailList;
        this.skuItem = skuItem;
        this.skuItemId = skuItemId;
    }

    public List<CustomerReview> getCustomerReviewList() {
        return customerReviewList;
    }

    public void setCustomerReviewList(List<CustomerReview> customerReviewList) {
        this.customerReviewList = customerReviewList;
    }

    public List<String> getImgUrlList() {
        return imgUrlList;
    }

    public void setImgUrlList(List<String> imgUrlList) {
        this.imgUrlList = imgUrlList;
    }

    public SalesInfo getSalesInfo() {
        return salesInfo;
    }

    public void setSalesInfo(SalesInfo salesInfo) {
        this.salesInfo = salesInfo;
    }

    public List<SkuDetail> getSkuDetailList() {
        return skuDetailList;
    }

    public void setSkuDetailList(List<SkuDetail> skuDetailList) {
        this.skuDetailList = skuDetailList;
    }

    public SkuItem getSkuItem() {
        return skuItem;
    }

    public void setSkuItem(SkuItem skuItem) {
        this.skuItem = skuItem;
    }

    public long getSkuItemId() {
        return skuItemId;
    }

    public void setSkuItemId(long skuItemId) {
        this.skuItemId = skuItemId;
    }

    public List<SkuTag> getSkuTagList() {
        return skuTagList;
    }

    public void setSkuTagList(List<SkuTag> skuTagList) {
        this.skuTagList = skuTagList;
    }
}
