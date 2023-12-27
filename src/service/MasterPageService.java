package service;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controller.View;
import dao.DateCourceDAO;
import dao.MemberDAO;
import dao.PayDAO;

public class MasterPageService {

	static Scanner sc = new Scanner(System.in);
	private static MasterPageService instance;
	static int pageNo = 0;

	private MasterPageService() {
	}

	public static MasterPageService getInstance() {
		if (instance == null)
			instance = new MasterPageService();
		return instance;
	}

	PayDAO dao = PayDAO.getInstance();
	DateCourceDAO datedao = DateCourceDAO.getInstance();
	MemberDAO memberDAO = MemberDAO.getInstance();

	public int printSalesAnalysis() {
		int totalSales = dao.selectAllSales();
		int totalExpenses = dao.selectAllExpenses();
		int profit = totalSales - totalExpenses;

		System.out.printf("\n\t%32s", "───────── [ 매출 분석 ] ─────────");
		System.out.printf("\n\t\t%18s", "총 매출 : " + profit);
		System.out.printf("\n\t\t%18s", "총 지출 : " + totalExpenses);
		System.out.printf("\n\t\t%18s", "순수익 : " + totalSales);
		System.out.println();

		return View.MASTERMAIN;
	}

	// ★
	public int viewAllMembers() {
		List<Map<String, Object>> members = memberDAO.getAllMembers();

		if (members.isEmpty()) {
			System.out.println("등록된 회원이 없습니다.");
		} else {
			System.out.printf("\n\t%31s", "──────── [전체 회원 목록] ────────\n");
			for (Map<String, Object> member : members) {
				String userId = (String) member.get("U_ID");
				String userName = (String) member.get("U_NAME");
				String userphone = (String) member.get("U_PHONE");
				int userage = Integer.parseInt(member.get("U_AGE").toString());
				String usergender = (String) member.get("U_GENDER");
				String useredu = (String) member.get("U_EDU");
				String userjob = (String) member.get("U_JOB");
				String usermyself = (String) member.get("U_MYSELF");
				String usermbti = (String) member.get("U_MBTI");
				String usermuerid = (String) member.get("M_USERID");
				String userloc = (String) member.get("U_LOC");
				int userheight = Integer.parseInt(member.get("U_HEIGHT").toString());
				String usersmk = (String) member.get("U_SMK");
				String userdrk = (String) member.get("U_DRK");
				int userucash = Integer.parseInt(member.get("U_CASH").toString());
				String userstate = (String) member.get("M_STATE");

				// 기타 회원 정보 출력
				System.out.printf("\n\t\t%16s", "[ " + userName + "님의 정보 ]\n");
				System.out.println("\n회원ID : " + userId + "\t이름 : " + userName + "\t전화번호 : " + userphone);
				System.out.println("───────────────────────────────────────────────────────");
				System.out.println("나이 : " + userage + "\t성별 : " + usergender + "\t학력 : " + useredu);
				System.out.println("───────────────────────────────────────────────────────");
				System.out.println("직업 : " + userjob + "\tMBTI : " + usermbti + "\t지역 : " + userloc);
				System.out.println("───────────────────────────────────────────────────────");
				System.out.println("흡연유무 : " + usersmk + "\t음주유무 : " + userdrk + "키 : " + userheight);
				System.out.println("───────────────────────────────────────────────────────");
				System.out.println("자기소개 : " + usermyself);
				System.out.println("───────────────────────────────────────────────────────");
				System.out.println("매칭상대아이디 : " + usermuerid + "\t매칭상태 : " + userstate + "\t보유캐시 : " + userucash);
				System.out.println("───────────────────────────────────────────────────────");
				System.out.println();
				System.out.println();
			}
		}
		return View.MASTERMAIN;
	}

