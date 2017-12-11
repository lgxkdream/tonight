package com.edu.service.student;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

import com.edu.service.BaseService;
import com.edu.service.sysconfig.SysConfigService;
import com.fh.entity.Page;
import com.fh.service.system.user.UserService;
import com.fh.util.PageData;
import common.utils.DateTool;
import common.utils.UUIDTool;


@Service("studentService")
public class StudentService extends BaseService{

	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="sysConfigService")
	private SysConfigService sysConfigService;
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("StudentMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("StudentMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("StudentMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("StudentMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StudentMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StudentMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("StudentMapper.deleteAll", ArrayDATA_IDS);
	}
	public void setClass(PageData pd) throws Exception{
		dao.update("StudentMapper.setClass", pd);
	}
	public List<PageData> showAllClass()throws Exception{
		return (List<PageData>)dao.findForList("ClassCtrMapper.listAll",null);
	}
	
	/**
	 * 查询name是max_std_num
	 * @return
	 * @throws Exception
	 */
	public int getMaxStdNum() throws Exception{
		PageData pd=new PageData();
		pd.put("name", "max_std_num");
		List<PageData> list=(List<PageData>)dao.findForList("sys_config.select_info", pd);
		int i=0;
		if(list!=null && !list.isEmpty()){
			Object o=list.get(0).get("value_int");
			if(o!=null){
				i=Integer.parseInt(o.toString());
			}
		}
		return i;
	}
	public int addMaxStdNum() throws Exception{
		String NAME="max_std_num";
		PageData pd=new PageData();
		pd.put("NAME", NAME);
		List<PageData> list=(List<PageData>)dao.findForList("sys_config.select_info", pd);
		int i=0;
		if(list!=null && !list.isEmpty()){
			PageData resultPd=list.get(0);
			String id=(String)resultPd.get("ID");
			Object valueObject=resultPd.get("VALUE_INT");
			if(valueObject!=null){
				i=Integer.parseInt(valueObject.toString());
				i=i+1;
				pd=new PageData();
				pd.put("ID", id);
				pd.put("VALUE_INT", i);
				sysConfigService.edit(pd);
				
			}else{
				i=1;
				pd=new PageData();
				pd.put("ID", UUIDTool.getUUID());
				pd.put("NAME", NAME);
				pd.put("VALUE_INT", i);
				sysConfigService.save(pd);
			}
		}else{
			i=1;
			pd=new PageData();
			pd.put("ID", UUIDTool.getUUID());
			pd.put("NAME", NAME);
			pd.put("VALUE_INT", i);
			sysConfigService.save(pd);
//			dao.save("sys_config.insert", pd);
		}
		return i;
	}
	/**
	 * 生成学号
	 * @return
	 * @throws Exception
	 */
	public String buildStdNum() throws Exception{
		int stdNum=addMaxStdNum();
		String year=DateTool.getDate(new Date(), "yy");
		return year+stdNum;
	}
	
	/**
	 * 注册成为用户
	 * @throws Exception 
	 */
	public boolean reg(PageData pd) throws Exception{
		String std_num=(String)pd.get("STD_NUM");
		String password="123456";
		String role_id="fcdd5ebf316a481fbc2de3523abbe397";
		
		PageData paraPd=new PageData();
		paraPd.put("USER_ID", UUIDTool.getUUID());	//ID
		paraPd.put("USERNAME",std_num );	//登录名
		paraPd.put("NAME", pd.get("NAME"));			//用户名
		paraPd.put("RIGHTS", "");					//权限
		paraPd.put("ROLE_ID", role_id);				//角色
		paraPd.put("LAST_LOGIN", "");				//最后登录时间
		paraPd.put("IP", "");						//IP
		paraPd.put("STATUS", "0");					//状态
		paraPd.put("SKIN", "default");				//默认皮肤
		paraPd.put("EMAIL", pd.get("EMAIL"));		//email
		paraPd.put("PHONE", pd.get("PHONE"));		//phone
		paraPd.put("NUMBER", std_num);		//phone
		
		paraPd.put("PASSWORD", new SimpleHash("SHA-1", std_num, password).toString());
		
		if(null == userService.findByUId(paraPd)){
			userService.saveU(paraPd);
			return true;
		}else{
			return false;
		}
	}
}

