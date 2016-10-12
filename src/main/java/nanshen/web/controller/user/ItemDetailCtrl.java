package nanshen.web.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/item")
public class ItemDetailCtrl extends BaseCtrl {

//	@Autowired
//	private SkuService skuService;
//
//	@Autowired
//	private CartService cartService;
//
//	@RequestMapping(value = "", method = RequestMethod.GET)
//	public ModelAndView itemDetail(ModelMap model, @RequestParam(defaultValue = "1", required = true) int itemId) {
//		UserInfo userInfo = getLoginedUser();
//        SkuItem skuItem = skuService.getSkuItemInfo(itemId);
//        if (skuItem != null) {
//            model.addAttribute("skuItem", skuItem);
//            model.addAttribute("isLogin", userInfo != null);
//        }
//		prepareHeaderModel(model, PageType.ITEM_DETAIL);
//		return new ModelAndView("user/item-detail");
//	}
//
//	@RequestMapping(value = "/addToCart", method = RequestMethod.POST)
//	public void addToCart(HttpServletResponse response, ModelMap model,
//						  @RequestParam(defaultValue = "1", required = true) int skuId,
//						  @RequestParam(defaultValue = "1", required = true) int count)
//			throws IOException {
//		UserInfo userInfo = getLoginedUser();
//		ExecResult<Long> execResult = ExecResult.fail("请登陆后再加入购物车，谢谢！");
//		if (userInfo != null) {
//			execResult= cartService.addSku(userInfo.getId(), skuId, count);
//		}
//		model.put("success", execResult.isSucc());
//		model.put("message", execResult.getMsg());
//		model.put("cnt", execResult.getValue());
//		prepareHeaderModel(model, PageType.ITEM_DETAIL);
//		responseJson(response, model);
//	}

}