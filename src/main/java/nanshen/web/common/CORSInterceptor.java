package nanshen.web.common;

import nanshen.constant.SystemConstants;
import nanshen.web.controller.user.BaseCtrl;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cross Origin Resource Sharing Interceptor
 *
 * @author WANG Minghao
 */
public class CORSInterceptor extends BaseCtrl implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", SystemConstants.IMAGE_URL);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                        ModelAndView modelAndView) {}

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {}

}
