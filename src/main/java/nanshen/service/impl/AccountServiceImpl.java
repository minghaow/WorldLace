package nanshen.service.impl;

import nanshen.dao.AdminUserInfoDao;
import nanshen.data.AdminUserInfo;
import nanshen.data.LookInfo;
import nanshen.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * NanShen
 *
 * @Author WANG Minghao
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AdminUserInfoDao adminUserInfoDao;

    @Override
    public Map<Long, AdminUserInfo> getAdminUserInfoBy(List<LookInfo> lookInfoList) {
        Map<Long, AdminUserInfo> idAndAdminUserInfoMap = new HashMap<Long, AdminUserInfo>();
        for (LookInfo lookInfo : lookInfoList) {
            idAndAdminUserInfoMap.put(lookInfo.getUploadUserId(), getAdminUserInfoBy(lookInfo.getUploadUserId()));
        }
        return idAndAdminUserInfoMap;
    }

    @Override
    public AdminUserInfo getAdminUserInfoBy(Long adminUserId) {
        return adminUserInfoDao.get(adminUserId);
    }

}
