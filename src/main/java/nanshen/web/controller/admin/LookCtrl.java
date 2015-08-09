package nanshen.web.controller.admin;

import nanshen.data.*;
import nanshen.service.AccountService;
import nanshen.service.LookService;
import nanshen.service.api.oss.OssFormalApi;
import nanshen.web.common.BaseController;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/operation/look")
public class LookCtrl extends BaseController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private LookService lookService;

    @Autowired
    private OssFormalApi ossFormalApi;

	@RequestMapping(value = "/look-list", method = RequestMethod.GET)
	public ModelAndView lookList(HttpServletRequest request, ModelMap model,
							   @RequestParam(defaultValue = "1", required = true) int page,
							   @RequestParam(defaultValue = "ONLINE", required = true) PublicationStatus status) {
        prepareLoginUserInfo(request, model);
        List<LookInfo> lookInfoList = lookService.getAll(status, new PageInfo(page));

        Map<Long, AdminUserInfo> idAndAdminUserInfoMap = accountService.getAdminUserInfoBy(lookInfoList);
        prepareLookCntInfo(model);
		model.addAttribute("lookInfoList", lookInfoList);
		model.addAttribute("idAndAdminUserInfoMap", idAndAdminUserInfoMap);
		model.addAttribute("status", status);
		model.addAttribute("page", page);
		return new ModelAndView("admin/lookList");
	}

    private void prepareLookCntInfo(ModelMap model) {
        long onlineCnt = lookService.getCnt(PublicationStatus.ONLINE);
        long offlineCnt = lookService.getCnt(PublicationStatus.OFFLINE);
        long onlineNewCnt = lookService.getThisWeekCnt(PublicationStatus.ONLINE);
        long offlineNewCnt = lookService.getThisWeekCnt(PublicationStatus.OFFLINE);
        model.addAttribute("onlineCnt", onlineCnt);
        model.addAttribute("offlineCnt", offlineCnt);
        model.addAttribute("onlineNewCnt", onlineNewCnt);
        model.addAttribute("offlineNewCnt", offlineNewCnt);
    }

    @RequestMapping(value = "/look-upload", method = RequestMethod.GET)
	public ModelAndView lookUpload(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                                   @RequestParam(defaultValue = "0", required = true) long lookId){
        String sessionId = request.getSession().getId();
        prepareLoginUserInfo(request, model);
        if (lookId != 0) {
            model.addAttribute("lookInfo", lookService.get(lookId));
        }
        List<LookTag> lookTagList = lookService.getAllTag();
        model.addAttribute("lookId", lookId);
        model.addAttribute("lookTagList", lookTagList);
        model.addAttribute("sessionId", sessionId);
		return new ModelAndView("admin/lookUpload");
	}

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public void upload(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                               @RequestParam(defaultValue = "0", required = true) long lookId,
                               @RequestParam(defaultValue = "", required = true) String title,
                               @RequestParam(defaultValue = "", required = true) String subTitle,
                               @RequestParam(defaultValue = "", required = true) String desc) throws IOException {
        prepareLoginUserInfo(request, model);
        AdminUserInfo adminUserInfo = getLoginedUser(request);
        boolean isSucc = lookService.update(lookId, title, subTitle, desc, PublicationStatus.OFFLINE, adminUserInfo.getId());
        model.addAttribute("success", isSucc);
        responseJson(response, model);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public void delete(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                            @RequestParam(defaultValue = "0", required = true) long lookId) throws IOException {
        AdminUserInfo adminUserInfo = getLoginedUser(request);
        ExecInfo execInfo = lookService.removeLook(lookId, adminUserInfo);
        model.addAttribute("success", execInfo.isSucc());
        model.addAttribute("message", execInfo.getMsg());
        responseJson(response, model);
    }

    @RequestMapping(value = "/online", method = RequestMethod.GET)
    public void online(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       @RequestParam(defaultValue = "0", required = true) long lookId) throws IOException {
        prepareLoginUserInfo(request, model);
        boolean isSucc = lookService.changeStatus(lookId, PublicationStatus.ONLINE);
        model.addAttribute("success", isSucc);
        responseJson(response, model);
    }

    @RequestMapping(value = "/offline", method = RequestMethod.GET)
    public void offline(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       @RequestParam(defaultValue = "0", required = true) long lookId) throws IOException {
        prepareLoginUserInfo(request, model);
        boolean isSucc = lookService.changeStatus(lookId, PublicationStatus.OFFLINE);
        model.addAttribute("success", isSucc);
        responseJson(response, model);
    }

    /**
     * 上传搭配配图功能
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public void uploadImage(MultipartHttpServletRequest request, HttpServletResponse response, ModelMap model,
                            @RequestParam(defaultValue = "0", required = true) long lookId)
            throws IOException {
        AdminUserInfo adminUserInfo = prepareLoginUserInfo(request, model);
        MultipartFile file = request.getFile("Filedata");

        ExecResult<LookInfo> execResult = lookService.uploadImage(lookId, adminUserInfo.getId(), file);
        model.addAttribute("success", execResult.isSucc());
        model.addAttribute("message", execResult.getMsg());
        model.addAttribute("id", execResult.getValue().getImgCount() - 1);
        model.addAttribute("lookId", execResult.getValue().getId());
        model.addAttribute("url", ossFormalApi.getLookImgUrl(execResult.getValue().getId(), execResult.getValue().getImgCount() - 1));
        responseJson(response, model);
    }


}