	public int searchDSource() {
		System.out.println("\n\t   ─────────[데이트코스 관리]────────");
		System.out.println("\t\t    ①. 맛집리스트 관리");
		System.out.println("\t\t    ②. 장소리스트 관리");
		System.out.println("\t\t    ③. 뒤로가기");
		System.out.println("\t   ─────────────────────────────");
		System.out.printf("\t\t%18s", "메뉴 선택 >>> ");
		int choice = sc.nextInt();
		sc.nextLine();

		switch (choice) {
		case 1:
			return showRestaurantList();
		case 2:
			return showPlaceList();
		case 3:
			return View.MASTERMAIN;
		default:
			System.out.printf("\n\t%33s", "잘못된 선택입니다. 다시 선택해주세요.\n");
			return View.MASTERDATECOURCE;
		}
	}

	public int showRestaurantList() {// ★
		System.out.println("\n\n\t\t\t\t\t [[ 맛집 리스트 ]]\n");

		List<Map<String, Object>> restaurantList = datedao.selectMRes();

		if (restaurantList.isEmpty()) {
			System.out.println("등록된 맛집이 없습니다.");
			return View.MASTERDATECOURCE;
		} else {
			System.out.printf("%-4s\t%-10s\t%-4s\t%-13s\t%-20s\t%-25s\t%-10s\n", "순번", "맛집코드", "지역", "카테고리", "상호명",
					"주소", "평점");
			System.out.println(
					"────────────────────────────────────────────────────────────────────────────────────────────────────");

			int index = 1;
			for (Map<String, Object> restaurantInfo : restaurantList) {
				String restaurantCode = (String) restaurantInfo.get("RES_CODE");
				String restaurantLocal = (String) restaurantInfo.get("RES_LOC");
				String category = (String) restaurantInfo.get("RES_CAT");
				String restaurantName = (String) restaurantInfo.get("RES_NAME");
				String restaurantAddress = (String) restaurantInfo.get("RES_ADDRESS");
				String restaurantGrade = (String) restaurantInfo.get("RES_GRADE");

				System.out.printf("%-4d\t%-10s\t%-4s\t%-13s\t%-20s\t%-25s\t%-10s\n", index, restaurantCode,
						restaurantLocal, category, restaurantName, restaurantAddress, restaurantGrade);
				index++;
				System.out.println(
						"────────────────────────────────────────────────────────────────────────────────────────────────────");
			}
		}

		System.out.println("\n\t─────────────[맛집정보 관리]─────────────");
		System.out.println("\n\t1.데이터 추가  2.데이터 수정  3.데이터 삭제  4.뒤로가기");
		System.out.println("\n\t─────────────────────────────────────");
		System.out.printf("\t\t%18s", "메뉴 선택 >>> ");

		int choice = sc.nextInt();
		sc.nextLine();

		switch (choice) {

		case 1:
			return insertRestaurant();
		case 2:
			return updateRestaurant();
		case 3:
			return deleteRestaurant();
		case 4:
			return View.MASTERDATECOURCE;
		default:
			System.out.printf("\n\t%33s", "잘못된 선택입니다. 다시 선택해주세요.\n");
			return showRestaurantList();
		}
	}

	public int insertRestaurant() {
		System.out.println("\n\t────────────[맛집 데이터 추가]────────────");

		String restaurantCode = null;
		boolean isDuplicate = true;

		while (isDuplicate) {
			System.out.print("\n\t\t       맛집 코드 : ");
			restaurantCode = sc.nextLine().toUpperCase();

			isDuplicate = datedao.checkDuplicateRestaurantCode(restaurantCode);
			if (isDuplicate) {
				System.out.print("\n\t      중복된 맛집 코드입니다. 다른 코드를 입력해주세요.\n");
			}
		}

		System.out.print("\n\t\t       지역(ex.서울) : ");
		String restaurantLocal = sc.nextLine();

		System.out.print("\n\t    카테고리(ex.양식/한식/중식/일식/카페) : ");
		String category = sc.nextLine();

		System.out.print("\n\t\t           상호명 : ");
		String restaurantName = sc.nextLine();

		System.out.print("\n\t\t           주소 : ");
		String restaurantAddress = sc.nextLine();

		System.out.print("\n\t\t        평점(/5.0) : ");
		String restaurantGrade = sc.nextLine();

		boolean success = datedao.insertMRes(restaurantCode, restaurantLocal, category, restaurantName,
				restaurantAddress, restaurantGrade);

		if (success) {
			System.out.println("\n\t\t  맛집 데이터가 추가되었습니다.");
		} else {
			System.out.println("\n\t\t  맛집 데이터 추가가 실패했습니다.");
		}
		return showRestaurantList();
	}

