package com.edu.ctrls;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.edu.service.TestService;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Controller
@RequestMapping(value="/test")
public class TestAction extends BaseController{

	@Resource(name="testService")
	private TestService testService;
	
	@RequestMapping(value="show")
	public String show(Model m){
		System.out.println("成功接收请求！");
		m.addAttribute("name", "小明");
		return "test2";
	}
	@RequestMapping(value="/show2")
	public ModelAndView show2(@RequestParam(value="id") String id,ModelAndView mv){
		System.out.println("-------------"+id);
		mv.setViewName("test2");
		mv.addObject("id", id);
		return mv;
	}
	@RequestMapping(value="/show3")
	public ModelAndView show3(@RequestParam(value="id") String id){
		System.out.println("show3-------------"+id);
		ModelAndView mv=new ModelAndView();
		mv.setViewName("test2");
		mv.addObject("id", id);
		return mv;
	}
	//最终不改版
	@RequestMapping(value="/show4")
	public ModelAndView show4(){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("test2");
		return mv;
	}
	@RequestMapping(value="/show5")
	public ModelAndView show5(){
		ModelAndView mv=new ModelAndView();
		PageData pd=getPageData();
		try {
			List list=testService.showList(pd);
			//testService.showByIds();
			mv.addObject("ls", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mv.addObject("name", "小明");
		System.out.println("id="+pd.getString("id"));
		System.out.println("name="+pd.getString("name"));
		mv.setViewName("test2");
		return mv;
	}
	@RequestMapping(value="/showListPage")
	public ModelAndView showListPage(Page page){
		ModelAndView mv=new ModelAndView();
		PageData pd=getPageData();
		page.setPd(pd);
		try {
			List list=testService.showListPage(page);
			//testService.showByIds();
			mv.addObject("ls", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mv.addObject("name", "小明");
		System.out.println("id="+pd.getString("id"));
		System.out.println("name="+pd.getString("name"));
		mv.setViewName("test2");
		return mv;
	}
	@RequestMapping(value="save")
	public ModelAndView save(){
		PageData pd=getPageData();
		try {
			testService.save(pd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView mv=new ModelAndView();
		mv.setViewName("save_result");
		return mv;
	}
	
	@RequestMapping(value="deleteByIds")
	public ModelAndView deleteByIds(){
		try {
			testService.deleteByIds();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView mv=new ModelAndView();
		mv.setViewName("ok");
		return mv;
	}
	@RequestMapping(value="goAdd")
	public ModelAndView goAdd(){
		ModelAndView mv=new ModelAndView();
		mv.addObject("action","save");
		mv.setViewName("test_edit");
		return mv;
	}
	@RequestMapping(value="goEdit")
	public ModelAndView goEdit(){
		ModelAndView mv=new ModelAndView();
		PageData pd=getPageData();
		PageData resultPd=null;
		try {
			List list=testService.showList(pd);
			if(list!=null && !list.isEmpty()){
				resultPd=(PageData)list.get(0);
				mv.addObject("pd", resultPd);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mv.addObject("action","edit");
		mv.setViewName("test_edit");
		return mv;
	}
	@RequestMapping(value="edit")
	public ModelAndView edit(){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("save_result");
		PageData pd=getPageData();
		try {
			testService.updateById(pd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	@ResponseBody
	@RequestMapping(value="deleteAll")
	public Object deleteAll(){
		PageData pd=getPageData();
		String ids=pd.getString("DATA_IDS");
		if(ids!=null && !ids.isEmpty()){
			String[] idsArray=ids.split(",");
			try {
				int row=testService.deleteByIds(idsArray);
				System.out.println();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List<PageData> pdList = new ArrayList<PageData>();
		pdList.add(pd);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list", pdList);
		return map;
	}
	
	
	
	@RequestMapping("/upload"  )  
    public String upload(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException {
		String name=request.getParameter("NAME");
        //创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            MultipartFile mf=multiRequest.getFile("FILE");
            MultipartFile mf2=multiRequest.getFile("FILE2");
            System.out.println(mf.getOriginalFilename());
            System.out.println(mf2.getOriginalFilename());
            //取得request中的所有文件名  
//            Iterator<String> iter = multiRequest.getFileNames();  
//            while(iter.hasNext()){  
//                //记录上传过程起始时的时间，用来计算上传时间  
//                int pre = (int) System.currentTimeMillis();  
//                //取得上传文件  
//                MultipartFile file = multiRequest.getFile(iter.next());  
//                if(file != null){  
//                    //取得当前上传文件的文件名称  
//                    String myFileName = file.getOriginalFilename();  
//                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
//                    if(myFileName.trim() !=""){  
//                        System.out.println(myFileName);  
//                        //重命名上传后的文件名  
//                        String fileName = "demoUpload" + file.getOriginalFilename();  
//                        //定义上传路径  
//                        String path = "H:/" + fileName;  
//                        File localFile = new File(path);  
//                        file.transferTo(localFile);  
//                    }  
//                }  
//                //记录上传该文件后的时间  
//                int finaltime = (int) System.currentTimeMillis();  
//                System.out.println(finaltime - pre);  
//            }  
              
        }  
        return "/success";  
    }  
	@RequestMapping("/upload2"  ) 
	 public Object upload2(@RequestParam(value = "FILE", required = false) MultipartFile file,@RequestParam(value = "FILE2", required = false) MultipartFile file2, HttpServletRequest request, ModelMap model) {  
		 String name=request.getParameter("NAME");
		 String filename=file.getOriginalFilename();
		 String filename2=file2.getOriginalFilename();
		 System.out.println(filename);
		 System.out.println(filename2);
		 return "";
	 }
}
