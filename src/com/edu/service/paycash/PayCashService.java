package com.edu.service.paycash;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edu.Dic;
import com.edu.service.student.StudentService;
import com.edu.service.sysconfig.SysConfigService;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import common.been.ResultHashMap;


@Service("paycashService")
public class PayCashService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Resource(name="studentService")
	private StudentService studentService;
	
	
	/**
	 * 登记缴费
	 * @param pd
	 * @throws Exception
	 */
	public void executPayCash(PageData pd) throws Exception{
		
		dao.save("PayCashMapper.save", pd);
		//分配学生号
		String stdNum=studentService.buildStdNum();
		
		PageData stdPd=new PageData();
		stdPd.put("STATUS", Dic.STD_STATUS_ZHENGSHI);
		stdPd.put("STATUS_NAME", Dic.getNameById(Dic.dic_std_status_list, Dic.STD_STATUS_ZHENGSHI));
		stdPd.put("ID", pd.get("STD_ID"));
		stdPd.put("STD_NUM", stdNum);
		studentService.edit(stdPd);
		
		//注册登录用户
		stdPd=new PageData();
		stdPd.put("ID", pd.get("STD_ID"));
		PageData resultPd=studentService.findById(stdPd);
		studentService.reg(resultPd);
	}
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("PayCashMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("PayCashMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("PayCashMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PayCashMapper.datalistPage", page);
	}
	public int numIsRepeated(PageData pd) throws Exception{
		ResultHashMap rhm=new ResultHashMap();
		int c=(Integer)dao.findForObject("PayCashMapper.select_count", pd);
		return c;
	}
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PayCashMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PayCashMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("PayCashMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

