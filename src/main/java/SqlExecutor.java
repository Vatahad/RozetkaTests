import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class SqlExecutor {
    private static final SqlExecutor INSTANCE = new SqlExecutor();

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/";

    static final String USER = "root";
    static final String PASS = "root";

    private Connection conn = null;
    private Statement stmt = null;

    private SqlExecutor() {
        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Creating database...");
            stmt = conn.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SqlExecutor getInstance() {
        return INSTANCE;
    }

    private boolean createDataBase(String name) {
        String sql = "CREATE DATABASE IF NOT EXISTS " + name.toUpperCase();
        try {
            stmt.executeUpdate(sql);
            System.out.println("Database with name " + name.toUpperCase() + " created successfully...");
            conn = DriverManager.getConnection(DB_URL + name.toUpperCase(), USER, PASS);
            stmt = conn.createStatement();
            return true;
        } catch (SQLException e) {
            System.out.println("Database with name " + name.toUpperCase() + " was not created");
            return false;
        }
    }

    private boolean createTable(String name) {
        String sql = "CREATE TABLE " + name.toUpperCase() + " " +
                "(phone_name VARCHAR(255) not NULL, " +
                " price INTEGER, " +
                " test_date DATETIME not NULL " +
                "DEFAULT '2000-01-01 00:00:01')";

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt.executeUpdate(sql);
            System.out.println("Table with name " + name.toUpperCase() + " was created successfully");
            return true;
        } catch (SQLException e) {
            try {
                sql = "SELECT * FROM " + name.toUpperCase();
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.wasNull()) return false;
                else return true;
            } catch (Exception e1) {
                return false;
            }
        }
    }

    public boolean readyDataBase(String dBName, String tableName) {
        if (createDataBase(dBName)) {
            if (createTable(tableName)) {
                return true;
            } else {
                return false;
            }
        } else return false;
    }

    public boolean insertIntoTable(String tableName, String nameOfTelephone, int price, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "INSERT INTO " + tableName.toUpperCase() + " " +
                "VALUES ('" + nameOfTelephone + "', " + price + ", '" + sdf.format(date) + "')";
        try {
            stmt.executeUpdate(sql);
            System.out.println("Row was added");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public void close() {
        try {
            if (stmt != null)
                conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }

        try {
            if (conn != null)
                conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public Statement getStmt() {
        return stmt;
    }
}