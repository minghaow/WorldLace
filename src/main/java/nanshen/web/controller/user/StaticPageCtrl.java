package nanshen.web.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class StaticPageCtrl extends BaseCtrl {

//	@Autowired
//	private ContactMsgDao contactMsgDao;
//
//	@RequestMapping(value = "/contact", method = RequestMethod.GET)
//	public ModelAndView topicList(ModelMap model) {
//		prepareHeaderModel(model, PageType.CONTACT);
//		return new ModelAndView("user/contact");
//	}
//
//	@RequestMapping(value = "/msg", method = RequestMethod.POST)
//	public void contactUs(HttpServletResponse response, ModelMap model,
//						  @RequestParam(defaultValue = "", required = true) String name,
//						  @RequestParam(defaultValue = "", required = true) String email,
//						  @RequestParam(defaultValue = "", required = true) String content)
//			throws IOException {
//		ContactMsg contactMsg = contactMsgDao.insert(new ContactMsg(content, new Date(), email, name, 0));
//		model.put("success", contactMsg != null);
//		model.put("message", "抱歉，系统错误。请重试。");
//		prepareHeaderModel(model, PageType.CONTACT);
//		responseJson(response, model);
//	}

}