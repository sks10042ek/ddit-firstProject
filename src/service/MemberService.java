package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controller.Controller;
import controller.View;
import dao.LoginDAO;
import dao.MemberDAO;
import dao.PayDAO;

public class MemberService {
	static Scanner sc = new Scanner(System.in);
	private static MemberService instance;
	static int pageNo = 0;

	private MemberService() {
	}

	public static MemberService getInstance() {
		if (instance == null)
			instance = new MemberService();
		return instance;
	}

	MemberDAO dao = MemberDAO.getInstance();
	PayDAO payDao = PayDAO.getInstance();
	LoginDAO loginDao = LoginDAO.getInstance();

	public int signUp() {

		List<Object> param = new ArrayList<Object>();

		String id;
		String pwd;
		String phone;
		String name;
		int age;
		String gender;
		String loc;
		int height;
		String edu;
		String job;
		String mbti;
		String smk;
		String drk;
		String mySelf;

		int result = 0;

		System.out.println();
		System.out.println("\t   ───────────[𝗦𝗜𝗚𝗡 𝗨𝗣]──────────");

		while (true) {
			System.out.print("\t\t       아이디 : ");
			id = sc.nextLine();

		
			Map<String, Object> member = isDuplicate(id);
			if (member != null) {
				System.out.println("\n\t   중복된 아이디입니다. 다른 아이디를 입력하세요.\n");
			} else {
				break;
			}
		}

		System.out.print("\n\t\t     비밀번호 : ");
		pwd = sc.nextLine();

		System.out.print("\n\t 전화번호(010-XXXX-XXXX) : ");
		phone = sc.nextLine();

		System.out.print("\n\t\t        이름 : ");
		name = sc.nextLine();

		System.out.print("\n\t\t        나이 : ");
		age = Integer.valueOf(sc.nextLine());

		while (true) {
			System.out.print("\n\t\t    성별(남/여) : ");
			gender = sc.nextLine();
			// 남/여가 아닐경우 잘못입력되었습니다. 반환
			if (gender.equals("남") || gender.equals("여")) {
				break;
			} else {
				System.out.println("\n\t    잘못된 성별입니다. 다시 입력해주세요.(남/여)\n");
			}
		}

		while (true) {
			System.out.print("\n\t지역(서울,대전,대구,부산,광주,울산,인천,세종) : ");
			loc = sc.nextLine();

			List<String> list = new ArrayList<>(Arrays.asList("서울", "대전", "대구", "부산", "광주", "울산", "인천", "세종"));
			if (list.contains(loc)) {
				break;
			} else {
				System.out.println("\n\t    유효하지 않은 지역입니다. 다시 입력해주세요.\n");
			}
		}

		System.out.print("\n\t\t         키 : ");
		height = Integer.parseInt(sc.nextLine());

		while (true) {
			System.out.print("\n\t  학력(고졸이하, 대졸, 석사이상) : ");
			edu = sc.nextLine();
			List<String> list = new ArrayList<>(Arrays.asList("고졸이하", "대졸", "석사이상"));
			if (list.contains(edu)) {
				break;
			} else {
				System.out.println("\n\t    유효하지 않은 학력입니다. 다시 입력해주세요.\n");
			}
		}

		System.out.print("\n\t\t      직업 : ");
		job = sc.nextLine();

		while (true) {
			System.out.print("\n\t\t       𝗠𝗕𝗧𝗜 : ");
			mbti = sc.nextLine();
			mbti = mbti.toUpperCase();
			List<String> list = new ArrayList<>(Arrays.asList("ISFP", "ISTJ", "ISFJ", "ESTJ", "ESFJ", "ISTP", "ESTP",
					"ESFP", "INFJ", "INFP", "ENFJ", "ENFP", "INTJ", "INTP", "ENTJ", "ENTP"));
			if (list.contains(mbti)) {
				break;
			} else {
				System.out.println("\n\t    유효하지 않은 MBTI입니다. 다시 입력해주세요.\n");
			}
		}

		while (true) {
			System.out.print("\n\t\t   흡연여부(Y/N) : ");
			smk = sc.next().toUpperCase();
			if (smk.equalsIgnoreCase("Y") || smk.equalsIgnoreCase("N")) {
				break;
			} else {
				System.out.println("\n\t    잘못된 입력입니다. 다시 입력해주세요.(Y/N)\n");
			}
		}

		while (true) {
			System.out.print("\n\t\t   음주여부(Y/N) : ");
			drk = sc.next().toUpperCase();
			if (drk.equalsIgnoreCase("Y") || drk.equalsIgnoreCase("N")) {
				break;
			} else {
				System.out.println("\n\t    잘못된 입력입니다. 다시 입력해주세요.(Y/N)\n");
			}
		}

		sc.nextLine();
		System.out.print("\n\t    자기소개 : ");
		mySelf = sc.nextLine();

		param.add(pwd);
		param.add(id);
		param.add(phone);
		param.add(name);
		param.add(age);
		param.add(gender);
		param.add(edu);
		param.add(job);
		param.add(mySelf);
		param.add(mbti);
		param.add(loc);
		param.add(height);
		param.add(smk);
		param.add(drk);

		result = dao.signUp(param);

		if (result > 0) {
			System.out.println("\n\t\t   *ﾟ  회원가입 성공! +｡ ");
			System.out.println();
			pageNo = View.HOME;
		} else {
			System.out.println("\n\t\t    회원가입 실패!\n");
			pageNo = View.HOME;
		}
		return pageNo;
	}

