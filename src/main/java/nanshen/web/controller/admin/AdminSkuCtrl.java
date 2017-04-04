package nanshen.web.controller.admin;

import nanshen.dao.AdminUserInfoDao;
import nanshen.dao.DiscountDao;
import nanshen.dao.StyleTagDao;
import nanshen.data.AdminUserInfo;
import nanshen.data.PublicationStatus;
import nanshen.data.Sku.SkuDetailType;
import nanshen.data.Sku.SkuItem;
import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.service.SkuService;
import nanshen.web.common.BaseController;
import org.apache.commons.lang.StringEscapeUtils;
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

@Controller
@RequestMapping("/admin/sku")
public class AdminSkuCtrl extends BaseController {

	@Autowired
	private AdminUserInfoDao adminUserInfoDao;

    @Autowired
    private DiscountDao discountDao;

    @Autowired
    private StyleTagDao styleTagDao;

    @Autowired
    private SkuService skuService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView sku(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                                @RequestParam(defaultValue = "0", required = true) long id){
        prepareLoginUserInfo(request, model);
        String sessionId = request.getSession().getId();
        SkuItem skuItem = skuService.getOrCreateSkuItemInfo(id);
        model.addAttribute("skuItem", skuItem);
        model.addAttribute("sessionId", sessionId);
        model.addAttribute("pageType", "sku");
        return new ModelAndView("admin/sku");
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView skuList(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                                @RequestParam(defaultValue = "1", required = true) int page){
        prepareLoginUserInfo(request, model);
        String sessionId = request.getSession().getId();
        List<SkuItem> skuItemList = skuService.getAll(new PageInfo(page));
        model.addAttribute("skuItemList", skuItemList);
        model.addAttribute("sessionId", sessionId);
        model.addAttribute("page", page);
        model.addAttribute("pageType", "sku-list");
        return new ModelAndView("admin/skuList");
    }

    @RequestMapping(value = "/list/delete", method = RequestMethod.GET)
    public void itemUpload(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                           @RequestParam(defaultValue = "0", required = true) long id) throws IOException {
        prepareLoginUserInfo(request, model);
        AdminUserInfo adminUserInfo = getLoginedUser(request);
        model.addAttribute("success", skuService.remove(id));
        responseJson(response, model);
    }

    @RequestMapping(value = "/item-upload/upload", method = RequestMethod.GET)
    public void itemUpload(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                           @RequestParam(defaultValue = "0", required = true) long id,
                           @RequestParam(defaultValue = "0", required = true) String title,
                           @RequestParam(defaultValue = "0", required = true) String subTitle,
                           @RequestParam(defaultValue = "0", required = true) long price,
                           @RequestParam(defaultValue = "0", required = true) long hasGif,
                           @RequestParam(defaultValue = "0", required = true) String description1,
                           @RequestParam(defaultValue = "0", required = true) String points,
                           @RequestParam(defaultValue = "0", required = true) String infos,
                           @RequestParam(defaultValue = "0", required = true) String shipSpeed,
                           @RequestParam(defaultValue = "0", required = true) String notice,
                           @RequestParam(defaultValue = "0", required = true) String packageInfo,
                           @RequestParam(defaultValue = "0", required = true) String description2) throws IOException {
        prepareLoginUserInfo(request, model);
        AdminUserInfo adminUserInfo = getLoginedUser(request);
        title = StringEscapeUtils.unescapeHtml(title);
        subTitle = StringEscapeUtils.unescapeHtml(subTitle);
        description1 = StringEscapeUtils.unescapeHtml(description1);
        points = StringEscapeUtils.unescapeHtml(points);
        infos = StringEscapeUtils.unescapeHtml(infos);
        shipSpeed = StringEscapeUtils.unescapeHtml(shipSpeed);
        notice = StringEscapeUtils.unescapeHtml(notice);
        packageInfo = StringEscapeUtils.unescapeHtml(packageInfo);
        description2 = StringEscapeUtils.unescapeHtml(description2);
        ExecInfo execInfo = skuService.update(id, title, subTitle, price, hasGif == 1L, description1, points, infos,
                shipSpeed, notice, description2, packageInfo);
        model.addAttribute("success", execInfo.isSucc());
        model.addAttribute("reason", execInfo.getMsg());
        responseJson(response, model);
    }

    @RequestMapping(value = "/detail-upload", method = RequestMethod.GET)
	public ModelAndView detailUpload(HttpServletRequest request, HttpServletResponse response, ModelMap model){
        prepareLoginUserInfo(request, model);
		String sessionId = request.getSession().getId();
        model.addAttribute("skuDetailTypeList", SkuDetailType.values());
        model.addAttribute("sessionId", sessionId);
        model.addAttribute("pageType", "sku-upload-page");
		return new ModelAndView("admin/skuUpload");
	}

    @RequestMapping(value = "/detail-upload/upload", method = RequestMethod.GET)
    public void detailUpload(HttpServletRequest request, HttpServletResponse response, ModelMap model,
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
//        ExecInfo execInfo = skuService.remove(skuId, adminUserInfo);
//        model.addAttribute("success", execInfo.isSucc());
//        model.addAttribute("message", execInfo.getMsg());
        responseJson(response, model);
    }

    @RequestMapping(value = "/online", method = RequestMethod.GET)
    public void online(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       @RequestParam(defaultValue = "0", required = true) long id) throws IOException {
        prepareLoginUserInfo(request, model);
        boolean isSucc = skuService.changeStatus(id, PublicationStatus.ONLINE);
        model.addAttribute("success", isSucc);
        responseJson(response, model);
    }

    @RequestMapping(value = "/offline", method = RequestMethod.GET)
    public void offline(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                        @RequestParam(defaultValue = "0", required = true) long id) throws IOException {
        prepareLoginUserInfo(request, model);
        boolean isSucc = skuService.changeStatus(id, PublicationStatus.OFFLINE);
        model.addAttribute("success", isSucc);
        responseJson(response, model);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public void upload(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       @RequestParam(defaultValue = "0", required = true) long skuId) throws IOException {
        prepareLoginUserInfo(request, model);
        AdminUserInfo adminUserInfo = getLoginedUser(request);
//        SkuItem skuItem = skuService.getSkuItemInfo(skuId);
//        model.addAttribute("success", skuItem != null);
//        model.addAttribute("skuInfo", skuItem);
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

//        ExecResult<SkuItem> execResult = skuService.uploadImage(skuId, adminUserInfo.getId(), file);
//        model.addAttribute("success", execResult.isSucc());
//        model.addAttribute("message", execResult.getMsg());
//        model.addAttribute("id", execResult.getValue().getImgCount() - 1);
//        model.addAttribute("skuId", execResult.getValue().getId());
//        model.addAttribute("url", ossFormalApi.getSkuImgUrl(execResult.getValue().getId(), execResult.getValue().getImgCount() - 1));
        responseJson(response, model);
    }

}