	public int updateRestaurant() {
		System.out.println("\n\t────────────[맛집 데이터 수정]────────────");

		while (true) {
			System.out.print("\n\t\t   수정할 맛집 코드 : ");
			String restaurantCode = sc.nextLine().toUpperCase();
			;

			if (!datedao.checkDuplicateRestaurantCode(restaurantCode)) {
				System.out.println("\n\t 존재하지 않는 맛집코드입니다. 다시 입력해주세요.");
			} else {
				System.out.print("\n\t\t      지역(ex.서울) : ");
				String restaurantLocal = sc.nextLine();

				System.out.print("\n\t   카테고리(ex.양식/한식/중식/일식/카페) : ");
				String category = sc.nextLine();

				System.out.print("\n\t\t     상호명 : ");
				String restaurantName = sc.nextLine();

				System.out.print("\n\t\t      주소 : ");
				String restaurantAddress = sc.nextLine();

				System.out.print("\n\t\t    평점(/5.0) : ");
				String restaurantGrade = sc.nextLine();

				boolean success = datedao.updateMRes(restaurantCode, restaurantLocal, category, restaurantName,
						restaurantAddress, restaurantGrade);

				if (success) {
					System.out.println("\n\t\t선택한 맛집 데이터가 수정되었습니다.");
				} else {
					System.out.println("\n\t\t선택한 맛집 데이터 수정에 실패하였습니다.");
				}
				return showRestaurantList();
			}
		}
	}

	public int deleteRestaurant() {
		System.out.println("\n\t────────────[맛집 데이터 삭제]────────────");

		System.out.print("\n\t\t    삭제할 맛집 코드 : ");
		String restaurantCode = sc.nextLine().toUpperCase();

		boolean success = datedao.deleteMRes(restaurantCode);

		if (success) {
			System.out.println("\n\t\t선택한 맛집 데이터가 삭제되었습니다.");
		} else {
			System.out.println("\n\t\t선택한 맛집 데이터 삭제에 실패하였습니다.");
		}
		return showRestaurantList();
	}

