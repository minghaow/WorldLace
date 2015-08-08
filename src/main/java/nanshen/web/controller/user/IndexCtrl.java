package nanshen.web.controller.user;

import nanshen.constant.SystemConstants;
import nanshen.dao.AdminUserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;

@Controller
@RequestMapping("/")
public class IndexCtrl {

	@Autowired
	private AdminUserInfoDao adminUserInfoDao;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView printWelcomeTest(ModelMap model) {

		prepareHelloMsg(model);
		return new ModelAndView("user/list");
	}

	private void prepareHelloMsg(ModelMap model) {
		String helloMsg = "";
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		if (hour >= 0 && hour < 12) {
			helloMsg = SystemConstants.HELLO_MSG_MORNING;
		} else if (hour >= 12 && hour < 18) {
			helloMsg = SystemConstants.HELLO_MSG_AFTERNOON;
		} else {
			helloMsg = SystemConstants.HELLO_MSG_EVENING;
		}
		model.addAttribute("helloMsg", helloMsg);
	}

}