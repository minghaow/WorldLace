package nanshen.web.controller.admin;

import nanshen.constant.SystemConstants;
import nanshen.dao.AdminUserInfoDao;
import nanshen.dao.LookInfoDao;
import nanshen.dao.LookTagDao;
import nanshen.data.AdminUserInfo;
import nanshen.data.ExecInfo;
import nanshen.data.LookInfo;
import nanshen.data.LookTag;
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
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/operation/sku")
public class SkuCtrl extends BaseController {

	@Autowired
	private AdminUserInfoDao adminUserInfoDao;

    @Autowired
    private LookInfoDao lookInfoDao;

    @Autowired
    private LookTagDao lookTagDao;

	@Autowired
	private OssFormalApi ossFormalApi;

	@RequestMapping(value = "/sku-list", method = RequestMethod.GET)
	public ModelAndView lookList(HttpServletRequest request, HttpServletResponse response, ModelMap model,
							   @RequestParam(defaultValue = "1", required = true) long page) {
        prepareLoginUserInfo(request, model);
		String imgUrl = "/images/slider/slider1.png";
		String title = "男神必备搭配";
		String subTitle = "作为一个长相倍儿帅的男神，假如再配上帅呆的衣服，那约炮就木问题啦。";
		model.addAttribute("AdminUserInfoList", adminUserInfoDao.getAll());
		model.addAttribute("page", page);
		model.addAttribute("imgUrl", imgUrl);
		model.addAttribute("title", title);
		model.addAttribute("subTitle", subTitle);
		return new ModelAndView("admin/lookList");
	}

	@RequestMapping(value = "/sku-upload", method = RequestMethod.GET)
	public ModelAndView lookUpload(HttpServletRequest request, HttpServletResponse response, ModelMap model){
        prepareLoginUserInfo(request, model);
        List<LookTag> lookTagList = lookTagDao.getAll();
		String sessionId = request.getSession().getId();
        model.addAttribute("lookId", 0);
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
        if (lookId != 0) {
            LookInfo lookInfo = lookInfoDao.get(lookId);
            lookInfo.setCreateTime(new Date());
            lookInfo.setTitle(title);
            lookInfo.setSubTitle(subTitle);
            lookInfo.setDescription(desc);
        }
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
                            @RequestParam(defaultValue = "0", required = true) long lookId)
            throws IOException {
        AdminUserInfo adminUserInfo = prepareLoginUserInfo(request, model);
        MultipartFile file = request.getFile("Filedata");
        InputStream is = file.getInputStream();
        LookInfo lookInfo;
        boolean setSuccessfully = false;
        String imgKey = "";
        if (lookId == 0) {
            lookInfo = lookInfoDao.insert(new LookInfo(adminUserInfo.getId()));
        } else {
            lookInfo = lookInfoDao.get(lookId);
        }
        imgKey = "images/look" + lookInfo.getId() + "/" + lookInfo.getImgCount();
        System.out.println("type : " + file.getContentType());
        ExecInfo execInfo = ossFormalApi.putObject(SystemConstants.BUCKET_NAME, imgKey, is, file.getSize());
        if (execInfo.isSucc()) {
            lookInfo.setImgCount(lookInfo.getImgCount() + 1);
            setSuccessfully = lookInfoDao.update(lookInfo);
        }
        model.addAttribute("success", execInfo.isSucc() && setSuccessfully);
        model.addAttribute("id", lookInfo.getImgCount() - 1);
        model.addAttribute("lookId", lookInfo.getId());
        model.addAttribute("url", ossFormalApi.getLookImgUrl(lookInfo.getId(), lookInfo.getImgCount() - 1));
        responseJson(response, model);
    }

}