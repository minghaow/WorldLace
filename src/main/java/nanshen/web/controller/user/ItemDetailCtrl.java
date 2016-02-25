package nanshen.web.controller.user;

import nanshen.data.AdminUserInfo;
import nanshen.data.LookInfo;
import nanshen.data.PageType;
import nanshen.data.SkuInfo;
import nanshen.service.AccountService;
import nanshen.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/item")
//@Secured({AccessAuthority.AUTHORITY_ADMIN})
public class ItemDetailCtrl extends BaseCtrl {

	@Autowired
	private AccountService accountService;

	@Autowired
	private SkuService skuService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView itemDetail(ModelMap model, @RequestParam(defaultValue = "1", required = true) int itemId) {
        SkuInfo skuInfo = skuService.getSkuInfo(itemId);
        if (skuInfo != null) {
            model.addAttribute("skuInfo", skuInfo);
        }
		prepareHeaderModel(model, PageType.ITEM_DETAIL);
		return new ModelAndView("user/itemDetail");
	}

	private void prepareExistingLookInfo(ModelMap model, LookInfo lookInfo) {
		AdminUserInfo uploader = accountService.getAdminUserInfoByUserId(lookInfo.getUploadUserId());
		prepareLookTagIdMap(model, lookInfo);
		model.addAttribute("lookInfo", lookInfo);
		model.addAttribute("uploader", uploader);
	}

	private void prepareLookTagIdMap(ModelMap model, LookInfo lookInfo) {
		Map<String, Boolean> tagIdMap = new HashMap<String, Boolean>();
		String[] tagIdList = lookInfo.getTagIdList();
		for (String tag : tagIdList) {
			tagIdMap.put(tag, true);
		}
		model.addAttribute("tagIdMap", tagIdMap);
	}

}