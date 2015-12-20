package nanshen.dao.impl;

import nanshen.dao.UserInfoDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.UserInfo;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 买手信息数据库操作实现
 *
 * @author KONG Xiangxin
 */
@Repository
public class UserInfoDaoImpl extends BaseDao implements UserInfoDao {

    @Override
    public UserInfo getBuyerInfoByUsername(String username) {
        UserInfo info = dao.fetch(UserInfo.class, Cnd.where("username", "=", username));
        return dao.fetchLinks(info, "authoritiesInDb");
    }

    @Override
    public UserInfo getBuyerInfo(long id) {
        UserInfo info =  dao.fetch(UserInfo.class, id);
        return dao.fetchLinks(info, "authoritiesInDb");
    }

    @Override
    public UserInfo addNewBuyer(UserInfo info) {
        return dao.insert(info);
    }

    @Override
    public boolean updateBuyer(UserInfo info) {
        return 1 == dao.update(info);
    }

    @Override
    public UserInfo getBuyerInfoByEmail(String email) {
        return dao.fetch(UserInfo.class, Cnd.where("username", "=", email));
    }

    @Override
    public boolean login(String username, String ip, Date loginTime) {
        Sql sql = Sqls.create("UPDATE BuyerInfo " +
                "SET loginCount = loginCount + 1, loginIp = @loginIp, loginTime = @loginTime " +
                "WHERE username = @username");
        sql.params().set("loginIp", ip);
        sql.params().set("loginTime", loginTime);
        sql.params().set("username", username);
        dao.execute(sql);
        return 1 == sql.getUpdateCount();
    }

    @Override
    public List<UserInfo> getAllBuyerInfos(List<UserInfo> buyerTypeList) {
        Condition condition = Cnd.where("buyerType", "in", buyerTypeList);
        return dao.query(UserInfo.class, condition);
    }

    @Override
    public List<UserInfo> getBuyerInfo(List<Long> buyerIds) {
        Condition condition = Cnd
                .where("id", "in", buyerIds);
        return dao.query(UserInfo.class, condition);
    }

}
