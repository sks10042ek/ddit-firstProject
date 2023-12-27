package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class DateCourceDAO {
    private static DateCourceDAO instance = null;
    private JDBCUtil jdbc = JDBCUtil.getInstance();

    private DateCourceDAO() {}

    public static DateCourceDAO getInstance() {
        if (instance == null)
            instance = new DateCourceDAO();
        return instance;
    }

    public List<Map<String, Object>> select(String rloc, String rcat) {
        String sql = "SELECT RES_NAME, RES_CAT, RES_ADDRESS, RES_GRADE FROM M_RESTAURANT WHERE RES_LOC = ? AND RES_CAT = ? ORDER BY RES_GRADE DESC, RES_NAME";
        List<Object> param = new ArrayList<>();
        param.add(rloc);
        param.add(rcat);

        return jdbc.selectList(sql, param);
    }
    
    public List<Map<String, Object>> selectPlace(String hloc, String hinout) {
        String sql = "SELECT H_LOC, H_NAME FROM H_PLACE WHERE H_LOC = ? AND H_INOUT = ? ORDER BY H_NAME";
        List<Object> param = new ArrayList<>();
        param.add(hloc);
        param.add(hinout);

        return jdbc.selectList(sql, param);
    }
    
    public Map<String, Object> selectMResByCode(String restaurantCode) {
        String sql = "SELECT * FROM M_RESTAURANT WHERE RES_CODE = ?";
        List<Object> param = new ArrayList<>();
        param.add(restaurantCode);

        return jdbc.selectOne(sql, param);
    }

    public List<Map<String, Object>> selectMRes() {
        String sql = "SELECT * FROM M_RESTAURANT ORDER BY RES_CODE";
        List<Object> param = new ArrayList<>();

        return jdbc.selectList(sql, param);
    }
    
    public boolean checkDuplicateRestaurantCode(String restaurantCode) {
        String sql = "SELECT COUNT(*) FROM M_RESTAURANT WHERE RES_CODE = ?";
        List<Object> param = new ArrayList<>();
        param.add(restaurantCode);

        return jdbc.getCount(sql, param) > 0;
    }

    public boolean insertMRes(String restaurantCode, String restaurantLocal, String category, String restaurantName, String restaurantAddress, String restaurantGrade) {
        String sql = "INSERT INTO M_RESTAURANT (RES_CODE, RES_LOC, RES_CAT, RES_NAME, RES_ADDRESS, RES_GRADE)VALUES (?, ?, ?, ?, ?, ?)";
        List<Object> param = new ArrayList<>();
        param.add(restaurantCode);
        param.add(restaurantLocal);
        param.add(category);
        param.add(restaurantName);
        param.add(restaurantAddress);
        param.add(restaurantGrade);

        return jdbc.update(sql, param) > 0;
    }

    public boolean updateMRes(String restaurantCode, String restaurantLocal, String category, String restaurantName, String restaurantAddress, String restaurantGrade) {
        String sql = "UPDATE M_RESTAURANT SET RES_LOC = ?, RES_CAT = ?, RES_NAME = ?, RES_ADDRESS = ?, RES_GRADE = ? WHERE RES_CODE = ?";
        List<Object> param = new ArrayList<>();
        
        param.add(restaurantLocal);
        param.add(category);
        param.add(restaurantName);
        param.add(restaurantAddress);
        param.add(restaurantGrade);
        param.add(restaurantCode);

        return jdbc.update(sql, param) > 0;
    }


    public boolean deleteMRes(String restaurantCode) {
        String sql = "DELETE FROM M_RESTAURANT WHERE RES_CODE = ?";
        List<Object> param = new ArrayList<>();
        param.add(restaurantCode);

        return jdbc.update(sql, param) > 0;
    }
    
    public List<Map<String, Object>> selectMPlace() {
        String sql = "SELECT H_CODE, H_LOC, H_INOUT, H_NAME FROM H_PLACE ORDER BY H_CODE";
        List<Object> param = new ArrayList<>();

        return jdbc.selectList(sql, param);
    }

    public boolean checkDuplicatePlaceCode(String placeCode) {
        String sql = "SELECT COUNT(*) FROM H_PLACE WHERE H_CODE = ?";
        List<Object> param = new ArrayList<>();
        param.add(placeCode);

        return jdbc.getCount(sql, param) > 0;
    }

    public boolean insertHPlace(String placeCode, String placeLocal, String placeInOut, String placeName) {
        String sql = "INSERT INTO H_PLACE VALUES (?, ?, ?, ?)";
        List<Object> param = new ArrayList<>();
        
      
        param.add(placeLocal);
        param.add(placeInOut);
        param.add(placeName);
        param.add(placeCode);

        return jdbc.update(sql, param) > 0;
    }

    public boolean updateHPlace(String placeCode, String placeLocal, String placeInOut, String placeName) {
        String sql = "UPDATE H_PLACE SET H_LOC = ?, H_INOUT = ?, H_NAME = ? WHERE H_CODE = ?";
        List<Object> param = new ArrayList<>();
        param.add(placeLocal);
        param.add(placeInOut);
        param.add(placeName);
        param.add(placeCode);

        return jdbc.update(sql, param) > 0;
    }

    public boolean deleteHPlace(String placeCode) {
        String sql = "DELETE FROM H_PLACE WHERE H_CODE = ?";
        List<Object> param = new ArrayList<>();
        param.add(placeCode);

        return jdbc.update(sql, param) > 0;
    }

}