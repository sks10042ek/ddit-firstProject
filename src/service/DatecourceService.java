package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controller.View;
import dao.DateCourceDAO;

public class DatecourceService {
	private Scanner sc = new Scanner(System.in);
	private static DatecourceService instance = null;
	static int pageNo = 0;

	private DatecourceService() {
	}

	public static DatecourceService getInstance() {
		if (instance == null)
			instance = new DatecourceService();
		return instance;
	}

	DateCourceDAO dao = DateCourceDAO.getInstance();

	public int mRestaurant() {
		String rloc;
		String rcat;
		while (true) {
			System.out.println("\n\t   ────── [ 맛집  정보 검색 ] ─────── ");

			System.out.print("\n      지역(서울/부산/대구/인천/대전/광주/울산/세종) : ");
			rloc = sc.nextLine();

			List<String> rlist = new ArrayList<>(Arrays.asList("서울", "대전", "대구", "부산", "광주", "울산", "인천", "세종"));
			if (rlist.contains(rloc)) {
				break;
			} else {
				System.out.print("\n\t        잘못된 입력입니다. 다시 입력해주세요.\n");
				pageNo = View.COURCEINFO;
			}
		}
		while (true) {
			System.out.print("\n\t  카테고리(양식/중식/일식/한식/카페) : ");
			rcat = sc.nextLine();

			List<String> list = new ArrayList<>(Arrays.asList("양식", "중식", "일식", "한식", "카페"));
			if (list.contains(rcat)) {
				break;
			} else {
				System.out.println("\n\t       잘못된 입력입니다. 다시 입력해주세요. \n");
				pageNo = View.COURCEINFO;
			}
		}
		List<Map<String, Object>> restaurantList = dao.select(rloc, rcat);

		if (!restaurantList.isEmpty()) {
			recommendRestaurantCourse(restaurantList);

			System.out.println("\n\t\t 1.다시 검색하기  2.뒤로가기");
			System.out.printf("\t\t%18s","메뉴 선택 >>> ");

			int choice = sc.nextInt();
			sc.nextLine();

			if (choice == 1) {
				pageNo = mRestaurant();
			} else if (choice == 2) {
				pageNo = View.COURCEINFO;
			} else {
				System.out.print("\n\t        잘못된 입력입니다. 다시 입력해주세요.\n");
			}
		} else {
			System.out.println("\n\t 조회된 맛집 정보가 없거나, 잘못된 입력입니다.\n");
		}
		return pageNo;
	}

	private void recommendRestaurantCourse(List<Map<String, Object>> restaurantList) {
		System.out.printf("\n\t\t%22s","[ 추천 리스트 ]\n");
	    System.out.printf("\n%4s\t%-15s\t%-10s\t%-20s\t%-5s\n", "순번", "상호명", "카테고리", "주소", "평점");
	    System.out.println("───────────────────────────────────────────────────────────────────────────");

	    int index = 1;
	    for (Map<String, Object> restaurantInfo : restaurantList) {
	        String restaurantName = (String) restaurantInfo.get("RES_NAME");
	        String category = (String) restaurantInfo.get("RES_CAT");
	        String address = (String) restaurantInfo.get("RES_ADDRESS");
	        String rating = (String) restaurantInfo.get("RES_GRADE");

	        System.out.printf("%4s\t%-15s\t%-10s\t%-30s\t%-5s\n", index, restaurantName, category, address, rating);
	        index++;
	        System.out.println("───────────────────────────────────────────────────────────────────────────");
	    }
	}


	public int hPlace() {

		String hloc;
		String hinout;

		while (true) {
			System.out.println("\n\t   ────── [ 장소  정보 검색 ] ─────── ");

			System.out.print("\n      지역(서울/부산/대구/인천/대전/광주/울산/세종) : ");
			hloc = sc.nextLine();

			List<String> rlist = new ArrayList<>(Arrays.asList("서울", "대전", "대구", "부산", "광주", "울산", "인천", "세종"));
			if (rlist.contains(hloc)) {
				break;
			} else {
				System.out.print("\n\t        잘못된 입력입니다. 다시 입력해주세요.\n");
				pageNo = View.COURCEINFO;
			}
		}

		while (true) {
			System.out.print("\n\t\t    실내/실외 : ");
			hinout = sc.nextLine();

			List<String> list = new ArrayList<>(Arrays.asList("실내", "실외"));
			if (list.contains(hinout)) {
				break;
			} else {
				System.out.print("\n\t 잘못된 입력입니다. 다시 입력해주세요.(실내/실외)\n");
				pageNo = View.COURCEINFO;
			}
		}

		List<Map<String, Object>> placeList = dao.selectPlace(hloc, hinout);

		if (!placeList.isEmpty()) {
			recommendPlaceCourse(placeList);

			System.out.println("\n\t\t 1.다시 검색하기  2.뒤로가기");
			System.out.printf("\t\t%18s","메뉴 선택 >>> ");

			int choice = sc.nextInt();
			sc.nextLine();
			System.out.println();

			if (choice == 1) {
				pageNo = hPlace();
			} else if (choice == 2) {
				pageNo = View.COURCEINFO;
			} else {
				System.out.print("\n\t        잘못된 입력입니다. 다시 입력해주세요.\n");
			}
		} else {
			System.out.println("\n\t 조회된 장소 정보가 없거나, 잘못된 입력입니다.\n");
		}

		return pageNo;
	}

	private void recommendPlaceCourse(List<Map<String, Object>> placeList) {
		System.out.printf("\n\t\t%18s","[ 추천 리스트 ]\n");
	    System.out.printf("\n\t%20s\t%4s\t%10s", "순번", "지역", "장소명");
	    System.out.println("\n\t──────────────────────────────────────────");

	    int index = 1;
	    for (Map<String, Object> placeInfo : placeList) {
	        String placeloc = (String) placeInfo.get("H_LOC");
	        String placename = (String) placeInfo.get("H_NAME");

	        System.out.printf("\t%11s\t%4s\t%11s\n", index, placeloc, placename);

	        index++;
	        System.out.println("\t──────────────────────────────────────────");
	    }
	}
}
