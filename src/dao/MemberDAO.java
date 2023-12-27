package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class MemberDAO {
	private static MemberDAO instance = null;

	private MemberDAO() {
	}

	public static MemberDAO getInstance() {
		if (instance == null)
			instance = new MemberDAO();
		return instance;
	}

	JDBCUtil jdbc = JDBCUtil.getInstance();

	public int signUp(List<Object> param) {

		String sql = "INSERT INTO U_MEMBER (U_PW, U_ID, U_PHONE, U_NAME, U_AGE, U_GENDER, U_EDU, U_JOB, U_MYSELF, U_MBTI, U_LOC, U_HEIGHT, U_SMK, U_DRK)"
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		return jdbc.update(sql, param);
	}

	public int update(String sql) {
		return jdbc.update(sql);
	}

	public int deleteMember(String uid) {
		String sql = "DELETE FROM U_MEMBER WHERE U_ID = '" + uid + "' ";
		return jdbc.update(sql);
	}

	public List<Map<String, Object>> getAllMembers() {
		String sql = "SELECT U_PW, U_ID, U_PHONE, U_NAME, U_AGE, U_GENDER, U_EDU, U_JOB, U_MYSELF, U_MBTI, U_LOC, U_HEIGHT, U_SMK, U_DRK, U_CASH, M_STATE FROM U_MEMBER ";
		List<Object> param = new ArrayList<>();

		return jdbc.selectList(sql, param);
	}

	// 모든회원 매칭이력 조회
	public List<Map<String, Object>> getMatchHistory() {
		String sql = "SELECT R_NO, U_ID, M_USERID, M_STATE, R_DATE FROM M_HITORY ";
		List<Object> param = new ArrayList<>();

		return jdbc.selectList(sql, param);
	}

}