	public int exit() {
		System.out.print("\n\t         시스템을 종료하시겠습니까?(Y/N) : ");
		String res = sc.next();

		if (res.equalsIgnoreCase("Y")) {
			System.out.println("\n\t\t     시스템을 종료합니다...");
			System.exit(0);
			return 0;
		} else if (res.equalsIgnoreCase("N")) {
			System.out.println();
			return View.HOME;
		} else {
			System.out.println("\t\t 잘못된 입력입니다. 다시 선택해주세요.\n");
			return View.HOME;
		}
	}

	//★
	public int myInfo() {
		Map<String, Object> member = (Map<String, Object>) Controller.sessionStorage.get("loginInfo");
		System.out.println("\n\t   ──────── [ 회원 정보 ] ───────── ");
		System.out.println("\t1.ID :" + member.get("U_ID") + "\t\t2.PW :" + member.get("U_PW"));
		System.out.println("\t3.전화번호 :" + member.get("U_PHONE") + "\t4.이름 :" + member.get("U_NAME"));
		System.out.println("\t5.나이 :" + member.get("U_AGE") + "\t\t6.성별 :" + member.get("U_GENDER"));
		System.out.println("\t7.지역 :" + member.get("U_LOC") + "\t\t8.키 :" + member.get("U_HEIGHT"));
		System.out.println("\t9.학력 :" + member.get("U_EDU") + "\t\t10.직업 :" + member.get("U_JOB"));
		System.out.println("\t11.MBTI :" + member.get("U_MBTI") + "\t\t12.흡연여부 :" + member.get("U_SMK"));
		System.out.println("\t13.음주여부 :" + member.get("U_DRK") + "\t\t14.자기소개 :" + member.get("U_MYSELF"));
		System.out.println("\n\t\t      1.뒤로가기");
		System.out.print("\t\t         메뉴 선택 >>> ");

		switch (sc.nextInt()) {
		case 1:
			System.out.println();
			return View.MYPAGE;
		default:
			System.out.println("\n\t\t잘못된 입력입니다.");
			return myInfo();
		}
	}

