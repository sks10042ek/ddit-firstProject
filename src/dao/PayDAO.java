package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.JDBCUtil;

public class PayDAO {
    private static PayDAO instance = null;

    private PayDAO() {}

    public static PayDAO getInstance() {
        if (instance == null)
            instance = new PayDAO();
        return instance;
    }

    JDBCUtil jdbc = JDBCUtil.getInstance();

 // 캐시 잔액조회
    public int getCashBalance(Object userId) {

        String sql = "SELECT U_CASH FROM U_MEMBER WHERE U_ID = ?";
        List<Object> param = new ArrayList<>();
        param.add(userId);

        Map<String, Object> result = jdbc.Balance(sql, param);

        if (result != null) {
            int cash = Integer.parseInt(String.valueOf(result.get("U_CASH")));
            return cash;
        } else {
            return 0;
        }
    }

    // 소지금 잔액조회
    public int getMoneyBalance(Object userId) {

        String sql = "SELECT U_MONEY FROM U_MEMBER WHERE U_ID = ?";
        List<Object> param = new ArrayList<>();
        param.add(userId);

        Map<String, Object> result = jdbc.Balance(sql, param);

        if (result != null) {
            int money = Integer.parseInt(String.valueOf(result.get("U_MONEY")));
            return money;
        } else {
            return 0;
        }
    }

    // 캐시충전
//    public int getCarge(String sql, List<Object> params) {
//        return jdbc.update(sql, params);
//    }
    public int getCarge(String sql) {
       return jdbc.update(sql);
    }
    
    
    // 캐시환불
//    public int getRefund(String sql, List<Object> params) {
//        return jdbc.update(sql, params);
//    }
    public int getRefund(String sql) {
       return jdbc.update(sql);
    }
    
    
    // 캐시 충전 내역
    public List<Map<String, Object>> getCashChargeHistory(String userId) {
        String sql = "SELECT P_PAYNO, TO_CHAR(P_DATE, 'YYYY-MM-DD') AS P_DATE, P_MONEY " +
                     "FROM PAY " +
                     "WHERE U_ID = ? " +
                     "ORDER BY P_PAYNO DESC";

        List<Object> param = new ArrayList<>();
        param.add(userId);

        return jdbc.selectList(sql, param);
    }
    
    // 캐시 환불 내역
    public List<Map<String, Object>> getCashRefundHistory(String userId) {
        String sql = "SELECT P_RPAYNO, TO_CHAR(P_RDATE, 'YYYY-MM-DD') AS P_RDATE, P_RMONEY " +
                     "FROM REFUND " +
                     "WHERE U_ID = ? " +
                     "ORDER BY P_RPAYNO DESC";

        List<Object> param = new ArrayList<>();
        param.add(userId);

        return jdbc.selectList(sql, param);
    }
    
    public int payMent(String uid, int cash) {

		String sql = "UPDATE U_MEMBER SET U_CASH = "+cash+" WHERE U_ID = '" + uid+"' ";
		return jdbc.update(sql);
	}
    
    public int selectAllSales() {
        String sql = "SELECT SUM(M_SALE) A FROM PAY";

        return Integer.parseInt(String.valueOf(jdbc.selectOne(sql).get("A")));
    }

    public int selectAllExpenses() {
        String sql = "SELECT SUM(M_SALE) A FROM REFUND";

        return Integer.parseInt(String.valueOf(jdbc.selectOne(sql).get("A")));
    }
    
}