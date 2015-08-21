package nanshen.web.common;

import nanshen.data.AdminUserInfo;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * LoginInterceptor 登陆检查拦截器
 *
 * @Author WANG Minghao
 */
public class LoginInterceptor extends BaseController implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        final String adminPage = "/admin";
        final String defaultPage = "/";
        final String adminSignin = "/admin/auth/signin";
        if (request.getRequestURI().equals(adminPage) ||
                request.getRequestURI().equals(defaultPage) ||
                request.getRequestURI().equals(adminSignin)) {
            return true;
        }
        AdminUserInfo adminUserInfo = getLoginedUser(request);
        if (adminUserInfo == null && request.getRequestURI().contains(adminPage)) {
            response.sendRedirect("/admin");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {}

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {}
}
