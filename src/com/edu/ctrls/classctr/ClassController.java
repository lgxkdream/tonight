package com.edu.ctrls.classctr;

import java.io.PrintWriter;
import java.net.URLDecoder;
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

import com.edu.service.classctr.ClassService;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.DateUtil;

/** 
 * 类名称：ClassCtrController
 * 创建人： gaobo
 * 创建时间：2015-06-12
 */
@Controller
@RequestMapping(value="/class")
public class ClassController extends BaseController {
	
	@Resource(name="classctrService")
	private ClassService classctrService;
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增ClassCtr");
	    SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
	    User user = (User)session.getAttribute(Const.SESSION_USER);
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ID", this.get32UUID());	//主键
		pd.put("CREATE_TIME", sdfTime.format(new Date()));	//创建时间
		pd.put("CREATOR", user.getUSER_ID());
		pd.put("CREATOR_NAME", user.getUSERNAME());
		classctrService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除ClassCtr");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			classctrService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改ClassCtr");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		classctrService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表ClassCtr");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
	       String KEYW = pd.getString("keyword");
			
			if(null != KEYW && !"".equals(KEYW)){
				KEYW = KEYW.trim();
				pd.put("KEYW", KEYW);
			}
			
			page.setPd(pd);
			List<PageData>	varList = classctrService.list(page);	//列出ClassCtr列表
			mv.setViewName("edu/classctr/classctr_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	@RequestMapping(value="/showList")
	public ModelAndView showList(Page page){
		logBefore(logger, "列表ClassCtr");
		   
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
	       String KEYW = pd.getString("keyword");
			
			if(null != KEYW && !"".equals(KEYW)){
				KEYW = KEYW.trim();
				pd.put("KEYW", KEYW);
			}
			page.setPd(pd);
//			List<PageData>	varList = classctrService.list(page);	//列出ClassCtr列表
			Map map = classctrService.showList(page);	//列出ClassCtr列表
			List<PageData> varList=(List<PageData>)map.get("classList");
			
			mv.setViewName("edu/classctr/showList");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	@RequestMapping(value="/showListByTchId")
	public ModelAndView showListByTchId(Page page){
		logBefore(logger, "列表ClassCtr");
		
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
	    User user = (User)session.getAttribute(Const.SESSION_USER);
	    
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
	       String KEYW = pd.getString("keyword");
			
			if(null != KEYW && !"".equals(KEYW)){
				KEYW = KEYW.trim();
				pd.put("KEYW", KEYW);
			}
			pd.put("TEACHER_ID", user.getUSER_ID());
			page.setPd(pd);
//			List<PageData>	varList = classctrService.list(page);	//列出ClassCtr列表
			Map map = classctrService.showListByTchId(page);	//列出ClassCtr列表
			List<PageData> varList=(List<PageData>)map.get("classList");
			mv.setViewName("edu/classctr/listByTchId");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	/**
	 * 未分班学生列表
	 */
	@RequestMapping(value="/goAssign")
	public ModelAndView goAssign(Page page){
		logBefore(logger, "列表未分配班级的学生");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String filter = (String)pd.get("Filter");
			if(filter == null || " ".equals(filter)){
				pd.put("Filter", "UnAssigned");
			}
			
			pd.put("STATUS", 20);
			page.setPd(pd);
			List<PageData>	varList = classctrService.goAssign(page);	//未分班学生列表
			mv.setViewName("edu/classctr/classctr_assign");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
			mv.addObject("param_class", URLDecoder.decode(pd.getString("Str_id"),"UTF-8"));
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	/**
	 * 可选教师列表
	 */
	@RequestMapping(value="/goAssignTea")
	public ModelAndView goAssignTea(Page page){
		logBefore(logger, "教师列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("JOB", 20);
			page.setPd(pd);
			List<PageData>	varList = classctrService.goTeacher(page);	//任课教师列表
			
			List<PageData>	teaList = classctrService.goClassTeacher(page);	//当前班级教师列表
			
//			String class_id = URLDecoder.decode(pd.getString("Str_id"),"UTF-8");
//			System.out.println(class_id);
			
			mv.setViewName("edu/classctr/classctr_teacher");
			mv.addObject("varList", varList);
			mv.addObject("teaList", teaList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
			mv.addObject("param_class",pd.getString("Str_id"));
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增ClassCtr页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("edu/classctr/classctr_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改ClassCtr页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = classctrService.findById(pd);	//根据ID读取
			mv.setViewName("edu/classctr/classctr_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除ClassCtr");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				classctrService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
			}else{
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
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出ClassCtr到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("班级名称");	//1
			titles.add("备注");	//2
			dataMap.put("titles", titles);
			List<PageData> varOList = classctrService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("NAME"));	//1
				vpd.put("var2", varOList.get(i).getString("REMARK"));	//2
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	/**
	 * 分配学生
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/setStu")
	public ModelAndView setStu() throws Exception{
		PageData pd = new PageData();		
		ModelAndView mv = this.getModelAndView();
		try {
			pd = this.getPageData();
			String studentIds=pd.getString("DATA_IDS");
			String clasz=pd.getString("DATA_CLASS");
			String[] claszArray=clasz.split("_");
			String classId=claszArray[0];
			String className=claszArray[1];
			if(studentIds!=null && !"".equals(studentIds)){
				String[] studentIdArray=studentIds.split(",");
				pd.put("studentIdArray", studentIdArray);
				pd.put("JR_CLASS", classId);
				pd.put("JR_CLASS_NAME", className);
				classctrService.executSetStu(pd);
				mv.addObject("msg","success");
				mv.setViewName("save_result");
			}
		}catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
    /**
	 * 分配教师
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/setTea")
	public ModelAndView setTea() throws Exception{
		PageData pd = new PageData();		
		ModelAndView mv = this.getModelAndView();
		SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
	    User user = (User)session.getAttribute(Const.SESSION_USER);
		try {
			pd = this.getPageData();
//			String teacherId=pd.getString("DATA_TID");
//			String clasz=pd.getString("DATA_CLASSID");
//			String[] teacherIdArray=teacherId.split(",");
			
//			pd.put("teacherIdArray", teacherId);
//				pd.put("ID", this.get32UUID());	//主键
				pd.put("CREATE_TIME", sdfTime.format(new Date()));	//创建时间
//				pd.put("TEACHER_ID", teacherId);
//				pd.put("CLASS_ID", clasz);
				pd.put("CREATOR", user.getUSERNAME());
				pd.put("CREATOR_NAME", user.getUSER_ID());
				
				//pd.put("REMARK", "system");
				
				classctrService.executSetTea(pd);
		}catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
    
    /**
	* 从班级中批量删除学生（修改JR_CLASS、JR_CLASS_NAME为空）
	*/
	@ResponseBody
	@RequestMapping(value="/updateAll")
	public Object updateAll() throws Exception{
		logBefore(logger, "修改Student");
		PageData pd = new PageData();
		pd = this.getPageData();

		String DATA_IDS=(String)pd.get("DATA_IDS");
		if(DATA_IDS!=null){
			String[] array=DATA_IDS.split(",");
			pd.put("id", array);
		}
		classctrService.deleteFromClassAll(pd);
		Map map=new HashMap();
		map.put("result", 1);
		
		return AppUtil.returnObject(pd, map);
	}
	/* ===============================权限================================== */
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
