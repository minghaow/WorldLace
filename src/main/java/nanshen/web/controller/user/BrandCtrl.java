package nanshen.web.controller.user;

import nanshen.data.SystemUtil.PageType;
import nanshen.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/brand")
public class BrandCtrl extends BaseCtrl {

	@Autowired
	private SkuService skuService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView aboutUsPage(ModelMap model, @RequestParam(defaultValue = "1", required = true) int page) {
		prepareHeaderModel(model, PageType.BRAND);
		return new ModelAndView("user/brand");
	}

}