package nanshen.web.controller.user;

import nanshen.data.PageInfo;
import nanshen.data.PageType;
import nanshen.data.PublicationStatus;
import nanshen.data.SkuInfo;
import nanshen.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
public class IndexCtrl extends BaseCtrl {

	@Autowired
	private SkuService skuService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView homePage(ModelMap model, @RequestParam(defaultValue = "1", required = true) int page) {
		List<SkuInfo> skuInfoList = skuService.getAll(PublicationStatus.ONLINE, new PageInfo(page));
		prepareHeader(model, PageType.ITEM_LIST);
		prepareHelloMsg(model);
		model.addAttribute("skuInfoList", skuInfoList);
		model.addAttribute("imageUrlPrefix", "http://image.zaitaoyuan.com");
//		model.addAttribute("imageUrlPrefix", "");
		return new ModelAndView("user/list");
	}

}