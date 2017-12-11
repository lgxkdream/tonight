package com.edu.ctrls.paycash;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

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
import com.edu.service.paycash.PayCashService;
import com.edu.service.student.StudentService;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/** 
 * 类名称：PayCashController
 * 创建人：FH 
 * 创建时间：2015-06-18
 */
@Controller
@RequestMapping(value="/paycash")
public class PayCashController extends BaseController {
	
	@Resource(name="paycashService")
	private PayCashService paycashService;
	
	@Resource(name="studentService")
	private StudentService studentService;
	
	/**
	 * 判断单号是否重复
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/numIsRepeated")
	public Object numIsRepeated() throws Exception{
		logBefore(logger, "判断单号是否重复");
		PageData pd = new PageData();
		pd = this.getPageData();
		int c=paycashService.numIsRepeated(pd);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("count", c);
		
	
		return JSONObject.fromObject(map);
	}
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增PayCash");

		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ID", this.get32UUID());	//主键
//		pd.put("STD_ID", "");	//学生ID
//		pd.put("STD_NAME", "");	//学生姓名
//		pd.put("STD_NUM", "");	//学号
//		pd.put("NUM", "");	//收据号
		pd.put("CREATOR", user.getUSER_ID());
		pd.put("CREATOR_NAME", user.getUSERNAME());
		pd.put("CREATE_TIME", DateUtil.getTime());
		paycashService.save(pd);
		
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	@RequestMapping(value="/payCash")
	public ModelAndView payCash() throws Exception{
		logBefore(logger, "报名");

		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ID", this.get32UUID());	//主键
//			pd.put("STD_ID", "");	//学生ID
//			pd.put("STD_NAME", "");	//学生姓名
//			pd.put("STD_NUM", "");	//学号
//			pd.put("NUM", "");	//收据号
		pd.put("CREATOR", user.getUSER_ID());
		pd.put("CREATOR_NAME", user.getUSERNAME());
		pd.put("CREATE_TIME", DateUtil.getTime());
		paycashService.executPayCash(pd);
		
		
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除PayCash");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			paycashService.delete(pd);
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
		logBefore(logger, "修改PayCash");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		paycashService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表PayCash");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String stdId=pd.getString("STD_ID");
			String STD_NAME=pd.getString("STD_NAME");
			String num=pd.getString("NUM");
			String CREATE_TIME_START=pd.getString("CREATE_TIME_START");
			String CREATE_TIME_END=pd.getString("CREATE_TIME_END");
			String PAY_TYPE=pd.getString("PAY_TYPE");
			
			PageData paycashPd=new PageData();
			if(stdId!=null && !"".equals(stdId)){
				paycashPd.put("STD_ID", stdId);
			}
			if(STD_NAME!=null && !"".equals(STD_NAME)){
				paycashPd.put("STD_NAME", "%"+STD_NAME+"%");
			}
			if(num!=null && !"".equals(num)){
				paycashPd.put("NUM", "%"+num+"%");
			}
			if(CREATE_TIME_START!=null && !"".equals(CREATE_TIME_START)){
				paycashPd.put("CREATE_TIME_START", CREATE_TIME_START+" 00:00:00");
			}
			if(CREATE_TIME_END!=null && !"".equals(CREATE_TIME_END)){
				paycashPd.put("CREATE_TIME_END", CREATE_TIME_END+" 23:59:59");
			}
			if(PAY_TYPE!=null && !"".equals(PAY_TYPE)){
				paycashPd.put("PAY_TYPE", PAY_TYPE);
			}
			page.setPd(paycashPd);
			List<PageData>	varList = paycashService.list(page);	//列出PayCash列表
			
			PageData stdResult=null;
			if(stdId!=null && !"".equals(stdId)){
				PageData stdPd=new PageData();
				stdPd.put("ID",pd.get("STD_ID"));
				stdResult=studentService.findById(stdPd);
			}
			mv.setViewName("edu/paycash/paycash_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject("stdPd", stdResult);
			mv.addObject("dic_pay_type_list", Dic.dic_pay_type_list);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	@RequestMapping(value="/listByStdId")
	public ModelAndView listByStdId(Page page){
		logBefore(logger, "列表PayCash");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String stdId=pd.getString("STD_ID");
			String STD_NAME=pd.getString("STD_NAME");
			String num=pd.getString("NUM");
			String CREATE_TIME_START=pd.getString("CREATE_TIME_START");
			String CREATE_TIME_END=pd.getString("CREATE_TIME_END");
			String PAY_TYPE=pd.getString("PAY_TYPE");
			
			PageData paycashPd=new PageData();
			if(stdId!=null && !"".equals(stdId)){
				paycashPd.put("STD_ID", stdId);
			}
			if(STD_NAME!=null && !"".equals(STD_NAME)){
				paycashPd.put("STD_NAME", "%"+STD_NAME+"%");
			}
			if(num!=null && !"".equals(num)){
				paycashPd.put("NUM", "%"+num+"%");
			}
			if(CREATE_TIME_START!=null && !"".equals(CREATE_TIME_START)){
				paycashPd.put("CREATE_TIME_START", CREATE_TIME_START+" 00:00:00");
			}
			if(CREATE_TIME_END!=null && !"".equals(CREATE_TIME_END)){
				paycashPd.put("CREATE_TIME_END", CREATE_TIME_END+" 23:59:59");
			}
			if(PAY_TYPE!=null && !"".equals(PAY_TYPE)){
				paycashPd.put("PAY_TYPE", PAY_TYPE);
			}
			page.setPd(paycashPd);
			List<PageData>	varList = paycashService.list(page);	//列出PayCash列表
			
			PageData stdResult=null;
			if(stdId!=null && !"".equals(stdId)){
				PageData stdPd=new PageData();
				stdPd.put("ID",pd.get("STD_ID"));
				stdResult=studentService.findById(stdPd);
			}
			mv.setViewName("edu/paycash/listByStdId");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject("stdPd", stdResult);
			mv.addObject("dic_pay_type_list", Dic.dic_pay_type_list);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
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
		logBefore(logger, "去新增PayCash页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("edu/paycash/paycash_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
			
			//数据字典
			mv.addObject("dic_pay_type_list", Dic.dic_pay_type_list);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	@RequestMapping(value="/goPayCash")
	public ModelAndView goPayCash(){
		logBefore(logger, "去缴费");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("edu/paycash/paycash_edit");
			mv.addObject("msg", "payCash");
			mv.addObject("pd", pd);
			
			//数据字典
			mv.addObject("dic_pay_type_list", Dic.dic_pay_type_list);
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
		logBefore(logger, "去修改PayCash页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = paycashService.findById(pd);	//根据ID读取
			mv.setViewName("edu/paycash/paycash_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
			
			//数据字典
			mv.addObject("dic_pay_type_list", Dic.dic_pay_type_list);
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
		logBefore(logger, "批量删除PayCash");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				paycashService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出PayCash到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String stdId=pd.getString("STD_ID");
		String STD_NAME=pd.getString("STD_NAME");
		String num=pd.getString("NUM");
		String CREATE_TIME_START=pd.getString("CREATE_TIME_START");
		String CREATE_TIME_END=pd.getString("CREATE_TIME_END");
		String PAY_TYPE=pd.getString("PAY_TYPE");
		
		PageData paycashPd=new PageData();
		if(STD_NAME!=null && !"".equals(STD_NAME)){
			paycashPd.put("NAME", "%"+STD_NAME+"%");
		}
		if(num!=null && !"".equals(num)){
			paycashPd.put("NUM", "%"+num+"%");
		}
		if(CREATE_TIME_START!=null && !"".equals(CREATE_TIME_START)){
			paycashPd.put("CREATE_TIME_START", CREATE_TIME_START+" 00:00:00");
		}
		if(CREATE_TIME_END!=null && !"".equals(CREATE_TIME_END)){
			paycashPd.put("CREATE_TIME_END", CREATE_TIME_END+" 23:59:59");
		}
		if(PAY_TYPE!=null && !"".equals(PAY_TYPE)){
			paycashPd.put("PAY_TYPE", PAY_TYPE);
		}
		
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("学生姓名");	//2
			titles.add("收据号");	//4
			titles.add("收费类型");	//6
			titles.add("金额");	//7
			titles.add("创建时间");	//8
			titles.add("创建人");	//9
			dataMap.put("titles", titles);
			List<PageData> varOList = paycashService.listAll(paycashPd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("STD_NAME"));	//2
				vpd.put("var2", varOList.get(i).getString("NUM"));	//4
				vpd.put("var3", varOList.get(i).getString("PAY_NAME"));	//6
				vpd.put("var4", varOList.get(i).get("MONEY").toString());	//7
				vpd.put("var5", varOList.get(i).getString("CREATE_TIME"));	//8
				vpd.put("var6", varOList.get(i).getString("CREATOR_NAME"));	//10
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
