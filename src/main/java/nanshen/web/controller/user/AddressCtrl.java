package nanshen.web.controller.user;

import nanshen.data.ExecInfo;
import nanshen.data.Region;
import nanshen.data.UserAddress;
import nanshen.data.UserInfo;
import nanshen.service.CartService;
import nanshen.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/address")
public class AddressCtrl extends BaseCtrl {

	@Autowired
	private UserAddressService userAddressService;

	@Autowired
	private CartService cartService;

	/**
	 * 获取指定地区的下一层地区信息
	 *
	 * @param response
	 * @param regionId
	 * @throws IOException
	 */
	@RequestMapping("/nextLevelRegion")
	public void childrenRegions(HttpServletResponse response, long regionId) throws IOException {
		Map<String, Object> json = new HashMap<String, Object>();
		List<Region> regions = userAddressService.getChildrenRegions(regionId);
		json.put("success", true);
		json.put("data", regions);
		responseJson(response, json);
	}

	@RequestMapping(value = "change-address", method = RequestMethod.POST)
	public void changeAddress(HttpServletResponse response,
							  long orderId, String name, String phone, int level1, int level2, int level3, String code,
							  String detail) throws IOException, ParseException {
		Map<String, Object> json = new HashMap<String, Object>();
//		if (!transportService.checkTransportStatus(orderId, TransportStatus.getNotSortingStatuses())) {
//			json.put("success", false);
//			json.put("message", "订单状态不正确，只能修改未分拣的订单！");
//			responseJson(response, json);
//			return;
//		}
		UserInfo userInfo = getLoginedUser();
		if (userInfo == null) {
			json.put("success", false);
			json.put("message", "未登录或登录状态过期，请重新登录！");
			responseJson(response, json);
			return;
		}
		UserAddress userAddress = new UserAddress();
		userAddress.setName(name);
		userAddress.setLevel1(level1);
		userAddress.setLevel2(level2);
		userAddress.setLevel3(level3);
		userAddress.setAddress(detail);
		userAddress.setCode(code);
		userAddress.setPhone(phone);
		ExecInfo result = userAddressService.changeAddress(orderId, userInfo.getId(), userAddress);
		json.put("success", result.isSucc());
		json.put("message", result.getMsg());
		responseJson(response, json);
	}
}