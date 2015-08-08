package nanshen.web.controller.user;

import nanshen.constant.SystemConstants;
import nanshen.dao.AdminUserInfoDao;
import nanshen.data.LookInfo;
import nanshen.service.LookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

@Controller
@RequestMapping("/look")
public class LookDetailCtrl {

	@Autowired
	private AdminUserInfoDao adminUserInfoDao;

	@Autowired
	private LookService lookService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView lookDetail(HttpServletRequest request, ModelMap model,
								   @RequestParam(defaultValue = "1", required = true) int lookId) {
		LookInfo lookInfo = lookService.get(lookId);
		model.addAttribute("success", lookInfo != null);
		model.addAttribute("lookInfo", lookInfo);
		prepareHelloMsg(model);
		return new ModelAndView("user/lookDetail");
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