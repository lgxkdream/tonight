package com.edu.service.payloan;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edu.Dic;
import com.edu.service.student.StudentService;
import com.edu.service.sysconfig.SysConfigService;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service("payloanService")
public class PayLoanService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Resource(name="studentService")
	private StudentService studentService;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("PayLoanMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("PayLoanMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("PayLoanMapper.edit", pd);
	}
	/**
	 * 执行审核
	 * @param pd
	 * @throws Exception
	 */
	public void executShenhe(PageData pd)throws Exception{
		dao.update("PayLoanMapper.edit", pd);
		String id=pd.getString("ID");
		String verify=pd.getString("VERIFY");
		if("1".equals(verify)){
			PageData paraPd=new PageData();
			paraPd.put("ID", id);
			PageData resultPd=(PageData)dao.findForObject("PayLoanMapper.findById", paraPd);
			String stdId=(String)resultPd.get("STD_ID");
			
			String stdNum=studentService.buildStdNum();;
			
			PageData stdPd=new PageData();
			stdPd.put("ID", stdId);
			stdPd.put("STATUS",Dic.STD_STATUS_ZHENGSHI);
			stdPd.put("STATUS_NAME", Dic.getById(Dic.dic_std_status_list, Dic.STD_STATUS_ZHENGSHI).get("NAME"));
			stdPd.put("STD_NUM", stdNum);
			studentService.edit(stdPd);
			
			//注册登录用户
			stdPd=new PageData();
			stdPd.put("ID", stdId);
			resultPd=studentService.findById(stdPd);
			studentService.reg(resultPd);
		}
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PayLoanMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PayLoanMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PayLoanMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("PayLoanMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

