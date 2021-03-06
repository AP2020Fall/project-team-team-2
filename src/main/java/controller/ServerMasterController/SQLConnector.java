package controller.ServerMasterController;

import model.GameLogStates;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLConnector {
    /*public static void main(String[] args) throws SQLException {
        Map<String, Object> newMap = new HashMap<>();
        Map<String, Object> newMap2 = new HashMap<>();
        newMap.put("first_name", "Ali");
        newMap2.put("hash_password","new_Password");
        System.out.println(getWholeTable("players"));
    }*/
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
    public static List<Map<String, Object>> selectFromDatabase(Map<String, Object> inputData, String tableName) {
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

        List<Map<String, Object>> returnSelectedData = new ArrayList<>();
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
                Map<String , Object> tempMap = new HashMap<>();
                for(String columnName : columns){
                    tempMap.put(columnName,rs.getString(columnName));
                }
                returnSelectedData.add(tempMap);
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
    public static List<Map<String, Object>> whereLike(String inputColumnName,String like, String tableName) {
        String query = "";
        query += "SELECT * FROM " + tableName;
        int i = 0;
        query += " WHERE " + inputColumnName + " LIKE '%" +like + "%'";



        List<Map<String, Object>> returnSelectedData = new ArrayList<>();
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
                Map<String,Object> tempMap = new HashMap<>();
                for(String columnName : columns){
                    tempMap.put(columnName , rs.getString(columnName));
                }
                returnSelectedData.add(tempMap);
            }
            return returnSelectedData;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    /* Edit (Update) */
    public static boolean updateTable(Map<String,Object> conditions,Map<String,Object> newValues , String tableName){
        String query = "UPDATE " + tableName + " SET ";
        int i = 0;
        for(Map.Entry<String, Object> entry : newValues.entrySet()){
            i++;
            if(i == newValues.size()){
                query += entry.getKey() + "='" + entry.getValue()+"'";
            }else{
                query += entry.getKey() + "='" + entry.getValue() +"',";
            }
        }
        query += " WHERE ";
        i = 0;
        for(Map.Entry<String, Object> entry : conditions.entrySet()){
            i++;
            if(i == conditions.size()){
                query += entry.getKey() + "='" + entry.getValue()+"'";
            }else{
                query += entry.getKey() + "='" + entry.getValue() +"' AND ";
            }
        }
        Statement stmt = null;
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
    /* Get Whole Table */
    public static List<Map<String,Object>> getWholeTable(String tableName){
        String query = "";
        query += "SELECT * FROM " + tableName;
        List<Map<String, Object>> returnSelectedData = new ArrayList<>();
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
                Map<String , Object> tempMap = new HashMap<>();
                for(String columnName : columns){
                    tempMap.put(columnName,rs.getString(columnName));
                }
                returnSelectedData.add(tempMap);
            }
            return returnSelectedData;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public static Map<String,Object> toMap(String... inputs){
        Map<String, Object> returnMap = new HashMap<>();
        int i = 0 ;
        String columnName = "";
        String value = "";
        for(String input :inputs){
            i++;
            if(i%2 ==1){
                /*Column Name*/
                columnName = input;
            }else{
                value = input;
                returnMap.put(columnName,value);
            }
        }
        return returnMap;
    }
}
