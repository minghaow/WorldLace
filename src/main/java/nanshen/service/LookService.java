package nanshen.service;

import nanshen.data.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Look related services
 *
 * @author WANG Minghao
 */
public interface LookService {

    /**
     * Update look info
     *
     * @param lookInfo the target look info
     * @return boolean
     */
    boolean update(LookInfo lookInfo);

    /**
     * Update look info
     *
     * @param lookId lookId
     * @param title the look title
     * @param subTitle the look subtitle
     * @param desc the look description
     * @param status publication status
     * @param operatorId the uploader
     * @return boolean
     */
    boolean update(long lookId, String title, String subTitle, String desc, PublicationStatus status, long operatorId);

    /**
     * 根据搭配id删除搭配
     *
     * @param lookId
     * @return
     */
    boolean removeLook(long lookId);

    /**
     * 根据搭配id和上传人删除搭配
     *
     * @param lookId lookId
     * @return
     */
    ExecInfo removeLook(long lookId, AdminUserInfo adminUserInfo);

    /**
     * 修改搭配状态
     *
     * @param lookId lookId
     * @param status the target status
     * @return
     */
    boolean changeStatus(long lookId, PublicationStatus status);

    /**
     * Get look info by lookId. Create one if find nothing.
     *
     * @param lookId lookId
     * @param operatorId uploader
     * @return
     */
    LookInfo getOrCreateLookInfo(long lookId, long operatorId);

    /**
     * Upload the look images
     *
     * @param lookId the look of the image
     * @param operatorId uploader
     * @param file image file
     * @return ExecResult<LookInfo>
     */
    ExecResult<LookInfo> uploadImage(long lookId, long operatorId, MultipartFile file) throws IOException;

    /**
     * Get all of the looks for specified publication status
     *
     * <strong>Note:<strong/> already paged.
     *
     * @param status publication status
     * @param pageInfo page number
     * @return List<LookInfo>
     */
    List<LookInfo> getAll(PublicationStatus status, PageInfo pageInfo);

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
}
