package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import service.AlarmService;
import service.DatecourceService;
import service.LoginService;
import service.MasterPageService;
import service.MatchService;
import service.MemberService;
import service.PayService;

public class Controller {

	static Scanner sc = new Scanner(System.in);

	public static Map<String, Object> sessionStorage = new HashMap<String, Object>();

	LoginService loginService = LoginService.getInstance();
	MemberService memberService = MemberService.getInstance();
	DatecourceService datecourceService = DatecourceService.getInstance();
	MatchService matchService = MatchService.getInstance();
	PayService payService = PayService.getInstance();
	AlarmService alarmService = AlarmService.getInstance();
	MasterPageService masterPageService = MasterPageService.getInstance();

	public static void main(String[] args) {
		new Controller().start();
	}

	public void start() {

		sessionStorage.put("login", false); // 로그인상태 : 미로그인
		sessionStorage.put("loginInfo", null);

		int view = View.HOME;

		while (true) {
			switch (view) {
			case View.HOME:
				view = home();
				break;
			case View.MAIN:
				view = mainPage();
				break;
			case View.SIGNIN:
				view = loginService.login();
				break;
			case View.SIGNUP:
				view = memberService.signUp();
				break;
			case View.FINDID:
				view = loginService.findId();
				break;
			case View.FINDPW:
				view = loginService.findPw();
				break;
			case View.EXIT:
				view = memberService.exit();
				break;

			case View.MYPAGE:
				view = myPage();
				break;
			case View.MSERVICE:
				view = mServicePage();
				break;
			case View.COURCEINFO:
				view = dCourcePage();
				break;
			case View.PAY:
				view = payPage();
				break;
			case View.ALARM:
				view = alarmPage();
				break;

			case View.SELECTINFO:
				view = memberService.myInfo();// 무슨무슨 서비스에서 정보 조회하는 메소드
				break;
			case View.UPDATEINFO:
				view = memberService.updateInfo();// 무슨무슨 서비스에서 정보 업데이트하는 메소드
				break;
			case View.BALANCEMAIN:
				view = memberService.balancemain();// 잔액 출력
				break;
			case View.BALANCE:
				view = payService.balance();// 잔액 출력
				break;
			case View.WITHDRAWAL:
				view = memberService.withdrawal();// 회원탈퇴
				break;
			case View.LOGOUT:
				view = loginService.logOut();
				break;

			case View.APPLICATION:
				view = matchService.matchingApply1();
				break; // 매칭신청메소드
			case View.RESULT:
				view = matchService.matchRes();
				break; // 매칭결과/상태 메소드
			case View.MDATEDELETE:
				view = matchService.matchDel();
				break;

			case View.PLAICE:
				view = dCourcePage();
				break; // 장소메소드
			case View.REST:
				view = dCourcePage();
				break;// 맛집메소드

			case View.CARGE:
				view = payService.carge();
				break;// 충전 메소드
			case View.CARGEHIS:
				view = payService.showCashChargeHistory();
				break; // 충전 이력 메소드
			case View.REFUND:
				view = payService.refund();
				break;// 환불메소드
			case View.REFUNDHIS:
				view = payService.showCashRefundHistory();
				break;// 환불이력 메소드

			case View.CHECK:
				view = alarmService.checkMsg();
				break;
				
			case View.MASTERMAIN:
				view = masterPage();
				break;
			case View.MASTERSALE:
				view=masterPageService.printSalesAnalysis();
				break;
			case View.MASTERMEMSEARCH:
				view = masterPageService.viewAllMembers();
				break;
			case View.MASTERDATECOURCE:
				view = masterPageService.searchDSource();
				break;
//			case View.MASTERMATCHHISTORY:
//				view=masterPageService.matchHistory();				//추가
			default:
				break;
			}

		}
	}

