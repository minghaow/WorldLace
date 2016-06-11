/*
 * @(#)UserAddressDaoImpl.java, 2014-12-16.
 *
 * Copyright 2014 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package nanshen.dao.impl;

import nanshen.dao.UserAddressDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.IDCard;
import nanshen.data.IDCardImage;
import nanshen.data.Region;
import nanshen.data.UserAddress;
import nanshen.utils.CollectionExtractor;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户地址的数据库操作的实现
 *
 * @author WANG Minghao
 */
@Repository
public class UserAddressDaoImpl extends BaseDao implements UserAddressDao {

    private CollectionExtractor<Integer, Region> regionExtractor = new CollectionExtractor<Integer, Region>() {
        @Override
        protected Integer convert(Region region) {
            return region.getRegionId();
        }
    };

    @Override
    public UserAddress getUserAddress(long addressId) {
        UserAddress address = dao.fetch(UserAddress.class, addressId);
        fillRegion(address);
        return address;
    }

    @Override
    public UserAddress insertAddress(UserAddress userAddress) {
        return dao.insert(userAddress);
    }

    private void fillRegion(UserAddress address) {
        List<Integer> regionIds = new ArrayList<Integer>();
        regionIds.add(address.getLevel1());
        regionIds.add(address.getLevel2());
        regionIds.add(address.getLevel3());
        regionIds.add(address.getLevel4());
        List<Region> regions = dao.query(Region.class, Cnd.where("regionId", "in", regionIds));
        Map<Integer, Region> regionMap = regionExtractor.extractMap(regions);
        address.setRegion1(getRegionFromMap(address.getLevel1(), regionMap));
        address.setRegion2(getRegionFromMap(address.getLevel2(), regionMap));
        address.setRegion3(getRegionFromMap(address.getLevel3(), regionMap));
        address.setRegion4(getRegionFromMap(address.getLevel4(), regionMap));
    }

    private String getRegionFromMap(int regionId, Map<Integer, Region> regionMap) {
        Region region = regionMap.get(regionId);
        if (null == region) {
            return "";
        }
        return region.getRegionName();
    }

    @Override
    public List<Region> getChildrenRegions(long regionId) {
        Condition condition = Cnd.where("parentId", "=", regionId);
        return dao.query(Region.class, condition);
    }

    @Override
    public IDCard getIDCard(long cardId) {
        return dao.fetch(IDCard.class, cardId);
    }

    @Override
    public IDCard insertIDCard(IDCard idCard) {
        return dao.insert(idCard);
    }

    @Override
    public IDCardImage getCardImage(long imageId) {
        return dao.fetch(IDCardImage.class, Cnd.where("id", "=", imageId));
    }

    @Override
    public boolean updateCardFrontImage(long cardId, long imageId) {
        Chain chain = Chain
                .make("frontImageId", imageId)
                .add("updateTime", new Date());
        Condition condition = Cnd.where("id", "=", cardId);
        return 1 == dao.update(IDCard.class, chain, condition);
    }

    @Override
    public boolean updateCardBackImage(long cardId, long imageId) {
        Chain chain = Chain
                .make("backImageId", imageId)
                .add("updateTime", new Date());
        Condition condition = Cnd.where("id", "=", cardId);
        return 1 == dao.update(IDCard.class, chain, condition);
    }

    @Override
    public IDCardImage insertCardImage(long userId, byte[] data) {
        IDCardImage idCardImage = new IDCardImage();
        idCardImage.setUserId(userId);
        idCardImage.setImageData(data);
        return dao.insert(idCardImage);
    }

    @Override
    public boolean setCardImageValid(long imageId, boolean available) {
        Chain chain = Chain.make("isValid", available);
        Condition condition = Cnd.where("id", "=", imageId);
        return 1 == dao.update(IDCardImage.class, chain, condition);
    }

    @Override
    public boolean verifyAddress(long addressId) {
        Chain chain = Chain.make("verified", 1);
        Condition condition = Cnd.where("id", "=", addressId);
        return 1 == dao.update(UserAddress.class, chain, condition);
    }

    @Override
    public boolean updateIDCardExpireDate(long addressId, Date date) {
        UserAddress address = dao.fetch(UserAddress.class, addressId);
        if (address != null && address.getIDCardId() != 0 ) {
            Chain chn = Chain.make("expireDate", date);
            Condition cnd = Cnd.where("id", "=", address.getIDCardId());
            return 1 == dao.update(IDCard.class, chn, cnd);
        }
        return false;
    }

    @Override
    public List<UserAddress> getUserAddressListByUserId(long userId) {
        Condition cnd = Cnd.where("userId", "=", userId).desc("id");
        return dao.query(UserAddress.class, cnd);
    }

}
