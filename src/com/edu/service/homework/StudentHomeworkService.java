package com.edu.service.homework;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

@Service("studentHomeworkService")
public class StudentHomeworkService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	*查询列表模板
	*/
	public List<PageData> templetList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("HomeworkTempletMapper.findById", pd);
	}
	/*
	*查询题目
	*/
	public List<PageData> findByHomeworkIds(String[] homework_ids)throws Exception{
		return (List<PageData>)dao.findForList("HomeworkMapper.selectByIds", homework_ids);
	}
	/*
	* 新增
	*/
	public void saveHomeworkToStudent(PageData pd)throws Exception{
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		
		dao.save("HomeworkMapper.saveHomeworkToClass",pd);
		String classHomeworkId=pd.getString("ID");
		
		
		PageData paraPd=new PageData();
		paraPd.put("JR_CLASS", pd.get("CLASS_ID"));
		List<PageData> stuList=(List<PageData>)dao.findForList("StudentMapper.listAll", paraPd);
		
		String JR_CLASS_NAME=pd.getString("CLASS_NAME");
		String JR_CLASS=pd.getString("CLASS_ID");
		String HOMEWORKS=pd.getString("HOMEWORKS");
		pd.put("CLASS_ID", JR_CLASS);
		pd.put("CLASS_NAME", JR_CLASS_NAME);
		pd.put("HOMEWORKS",HOMEWORKS);
		pd.put("END_TIME", pd.getString("END_TIME"));
		
		for(int i=0;i<stuList.size();i++){
			String STUDENT_ID=stuList.get(i).getString("ID");
			String STUDENT_NAME=stuList.get(i).getString("NAME");
			PageData tempPd=new PageData();
			tempPd.put("ID", UuidUtil.get32UUID());
			tempPd.put("CH_ID",classHomeworkId);
			tempPd.put("STUDENT_ID", STUDENT_ID);
			tempPd.put("STUDENT_NAME", STUDENT_NAME);
			tempPd.put("TITLE", pd.getString("TITLE"));
			tempPd.put("END_TIME", pd.getString("END_TIME"));
			tempPd.put("HOMEWORKS",HOMEWORKS);
			tempPd.put("HOMEWORKS2",pd.getString("HOMEWORKS2"));
			tempPd.put("CREATOR", user.getUSER_ID());
			tempPd.put("CREATOR_NAME", user.getUSERNAME());
			tempPd.put("CREATE_TIME", DateUtil.getTime());
			tempPd.put("STATUS", 0);
			tempPd.put("CLASS_ID", JR_CLASS);
			tempPd.put("CLASS_NAME", JR_CLASS_NAME);
			
			tempPd.put("HOMEWORK_CREATE_TIME", DateUtil.getTime());
			dao.save("HomeworkMapper.saveHomeworkToStudent", tempPd);
		}
	}
}
