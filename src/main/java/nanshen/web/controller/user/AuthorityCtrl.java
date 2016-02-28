package nanshen.web.controller.user;

import nanshen.data.ExecInfo;
import nanshen.data.LoginError;
import nanshen.data.PageType;
import nanshen.service.AccountService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AccountService accountService;

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
     * Login successfully
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
     * Login failed
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

    /**
     * Register new user
     *
     * @param response
     * @param model
     * @param password1
     * @param password2
     * @param phone
     * @throws IOException
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(HttpServletResponse response, ModelMap model,
                         @RequestParam(defaultValue = "") String password1,
                         @RequestParam(defaultValue = "") String password2,
                         @RequestParam(defaultValue = "") String phone)
            throws IOException {
        ExecInfo execInfo = registerInputValidCheck(phone, password1, password2);
        if (!execInfo.isSucc()) {
            model.put("msg", execInfo.getMsg());
        } else {
            execInfo = accountService.createNewUser(phone, password1);
        }
        model.put("success", execInfo.isSucc());
        model.put("msg", execInfo.getMsg());
        responseJson(response, model);
    }

    private ExecInfo registerInputValidCheck(String phone, String password1, String password2) {
        ExecInfo execInfo = ExecInfo.succ();
        if (StringUtils.isBlank(phone)) {
            execInfo = ExecInfo.fail("抱歉，手机号不能为空，注册失败。");
        } else if (StringUtils.isBlank(password1)) {
            execInfo = ExecInfo.fail("抱歉，密码不能为空，注册失败。");
        } else if (!password1.equals(password2)) {
            execInfo = ExecInfo.fail("抱歉，两次密码输入不一致，请重新输入。");
        } else if (password1.equalsIgnoreCase(phone) || password1.equalsIgnoreCase("123456") || password1.length() < 6) {
            execInfo = ExecInfo.fail("抱歉，密码过于简单，请重新输入。");
        }
        return execInfo;
    }

}
