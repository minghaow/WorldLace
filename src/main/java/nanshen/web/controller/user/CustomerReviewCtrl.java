package nanshen.web.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/review")
public class CustomerReviewCtrl extends BaseCtrl {

//	@Autowired
//	private SkuService skuService;
//
//	@Autowired
//	private OrderService orderService;
//
//	@Autowired
//	private CustomerReviewService customerReviewService;
//
//	@Autowired
//	private UserAddressService userAddressService;
//
//	private CollectionExtractor<Long, CustomerReviewSku> skuIdExtractor = new CollectionExtractor<Long, CustomerReviewSku>() {
//		@Override
//		protected Long convert(CustomerReviewSku customerReviewSku) {
//			return customerReviewSku.getSkuId();
//		}
//	};
//
//	@RequestMapping(value = "/order", method = RequestMethod.GET)
//	public ModelAndView orderList(HttpServletResponse response, ModelMap model,
//								  @RequestParam(defaultValue = "0", required = true) long orderId) {
//		UserInfo userInfo = getLoginedUser();
//		if (userInfo != null) {
//			orderService.finish(orderId);
//			Order order = orderService.getByOrderId(orderId);
//			model.put("order", order);
//		}
//		prepareHeaderModel(model, PageType.CUSTOMER_REVIEW_LIST);
//		return new ModelAndView("user/review-order");
//	}
//
//	@RequestMapping(value = "/create", method = RequestMethod.GET)
//	public ModelAndView orderList(HttpServletResponse response, ModelMap model,
//								  @RequestParam(defaultValue = "0", required = true) long orderId,
//								  @RequestParam(defaultValue = "0", required = true) long itemId,
//								  @RequestParam(defaultValue = "0", required = true) long skuId) {
//		UserInfo userInfo = getLoginedUser();
//		if (userInfo != null) {
////			ExecResult<CustomerReview> customerReviewExecResult = customerReviewService.getByOrderIdAndItemId(userInfo, orderId, itemId, skuId);
//			ExecResult<CustomerReview> customerReviewExecResult = customerReviewService.addNewCustomerReview(userInfo, orderId, itemId, skuId);
//			Order order = orderService.getByOrderId(orderId);
//			for (Goods goods : order.getGoodsList()) {
//				if (goods.getSkuId() == skuId) {
//					model.put("goods", goods);
//				}
//			}
//			model.put("order", order);
//			model.put("success", customerReviewExecResult.isSucc());
//			model.put("customerReview", customerReviewExecResult.getValue());
//			model.put("URLDecoder", URLDecoder.class);
//		}
//		prepareHeaderModel(model, PageType.CUSTOMER_REVIEW_CREATE);
//		return new ModelAndView("user/review-create");
//	}
//
//	@RequestMapping(value = "", method = RequestMethod.GET)
//	public ModelAndView reviewPage(ModelMap model, @RequestParam(defaultValue = "1", required = true) int reviewId) {
//		UserInfo userInfo = getLoginedUser();
//		model.put("URLDecoder", URLDecoder.class);
//		model.put("StringEscapeUtils", StringEscapeUtils.class);
//		CustomerReview customerReview = customerReviewService.getByReviewId(reviewId);
//		if (customerReview != null) {
//			List<SkuDetail> skuDetailList = skuService.getSkuDetailList(skuIdExtractor.extractList(customerReview.getCustomerReviewSkuList()));
//			model.addAttribute("skuDetailList", skuDetailList);
//			model.addAttribute("customerReview", customerReview);
//			model.addAttribute("isLogin", userInfo != null);
//		}
//		prepareHeaderModel(model, PageType.ITEM_DETAIL);
//		return new ModelAndView("user/review");
//	}
//
//	@RequestMapping(value = "/create/save", method = RequestMethod.POST)
//	public void save(HttpServletResponse response, HttpServletRequest request, ModelMap model,
//						 @RequestParam(defaultValue = "0", required = true) long reviewId,
//						 @RequestParam(defaultValue = "", required = true) String content,
//						 @RequestParam(defaultValue = "", required = true) String title) throws IOException {
//		UserInfo userInfo = getLoginedUser();
//		if (userInfo != null) {
//			title = StringEscapeUtils.unescapeHtml(title);
//			content = StringEscapeUtils.unescapeHtml(content);
//			ExecInfo execInfo = customerReviewService.updateCustomerReview(reviewId, title, content);
//			model.put("success", execInfo.isSucc());
//		}
//		responseJson(response, model);
//	}
//
//	@RequestMapping(value = "/create/confirm", method = RequestMethod.POST)
//	public void publish(HttpServletResponse response, HttpServletRequest request, ModelMap model,
//					 @RequestParam(defaultValue = "0", required = true) long reviewId) throws IOException {
//		UserInfo userInfo = getLoginedUser();
//		if (userInfo != null) {
//			ExecInfo execInfo = customerReviewService.publishCustomerReview(reviewId);
//			model.put("success", execInfo.isSucc());
//		}
//		responseJson(response, model);
//	}
}