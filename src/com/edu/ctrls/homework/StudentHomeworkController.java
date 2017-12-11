package com.edu.ctrls.homework;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.entity.Page;
import com.fh.util.DateUtil;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.edu.service.classctr.ClassService;
import com.edu.service.homework.StudentHomeworkService;

@Controller
@RequestMapping(value = "/studentHomework")
public class StudentHomeworkController extends BaseController {

	@Resource(name = "studentHomeworkService")
	private StudentHomeworkService studentHomeworkService;
	@Resource(name = "classctrService")
	private ClassService classctrService;

	@RequestMapping(value = "/check")
	public ModelAndView check(Page page) throws UnsupportedEncodingException {
		System.out.println("*********************");
		logBefore(logger, "作业列表");
		PageData pd = new PageData();
		ModelAndView mv = this.getModelAndView();
		pd = this.getPageData();

		try {
			PageData classPd = classctrService.findById(pd);
			mv.addObject("classPd", classPd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mv.addObject("pd", pd);
		mv.setViewName("edu/homework/outer");
		return mv;

	}

	/**
	 * 查看作业模板列表
	 */
	@RequestMapping(value = "/HomeworkTemplates")
	public ModelAndView HomeworkTemplates(Page page) {
		logBefore(logger, "作业模板列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		List<PageData> varList2 = null;
		try {
			pd = this.getPageData();
			page.setPd(pd);

			List<PageData> varList = studentHomeworkService
					.templetList(new PageData());
			mv.addObject("varList", varList);

			String temId = pd.getString("ID");
			if (temId == null || "".equals(temId)) {
				if (varList != null && !varList.isEmpty()) {
					String HOMEWORK_IDS = varList.get(0).getString(
							"HOMEWORK_IDS");
					String[] homework_ids = HOMEWORK_IDS.split(",");
					varList2 = studentHomeworkService
							.findByHomeworkIds(homework_ids);
					mv.addObject("varList2", varList2);
					mv.addObject("homework_ids", homework_ids);
				}
			} else {

				for (int i = 0; i < varList.size(); i++) {
					if (varList.get(i).getString("ID").equals(temId)) {
						String HOMEWORK_IDS = varList.get(i).getString(
								"HOMEWORK_IDS");
						String[] homework_ids = HOMEWORK_IDS.split(",");
						varList2 = studentHomeworkService
								.findByHomeworkIds(homework_ids);
						mv.addObject("varList2", varList2);
						mv.addObject("homework_ids", homework_ids);
					}
				}
				mv.addObject("pd", pd);
				mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		mv.setViewName("edu/homework/inner");
		return mv;
	}

	@RequestMapping(value = "/saveHomework")
	public ModelAndView saveHomework() throws Exception {
		logBefore(logger, "新增Homework");
		ModelAndView mv = this.getModelAndView();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		PageData pd = new PageData();
		PageData parapd = new PageData();
		pd = this.getPageData();
		String JR_CLASS_NAME = pd.getString("CLASS_NAME");
		String JR_CLASS = pd.getString("CLASS_ID");

		String HOMEWORK_IDS = pd.getString("homework_ids");
		if (HOMEWORK_IDS != null && !"".equals(HOMEWORK_IDS)) {
			parapd.put("HOMEWORKS", HOMEWORK_IDS);
		}
		parapd.put("TITLE", pd.getString("TITLE"));
		parapd.put("END_TIME", pd.getString("END_TIME"));
		parapd.put("ID", get32UUID());
		parapd.put("CREATOR", user.getUSER_ID());
		parapd.put("CREATOR_NAME", user.getUSERNAME());
		parapd.put("CREATE_TIME", DateUtil.getTime());
		parapd.put("CLASS_ID", JR_CLASS);
		parapd.put("CLASS_NAME", JR_CLASS_NAME);
		parapd.put("HOMEWORK_CREATE_TIME", DateUtil.getTime());

		studentHomeworkService.saveHomeworkToStudent(parapd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/* ===============================权限================================== */
	public Map<String, String> getHC() {
		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
	}

	/* ===============================权限================================== */

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,
				true));
	}
}
