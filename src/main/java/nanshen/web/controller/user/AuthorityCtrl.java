package nanshen.web.controller.user;

import nanshen.data.LoginError;
import nanshen.data.PageType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录认证相关页面
 *
 * @author WANG Minghao
 */
@Controller
@RequestMapping("/auth")
public class AuthorityCtrl extends BaseCtrl {

    @RequestMapping("/login_page")
    public String login(ModelMap model, LoginError error) {
        prepareHeaderModel(model, PageType.LOGIN_PAGE);
        model.put("error", error);
        return "user/login";
    }

    @RequestMapping("/logout")
    public String logout(ModelMap model, @RequestParam(defaultValue = "false") boolean error) {
        model.put("error", error);
        return "user/login";
    }

    @RequestMapping("/deny")
    public String deny() {
        return "user/deny";
    }

    /**
     * 登陆成功
     *
     * @param response
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public void loginSuccess(HttpServletResponse response, ModelMap model)
            throws IOException {
        model.put("success", "true");
        prepareHeaderModel(model, PageType.LOGIN_PAGE);
        responseJson(response, model);
    }

    /**
     * 登陆失败
     *
     * @param response
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/fail", method = RequestMethod.GET)
    public void loginFail(HttpServletResponse response, ModelMap model)
            throws IOException {
        model.put("success", "false");
        responseJson(response, model);
    }

}
