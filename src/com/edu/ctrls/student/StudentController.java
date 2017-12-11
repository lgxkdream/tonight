package com.edu.ctrls.student;

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.edu.service.student.StudentService;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.FileUpload;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.Watermark;

/** 
 * 类名称：StudentController
 * 创建人：MYM
 * 创建时间：2015-06-11
 */
@Controller
@RequestMapping(value="/student")
public class StudentController extends BaseController {
	
	@Resource(name="studentService")
	private StudentService studentService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Student");
		String ffile = DateUtil.getDays();
		String fileName = "";
//		if (null != file && !file.isEmpty()) {
//			String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile;		//文件上传路径
//			fileName = FileUpload.fileUp(file, filePath, this.get32UUID());				//执行上传
//		}else{
//			System.out.println("上传失败");
//		}
		
		ModelAndView mv = this.getModelAndView();
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		PageData pd = new PageData();
		pd = this.getPageData();
		if("".equals(pd.get("ENGLISH"))){
			pd.put("ENGLISH", null);
		};
		pd.put("ID", this.get32UUID());	//主键
		pd.put("CREATOR", user.getUSER_ID());
		pd.put("CREATOR_NAME", user.getUSERNAME());
		pd.put("CREATE_TIME", DateUtil.getTime());
		pd.put("pic", fileName);
		studentService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Student");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			studentService.delete(pd);
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
		logBefore(logger, "修改Student");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		if("".equals(pd.get("ENGLISH"))){
			pd.put("ENGLISH", null);
		};
		if("".equals(pd.get("YIXIANG"))){
			pd.put("YIXIANG", null);
		}
		studentService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 登记列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/dengjiList")
	public ModelAndView dengjiList(Page page){
		logBefore(logger, "列表Student");
		
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			
			PageData paraPd=new PageData();
			paraPd.putAll(pd);
			
			String name=paraPd.getString("NAME");
			if(name!=null && !"".equals(name)){
				paraPd.put("NAME", name);
			}
			String CREATE_TIME_START=paraPd.getString("CREATE_TIME_START");
			if(CREATE_TIME_START!=null && !"".equals(CREATE_TIME_START)){
				paraPd.put("CREATE_TIME_START", CREATE_TIME_START+" 00:00:00");
				
			}
			String CREATE_TIME_END=paraPd.getString("CREATE_TIME_END");
			if(CREATE_TIME_END!=null && !"".equals(CREATE_TIME_END)){
				paraPd.put("CREATE_TIME_END", CREATE_TIME_END+" 23:59:59");
				
			}
			paraPd.put("STATUS", 10);
			paraPd.put("CREATOR", user.getUSER_ID());
			paraPd.put("ORDERBY", "YIXIANG");
			page.setPd(paraPd);
			List<PageData>	varList = studentService.list(page);	//列出Student列表
			mv.setViewName("edu/student/dengji_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject("dic_school_list", Dic.dic_school_list);
			mv.addObject("dic_speciality_list", Dic.dic_speciality_list);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Student");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		PageData paraPd=new PageData();
		paraPd.putAll(pd);
		
//		String name=paraPd.getString("NAME");
//		if(name!=null && !"".equals(name)){
//			paraPd.put("NAME", name);
//		}
		String CREATE_TIME_START=paraPd.getString("CREATE_TIME_START");
		if(CREATE_TIME_START!=null && !"".equals(CREATE_TIME_START)){
			paraPd.put("CREATE_TIME_START", CREATE_TIME_START+" 00:00:00");
			
		}
		String CREATE_TIME_END=paraPd.getString("CREATE_TIME_END");
		if(CREATE_TIME_END!=null && !"".equals(CREATE_TIME_END)){
			paraPd.put("CREATE_TIME_END", CREATE_TIME_END+" 23:59:59");
			
		}
//		paraPd.put("STATUS", 20);
		paraPd.put("ORDERBY", "JR_CLASS");
		try{
			page.setPd(paraPd);
			List<PageData>	varList = studentService.list(page);	//列出Student列表
			mv.setViewName("edu/student/student_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject("dic_school_list", Dic.dic_school_list);
			mv.addObject("dic_speciality_list", Dic.dic_speciality_list);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
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
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增Student页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		List schoolYearList=new ArrayList();
		int year=Calendar.getInstance().get(Calendar.YEAR);
		for(int i=-3;i<2;i++){
			schoolYearList.add(year+i);
		}
		try {
			mv.setViewName("edu/student/student_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
			//数据字典
			mv.addObject("dic_english_list", Dic.dic_english_list);
			mv.addObject("dic_school_list", Dic.dic_school_list);
			mv.addObject("dic_speciality_list", Dic.dic_speciality_list);
			mv.addObject("dic_std_status_list", Dic.dic_std_status_list);
			//入选年份
			mv.addObject("schoolYearList", schoolYearList);
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
		logBefore(logger, "去修改Student页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		List schoolYearList=new ArrayList();
		int year=Calendar.getInstance().get(Calendar.YEAR);
		for(int i=-3;i<2;i++){
			schoolYearList.add(year+i);
		}
		try {
			pd = studentService.findById(pd);	//根据ID读取
			mv.setViewName("edu/student/student_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
			
			//数据字典
			mv.addObject("dic_english_list", Dic.dic_english_list);
			mv.addObject("dic_school_list", Dic.dic_school_list);
			mv.addObject("dic_speciality_list", Dic.dic_speciality_list);
			mv.addObject("dic_std_status_list", Dic.dic_std_status_list);
			//入选年份
			mv.addObject("schoolYearList", schoolYearList);
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
		logBefore(logger, "批量删除Student");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", 0);
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				studentService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
				map.put("result", 1);
			}else{
				pd.put("msg", "no");
				map.put("result", 0);
			}
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			map.put("result", 0);
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
		logBefore(logger, "导出Student到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		PageData paraPd=new PageData();
		paraPd.putAll(pd);
		
		String name=paraPd.getString("NAME");
		if(name!=null && !"".equals(name)){
			paraPd.put("NAME", name);
		}
		String CREATE_TIME_START=paraPd.getString("CREATE_TIME_START");
		if(CREATE_TIME_START!=null && !"".equals(CREATE_TIME_START)){
			paraPd.put("CREATE_TIME_START", CREATE_TIME_START+" 00:00:00");
			
		}
		String CREATE_TIME_END=paraPd.getString("CREATE_TIME_END");
		if(CREATE_TIME_END!=null && !"".equals(CREATE_TIME_END)){
			paraPd.put("CREATE_TIME_END", CREATE_TIME_END+" 23:59:59");
			
		}
		
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
//			titles.add("id");	//1
			titles.add("学号");	//2
			titles.add("姓名");	//3
			titles.add("性别");	//4
			titles.add("生日");	//5
			titles.add("民族");	//7
			titles.add("状态");	//9
			titles.add("身份证号");	//10
			titles.add("学校");	//12
			titles.add("入学时间");	//13
			titles.add("班级号");	//14
			titles.add("寝室号");	//15
			titles.add("吉软班级");	//17
			titles.add("英语级别");	//19
			titles.add("电话");	//20
			titles.add("签到卡号");	//21
			titles.add("家长");	//22
			titles.add("家长联系方式");	//23
			titles.add("紧急联系人");	//24
			titles.add("紧急联系人电话");	//25
			titles.add("导员");	//26
			titles.add("导员电话");	//27
			titles.add("意向");	//28
			titles.add("专业");	//31
			titles.add("电邮");	//32
			titles.add("qq");	//33
			titles.add("备注");	//34
//			titles.add("创建人ID");	//36
//			titles.add("创建人姓名");	//37
//			titles.add("创建时间");	//38
			dataMap.put("titles", titles);
			List<PageData> varOList = studentService.listAll(paraPd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
//				vpd.put("var1", varOList.get(i).getString("ID"));	//1
				vpd.put("var1", varOList.get(i).getString("STD_NUM"));	//2
				vpd.put("var2", varOList.get(i).getString("NAME"));	//3
				int gender=(Integer)varOList.get(i).get("GENDER");
				if(gender==1){
					vpd.put("var3", "男");	//4
				}else if(gender==2){
					vpd.put("var3", "女");	//4
				}
				vpd.put("var4", varOList.get(i).getString("BIRTHDAY"));	//5
				vpd.put("var5", varOList.get(i).getString("MINZU_NAME"));	//7
				vpd.put("var6", varOList.get(i).getString("STATUS_NAME"));	//9
				vpd.put("var7", varOList.get(i).getString("SFZH"));	//10
				vpd.put("var8", varOList.get(i).getString("SCHOOL_NAME"));	//12
				vpd.put("var9", varOList.get(i).getString("SCHOOL_YEAR"));	//13
				vpd.put("var10", varOList.get(i).getString("CLASS"));	//14
				vpd.put("var11", varOList.get(i).getString("ROOM_NUM"));	//15
				vpd.put("var12", varOList.get(i).getString("JR_CLASS_NAME"));	//17
				vpd.put("var13", varOList.get(i).getString("ENGLISH_NAME"));	//19
				vpd.put("var14", varOList.get(i).getString("PHONE"));	//20
				vpd.put("var15", varOList.get(i).getString("QIANDAOKAHAO"));	//21
				vpd.put("var16", varOList.get(i).getString("PARENT"));	//22
				vpd.put("var17", varOList.get(i).getString("PARENT_PHONE"));	//23
				vpd.put("var18", varOList.get(i).getString("JINJI_LXR"));	//24
				vpd.put("var19", varOList.get(i).getString("JINJI_DH"));	//25
				vpd.put("var20", varOList.get(i).getString("DAOYUAN"));	//26
				vpd.put("var21", varOList.get(i).getString("DAOYUAN_DH"));	//27
				String yixiang="";
				if(varOList.get(i).get("YIXIANG")!=null){
					yixiang=varOList.get(i).get("YIXIANG").toString();
				}
				vpd.put("var22",yixiang );	//28
				vpd.put("var23", varOList.get(i).getString("SPECIALITY_NAME"));	//31
				vpd.put("var24", varOList.get(i).getString("EMAIL"));	//32
				vpd.put("var25", varOList.get(i).getString("QQ"));	//33
				vpd.put("var26", varOList.get(i).getString("REMARK"));	//34
//				vpd.put("var36", varOList.get(i).getString("CREATOR"));	//36
//				vpd.put("var37", varOList.get(i).getString("CREATOR_NAME"));	//37
//				vpd.put("var38", varOList.get(i).getString("CREATE_TIME"));	//38
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
	@ResponseBody
    @RequestMapping(value = "/setClass",produces = "text/html;charset=UTF-8")
	public ModelAndView setClass() throws Exception{
		PageData pd = new PageData();		
		ModelAndView mv = this.getModelAndView();
		try {
			pd = this.getPageData();
			String studentIds=pd.getString("studentIds");
			String clasz=pd.getString("JR_CLASS");
			String[] claszArray=clasz.split("_");
			String classId=claszArray[0];
			String className=claszArray[1];
			if(studentIds!=null && !"".equals(studentIds)){
				String[] studentIdArray=studentIds.split(",");
				pd.put("studentIdArray", studentIdArray);
				pd.put("JR_CLASS", classId);
				pd.put("JR_CLASS_NAME", className);
				studentService.setClass(pd);
				
				mv.addObject("msg","success");
				mv.setViewName("save_result");
			}
		}catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	@RequestMapping(value="/showAllClass")
	public ModelAndView showAllClass(){
		logBefore(logger, "显示所有班级");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			List<PageData>	varList = studentService.showAllClass();	
			mv.setViewName("edu/student/class_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
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
