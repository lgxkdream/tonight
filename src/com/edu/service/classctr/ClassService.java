package com.edu.service.classctr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.controller.base.BaseController;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("classctrService")
public class ClassService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/*
	 * 新增
	 */
	public void save(PageData pd) throws Exception {
		dao.save("ClassCtrMapper.save", pd);
	}

	/*
	 * 删除
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("ClassCtrMapper.delete", pd);
	}

	/*
	 * 修改
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("ClassCtrMapper.edit", pd);
	}

	/*
	 * 列表未分班学生
	 */
	public List<PageData> goAssign(Page page) throws Exception {

		return (List<PageData>) dao.findForList(
				"ClassCtrMapper.listStu_listPage", page);
	}

	/*
	 * 列表未分班老师
	 */
	public List<PageData> goTeacher(Page page) throws Exception {

		return (List<PageData>) dao.findForList(
				"ClassCtrMapper.listTea_listPage", page);
	}

	/*
	 * 列表当前班教师
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> goClassTeacher(Page page) throws Exception {
		List tearchList = (List<PageData>) dao.findForList(
				"ClassCtrMapper.showTeacherByClassId2", page);
		return tearchList;
	}

	/*
	 * 列表
	 */
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ClassCtrMapper.datalistPage",
				page);
	}

	public Map<String, Object> showList(Page page) throws Exception {
		Map map = new HashMap();

		List classList = (List<PageData>) dao.findForList(
				"ClassCtrMapper.showClassStdCnt_listPage", page);
		for (Object obj : classList) {
			PageData tempPd = (PageData) obj;
			String clsId = tempPd.getString("ID");
			PageData paraPd = new PageData();
			paraPd.put("CLASS_ID", clsId);
			List tearchList = (List<PageData>) dao.findForList(
					"ClassCtrMapper.showTeacherByClassId", paraPd);
			tempPd.put("tearchList", tearchList);
		}

		map.put("classList", classList);
		return map;
	}

	public Map<String, Object> showListByTchId(Page page) throws Exception {
		Map map = new HashMap();

		List classList = (List<PageData>) dao.findForList(
				"ClassCtrMapper.showClassStdCntByTchId", page);

		for (Object obj : classList) {
			PageData tempPd = (PageData) obj;
			String clsId = tempPd.getString("ID");
			PageData paraPd = new PageData();
			paraPd.put("CLASS_ID", clsId);
			List tearchList = (List<PageData>) dao.findForList(
					"ClassCtrMapper.showTeacherByClassId", paraPd);
			tempPd.put("tearchList", tearchList);
		}

		map.put("classList", classList);
		return map;

	}

	/*
	 * 列表(全部)
	 */
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ClassCtrMapper.listAll", pd);
	}

	/*
	 * 通过id获取数据
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ClassCtrMapper.findById", pd);
	}

	/*
	 * 批量删除
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("ClassCtrMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 分配学生
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void executSetStu(PageData pd) throws Exception {
		dao.update("StudentMapper.setClass", pd);
	}

	/**
	 * 分配教师
	 * 
	 * @param pd
	 * @throws Exception
	 */

	public void executSetTea(PageData pd) throws Exception {

		String clasz = pd.getString("DATA_CLASSID");
		String teacherId = pd.getString("DATA_TID");
		String[] teacherIdArray = teacherId.split(",");
		dao.delete("ClassCtrMapper.deleteFromClass", pd);

		BaseController bc = new BaseController();
		for (int i = 0; i < teacherIdArray.length; i++) {
			pd.put("ID", bc.get32UUID());
			pd.put("TEACHER_ID", teacherIdArray[i]);
			pd.put("CLASS_ID", clasz);
			dao.save("ClassCtrMapper.setClassTeacher", pd);
		}
	}
	/*
	* 从班级中批量删除学生（修改JR_CLASS、JR_CLASS_NAME为空）
	*/
	public int deleteFromClassAll(PageData pd)throws Exception{
		return (Integer)dao.update("ClassCtrMapper.updateAll", (String[])pd.get("id"));
	}
}
