package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class AlarmDAO {
	private static AlarmDAO instance = null;
	private JDBCUtil jdbc = JDBCUtil.getInstance();
//	static List<Object> param = new ArrayList<Object>();

	private AlarmDAO() {}

	public static AlarmDAO getInstance() {
		if (instance == null)
			instance = new AlarmDAO();
		return instance;
	}
	
	public int update(String sql){
		return jdbc.update(sql);	
	}
	
	public Map<String, Object> getCheckInfo(String uid) {
		String sql = "SELECT * FROM U_MEMBER WHERE U_ID = (SELECT M_USERID FROM U_MEMBER WHERE U_ID = ?)";
		List<Object> param = new ArrayList<Object>();
		param.add(uid);

		return jdbc.selectOne(sql, param);
	}
}
