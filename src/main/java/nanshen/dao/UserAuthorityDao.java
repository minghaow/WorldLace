package nanshen.dao;

import nanshen.data.UserAuthority;

import java.util.List;

/**
 * 买手权限的相关操作
 *
 * @author WANG Minghao
 */
public interface UserAuthorityDao {

    /**
     * 添加一个新的买手权限
     *
     * @param buyerAuthority
     * @return BuyerApplyInfo
     */
    boolean insert(UserAuthority buyerAuthority);

    /**
     * 获取买手权限信息
     *
     * @param buyerId
     * @return
     */
    List<UserAuthority> get(long buyerId);
}
