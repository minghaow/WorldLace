package nanshen.web.controller.admin;

import nanshen.dao.AdminUserInfoDao;
import nanshen.data.AdminUserInfo;
import nanshen.utils.EncryptUtils;
import nanshen.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AccountCtrl extends BaseController {

	@Autowired
	private AdminUserInfoDao adminUserInfoDao;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView signinPage(ModelMap model) {
		model.addAttribute("AdminUserInfoList", adminUserInfoDao.getAll());
		return new ModelAndView("admin/signIn");
	}

	@RequestMapping(value = "/auth/signin", method = RequestMethod.POST)
	public ModelAndView signin(HttpServletRequest request, HttpServletResponse response, ModelMap model,
								@RequestParam(defaultValue = "b", required = true) String username,
								@RequestParam(defaultValue = "1", required = true) String password,
								@RequestParam(defaultValue = "123321", required = true) long id) throws IOException {
		AdminUserInfo adminUserInfo = adminUserInfoDao.get(username);
		if (adminUserInfo != null && EncryptUtils.isPasswordValid(adminUserInfo.getPassword(), password)) {
            request.getSession().setAttribute("current_user", username);
			response.sendRedirect("/admin/operation/look/look-list");
			return null;
		} else {
			model.addAttribute("orderId", id);
			model.addAttribute("error", "true");
			model.addAttribute("sessionId", request.getSession().getId());
			return new ModelAndView("admin/signIn");
		}
	}

    @RequestMapping(value = "/auth/signout", method = RequestMethod.GET)
    public ModelAndView signout(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect("/admin");
        return null;
    }
}