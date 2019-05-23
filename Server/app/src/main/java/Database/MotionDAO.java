package Database;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Database.Model.MotionModel;

/**
 * Created by jasontd on 5/23/19.
 */

public class MotionDAO{
    SQLDBConnection connection;
    public boolean addMotionEvent(String username, MotionModel motion){
        connection = new SQLDBConnection();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Boolean result = null;
        String statement = "INSERT INTO motion (username, time, motionBlob) " +
                "VALUES (?, ?, ?)";
        PreparedStatement ps = connection.getPreparedStatment(statement);
        try{
            ObjectOutput out = new ObjectOutputStream(bos);
            out.writeObject(motion);
            out.flush();
            byte[] bytes = bos.toByteArray();
            ps.setString(1,username);
            ps.setString(2, Float.toString(motion.getStartTime()));
            ps.setBytes(3, bytes);
            result = connection.executeUpdateStatement(ps);
        } catch (SQLException e) {
            e.printStackTrace();
            connection.closeConnection();
            return false;
        } catch (IOException ie){
            ie.printStackTrace();
            connection.closeConnection();
            return false;
        }
        if (result == null) {
            connection.closeConnection();
            return false;
        }
        connection.closeConnection();
        return true;
    }

    public boolean deleteMotionEvent(String username, String startTime) {
        connection = new SQLDBConnection();
        Boolean result = null;
        String statement = "DELETE FROM motion WHERE username='" +
                username + "' and time='" + startTime + "';";
        PreparedStatement ps = connection.getPreparedStatment(statement);
        result = connection.executeUpdateStatement(ps);
        if (result == null) {
            connection.closeConnection();
            return false;
        }
        connection.closeConnection();
        return true;
    }


    public MotionModel getMotionEvent(String username, String startTime){
        connection = new SQLDBConnection();
        ByteArrayInputStream bis;
        ObjectInput in = null;
        MotionModel m = null;
        try{
            String sql = "SELECT * FROM motion WHERE username='" +
                    username + "' and time='" + startTime + "';";
            PreparedStatement stmt = connection.getPreparedStatment(sql);
            ResultSet result = connection.executeQueryStatement(stmt);
            try{
                while(result.next()){
                    bis = new ByteArrayInputStream(result.getBytes("motionBLOB"));
                    in = new ObjectInputStream(bis);
                    m = (MotionModel) in.readObject();
                }
            }
            finally {
                try{
                    if(in != null){
                        in.close();
                    }
                }
                catch (Exception e){
                    connection.closeConnection();
                }
            }
        }
        catch(Exception e){
            connection.closeConnection();
            e.printStackTrace();
        }
        connection.closeConnection();
        return m;
    }

    public void clear() {
        connection = new SQLDBConnection();
        String sql = "DELETE FROM motion where 1=1;";
        PreparedStatement stmt = connection.getPreparedStatment(sql);
        connection.executeUpdateStatement(stmt);
        connection.closeConnection();
    }

}
