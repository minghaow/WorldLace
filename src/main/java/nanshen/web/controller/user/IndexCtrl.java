package nanshen.web.controller.user;

import nanshen.dao.AdminUserInfoDao;
import nanshen.data.LookInfo;
import nanshen.data.PageInfo;
import nanshen.data.PageType;
import nanshen.data.PublicationStatus;
import nanshen.service.LookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
//@Secured({AccessAuthority.AUTHORITY_ADMIN})
public class IndexCtrl extends BaseCtrl {

	@Autowired
	private AdminUserInfoDao adminUserInfoDao;

	@Autowired
	private LookService lookService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView printWelcomeTest(ModelMap model) {
		List<LookInfo> lookInfoList = lookService.getAll(PublicationStatus.ONLINE, new PageInfo(0));
		prepareHeader(model, PageType.ITEM_LIST);
		prepareHelloMsg(model);
		model.addAttribute("lookInfoList", lookInfoList);
		model.addAttribute("imageUrlPrefix", "http://static.lanzhujue.com/taoyuan");
//		model.addAttribute("imageUrlPrefix", "");
		return new ModelAndView("user/list");
	}

}