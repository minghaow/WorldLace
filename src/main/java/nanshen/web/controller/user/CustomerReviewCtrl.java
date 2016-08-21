package nanshen.web.controller.user;

import nanshen.data.CustomerReview.CustomerReview;
import nanshen.data.CustomerReview.CustomerReviewSku;
import nanshen.data.Goods;
import nanshen.data.Order.Order;
import nanshen.data.Sku.SkuDetail;
import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.ExecResult;
import nanshen.data.SystemUtil.PageType;
import nanshen.data.User.UserInfo;
import nanshen.service.CustomerReviewService;
import nanshen.service.OrderService;
import nanshen.service.SkuService;
import nanshen.service.UserAddressService;
import nanshen.utils.CollectionExtractor;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

@Controller
@RequestMapping("/review")
public class CustomerReviewCtrl extends BaseCtrl {

	@Autowired
	private SkuService skuService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private CustomerReviewService customerReviewService;

	@Autowired
	private UserAddressService userAddressService;

	private CollectionExtractor<Long, CustomerReviewSku> skuIdExtractor = new CollectionExtractor<Long, CustomerReviewSku>() {
		@Override
		protected Long convert(CustomerReviewSku customerReviewSku) {
			return customerReviewSku.getSkuId();
		}
	};

	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public ModelAndView orderList(HttpServletResponse response, ModelMap model,
								  @RequestParam(defaultValue = "0", required = true) long orderId) {
		UserInfo userInfo = getLoginedUser();
		if (userInfo != null) {
			orderService.finish(orderId);
			Order order = orderService.getByOrderId(orderId);
			model.put("order", order);
		}
		prepareHeaderModel(model, PageType.CUSTOMER_REVIEW_LIST);
		return new ModelAndView("user/review");
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView reviewPage(ModelMap model, @RequestParam(defaultValue = "1", required = true) int reviewId) {
		UserInfo userInfo = getLoginedUser();
		model.put("URLDecoder", URLDecoder.class);
		model.put("StringEscapeUtils", StringEscapeUtils.class);
		CustomerReview customerReview = customerReviewService.getByReviewId(reviewId);
		if (customerReview != null) {
			List<SkuDetail> skuDetailList = skuService.getSkuDetailList(skuIdExtractor.extractList(customerReview.getCustomerReviewSkuList()));
			model.addAttribute("skuDetailList", skuDetailList);
			model.addAttribute("customerReview", customerReview);
			model.addAttribute("isLogin", userInfo != null);
		}
		prepareHeaderModel(model, PageType.ITEM_DETAIL);
		return new ModelAndView("user/review");
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView orderList(HttpServletResponse response, ModelMap model,
								  @RequestParam(defaultValue = "0", required = true) long orderId,
								  @RequestParam(defaultValue = "0", required = true) long itemId,
								  @RequestParam(defaultValue = "0", required = true) long skuId) {
		UserInfo userInfo = getLoginedUser();
		if (userInfo != null) {
			ExecResult<CustomerReview> customerReviewExecResult = customerReviewService.addNewCustomerReview(userInfo, itemId, skuId);
			Order order = orderService.getByOrderId(orderId);
			for (Goods goods : order.getGoodsList()) {
				if (goods.getSkuId() == skuId) {
					model.put("goods", goods);
				}
			}
			model.put("order", order);
			model.put("success", customerReviewExecResult.isSucc());
			model.put("customerReview", customerReviewExecResult.getValue());
			model.put("URLDecoder", URLDecoder.class);
		}
		prepareHeaderModel(model, PageType.CUSTOMER_REVIEW_CREATE);
		return new ModelAndView("user/review-create");
	}

	@RequestMapping(value = "/create/save", method = RequestMethod.POST)
	public void save(HttpServletResponse response, HttpServletRequest request, ModelMap model,
						 @RequestParam(defaultValue = "0", required = true) long reviewId,
						 @RequestParam(defaultValue = "", required = true) String content,
						 @RequestParam(defaultValue = "", required = true) String title) throws IOException {
		UserInfo userInfo = getLoginedUser();
		if (userInfo != null) {
			title = StringEscapeUtils.unescapeHtml(title);
			content = StringEscapeUtils.unescapeHtml(content);
			ExecInfo execInfo = customerReviewService.updateCustomerReview(reviewId, title, content);
			model.put("success", execInfo.isSucc());
		}
		responseJson(response, model);
	}

	@RequestMapping(value = "/create/confirm", method = RequestMethod.POST)
	public void publish(HttpServletResponse response, HttpServletRequest request, ModelMap model,
					 @RequestParam(defaultValue = "0", required = true) long reviewId) throws IOException {
		UserInfo userInfo = getLoginedUser();
		if (userInfo != null) {
			ExecInfo execInfo = customerReviewService.publishCustomerReview(reviewId);
			model.put("success", execInfo.isSucc());
		}
		responseJson(response, model);
	}
}