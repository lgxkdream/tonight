package com.edu.service;

import java.util.List;
import java.util.Map;

import com.edu.Dic;

public class InitService extends BaseService{
	
	public void init(){
		List list=null;
		try {
			Dic.dic_school_list=(List<Map>)dao.findForList("dic_school.select_info", null);
			Dic.dic_job_list=(List<Map>)dao.findForList("dic_job.select_info", null);
			Dic.dic_speciality_list=(List<Map>)dao.findForList("dic_speciality.select_info", null);
			Dic.dic_std_status_list=(List<Map>)dao.findForList("dic_std_status.select_info", null);
			Dic.dic_english_list=(List<Map>)dao.findForList("dic_english.select_info", null);
			Dic.dic_pay_type_list=(List<Map>)dao.findForList("dic_pay_type.select_info", null);
			
		
		
			Dic.dic_edu_list=(List<Map>)dao.findForList("dic_edu.select_info", null);
			Dic.dic_minzu_list=(List<Map>)dao.findForList("dic_minzu.select_info", null);
			Dic.dic_subject_list=(List<Map>)dao.findForList("dic_subject.select_info", null);
			
			Dic.dic_zzmm_list = (List<Map>)dao.findForList("dic_zzmm.select_info", null);
			
			Dic.dic_stff_status_list = (List<Map>)dao.findForList("dic_stff_status.select_info", null);
			
			Dic.dic_homework_type_list=(List<Map>)dao.findForList("dic_homework_type.select_info", null);
			/*System.out.println("-----------------------------------InitService"+Dic.dic_stff_status_list);*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
	}

	
}
