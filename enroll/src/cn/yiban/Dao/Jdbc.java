package cn.yiban.Dao;

import java.sql.*;

/**
 *
 * Created by Éµ±Æ on 2018/1/25.
 */
public class Jdbc {
    public Connection conn = null;
    public ResultSet rs = null;
    private String DatabaseDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private String DatabaseConnStr = "jdbc:sqlserver://localhost:1433;DatabaseName=Enroll";
    private String User = "zheng";
    private String Pass = "zws19970423";
    public boolean isConn()
    {
        if(conn != null)
        {
            return true;
        }
        return false;
    }
    public boolean isResultSet()
    {
        if(rs != null)
        {
            return true;
        }
        return false;
    }
    public String getDatabaseConnStr() {
        return DatabaseConnStr;
    }

    public void setDatabaseConnStr(String databaseConnStr) {
        DatabaseConnStr = databaseConnStr;
    }

    public String getDatabaseDriver() {
        return DatabaseDriver;
    }

    public void setDatabaseDriver(String databaseDriver) {
        DatabaseDriver = databaseDriver;
    }

    public Jdbc()
    {
        try {
            Class.forName(DatabaseDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getString(String name) throws SQLException {
        if(rs == null)
        {
            return ("ResuleSet is null");
        }
        return String.valueOf(rs.getString(name));
    }

    private void execute(String sql)
    {
        try {
            conn = DriverManager.getConnection(DatabaseConnStr,User,Pass);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void executeInsert(String sql)
    {
        execute(sql);
    }
    public ResultSet executeQuery(String sql)
    {
        rs = null;
        try {
            conn = DriverManager.getConnection(DatabaseConnStr,User,Pass);
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    public void executeDelete(String sql)
    {
        execute(sql);
    }
    public void CloseDatabase()
    {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
