package nanshen.web.common;

import nanshen.utils.CookieUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * LanguageInterceptor 语言拦截器
 *
 * @Author WANG Minghao
 */
public class LanguageInterceptor extends BaseController implements HandlerInterceptor {

    private static final String PARAM_LANGUAGE = "l";
    private static final String PARAM_LANGUAGE_ENGLISH = "en";
    private static final String COOKIE_LANGUAGE = "l";
    private static final String COOKIE_LANGUAGE_ENGLISH = "en";
    private static final String COOKIE_LANGUAGE_CHINESE = "ch";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if (isLanguageChangingRequest(request)) {
            String languageParam = request.getParameter(PARAM_LANGUAGE);
            if (languageParam.equals(PARAM_LANGUAGE_ENGLISH)) {
                response.addCookie(new Cookie(COOKIE_LANGUAGE, COOKIE_LANGUAGE_ENGLISH));
            } else {
                response.addCookie(new Cookie(COOKIE_LANGUAGE, COOKIE_LANGUAGE_CHINESE));
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
        if (isEnglishRequest(request)) {
            modelAndView.addObject("en", "true");
        }
    }

    private boolean isEnglishRequest(HttpServletRequest request) {

        if (isLanguageChangingRequest(request)) {
            String languageParam = request.getParameter(PARAM_LANGUAGE);
            if (languageParam.equals(PARAM_LANGUAGE_ENGLISH)) {
                return true;
            }
        }

        Cookie languageCookie = CookieUtils.getCookieByName(request, COOKIE_LANGUAGE);
        if (languageCookie != null && languageCookie.getValue().equals(COOKIE_LANGUAGE_ENGLISH)) {
            return true;
        }

        return false;
    }

    private boolean isLanguageChangingRequest(HttpServletRequest request) {
        String languageParam = request.getParameter(PARAM_LANGUAGE);
        return StringUtils.isNotBlank(languageParam);
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {}
}
