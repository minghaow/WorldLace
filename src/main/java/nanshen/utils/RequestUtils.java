package nanshen.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.switchuser.SwitchUserGrantedAuthority;

import java.util.Collection;

/**
 * 获取用户请求相关的信息
 *
 * @author WANG Minghao
 */
public class RequestUtils {

    /**
     * 判断用户是否登陆
     *
     * @return
     */
    public static boolean isLogined() {
        return loginedBuyerId() > 0;
    }

    /**
     * 获取登陆用户的ID，如果切换过用户则返回切换后的用户的ID
     *
     * @return 用户ID，若未登录返回0
     */
    public static long loginedBuyerId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getBuyerId(authentication);
    }

    /**
     * 是否切换过用户
     *
     * @return
     */
    public static boolean isSwitched() {
        return buyerIdBeforeSwitch() > 0;
    }

    /**
     * 获取切换用户前原始用户的ID
     *
     * @return 原始用户的ID
     */
    public static long originalBuyerId() {
        long buyerId = buyerIdBeforeSwitch();
        if (buyerId > 0) {
            return buyerId;
        }
        return loginedBuyerId();
    }

    private static long buyerIdBeforeSwitch() {
        Authentication original = null;
        Authentication current = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = current.getAuthorities();
        for (GrantedAuthority auth : authorities) {
            // check for switch user type of authority
            if (auth instanceof SwitchUserGrantedAuthority) {
                original = ((SwitchUserGrantedAuthority) auth).getSource();
            }
        }
        return getBuyerId(original);
    }

    private static long getBuyerId(Authentication authentication) {
        if (null == authentication || !authentication.isAuthenticated()) {
            return 0;
        }
        try {
            return Long.valueOf(authentication.getName());
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取客户端的真实IP地址
     *
     * @return IP地址
     */
    public static String getRequestIp() {
        return "127.0.0.1";
        // TODO: finish the ip checker
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        String ip = request.getHeader("X-Forwarded-For");
//        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
//            //多次反向代理后会有多个ip值，第一个ip才是真实ip
//            int index = ip.indexOf(",");
//            if(index != -1){
//                return ip.substring(0,index);
//            }else{
//                return ip;
//            }
//        }
//        ip = request.getHeader("X-Real-IP");
//        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
//            return ip;
//        }
//        return request.getRemoteAddr();
    }

}
