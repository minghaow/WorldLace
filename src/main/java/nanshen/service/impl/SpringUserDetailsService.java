package nanshen.service.impl;

import nanshen.dao.UserInfoDao;
import nanshen.data.User.UserInfo;
import nanshen.utils.RequestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * SpringSecurity用于判断用户权限的服务
 *
 * @author WANG Minghao
 */
@Service
public class SpringUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoDao userInfoDao;

    /**
     * 增加用户名密码错误的计数
     *
     * @param username 用户名
     */
    public void increaseFailureCount(String username) {
        if (StringUtils.isBlank(username)) {
            return;
        }
        String key = getFailureCountKey(username);
//        failureCount.put(key, failureCount.getUnchecked(key) + 1);
    }

    private String getFailureCountKey(String username) {
        return username + "|" + RequestUtils.getRequestIp();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoDao.getUserInfoByPhone(username);
        if (null == userInfo) {
            throw new UsernameNotFoundException("Phone " + username + " not found!");
        }
        return generateUserDetails(userInfo);
    }

    private UserDetails generateUserDetails(UserInfo userInfo) {
        return new User(
                String.valueOf(userInfo.getId()),
                userInfo.getPassword(),
                userInfo.isAvailable(),
                true, true, !failureTooMuch(userInfo.getUsername()),
                userInfo.getAuthorities()
        );
    }

    /**
     * 检查错误计数是否超过最大允许错误数量
     *
     * @param username
     * @return
     */
    public boolean failureTooMuch(String username) {
//        return 4 < failureCount.getUnchecked(getFailureCountKey(username));
        return false;
    }

}
