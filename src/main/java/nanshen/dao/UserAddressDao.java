package nanshen.dao;

import nanshen.data.Region;
import nanshen.data.User.IDCard;
import nanshen.data.User.IDCardImage;
import nanshen.data.User.UserAddress;

import java.util.Date;
import java.util.List;

/**
 * 用户地址的数据库操作
 *
 * @author WANG Minghao
 */
public interface UserAddressDao {

    public UserAddress getUserAddress(long addressId);

    public UserAddress insertAddress(UserAddress userAddress);

    public List<Region> getChildrenRegions(long regionId);

    public IDCard getIDCard(long cardId);

    public IDCard insertIDCard(IDCard idCard);

    public boolean updateCardFrontImage(long cardId, long imageId);

    public boolean updateCardBackImage(long cardId, long imageId);

    public IDCardImage getCardImage(long imageId);

    public IDCardImage insertCardImage(long userId, byte[] data);

    public boolean setCardImageValid(long imageId, boolean available);

    public boolean verifyAddress(long addressId);

    public boolean updateIDCardExpireDate(long addressId, Date date);

    List<UserAddress> getUserAddressListByUserId(long userId);

    List<UserAddress> getUserAddressByUserIdList(List<Long> userIdList);
}
