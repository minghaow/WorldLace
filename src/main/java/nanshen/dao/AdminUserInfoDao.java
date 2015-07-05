package nanshen.dao;

import nanshen.data.AdminUserInfo;

import java.util.List;

/**
 * @author WANG Minghao
 */
public interface AdminUserInfoDao {

    AdminUserInfo insert(AdminUserInfo userInfo);

    List<AdminUserInfo> getAll();

}
