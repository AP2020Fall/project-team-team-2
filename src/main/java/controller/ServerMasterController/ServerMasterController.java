package controller.ServerMasterController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.risk.MatchCardController;
import controller.risk.RiskGameController;
import controller.risk.StartGameController;
import javafx.scene.control.ProgressBar;
import main.Client;
import main.Command;
import model.*;
import org.javatuples.Pair;
import view.risk.RiskGameView;

import java.sql.*;
import java.util.*;


import main.Token;
import model.Account;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.nio.channels.Pipe;
import java.util.Map;

public class ServerMasterController {
    private static Token token;

    public static Token getCurrentToken() {
        return token;
    }

    public static void main(String[] args) throws SQLException {
        System.out.println(whereLike("first_name", "Al", "players"));
    }

    public Triplet<String, String, String> takeAction(String input) {
        Command command = Command.fromJson(input);
        token = Token.decrypt(command.getAuthToken());
        if (!token.validate(command.getClientInfo())) {
            return new Triplet<>("Token is invalid", new Gson().toJson(command.getClientInfo()),
                    token.encrypt());
        }
        if (command.getCommand().equals("endConnection")) {
            return new Triplet<>("Connection is terminated.", new Gson().toJson(command.getClientInfo()),
                    token.encrypt());
        }
        if (command.getDeclaringClass() == null)
            return new Triplet<>("", new Gson().toJson(command.getClientInfo()),
                    token.encrypt());
        if (command.getMethod() == null)
            return new Triplet<>("", new Gson().toJson(command.getClientInfo()),
                    token.encrypt());
        return new Triplet<>(new Gson().toJson(command.invokeMethod()), new Gson().toJson(command.getClientInfo()),
                token.encrypt());
    }

    public String test(String input) {
        if (input.equalsIgnoreCase("test")) {
            return "Test successfully completed.";
        } else
            return "Test was unsuccessful.";
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
            for (int j = 1; j < columnCount; j++) {
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
            for (int j = 1; j < columnCount; j++) {
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
