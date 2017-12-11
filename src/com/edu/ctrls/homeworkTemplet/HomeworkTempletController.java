package com.edu.ctrls.homeworkTemplet;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.edu.Dic;
import com.edu.service.homeworkTemplet.HomeworkTempletService;
import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.DateUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.Const;
import com.fh.util.PageData;

/**
 * 类名称：HomeworkTempletController 创建人：zhanghengyuan 创建时间：2015-08-12
 */
@Controller
@RequestMapping(value = "/homeworkTemplet")
public class HomeworkTempletController extends BaseController {

	@Resource(name = "homeworkTempletService")
	private HomeworkTempletService homeworkTempletService;
	/**
	 * 新增
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, "新增HomeworkTemplet");
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ID", this.get32UUID()); // 主键
		pd.put("CREATOR", user.getUSER_ID());
		pd.put("CREATOR_NAME", user.getUSERNAME());
		pd.put("CREATE_TIME", DateUtil.getTime());
		homeworkTempletService.save(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) {
		logBefore(logger, "删除HomeWorkTemplet");
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			homeworkTempletService.delete(pd);
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
	public ModelAndView edit() {
		logBefore(logger, "修改HomeWorkTemplet");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			homeworkTempletService.edit(pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 修改HOMEWORK_IDS字段
	 */
	@RequestMapping(value = "/editHomeWorkIds")
	@ResponseBody
	public Object editHomeWorkIds() {
		logBefore(logger, "修改HomeWorkTemplet");
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			homeworkTempletService.edit(pd);
			pd.put("msg", "ok");
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
	 * 列表
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) {
		logBefore(logger, "列表HomeworkTemplet");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData paraPd = new PageData();
		paraPd.putAll(pd);
		String title = paraPd.getString("TITLE");
		if (title != null && !"".equals(title)) {
			paraPd.put("TITLE",  title );
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
			page.setPd(paraPd);
			List<PageData> varList = homeworkTempletService.list(page); // 列出HomeworkTemplet列表
			mv.setViewName("edu/homeworktemp/homeworkTemplet_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject("dic_homeworkTemplet_type_list",
					Dic.dic_homework_type_list);
			mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	/**
	 * 列表连接的是templet_list页的题目数按钮
	 */
	@RequestMapping(value = "/listFromTemplet")
	public ModelAndView listFromTemplet(Page page) {
		logBefore(logger, "由模板字段列表Homework");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
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
		String TEMPLET_ID = paraPd.getString("TEMPLET_ID");
		if (TEMPLET_ID != null && !"".equals(TEMPLET_ID)) {
			paraPd.put("ID", TEMPLET_ID);
		}
		try {
			PageData temp=homeworkTempletService.findById(paraPd);
			String HOMEWORK_IDS=temp.getString("HOMEWORK_IDS");
			paraPd.put("HOMEWORK_IDS",HOMEWORK_IDS);//模板的更新操作需要使用。
			List<PageData> varList=new ArrayList<PageData>();
			if(null!=HOMEWORK_IDS&&!"".equals(HOMEWORK_IDS)){
				String[] Array=HOMEWORK_IDS.split(",");
				paraPd.put("HOMEWORK_IDS_ARR",Array);
				page.setPd(paraPd);
				varList = homeworkTempletService.listFromTemplet(page); // 列出Homework列表
			}else{
				page.setPd(paraPd);
			}
			mv.setViewName("edu/homeworktemp/homework_list_no_del");
			mv.addObject("varList", varList);
			mv.addObject("pd", paraPd);
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
		logBefore(logger, "去新增HomeworkTemplet页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("edu/homeworktemp/homeworkTemplet_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
			mv.addObject("dic_homeworkTemplet_type_list",
					Dic.dic_homework_type_list);
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
		logBefore(logger, "去修改HomeworkTemplet页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = homeworkTempletService.findById(pd); // 根据ID读取
			mv.setViewName("edu/homeworktemp/homeworkTemplet_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
			mv.addObject("dic_homeworkTemplet_type_list",
					Dic.dic_homework_type_list);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 去设置模板题目页
	 */
	@RequestMapping(value = "/goAssignHomework")
	public ModelAndView goAssignHomework(Page page) {
		logBefore(logger, "去修改HomeworkTemplet页面");
		ModelAndView mv = this.getModelAndView();
		try {
			PageData pd = this.getPageData();
			page.setPd(pd);
			List<PageData> varList = homeworkTempletService
					.goAssignHomework(page);
			mv.setViewName("edu/homeworktemp/assignhomework_inner");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);// 设置分类
			mv.addObject("dic_homeworkTemplet_type_list",
					Dic.dic_homework_type_list);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 去设置模板题目页
	 */
	@RequestMapping(value = "/beforeAssignHomework")
	public ModelAndView beforeAssignHomework(Page page) {
		logBefore(logger, "去assignhomework页面");
		ModelAndView mv = this.getModelAndView();
		try {
			PageData pd = this.getPageData();
			String TEMPLET_ID = pd.getString("TEMPLET_ID");
			if (TEMPLET_ID != null && !"".equals(TEMPLET_ID)) {
				pd.put("ID", TEMPLET_ID);
			}
			PageData temp = homeworkTempletService.findById(pd);
			String HOMEWORK_IDS = temp.getString("HOMEWORK_IDS");
			pd.put("HOMEWORK_IDS", HOMEWORK_IDS);
			page.setPd(pd);
			mv.setViewName("edu/homeworktemp/assignhomework");
			mv.addObject("pd", pd);
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
		logBefore(logger, "批量删除HomeworkTemplet");
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if (null != DATA_IDS && !"".equals(DATA_IDS)) {
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				homeworkTempletService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出HomeWork到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("标题"); // 1
			titles.add("类型"); // 2
			titles.add("创建时间"); // 3
			titles.add("创建人姓名"); // 4
			dataMap.put("titles", titles);
			List<PageData> varOList = homeworkTempletService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for (int i = 0; i < varOList.size(); i++) {
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("TITLE")); // 1
				vpd.put("var2", varOList.get(i).getString("TYPE_NAME")); // 2
				vpd.put("var3", varOList.get(i).getString("CREATE_TIME")); // 3
				vpd.put("var4", varOList.get(i).getString("CREATOR_NAME")); // 4
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
	@SuppressWarnings("unchecked")
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
