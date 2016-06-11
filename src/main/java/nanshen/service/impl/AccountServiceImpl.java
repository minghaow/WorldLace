package nanshen.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import nanshen.constant.TimeConstants;
import nanshen.dao.AdminUserInfoDao;
import nanshen.dao.UserAddressDao;
import nanshen.dao.UserInfoDao;
import nanshen.data.*;
import nanshen.service.AccountService;
import nanshen.service.common.ScheduledService;
import nanshen.utils.EncryptUtils;
import nanshen.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Account Service Implementation
 * NOTE: Mainly provide id and account information map according to all kinds of list
 *
 * @Author WANG Minghao
 */
@Service
public class AccountServiceImpl extends ScheduledService implements AccountService {

    @Autowired
    private AdminUserInfoDao adminUserInfoDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserAddressDao userAddressDao;

    /** 买手ID到买手信息的缓存 */
    private final LoadingCache<Long, UserInfo> userCache = CacheBuilder.newBuilder()
            .softValues()
            .expireAfterWrite(TimeConstants.HALF_HOUR_IN_SECONDS, TimeUnit.SECONDS)
            .build(
                    new CacheLoader<Long, UserInfo>() {
                        @Override
                        public UserInfo load(Long id) throws Exception {
                            return userInfoDao.getUserInfo(id);
                        }
                    });

    Map<Long, AdminUserInfo> adminIdUserInfoMap = new HashMap<Long, AdminUserInfo>();
    Map<String, AdminUserInfo> adminNameUserInfoMap = new HashMap<String, AdminUserInfo>();

    public void update() {
        adminIdUserInfoMap = new HashMap<Long, AdminUserInfo>();
        adminNameUserInfoMap = new HashMap<String, AdminUserInfo>();
        List<AdminUserInfo> adminUserInfoList = adminUserInfoDao.getAll();
        for (AdminUserInfo adminUserInfo : adminUserInfoList) {
            adminIdUserInfoMap.put(adminUserInfo.getId(), adminUserInfo);
            adminNameUserInfoMap.put(adminUserInfo.getUsername(), adminUserInfo);
        }
    }

    public int updatePeriod() {
        return TimeConstants.HOUR_IN_SECONDS;
    }

    @Override
    public Map<Long, AdminUserInfo> getAdminUserInfoByLookInfoList(List<LookInfo> lookInfoList) {
        Map<Long, AdminUserInfo> idAndAdminUserInfoMap = new HashMap<Long, AdminUserInfo>();
        for (LookInfo lookInfo : lookInfoList) {
            idAndAdminUserInfoMap.put(lookInfo.getUploadUserId(), getAdminUserInfoByUserId(lookInfo.getUploadUserId()));
        }
        return idAndAdminUserInfoMap;
    }

    @Override
    public Map<Long, AdminUserInfo> getAdminUserInfoBySkuInfoList(List<SkuItem> skuItemList) {
        Map<Long, AdminUserInfo> idAndAdminUserInfoMap = new HashMap<Long, AdminUserInfo>();
        for (SkuItem skuItem : skuItemList) {
            idAndAdminUserInfoMap.put(skuItem.getUploadUserId(), getAdminUserInfoByUserId(skuItem.getUploadUserId()));
        }
        return idAndAdminUserInfoMap;
    }

    @Override
    public AdminUserInfo getAdminUserInfoByUsername(String username) {
        return adminNameUserInfoMap.get(username);
    }

    @Override
    public UserInfo getUserInfo(long userId) {
        try {
            return userCache.get(userId);
        } catch (ExecutionException e) {
            LogUtils.warning("AccountService: get user info error!", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearBuyerInfoCache() {
        userCache.invalidateAll();
    }

    @Override
    public ExecResult<UserInfo> createNewUser(String phone, String passwordOrigin) {
        UserInfo userInfo = new UserInfo(phone, EncryptUtils.encodePassword(passwordOrigin));
        userInfo = userInfoDao.addNewUser(userInfo);
        if (userInfo == null) {
            return ExecResult.fail("手机号已注册，请找回密码或换新的手机号~");
        }
        return ExecResult.succ(userInfo);
    }

    @Override
    public ExecInfo setUsername(long userId, String username) {
        if (!userInfoDao.setUsername(userId, username)) {
            return ExecInfo.fail("用户名设置失败，请联系客服！");
        }
        userCache.invalidate(userId);
        return ExecInfo.succ();
    }

    @Override
    public List<UserAddress> getUserAddressListByUserId(long userId) {
        return userAddressDao.getUserAddressListByUserId(userId);
    }

    @Override
    public UserAddress getUserAddress(long addressId) {
        return userAddressDao.getUserAddress(addressId);
    }

    @Override
    public ExecResult<UserInfo> checkRegistered(String phone) {
        UserInfo userInfo = userInfoDao.getUserInfoByPhone(phone);
        if (userInfo != null) {
            return ExecResult.fail("该手机号已经注册过，请直接登录");
        }
        return ExecResult.succ(null);
    }

    @Override
    public AdminUserInfo getAdminUserInfoByUserId(Long adminUserId) {
        return adminIdUserInfoMap.get(adminUserId);
    }

}
