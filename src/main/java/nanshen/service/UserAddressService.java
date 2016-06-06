package nanshen.service;

import nanshen.data.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用户地址的相关操作
 *
 * @author WANG Minghao
 */
public interface UserAddressService {

    /**
     * 获取指定订单的地址信息
     *
     * @param orderId
     * @return
     */
    public UserAddress getAddress(long orderId);

    /**
     * 用指定的地址和身份证信息替换指定订单的地址信息
     *
     * @param orderId
     * @param operatorId
     * @param userAddress
     * @return
     */
    public ExecInfo changeAddress(long orderId, long operatorId, UserAddress userAddress);

    /**
     * 根据用户身份证的数据库ID，获取身份证信息
     *
     * @param cardId
     * @return
     */
    public IDCard getIdCard(long cardId);

    /**
     * 根据身份证图片的数据库ID，获取身份证图片
     *
     * @param imageId
     * @return
     */
    public IDCardImage getIdCardImage(long imageId);

    /**
     * 获取地区子节点的信息
     *
     * @param regionId
     * @return
     */
    public List<Region> getChildrenRegions(long regionId);

    /**
     * 为了更新指定身份证图片而生成一个身份证图片信息，但该身份证图片信息暂时没有关联到指定的身份证
     *
     * @param userId
     * @param file
     * @return
     */
    public IDCardImage createIdCardImageForIdCard(long userId, MultipartFile file);

    /**
     * 生成一个新的address
     *
     * @param userAddress
     * @return
     */
    UserAddress createAddress(UserAddress userAddress);
}
