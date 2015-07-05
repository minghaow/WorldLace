package nanshen.web.controller.admin;

import nanshen.dao.AdminUserInfoDao;
import nanshen.data.AdminUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AccountCtrl {

	@Autowired
	private AdminUserInfoDao adminUserInfoDao;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView signIn(ModelMap model) {
		adminUserInfoDao.insert(new AdminUserInfo("abc"));
		model.addAttribute("AdminUserInfoList", adminUserInfoDao.getAll());
		return new ModelAndView("admin/signIn");
	}

}