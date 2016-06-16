package nanshen.web.controller.admin;

import nanshen.constant.SystemConstants;
import nanshen.dao.AdminUserInfoDao;
import nanshen.dao.LookInfoDao;
import nanshen.dao.StyleTagDao;
import nanshen.data.AdminUserInfo;
import nanshen.service.AccountService;
import nanshen.service.CartService;
import nanshen.service.OrderService;
import nanshen.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/admin/special")
public class AdminSpecialCtrl extends BaseController {

	@Autowired
	private AdminUserInfoDao adminUserInfoDao;

    @Autowired
    private LookInfoDao lookInfoDao;

    @Autowired
    private StyleTagDao styleTagDao;

	@Autowired
	private AccountService accountService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

	@RequestMapping(value = "/clearOrderCache", method = RequestMethod.GET)
	public void clearOrderCache(HttpServletRequest request, HttpServletResponse response,
							   @RequestParam(defaultValue = "1", required = true) int key) throws IOException {
        AdminUserInfo adminUserInfo = getLoginedUser(request);
        response.setContentType("application/json;charset=" + SystemConstants.SYS_ENC);
        if (key == 2 && adminUserInfo != null) {
            orderService.clearOrderCache();
            response.getWriter().print("Success.");
        } else {
            response.getWriter().print("Failed. Wrong key value");
        }
	}

    @RequestMapping(value = "/clearCartCache", method = RequestMethod.GET)
    public void clearCartCache(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(defaultValue = "1", required = true) int key) throws IOException {
        AdminUserInfo adminUserInfo = getLoginedUser(request);
        response.setContentType("application/json;charset=" + SystemConstants.SYS_ENC);
        if (key == 2 && adminUserInfo != null) {
            cartService.clearCartCache();
            response.getWriter().print("Success.");
        } else {
            response.getWriter().print("Failed. Wrong key value");
        }
    }

    @RequestMapping(value = "/clearAdminUserInfoCache", method = RequestMethod.GET)
    public void clearAdminUserInfoCache(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(defaultValue = "1", required = true) int key) throws IOException {
        AdminUserInfo adminUserInfo = getLoginedUser(request);
        response.setContentType("application/json;charset=" + SystemConstants.SYS_ENC);
        if (key == 2 && adminUserInfo != null) {
            accountService.clearAdminUserInfoCache();
            response.getWriter().print("Success.");
        } else {
            response.getWriter().print("Failed. Wrong key value");
        }
    }

}