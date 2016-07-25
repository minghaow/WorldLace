package nanshen.web.controller.user;

import nanshen.data.CustomerReview.CustomerReview;
import nanshen.data.Order.Order;
import nanshen.data.SystemUtil.ExecResult;
import nanshen.data.SystemUtil.PageType;
import nanshen.data.User.UserInfo;
import nanshen.service.CartService;
import nanshen.service.CustomerReviewService;
import nanshen.service.OrderService;
import nanshen.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/order")
public class CustomerReviewCtrl extends BaseCtrl {

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private CustomerReviewService customerReviewService;

	@Autowired
	private UserAddressService userAddressService;

	@RequestMapping(value = "/review", method = RequestMethod.GET)
	public ModelAndView orderList(HttpServletResponse response, ModelMap model,
								  @RequestParam(defaultValue = "0", required = true) long orderId) {
		UserInfo userInfo = getLoginedUser();
		if (userInfo != null) {
			orderService.finish(orderId);
			Order order = orderService.getByOrderId(orderId);
			model.put("order", order);
		}
		prepareHeaderModel(model, PageType.CUSTOMER_REVIEW);
		return new ModelAndView("user/review");
	}

	@RequestMapping(value = "/review/create", method = RequestMethod.GET)
	public ModelAndView orderList(HttpServletResponse response, ModelMap model,
								  @RequestParam(defaultValue = "0", required = true) long orderId,
								  @RequestParam(defaultValue = "0", required = true) long itemId,
								  @RequestParam(defaultValue = "0", required = true) long skuId) {
		UserInfo userInfo = getLoginedUser();
		if (userInfo != null) {
			ExecResult<CustomerReview> customerReviewExecResult = customerReviewService.addNewCustomerReview(userInfo, itemId, skuId);
			Order order = orderService.getByOrderId(orderId);
			model.put("order", order);
			model.put("success", customerReviewExecResult.isSucc());
			model.put("customerReview", customerReviewExecResult);
		}
		prepareHeaderModel(model, PageType.CUSTOMER_REVIEW);
		return new ModelAndView("user/review-create");
	}

}