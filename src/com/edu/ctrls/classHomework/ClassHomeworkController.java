package com.edu.ctrls.classHomework;

import java.io.File;
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

import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
import com.edu.Dic;
import com.edu.service.awayHomework.ClassHomeworkService;
import com.edu.service.classctr.ClassService;
import com.edu.service.homework.HomeworkService;
import common.utils.PathTools;

/**
 * 类名称：HomeworkController 创建人：FH 创建时间：2015-08-12
 */
@Controller
@RequestMapping(value = "/classHomework")
public class ClassHomeworkController extends BaseController {

	@Resource(name="classctrService")
	private ClassService classService;
	
	@Resource(name = "classHomeworkService")
	private ClassHomeworkService classHomeworkService;

//	/**
//	 * 新增
//	 */
//	@RequestMapping(value = "/save")
//	public ModelAndView save() throws Exception {
//		logBefore(logger, "新增Homework");
//		SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Subject currentUser = SecurityUtils.getSubject();
//		Session session = currentUser.getSession();
//		User user = (User) session.getAttribute(Const.SESSION_USER);
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		pd.put("ID", this.get32UUID()); // 主键
//		pd.put("CREATE_TIME", sdfTime.format(new Date())); // 创建时间
//		pd.put("CREATOR", user.getUSER_ID());
//		pd.put("CREATOR_NAME", user.getUSERNAME());
//		homeworkService.save(pd);
//		mv.addObject("msg", "success");
//		mv.setViewName("save_result");
//		return mv;
//	}

//	/**
//	 * 删除
//	 */
//	@RequestMapping(value = "/delete")
//	public void delete(PrintWriter out) {
//		logBefore(logger, "删除Homework");
//		PageData pd = new PageData();
//		try {
//			pd = this.getPageData();
//			homeworkService.delete(pd);
//			out.write("success");
//			out.close();
//		} catch (Exception e) {
//			logger.error(e.toString(), e);
//		}
//
//	}

//	/**
//	 * 修改
//	 */
//	@RequestMapping(value = "/edit")
//	public ModelAndView edit() throws Exception {
//		logBefore(logger, "修改Homework");
//		SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Subject currentUser = SecurityUtils.getSubject();
//		Session session = currentUser.getSession();
//		User user = (User) session.getAttribute(Const.SESSION_USER);
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		pd.put("CREATE_TIME", sdfTime.format(new Date())); // 创建时间
//		pd.put("CREATOR", user.getUSER_ID());
//		pd.put("CREATOR_NAME", user.getUSERNAME());
//		homeworkService.edit(pd);
//		mv.addObject("msg", "success");
//		mv.setViewName("save_result");
//		return mv;
//	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) {
		logBefore(logger, "列表Homework");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData> varList = classHomeworkService.list(page); // 列出Homework列表
			mv.setViewName("edu/homework/homework_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	/**
	 * 列表作业详细
	 */
	@RequestMapping(value = "/HomeworkDetailed")
	public ModelAndView HomeworkDetailed(Page page) {
		logBefore(logger, "列表Homework");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			page.setPd(pd);
			
			PageData para_pd = new PageData();
			para_pd.put("JR_CLASS_ID", pd.get("HOMEWORK_ID"));
			List<PageData> varList = classHomeworkService.HomeworkDetailed(pd); // 列出Homework列表
			mv.setViewName("edu/homework/homework_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 查看作业
	 */
	@RequestMapping(value="/showHomework")
	public ModelAndView showHomework(Page page){
		logBefore(logger, "查看作业Homework");
		ModelAndView mv = new ModelAndView();
		
		PageData pd = new PageData();
		try{
			
			
			pd = this.getPageData();
			page.setPd(pd);
			
//			PageData para_pd = new PageData();
			
			
//			para_pd.put("ID", pd.get("JR_CLASS"));
//			PageData class_pd= classHomeworkService.findById(para_pd);	//列出ClassCtr列表
			
			
//			para_pd.put("CLASS_ID", pd.get("JR_CLASS"));
//			List<PageData> varList=(List<PageData>)map.get("classList");
			

			PageData paraClassPd = new PageData();
			paraClassPd.put("ID", pd.get("JR_CLASS"));
			PageData classPd=classService.findById(paraClassPd);//查询班级的名称
			
			PageData paraCountPd = new PageData();
			paraCountPd.put("CLASS_ID", pd.get("JR_CLASS"));
			PageData resultCountStudentPd= classHomeworkService.queryStudentCountByClass(paraCountPd);	//查询班级的人数
			int countStudent=Integer.parseInt(resultCountStudentPd.get("count(ID)").toString());
			
			Map map = classHomeworkService.listHomework(page);
			
//			mv.addObject("varList", varList);
			mv.addObject("classInfo", classPd);
			mv.addObject("countStudent", countStudent);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.setViewName("edu/classHomework/showHomework");
		mv.addObject("pd", pd);
		mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		
		return mv;
	}
	
//	/**
//	 * 去新增页面
//	 */
//	@RequestMapping(value = "/goAdd")
//	public ModelAndView goAdd() {
//		logBefore(logger, "去新增Homework页面");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try {
//			mv.setViewName("edu/homework/homework_edit");
//
//			mv.addObject("msg", "homepayLoan");
//			mv.addObject("dic_homework_type", Dic.dic_homework_type_list);// 类型
//			mv.addObject("pd", pd);
//		} catch (Exception e) {
//			logger.error(e.toString(), e);
//		}
//		return mv;
//	}

//	/**
//	 * 去修改页面
//	 */
//	@RequestMapping(value = "/goEdit")
//	public ModelAndView goEdit() {
//		logBefore(logger, "去修改Homework页面");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try {
//			pd = homeworkService.findById(pd); // 根据ID读取
//			mv.setViewName("edu/homework/homework_edit");
//			mv.addObject("msg", "homepayLoan");
//			mv.addObject("dic_homework_type", Dic.dic_homework_type_list);// 类型
//			mv.addObject("msg", "edit");
//			mv.addObject("pd", pd);
//		} catch (Exception e) {
//			logger.error(e.toString(), e);
//		}
//		return mv;
//	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除Homework");
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if (null != DATA_IDS && !"".equals(DATA_IDS)) {
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				classHomeworkService.deleteAll(ArrayDATA_IDS);
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
	
	/**
	 * 批量删除homework
	 */
	@RequestMapping(value = "/deleteAlls")
	@ResponseBody
	public Object deleteAlls() {
		logBefore(logger, "批量删除Homework");
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if (null != DATA_IDS && !"".equals(DATA_IDS)) {
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				classHomeworkService.deleteAlls(ArrayDATA_IDS);
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
	

	
	

//	@RequestMapping(value = "/homepayLoan")
//	public ModelAndView payLoan(
//			@RequestParam(value = "fujian", required = false) MultipartFile file,
//			@RequestParam(value = "answer", required = false) MultipartFile answer,
//			HttpServletRequest request, ModelMap model) throws Exception {
//		logBefore(logger, "上传文件");
//
//		PageData pd = new PageData(request);
//		String jspID = (String) pd.get("ID");
//
//		Subject currentUser = SecurityUtils.getSubject();
//		Session session = currentUser.getSession();
//		User user = (User) session.getAttribute(Const.SESSION_USER);
//
//		List<String> suffixList = new ArrayList();
//
//		List<String> notSuffixList = new ArrayList();
//		notSuffixList.add("EXE");
//
//		String filePath = null;
//		String ffile = DateUtil.getDays();
//		String fileName = null;
//		String path = null;
//		ModelAndView mv = this.getModelAndView();
//		String originalFilename = null;
//		if (null != file && !file.isEmpty()) {
//			originalFilename = file.getOriginalFilename();
//			String suffix = originalFilename.substring(
//					originalFilename.lastIndexOf(".") + 1).toUpperCase();
//			if (suffixList != null && !suffixList.isEmpty()
//					&& !suffixList.contains(suffix)) {
//				Map pd2 = new HashMap();
//				pd2.put("RESULT", 0);
//
//				StringBuilder suffixStr = new StringBuilder();
//				for (String suffixTemp : suffixList) {
//					suffixStr.append(suffixTemp);
//					suffixStr.append(",");
//				}
//				String suffixMsg = suffixStr.substring(0,
//						suffixStr.length() - 1);
//				pd2.put("MSG", "请上传格式为【" + suffixMsg + "】的附件！");
//				return mv;
//			}
//			if (notSuffixList != null && !notSuffixList.isEmpty()
//					&& notSuffixList.contains(suffix)) {
//				Map pd2 = new HashMap();
//				pd2.put("RESULT", 0);
//				StringBuilder notSuffixStr = new StringBuilder();
//				for (String suffixTemp : notSuffixList) {
//					notSuffixStr.append(suffixTemp);
//					notSuffixStr.append(",");
//				}
//				String notSuffixMsg = notSuffixStr.substring(0, notSuffixStr
//						.length() - 1);
//				pd2.put("MSG", "请不要上传格式为【" + notSuffixMsg + "】的附件！");
//				return mv;
//			}
//			filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile; // 文件上传路径
//			fileName = FileUpload.fileUp(file, filePath, this.get32UUID()); // 执行上传
//			path = Const.FILEPATHIMG + ffile + "/" + fileName;
//		} else {
//			if (jspID == null || "".equals(jspID)) {// id为空，说明是添加，提示必须添加文件
//				return null;
//			}
//		}
//		if (jspID == null || "".equals(jspID)) {
//			pd.put("ID", this.get32UUID()); // 主键
//			pd.put("FILEPATH", path);
//			pd.put("FILE_NAME", originalFilename);
//			pd.put("VERIFY", 0);
//			pd.put("CREATOR", user.getUSER_ID());
//			pd.put("CREATOR_NAME", user.getUSERNAME());
//			pd.put("CREATE_TIME", DateUtil.getTime());
////			homeworkService.save(pd);
//		} else {
//			pd.put("FILEPATH", path);
//			pd.put("FILE_NAME", originalFilename);
////			homeworkService.edit(pd);
//		}
//		
//	//上传答案
//		if (null != answer && !answer.isEmpty()) {
//			originalFilename = answer.getOriginalFilename();
//			String suffix = originalFilename.substring(
//					originalFilename.lastIndexOf(".") + 1).toUpperCase();
//			if (suffixList != null && !suffixList.isEmpty()
//					&& !suffixList.contains(suffix)) {
//				Map pd2 = new HashMap();
//				pd2.put("RESULT", 0);
//				StringBuilder suffixStr = new StringBuilder();
//				for (String suffixTemp : suffixList) {
//					suffixStr.append(suffixTemp);
//					suffixStr.append(",");
//				}
//				String suffixMsg = suffixStr.substring(0,
//						suffixStr.length() - 1);
//				pd2.put("MSG", "请上传格式为【" + suffixMsg + "】的附件！");
//				return mv;
//			}
//			if (notSuffixList != null && !notSuffixList.isEmpty()
//					&& notSuffixList.contains(suffix)) {
//				Map pd2 = new HashMap();
//				pd2.put("RESULT", 0);
//				StringBuilder notSuffixStr = new StringBuilder();
//				for (String suffixTemp : notSuffixList) {
//					notSuffixStr.append(suffixTemp);
//					notSuffixStr.append(",");
//				}
//				String notSuffixMsg = notSuffixStr.substring(0, notSuffixStr
//						.length() - 1);
//				pd2.put("MSG", "请不要上传格式为【" + notSuffixMsg + "】的附件！");
//				return mv;
//			}
//			filePath = PathUtil.getClasspath() + Const.FILEPATHFILE + ffile; // 文件上传路径
//			fileName = FileUpload.fileUp(answer, filePath, this.get32UUID()); // 执行上传
//			path = Const.FILEPATHFILE + ffile + "/" + fileName;
//		} else {
//			if (jspID == null || "".equals(jspID)) {// id为空，说明是添加，提示必须添加文件
//				return null;
//			}
//		}
//		if (jspID == null || "".equals(jspID)) {
//			pd.put("ID", this.get32UUID()); // 主键
//			pd.put("ANSWER_PATH", path);
//			pd.put("ANSWER", originalFilename);
////			pd.put("VERIFY", 0);
////			pd.put("CREATOR", user.getUSER_ID());
////			pd.put("CREATOR_NAME", user.getUSERNAME());
////			pd.put("CREATE_TIME", DateUtil.getTime());
//			homeworkService.save(pd);
//		} else {
//			pd.put("ANSWER_PATH", path);
//			pd.put("ANSWER", originalFilename);
//			homeworkService.edit(pd);
//		}
//		mv.addObject("msg", "success");
//		mv.setViewName("save_result");
//		return mv;
//	}
//
//	@RequestMapping("download")
//	public ResponseEntity<byte[]> download() {
//		logBefore(logger, "下载附件");
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try {
//			List<PageData> list = homeworkService.listAll(pd);
//			if (list != null && !list.isEmpty()) {
//				PageData resultPd = list.get(0);
//				String path = (String) resultPd.get("FILEPATH");
//				String filename = (String) resultPd.get("FILE_NAME");
//
//				File webRootPath = PathTools.getWebRootPath();
//				// String
//				// path="D:\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\springMVC\\WEB-INF\\upload\\图片10（定价后）.xlsx";
//				File file = new File(webRootPath, path);
//				HttpHeaders headers = new HttpHeaders();
//				String fileName = new String(filename.getBytes("UTF-8"),
//						"iso-8859-1");// 为了解决中文名称乱码问题
//				headers.setContentDispositionFormData("attachment", fileName);
//				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//				return new ResponseEntity<byte[]>(FileUtils
//						.readFileToByteArray(file), headers, HttpStatus.CREATED);
//			}
//		} catch (Exception e) {
//			logger.error(e.toString(), e);
//		}
//		return null;
//	}

	/*
	 * 导出到excel
	 * 
	 * @return
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() {
		logBefore(logger, "导出Homework到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("标题"); // 1
			titles.add("分类"); // 2
			titles.add("分类名称"); // 3
			titles.add("上传文件路径"); // 4
			titles.add("创建时间"); // 5
			titles.add("创建人id"); // 6
			titles.add("创建人姓名"); // 7
			dataMap.put("titles", titles);
			List<PageData> varOList = classHomeworkService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for (int i = 0; i < varOList.size(); i++) {
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("TITLE")); // 1
				vpd.put("var2", varOList.get(i).getString("TYPE")); // 2
				vpd.put("var3", varOList.get(i).getString("TYPE_NAME")); // 3
				vpd.put("var4", varOList.get(i).getString("FILEPATH")); // 4
				vpd.put("var5", varOList.get(i).getString("CREATE_TIME")); // 5
				vpd.put("var6", varOList.get(i).getString("CREATOR")); // 6
				vpd.put("var7", varOList.get(i).getString("CREATOR_NAME")); // 7
				varList.add(vpd);
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
