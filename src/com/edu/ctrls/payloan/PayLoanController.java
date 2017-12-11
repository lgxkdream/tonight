package com.edu.ctrls.payloan;

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

import net.sf.json.JSONObject;

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

import com.edu.Dic;
import com.edu.service.payloan.PayLoanService;
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

import common.been.JsonHashMap;
import common.utils.DateTool;
import common.utils.PathTools;

/** 
 * 类名称：PayLoanController
 * 创建人：FH 
 * 创建时间：2015-06-19
 */
@Controller
@RequestMapping(value="/payloan")
public class PayLoanController extends BaseController {
	
	@Resource(name="payloanService")
	private PayLoanService payloanService;
	
	@Resource(name="studentService")
	private StudentService studentService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增PayLoan");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PAYLOAN_ID", this.get32UUID());	//主键
		pd.put("ID", "");	//主键
		payloanService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除PayLoan");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			payloanService.delete(pd);
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
		logBefore(logger, "修改PayLoan");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		payloanService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表PayLoan");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String STD_NAME=pd.getString("STD_NAME");
		String shenhe=pd.getString("shenhe");
//		String CREATE_TIME_START=pd.getString("CREATE_TIME_START");
//		String CREATE_TIME_END=pd.getString("CREATE_TIME_END");
		
		PageData paycashPd=new PageData();
		if(STD_NAME!=null && !"".equals(STD_NAME)){
			paycashPd.put("STD_NAME", "%"+STD_NAME+"%");
		}
		if(shenhe!=null && !"".equals(shenhe)){
			if("0".equals(shenhe)){
				paycashPd.put("VERIFY", "0");
			}else if("1".equals(shenhe)){
				paycashPd.put("VERIFYS", "1,2");
			}
		}
