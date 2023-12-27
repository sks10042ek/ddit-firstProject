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

	//★
//	요구사항 작성
	public int matchingApply1() {

		Map<String, Object> member = (Map<String, Object>) Controller.sessionStorage.get("loginInfo");
		String targetId = "";
		int cash = Integer.parseInt(String.valueOf(member.get("U_CASH")));

		if (!(member.get("M_USERID") == null)) {
			System.out.println("\t\t매칭데이터가 있습니다.");
		} else {
			matchingApply(member);
			if (state == 1) {
				return View.MSERVICE;
			} else {
				System.out.printf("\t%32s","원하는 상대 아이디를 입력하세요!");
				System.out.printf("\n\t%32s","원하는상대가 없으면 0, 매칭신청시 1500캐시 차감!\n");
				System.out.printf("\n\t\t%12s","입력 >>> ");
				targetId = sc.next();
			}

			if (targetId.equals("0")) {
				return View.MSERVICE;
			} else {
				List<Map<String, Object>> members = memberDAO.getAllMembers();

				if (members.isEmpty()) {
					System.out.println("등록된 회원이 없습니다.");
				} else {
					if (cash >= 1500) {

						dao.saveMyData(targetId, "ENROLL");
						dao.saveUserData(targetId, "NEW");

						paydao.payMent(String.valueOf(member.get("U_ID")), (cash - 1500));

						member.put("M_STATE", "ENROLL");
						member.put("M_USERID", targetId);
						member.put("U_CASH", (cash - 1500));

						Controller.sessionStorage.put("loginInfo", member);

						System.out.println("\t\t매칭신청 완료!\n\t\t캐시잔액 : " + member.get("U_CASH"));
					} else {
						System.out.println("\t\t매칭신청 실패\n캐시 잔액이 부족합니다.\n");
					}
				}
			}
			state = 0;

		}
		return View.MSERVICE;
	}

	//★
	public void matchingApply(Map<String, Object> currentUser) {

		String ugender = null;
		String uloc = null;
		String usmk = null;
		String udrk = null;
		List<String> list = null;

		System.out.printf("\n\t\t%12s","[ 희망사항 작성 ]\n");

		while (true) {
			System.out.printf("\n\t%8s","성별을 선택해주세요(남/여) : ");
			ugender = sc.next();
			if (ugender.equals("남") || ugender.equals("여")) {
				break;
			} else {
				System.out.printf("\n\t%20s","잘못된 입력입니다. 다시 입력해주세요.\n");
			}
		}

		while (true) {
			System.out.print("\n  지역(서울,대전,대구,부산,광주,울산,인천,세종,상관없음) : ");
			uloc = sc.next();
			list = new ArrayList<>(Arrays.asList("서울", "대전", "대구", "부산", "광주", "울산", "인천", "세종", "상관없음"));
			if (list.contains(uloc)) {
				break;
			} else {
				System.out.println("\t유효하지 않은 지역입니다. 다시 입력해주세요.\n");
			}

		}

		while (true) {
			System.out.print("\n\t\t흡연여부(Y/N) : ");
			usmk = sc.next().toUpperCase();
			usmk = usmk.toUpperCase();
			if (usmk.equalsIgnoreCase("Y") || usmk.equalsIgnoreCase("N")) {
				break;
			} else {
				System.out.println("\t잘못된 입력입니다. 다시 입력해주세요.");
			}
		}

		while (true) {
			System.out.print("\n\t\t음주여부(Y/N) : ");
			udrk = sc.next().toUpperCase();
			if (udrk.equalsIgnoreCase("Y") || udrk.equalsIgnoreCase("N")) {
				break;
			} else {
				System.out.println("\t잘못된 입력입니다. 다시 입력해주세요.");
			}
		}
		if (uloc.equals("상관없음")) {
			List<Map<String, Object>> foundMatchUsers = findAllMatches(ugender, udrk, usmk, currentUser);

			if (!foundMatchUsers.isEmpty()) {
				System.out.println("\n\t   입력하신 조건에 맞는 사람은 다음과 같습니다!");

				// 랜덤하게 3명 선택하기 위해 리스트를 섞음
				Collections.shuffle(foundMatchUsers);

				// 최대 3명까지만 출력
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

					String matchUser = "ID: " + userId + "\n" + "나이: " + userage + "\n" + "키: " + userKey + "\n"
							+ "지역: " + userLoc + "\n" + "학력: " + userEducation + "\n" + "직업: " + userJob + "\n"
							+ "MBTI:" + userMbti + "\n" + "자기소개: " + userMyself + "\n";

					System.out.println(matchUser);
				}
			} else {
				System.out.println("입력하신 조건에 해당하는 사람이 없습니다.\n");
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
				System.out.println("\n\t   입력하신 조건에 맞는 사람은 다음과 같습니다!");
				System.out.println("\t──────────────────────────────────────");

				// 랜덤하게 3명 선택하기 위해 리스트를 섞음
				Collections.shuffle(foundMatchUsers);

				// 최대 3명까지만 출력
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

					String matchUser = "\t\tID : " + userId + "\n" + "\t\t나이 : " + userage + "\n" + "\t\t키 : " + userKey + "\n"
							+ "\t\t지역 : " + userLoc + "\n" + "\t\t학력 : " + userEducation + "\n" + "\t\t직업 : " + userJob + "\n"
							+ "\t\tMBTI :" + userMbti + "\n" + "\t\t자기소개 : " + userMyself + "\n";

					System.out.println(matchUser);
				}
			} else {
				System.out.println("\t입력하신 조건에 해당하는 사람이 없습니다.\n");
				state = 1;
				ugender = null;
				uloc = null;
				usmk = null;
				udrk = null;
				list = null;
			}
		}
	}

	// 지역 상관없음일때
	public List<Map<String, Object>> findAllMatches(String ugender, String udrk, String usmk,
			Map<String, Object> currentUser) {
		List<Map<String, Object>> foundMatchUsers = new ArrayList<>();
		List<Map<String, Object>> users = dao.getMatchUsers(ugender, udrk, usmk);
		if (users != null && !users.isEmpty()) {
			for (Map<String, Object> user : users) {
				String userId = (String) user.get("U_ID");

				// 자기 자신인 경우 건너뛰기
				if (currentUser != null && userId.equals(currentUser.get("U_ID"))) {
					continue;
				}

				foundMatchUsers.add(user);
			}
		}
		// 자기 자신을 출력하지 않도록 수정
		foundMatchUsers.remove(currentUser);
		return foundMatchUsers;
	}

	// 지역 상관 있을때
	public List<Map<String, Object>> findAllMatches(String ugender, String uloc, String udrk, String usmk,
			Map<String, Object> currentUser) {
		List<Map<String, Object>> foundMatchUsers = new ArrayList<>();
		List<Map<String, Object>> users = dao.getMatchUsers(ugender, uloc, udrk, usmk);
		if (users != null && !users.isEmpty()) {
			for (Map<String, Object> user : users) {
				String userId = (String) user.get("U_ID");

				// 자기 자신인 경우 건너뛰기
				if (currentUser != null && userId.equals(currentUser.get("U_ID"))) {
					continue;
				}

				foundMatchUsers.add(user);
			}
		}
		// 자기 자신을 출력하지 않도록 수정
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
				System.out.println("\n새로운 매칭 신청이 있습니다.\n");
				break;
			case "ENROLL":
				System.out.println("\t\t수락 대기중입니다.\n");
				break;
			case "SUCCES":
				System.out.println("\n\t      매칭에 성공하였습니다! 축하합니다!\n");
				String uId = String.valueOf(member.get("U_ID"));
				Map<String, Object> user = adao.getCheckInfo(uId);
				if (user != null) {
					System.out.println("\t매칭된 ID : " + user.get("U_ID"));
					System.out.println("\t나이 : " + user.get("U_AGE"));
					System.out.println("\t성별 : " + user.get("U_GENDER"));
					System.out.println("\tMBTI : " + user.get("U_MBTI"));
					System.out.println("\t자기소개 : " + user.get("U_MYSELF"));
					System.out.println();
				}
				break;
			case "FAIL":
				System.out.println("\t\t매칭에 실패하였습니다.\n");
				break;
			default:
				System.out.println("\t\t매칭 데이터가 존재하지않습니다.\n");
			}
		} catch (NullPointerException e) {
			state = "null";
		} finally {
			if (state.equals("null")) {
				System.out.println("\t\t매칭 데이터가 존재하지않습니다.\n");
			}
		}

		return View.MSERVICE;
	}

	//★
	public int matchDel() {

		String flag = "";
		int pageNo = 0;
		Map<String, Object> member = (Map<String, Object>) Controller.sessionStorage.get("loginInfo");

		System.out.println();
		System.out.printf("\t%30s","매칭정보를 삭제하시겠습니까?(Y/N) : ");
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
				System.out.println("\t삭제가 정상적으로 처리되지않았습니다. 다시 진행해주세요.\n");

				pageNo = View.MSERVICE;
			} else {
				System.out.println("\t\t삭제가 완료되었습니다.\n");
				System.out.println();
				pageNo = View.MSERVICE;
			}
		} else if (flag.equalsIgnoreCase("n")) {
			pageNo = View.MSERVICE;
		} else {
			System.out.println("\t\t잘못된 입력입니다.\n");
			pageNo = View.MSERVICE;
		}
		return pageNo;
	}
}
