package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controller.Controller;
import controller.View;
import dao.PayDAO;

//★
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

	PayDAO payDao = PayDAO.getInstance(); // Dao는 쿼리를 실행해서 결과를 반환해주는 클래스

	// 캐시잔액확인
	public int balance() {
		Map<String, Object> member = (Map<String, Object>) (Controller.sessionStorage.get("loginInfo"));

		int cashBalance = payDao.getCashBalance(member.get("U_ID"));
		System.out.println("\n\t\t 캐시 잔액 : " + cashBalance);

		return View.PAY;
	}

	// 캐시충전
	public int carge() {

		Map<String, Object> member = (Map<String, Object>) (Controller.sessionStorage.get("loginInfo"));
		int moneyBalance = payDao.getMoneyBalance(member.get("U_ID"));
		int cashBalance = payDao.getCashBalance(member.get("U_ID"));
		int tempMoney = payDao.getMoneyBalance(member.get("U_MONEY"));
		int tempCash = payDao.getCashBalance(member.get("U_CASH"));

		System.out.printf("\n\t%37s","▨ 소유자산은" + moneyBalance + "원입니다.\n");
		System.out.printf("\t%34s","▨ 충전할 금액을 입력하세요 : ");
		int temp = sc.nextInt();
		System.out.println();

		System.out.printf("\t%30s","::: 정말로 충전하겠습니까?(Y/N) : ");
		String flag = sc.next();
		if (flag.equalsIgnoreCase("y")) {
			System.out.println("\t\t::: 충전을 진행합니다...");
			for (int j = 0; j < 10; j++) {
                System.out.print("　");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
			for (int i = 0; i < 10; i++) {
				System.out.print("■");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(" 100%");
			System.out.println();

			if (moneyBalance >= temp) {

				// MEMBER 테이블 소지금, 소지캐시 변경
				String updateSql = "UPDATE U_MEMBER SET U_MONEY = (U_MONEY - " + temp + "), U_CASH = (U_CASH + " + temp
						+ ") WHERE U_ID = '" + String.valueOf((member.get("U_ID"))) + "'";

				// PAY테이블 충전내역 추가
				String insertSql = "INSERT INTO PAY (P_PAYNO, U_ID, P_DATE, P_MONEY, M_SALE) "
						+ "VALUES ('P' || TO_CHAR(SYSDATE, 'YYMMDD') || LPAD((SEQ_PAYNO.NEXTVAL), 4, '0'), " + "'"
						+ String.valueOf(member.get("U_ID")) + "', sysdate, " + temp + ", " + temp + ")";

				payDao.getCarge(updateSql);
				payDao.getCarge(insertSql);
				member.put("U_MONEY", (tempMoney - temp));
				member.put("U_CASH", (tempCash + temp));

				System.out.println();
				System.out.printf("\t\t%20s","충전이 완료되었습니다!");
				System.out.println("");

				// ★★★★ 소지잔액 보여주기 넣을꺼임

				System.out.println("\n\t ─────────────[충전후 소지잔액]───────────");

				System.out.printf("\t%21s","▨ 소지금 :  " + payDao.getMoneyBalance(member.get("U_MONEY")) + "원");

				System.out.printf("\n\t%18s","▨ 캐시 : " + payDao.getCashBalance(member.get("U_CASH")) + "캐시");
				System.out.println("\n\t ────────────────────────────────────");
				System.out.println();

			} else {
				System.out.printf("\t%29s","::: 충전할 금액이 소지금보다 많습니다.");
				System.out.printf("\n\t%22s","::: 충전이 취소되었습니다.\n");
			}
		}
		return View.PAY;
	}

	// 충전이력조회
	public int showCashChargeHistory() {
		Map<String, Object> mem = (Map<String, Object>) Controller.sessionStorage.get("loginInfo");
		String userId = (String) mem.get("U_ID");
		List<Map<String, Object>> list = payDao.getCashChargeHistory(userId);

		if (list.isEmpty()) {
			System.out.println("\t\t캐시 충전 내역이 없습니다.");
		} else {
			System.out.println("\n\t========================================");
			System.out.printf("\n\t\t%15s","[캐시 충전 내역]");
			System.out.println("\n\t────────────────────────────────────────");
			System.out.printf("\t   %s        \t%s   \t\t%s%n", "충전번호", "충전일자", "충전금액");
			System.out.println("\t────────────────────────────────────────");
			for (int j = 0; j < list.size(); j++) {
				String payNo = String.valueOf(list.get(j).get("P_PAYNO"));
				String date = String.valueOf(list.get(j).get("P_DATE"));
				int money = Integer.parseInt(String.valueOf(list.get(j).get("P_MONEY")));

				System.out.printf("%-12s %12s %12d%n", payNo, date, money);

			}
			System.out.println("\n\t────────────────────────────────────────");
			System.out.println();
			System.out.println();
		}
		return View.PAY;
	}

	// 캐시환불
	public int refund() {
		Map<String, Object> member = (Map<String, Object>) (Controller.sessionStorage.get("loginInfo"));
		int moneyBalance = payDao.getMoneyBalance(member.get("U_ID"));
		int cashBalance = payDao.getCashBalance(member.get("U_ID"));
		int tempCash = payDao.getCashBalance(member.get("U_CASH"));
		int tempMoney = payDao.getMoneyBalance(member.get("U_MONEY"));

		System.out.println("\n\t\t▨ 소유캐시 : " + cashBalance);
		System.out.print("\t\t▨ 환불할 금액을 입력하세요 : ");
		int temp = sc.nextInt();
		System.out.println();

		System.out.printf("\t%30s","::: 정말로 환불하겠습니까?(Y/N) : ");
		String flag = sc.next();
		if (flag.equalsIgnoreCase("y")) {
			System.out.println("\t\t::: 환불을 진행합니다...");
			for (int j = 0; j < 10; j++) {
                System.out.print("　");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
			for (int i = 0; i < 10; i++) {
				System.out.print("■");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(" 100%");
			System.out.println();

			if (cashBalance >= temp) {
				// MEMBER 테이블 소지금, 소지캐시 변경

				String updateSql = "UPDATE U_MEMBER SET U_MONEY = (U_MONEY + " + temp + "), U_CASH = (U_CASH - " + temp
						+ ") WHERE U_ID = '" + String.valueOf((member.get("U_ID"))) + "'";

				// PAY테이블 충전내역 추가
				String insertSql = "INSERT INTO REFUND (P_RPAYNO, U_ID, P_RDATE, P_RMONEY, M_SALE) "
						+ "VALUES ('RP' || TO_CHAR(SYSDATE, 'YYMMDD') || LPAD((SEQ_RPAYNO.NEXTVAL), 4, '0'), " + "'"
						+ String.valueOf(member.get("U_ID")) + "', sysdate, " + temp + ", " + (-temp) + ")";

//				List<Object> insertParams = new ArrayList<>();

				payDao.getRefund(updateSql);
				payDao.getRefund(insertSql);
				member.put("U_CASH", (tempCash - temp));
				member.put("U_MONDY", (tempMoney + temp));

				System.out.println();
				System.out.printf("\t\t%20s","환불이 완료되었습니다.");
				System.out.println("");
				// ★★★★ 소지잔액 보여주기 넣을꺼임

				System.out.println("\n\t ────────────[환불후 소지잔액]───────────");

				System.out.printf("\t%21s","▨ 소지금 :  " + payDao.getMoneyBalance(member.get("U_MONEY")) + "원");

				System.out.printf("\n\t%18s","▨ 캐시 : " + payDao.getCashBalance(member.get("U_CASH")) + "캐시");
				System.out.println("\n\t ────────────────────────────────────");
				System.out.println();
				System.out.println();

			} else {
				System.out.println("\t::: 환불할 금액이 소유캐시보다 많습니다.");
				System.out.println("\t::: 환불이 취소되었습니다.");
			}

		}
		return View.PAY;
	}

	// 환불이력 조회
	public int showCashRefundHistory() {
		Map<String, Object> mem = (Map<String, Object>) Controller.sessionStorage.get("loginInfo");
		String userId = (String) mem.get("U_ID");
		List<Map<String, Object>> list = payDao.getCashRefundHistory(userId);

		if (list.isEmpty()) {
			System.out.println("::: 캐시 환불 내역이 없습니다.");
		} else {
			System.out.println("\n\t========================================");
			System.out.printf("\n\t\t%15s","[캐시 환불 내역]");
			System.out.println("\n\t────────────────────────────────────────");
			System.out.printf("                 %s        \t%s   \t\t%s%n", "환불번호", "환불일자", "환불금액");
			System.out.println("\t────────────────────────────────────────");
			for (int j = 0; j < list.size(); j++) {
				String payNo = String.valueOf(list.get(j).get("P_RPAYNO"));
				String date = String.valueOf(list.get(j).get("P_RDATE"));
				int money = Integer.parseInt(String.valueOf(list.get(j).get("P_RMONEY")));

				System.out.printf("　　　　　　　%-12s %12s %12d%n", payNo, date, money);

			}

			System.out.println("\n\t────────────────────────────────────────");
			System.out.println();
			System.out.println();
		}
		return View.PAY;

	}
	
}
