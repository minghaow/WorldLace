/*
 * @(#)UserAddressServiceImpl.java, 2015-3-20.
 *
 * Copyright 2015 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package nanshen.service.impl;

import nanshen.dao.OrderDao;
import nanshen.dao.UserAddressDao;
import nanshen.data.*;
import nanshen.service.UserAddressService;
import nanshen.service.common.BaseService;
import nanshen.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * 用户地址的相关操作的实现
 *
 * @author WANG Minghao
 */
@Service
public class UserAddressServiceImpl extends BaseService implements UserAddressService {

    @Autowired
    private UserAddressDao userAddressDao;

    @Autowired
    private OrderDao orderDao;

    @Override
    public UserAddress getAddress(long orderId) {
        Order order = orderDao.get(orderId);
        if (null == order) {
            return null;
        }
        return userAddressDao.getUserAddress(order.getAddressId());
    }

    @Override
    public ExecInfo changeAddress(long orderId, long operatorId, UserAddress userAddress) {
        Order order = orderDao.get(orderId);
        if (null == order) {
            return ExecInfo.fail("订单不存在");
        }
        userAddress.setIDCardId(0);
        userAddress.setDisplay(false);
        userAddress.setVerified(true);
        UserAddress insertedUserAddress = userAddressDao.insertAddress(userAddress);
        if (!orderDao.changeAddress(orderId, insertedUserAddress.getId())) {
            return ExecInfo.fail("修改地址失败");
        }
        orderLogDao.log(orderId, operatorId, OrderOperation.CHANGE_ADDRESS,
                "from " + order.getAddressId() + " to " + insertedUserAddress.getId());
        return ExecInfo.succ();
    }

    @Override
    public IDCard getIdCard(long cardId) {
        return userAddressDao.getIDCard(cardId);
    }

    @Override
    public IDCardImage getIdCardImage(long imageId) {
        return userAddressDao.getCardImage(imageId);
    }

    @Override
    public List<Region> getChildrenRegions(long regionId) {
        return userAddressDao.getChildrenRegions(regionId);
    }

    @Override
    public IDCardImage createIdCardImageForIdCard(long userId, MultipartFile file) {
        byte[] imageData;
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            BufferedImage smallImage = ImageUtils.resize(image, 680, 950);
            imageData = ImageUtils.getData(smallImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return userAddressDao.insertCardImage(userId, imageData);
    }

    @Override
    public UserAddress createAddress(UserAddress userAddress) {
        return userAddressDao.insertAddress(userAddress);
    }

}
