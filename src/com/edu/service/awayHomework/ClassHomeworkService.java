package com.edu.service.awayHomework;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("classHomeworkService")
public class ClassHomeworkService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
//
//	/*
//	 * 新增
//	 */
//	public void save(PageData pd) throws Exception {
//		dao.save("HomeworkMapper.save", pd);
//	}
//
//	/*
//	 * 删除
//	 */
//	public void delete(PageData pd) throws Exception {
//		dao.delete("HomeworkMapper.delete", pd);
//	}
//
//	/*
//	 * 修改
//	 */
//	public void edit(PageData pd) throws Exception {
//		dao.update("HomeworkMapper.edit", pd);
//	}

	/*
	 * 列表
	 */
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("HomeworkMapper.datalistPage",
				page);
	}

	/*
	 * 列表作业详细
	 */
	public List<PageData> HomeworkDetailed(PageData pd) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList(
				"HomeworkMapper.class_ids", pd);
		List<PageData> relist=null;
		if(list!=null && !list.isEmpty()){
			String homeworks = (String) list.get(0).get("HOMEWORKS");
			if(homeworks!=null && !"".equals(homeworks)){
				String Array_ID[] = homeworks.split(",");
				relist=(List<PageData>) dao.findForList(
						"ClassHomeworkMapper.HomeworkDetailed", Array_ID);
			}
		}

		return relist;

	}

	/*
	 * 查看作业列表
	 */
	public Map<String, Object> listHomework(Page page) throws Exception {
		Map map = new HashMap();

		List classList = (List<PageData>) dao.findForList(
				"ClassHomeworkMapper.classHomeworklistPage", page);
		for (Object obj : classList) {
			PageData tempPd = (PageData) obj;
			String class_homework_Id = tempPd.getString("ID");
			String class_id = tempPd.getString("CLASS_ID");
			PageData paraPd = new PageData();
			paraPd.put("CLASS_HOMEWORK_ID", class_homework_Id);
			// paraPd.put("CLASS_ID", class_id);
			Object count = ((List) dao.findForList(
					"ClassHomeworkMapper.show_homework", paraPd)).get(0);
			tempPd.put("COUNT", ((PageData) count).get("COUNT(*)"));

			List<PageData> nameList = ((List<PageData>) dao.findForList(
					"ClassHomeworkMapper.show_studnetname", paraPd));
			tempPd.put("NAMELIST", nameList);
		}

		map.put("classList", classList);
		return map;

	}

	/*
	 * 列表(全部)
	 */
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ClassHomeworkMapper.listAll", pd);
	}

	/*
	 * 通过id获取数据
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ClassHomeworkMapper.findById", pd);
	}

	/*
	 * 批量删除
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("ClassHomeworkMapper.deleteAll", ArrayDATA_IDS);
	}

	/*
	 * 批量删除homework
	 */
	public void deleteAlls(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("ClassHomeworkMapper.deleteAlls", ArrayDATA_IDS);
	}

	/*
	 * 查班级人数
	 */
	public PageData queryStudentCountByClass(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ClassHomeworkMapper.queryStudentCountByClass", pd);
	}

}
