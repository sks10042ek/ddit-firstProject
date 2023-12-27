package service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controller.Controller;
import controller.View;
import dao.AlarmDAO;
import dao.MatchDAO;
import dao.MemberDAO;
import dao.PayDAO;

public class MatchService {
	static Scanner sc = new Scanner(System.in);
	private static MatchService instance;

	static int state = 0;

	private MatchService() {
	}

	public static MatchService getInstance() {
		if (instance == null)
			instance = new MatchService();
		return instance;
	}

	MatchDAO dao = MatchDAO.getInstance();
	PayDAO paydao = PayDAO.getInstance();
	AlarmDAO adao = AlarmDAO.getInstance();
	MemberDAO memberDAO = MemberDAO.getInstance();

	//��
//	�䱸���� �ۼ�
	public int matchingApply1() {

		Map<String, Object> member = (Map<String, Object>) Controller.sessionStorage.get("loginInfo");
		String targetId = "";
		int cash = Integer.parseInt(String.valueOf(member.get("U_CASH")));

		if (!(member.get("M_USERID") == null)) {
			System.out.println("\t\t��Ī�����Ͱ� �ֽ��ϴ�.");
		} else {
			matchingApply(member);
			if (state == 1) {
				return View.MSERVICE;
			} else {
				System.out.printf("\t%32s","���ϴ� ��� ���̵� �Է��ϼ���!");
				System.out.printf("\n\t%32s","���ϴ»�밡 ������ 0, ��Ī��û�� 1500ĳ�� ����!\n");
				System.out.printf("\n\t\t%12s","�Է� >>> ");
				targetId = sc.next();
			}

			if (targetId.equals("0")) {
				return View.MSERVICE;
			} else {
				List<Map<String, Object>> members = memberDAO.getAllMembers();

				if (members.isEmpty()) {
					System.out.println("��ϵ� ȸ���� �����ϴ�.");
				} else {
					if (cash >= 1500) {

						dao.saveMyData(targetId, "ENROLL");
						dao.saveUserData(targetId, "NEW");

						paydao.payMent(String.valueOf(member.get("U_ID")), (cash - 1500));

						member.put("M_STATE", "ENROLL");
						member.put("M_USERID", targetId);
						member.put("U_CASH", (cash - 1500));

						Controller.sessionStorage.put("loginInfo", member);

						System.out.println("\t\t��Ī��û �Ϸ�!\n\t\tĳ���ܾ� : " + member.get("U_CASH"));
					} else {
						System.out.println("\t\t��Ī��û ����\nĳ�� �ܾ��� �����մϴ�.\n");
					}
				}
			}
			state = 0;

		}
		return View.MSERVICE;
	}