	//★
	public int updateInfo() {

		String pwd;
		String phone;
		String name;
		String loc;
		String edu;
		String job;
		String mbti;
		String smk;
		String drk;
		String mySelf;

		String updateSql = " UPDATE U_MEMBER SET ";
		String flag = "";
		int pageNo = 0;

		Map<String, Object> member = (Map<String, Object>) Controller.sessionStorage.get("loginInfo");
		System.out.println("\n\t   ──────── [ 수정 항목 선택 ] ──────── ");
		System.out.println("\t1.ID :" + member.get("U_ID") + "\t\t2.PW :" + member.get("U_PW"));
		System.out.println("\t3.전화번호 :" + member.get("U_PHONE") + "\t4.이름 :" + member.get("U_NAME"));
		System.out.println("\t5.나이 :" + member.get("U_AGE") + "\t\t6.성별 :" + member.get("U_GENDER"));
		System.out.println("\t7.지역 :" + member.get("U_LOC") + "\t\t8.키 :" + member.get("U_HEIGHT"));
		System.out.println("\t9.학력 :" + member.get("U_EDU") + "\t\t10.직업 :" + member.get("U_JOB"));
		System.out.println("\t11.MBTI :" + member.get("U_MBTI") + "\t\t12.흡연여부 :" + member.get("U_SMK"));
		System.out.println("\t13.음주여부 :" + member.get("U_DRK") + "\t\t14.자기소개 :" + member.get("U_MYSELF"));
		System.out.println("\n\t\t      0.뒤로가기");
		System.out.print("\t\t         메뉴 선택 >>> ");

		switch (sc.nextInt()) {
		case 0:
			pageNo = View.MYPAGE;
			return pageNo;
		case 1:
			System.out.println();
			System.out.println("\n\t\t 변경할 수 없는 항목입니다!");
			System.out.println();
			pageNo = View.UPDATEINFO;
			return pageNo;
		case 2:
			System.out.println();
			System.out.print("\t\t비밀번호를 변경하시겠습니까?(Y/N) : ");
			flag = sc.next();
			if (flag.equalsIgnoreCase("y")) {
				System.out.print("\t\t변경할 비밀번호 : ");
				pwd = sc.next();
				updateSql += "U_PW = '" + pwd + "' WHERE U_ID =  '" + member.get("U_ID") + "' ";
				pageNo = View.UPDATEINFO;
				dao.update(updateSql);

				System.out.println("\t\t변경이 완료되었습니다.");
				System.out.println();

				member.put("U_PW", pwd);
				Controller.sessionStorage.put("loginInfo", member);

			} else if (flag.equalsIgnoreCase("n")) {
				System.out.println();
				pageNo = View.UPDATEINFO;
			} else {
				System.out.println("\t\t잘못된 입력입니다.\n");
				pageNo = View.UPDATEINFO;
			}
			return pageNo;
		case 3:
			System.out.println();
			System.out.print("\t\t전화번호를 변경하시겠습니까?(Y/N) : ");
			flag = sc.next();
			if (flag.equalsIgnoreCase("y")) {
				System.out.print("\t\t변경할 전화번호 : ");
				phone = sc.next();
				updateSql += "U_PHONE = '" + phone + "' WHERE U_ID =  '" + member.get("U_ID") + "' ";
				pageNo = View.UPDATEINFO;
				dao.update(updateSql);

				System.out.println("\t\t변경이 완료되었습니다.");
				System.out.println();

				member.put("U_PHONE", phone);
				Controller.sessionStorage.put("loginInfo", member);

			} else if (flag.equalsIgnoreCase("n")) {
				pageNo = View.UPDATEINFO;
				System.out.println();
			} else {
				System.out.println("\t\t잘못된 입력입니다.\n");
				
				pageNo = View.UPDATEINFO;
			}
			return pageNo;
		case 4:
			System.out.println();
			System.out.print("\t\t이름을 변경하시겠습니까?(Y/N) : ");
			flag = sc.next();
			if (flag.equalsIgnoreCase("y")) {
				System.out.print("\t\t변경할 이름 : ");
				name = sc.next();
				updateSql += "U_NAME = '" + name + "' WHERE U_ID =  '" + member.get("U_ID") + "' ";
				pageNo = View.UPDATEINFO;
				dao.update(updateSql);

				System.out.println("\t\t변경이 완료되었습니다.");
				System.out.println();

				member.put("U_NAME", name);
				Controller.sessionStorage.put("loginInfo", member);

			} else if (flag.equalsIgnoreCase("n")) {
				pageNo = View.UPDATEINFO;
				System.out.println();
			} else {
				System.out.println("\t\t잘못된 입력입니다.\n");

				pageNo = View.UPDATEINFO;
			}
			return pageNo;
		case 5:
			System.out.println();
			System.out.println("\t\t변경할 수 없는 항목입니다.");
			System.out.println();
			pageNo = View.UPDATEINFO;
			return pageNo;
		case 6:
			System.out.println();
			System.out.println("\t\t변경할 수 없는 항목입니다.");
			System.out.println();
			pageNo = View.UPDATEINFO;
			return pageNo;
		case 7:
			System.out.println();
			System.out.print("\t\t지역을 변경하시겠습니까?(Y/N) : ");
			flag = sc.next();
			if (flag.equalsIgnoreCase("y")) {
				System.out.print("\t변경할 지역(서울,대전,대구,부산,광주,울산,인천,세종) : ");
				loc = sc.next();

				List<String> list = new ArrayList<>(Arrays.asList("서울", "대전", "대구", "부산", "광주", "울산", "인천", "세종"));
				if (list.contains(loc)) {
					updateSql += "U_LOC = '" + loc + "' WHERE U_ID =  '" + member.get("U_ID") + "' ";
					pageNo = View.UPDATEINFO;
					dao.update(updateSql);

					System.out.println("\t\t변경이 완료되었습니다.");
					System.out.println();

					member.put("U_LOC", loc);
					Controller.sessionStorage.put("loginInfo", member);
				} else {
					System.out.println("\t유효하지 않은 지역입니다. 다시 입력해주세요.\n");
					pageNo = View.UPDATEINFO;
				}

			} else if (flag.equalsIgnoreCase("n")) {
				pageNo = View.UPDATEINFO;
				System.out.println();
			}else {
				System.out.println("\t\t잘못된 입력입니다.\n");
				pageNo = View.UPDATEINFO;
			}
			return pageNo;
		case 8:
			System.out.println();
			System.out.println("\t\t변경할 수 없는 항목입니다.");
			System.out.println();
			pageNo = View.UPDATEINFO;
			return pageNo;
		case 9:
			System.out.println();
			System.out.print("\t\t학력을 변경하시겠습니까?(Y/N) : ");
			flag = sc.next();
			if (flag.equalsIgnoreCase("y")) {

				System.out.print("\t변경할 학력(고졸이하, 대졸, 석사이상) : ");
				edu = sc.next();
				List<String> list = new ArrayList<>(Arrays.asList("고졸이하", "대졸", "석사이상"));
				if (list.contains(edu)) {
					updateSql += "U_EDU = '" + edu + "' WHERE U_ID =  '" + member.get("U_ID") + "' ";
					pageNo = View.UPDATEINFO;
					dao.update(updateSql);

					System.out.println("\t\t변경이 완료되었습니다.");
					System.out.println();

					member.put("U_EDU", edu);
					Controller.sessionStorage.put("loginInfo", member);
				} else {
					System.out.println("\t\t잘못된 입력입니다. 다시 입력해주세요.\n");
					pageNo = View.UPDATEINFO;
				}

			} else if (flag.equalsIgnoreCase("n")) {
				pageNo = View.UPDATEINFO;
				System.out.println();
			}else {
				System.out.println("\t\t잘못된 입력입니다.\n");
				pageNo = View.UPDATEINFO;
			}
			return pageNo;
		case 10:
			System.out.println();
			System.out.print("\t\t직업을 변경하시겠습니까?(Y/N) : ");
			flag = sc.next();
			if (flag.equalsIgnoreCase("y")) {
				System.out.print("\t\t변경할 직업 : ");
				job = sc.next();
				updateSql += "U_JOB = '" + job + "' WHERE U_ID =  '" + member.get("U_ID") + "' ";
				pageNo = View.UPDATEINFO;
				dao.update(updateSql);

				System.out.println("\t\t변경이 완료되었습니다.");
				System.out.println();

				member.put("U_JOB", job);
				Controller.sessionStorage.put("loginInfo", member);

			} else if (flag.equalsIgnoreCase("n")) {
				pageNo = View.UPDATEINFO;
				System.out.println();
			} else {
				System.out.println("\t\t잘못된 입력입니다.\n");
				pageNo = View.UPDATEINFO;
			}
			return pageNo;
		case 11:
			System.out.println();
			System.out.print("\t\tMBTI를 변경하시겠습니까?(Y/N) : ");
			flag = sc.next();
			if (flag.equalsIgnoreCase("y")) {

				System.out.print("\t\t변경할 MBTI : ");
				mbti = sc.next();
				mbti = mbti.toUpperCase();
				List<String> list = new ArrayList<>(Arrays.asList("ISFP", "ISTJ", "ISFJ", "ESTJ", "ESFJ", "ISTP",
						"ESTP", "ESFP", "INFJ", "INFP", "ENFJ", "ENFP", "INTJ", "INTP", "ENTJ", "ENTP"));
				if (list.contains(mbti)) {
					updateSql += "U_MBTI = '" + mbti + "' WHERE U_ID =  '" + member.get("U_ID") + "' ";
					pageNo = View.UPDATEINFO;
					dao.update(updateSql);

					System.out.println("\t\t변경이 완료되었습니다.");
					System.out.println();

					member.put("U_MBTI", mbti);
					Controller.sessionStorage.put("loginInfo", member);

				} else {
					System.out.println("\t유효하지 않은 MBTI입니다. 다시 입력해주세요.\n");
					pageNo = View.UPDATEINFO;
				}
			} else if (flag.equalsIgnoreCase("n")) {
				pageNo = View.UPDATEINFO;
				System.out.println();
			}else {
				System.out.println("\t\t잘못된 입력입니다.\n");
				pageNo = View.UPDATEINFO;
			}
			return pageNo;
		case 12:
			System.out.println();
			System.out.print("\t\t흡연여부를 변경하시겠습니까?(Y/N) : ");
			flag = sc.next();
			if (flag.equalsIgnoreCase("y")) {
				System.out.print("\t\t흡연여부(Y/N) : ");
				smk = sc.next();
				smk = smk.toUpperCase();
				if (smk.equalsIgnoreCase("Y") || smk.equalsIgnoreCase("N")) {
					updateSql += "U_SMK = '" + smk + "' WHERE U_ID =  '" + member.get("U_ID") + "' ";
					pageNo = View.UPDATEINFO;
					dao.update(updateSql);

					System.out.println("\t\t변경이 완료되었습니다.");
					System.out.println();

					member.put("U_SMK", smk);
					Controller.sessionStorage.put("loginInfo", member);
				} 
				
			} else if (flag.equalsIgnoreCase("n")) {
				pageNo = View.UPDATEINFO;
				System.out.println();
			} else {
				System.out.println("\t\t잘못된 입력입니다.\n");
				pageNo = View.UPDATEINFO;
			}
			return pageNo;
		case 13:
			System.out.println();
			System.out.print("\t\t음주여부를 변경하시겠습니까?(Y/N) : ");
			flag = sc.next();
			if (flag.equalsIgnoreCase("y")) {
				System.out.print("\t\t음주여부(Y/N) : ");
				drk = sc.next();
				drk = drk.toUpperCase();
				if (drk.equalsIgnoreCase("Y") || drk.equalsIgnoreCase("N")) {
					updateSql += "U_DRK = '" + drk + "' WHERE U_ID =  '" + member.get("U_ID") + "' ";
					pageNo = View.UPDATEINFO;
					dao.update(updateSql);

					System.out.println("\t\t변경이 완료되었습니다.");
					System.out.println();

					member.put("U_DRK", drk);
					Controller.sessionStorage.put("loginInfo", member);
				} 
				
			} else if (flag.equalsIgnoreCase("n")) {
				pageNo = View.UPDATEINFO;
				System.out.println();
			} else {
				System.out.println("\t\t잘못된 입력입니다.\n");
				pageNo = View.UPDATEINFO;
			}
			return pageNo;
		case 14:
			System.out.println();
			System.out.print("\t\t자기소개를 변경하시겠습니까?(Y/N) : ");
			flag = sc.next();
			if (flag.equalsIgnoreCase("y")) {
				System.out.print("\t\t변경할 내용 : ");
				mySelf = sc.next();
				updateSql += "U_MYSELF = '" + mySelf + "' WHERE U_ID =  '" + member.get("U_ID") + "' ";
				pageNo = View.UPDATEINFO;
				dao.update(updateSql);

				System.out.println("\t\t변경이 완료되었습니다.");
				System.out.println();

				member.put("U_MYSELF", mySelf);
				Controller.sessionStorage.put("loginInfo", member);
			} else if (flag.equalsIgnoreCase("n")) {
				pageNo = View.UPDATEINFO;
				System.out.println();
			} else {
				System.out.println("\t\t잘못된 입력입니다.\n");
				pageNo = View.UPDATEINFO;
			}
			return pageNo;
		default:
			System.out.println("\t\t잘못된 입력입니다.");
			pageNo = View.MYPAGE;
			return pageNo;
		}
	}

