package com.edu.ctrls.homework;

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
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.AppUtil;
import com.fh.util.DateUtil;
import com.fh.util.FileUpload;
import com.fh.util.ObjectExcelView;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.edu.Dic;
import com.edu.service.homework.HomeworkService;
import common.utils.PathTools;

/**
 * 类名称：HomeworkController 创建人：FH 创建时间：2015-08-12
 */
@Controller
@RequestMapping(value = "/homework")
public class HomeworkController extends BaseController {

	@Resource(name = "homeworkService")
	private HomeworkService homeworkService;

	/**
	 * 新增
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, "新增Homework");

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		homeworkService.save(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) {
		logBefore(logger, "删除Homework");
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			PageData resultPd = homeworkService.findById(pd);

			String file_path = resultPd.getString("FILE_PATH");
			if (file_path != null) {
				this.deleteFile(file_path);
			}

			String answer_path = resultPd.getString("ANSWER_PATH");
			if (answer_path != null) {
				this.deleteFile(answer_path);
			}

			homeworkService.delete(pd);
			out.write("success");
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}

	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public boolean deleteFile(String path) {
		logBefore(logger, "删除文件");

		boolean flag = false;
		File webRootPath = PathTools.getWebRootPath();
		File file = new File(webRootPath, path);

		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * Edit页_刪除
	 */
	@RequestMapping(value = "/deleteFileEdit")
	public void deleteFileEdit() {
		logBefore(logger, "根据ID删除文件");

		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			PageData resultPd = homeworkService.findById(pd);
			String operator = (String) pd.get("OPERATOR");
			if (operator.equals("FILE")) {
				deleteFile((String) resultPd.get("FILE_PATH"));
				homeworkService.deleteFile(pd);
				
			} else {
				deleteFile((String) resultPd.get("ANSWER_PATH"));
				homeworkService.deleteAnswer(pd);
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, "修改Homework");

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		homeworkService.edit(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) {
		logBefore(logger, "列表Homework");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		PageData paraPd = new PageData();
		paraPd.putAll(pd);

		String title = paraPd.getString("TITLE");
		if (title != null && !"".equals(title)) {
			paraPd.put("TITLE", title);
		}

		String CREATE_TIME_START = paraPd.getString("CREATE_TIME_START");
		if (CREATE_TIME_START != null && !"".equals(CREATE_TIME_START)) {
			paraPd.put("CREATE_TIME_START", CREATE_TIME_START + " 00:00:00");
		}

		String CREATE_TIME_END = paraPd.getString("CREATE_TIME_END");
		if (CREATE_TIME_END != null && !"".equals(CREATE_TIME_END)) {
			paraPd.put("CREATE_TIME_END", CREATE_TIME_END + " 23:59:59");
		}
		try {
			page.setPd(pd);
			List<PageData> varList = homeworkService.list(page); // 列出Homework列表
			mv.setViewName("edu/homework/homework_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject("dic_homework_type_list", Dic.dic_homework_type_list);
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
		logBefore(logger, "去新增Homework页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("edu/homework/homework_edit");
			mv.addObject("msg", "homework");
			mv.addObject("pd", pd);
			mv.addObject("dic_homework_type_list", Dic.dic_homework_type_list);
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
		logBefore(logger, "去修改Homework页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = homeworkService.findById(pd); // 根据ID读取
			mv.setViewName("edu/homework/homework_edit");
			mv.addObject("msg", "homework");
			mv.addObject("pd", pd);
			mv.addObject("dic_homework_type_list", Dic.dic_homework_type_list);
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
		logBefore(logger, "批量删除Homework");
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			PageData resultPdId = new PageData();

			if (null != DATA_IDS && !"".equals(DATA_IDS)) {
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				for (int i = 0; i < ArrayDATA_IDS.length; i++) {
					resultPdId.put("ID", ArrayDATA_IDS[i]);
					PageData resultPd = homeworkService.findById(resultPdId);

					String file_path = resultPd.getString("FILE_PATH");
					if (file_path != null) {
						this.deleteFile(file_path);
					}

					String answer_path = resultPd.getString("ANSWER_PATH");
					if (answer_path != null) {
						this.deleteFile(answer_path);
					}
				}
				homeworkService.deleteAll(ArrayDATA_IDS);
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
	 * 
	 * 上传文件
	 * 
	 * @param file
	 * @param answer
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/homework.do")
	public ModelAndView homework(
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "answer", required = false) MultipartFile answer,
			HttpServletRequest request, ModelMap model) throws Exception {
		PageData pd = new PageData(request);
		String jspID = (String) pd.get("ID");

		String file_name = pd.getString("FILE_NAME");
		String file_path = pd.getString("FILE_PATH");
		String answer_name = pd.getString("ANSWER_NAME");
		String answer_path = pd.getString("ANSWER_PATH");

		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);

		pd.put("CREATE_TIME", DateUtil.getTime());
		pd.put("CREATOR", user.getUSER_ID());
		pd.put("CREATOR_NAME", user.getUSERNAME());

		List<String> suffixList = new ArrayList<String>();

		List<String> notSuffixList = new ArrayList<String>();
		notSuffixList.add("EXE");

		String filePath = null;
		String ffile = DateUtil.getDays();
		String fileName = null;
		String path = null;
		ModelAndView mv = this.getModelAndView();

		String originalFilename = null;

		// 上传文件
		if (null != file && !file.isEmpty()) {
			originalFilename = file.getOriginalFilename();
			String suffix = originalFilename.substring(
					originalFilename.lastIndexOf(".") + 1).toUpperCase();
			if (suffixList != null && !suffixList.isEmpty()
					&& !suffixList.contains(suffix)) {
				Map<String, Object> pd2 = new HashMap<String, Object>();
				pd2.put("RESULT", 0);

				StringBuilder suffixStr = new StringBuilder();
				for (String suffixTemp : suffixList) {
					suffixStr.append(suffixTemp);
					suffixStr.append(",");
				}
				String suffixMsg = suffixStr.substring(0,
						suffixStr.length() - 1);
				pd2.put("MSG", "请上传格式为【" + suffixMsg + "】的附件！");
				return mv;
			}
			if (notSuffixList != null && !notSuffixList.isEmpty()
					&& notSuffixList.contains(suffix)) {
				Map<String, Object> pd2 = new HashMap<String, Object>();
				pd2.put("RESULT", 0);

				StringBuilder notSuffixStr = new StringBuilder();
				for (String suffixTemp : notSuffixList) {
					notSuffixStr.append(suffixTemp);
					notSuffixStr.append(",");
				}
				String notSuffixMsg = notSuffixStr.substring(0,
						notSuffixStr.length() - 1);

				pd2.put("MSG", "请不要上传格式为【" + notSuffixMsg + "】的附件！");
				return mv;
			}
			filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile; // 文件上传路径
			fileName = FileUpload.fileUp(file, filePath, this.get32UUID()); // 执行上传

			path = Const.FILEPATHIMG + ffile + "/" + fileName;
		} else {
			originalFilename = file_name;
			path = file_path;
		}

		if (jspID == null || "".equals(jspID)) {// 新增
			pd.put("FILE_NAME", originalFilename);
			pd.put("FILE_PATH", path);

			// homeworkService.save(pd);
		} else {// 编辑
			pd.put("FILE_NAME", originalFilename);
			pd.put("FILE_PATH", path);

			// homeworkService.edit(pd);
		}

		// 上传答案附件
		if (null != answer && !answer.isEmpty()) {
			originalFilename = answer.getOriginalFilename();
			String suffix = originalFilename.substring(
					originalFilename.lastIndexOf(".") + 1).toUpperCase();

			if (suffixList != null && !suffixList.isEmpty()
					&& !suffixList.contains(suffix)) {
				Map<String, Object> pd2 = new HashMap<String, Object>();
				pd2.put("RESULT", 0);

				StringBuilder suffixStr = new StringBuilder();
				for (String suffixTemp : suffixList) {
					suffixStr.append(suffixTemp);
					suffixStr.append(",");
				}
				String suffixMsg = suffixStr.substring(0,
						suffixStr.length() - 1);
				pd2.put("MSG", "请上传格式为【" + suffixMsg + "】的附件！");
				return mv;
			}

			if (notSuffixList != null && !notSuffixList.isEmpty()
					&& notSuffixList.contains(suffix)) {
				Map<String, Object> pd2 = new HashMap<String, Object>();
				pd2.put("RESULT", 0);

				StringBuilder notSuffixStr = new StringBuilder();
				for (String suffixTemp : notSuffixList) {
					notSuffixStr.append(suffixTemp);
					notSuffixStr.append(",");
				}
				String notSuffixMsg = notSuffixStr.substring(0,
						notSuffixStr.length() - 1);

				pd2.put("MSG", "请不要上传格式为【" + notSuffixMsg + "】的附件！");
				return mv;
			}
			filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile; // 文件上传路径
			fileName = FileUpload.fileUp(answer, filePath, this.get32UUID()); // 执行上传

			path = Const.FILEPATHIMG + ffile + "/" + fileName;
		} else {
			originalFilename = answer_name;
			path = answer_path;
		}

		if (jspID == null || "".equals(jspID)) {// 新增
			pd.put("ANSWER_NAME", originalFilename);
			pd.put("ANSWER_PATH", path);


			pd.put("ID", this.get32UUID());
			homeworkService.save(pd);
		} else {// 编辑
			pd.put("ANSWER_NAME", originalFilename);
			pd.put("ANSWER_PATH", path);

			homeworkService.edit(pd);
		}

		mv.addObject("msg", "success");
		mv.setViewName("save_result");

		return mv;
	}

	/**
	 * 
	 * 下载附件
	 * 
	 * @return
	 */
	@RequestMapping("download")
	public ResponseEntity<byte[]> download() {
		logBefore(logger, "下载附件");
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			PageData resultPd = homeworkService.findById(pd);
			if (resultPd != null && !resultPd.isEmpty()) {

				String operator = (String) pd.get("OPERATOR");
				if ("FILE".equals(operator)) {
					String filename = (String) resultPd.get("FILE_NAME");
					String path = (String) resultPd.get("FILE_PATH");

					File webRootPath = PathTools.getWebRootPath();
					File file = new File(webRootPath, path);
					HttpHeaders headers = new HttpHeaders();
					String fileName = new String(filename.getBytes("UTF-8"),
							"iso-8859-1");// 为了解决中文名称乱码问题
					headers.setContentDispositionFormData("attachment",
							fileName);
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					return new ResponseEntity<byte[]>(
							FileUtils.readFileToByteArray(file), headers,
							HttpStatus.CREATED);
				} else {
					String filename = (String) resultPd.get("ANSWER_NAME");
					String path = (String) resultPd.get("ANSWER_PATH");

					File webRootPath = PathTools.getWebRootPath();
					File file = new File(webRootPath, path);
					HttpHeaders headers = new HttpHeaders();
					String fileName = new String(filename.getBytes("UTF-8"),
							"iso-8859-1");// 为了解决中文名称乱码问题
					headers.setContentDispositionFormData("attachment",
							fileName);
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					return new ResponseEntity<byte[]>(
							FileUtils.readFileToByteArray(file), headers,
							HttpStatus.CREATED);
				}

			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return null;
	}

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
			titles.add("分类id"); // 2
			titles.add("分类名称"); // 3
			titles.add("上传文件名称");// 4
			titles.add("上传文件路径");// 5
			titles.add("答案");// 6
			titles.add("答案文件名称");// 7
			titles.add("答案文件路径"); // 8
			titles.add("创建时间"); // 9
			titles.add("创建人ID"); // 10
			titles.add("创建人姓名"); // 11
			dataMap.put("titles", titles);
			List<PageData> varOList = homeworkService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for (int i = 0; i < varOList.size(); i++) {
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("TITLE")); // 1
				vpd.put("var2", varOList.get(i).get("TYPE") + ""); // 2
				vpd.put("var3", varOList.get(i).getString("TYPE_NAME")); // 3
				vpd.put("var4", varOList.get(i).getString("FILE_NAME"));
				vpd.put("var5", varOList.get(i).getString("FILE_PATH"));
				vpd.put("var6", varOList.get(i).getString("ANSWER"));
				vpd.put("var7", varOList.get(i).getString("ANSWER_NAME"));
				vpd.put("var8", varOList.get(i).getString("ANSWER_PATH"));
				vpd.put("var9", varOList.get(i).getString("CREATE_TIME"));
				vpd.put("var10", varOList.get(i).getString("CREATOR"));
				vpd.put("var11", varOList.get(i).getString("CREATOR_NAME"));
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
