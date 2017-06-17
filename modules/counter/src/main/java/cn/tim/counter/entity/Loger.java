package cn.tim.counter.entity;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

public class Loger {
	private int id;
	private String jsessionid;
	private String ip;
	private Date stime;
	private String etime;
	private String system;
	private String browser;

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJsessionid() {
		return jsessionid;
	}

	public void setJsessionid(String jsessionid) {
		this.jsessionid = jsessionid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getStime() {
		return stime;
	}

	public void setStime(Date stime) {
		this.stime = stime;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

	public static Loger createLoger(HttpServletRequest request) {
		// 根据request创建loger对象
		Loger loger = new Loger();
		// 返回
		return loger;
	}
}
