package com.edu.ctrls.student;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.edu.service.student.StudentUploadExcelService;
import com.edu.service.student.StudentUploadExcelService.StudentUploadExcelException;
import com.fh.controller.base.BaseController;
import com.fh.util.Const;
import com.fh.util.FileUtil;
import com.fh.util.PageData;
import com.fh.util.PathUtil;

@Controller
@RequestMapping(value = "/studentUploadExcel")
public class StudentUploadExcelCtrl extends BaseController {
	@Resource(name = "studentUploadExcelService")
	private StudentUploadExcelService studentUploadExcelService;

	@RequestMapping(value = "/goUpload")
	public ModelAndView goUpload() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("edu/student/uploadexcel");
		return mv;
	}

	@RequestMapping(value = "/execute")
	public ModelAndView execute(
			@RequestParam(value = "excelFile", required = false) MultipartFile file,
			HttpServletRequest request) throws Exception {
		ModelAndView mv = getModelAndView();
		if (file != null && !file.isEmpty()) {
			InputStream is = file.getInputStream();
			try {
				 studentUploadExcelService.executReadExcel(is, 2, 0, 0);
				// if(pd!=null){
				// int row_num=(Integer)pd.get("row_num");
				// mv.addObject("msg","第"+(row_num+1)+"条记录发生错误，请处理该条记录，然后上传");
				// mv.setViewName("save_failse");
				// return mv;
				// }
			} catch (Exception e) {
				e.printStackTrace();
				StudentUploadExcelException sue=(StudentUploadExcelException)e;
				PageData pd=sue.getPd();
				if (pd != null) {
					int row_num = (Integer) pd.get("row_num");
					mv.addObject("msg", "第" + (row_num + 1)
							+ "条记录发生错误，请处理该条记录，然后上传");
					mv.setViewName("save_failse");
					return mv;
				}
			}
		}
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 下载模版
	 */
	@RequestMapping(value = "/downExcel")
	public void downExcel(HttpServletResponse response) throws Exception {

		// FileDownload.fileDownload(response, PathUtil.getClasspath() +
		// Const.FILEPATHFILE + "导入学生模板.xls", "导入学生模板.xls");

		byte[] data = FileUtil.toByteArray2(PathUtil.getClasspath()
				+ Const.FILEPATHFILE + "导入学生模板.xls");
		String fileName = new String("导入学生模板.xls".getBytes("UTF-8"),
				"iso-8859-1");
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ fileName + "\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream;charset=UTF-8");
		OutputStream outputStream = new BufferedOutputStream(response
				.getOutputStream());
		outputStream.write(data);
		outputStream.flush();
		outputStream.close();
		response.flushBuffer();

	}
}
