package nanshen.web.controller.user;

import nanshen.data.PageType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class StaticPageCtrl extends BaseCtrl {

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public ModelAndView topicList(ModelMap model) {
		prepareHeaderModel(model, PageType.CONTACT);
		return new ModelAndView("user/contact");
	}

}