package nanshen.web.controller.user;

import nanshen.data.LoginError;
import nanshen.web.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 登录认证相关页面
 *
 * @author WANG Minghao
 */
@Controller
@RequestMapping("/auth")
public class AuthorityCtrl extends BaseController {

    @RequestMapping("/login_page")
    public String login(Map<String, Object> model, LoginError error) {
        model.put("error", error);
        return "user/login";
    }

    @RequestMapping("/logout")
    public String logout(Map<String, Object> model, @RequestParam(defaultValue = "false") boolean error) {
        model.put("error", error);
        return "user/login";
    }

    @RequestMapping("/deny")
    public String deny() {
        return "user/deny";
    }

}
