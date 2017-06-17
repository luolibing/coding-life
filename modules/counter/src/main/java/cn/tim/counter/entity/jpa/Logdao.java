package cn.tim.counter.entity.jpa;

import cn.tim.counter.entity.Loger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Logdao extends BaseDao{
	
	/**
	 * �����������Ϣ
	 * @param loger
	 * @return
	 */
	public int updatelog(Loger loger){
		int result=0;
		//��ȡ���ݿ�����
		Connection conn=getConnection();
		//��дSQL���
		String sql="INSERT INTO log(jsessionid,ip,system,browser) VALUES (?,?,?,?)";
		//ִ��SQL���
		PreparedStatement pst=null;
		try {
			pst=conn.prepareStatement(sql);
			pst.setString(1, loger.getJsessionid());
			pst.setString(2, loger.getIp());
			pst.setString(3, loger.getSystem());
			pst.setString(4, loger.getBrowser());
			result=pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
}
