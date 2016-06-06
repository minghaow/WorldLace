package nanshen.dao;

import nanshen.data.IDCard;
import nanshen.data.IDCardImage;
import nanshen.data.Region;
import nanshen.data.UserAddress;

import java.util.Date;
import java.util.List;

/**
 * 用户地址的数据库操作
 *
 * @author WANG Minghao
 */
public interface UserAddressDao {

    /**
     * 根据用户地址的ID获取用户地址信息
     *
     * @param addressId
     * @return
     */
    public UserAddress getUserAddress(long addressId);

    /**
     * 新增一个地址
     *
     * @param userAddress
     * @return
     */
    public UserAddress insertAddress(UserAddress userAddress);

    /**
     * 获取地区子节点的数据
     *
     * @param regionId
     * @return
     */
    public List<Region> getChildrenRegions(long regionId);

    /**
     * 根据用户身份证的数据库ID获取身份证信息
     *
     * @param cardId
     * @return
     */
    public IDCard getIDCard(long cardId);

    /**
     * 新增一个身份证
     *
     * @param idCard
     * @return
     */
    public IDCard insertIDCard(IDCard idCard);

    /**
     * 将身份证正面图片和某个身份证关联
     *
     * @param cardId
     * @param imageId
     * @return
     */
    public boolean updateCardFrontImage(long cardId, long imageId);

    /**
     * 将身份证背面图片和某个身份证关联
     *
     * @param cardId
     * @param imageId
     * @return
     */
    public boolean updateCardBackImage(long cardId, long imageId);

    /**
     * 根据身份证图片的ID获取身份证图片的数据
     *
     * @param imageId
     * @return
     */
    public IDCardImage getCardImage(long imageId);

    /**
     * 添加一个身份证图片，但没有和任何身份证关联
     *
     * @param userId
     * @param data
     * @return
     */
    public IDCardImage insertCardImage(long userId, byte[] data);

    /**
     * 标记身份证图片是否和某个身份证关联
     *
     * @param imageId
     * @param available
     * @return
     */
    public boolean setCardImageValid(long imageId, boolean available);

    /**
     * 验证通过用户的地址
     *
     * @param addressId
     * @return
     */
    public boolean verifyAddress(long addressId);

    /**
     * 更新身份证过期时间
     *
     * @param addressId
     * @param date
     * @return
     */
    public boolean updateIDCardExpireDate(long addressId, Date date);

    /**
     * 根据用户的id获得用户地址列表
     *
     * @param userId
     * @return
     */
    List<UserAddress> getUserAddressListByUserId(long userId);
}
