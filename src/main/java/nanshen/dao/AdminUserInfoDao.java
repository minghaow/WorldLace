package nanshen.dao;

import nanshen.data.AdminUserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WANG Minghao
 */
@Repository
public interface AdminUserInfoDao {

    AdminUserInfo insert(AdminUserInfo userInfo);

    List<AdminUserInfo> getAll();

}
