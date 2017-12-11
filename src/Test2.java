import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Test2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String timeStr="2014-06-12 00:00:00";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date timeDate=sdf.parse(timeStr);
			Calendar timeCln=Calendar.getInstance();
			timeCln.setTime(timeDate);
			Calendar currentCln=Calendar.getInstance();
			int workAge=currentCln.get(Calendar.YEAR)-timeCln.get(Calendar.YEAR);
			System.out.println(workAge);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
