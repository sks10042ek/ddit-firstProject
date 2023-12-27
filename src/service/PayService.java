package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controller.Controller;
import controller.View;
import dao.PayDAO;

//��
public class PayService {
	Scanner sc = new Scanner(System.in);

	private static PayService instance;
	static int pageNo = 0;

	public PayService() {
	}

	public static PayService getInstance() {
		if (instance == null)
			instance = new PayService();
		return instance;
	}

	PayDAO payDao = PayDAO.getInstance(); // Dao�� ������ �����ؼ� ����� ��ȯ���ִ� Ŭ����

	// ĳ���ܾ�Ȯ��
	public int balance() {
		Map<String, Object> member = (Map<String, Object>) (Controller.sessionStorage.get("loginInfo"));

		int cashBalance = payDao.getCashBalance(member.get("U_ID"));
		System.out.println("\n\t\t ĳ�� �ܾ� : " + cashBalance);

		return View.PAY;
	}

	// ĳ������
	public int carge() {

		Map<String, Object> member = (Map<String, Object>) (Controller.sessionStorage.get("loginInfo"));
		int moneyBalance = payDao.getMoneyBalance(member.get("U_ID"));
		int cashBalance = payDao.getCashBalance(member.get("U_ID"));
		int tempMoney = payDao.getMoneyBalance(member.get("U_MONEY"));
		int tempCash = payDao.getCashBalance(member.get("U_CASH"));

		System.out.printf("\n\t%37s","�� �����ڻ���" + moneyBalance + "���Դϴ�.\n");
		System.out.printf("\t%34s","�� ������ �ݾ��� �Է��ϼ��� : ");
		int temp = sc.nextInt();
		System.out.println();

		System.out.printf("\t%30s","::: ������ �����ϰڽ��ϱ�?(Y/N) : ");
		String flag = sc.next();
		if (flag.equalsIgnoreCase("y")) {
			System.out.println("\t\t::: ������ �����մϴ�...");
			for (int j = 0; j < 10; j++) {
                System.out.print("��");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
			for (int i = 0; i < 10; i++) {
				System.out.print("��");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(" 100%");
			System.out.println();

			if (moneyBalance >= temp) {

				// MEMBER ���̺� ������, ����ĳ�� ����
				String updateSql = "UPDATE U_MEMBER SET U_MONEY = (U_MONEY - " + temp + "), U_CASH = (U_CASH + " + temp
						+ ") WHERE U_ID = '" + String.valueOf((member.get("U_ID"))) + "'";

				// PAY���̺� �������� �߰�
				String insertSql = "INSERT INTO PAY (P_PAYNO, U_ID, P_DATE, P_MONEY, M_SALE) "
						+ "VALUES ('P' || TO_CHAR(SYSDATE, 'YYMMDD') || LPAD((SEQ_PAYNO.NEXTVAL), 4, '0'), " + "'"
						+ String.valueOf(member.get("U_ID")) + "', sysdate, " + temp + ", " + temp + ")";

				payDao.getCarge(updateSql);
				payDao.getCarge(insertSql);
				member.put("U_MONEY", (tempMoney - temp));
				member.put("U_CASH", (tempCash + temp));

				System.out.println();
				System.out.printf("\t\t%20s","������ �Ϸ�Ǿ����ϴ�!");
				System.out.println("");

				// �ڡڡڡ� �����ܾ� �����ֱ� ��������

				System.out.println("\n\t ��������������������������[������ �����ܾ�]����������������������");

				System.out.printf("\t%21s","�� ������ :  " + payDao.getMoneyBalance(member.get("U_MONEY")) + "��");

				System.out.printf("\n\t%18s","�� ĳ�� : " + payDao.getCashBalance(member.get("U_CASH")) + "ĳ��");
				System.out.println("\n\t ������������������������������������������������������������������������");
				System.out.println();

			} else {
				System.out.printf("\t%29s","::: ������ �ݾ��� �����ݺ��� �����ϴ�.");
				System.out.printf("\n\t%22s","::: ������ ��ҵǾ����ϴ�.\n");
			}
		}
		return View.PAY;
	}

	// �����̷���ȸ
	public int showCashChargeHistory() {
		Map<String, Object> mem = (Map<String, Object>) Controller.sessionStorage.get("loginInfo");
		String userId = (String) mem.get("U_ID");
		List<Map<String, Object>> list = payDao.getCashChargeHistory(userId);

		if (list.isEmpty()) {
			System.out.println("\t\tĳ�� ���� ������ �����ϴ�.");
		} else {
			System.out.println("\n\t========================================");
			System.out.printf("\n\t\t%15s","[ĳ�� ���� ����]");
			System.out.println("\n\t��������������������������������������������������������������������������������");
			System.out.printf("\t   %s        \t%s   \t\t%s%n", "������ȣ", "��������", "�����ݾ�");
			System.out.println("\t��������������������������������������������������������������������������������");
			for (int j = 0; j < list.size(); j++) {
				String payNo = String.valueOf(list.get(j).get("P_PAYNO"));
				String date = String.valueOf(list.get(j).get("P_DATE"));
				int money = Integer.parseInt(String.valueOf(list.get(j).get("P_MONEY")));

				System.out.printf("%-12s %12s %12d%n", payNo, date, money);

			}
			System.out.println("\n\t��������������������������������������������������������������������������������");
			System.out.println();
			System.out.println();
		}
		return View.PAY;
	}

	// ĳ��ȯ��
	public int refund() {
		Map<String, Object> member = (Map<String, Object>) (Controller.sessionStorage.get("loginInfo"));
		int moneyBalance = payDao.getMoneyBalance(member.get("U_ID"));
		int cashBalance = payDao.getCashBalance(member.get("U_ID"));
		int tempCash = payDao.getCashBalance(member.get("U_CASH"));
		int tempMoney = payDao.getMoneyBalance(member.get("U_MONEY"));

		System.out.println("\n\t\t�� ����ĳ�� : " + cashBalance);
		System.out.print("\t\t�� ȯ���� �ݾ��� �Է��ϼ��� : ");
		int temp = sc.nextInt();
		System.out.println();

		System.out.printf("\t%30s","::: ������ ȯ���ϰڽ��ϱ�?(Y/N) : ");
		String flag = sc.next();
		if (flag.equalsIgnoreCase("y")) {
			System.out.println("\t\t::: ȯ���� �����մϴ�...");
			for (int j = 0; j < 10; j++) {
                System.out.print("��");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
			for (int i = 0; i < 10; i++) {
				System.out.print("��");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(" 100%");
			System.out.println();

			if (cashBalance >= temp) {
				// MEMBER ���̺� ������, ����ĳ�� ����

				String updateSql = "UPDATE U_MEMBER SET U_MONEY = (U_MONEY + " + temp + "), U_CASH = (U_CASH - " + temp
						+ ") WHERE U_ID = '" + String.valueOf((member.get("U_ID"))) + "'";

				// PAY���̺� �������� �߰�
				String insertSql = "INSERT INTO REFUND (P_RPAYNO, U_ID, P_RDATE, P_RMONEY, M_SALE) "
						+ "VALUES ('RP' || TO_CHAR(SYSDATE, 'YYMMDD') || LPAD((SEQ_RPAYNO.NEXTVAL), 4, '0'), " + "'"
						+ String.valueOf(member.get("U_ID")) + "', sysdate, " + temp + ", " + (-temp) + ")";

//				List<Object> insertParams = new ArrayList<>();

				payDao.getRefund(updateSql);
				payDao.getRefund(insertSql);
				member.put("U_CASH", (tempCash - temp));
				member.put("U_MONDY", (tempMoney + temp));

				System.out.println();
				System.out.printf("\t\t%20s","ȯ���� �Ϸ�Ǿ����ϴ�.");
				System.out.println("");
				// �ڡڡڡ� �����ܾ� �����ֱ� ��������

				System.out.println("\n\t ������������������������[ȯ���� �����ܾ�]����������������������");

				System.out.printf("\t%21s","�� ������ :  " + payDao.getMoneyBalance(member.get("U_MONEY")) + "��");

				System.out.printf("\n\t%18s","�� ĳ�� : " + payDao.getCashBalance(member.get("U_CASH")) + "ĳ��");
				System.out.println("\n\t ������������������������������������������������������������������������");
				System.out.println();
				System.out.println();

			} else {
				System.out.println("\t::: ȯ���� �ݾ��� ����ĳ�ú��� �����ϴ�.");
				System.out.println("\t::: ȯ���� ��ҵǾ����ϴ�.");
			}

		}
		return View.PAY;
	}

	// ȯ���̷� ��ȸ
	public int showCashRefundHistory() {
		Map<String, Object> mem = (Map<String, Object>) Controller.sessionStorage.get("loginInfo");
		String userId = (String) mem.get("U_ID");
		List<Map<String, Object>> list = payDao.getCashRefundHistory(userId);

		if (list.isEmpty()) {
			System.out.println("::: ĳ�� ȯ�� ������ �����ϴ�.");
		} else {
			System.out.println("\n\t========================================");
			System.out.printf("\n\t\t%15s","[ĳ�� ȯ�� ����]");
			System.out.println("\n\t��������������������������������������������������������������������������������");
			System.out.printf("                 %s        \t%s   \t\t%s%n", "ȯ�ҹ�ȣ", "ȯ������", "ȯ�ұݾ�");
			System.out.println("\t��������������������������������������������������������������������������������");
			for (int j = 0; j < list.size(); j++) {
				String payNo = String.valueOf(list.get(j).get("P_RPAYNO"));
				String date = String.valueOf(list.get(j).get("P_RDATE"));
				int money = Integer.parseInt(String.valueOf(list.get(j).get("P_RMONEY")));

				System.out.printf("��������������%-12s %12s %12d%n", payNo, date, money);

			}

			System.out.println("\n\t��������������������������������������������������������������������������������");
			System.out.println();
			System.out.println();
		}
		return View.PAY;

	}
	
}
