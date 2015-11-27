package nanshen.web.controller.admin;

import nanshen.dao.AdminUserInfoDao;
import nanshen.dao.LookInfoDao;
import nanshen.dao.StyleTagDao;
import nanshen.data.*;
import nanshen.service.AccountService;
import nanshen.service.SkuService;
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
@RequestMapping("/admin/operation/sku")
public class SkuCtrl extends BaseController {

	@Autowired
	private AdminUserInfoDao adminUserInfoDao;

    @Autowired
    private LookInfoDao lookInfoDao;

    @Autowired
    private StyleTagDao styleTagDao;

	@Autowired
	private OssFormalApi ossFormalApi;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SkuService skuService;

	@RequestMapping(value = "/sku-list", method = RequestMethod.GET)
	public ModelAndView lookList(HttpServletRequest request, HttpServletResponse response, ModelMap model,
							   @RequestParam(defaultValue = "1", required = true) int page,
                                 @RequestParam(defaultValue = "ONLINE", required = true) PublicationStatus status) {
        prepareLoginUserInfo(request, model);
        prepareSkuCntInfo(model);
        List<SkuInfo> skuInfoList = skuService.getAll(status, new PageInfo(page));
        Map<Long, AdminUserInfo> idAndAdminUserInfoMap = accountService.getAdminUserInfoBySkuInfoList(skuInfoList);
        model.addAttribute("idAndAdminUserInfoMap", idAndAdminUserInfoMap);
		model.addAttribute("skuInfoList", skuInfoList);
		model.addAttribute("status", status);
		model.addAttribute("page", page);
        model.addAttribute("pageType", "sku-list-page");
		return new ModelAndView("admin/skuList");
	}

    private void prepareSkuCntInfo(ModelMap model) {
        long onlineCnt = skuService.getCnt(PublicationStatus.ONLINE);
        long offlineCnt = skuService.getCnt(PublicationStatus.OFFLINE);
        long onlineNewCnt = skuService.getThisWeekCnt(PublicationStatus.ONLINE);
        long offlineNewCnt = skuService.getThisWeekCnt(PublicationStatus.OFFLINE);
        model.addAttribute("onlineCnt", onlineCnt);
        model.addAttribute("offlineCnt", offlineCnt);
        model.addAttribute("onlineNewCnt", onlineNewCnt);
        model.addAttribute("offlineNewCnt", offlineNewCnt);
    }

	@RequestMapping(value = "/sku-upload", method = RequestMethod.GET)
	public ModelAndView lookUpload(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                                   @RequestParam(defaultValue = "0", required = true) long skuId){
        prepareLoginUserInfo(request, model);
        List<StyleTag> styleTagList = styleTagDao.getAll();
        if (skuId != 0) {
            prepareExistedSkuInfo(model, skuId);
        }
		String sessionId = request.getSession().getId();
        model.addAttribute("skuDetailTypeList", SkuDetailType.values());
        model.addAttribute("lookId", 0);
        model.addAttribute("lookTagList", styleTagList);
        model.addAttribute("sessionId", sessionId);
        model.addAttribute("pageType", "sku-upload-page");
		return new ModelAndView("admin/skuUpload");
	}

