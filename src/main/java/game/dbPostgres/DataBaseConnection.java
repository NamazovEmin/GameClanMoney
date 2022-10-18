package game.dbPostgres;

import java.sql.*;

public class DataBaseConnection {

    private static final String CON_STR = "jdbc:postgresql://localhost:5432/GameGold";
    private static DataBaseConnection instance;
    private  String dbUser = "postgres";
    private  String dbPassword = "Diabolick369";
    public   Connection conn;
    public  Statement stmt;
    public  PreparedStatement pstmt;

    public static synchronized DataBaseConnection getInstance(){
        if (instance == null) {
            instance = new DataBaseConnection();
        }
        return instance;
    }

    public DataBaseConnection() {
        try {
            conn = DriverManager.getConnection(CON_STR, dbUser, dbPassword);
            System.out.println("Connected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


