package dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.JDBCUtil;

public class MatchDAO {
	
	private static MatchDAO instance = null;
	private JDBCUtil jdbc = null;

	private MatchDAO() {
		// JDBCUtil 인스턴스 생성
		jdbc = JDBCUtil.getInstance();
		// Oracle JDBC 드라이버 로드
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static MatchDAO getInstance() {
		if (instance == null)
			instance = new MatchDAO();
		return instance;
	}
	
//	public int 
	//지역 상관없을때
	public List<Map<String, Object>> getMatchUsers(String ugender, String udrk, String usmk) {
		String sql = "SELECT * FROM U_MEMBER WHERE U_GENDER = ? AND U_DRK = ? AND U_SMK = ?";
		List<Object> param = new ArrayList<>();
		param.add(ugender);
		param.add(udrk);
		param.add(usmk);

		return jdbc.selectList(sql, param);
	}
	//지역상관있을때
	public List<Map<String, Object>> getMatchUsers(String ugender, String uloc, String udrk, String usmk) {
		String sql = "SELECT * FROM U_MEMBER WHERE U_GENDER = ? AND U_LOC = ? AND U_DRK = ? AND U_SMK = ?";
		List<Object> param = new ArrayList<>();
		param.add(ugender);
		param.add(uloc);
		param.add(udrk);
		param.add(usmk);

		return jdbc.selectList(sql, param);
	}
//	내 M_USER
	public int saveMyData(String targetId, String state) {
		Map<String, Object> member = Controller.sessionStorage;

		String sql = "UPDATE U_MEMBER SET M_USERID = '" + targetId + "', M_STATE = '"+state+"' WHERE U_ID = '"
				+ member.get("U_ID") + "' ";
		return jdbc.update(sql);
	}
//	상대 M_USER
	public int saveUserData(String targetId, String state) {
		Map<String, Object> member = Controller.sessionStorage;

		String sql = "UPDATE U_MEMBER SET M_USERID = '" + member.get("U_ID") + "', M_STATE = '"+state+"' WHERE U_ID = '"
				+ targetId + "' ";
		return jdbc.update(sql);
	}
	
	public int update(String sql){
		return jdbc.update(sql);
	}
//	
//	public int saveMatchHis(Object uid, Object mid, String state) {
//		
////		Date now = new Date();
////		SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
////		String today = formatter.format(now);
//		
//		String sql = "INSERT INTO M_HISTORY (R_NO, U_ID, M_USERID, M_STATE, R_DATE) "
//				+ "VALUES(FN_R_NO(TO_CHAR(sysdate, 'YYMMDD')), '" + uid
//				+ "', '" + mid + "', '" + state + "', TO_CHAR(SYSDATE, 'YYMMDD') ";
//
//		return jdbc.update(sql);
//	}
}
