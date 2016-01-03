package nanshen.web.controller.user;

import nanshen.data.PageType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/topic")
public class TopicCtrl extends BaseCtrl {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView topicList(ModelMap model, @RequestParam(defaultValue = "1", required = true) int lookId) {
		prepareHeader(model, PageType.TOPIC);
		prepareHelloMsg(model);
		model.addAttribute("imageUrlPrefix", "http://static.lanzhujue.com/taoyuan");
//		model.addAttribute("imageUrlPrefix", "");
		return new ModelAndView("user/topicList");
	}

}