package nanshen.web.controller.user;

import nanshen.data.AdminUserInfo;
import nanshen.data.LookInfo;
import nanshen.data.LookTag;
import nanshen.data.PageType;
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
@RequestMapping("/look")
public class LookDetailCtrl extends BaseCtrl {

	@Autowired
	private AccountService accountService;

	@Autowired
	private LookService lookService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView lookDetail(ModelMap model,
								   @RequestParam(defaultValue = "1", required = true) int lookId) {
		LookInfo lookInfo = lookService.get(lookId);
		if (lookInfo != null) {
			AdminUserInfo uploader = accountService.getAdminUserInfoByUserId(lookInfo.getUploadUserId());
            List<LookTag> lookTagList = lookService.getAllTag();
            prepareLookTagIdMap(model, lookInfo);
			model.addAttribute("lookInfo", lookInfo);
			model.addAttribute("lookTagList", lookTagList);
			model.addAttribute("uploader", uploader);
		}
		model.addAttribute("success", lookInfo != null);
		prepareHeader(model, PageType.LOOK_DETAIL);
		prepareHelloMsg(model);
		return new ModelAndView("user/lookDetail");
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