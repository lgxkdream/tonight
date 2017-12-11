package common.been;

import java.util.HashMap;

/**
 * @author mym
 * jstl只能从map对象中取值
 */
public class UserBean extends HashMap<String,Object>{
	private String id;
	private String email;
	private String realName;
	private String createTime;
	private String modifyTime;
	private int loginNum;
	private int status;
	private int level;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		put("id", id);
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		put("email", email);
		this.email = email;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		put("status", status);
		this.status = status;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		put("level", level);
		this.level = level;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		put("realName", realName);
		this.realName = realName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		put("createTime", createTime);
		this.createTime = createTime;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		put("modifyTime", modifyTime);
		this.modifyTime = modifyTime;
	}
	public int getLoginNum() {
		return loginNum;
	}
	public void setLoginNum(int loginNum) {
		put("loginNum", loginNum);
		this.loginNum = loginNum;
	}
	
}
