package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class LoginDAO {
	private static LoginDAO instance = null;
	private JDBCUtil jdbc = JDBCUtil.getInstance();

	private LoginDAO() {}

	public static LoginDAO getInstance() {
		if (instance == null)
			instance = new LoginDAO();
		return instance;
	}

	public Map<String, Object> select(String id) {
		String sql = "SELECT * FROM U_MEMBER WHERE U_ID = ?";
		List<Object> param = new ArrayList<Object>();
		param.add(id);

		return jdbc.selectOne(sql, param);
	}

	public Map<String, Object> login(String id, String pw) {
		String sql = "SELECT * FROM U_MEMBER WHERE U_ID = ? AND U_PW = ?";
		List<Object> param = new ArrayList<Object>();
		param.add(id);
		param.add(pw);

		return jdbc.selectOne(sql, param);
	}

	public Map<String, Object> getUserId(String name, String phoneNumber) {
		String sql = "SELECT * FROM U_MEMBER WHERE U_NAME = ? AND U_PHONE = ? ";
		List<Object> param = new ArrayList<Object>();
		param.add(name);
		param.add(phoneNumber);

		return jdbc.selectOne(sql, param);
	}

	public Map<String, Object> getUserPw(String id, String name, String phoneNumber) {
		String sql = "SELECT * FROM U_MEMBER WHERE U_ID = ? AND U_NAME = ? AND U_PHONE = ? ";
		List<Object> param = new ArrayList<Object>();
		param.add(id);
		param.add(name);
		param.add(phoneNumber);

		return jdbc.selectOne(sql, param);

	}
}
