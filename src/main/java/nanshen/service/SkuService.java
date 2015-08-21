package nanshen.service;

import nanshen.data.*;
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
     * @param skuInfo the target sku info
     * @return boolean
     */
    boolean update(SkuInfo skuInfo);

    /**
     * Update sku info
     *
     * @param skuId skuId
     * @param title the sku title
     * @param subTitle the sku subtitle
     * @param url the sku link
     * @param desc the sku description
     * @param operatorId the uploader
     * @return boolean
     */
    boolean update(long skuId, String title, String subTitle, String url, String desc, long operatorId);

    /**
     * Remove sku according to skuId
     *
     * @param skuId skuId
     * @return
     */
    boolean remove(long skuId);

    /**
     * Remove sku according to skuId and admin user
     *
     * @param skuId lookId
     * @return
     */
    ExecInfo remove(long skuId, AdminUserInfo adminUserInfo);

    /**
     * Get sku info by skuId
     *
     * @param skuId skuId
     * @return
     */
    SkuInfo get(long skuId);

    /**
     * Get sku info by skuId. Create one if find nothing.
     *
     * @param skuId skuId
     * @param operatorId uploader
     * @return
     */
    SkuInfo getOrCreateSkuInfo(long skuId, long operatorId);

    /**
     * Upload the sku images
     *
     * @param skuId the sku of the image
     * @param operatorId uploader
     * @param file image file
     * @return ExecResult<SkuInfo>
     */
    ExecResult<SkuInfo> uploadImage(long skuId, long operatorId, MultipartFile file) throws IOException;

    /**
     * Get all of the skus for specified publication status
     *
     * <strong>Note:<strong/> already paged.
     *
     * @param status publication status
     * @param pageInfo page number
     * @return List<LookInfo>
     */
    List<SkuInfo> getAll(PublicationStatus status, PageInfo pageInfo);

    /**
     * Get the all of the tags in a list
     * {@code nanshen.data.LookTag}
     *
     * @return List<LookTag>
     */
    List<LookTag> getAllTag();

    /**
     * Get the count of looks for the specified publication status
     *
     * @param status publication status
     * @return long
     */
    long getCnt(PublicationStatus status);

    /**
     * Get the count of this week new looks for the specified publication status
     *
     * @param status publication status
     * @return long
     */
    long getThisWeekCnt(PublicationStatus status);

    /**
     * Get all of the sku info by lookId
     * @param lookId look id
     * @return
     */
    List<SkuInfo> getByLookId(long lookId);
}
