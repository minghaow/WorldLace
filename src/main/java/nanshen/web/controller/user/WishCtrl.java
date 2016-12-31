package nanshen.web.controller.user;

import nanshen.data.SystemUtil.ExecResult;
import nanshen.data.SystemUtil.PageType;
import nanshen.data.User.UserInfo;
import nanshen.service.WishService;
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
@RequestMapping("/wish")
public class WishCtrl extends BaseCtrl {

	@Autowired
	private WishService wishService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView wish(ModelMap model) {
		model.addAttribute("wish", getWishInfo());
		prepareHeaderModel(model, PageType.WISH);
		return new ModelAndView("user/wish");
	}

	@RequestMapping(value = "/addToWish", method = RequestMethod.POST)
	public void addToWish(HttpServletResponse response, ModelMap model,
						  @RequestParam(defaultValue = "1", required = true) long skuId)
			throws IOException {
		UserInfo userInfo = getLoginedUser();
		ExecResult<Long> execResult = ExecResult.fail("请登陆后再加入购物车，谢谢！");
		if (userInfo != null) {
			execResult= wishService.addSku(userInfo.getId(), skuId, 1);
		}
		model.put("success", execResult.isSucc());
		model.put("message", execResult.getMsg());
		model.put("cnt", execResult.getValue());
		prepareHeaderModel(model, PageType.CART);
		responseJson(response, model);
	}

}