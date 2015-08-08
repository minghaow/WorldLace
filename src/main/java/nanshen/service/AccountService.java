package nanshen.service;

import nanshen.data.AdminUserInfo;
import nanshen.data.LookInfo;

import java.util.List;
import java.util.Map;

/**
 * 账户的相关操作
 *
 * @author WANG Minghao
 */
public interface AccountService {

    /**
     * 根据搭配信息表得到管理员信息
     *
     * @param lookInfoList
     * @return
     */
    Map<Long, AdminUserInfo> getAdminUserInfoBy(List<LookInfo> lookInfoList);

    /**
     * 根据管理员Id获得管理员信息
     *
     * @param adminUserId
     * @return
     */
    AdminUserInfo getAdminUserInfoBy(Long adminUserId);

}
