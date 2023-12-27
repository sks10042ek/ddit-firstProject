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
				System.out.println("\n\t\t���ο� ��Ī ��û�� 1�� �ֽ��ϴ�.");
				System.out.println("\t����������������������������������������������������������������������");
				System.out.println("\t��Ī�� ID: " + user.get("U_ID"));
				System.out.println("\t����: " + user.get("U_AGE"));
				System.out.println("\t����: " + user.get("U_GENDER"));
				System.out.println("\tMBTI: " + user.get("U_MBTI"));
				System.out.println("\t�ڱ�Ұ�: " + user.get("U_MYSELF"));
				System.out.println("\t����������������������������������������������������������������������");
				System.out.print("\t\t��û�� �����Ͻðڽ��ϱ�?(Y/N) : ");
				flag = sc.next();

				if (flag.equalsIgnoreCase("y")) {


					String mysql = "UPDATE U_MEMBER SET M_STATE = 'SUCCES' WHERE U_ID = '" + member.get("U_ID") + "' ";
					String usersql = "UPDATE U_MEMBER SET M_STATE = 'SUCCES' WHERE U_ID = '" + member.get("M_USERID")
							+ "' ";

					dao.update(usersql);
					dao.update(mysql);
//					mDao.saveMatchHis(member.get("U_ID"), member.get("M_USERID"),"SUCCES");		//��Ī�̷� �߰�

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
//					mDao.saveMatchHis(member.get("U_ID"), member.get("M_USERID"),"FAIL");		//��Ī�̷� �߰�
					
					member.put("M_STATE", "NULL");
					Controller.sessionStorage.put("loginInfo", member);
					pageNo = View.ALARM;
				}else System.out.println("�߸��� �Է��Դϴ�.\n");
				pageNo = View.ALARM;
			} else {
				System.out.printf("\n\t%30s","��Ī ��û ������ ���������ʽ��ϴ�.\n");
				pageNo = View.ALARM;
			}
			return pageNo;
		} else {
			System.out.println("\t��Ī ��û ������ ���������ʽ��ϴ�.");
		}
		return View.ALARM;
	}
}