	private int home() {
		System.out.println();
		System.out.printf("\n\t%40s", "✧･ﾟ: ✧･ﾟ✧.✧･ﾟ: ✧･ﾟ: ✧･ﾟ: ✧･ﾟ");
		System.out.println("\n\t\t♡　✩   𝑳𝒐𝒗𝒆 𝑫𝒆𝒔𝒕𝒊𝒏𝒚 . . ♡");
		System.out.println("\t\t ♡　･*･  ∧_∧ ⊹ ∧_∧･*･  ♡");
		System.out.println("\t\t♡　.　(｡･ω･)(･ω･｡)  ♡");
		System.out.println("\t\t  ♡　'･ |　つ♡と   |･' ♡");
		System.out.printf("\t%43s","♡　ﾟ'･｡°｡･ﾟ'　♡\n");
        System.out.println("  ═══❤︎═♡═══════════════════════════════════════════════════════════");		
        System.out.println("  ══════════════════════════════════════════════❤︎═♡══");
        System.out.printf("\t\t%19s","인연 매칭 프로그램\n");
        System.out.printf("\t\t%21s", "만 나 듀 오");
        System.out.println("\n  ═══❤︎═♡═══════════════════════════════════════════════════════════");		
        System.out.println("  ══════════════════════════════════════════════❤︎═♡══");
       
		System.out.println();
		System.out.println("\t   ─────────[𝗛𝗢𝗠𝗘 𝗠𝗘𝗡𝗨]────────");
		System.out.println("\t\t    ①. 로그인");
		System.out.println("\t\t    ②. 회원가입");
		System.out.println("\t\t    ③. 아이디 찾기");
		System.out.println("\t\t    ④. 비밀번호 찾기");
		System.out.println("\t\t    ⑤. 종료");
		System.out.println("\t   ───────────────────────────");
		
		System.out.printf("\t\t%18s","메뉴 선택 >>> ");

		// 입력한 메뉴 번호에 따라 번호에 해당하는 메뉴로 이동한다.
		switch (sc.nextInt()) {
		case 1:
			return View.SIGNIN;
		case 2:
			return View.SIGNUP;
		case 3:
			return View.FINDID;
		case 4:
			return View.FINDPW;
		case 5:
			return View.EXIT;
		default:
			System.out.printf("\n\t%33s","잘못된 선택입니다. 다시 선택해주세요.\n");
			return View.HOME;
		}
	}

	private int mainPage() {

		System.out.println();
		System.out.println("\t   ─────────[𝗠𝗔𝗜𝗡 𝗠𝗘𝗡𝗨]──────────");
		System.out.println("\t\t   ①. 개인정보관리");
		System.out.println("\t\t   ②. 매칭서비스");
		System.out.println("\t\t   ③. 데이트코스 추천");
		System.out.println("\t\t   ④. 결제서비스");
		System.out.println("\t\t   ⑤. 알림서비스");
		System.out.println("\t\t   ⑥. 로그아웃");
		System.out.println("\t   ────────────────────────────");
		
		System.out.printf("\t\t%18s","메뉴 선택 >>> ");

		switch (sc.nextInt()) {
		case 1:
			return View.MYPAGE;
		case 2:
			return View.MSERVICE;
		case 3:
			return View.COURCEINFO;
		case 4:
			return View.PAY;
		case 5:
			return View.ALARM;
		case 6:
			return View.LOGOUT;
		default:
			System.out.printf("\n\t%33s","잘못된 선택입니다. 다시 선택해주세요.\n");
			return View.MAIN;
		}
	}

	public int myPage() {
		
		System.out.println();
		System.out.println("\t   ───────────[𝗠𝗬𝗣𝗔𝗚𝗘]───────────");
		System.out.println("\t\t   ①. 내정보조회");
		System.out.println("\t\t   ②. 내정보수정");
		System.out.println("\t\t   ③. 캐시잔액확인");
		System.out.println("\t\t   ④. 회원탈퇴");
		System.out.println("\t\t   ⑤. 뒤로가기");
		System.out.println("\t   ─────────────────────────────");
		
		System.out.printf("\t\t%18s","메뉴 선택 >>> ");
		
		switch (sc.nextInt()) {
		case 1:
			return View.SELECTINFO;
		case 2:
			return View.UPDATEINFO;
		case 3:
			return View.BALANCEMAIN;
		case 4:
			return View.WITHDRAWAL;
		case 5:
			return View.MAIN;
		default:
			System.out.printf("\n\t%33s","잘못된 선택입니다. 다시 선택해주세요.\n");
			return View.MYPAGE;
		}
	}

	public int mServicePage() {
		
		System.out.println();
		System.out.println("\t   ───────[𝗠𝗔𝗧𝗖𝗛𝗜𝗡𝗚 𝗦𝗘𝗥𝗩𝗜𝗖𝗘]────────");
		System.out.println("\t\t   ①. 매칭신청");
		System.out.println("\t\t   ②. 매칭상태");
		System.out.println("\t\t   ③. 데이터삭제");
		System.out.println("\t\t   ④. 뒤로가기");
		System.out.println("\t   ──────────────────────────────");

		System.out.printf("\t\t%18s","메뉴 선택 >>> ");
		
		switch (sc.nextInt()) {
		case 1:
			return View.APPLICATION;
		case 2:
			return View.RESULT;
		case 3:
			return View.MDATEDELETE;
		case 4:
			return View.MAIN;
		default:
			System.out.printf("\n\t%33s","잘못된 선택입니다. 다시 선택해주세요.\n");
			return View.MSERVICE;
		}
	}

