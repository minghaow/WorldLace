package nanshen.dao.impl;

import nanshen.dao.UserAuthorityDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.User.UserAuthority;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 买手权限的相关操作具体实现
 *
 * @author WANG Minghao
 */
@Repository
public class UserAuthorityDaoImpl extends BaseDao implements UserAuthorityDao {
    @Override
    public boolean insert(UserAuthority buyerAuthority) {
        return dao.insert(buyerAuthority) != null;
    }

    @Override
    public List<UserAuthority> get(long buyerId) {
        Condition condition = Cnd.where("buyerId", "=", buyerId);
        return dao.query(UserAuthority.class, condition);
    }
}
