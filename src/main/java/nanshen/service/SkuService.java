package nanshen.service;

import nanshen.data.AdminUserInfo;
import nanshen.data.PublicationStatus;
import nanshen.data.Sku.SkuDetail;
import nanshen.data.Sku.SkuDetailType;
import nanshen.data.Sku.SkuItem;
import nanshen.data.Sku.StoreType;
import nanshen.data.StyleTag;
import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.ExecResult;
import nanshen.data.SystemUtil.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Sku related services
 *
 * @author WANG Minghao
 */
public interface SkuService {

    /**
     * Update sku info
     *
     * @param skuItem the target sku info
     * @return boolean
     */
    boolean update(SkuItem skuItem);

    /**
     * Update sku info
     * <strong>NOTE:</strong> The function will also change status to
     * {@link nanshen.data.PublicationStatus#OFFLINE}
     *
     * @param itemId itemId
     * @param title the sku title
     * @param subTitle the sku subtitle
     * @param url the sku link
     * @param desc the sku description
     * @param category the sku category {@link SkuDetailType}
     * @param operatorId the uploader
     * @return ExecInfo
     */
    ExecInfo update(long itemId, String title, String subTitle, String url, SkuDetailType category, String desc, long operatorId);

    /**
     * Update sku info
     *
     * @return ExecInfo
     */
    ExecInfo update(long itemId, String title, String subTitle, long price, boolean hasGif, String description1, String points,
                    String infos, String shipSpeed, String notice, String description2, String packageInfo);

    /**
     * Remove sku according to skuId
     *
     * @param itemId itemId
     * @return
     */
    boolean remove(long itemId);

    /**
     * Remove sku according to skuId and admin user
     *
     * @param itemId itemId
     * @return
     */
    ExecInfo remove(long itemId, AdminUserInfo adminUserInfo);

    /**
     * Get sku item info by itemId
     *
     * @param itemId itemId
     * @return
     */
    SkuItem getSkuItemInfo(long itemId);

    /**
     * Get sku item info by itemId
     *
     * @param itemId itemId
     * @return
     */
    SkuItem getOrCreateSkuItemInfo(long itemId);

    /**
     * Get sku info by skuId
     *
     * @param skuId skuId
     * @return
     */
    SkuDetail getSkuDetail(long skuId);

    /**
     * Get sku details by skuId
     *
     * @param itemId itemId
     * @return
     */
    List<SkuDetail> getSkuDetailByItemId(long itemId);

    /**
     * Get sku info by skuId. Create one if find nothing.
     *
     * @param itemId itemId
     * @param operatorId uploader
     * @return
     */
    SkuItem getOrCreateSkuInfo(long itemId, long operatorId);

    /**
     * Upload the sku images
     *
     * @param itemId the sku of the image
     * @param operatorId uploader
     * @param file image file
     * @return ExecResult<SkuInfo>
     */
    ExecResult<SkuItem> uploadImage(long itemId, long operatorId, MultipartFile file) throws IOException;

    /**
     * Get all of the skus for specified publication status
     *
     * <strong>Note:<strong/> already paged.
     *
     * @param status publication status
     * @param pageInfo page number
     * @return List<LookInfo>
     */
    List<SkuItem> getAll(PublicationStatus status, PageInfo pageInfo);

    /**
     * Get all of the skus for specified publication status
     *
     * <strong>Note:<strong/> already paged.
     *
     * @param storeType StoreType
     * @param pageInfo page number
     * @return List<LookInfo>
     */
    List<SkuItem> getAll(StoreType storeType, PageInfo pageInfo);

    /**
     * Get the all of the tags in a list
     * {@code nanshen.data.LookTag}
     *
     * @return List<LookTag>
     */
    List<StyleTag> getAllTag();

    /**
     * Get the count of looks for the specified publication status
     *
     * @param publicationStatus publication status
     * @return long
     */
    long getCnt(PublicationStatus publicationStatus);

    /**
     * Get the count of this week new looks for the specified publication status
     *
     * @param publicationStatus publication status
     * @return long
     */
    long getThisWeekCnt(PublicationStatus publicationStatus);

    /**
     * Get all of the sku info by lookId
     * @param itemId look id
     * @return
     */
    List<SkuItem> getByLookId(long itemId);

    /**
     * Change the publication status of a sku item
     *
     * @param itemId the id of item
     * @param publicationStatus publication status
     * @return
     */
    boolean changeStatus(long itemId, PublicationStatus publicationStatus);

    /**
     * Add look id for each sku
     *
     * @param itemId itemId
     * @param skuIdList sku id list, split by ','
     * @return ExecInfo
     */
    ExecInfo addRelatedSku(long itemId, String skuIdList);

    /**
     * Get sku detail list by sku id list
     *
     * @param skuIdList sku id list
     * @return List<SkuDetail>
     */
    List<SkuDetail> getSkuDetailList(List<Long> skuIdList);
}