//		if(CREATE_TIME_START!=null && !"".equals(CREATE_TIME_START)){
//			paycashPd.put("CREATE_TIME_START", CREATE_TIME_START+" 00:00:00");
//		}
//		if(CREATE_TIME_END!=null && !"".equals(CREATE_TIME_END)){
//			paycashPd.put("CREATE_TIME_END", CREATE_TIME_END+" 23:59:59");
//		}
		
		try{
			page.setPd(paycashPd);
			List<PageData>	varList = payloanService.list(page);	//列出PayLoan列表
			mv.setViewName("edu/payloan/payloan_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	/**
	 * 根据学生ID获取贷款列表
	 */
	@RequestMapping(value="/listByStdId")
	public ModelAndView listByStdId(Page page){
		logBefore(logger, "列表PayLoan");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String stdId=pd.getString("STD_ID");
//		if(CREATE_TIME_START!=null && !"".equals(CREATE_TIME_START)){
//			paycashPd.put("CREATE_TIME_START", CREATE_TIME_START+" 00:00:00");
//		}
//		if(CREATE_TIME_END!=null && !"".equals(CREATE_TIME_END)){
//			paycashPd.put("CREATE_TIME_END", CREATE_TIME_END+" 23:59:59");
//		}
		
		try{
			PageData stdPdPara=new PageData();
			stdPdPara.put("ID", stdId);
			PageData stdPd=studentService.findById(stdPdPara);
			page.setPd(pd);
			List<PageData>	varList = payloanService.list(page);	//列出PayLoan列表
			mv.setViewName("edu/payloan/listByStdId");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject("stdPd", stdPd);
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
		logBefore(logger, "去新增PayLoan页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("edu/payloan/payloan_edit");
			mv.addObject("msg", "payLoan");
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
		logBefore(logger, "去修改PayLoan页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = payloanService.findById(pd);	//根据ID读取
			mv.setViewName("edu/payloan/payloan_edit");
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
		logBefore(logger, "批量删除PayLoan");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				payloanService.deleteAll(ArrayDATA_IDS);
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
	
	@RequestMapping(value="/goPayLoan")
	public ModelAndView goPayLoan(){
		logBefore(logger, "去贷款");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		PageData resultPd = new PageData();
		try {
			PageData stuPd=new PageData();
			stuPd.put("STD_ID", pd.get("STD_ID"));
			List<PageData> list=payloanService.listAll(stuPd);
			if(list!=null && !list.isEmpty()){
				PageData result=list.get(0);
				
				resultPd.putAll(result);
				resultPd.putAll(pd);//后放表单传入的pd，是考虑到，学员姓名等信息有可能修改，将最新的信息返回到页面中
			}else{
				resultPd.putAll(pd);
			}
			
			mv.setViewName("edu/payloan/payloan_edit");
			mv.addObject("msg", "payLoan");
			mv.addObject("pd", resultPd);
			
			//数据字典
			mv.addObject("dic_pay_type_list", Dic.dic_pay_type_list);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	@RequestMapping(value="/payLoan")
	public ModelAndView payLoan(@RequestParam(value = "fujian", required = false) MultipartFile file,HttpServletRequest request, ModelMap model) throws Exception{
		logBefore(logger, "申请贷款");

		PageData pd = new PageData(request);
		String jspID=(String)pd.get("ID");
		
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		
		
		List<String> suffixList=new ArrayList();
		
		List<String> notSuffixList=new ArrayList();
		notSuffixList.add("EXE");
		
		String filePath = null;
		String ffile = DateUtil.getDays();
		String fileName = null;
		String path=null;
		ModelAndView mv = this.getModelAndView();
		String originalFilename=null;
		if (null != file && !file.isEmpty()) {
			originalFilename = file.getOriginalFilename();
			String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1)
					.toUpperCase();
			if (suffixList!=null && !suffixList.isEmpty() && !suffixList.contains(suffix)) {
				Map pd2 = new HashMap();
				pd2.put("RESULT", 0);
				
				StringBuilder suffixStr=new StringBuilder();
				for(String suffixTemp:suffixList){
					suffixStr.append(suffixTemp);
					suffixStr.append(",");
				}
				String suffixMsg=suffixStr.substring(0, suffixStr.length()-1);
				pd2.put("MSG", "请上传格式为【" + suffixMsg + "】的附件！");
				return mv;
			}
			if (notSuffixList!=null && !notSuffixList.isEmpty() && notSuffixList.contains(suffix)) {
				Map pd2 = new HashMap();
				pd2.put("RESULT", 0);
				
				StringBuilder notSuffixStr=new StringBuilder();
				for(String suffixTemp:notSuffixList){
					notSuffixStr.append(suffixTemp);
					notSuffixStr.append(",");
				}
				String notSuffixMsg=notSuffixStr.substring(0, notSuffixStr.length()-1);
				
				
				pd2.put("MSG", "请不要上传格式为【" + notSuffixMsg + "】的附件！");
				return mv;
			}
			filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile; // 文件上传路径
			fileName = FileUpload.fileUp(file, filePath, this.get32UUID()); // 执行上传

			path=Const.FILEPATHIMG+ffile + "/" + fileName;
		} else {
			//如果文件为空
			if(jspID==null || "".equals(jspID)){//id为空，说明是添加，提示必须添加文件
//				Map pd2 = new HashMap();
//				pd2.put("RESULT", 0);
//				pd2.put("MSG", "上传失败");
				return null;
			}
		}

//		Map pd = new HashMap();
//		pd.put("RESULT", 1);
//		pd.put("NAME", fileName); // 文件名
//		pd.put("PATH", Const.FILEPATHIMG+ffile + "/" + fileName);
		
		
		
		if(jspID==null || "".equals(jspID)){
			pd.put("ID", this.get32UUID());	//主键
			
//			pd.put("STD_ID", "");	//学生ID
//			pd.put("STD_NAME", "");	//学生姓名
//			pd.put("STD_NUM", "");	//学号
//			pd.put("NUM", "");	//收据号

			pd.put("FILE", path);
			pd.put("FILENAME", originalFilename);
			pd.put("VERIFY", 0);
			pd.put("CREATOR", user.getUSER_ID());
			pd.put("CREATOR_NAME", user.getUSERNAME());
			pd.put("CREATE_TIME", DateUtil.getTime());
			payloanService.save(pd);
		}else{
			pd.put("FILE", path);
			pd.put("FILENAME", originalFilename);
			payloanService.edit(pd);
		}
		
//		PageData stdPd=new PageData();
//		stdPd.put("STATUS", 20);
//		stdPd.put("STATUS_NAME", 20);
//		stdPd.put("ID", pd.get("STD_ID"));
//		studentService.edit(stdPd);
		
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	@RequestMapping("download")    
    public ResponseEntity<byte[]> download() {   
    	logBefore(logger, "下载附件");
    	PageData pd = new PageData();
		pd = this.getPageData();
		try {
			List<PageData> list=payloanService.listAll(pd);
			if(list!=null && !list.isEmpty()){
				PageData resultPd=list.get(0);
				String path=(String)resultPd.get("FILE");
				String filename=(String)resultPd.get("FILENAME");
				
				File webRootPath=PathTools.getWebRootPath();
//				String path="D:\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\springMVC\\WEB-INF\\upload\\图片10（定价后）.xlsx";  
				File file=new File(webRootPath,path);  
				HttpHeaders headers = new HttpHeaders();    
				String fileName=new String(filename.getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题  
				headers.setContentDispositionFormData("attachment", fileName);   
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
				return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
						headers, HttpStatus.CREATED);    
			}
		}catch(Exception e){
			logger.error(e.toString(), e);
		}
		return null;
    } 
    //@ResponseBody
    //@RequestMapping(value="shenhe")
    @Deprecated
    public Object shenhe(){
    	logBefore(logger, "审核通过");
    	JsonHashMap jhm=new JsonHashMap();
    	
    	Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		
    	PageData pd = new PageData();
		pd = this.getPageData();
		
		String id=pd.getString("ID");
		String stdId=pd.getString("STD_ID");
		try {
			PageData plPd=new PageData();
			plPd.put("ID", id);
			plPd.put("VERIFY", 1);
			plPd.put("VERIFY_NAME", user.getUSERNAME());
			plPd.put("VERIFY_ID", user.getUSER_ID());
			plPd.put("VERIFY_TIME", DateTool.GetDateTime());
			
			payloanService.executShenhe(plPd);
			
		}catch(Exception e){
			logger.error(e.toString(), e);
		}
    	
    	return JSONObject.fromObject(jhm);
    }
    @RequestMapping(value="verify")
    public Object verify(){
    	logBefore(logger, "审核通过");

    	ModelAndView mv = this.getModelAndView();
    	
    	Subject currentUser = SecurityUtils.getSubject();  
    	Session session = currentUser.getSession();
    	User user = (User)session.getAttribute(Const.SESSION_USER);
    	
    	PageData pd = new PageData();
    	pd = this.getPageData();
    	
    	String id=pd.getString("ID");
    	String verify=pd.getString("VERIFY");
    	String remark=pd.getString("REMARK");
    	try {
    		PageData plPd=new PageData();
    		plPd.put("ID", id);
    		plPd.put("VERIFY", verify);
    		plPd.put("REMARK", remark);
    		plPd.put("VERIFY_NAME", user.getUSERNAME());
    		plPd.put("VERIFY_ID", user.getUSER_ID());
    		plPd.put("VERIFY_TIME", DateTool.GetDateTime());
    		
    		payloanService.executShenhe(plPd);
    		
    		mv.addObject("msg","success");
    		mv.setViewName("save_result");
    	}catch(Exception e){
    		logger.error(e.toString(), e);
    	}
    	
    	return mv;
    }
    @RequestMapping(value="goVerify")
    public Object goVerify(){
    	logBefore(logger, "去审核页面");
    	PageData pd = new PageData();
		pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		
		try{
			mv.setViewName("edu/payloan/verify");
			mv.addObject("msg", "verify");
			mv.addObject("pd", pd);
		}catch(Exception e){
			logger.error(e.toString(), e);
		}
		
		return mv;
    }
    
    @RequestMapping(value="showById")
    public Object showById(){
    	logBefore(logger, "查看页面");
    	PageData pd = new PageData();
		pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		try{
			PageData resultPd=payloanService.findById(pd);
			mv.setViewName("edu/payloan/show");
			mv.addObject("msg", "verify");
			mv.addObject("pd", pd);
			mv.addObject("resultPd", resultPd);
		}catch(Exception e){
			logger.error(e.toString(), e);
		}
		
		return mv;
    }
	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出PayLoan到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String STD_NAME=pd.getString("STD_NAME");
		String shenhe=pd.getString("shenhe");
//		String CREATE_TIME_START=pd.getString("CREATE_TIME_START");
//		String CREATE_TIME_END=pd.getString("CREATE_TIME_END");
		
		PageData paycashPd=new PageData();
		if(STD_NAME!=null && !"".equals(STD_NAME)){
			paycashPd.put("STD_NAME", "%"+STD_NAME+"%");
		}
		if(shenhe!=null && !"".equals(shenhe)){
			if("0".equals(shenhe)){
				paycashPd.put("VERIFY", "0");
			}else if("1".equals(shenhe)){
				paycashPd.put("VERIFYS", "1,2");
			}
		}
//		if(CREATE_TIME_START!=null && !"".equals(CREATE_TIME_START)){
//			paycashPd.put("CREATE_TIME_START", CREATE_TIME_START+" 00:00:00");
//		}
//		if(CREATE_TIME_END!=null && !"".equals(CREATE_TIME_END)){
//			paycashPd.put("CREATE_TIME_END", CREATE_TIME_END+" 23:59:59");
//		}
		
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("学员姓名");	//3
			titles.add("审核金额");	//5
			titles.add("贷款金额");	//6
			titles.add("创建人");	//9
			titles.add("创建时间");	//10
			titles.add("审核是否通过");	//11
			titles.add("审核人姓名");	//12
			titles.add("审核时间");	//14
			dataMap.put("titles", titles);
			List<PageData> varOList = payloanService.listAll(paycashPd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("STD_NAME"));	//3
				vpd.put("var2", varOList.get(i).get("VERIFY_MONEY").toString());	//5
				vpd.put("var3", varOList.get(i).get("LOAN_MONEY").toString());	//6
				vpd.put("var4", varOList.get(i).getString("CREATOR_NAME"));	//9
				vpd.put("var5", varOList.get(i).getString("CREATE_TIME"));	//10
				int verify=(Integer)varOList.get(i).get("VERIFY");
				String verifyStr="";
				if(verify==0){
					verifyStr="未审核";
				}else if(verify==1){
					verifyStr="审核通过";
				}else if(verify==2){
					verifyStr="审核拒绝";					
				}
				vpd.put("var6", verifyStr);	//11
				vpd.put("var7", varOList.get(i).getString("VERIFY_NAME"));	//12
				vpd.put("var8", varOList.get(i).getString("VERIFY_TIME"));	//14
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
