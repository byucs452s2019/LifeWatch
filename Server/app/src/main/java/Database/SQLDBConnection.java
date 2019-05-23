package Database;

import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
 import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


//import communication.Result;

public class SQLDBConnection {

    String url;
    static Connection conn;
    static int numberProcess = 0;

    public SQLDBConnection() {
        String fileDirectory = System.getProperty("user.dir") + "/";

        String fileName="server.db";
        this.url = fileDirectory + fileName;
        System.out.println(url);
    }


    public Boolean createDBFile() {
        File file = new File(url);
        try {
            if(!file.exists()) {
                if (file.createNewFile()) {
                    System.out.println("file has been created");
                    createTables();
                    return true;
                } else {
                    System.out.println("failed to create a file");
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private Boolean createTables(){
        String createUserTable = "CREATE TABLE user (" +
                "username varchar(25) PRIMARY KEY, " +
                "password varchar(25)," +
                "email varchar(50)," +
                "age int(2)," +
                "profession varchar(25)" +
                ");";
        String createMotionTable = "CREATE TABLE motion (" +
                "username varchar(25), " +
                "time datetime," +
                "motionBlob BLOB" +
                ");";
        String createActivityTable = "CREATE TABLE ativity (" +
                "username varchar(25), " +
                "startTime datetime, " +
                "endTime datetime," +
                "activityType varchar(25)," +
                "location varchar(25)" +
                ");";
        openConnection();
        PreparedStatement u = getPreparedStatment(createUserTable);
        PreparedStatement m = getPreparedStatment(createMotionTable);
        PreparedStatement a = getPreparedStatment(createActivityTable);
        try{
            u.execute();
            m.execute();
            a.execute();
            conn.commit();
            closeConnection();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            closeConnection();
        }
        return false;
    }

    private Boolean openConnection() {
        try {
            createDBFile();
            String connectionUrl = "jdbc:sqlite:" + url;
            if (conn == null) {
                this.conn = DriverManager.getConnection(connectionUrl);
                conn.setAutoCommit(false);
            }
            else if (conn.isClosed()) {
                this.conn = DriverManager.getConnection(connectionUrl);
                conn.setAutoCommit(false);
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
//     TODO: never used
//     public ResultSet executeCommand (String stmtString) {
//         if (openConnection()) {
//             try {
//                 conn.setAutoCommit(false);
//                 Statement stmt = conn.createStatement();
//                 numberProcess--;
//                 if (stmt.execute(stmtString)) {
//                     conn.commit();
//                     closeConnection();
//                     return stmt.getResultSet();
//                 }
//                 else {
//                     conn.rollback();
//                 }
//             }catch (SQLException e) {
//                 e.printStackTrace();
//             }catch (Exception e) {
//                 e.printStackTrace();
//             }
//             closeConnection();
//         }
//         return null;
//     }

    // return null for false result
    public PreparedStatement getPreparedStatment(String sql){
        PreparedStatement preparedStatement = null;
        if (openConnection()) {
            try {
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setQueryTimeout(1);
                numberProcess++;
            }
            catch(SQLException e) {
                e.printStackTrace();
                closeConnection();
            }
        }
        return preparedStatement;
    }

    public Statement getStatement() {
        if (openConnection()) {
            try {
                numberProcess++;
                return conn.createStatement();
            }
            catch(SQLException e) {
                e.printStackTrace();
                closeConnection();
            }

        }
        return null;
    }

     public ResultSet executeQueryStatement(PreparedStatement p) {
         try{
             numberProcess--;
             ResultSet rs  = p.executeQuery();

             if (rs.getFetchSize() == 0) {
                 conn.rollback();
                 closeConnection();
                 System.out.println("Execute result is false.");
                 System.out.println(p.getWarnings());
                 return null;
             }
             conn.commit();
             closeConnection();
             return rs;

         } catch (SQLException e) {
             e.printStackTrace();
             closeConnection();
             return null;
         }
     }

     public boolean executeUpdateStatement(PreparedStatement p) {
         try{
             numberProcess--;
             int count = p.executeUpdate();
             if (count == 0) {
                 conn.rollback();
                 closeConnection();
                 System.out.println("Execute result is falise.");
                 return false;
             }
             conn.commit();
             closeConnection();
             return true;

         } catch (SQLException e) {
             e.printStackTrace();
             closeConnection();
             return true;
         }
     }

    private boolean closeConnection() {
        if (conn != null) {
            try {
                if (numberProcess == 0) {
                    conn.close();
                    conn = null;
                }
                return true;
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Blob getBlob() {
        openConnection();
        try {
            return conn.createBlob();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}