	public int showPlaceList() { // ★
		System.out.println("\n\n\t\t\t     [[ 장소 리스트 ]]\n");

		List<Map<String, Object>> placeList = datedao.selectMPlace();

		if (placeList.isEmpty()) {
			System.out.println("등록된 장소가 없습니다.");
			return View.MASTERDATECOURCE;
		} else {
			System.out.printf("%20s\t%12s\t%4s\t%13s\t%10s\n", "순번", "장소코드", "지역", "실내/실외", "장소명");
			System.out.println("\t──────────────────────────────────────────────────────────");

			int index = 1;
			for (Map<String, Object> placeInfo : placeList) {
				String placeCode = (String) placeInfo.get("H_CODE");
				String placeLocal = (String) placeInfo.get("H_LOC");
				String placeInOut = (String) placeInfo.get("H_INOUT");
				String placeName = (String) placeInfo.get("H_NAME");

				System.out.printf("%11d\t%10s\t%4s\t%13s\t%11s\n", index, placeCode, placeLocal, placeInOut, placeName);
				index++;
				System.out.println("\t──────────────────────────────────────────────────────────");
			}

			System.out.println("\n\t─────────────[장소정보 관리]─────────────");
			System.out.println("\n\t1.데이터 추가  2.데이터 수정  3.데이터 삭제  4.뒤로가기");
			System.out.println("\n\t─────────────────────────────────────");
			System.out.printf("\t\t%18s", "메뉴 선택 >>> ");
			int choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
			case 1:
				return insertPlace();
			case 2:
				return updatePlace();
			case 3:
				return deletePlace();
			case 4:
				return View.MASTERDATECOURCE;
			default:
				System.out.printf("\n\t%33s", "잘못된 선택입니다. 다시 선택해주세요.\n");
				return showPlaceList();
			}
		}
	}

	public int insertPlace() {
		System.out.println("\n\t────────────[장소 데이터 추가]────────────");

		String placeCode = null;
		boolean isDuplicate = true;

		while (isDuplicate) {
			System.out.print("\n\t\t        장소 코드 : ");
			placeCode = sc.nextLine().toUpperCase();

			isDuplicate = datedao.checkDuplicatePlaceCode(placeCode);
			if (isDuplicate) {
				System.out.print("\n\t      중복된 장소 코드입니다. 다른 코드를 입력해주세요.\n");
			}
		}

		System.out.print("\n\t\t      지역(ex.서울) : ");
		String placeLocal = sc.nextLine();

		System.out.print("\n\t\t      실내/외(ex.실내) : ");
		String placeInOut = sc.nextLine();

		System.out.print("\n\t\t      장소명 : ");
		String placeName = sc.nextLine();

		boolean success = datedao.insertHPlace(placeCode, placeLocal, placeInOut, placeName);

		if (success) {
			System.out.println("\n\t\t  장소 데이터가 추가되었습니다.");
		} else {
			System.out.println("\n\t\t  장소 데이터 추가가 실패했습니다.");
		}
		return showPlaceList();
	}

	public int updatePlace() {
		while (true) {
			System.out.println("\n\t────────────[장소 데이터 수정]────────────");
			System.out.print("\n\t\t     수정할 장소 코드 : ");
			String placeCode = sc.nextLine().toUpperCase();

			if (!datedao.checkDuplicatePlaceCode(placeCode)) {
				System.out.println("\n\t  존재하지 않는 장소코드입니다. 다시 입력해주세요.");
			} else {
				System.out.print("\n\t\t      지역(ex.서울) : ");
				String placeLocal = sc.nextLine();

				System.out.print("\n\t\t      실내/외(ex.실내) : ");
				String placeInOut = sc.nextLine();

				System.out.print("\n\t\t      장소명 : ");
				String placeName = sc.nextLine();

				boolean success = datedao.updateHPlace(placeCode, placeLocal, placeInOut, placeName);

				if (success) {
					System.out.println("\n\t\t선택한 장소 데이터가 수정되었습니다.");
				} else {
					System.out.println("\n\t\t선택한 장소 데이터 수정에 실패하였습니다.");
				}
				return showPlaceList();
			}
		}
	}

	public int deletePlace() {
		System.out.println("\n\t────────────[장소 데이터 삭제]────────────");

		System.out.print("\n\t\t      삭제할 장소 코드 : ");
		String placeCode = sc.nextLine().toUpperCase();

		boolean success = datedao.deleteHPlace(placeCode);

		if (success) {
			System.out.println("\n\t\t선택한 장소 데이터가 삭제되었습니다.");
		} else {
			System.out.println("\n\t\t선택한 장소 데이터 삭제에 실패하였습니다.");
		}
		return showPlaceList();
	}

	// 전체회원 매칭이력 조회
//	public int matchHistory() {
//		System.out.println("\n\t────────────[매칭이력 조회]────────────");
//
//		List<Map<String, Object>> members = memberDAO.getMatchHistory();
//
//		if (members.isEmpty()) {
//			System.out.println("등록된 이력이 없습니다.");
//		} else {
//			System.out.println("[전체 회원 목록]");
//			for (Map<String, Object> member : members) {
//				String rNo = (String) member.get("R_NO");
//				String uId = (String) member.get("U_ID");
//				String mId = (String) member.get("M_USERID");
//				String mState = (String) member.get("M_STATE");
//				String rDate = (String) member.get("R_DATE");
//
//				System.out.println("매치코드: " + rNo + "\t매칭유저: " + uId + "\t매칭유저: " + mId);
//				System.out.println("상태: " + mState + "\t매칭날짜: " + rDate);
//				System.out.println();
//				System.out.println();
//			}
//		}
//		return View.MASTERMAIN;
//
//	}
}
