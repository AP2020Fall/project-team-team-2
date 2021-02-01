package controller.ServerMasterController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectToSQL {
    public static Statement connection() {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/riskgamedatabase",
                        "root", "rootpass1");   // For MySQL only
                Statement stmt = conn.createStatement();
        ) {
            return stmt;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