	//��
	public void matchingApply(Map<String, Object> currentUser) {

		String ugender = null;
		String uloc = null;
		String usmk = null;
		String udrk = null;
		List<String> list = null;

		System.out.printf("\n\t\t%12s","[ ������� �ۼ� ]\n");

		while (true) {
			System.out.printf("\n\t%8s","������ �������ּ���(��/��) : ");
			ugender = sc.next();
			if (ugender.equals("��") || ugender.equals("��")) {
				break;
			} else {
				System.out.printf("\n\t%20s","�߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.\n");
			}
		}

		while (true) {
			System.out.print("\n  ����(����,����,�뱸,�λ�,����,���,��õ,����,�������) : ");
			uloc = sc.next();
			list = new ArrayList<>(Arrays.asList("����", "����", "�뱸", "�λ�", "����", "���", "��õ", "����", "�������"));
			if (list.contains(uloc)) {
				break;
			} else {
				System.out.println("\t��ȿ���� ���� �����Դϴ�. �ٽ� �Է����ּ���.\n");
			}

		}

		while (true) {
			System.out.print("\n\t\t������(Y/N) : ");
			usmk = sc.next().toUpperCase();
			usmk = usmk.toUpperCase();
			if (usmk.equalsIgnoreCase("Y") || usmk.equalsIgnoreCase("N")) {
				break;
			} else {
				System.out.println("\t�߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
			}
		}

		while (true) {
			System.out.print("\n\t\t���ֿ���(Y/N) : ");
			udrk = sc.next().toUpperCase();
			if (udrk.equalsIgnoreCase("Y") || udrk.equalsIgnoreCase("N")) {
				break;
			} else {
				System.out.println("\t�߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
			}
		}
		if (uloc.equals("�������")) {
			List<Map<String, Object>> foundMatchUsers = findAllMatches(ugender, udrk, usmk, currentUser);

			if (!foundMatchUsers.isEmpty()) {
				System.out.println("\n\t   �Է��Ͻ� ���ǿ� �´� ����� ������ �����ϴ�!");

				// �����ϰ� 3�� �����ϱ� ���� ����Ʈ�� ����
				Collections.shuffle(foundMatchUsers);

				// �ִ� 3������� ���
				int count = Math.min(foundMatchUsers.size(), 3);
				for (int i = 0; i < count; i++) {
					Map<String, Object> user = foundMatchUsers.get(i);
					String userId = (String) user.get("U_ID");
					BigDecimal userage = (BigDecimal) user.get("U_AGE");
					BigDecimal userKey = (BigDecimal) user.get("U_HEIGHT");
					String userLoc = (String) user.get("U_LOC");
					String userEducation = (String) user.get("U_EDU");
					String userJob = (String) user.get("U_JOB");
					String userMbti = (String) user.get("U_MBTI");
					String userMyself = (String) user.get("U_MYSELF");

					String matchUser = "ID: " + userId + "\n" + "����: " + userage + "\n" + "Ű: " + userKey + "\n"
							+ "����: " + userLoc + "\n" + "�з�: " + userEducation + "\n" + "����: " + userJob + "\n"
							+ "MBTI:" + userMbti + "\n" + "�ڱ�Ұ�: " + userMyself + "\n";

					System.out.println(matchUser);
				}
			} else {
				System.out.println("�Է��Ͻ� ���ǿ� �ش��ϴ� ����� �����ϴ�.\n");
				state = 1;
				ugender = null;
				uloc = null;
				usmk = null;
				udrk = null;
				list = null;
			}

		} else {
			List<Map<String, Object>> foundMatchUsers = findAllMatches(ugender, uloc, udrk, usmk, currentUser);

			if (!foundMatchUsers.isEmpty()) {
				System.out.println("\n\t   �Է��Ͻ� ���ǿ� �´� ����� ������ �����ϴ�!");
				System.out.println("\t����������������������������������������������������������������������������");

				// �����ϰ� 3�� �����ϱ� ���� ����Ʈ�� ����
				Collections.shuffle(foundMatchUsers);

				// �ִ� 3������� ���
				int count = Math.min(foundMatchUsers.size(), 3);
				for (int i = 0; i < count; i++) {
					Map<String, Object> user = foundMatchUsers.get(i);
					String userId = (String) user.get("U_ID");
					BigDecimal userage = (BigDecimal) user.get("U_AGE");
					BigDecimal userKey = (BigDecimal) user.get("U_HEIGHT");
					String userLoc = (String) user.get("U_LOC");
					String userEducation = (String) user.get("U_EDU");
					String userJob = (String) user.get("U_JOB");
					String userMbti = (String) user.get("U_MBTI");
					String userMyself = (String) user.get("U_MYSELF");

					String matchUser = "\t\tID : " + userId + "\n" + "\t\t���� : " + userage + "\n" + "\t\tŰ : " + userKey + "\n"
							+ "\t\t���� : " + userLoc + "\n" + "\t\t�з� : " + userEducation + "\n" + "\t\t���� : " + userJob + "\n"
							+ "\t\tMBTI :" + userMbti + "\n" + "\t\t�ڱ�Ұ� : " + userMyself + "\n";

					System.out.println(matchUser);
				}
			} else {
				System.out.println("\t�Է��Ͻ� ���ǿ� �ش��ϴ� ����� �����ϴ�.\n");
				state = 1;
				ugender = null;
				uloc = null;
				usmk = null;
				udrk = null;
				list = null;
			}
		}
	}

	// ���� ��������϶�
	public List<Map<String, Object>> findAllMatches(String ugender, String udrk, String usmk,
			Map<String, Object> currentUser) {
		List<Map<String, Object>> foundMatchUsers = new ArrayList<>();
		List<Map<String, Object>> users = dao.getMatchUsers(ugender, udrk, usmk);
		if (users != null && !users.isEmpty()) {
			for (Map<String, Object> user : users) {
				String userId = (String) user.get("U_ID");

				// �ڱ� �ڽ��� ��� �ǳʶٱ�
				if (currentUser != null && userId.equals(currentUser.get("U_ID"))) {
					continue;
				}

				foundMatchUsers.add(user);
			}
		}
		// �ڱ� �ڽ��� ������� �ʵ��� ����
		foundMatchUsers.remove(currentUser);
		return foundMatchUsers;
	}

	// ���� ��� ������
	public List<Map<String, Object>> findAllMatches(String ugender, String uloc, String udrk, String usmk,
			Map<String, Object> currentUser) {
		List<Map<String, Object>> foundMatchUsers = new ArrayList<>();
		List<Map<String, Object>> users = dao.getMatchUsers(ugender, uloc, udrk, usmk);
		if (users != null && !users.isEmpty()) {
			for (Map<String, Object> user : users) {
				String userId = (String) user.get("U_ID");

				// �ڱ� �ڽ��� ��� �ǳʶٱ�
				if (currentUser != null && userId.equals(currentUser.get("U_ID"))) {
					continue;
				}

				foundMatchUsers.add(user);
			}
		}
		// �ڱ� �ڽ��� ������� �ʵ��� ����
		foundMatchUsers.remove(currentUser);
		return foundMatchUsers;
	}

	private Map<String, Object> getCurrentUser() {
		return Controller.sessionStorage;
	}

	public int matchRes() {

		Map<String, Object> member = (Map<String, Object>) Controller.sessionStorage.get("loginInfo");
		String state = (String) member.get("M_STATE");
		try {
			switch (state) {
			case "NEW":
				System.out.println("\n���ο� ��Ī ��û�� �ֽ��ϴ�.\n");
				break;
			case "ENROLL":
				System.out.println("\t\t���� ������Դϴ�.\n");
				break;
			case "SUCCES":
				System.out.println("\n\t      ��Ī�� �����Ͽ����ϴ�! �����մϴ�!\n");
				String uId = String.valueOf(member.get("U_ID"));
				Map<String, Object> user = adao.getCheckInfo(uId);
				if (user != null) {
					System.out.println("\t��Ī�� ID : " + user.get("U_ID"));
					System.out.println("\t���� : " + user.get("U_AGE"));
					System.out.println("\t���� : " + user.get("U_GENDER"));
					System.out.println("\tMBTI : " + user.get("U_MBTI"));
					System.out.println("\t�ڱ�Ұ� : " + user.get("U_MYSELF"));
					System.out.println();
				}
				break;
			case "FAIL":
				System.out.println("\t\t��Ī�� �����Ͽ����ϴ�.\n");
				break;
			default:
				System.out.println("\t\t��Ī �����Ͱ� ���������ʽ��ϴ�.\n");
			}
		} catch (NullPointerException e) {
			state = "null";
		} finally {
			if (state.equals("null")) {
				System.out.println("\t\t��Ī �����Ͱ� ���������ʽ��ϴ�.\n");
			}
		}

		return View.MSERVICE;
	}

	//��
	public int matchDel() {

		String flag = "";
		int pageNo = 0;
		Map<String, Object> member = (Map<String, Object>) Controller.sessionStorage.get("loginInfo");

		System.out.println();
		System.out.printf("\t%30s","��Ī������ �����Ͻðڽ��ϱ�?(Y/N) : ");
		flag = sc.next();

		if (flag.equalsIgnoreCase("y")) {

			String mysql = "UPDATE U_MEMBER SET M_USERID = NULL, M_STATE = 'NULL' WHERE U_ID = '" + member.get("U_ID")
					+ "' ";
			String usersql = "UPDATE U_MEMBER SET M_USERID = NULL, M_STATE = 'NULL' WHERE U_ID = '"
					+ member.get("M_USERID") + "' ";

			dao.update(usersql);
			dao.update(mysql);

			member.put("M_USERID", null);
			member.put("M_STATE", null);
			Controller.sessionStorage.put("loginInfo", member);
			if (!(member.get("M_STATE") == "NULL")) {
				System.out.println("\t������ ���������� ó�������ʾҽ��ϴ�. �ٽ� �������ּ���.\n");

				pageNo = View.MSERVICE;
			} else {
				System.out.println("\t\t������ �Ϸ�Ǿ����ϴ�.\n");
				System.out.println();
				pageNo = View.MSERVICE;
			}
		} else if (flag.equalsIgnoreCase("n")) {
			pageNo = View.MSERVICE;
		} else {
			System.out.println("\t\t�߸��� �Է��Դϴ�.\n");
			pageNo = View.MSERVICE;
		}
		return pageNo;
	}
}
