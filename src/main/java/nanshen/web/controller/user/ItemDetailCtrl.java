package nanshen.web.controller.user;

import nanshen.data.PageType;
import nanshen.data.SkuItem;
import nanshen.service.AccountService;
import nanshen.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        SkuItem skuItem = skuService.getSkuInfo(itemId);
        if (skuItem != null) {
            model.addAttribute("skuInfo", skuItem);
        }
		prepareHeaderModel(model, PageType.ITEM_DETAIL);
		return new ModelAndView("user/itemDetail");
	}

	@RequestMapping(value = "/addToCart", method = RequestMethod.POST)
	public void addToCart(HttpServletResponse response, ModelMap model,
						  @RequestParam(defaultValue = "1", required = true) int itemId)
			throws IOException {

		model.put("success", "true");
		prepareHeaderModel(model, PageType.ITEM_DETAIL);
		responseJson(response, model);
	}

}