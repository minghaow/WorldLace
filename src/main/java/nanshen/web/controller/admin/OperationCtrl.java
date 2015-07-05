package nanshen.web.controller.admin;

import nanshen.dao.AdminUserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/operation")
public class OperationCtrl {

	@Autowired
	private AdminUserInfoDao adminUserInfoDao;

	@RequestMapping(value = "/look-list", method = RequestMethod.GET)
	public ModelAndView signIn(ModelMap model,
							   @RequestParam(defaultValue = "1", required = true) long page) {
		String imgUrl = "/images/slider/slider1.png";
		String title = "男神必备搭配";
		String subTitle = "作为一个长相倍儿帅的男神，假如再配上帅呆的衣服，那约炮就木问题啦。";
		model.addAttribute("AdminUserInfoList", adminUserInfoDao.getAll());
		model.addAttribute("page", page);
		model.addAttribute("imgUrl", imgUrl);
		model.addAttribute("title", title);
		model.addAttribute("subTitle", subTitle);
		return new ModelAndView("admin/lookList");
	}

}