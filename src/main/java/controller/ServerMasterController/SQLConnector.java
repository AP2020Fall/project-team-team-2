package controller.ServerMasterController;

import model.GameLogStates;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLConnector {
    public static void main(String[] args) throws SQLException {
        /*
        Get Admin Messages
            Map<String, Object> newMap = new HashMap<>();
            newMap.put("is_admin", "1");
            String admin_id =(String) (selectFromDatabase(newMap, "players").get("player_id").get(0));
            newMap.clear();
            newMap.put("account_id" , admin_id);
            System.out.println(selectFromDatabase(newMap,"messages"));
        */
    }
    /* Database Methods */
    /* Insert */
    /* Example
        Map<String, Object> newMap = new HashMap<>();
        newMap.put("first_name", "Javad");
        System.out.println(insertInDatabase(newMap, "players"));
    */
    public static boolean insertInDatabase(Map<String, Object> inputData, String tableName) {
        String query = "";
        query += "INSERT INTO " + tableName + " (";
        int i = 0;
        String columns = "";
        String values = "";

        for (Map.Entry<String, Object> entry : inputData.entrySet()) {
            i++;
            if (i != inputData.size()) {
                columns += entry.getKey() + ",";
                values += "'" + entry.getValue() + "',";
            } else {
                columns += entry.getKey();
                values += "'" + entry.getValue() + "'";
            }
        }
        Statement stmt = null;
        query += columns + ")" + " VALUES (" + values + ")";
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/riskgamedatabase",
                    "root", "rootpass1");   // For MySQL only
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
            conn.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    /* Select */
    /* Example
        Map<String, Object> newMap = new HashMap<>();
        newMap.put("first_name", "Javad");
        System.out.println(selectFromDatabase(newMap, "players"));
    */
    public static Map<String, List<Object>> selectFromDatabase(Map<String, Object> inputData, String tableName) {
        String query = "";
        query += "SELECT * FROM " + tableName;
        int i = 0;
        query += " WHERE ";

        for (Map.Entry<String, Object> entry : inputData.entrySet()) {
            i++;
            if (i != inputData.size()) {
                query += entry.getKey() + "=" + "'" + entry.getValue() + "',";
            } else {
                query += entry.getKey() + "=" + "'" + entry.getValue() + "'";
            }
        }

        Map<String, List<Object>> returnSelectedData = new HashMap<>();
        Connection conn = null;   // For MySQL only
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/riskgamedatabase",
                    "root", "rootpass1");
            Statement stmt = null;
            stmt =conn.createStatement() ;
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData metadata = rs.getMetaData();
            int columnCount = metadata.getColumnCount();

            ArrayList<String> columns = new ArrayList<>();
            for (int j = 1; j <= columnCount; j++) {
                String columnName = metadata.getColumnName(j);
                columns.add(columnName);
            }

            while(rs.next()){
                for(String columnName : columns){
                    if(returnSelectedData.containsKey(columnName)){
                        returnSelectedData.get(columnName).add(rs.getString(columnName));
                    }else{
                        List<Object> tempList = new ArrayList<>();
                        tempList.add(rs.getString(columnName));
                        returnSelectedData.put(columnName,tempList);
                    }
                }
            }
            return returnSelectedData;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /* Delete */

    /* Example
        Map<String, Object> newMap = new HashMap<>();
        newMap.put("first_name", "Javad");
        System.out.println(deleteFromTable(newMap, "players"));
    * */
    public static boolean deleteFromTable(Map<String,Object> inputData , String tableName){
        String query = "";
        query += "DELETE FROM " + tableName;
        int i = 0;
        query += " WHERE ";

        for (Map.Entry<String, Object> entry : inputData.entrySet()) {
            i++;
            if (i != inputData.size()) {
                query += entry.getKey() + "=" + "'" + entry.getValue() + "' AND ";
            } else {
                query += entry.getKey() + "=" + "'" + entry.getValue() + "'";
            }
        }

        Map<String, List<Object>> returnSelectedData = new HashMap<>();
        Connection conn = null;   // For MySQL only
        System.out.println(query);
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/riskgamedatabase",
                    "root", "rootpass1");
            Statement stmt = null;
            stmt =conn.createStatement() ;
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    /* Select */
    /* Example
        System.out.println(whereLike("first_name", "Al", "players"));
    */
    public static Map<String, List<Object>> whereLike(String inputColumnName,String like, String tableName) {
        String query = "";
        query += "SELECT * FROM " + tableName;
        int i = 0;
        query += " WHERE " + inputColumnName + " LIKE '%" +like + "%'";



        Map<String, List<Object>> returnSelectedData = new HashMap<>();
        Connection conn = null;   // For MySQL only
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/riskgamedatabase",
                    "root", "rootpass1");
            Statement stmt = null;
            stmt =conn.createStatement() ;
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData metadata = rs.getMetaData();
            int columnCount = metadata.getColumnCount();
            ArrayList<String> columns = new ArrayList<>();
            for (int j = 1; j <= columnCount; j++) {
                String columnName = metadata.getColumnName(j);
                columns.add(columnName);
            }


            while(rs.next()){
                for(String columnName : columns){
                    if(returnSelectedData.containsKey(columnName)){
                        returnSelectedData.get(columnName).add(rs.getString(columnName));
                    }else{
                        List<Object> tempList = new ArrayList<>();
                        tempList.add(rs.getString(columnName));
                        returnSelectedData.put(columnName,tempList);
                    }
                }
            }
            return returnSelectedData;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
