package nanshen.web.controller.user;

import nanshen.data.AdminUserInfo;
import nanshen.data.LookInfo;
import nanshen.data.PageType;
import nanshen.data.StyleTag;
import nanshen.service.AccountService;
import nanshen.service.LookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/item")
public class ItemDetailCtrl extends BaseCtrl {

	@Autowired
	private AccountService accountService;

	@Autowired
	private LookService lookService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView itemDetail(ModelMap model, @RequestParam(defaultValue = "1", required = true) int itemId) {
//		LookInfo lookInfo = lookService.get(itemId);
//		if (lookInfo != null) {
//			prepareExistingLookInfo(model, lookInfo);
//		}
//		model.addAttribute("success", lookInfo != null);
		prepareHeader(model, PageType.LOOK_DETAIL);
		prepareHelloMsg(model);
		model.addAttribute("imageUrlPrefix", "http://static.lanzhujue.com/taoyuan/");
		return new ModelAndView("user/itemDetail");
	}

	private void prepareExistingLookInfo(ModelMap model, LookInfo lookInfo) {
		AdminUserInfo uploader = accountService.getAdminUserInfoByUserId(lookInfo.getUploadUserId());
		List<StyleTag> styleTagList = lookService.getAllTag();
		prepareLookTagIdMap(model, lookInfo);
		model.addAttribute("lookInfo", lookInfo);
		model.addAttribute("lookTagList", styleTagList);
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