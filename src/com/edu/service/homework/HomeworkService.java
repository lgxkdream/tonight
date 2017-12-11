package com.edu.service.homework;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("homeworkService")
public class HomeworkService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 新增
	 */
	public void save(PageData pd) throws Exception {
		dao.save("HomeworkMapper.save", pd);
	}

	/**
	 * 删除
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("HomeworkMapper.delete", pd);
	}

	/**
	 * 修改
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("HomeworkMapper.edit", pd);
	}

	/**
	 * 列表
	 */
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("HomeworkMapper.datalistPage",
				page);
	}

	/**
	 * 列表(全部)
	 */
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("HomeworkMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("HomeworkMapper.findById", pd);
	}

	/**
	 * 批量删除
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("HomeworkMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**
	 * 删除题目附件
	 */
	public void deleteFile(PageData pd) throws Exception {
		dao.update("HomeworkMapper.deleteFile", pd);
	}
	
	/**
	 * 删除答案附件
	 */
	public void deleteAnswer(PageData pd) throws Exception {
		dao.update("HomeworkMapper.deleteAnswer", pd);
	}

}
