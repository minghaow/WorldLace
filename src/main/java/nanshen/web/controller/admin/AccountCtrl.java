package nanshen.web.controller.admin;

import nanshen.dao.AdminUserInfoDao;
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
	public ModelAndView orderDetailJson(HttpServletRequest request, HttpServletResponse response, ModelMap model,
								@RequestParam(defaultValue = "b", required = true) String username,
								@RequestParam(defaultValue = "123321", required = true) long id) throws IOException {
//		model.put("sessionId", request.getSession().getId());
//		responseJson(response, model);
		boolean success = true;
		if (success && username.equals("b")) {
			response.sendRedirect("/admin/operation/look-list");
			return null;
		} else {
			model.addAttribute("orderId", id);
			model.addAttribute("error", "true");
			model.addAttribute("sessionId", request.getSession().getId());
			return new ModelAndView("admin/signIn");
		}
	}
}