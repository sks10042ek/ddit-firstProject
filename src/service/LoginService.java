package service;

import java.util.Map;
import java.util.Scanner;

import javax.security.auth.login.LoginContext;

import controller.Controller;
import controller.View;
import dao.LoginDAO;

public class LoginService {
	static Scanner sc = new Scanner(System.in);
	private static LoginService instance;
	static int pageNo = 0;

	private LoginService() {
	}

	public static LoginService getInstance() {
		if (instance == null)
			instance = new LoginService();
		return instance;
	}

	LoginDAO dao = LoginDAO.getInstance();

	public int login() {
		int loginCount = 0;

		while (true) {
			System.out.println("\n\t   ───────────[𝗟𝗢𝗚𝗜𝗡]───────────");
			System.out.print("\t\t     ID : ");
			String id = sc.nextLine();

			System.out.print("\t\t     PW : ");
			String pw = sc.nextLine();
			System.out.println("\t   ────────────────────────────");

			Map<String, Object> member = dao.login(id, pw);

			if (loginCount > 2) {
				System.out.println("\n\t\t로그인시도 횟수를 초과하였습니다.");
				System.out.println("\n\t\t     시스템을 종료합니다...");
				System.exit(0);
				break;
			}
			
			if (member != null) {
				Controller.sessionStorage.put("U_ID", id);
				Controller.sessionStorage.put("loginInfo", member);
				System.out.println("\n\t\t      " + member.get("U_NAME") + "님 반갑습니다!");
				
				if(id.equals("admin")) {
					pageNo=View.MASTERMAIN;
					loginCount = 0;
					break;
				}
	
				if (member.get("M_STATE").equals("NEW")) {
					System.out.println("   *****새로운 매칭 신청이 있습니다. 알림메뉴에서 확인해주세요.*****\n");
					loginCount = 0;
					pageNo = View.MAIN;
					break;
				}else {
					loginCount = 0;
					pageNo = View.MAIN;
					break;
				}
			} else {
				System.out.println("\t\t       다시 로그인하세요...\n");
				loginCount++;
			}
		}
		return pageNo;
	}

	public int findId() {
		System.out.println("\n\t   ──────────[𝗙𝗜𝗡𝗗 𝗜𝗗]───────────");
		System.out.print("\t\t      이름 입력 : ");
		String name = sc.nextLine();

		System.out.print("\t           전화번호 입력 : ");
		String phone = sc.nextLine();
		System.out.println("\t   ────────────────────────────");;

		System.out.println();
		String foundId = findId(name, phone);
		if (foundId != null) {
			System.out.printf("\t             " + name + "님의 아이디는 " + foundId + "입니다.\n");
		} else {
			System.out.println("\t\t일치하는 정보가 없습니다.\n");
		}
		return View.HOME;
	}

	public String findId(String name, String phone) {

		String foundId = null;
		Map<String, Object> user = dao.getUserId(name, phone);
		if (user != null) {
			foundId = (String) user.get("U_ID");
		}
		return foundId;
	}

	public int findPw() {
		Scanner sc = new Scanner(System.in);
		System.out.println("\n\t   ──────────[𝗙𝗜𝗡𝗗 𝗣𝗪]───────────");
		System.out.print("\t\t    ID : ");
		String id = sc.nextLine();

		System.out.print("\t\t        이름 : ");
		String name = sc.nextLine();

		System.out.print("\t              전화번호 : ");
		String phone = sc.nextLine();
		System.out.println("\t   ────────────────────────────");;
		
		String foundPW = findPw(id, name, phone);
		if (foundPW != null) {
			System.out.println("\t             "  + name + "님의 비밀번호는 " + foundPW + "입니다.\n");
		} else {
			System.out.println("\t\t  일치하는 정보가 없습니다.\n");
		}
		return View.HOME;
	}

	public String findPw(String id, String name, String phone) {

		String foundPW = null;
		Map<String, Object> user = dao.getUserPw(id, name, phone);
		if (user != null) {
			foundPW = (String) user.get("U_PW");
		}
		return foundPW;

	}

	
	public int logOut() {
		System.out.printf("\n\t%30s","로그아웃 하시겠습니까?(Y/N) : ");
		String res = sc.nextLine();
		if (res.equalsIgnoreCase("Y")) {
			System.out.println("\n\t\t     로그아웃 되었습니다...\n");
			Controller.sessionStorage.put("login", false);
			return View.HOME;
		} else if (res.equalsIgnoreCase("N")) {
	        String id= (String) Controller.sessionStorage.get("U_ID");
	        if (id.equalsIgnoreCase("admin")) {
	            return View.MASTERMAIN;
	        } else {
	            return View.MAIN;
	        }
	    } else {
			System.out.println("\n\t\t 잘못된 선택입니다. 다시 선택해주세요.\n");
			return View.MAIN;
		}
	}

//	아이디 중복 확인용
	public Map<String, Object> isDuplicate(String id) {
		Map<String, Object> result = dao.select(id);
		return result;
	}

}