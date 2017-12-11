package com.edu.ctrls.staffCtrl;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.edu.Dic;
import com.edu.service.staffSrvImpl.StaffService;
import com.fh.controller.base.BaseController;
import com.fh.entity.system.Menu;
import com.fh.entity.system.User;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.DateUtil;
import com.fh.util.FileUpload;
import com.fh.util.ObjectExcelView;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.Tools;
import com.fh.util.Watermark;

/**
 * 类名称：StaffController 创建人：FH 创建时间：2015-06-18
 */
@Controller
@RequestMapping(value = "/staff")
public class StaffController extends BaseController {
	
	@Resource(name = "staffService")
	private StaffService staffService;

	/**
	 * 新增
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, "新增Staff");
		
		ModelAndView mv = this.getModelAndView();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		PageData pd = new PageData();
		pd = this.getPageData();

		if("".equals(pd.get("MINZU"))){
			pd.put("MINZU", null);
			pd.put("MINZU_NAME", null);
		}
		if("".equals(pd.get("ZZMM"))){
			pd.put("ZZMM", null);
			pd.put("ZZMM_NAME", null);
		}
		if("".equals(pd.get("EDU"))){
			pd.put("EDU", null);
			pd.put("EDU_NAME", null);
		}
		if("".equals(pd.get("WAGE"))){
			pd.put("WAGE", 0);
		}
		pd.put("ID", this.get32UUID()); // 主键
		pd.put("DELETED", "0"); // 是否删除
		pd.put("CREATOR", user.getUSER_ID());
		pd.put("CREATOR_NAME", user.getUSERNAME());
		pd.put("CREATE_TIME", DateUtil.getTime());
		

//		pd.put("WORK_AGE",
//				DateUtil.getDiffYear(pd.getString("ENTRY_TIME"),
//						DateUtil.getTime()));

		staffService.save(pd);

		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	
	@ResponseBody
    @RequestMapping(value = "/upload",produces = "text/html;charset=UTF-8")  
    public Object upload(@RequestParam(value = "picFile", required = false) MultipartFile file, HttpServletRequest request, ModelMap model) {  
		List<String> suffixList=new ArrayList();
		suffixList.add("JPG");
		suffixList.add("JPE");
		suffixList.add("JPEG");
		suffixList.add("PNG");
		suffixList.add("BMP");
		suffixList.add("GIF");
		StringBuilder suffixStr=new StringBuilder();
		for(String suffix:suffixList){
			suffixStr.append(suffix);
			suffixStr.append(",");
		}
		String suffixMsg=suffixStr.substring(0, suffixStr.length()-1);
        String filePath=null;
        String  ffile = DateUtil.getDays();
        String fileName = "";
		if (null != file && !file.isEmpty()) {
			String name=file.getOriginalFilename();
			String suffix=name.substring(name.lastIndexOf(".")+1).toUpperCase();
			if(!suffixList.contains(suffix)){
				Map pd = new HashMap();
				pd.put("RESULT", 0);
				pd.put("MSG", "请上传格式为【"+suffixMsg+"】的图片！");
				return JSONObject.fromObject(pd).toString();
			}
			filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile;		//文件上传路径
			fileName = FileUpload.fileUp(file, filePath, this.get32UUID());				//执行上传
		}else{
			Map pd = new HashMap();
			pd.put("RESULT", 0);
			pd.put("MSG", "上传失败");
			return JSONObject.fromObject(pd).toString();
		}
		
		Map pd = new HashMap();
		pd.put("RESULT", 1);
		pd.put("NAME", fileName);							//文件名
		pd.put("PATH", Const.FILEPATHIMG+ffile + "/" + fileName);				//路径
		//加水印
		Watermark.setWatemark(PathUtil.getClasspath() + Const.FILEPATHIMG + ffile + "/" + fileName);
		return JSONObject.fromObject(pd).toString();
    }  
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) {
		logBefore(logger, "删除Staff");
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			staffService.delete(pd);
			out.write("success");
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}

	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, "修改Staff");
		ModelAndView mv = this.getModelAndView();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		PageData pd = new PageData();
		pd = this.getPageData();
		if("".equals(pd.get("MINZU"))){
			pd.put("MINZU", null);
			pd.put("MINZU_NAME", null);
		}
		if("".equals(pd.get("ZZMM"))){
			pd.put("ZZMM", null);
			pd.put("ZZMM_NAME", null);
		}
		if("".equals(pd.get("EDU"))){
			pd.put("EDU", null);
			pd.put("EDU_NAME", null);
		}
		if("".equals(pd.get("WAGE"))){
			pd.put("WAGE", 0);
		}
		pd.put("CREATOR", user.getUSER_ID());
		pd.put("CREATOR_NAME", user.getUSERNAME());
		pd.put("CREATE_TIME", DateUtil.getTime());
		pd.put("WORK_AGE",
				DateUtil.getDiffYear(pd.getString("ENTRY_TIME"),
						DateUtil.getTime()));
		staffService.edit(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) {
		logBefore(logger, "列表Staff");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String NAME = pd.getString("NAME");
		String CREATE_TIME_START = pd.getString("lastLoginStart");
		String CREATE_TIME_END = pd.getString("lastLoginEnd");
		
		
		PageData staPd = null;
		staPd = new PageData();
		
		staPd.putAll(pd);
		
		if (NAME != null && !NAME.equals("")) {
			staPd.put("NAME", "%" + NAME + "%");
		}
		if (CREATE_TIME_START != null && !CREATE_TIME_START.equals("")) {
			staPd.put("CREATE_TIME_START", CREATE_TIME_START + " 00:00:00");
		} 
		if (CREATE_TIME_END != null && !CREATE_TIME_END.equals("")) {
			staPd.put("CREATE_TIME_END", CREATE_TIME_END + " 23:59:59");
		}
		
		
		try {
			page.setPd(staPd);
			List<PageData> varList = staffService.list(page); // 列出Staff列表
			mv.setViewName("edu/staff/staff_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			
			mv.addObject("dic_zzmm_list", Dic.dic_zzmm_list);
			mv.addObject("dic_job_list", Dic.dic_job_list);
			
			mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
			
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}

		return mv;
	}

	/**
	 * 去新增页面
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() {
		logBefore(logger, "去新增Staff页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("edu/staff/staff_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
			mv.addObject("dic_job_list", Dic.dic_job_list);

			mv.addObject("dic_zzmm_list", Dic.dic_zzmm_list);
			mv.addObject("dic_minzu_list", Dic.dic_minzu_list);
			mv.addObject("dic_edu_list", Dic.dic_edu_list);
			mv.addObject("dic_subject_list", Dic.dic_subject_list);
			mv.addObject("dic_stff_status_list", Dic.dic_stff_status_list);

		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 去修改页面
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit() {
		logBefore(logger, "去修改Staff页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = staffService.findById(pd); // 根据ID读取
			mv.setViewName("edu/staff/staff_edit");
			mv.addObject("msg", "edit");

			mv.addObject("pd", pd);

			mv.addObject("dic_job_list", Dic.dic_job_list);

			mv.addObject("dic_zzmm_list", Dic.dic_zzmm_list);
			mv.addObject("dic_minzu_list", Dic.dic_minzu_list);
			mv.addObject("dic_edu_list", Dic.dic_edu_list);
			mv.addObject("dic_subject_list", Dic.dic_subject_list);
			mv.addObject("dic_stff_status_list", Dic.dic_stff_status_list);

		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除Staff");
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if (null != DATA_IDS && !"".equals(DATA_IDS)) {
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				staffService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
			} else {
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}

	/*
	 * 导出到excel
	 * 
	 * @return
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() {
		logBefore(logger, "导出Staff到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String NAME = pd.getString("NAME");
		String CREATE_TIME_START = pd.getString("lastLoginStart");
		String CREATE_TIME_END = pd.getString("lastLoginEnd");
		String ZZMM = pd.getString("ZZMM");
		
		PageData staPd = new PageData();
		if (NAME != null && !NAME.equals("")) {
			staPd.put("NAME", "%" + NAME + "%");
		}
		if (CREATE_TIME_START != null && !CREATE_TIME_START.equals("")) {
			staPd.put("CREATE_TIME_START", CREATE_TIME_START + " 00:00:00");
		} 
		if (CREATE_TIME_END != null && !CREATE_TIME_END.equals("")) {
			staPd.put("CREATE_TIME_END", CREATE_TIME_END + " 23:59:59");
		}
		if(ZZMM != null && !ZZMM.equals("")){
			staPd.put("ZZMM", ZZMM);
		}
		
		
		try {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("姓名"); // 1
			titles.add("性别"); // 2
			//titles.add("职务ID"); // 3
			titles.add("职务名"); // 4
			titles.add("身份证号");
			titles.add("电话号码");
			titles.add("学历");
			titles.add("生日");
			titles.add("地址");
			titles.add("入职时间");
			//titles.add("创建时间"); // 5
			//titles.add("创建人ID"); // 6
			//titles.add("创建人姓名"); // 7
			//titles.add("是否删除"); // 8
			dataMap.put("titles", titles);
			
			List<PageData> varOList = staffService.listAll(staPd);
			List<PageData> varList = new ArrayList<PageData>();
			for (int i = 0; i < varOList.size(); i++) {
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("NAME")); // 1
				
				Object gender=varOList.get(i).get("GENDER");
				if(gender!=null){
					int genderInt = (Integer)gender;
					if(genderInt==1){
						vpd.put("var2", "男");
					}else{
						vpd.put("var2", "女");
					}
				}
				
				 // 2
				//vpd.put("var3", varOList.get(i).getString("JOB")); // 3
				vpd.put("var3", varOList.get(i).getString("JOB_NAME")); // 4
				vpd.put("var4", varOList.get(i).getString("SFZH"));
				vpd.put("var5", varOList.get(i).getString("PHONE"));
				vpd.put("var6", varOList.get(i).getString("EDU_NAME"));
				vpd.put("var7", varOList.get(i).getString("BIRTHDAY"));
				vpd.put("var8", varOList.get(i).getString("ADDRESS"));
				vpd.put("var9", varOList.get(i).getString("ENTRY_TIME"));
				/*vpd.put("var5", varOList.get(i).getString("CREATE_TIME")); // 5
				vpd.put("var6", varOList.get(i).getString("CREATOR")); // 6
				vpd.put("var7", varOList.get(i).getString("CREATOR_NAME")); // 7
				vpd.put("var8", varOList.get(i).get("DELETED").toString()); // 8
*/				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv, dataMap);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/* ===============================权限================================== */
	public Map<String, String> getHC() {
		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
	}

	/* ===============================权限================================== */

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,
				true));
	}
}
