package nanshen.web.common;

import nanshen.constant.SystemConstants;
import nanshen.dao.AdminUserInfoDao;
import nanshen.data.AdminUserInfo;
import nanshen.utils.JsonUtils;
import nanshen.utils.ViewUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.io.IOException;

/**
 * Controller的基类，定义了Controller使用的基本方法，所有Controller都应该继承该基类
 *
 * @author WANG Minghao
 */
public abstract class BaseController {

    @Autowired
    protected AdminUserInfoDao adminUserInfoDao;

    class StringEscapeEditor extends PropertyEditorSupport {

        @Override
        public void setAsText(String text) {
            if (text != null) {
                text = html(text);
            }
            setValue(text);
        }

        /**
         * 该方法必须重载，否则在Java6环境下会出问题
         *
         * @return
         */
        @Override
        public String getAsText() {
            return (String) getValue();
        }

    }

    /**
     * 默认对所有的输入字符串进行过滤，防止XSS攻击
     * <p />
     * 输入的字符串变量不需要使用{@link #html(String)}方法过滤了
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringEscapeEditor());
    }

    /**
     * 将html文件中直接展示的文本进行转换，以预防XSS漏洞。
     * <p>
     * 若str变量要在html中直接展示，如使用&lt;span>$!str&lt;/span>的模板代码， 则应该对str使用此函数进行过滤。
     * </p>
     *
     * @param display
     *            待展示的字符串
     * @return
     */
    protected String html(String display) {
        return ViewUtils.getHTMLValidText(display);
    }

    /**
     * 获取当前客户端的ip地址，若客户端使用代理返回的可能是代理服务器的地址
     *
     * @param request
     * @return
     */
    protected String getRequestIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    /**
     * 获取当前已登录管理员用户的信息
     *
     * @param request
     * @return
     */
    protected AdminUserInfo getLoginedUser(HttpServletRequest request) {
        String username = (String)request.getSession().getAttribute("current_user");
        if (StringUtils.isBlank(username)) {
            return null;
        }
        return adminUserInfoDao.get(username);
    }

    protected AdminUserInfo prepareLoginUserInfo(HttpServletRequest request, ModelMap model) {
        AdminUserInfo adminUserInfo = getLoginedUser(request);
        model.addAttribute("adminUserInfo", adminUserInfo);
        return adminUserInfo;
    }

    /**
     * 将指定对象转换为json输出
     *
     * @param response
     * @param object
     * @throws IOException
     */
    protected void responseJson(HttpServletResponse response, Object object) throws IOException {
        response.setContentType("application/json;charset=" + SystemConstants.SYS_ENC);
        response.getWriter().print(JsonUtils.toJson(object));
    }

}
