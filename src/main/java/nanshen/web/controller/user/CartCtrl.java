package nanshen.web.controller.user;

import nanshen.data.ExecResult;
import nanshen.data.PageType;
import nanshen.data.UserInfo;
import nanshen.service.CartService;
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
@RequestMapping("/cart")
public class CartCtrl extends BaseCtrl {

	@Autowired
	private SkuService skuService;

	@Autowired
	private CartService cartService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView itemDetail(ModelMap model, @RequestParam(defaultValue = "1", required = true) int itemId) {
		prepareHeaderModel(model, PageType.ITEM_DETAIL);
		return new ModelAndView("user/cart");
	}

	@RequestMapping(value = "/addToCart", method = RequestMethod.POST)
	public void addToCart(HttpServletResponse response, ModelMap model,
						  @RequestParam(defaultValue = "1", required = true) int skuId,
						  @RequestParam(defaultValue = "1", required = true) int count)
			throws IOException {
		UserInfo userInfo = getLoginedUser();
		ExecResult<Long> execResult = ExecResult.fail("请登陆后再加入购物车，谢谢！");
		if (userInfo != null) {
			execResult= cartService.addSku(userInfo.getId(), skuId, count);
		}
		model.put("success", execResult.isSucc());
		model.put("message", execResult.getMsg());
		model.put("cnt", execResult.getValue());
		prepareHeaderModel(model, PageType.ITEM_DETAIL);
		responseJson(response, model);
	}

}