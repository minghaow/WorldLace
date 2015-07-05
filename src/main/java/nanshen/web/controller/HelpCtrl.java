package nanshen.web.controller;

import nanshen.dao.AdminUserInfoDao;
import nanshen.data.AdminUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HelpCtrl {

	@Autowired
	private AdminUserInfoDao adminUserInfoDao;

	/**
	 * 默认页面
	 * <br/>
	 * 登陆后根据权限不同跳转到不同的页面
	 *
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/help", method = RequestMethod.GET)
	public ModelAndView printWelcomeTest(ModelMap model) {
		adminUserInfoDao.insert(new AdminUserInfo("abc"));
		model.addAttribute("AdminUserInfoList", adminUserInfoDao.getAll());
		model.addAttribute("message", "Hello world!");
		model.addAttribute("goodsCartCount", 0);
		model.addAttribute("userName", "Minghao");
		return new ModelAndView("hello");
	}

}