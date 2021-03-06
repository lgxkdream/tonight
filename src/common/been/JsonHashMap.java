package common.been;

import java.util.HashMap;

/**
 * @author mym
 *
 * result:成功为1，失败为0
 * error:当result为0时，一定有error，表示失败原因
 * message:当result为1时，可能会有message，返回的信息
 * 默认为成功
 */
public class JsonHashMap extends HashMap<String, Object>{
	public static final int RESULT_ERROR=0;
	public static final int RESULT_SUCCESS=1;
	
	private static final String RESULT="result";
	private static final String ERROR="error";
	private static final String MESSAGE="message";
	
	public JsonHashMap(){
		putResult(RESULT_SUCCESS);
	}
	/**
	 * 0：发生错误
	 * 1：成功
	 */
	public int getResult() {
		return (Integer)get(RESULT);
	}
	public void putResult(int result) {
		put(RESULT, result);
		//当result为0时，一定有error，表示失败原因
		if(result==RESULT_ERROR){
			put(ERROR, "");
		}
	}
	public String getError() {
		return (String)get(ERROR);
	}
	public void putError(String error) {
		put(ERROR, error);
	}
	public String getMessage() {
		return (String)get(MESSAGE);
	}
	public void putMessage(String message) {
		put(MESSAGE, message);
	}
}
