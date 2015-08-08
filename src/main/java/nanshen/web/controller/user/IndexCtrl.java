package nanshen.web.controller.user;

import nanshen.constant.SystemConstants;
import nanshen.dao.AdminUserInfoDao;
import nanshen.data.LookInfo;
import nanshen.data.PageInfo;
import nanshen.data.PublicationStatus;
import nanshen.service.LookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/")
public class IndexCtrl {

	@Autowired
	private AdminUserInfoDao adminUserInfoDao;

	@Autowired
	private LookService lookService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView printWelcomeTest(ModelMap model) {
		List<LookInfo> lookInfoList = lookService.getAll(PublicationStatus.ONLINE, new PageInfo(0));
		prepareHelloMsg(model);
		model.addAttribute("lookInfoList", lookInfoList);
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