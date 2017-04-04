package nanshen.web.controller.user;

import nanshen.data.Sku.SituationType;
import nanshen.data.Sku.SkuItem;
import nanshen.data.Sku.StoreType;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.SystemUtil.PageType;
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
@RequestMapping("/list")
public class ListCtrl extends BaseCtrl {

	@Autowired
	private SkuService skuService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView listPage(ModelMap model,
								 @RequestParam(defaultValue = "", required = false) StoreType type,
								 @RequestParam(defaultValue = "", required = false) SituationType situationType,
								 @RequestParam(defaultValue = "1", required = true) int page) {
		List<SkuItem> skuInfoList = skuService.getAll(type, situationType, new PageInfo(page));
		prepareHeaderModel(model, PageType.ITEM_LIST);
		model.addAttribute("skuInfoList", skuInfoList);
		model.addAttribute("storeType", type);
		model.addAttribute("situationType", situationType);
		return new ModelAndView("user/list");
	}

//	@RequestMapping(value = "/home", method = RequestMethod.GET)
//	public ModelAndView woYangHome(ModelMap model, @RequestParam(defaultValue = "1", required = true) int page) {
//		List<SkuItem> skuInfoList = skuService.getAll(PublicationStatus.ONLINE, new PageInfo(page));
//		prepareHeaderModel(model, PageType.WOYANG_HOME);
//		model.addAttribute("skuInfoList", skuInfoList);
//		return new ModelAndView("woyang/woyang-home");
//	}
//
//	@RequestMapping(value = "/search", method = RequestMethod.GET)
//	public ModelAndView woYangSearch(ModelMap model, @RequestParam(defaultValue = "餐具", required = true) String s) {
//		List<SkuItem> skuInfoList = skuService.getAll(PublicationStatus.ONLINE, new PageInfo(0));
//		prepareHeaderModel(model, PageType.WOYANG_SEARCH);
//		model.addAttribute("skuInfoList", skuInfoList);
//		return new ModelAndView("woyang/woyang-search");
//	}

}