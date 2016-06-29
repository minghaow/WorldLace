package nanshen.web.controller.user;

import nanshen.data.Order.Order;
import nanshen.data.Sku.SkuCommentImg;
import nanshen.data.SystemUtil.ExecResult;
import nanshen.data.SystemUtil.PageType;
import nanshen.data.User.UserAddress;
import nanshen.data.User.UserInfo;
import nanshen.service.CartService;
import nanshen.service.OrderService;
import nanshen.service.UserAddressService;
import nanshen.service.api.alipay.util.AlipayNotify;
import nanshen.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderCtrl extends BaseCtrl {

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserAddressService userAddressService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView orderList(HttpServletResponse response, ModelMap model) {
		UserInfo userInfo = getLoginedUser();
		if (userInfo != null) {
			List<Order> orderList = orderService.getByUserId(getLoginedUser().getId());
			model.put("orderList", orderList);
		}
		prepareHeaderModel(model, PageType.ORDER_LIST);
		return new ModelAndView("user/my-order");
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView orderDetail(HttpServletResponse response, ModelMap model,
									@RequestParam(defaultValue = "0", required = true) long orderId) {
		UserInfo userInfo = getLoginedUser();
		if (userInfo != null) {
			Order order = orderService.getByOrderId(orderId);
			model.put("order", order);
		}
		prepareHeaderModel(model, PageType.ORDER_LIST);
		return new ModelAndView("user/order");
	}

	@RequestMapping(value = "/process", method = RequestMethod.GET)
	public ModelAndView orderProcess(HttpServletResponse response, ModelMap model,
								 @RequestParam(defaultValue = "0", required = true) long orderId) {
		UserInfo userInfo = getLoginedUser();
		if (userInfo != null) {
			Order order = orderService.getByOrderId(orderId);
			String paymentHtml = orderService.getPaymentHtml(order);
			model.put("paymentHtml", paymentHtml);
			List<UserAddress> userAddressList = accountService.getUserAddressListByUserId(userInfo.getId());
			if (userAddressList != null && userAddressList.size() > 0) {
				model.put("userAddress", userAddressList.get(0));
			}
			model.put("order", order);
		}
		prepareHeaderModel(model, PageType.ORDER_LIST);
		return new ModelAndView("user/order-process");
	}

	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	public void orderPay(HttpServletResponse response, ModelMap model,
								 @RequestParam(defaultValue = "0", required = true) long orderId,
								 @RequestParam(defaultValue = "0", required = true) String name,
								 @RequestParam(defaultValue = "0", required = true) String phone,
								 @RequestParam(defaultValue = "0", required = true) long level1,
								 @RequestParam(defaultValue = "0", required = true) long level2,
								 @RequestParam(defaultValue = "0", required = true) long level3,
									@RequestParam(defaultValue = "0", required = true) String detail) throws IOException  {
		UserInfo userInfo = getLoginedUser();
		if (userInfo != null) {
			orderService.updateOrderToPaying(orderId, new UserAddress(detail, (int)level1, (int)level2, (int)level3, name, phone,
					userInfo.getId()));
			model.put("success", true);
		}
		responseJson(response, model);
	}

	@RequestMapping(value = "/alipay/return", method = RequestMethod.GET)
	public ModelAndView alipayReturn(HttpServletResponse response, HttpServletRequest request, ModelMap model) {
		UserInfo userInfo = getLoginedUser();
		if (userInfo != null) {
			Map<String,String> params = getRequestParams(request);
			String out_trade_no = params.get("out_trade_no");
			String trade_no = params.get("trade_no");
			String trade_status = params.get("trade_status");
			if(AlipayNotify.verify(params)){//验证成功
				if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
					orderService.updateOrderToPayed(out_trade_no, trade_no, params);
					Order order = orderService.getByShowOrderId(out_trade_no);
					model.put("order", order);
					model.put("success", true);
				} else {
					model.put("success", false);
				}
			} else {
				model.put("success", false);
			}
		}
		prepareHeaderModel(model, PageType.ORDER_LIST);
		return new ModelAndView("user/pay-result");
	}

	@RequestMapping(value = "/alipay/notify", method = RequestMethod.POST)
	public void alipayNotify(HttpServletResponse response, HttpServletRequest request, ModelMap model)
			throws IOException {
		Map<String,String> params = getRequestParams(request);
		String out_trade_no = params.get("out_trade_no");
		String trade_no = params.get("trade_no");
		String trade_status = params.get("trade_status");
		if(AlipayNotify.verify(params)){//验证成功
			if(trade_status.equals("TRADE_FINISHED")){
				LogUtils.info("TRADE_FINISH" + request.getRequestURI());
			} else if (trade_status.equals("TRADE_SUCCESS")){
				orderService.updateOrderToPayed(out_trade_no, trade_no, params);
			}
			response.getWriter().print("success");	//请不要修改或删除
		}else{
			response.getWriter().print("fail");
		}
	}

	/**
	 * 上传评论配图功能
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadCommentImage", method = RequestMethod.POST)
	public void uploadCommentImage(MultipartHttpServletRequest request, HttpServletResponse response, ModelMap model,
							@RequestParam(defaultValue = "0", required = true) long skuId)
			throws IOException {
		UserInfo userInfo = getLoginedUser();
		if (userInfo != null) {
			MultipartFile file = request.getFile("Filedata");
			ExecResult<SkuCommentImg> execResult = orderService.uploadCommentImg(userInfo.getId(), skuId, file);
			model.addAttribute("success", execResult.isSucc());
			model.addAttribute("message", execResult.getMsg());
			model.addAttribute("id", execResult.getValue().getId() - 1);
			model.addAttribute("skuId", skuId);
			model.addAttribute("url", execResult.getValue().getImgUrl());
		} else {
			model.addAttribute("success", false);
			model.addAttribute("message", "请登陆后再上传图片，谢谢");
		}
		responseJson(response, model);
	}

	public Map<String,String> getRequestParams(HttpServletRequest request) {
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		return params;
	}

}