    private void prepareExistedSkuInfo(ModelMap model, @RequestParam(defaultValue = "0", required = true) long skuId) {
        SkuInfo skuInfo = skuService.get(skuId);
        model.addAttribute("skuInfo", skuInfo);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public void upload(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                               @RequestParam(defaultValue = "0", required = true) long skuId,
                               @RequestParam(defaultValue = "", required = true) String title,
                               @RequestParam(defaultValue = "", required = true) String subTitle,
                               @RequestParam(defaultValue = "", required = true) String url,
                               @RequestParam(defaultValue = "", required = true) SkuDetailType category,
                               @RequestParam(defaultValue = "", required = true) String desc) throws IOException {
        prepareLoginUserInfo(request, model);
        AdminUserInfo adminUserInfo = getLoginedUser(request);
        ExecInfo inputCheck = uploadInputCheck(title, subTitle, url, category, desc);
        if (inputCheck.isSucc()) {
            inputCheck = skuService.update(skuId, title, subTitle, url, category, desc, adminUserInfo.getId());
        }
        model.addAttribute("success", inputCheck.isSucc());
        model.addAttribute("reason", inputCheck.getMsg());
        responseJson(response, model);
    }

    private ExecInfo uploadInputCheck(@RequestParam(defaultValue = "", required = true) String title, @RequestParam(defaultValue = "", required = true) String subTitle, @RequestParam(defaultValue = "", required = true) String url, @RequestParam(defaultValue = "", required = true) SkuDetailType category, @RequestParam(defaultValue = "", required = true) String desc) {
        String failReason = "";
        if (title == null || title.trim().equals("")) {
            failReason += "标题不能为空 ";
        } else if (title.trim().length() > 20) {
            failReason += "标题过长 ";
        }
        if (subTitle != null && subTitle.trim().length() > 30) {
            failReason += "子标题过长 ";
        }
        if (desc == null || desc.trim().equals("")) {
            failReason += "描述不能为空 ";
        } else if (desc.trim().length() > 200) {
            failReason += "描述过长 ";
        }
        if (url ==null || url.trim().equals("")) {
            failReason += "外链url不能为空 ";
        }
        if (category == null || category == SkuDetailType.UNKNOWN) {
            failReason += "类别不能为空或不能识别 ";
        }
        if (failReason.equals("")) {
            return ExecInfo.succ();
        }
        return ExecInfo.fail(failReason);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public void delete(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       @RequestParam(defaultValue = "0", required = true) long skuId) throws IOException {
        AdminUserInfo adminUserInfo = getLoginedUser(request);
        ExecInfo execInfo = skuService.remove(skuId, adminUserInfo);
        model.addAttribute("success", execInfo.isSucc());
        model.addAttribute("message", execInfo.getMsg());
        responseJson(response, model);
    }

    @RequestMapping(value = "/online", method = RequestMethod.GET)
    public void online(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       @RequestParam(defaultValue = "0", required = true) long skuId) throws IOException {
        prepareLoginUserInfo(request, model);
        boolean isSucc = skuService.changeStatus(skuId, PublicationStatus.ONLINE);
        model.addAttribute("success", isSucc);
        responseJson(response, model);
    }

    @RequestMapping(value = "/offline", method = RequestMethod.GET)
    public void offline(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                        @RequestParam(defaultValue = "0", required = true) long skuId) throws IOException {
        prepareLoginUserInfo(request, model);
        boolean isSucc = skuService.changeStatus(skuId, PublicationStatus.OFFLINE);
        model.addAttribute("success", isSucc);
        responseJson(response, model);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public void upload(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       @RequestParam(defaultValue = "0", required = true) long skuId) throws IOException {
        prepareLoginUserInfo(request, model);
        AdminUserInfo adminUserInfo = getLoginedUser(request);
        SkuInfo skuInfo = skuService.get(skuId);
        model.addAttribute("success", skuInfo != null);
        model.addAttribute("skuInfo", skuInfo);
        responseJson(response, model);
    }

    /**
     * 上传单品配图功能
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public void uploadImage(MultipartHttpServletRequest request, HttpServletResponse response, ModelMap model,
                            @RequestParam(defaultValue = "0", required = true) long skuId)
            throws IOException {
        AdminUserInfo adminUserInfo = prepareLoginUserInfo(request, model);
        MultipartFile file = request.getFile("Filedata");

        ExecResult<SkuInfo> execResult = skuService.uploadImage(skuId, adminUserInfo.getId(), file);
        model.addAttribute("success", execResult.isSucc());
        model.addAttribute("message", execResult.getMsg());
        model.addAttribute("id", execResult.getValue().getImgCount() - 1);
        model.addAttribute("skuId", execResult.getValue().getId());
        model.addAttribute("url", ossFormalApi.getSkuImgUrl(execResult.getValue().getId(), execResult.getValue().getImgCount() - 1));
        responseJson(response, model);
    }

}