	private int dCourcePage() {
		
		System.out.println();
		System.out.println("\t   ───────[𝗗𝗔𝗧𝗘 𝗖𝗢𝗨𝗥𝗦𝗘 𝗜𝗡𝗙𝗢]───────");
		System.out.println("\t\t   ①. 맛집 정보");
		System.out.println("\t\t   ②. 장소 정보");
		System.out.println("\t\t   ③. 뒤로가기");
		System.out.println("\t   ─────────────────────────────");

		System.out.printf("\t\t%18s","메뉴 선택 >>> ");

		switch (sc.nextInt()) {
		case 1:
			int resultmres = datecourceService.mRestaurant();
			if (resultmres == View.COURCEINFO) {
				return View.COURCEINFO;
			} else {
				return dCourcePage();
			}
			
		case 2:
			int resultpla = datecourceService.hPlace();
			if (resultpla == View.COURCEINFO) {
				return View.COURCEINFO;
			} else {
				return dCourcePage();
			}
			
		case 3:
			return View.MAIN;
		default:
			System.out.printf("\n\t%33s","잘못된 선택입니다. 다시 선택해주세요.\n");
			return View.COURCEINFO;
		}
	}

	public int payPage() {
		
		System.out.println();
		System.out.println("\t   ─────────[𝗣𝗔𝗬 𝗦𝗘𝗥𝗩𝗜𝗖𝗘]─────────");
		System.out.println("\t\t   ①. 캐시잔액");
		System.out.println("\t\t   ②. 캐시충전");
		System.out.println("\t\t   ③. 충전이력");
		System.out.println("\t\t   ④. 환불신청");
		System.out.println("\t\t   ⑤. 환불이력");
		System.out.println("\t\t   ⑥. 뒤로가기");
		System.out.println("\t   ────────────────────────────");

		System.out.printf("\t\t%18s","메뉴 선택 >>> ");

		switch (sc.nextInt()) {
		case 1:
			return View.BALANCE;
		case 2:
			return View.CARGE;
		case 3:
			return View.CARGEHIS;
		case 4:
			return View.REFUND;
		case 5:
			return View.REFUNDHIS;
		case 6:
			return View.MAIN;
		default:
			System.out.printf("\n\t%33s","잘못된 선택입니다. 다시 선택해주세요.\n");
			return View.PAY;
		}
	}

	public int alarmPage() {
		
		System.out.println();
		System.out.println("\t   ────────[𝗔𝗟𝗔𝗥𝗠 𝗦𝗘𝗥𝗩𝗜𝗖𝗘]─────────");
		System.out.println("\t\t   ①. 알람확인");
		System.out.println("\t\t   ②. 뒤로가기");
		System.out.println("\t   ─────────────────────────────");

		System.out.printf("\t\t%18s","메뉴 선택 >>> ");
		
		switch (sc.nextInt()) {
		case 1:
			return View.CHECK;
		case 2:
			return View.MAIN;
		default:
			System.out.printf("\n\t%33s","잘못된 선택입니다. 다시 선택해주세요.\n");
			return View.ALARM;
		}
	}

	public int masterPage() {

		System.out.println();
		System.out.println("\t   ───────[𝗜𝗡𝗙𝗢 𝗠𝗔𝗡𝗔𝗚𝗘𝗠𝗘𝗡𝗧]───────");
		System.out.println("\t\t    ①. 데이트코스 관리");
		System.out.println("\t\t    ②. 매출확인");
		System.out.println("\t\t    ③. 전체회원조회");
//		System.out.println("\t\t    ④. 회원매칭이력");						//추가
		System.out.println("\t\t    ④. 로그아웃");
		System.out.println("\t   ─────────────────────────────");

		System.out.printf("\t\t%18s", "메뉴 선택 >>> ");

		switch (sc.nextInt()) {
		case 1:
			return View.MASTERDATECOURCE;
		case 2:
			return View.MASTERSALE;
		case 3:
			return View.MASTERMEMSEARCH;
//		case 4:
//			return View.MASTERMATCHHISTORY;				//추가
		case 4:
			return View.LOGOUT;
		default:
			System.out.printf("\n\t%33s", "잘못된 선택입니다. 다시 선택해주세요.\n");
			return View.MASTERMAIN;
		}
	}
}
