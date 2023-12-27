package service;

import java.util.Map;
import java.util.Scanner;

import controller.Controller;
import controller.View;
import dao.AlarmDAO;
import dao.MatchDAO;
import dao.MemberDAO;

public class AlarmService {
	static Scanner sc = new Scanner(System.in);
	private static AlarmService instance;
	static int pageNo = 0;

	private AlarmService() {
	}

	public static AlarmService getInstance() {
		if (instance == null)
			instance = new AlarmService();
		return instance;
	}

	AlarmDAO dao = AlarmDAO.getInstance();
	MatchDAO mDao = MatchDAO.getInstance();

	public int checkMsg() {

		Map<String, Object> member = (Map<String, Object>) Controller.sessionStorage.get("loginInfo");
		String flag = "";
		String uId = String.valueOf(member.get("U_ID"));

		Map<String, Object> user = dao.getCheckInfo(uId);
		if (user != null) {
			if (member.get("M_STATE").equals("NEW")) {
				System.out.println("\n\t\t새로운 매칭 신청이 1건 있습니다.");
				System.out.println("\t───────────────────────────────────");
				System.out.println("\t매칭된 ID: " + user.get("U_ID"));
				System.out.println("\t나이: " + user.get("U_AGE"));
				System.out.println("\t성별: " + user.get("U_GENDER"));
				System.out.println("\tMBTI: " + user.get("U_MBTI"));
				System.out.println("\t자기소개: " + user.get("U_MYSELF"));
				System.out.println("\t───────────────────────────────────");
				System.out.print("\t\t요청을 수락하시겠습니까?(Y/N) : ");
				flag = sc.next();

				if (flag.equalsIgnoreCase("y")) {


					String mysql = "UPDATE U_MEMBER SET M_STATE = 'SUCCES' WHERE U_ID = '" + member.get("U_ID") + "' ";
					String usersql = "UPDATE U_MEMBER SET M_STATE = 'SUCCES' WHERE U_ID = '" + member.get("M_USERID")
							+ "' ";

					dao.update(usersql);
					dao.update(mysql);
//					mDao.saveMatchHis(member.get("U_ID"), member.get("M_USERID"),"SUCCES");		//매칭이력 추가

					member.put("M_STATE", "SUCCES");
					Controller.sessionStorage.put("loginInfo", member);

					pageNo = View.ALARM;
				} else if (flag.equalsIgnoreCase("n")) {
					String mysql = "UPDATE U_MEMBER SET M_USERID = NULL, M_STATE = 'NULL' WHERE U_ID = '"
							+ member.get("U_ID") + "' ";
					String usersql = "UPDATE U_MEMBER SET M_USERID = NULL, M_STATE = 'NULL' WHERE U_ID = '"
							+ member.get("M_USERID") + "' ";

					dao.update(usersql);
					dao.update(mysql);
//					mDao.saveMatchHis(member.get("U_ID"), member.get("M_USERID"),"FAIL");		//매칭이력 추가
					
					member.put("M_STATE", "NULL");
					Controller.sessionStorage.put("loginInfo", member);
					pageNo = View.ALARM;
				}else System.out.println("잘못된 입력입니다.\n");
				pageNo = View.ALARM;
			} else {
				System.out.printf("\n\t%30s","매칭 요청 내역이 존재하지않습니다.\n");
				pageNo = View.ALARM;
			}
			return pageNo;
		} else {
			System.out.println("\t매칭 요청 내역이 존재하지않습니다.");
		}
		return View.ALARM;
	}
}
