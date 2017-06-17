package cn.tim.counter.entity.jpa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Properties;

public class BaseDao {
	
	private static String strDirPath = "C:\\";
	static {
		String strPath = BaseDao.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		if (strPath.indexOf("WEB-INF/classes") > 0) {
			strDirPath = strPath.substring(0,strPath.indexOf("WEB-INF/classes"));
		}
	}
	public String getBaseDirPath() {
		return strDirPath;
	}
	/***
	 * ��ȡ���ݿ�����
	 * 
	 * @return
	 */
	public Connection getConnection() {
		Properties properties = new Properties();
		try {
			File file = new File(getBaseDirPath()
					+ "config/databasecfg.properties");
			if (file.exists() && file.isFile()) {
				properties.load(new FileInputStream(file));
			} else {
				throw new FileNotFoundException("û�������õ��ļ�!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String url = properties.getProperty("url");
		url=url+"?useUnicode=true&characterEncoding=UTF-8";
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");
		try {
			Class.forName(properties.getProperty("driver"));
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �ر����ݿ�
	 * 
	 * @param rs
	 * @param pst
	 * @param conn
	 */
	public void closeConnection(ResultSet rs, PreparedStatement pst,
			Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pst != null) {
				pst.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
