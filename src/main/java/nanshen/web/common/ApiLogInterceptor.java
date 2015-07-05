package nanshen.web.common;

import nanshen.utils.LogUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 买手系统web接口Log记录拦截器
 *
 * @author WANG Minghao
 */
public class ApiLogInterceptor extends BaseController implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        printRequestLogWithParams(request);
        return true;
    }

    private void printRequestLogWithParams(HttpServletRequest request) {
        StringBuilder content = new StringBuilder();
        content.append("[");
        content.append("未登录用户");
        content.append("] ");
        content.append(request.getRequestURI());
        String method = request.getMethod();
        if ("POST".equals(method)) {
            appendPostParams(content, request);
        } else if ("GET".equals(method)) {
            appendGetParams(content, request);
        }
        LogUtils.info(content.toString());
    }

    private void appendGetParams(StringBuilder content, HttpServletRequest request) {
        String queryString = request.getQueryString();
        if (!StringUtils.isBlank(queryString)) {
            content.append("?");
            content.append(queryString);
        }
    }

    private void appendPostParams(StringBuilder content, HttpServletRequest request) {
        Map parameterMap = request.getParameterMap();
        if (parameterMap != null && parameterMap.size() != 0) {
            content.append("?");
            for (Object key : parameterMap.keySet()) {
                content.append(key);
                content.append("=");
                content.append(request.getParameterValues(key.toString())[0]);
                content.append("&");
            }
            content.deleteCharAt(content.length() - 1);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                        ModelAndView modelAndView) {}

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {}

}
