package nanshen.web.controller.user;

import nanshen.dao.AdminUserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexCtrl {

	@Autowired
	private AdminUserInfoDao adminUserInfoDao;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView printWelcomeTest(ModelMap model) {
		model.addAttribute("message", "Hello world!");
		model.addAttribute("goodsCartCount", 0);
		model.addAttribute("userName", "Minghao");
		return new ModelAndView("hello");
	}

}