package com.edu.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import common.utils.UUIDTool;

@Service("testService")
public class TestService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public List showList(PageData pd) throws Exception{
		System.out.println("id==="+pd.getString("id"));
		System.out.println("name==="+pd.getString("name"));
		
		List list=(List)dao.findForList("test.select", pd);
		return list;
	}
	public List showListPage(Page page) throws Exception{
		
		List list=(List)dao.findForList("test.selectlistPage", page);
		return list;
	}
	public void save(PageData pd) throws Exception{
		pd.put("ID", UUIDTool.getUUID());
		int i =(Integer)dao.save("test.save", pd);
		System.out.println("i=========="+i);
	}

	public void showByIds() throws Exception{
		String[] array23423413=new String[]{"1","2","3"};
		List list=(List)dao.findForList("test.selectByIds", array23423413);
		System.out.println();
	}
	public void deleteByIds() throws Exception{
		String[] array=new String[]{"1","2","3"};
//		List list=(List)dao.findForList("test.selectByIds", array);
		PageData pd=new PageData();
		pd.put("ids", array);
		dao.delete("test.deleteByIds", pd);
		System.out.println();
	}
	
	public void updateById(PageData pd) throws Exception{
		dao.update("test.update", pd);
		
	}
	public int deleteByIds(String[] idsArray) throws Exception{
//		List list=(List)dao.findForList("test.selectByIds", array);
		PageData pd=new PageData();
		pd.put("ids", idsArray);
		int row=(Integer)dao.delete("test.deleteByIds", pd);
		return row;
	}
}
