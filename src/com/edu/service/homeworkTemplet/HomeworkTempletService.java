package com.edu.service.homeworkTemplet;


import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service("homeworkTempletService")
public class HomeworkTempletService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("HomeworkTempletMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("HomeworkTempletMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("HomeworkTempletMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("HomeworkTempletMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("HomeworkTempletMapper.listAll", pd);
	}
	/**
	 * 通过Templet ID列表(全部)
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listFromTemplet(Page page)throws Exception{
		return (List<PageData>)dao.findForList("HomeworkTempletMapper.dataFormTempletlistPage", page);
	}
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("HomeworkTempletMapper.findById", pd);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> goAssignHomework(Page page)throws Exception{
		return (List<PageData>)dao.findForList("HomeworkTempletMapper.goAssignHomeworklistPage", page);
	}
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("HomeworkTempletMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

