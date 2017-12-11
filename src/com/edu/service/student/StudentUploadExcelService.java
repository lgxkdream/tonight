package com.edu.service.student;

import java.io.InputStream;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.edu.Dic;
import com.edu.service.BaseService;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import common.utils.UUIDTool;
@Service("studentUploadExcelService")
public class StudentUploadExcelService extends BaseService {
	@Resource(name="studentService")
	private StudentService studentService;
	
	public PageData executReadExcel(InputStream is, int startrow, int startcol, int sheetnum) throws Exception{
//		List<Object> varList = new ArrayList<Object>();

		try {
			HSSFWorkbook wb = new HSSFWorkbook(is);
			HSSFSheet sheet = wb.getSheetAt(sheetnum); 					//sheet 从0开始
			int rowNum = sheet.getLastRowNum() + 1; 					//取得最后一行的行号

			for (int i = startrow; i < rowNum; i++) {					//行循环开始
				
				PageData varpd = new PageData();
				HSSFRow row = sheet.getRow(i); 							//行
				int cellNum = row.getLastCellNum(); 					//每行的最后一个单元格位置

				for (int j = startcol; j < cellNum; j++) {				//列循环开始
					
					HSSFCell cell = row.getCell(Short.parseShort(j + ""));
					String cellValue = null;
					if (null != cell) {
						switch (cell.getCellType()) { 					// 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
						case 0:
							cellValue = String.valueOf((int) cell.getNumericCellValue());
							break;
						case 1:
							cellValue = cell.getStringCellValue();
							break;
						case 2:
							cellValue = cell.getNumericCellValue() + "";
							// cellValue = String.valueOf(cell.getDateCellValue());
							break;
						case 3:
							cellValue = "";
							break;
						case 4:
							cellValue = String.valueOf(cell.getBooleanCellValue());
							break;
						case 5:
							cellValue = String.valueOf(cell.getErrorCellValue());
							break;
						}
					} else {
						cellValue = "";
					}
					
					varpd.put("var"+j, cellValue);
					
				}
//				varList.add(varpd);
				try{
					save(varpd);
				}catch(Exception e){
					
					varpd.put("row_num", i);
					throw new StudentUploadExcelException(varpd);
				}
			}

		} catch (Exception e) {
			throw e;
		}
		
		return null;
	}
	
	private void save(PageData pd) throws Exception{
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		
//		String std_num=studentService.buildStdNum();
		String name=(String)pd.get("var0");
		String gender=(String)pd.get("var1");
		String sfzh=(String)pd.get("var2");
		String school=(String)pd.get("var3");
		Object school_id=Dic.getIdByText(Dic.dic_school_list, school);
		String school_year=(String)pd.get("var4");
		String speciality=(String)pd.get("var5");
		Object speciality_id=Dic.getIdByText(Dic.dic_speciality_list, speciality);
		String clasz=(String)pd.get("var6");
		String room_num=(String)pd.get("var7");
		String phone=(String)pd.get("var8");
		String email=(String)pd.get("var9");
		String qq=(String)pd.get("var10");
		
		
		PageData savePd=new PageData();
		savePd.put("ID", UUIDTool.getUUID());
//		savePd.put("STD_NUM", std_num);
		savePd.put("NAME", name);
//		Dic.getIdByText(Dic., id)(list, id)
		if("男".equals(gender)){
			savePd.put("GENDER", 1);
		}else{
			savePd.put("GENDER", 2);
		}
		savePd.put("STATUS", Dic.STD_STATUS_YUBEI);
		savePd.put("STATUS_NAME", Dic.getNameById(Dic.dic_std_status_list, Dic.STD_STATUS_YUBEI));
		savePd.put("SCHOOL", school_id);
		savePd.put("SCHOOL_NAME", school);
		savePd.put("SCHOOL_YEAR", school_year);
		savePd.put("CLASS", clasz);
		savePd.put("ROOM_NUM", room_num);
		savePd.put("PHONE", phone);
		savePd.put("SPECIALITY", speciality_id);
		savePd.put("SPECIALITY_NAME", speciality);
		savePd.put("EMAIL", email);
		savePd.put("QQ", qq);
		savePd.put("SFZH", sfzh);
		savePd.put("DELETED", 0);
		savePd.put("CREATOR", user.getUSER_ID());
		savePd.put("CREATOR_NAME", user.getUSERNAME());
		savePd.put("CREATE_TIME", DateUtil.getTime());
		studentService.save(savePd);
		
	}
	public class StudentUploadExcelException extends Exception{
		private PageData pd=null;
		public PageData getPd() {
			return pd;
		}
		public void setPd(PageData pd) {
			this.pd = pd;
		}
		public StudentUploadExcelException(PageData pd){
			this.pd=pd;
		}
	}
}
