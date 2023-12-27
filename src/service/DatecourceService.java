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
			System.out.println("\n\t   ������������ [ ����  ���� �˻� ] �������������� ");

			System.out.print("\n      ����(����/�λ�/�뱸/��õ/����/����/���/����) : ");
			rloc = sc.nextLine();

			List<String> rlist = new ArrayList<>(Arrays.asList("����", "����", "�뱸", "�λ�", "����", "���", "��õ", "����"));
			if (rlist.contains(rloc)) {
				break;
			} else {
				System.out.print("\n\t        �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.\n");
				pageNo = View.COURCEINFO;
			}
		}
		while (true) {
			System.out.print("\n\t  ī�װ�(���/�߽�/�Ͻ�/�ѽ�/ī��) : ");
			rcat = sc.nextLine();

			List<String> list = new ArrayList<>(Arrays.asList("���", "�߽�", "�Ͻ�", "�ѽ�", "ī��"));
			if (list.contains(rcat)) {
				break;
			} else {
				System.out.println("\n\t       �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���. \n");
				pageNo = View.COURCEINFO;
			}
		}
		List<Map<String, Object>> restaurantList = dao.select(rloc, rcat);

		if (!restaurantList.isEmpty()) {
			recommendRestaurantCourse(restaurantList);

			System.out.println("\n\t\t 1.�ٽ� �˻��ϱ�  2.�ڷΰ���");
			System.out.printf("\t\t%18s","�޴� ���� >>> ");

			int choice = sc.nextInt();
			sc.nextLine();

			if (choice == 1) {
				pageNo = mRestaurant();
			} else if (choice == 2) {
				pageNo = View.COURCEINFO;
			} else {
				System.out.print("\n\t        �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.\n");
			}
		} else {
			System.out.println("\n\t ��ȸ�� ���� ������ ���ų�, �߸��� �Է��Դϴ�.\n");
		}
		return pageNo;
	}

	private void recommendRestaurantCourse(List<Map<String, Object>> restaurantList) {
		System.out.printf("\n\t\t%22s","[ ��õ ����Ʈ ]\n");
	    System.out.printf("\n%4s\t%-15s\t%-10s\t%-20s\t%-5s\n", "����", "��ȣ��", "ī�װ�", "�ּ�", "����");
	    System.out.println("������������������������������������������������������������������������������������������������������������������������������������������������������");

	    int index = 1;
	    for (Map<String, Object> restaurantInfo : restaurantList) {
	        String restaurantName = (String) restaurantInfo.get("RES_NAME");
	        String category = (String) restaurantInfo.get("RES_CAT");
	        String address = (String) restaurantInfo.get("RES_ADDRESS");
	        String rating = (String) restaurantInfo.get("RES_GRADE");

	        System.out.printf("%4s\t%-15s\t%-10s\t%-30s\t%-5s\n", index, restaurantName, category, address, rating);
	        index++;
	        System.out.println("������������������������������������������������������������������������������������������������������������������������������������������������������");
	    }
	}


	public int hPlace() {

		String hloc;
		String hinout;

		while (true) {
			System.out.println("\n\t   ������������ [ ���  ���� �˻� ] �������������� ");

			System.out.print("\n      ����(����/�λ�/�뱸/��õ/����/����/���/����) : ");
			hloc = sc.nextLine();

			List<String> rlist = new ArrayList<>(Arrays.asList("����", "����", "�뱸", "�λ�", "����", "���", "��õ", "����"));
			if (rlist.contains(hloc)) {
				break;
			} else {
				System.out.print("\n\t        �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.\n");
				pageNo = View.COURCEINFO;
			}
		}

		while (true) {
			System.out.print("\n\t\t    �ǳ�/�ǿ� : ");
			hinout = sc.nextLine();

			List<String> list = new ArrayList<>(Arrays.asList("�ǳ�", "�ǿ�"));
			if (list.contains(hinout)) {
				break;
			} else {
				System.out.print("\n\t �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.(�ǳ�/�ǿ�)\n");
				pageNo = View.COURCEINFO;
			}
		}

		List<Map<String, Object>> placeList = dao.selectPlace(hloc, hinout);

		if (!placeList.isEmpty()) {
			recommendPlaceCourse(placeList);

			System.out.println("\n\t\t 1.�ٽ� �˻��ϱ�  2.�ڷΰ���");
			System.out.printf("\t\t%18s","�޴� ���� >>> ");

			int choice = sc.nextInt();
			sc.nextLine();
			System.out.println();

			if (choice == 1) {
				pageNo = hPlace();
			} else if (choice == 2) {
				pageNo = View.COURCEINFO;
			} else {
				System.out.print("\n\t        �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.\n");
			}
		} else {
			System.out.println("\n\t ��ȸ�� ��� ������ ���ų�, �߸��� �Է��Դϴ�.\n");
		}

		return pageNo;
	}

	private void recommendPlaceCourse(List<Map<String, Object>> placeList) {
		System.out.printf("\n\t\t%18s","[ ��õ ����Ʈ ]\n");
	    System.out.printf("\n\t%20s\t%4s\t%10s", "����", "����", "��Ҹ�");
	    System.out.println("\n\t������������������������������������������������������������������������������������");

	    int index = 1;
	    for (Map<String, Object> placeInfo : placeList) {
	        String placeloc = (String) placeInfo.get("H_LOC");
	        String placename = (String) placeInfo.get("H_NAME");

	        System.out.printf("\t%11s\t%4s\t%11s\n", index, placeloc, placename);

	        index++;
	        System.out.println("\t������������������������������������������������������������������������������������");
	    }
	}
}