	public int withdrawal() {

		String flag = "";
		int pageNo = 0;
		Map<String, Object> member = (Map<String, Object>) Controller.sessionStorage.get("loginInfo");

		System.out.println();
		System.out.print("\n\t   회원 탈퇴를 진행하시겠습니까?(Y/N) : ");
		flag = sc.next();

		if (flag.equalsIgnoreCase("y")) {

			String uid = (String) member.get("U_ID");
			dao.deleteMember(uid);
			if (!(member.get("M_USERID").equals("NULL"))) {
				System.out.println("\t  매칭 정보가 존재합니다. 삭제 후 탈퇴해주세요.");
				pageNo = View.MYPAGE;
			} else {
				if (member.get("U_ID") == null) {
					System.out.println("\t 탈퇴가 정상적으로 처리되지않았습니다. 다시 진행해주세요.\n");
					pageNo = View.MYPAGE;
				} else {
					System.out.println("\n\t\t    탈퇴가 완료되었습니다.\n");
					System.out.println();
					pageNo = View.HOME;
				}
			}
		} else if (flag.equalsIgnoreCase("n")) {
			pageNo = View.MYPAGE;
		} else {
			System.out.println("\n\t\t      잘못된 입력입니다.\n");
			pageNo = View.MYPAGE;
		}
		return pageNo;
	}
	
	 public int balancemain() {
	       Map<String, Object> member = (Map<String, Object>)(Controller.sessionStorage.get("loginInfo"));

	        int cashBalance = payDao.getCashBalance(member.get("U_ID"));

	        System.out.println("\n\t\t ▩ 캐시 잔액: " + cashBalance);

	        return View.MYPAGE;
	    }
	 
	 public Map<String, Object> isDuplicate(String id) {
			Map<String, Object> result = loginDao.select(id);
			return result;
		}

}