package nanshen.service;

import nanshen.data.AdminUserInfo;
import nanshen.data.LookInfo;
import nanshen.data.SkuInfo;

import java.util.List;
import java.util.Map;

/**
 * 账户的相关操作
 *
 * @author WANG Minghao
 */
public interface AccountService {

    /**
     * 根据管理员Id获得管理员信息
     *
     * @param adminUserId
     * @return
     */
    AdminUserInfo getAdminUserInfoByUserId(Long adminUserId);

    /**
     * 根据搭配信息表得到管理员信息
     *
     * @param lookInfoList
     * @return
     */
    Map<Long, AdminUserInfo> getAdminUserInfoByLookInfoList(List<LookInfo> lookInfoList);

    /**
     * 根据单品信息表得到管理员信息
     *
     * @param skuInfoList
     * @return
     */
    Map<Long, AdminUserInfo> getAdminUserInfoBySkuInfoList(List<SkuInfo> skuInfoList);

    /**
     * 根据管理员名称获得管理员信息
     *
     * @param username
     * @return
     */
    AdminUserInfo getAdminUserInfoByUsername(String username);
}
