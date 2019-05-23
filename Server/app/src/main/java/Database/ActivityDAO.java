package Database;

import android.app.Activity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Database.Model.ActivityModel;
import Database.Model.MotionModel;

/**
 * Created by jasontd on 5/23/19.
 */

public class ActivityDAO {
    SQLDBConnection connection;
    public boolean addActivity(String username, ActivityModel activity){
        connection = new SQLDBConnection();
        Boolean result = null;
        String statement = "INSERT INTO activity (username, startTime, endTime, activityType, location) " +
                "VALUES (?, ?, ?)";
        PreparedStatement ps = connection.getPreparedStatment(statement);
        try{
            ps.setString(1,username);
            ps.setString(2, activity.getStartTime());
            ps.setString(3, activity.getEndTime());
            ps.setString(4, activity.getActivityType());
            ps.setString(5, activity.getLocation());
            result = connection.executeUpdateStatement(ps);
        } catch (SQLException e) {
            e.printStackTrace();
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

    public boolean deleteActivity(String username, String startTime) {
        connection = new SQLDBConnection();
        Boolean result = null;
        String statement = "DELETE FROM motion WHERE username='" +
                username + "' and startTime='" + startTime + "';";
        PreparedStatement ps = connection.getPreparedStatment(statement);
        result = connection.executeUpdateStatement(ps);
        if (result == null) {
            connection.closeConnection();
            return false;
        }
        connection.closeConnection();
        return true;
    }


    public ActivityModel getActivity(String username, String startTime){
        connection = new SQLDBConnection();
        ActivityModel m = new ActivityModel();
        try{
            String sql = "SELECT * FROM activity WHERE username='" +
                    username + "' and startTime='" + startTime + "';";
            PreparedStatement stmt = connection.getPreparedStatment(sql);
            ResultSet result = connection.executeQueryStatement(stmt);
            try{
                while(result.next()){
                    m.setUsername(result.getString("username"));
                    m.setStartTime(result.getString("startTime"));
                    m.setEndTime(result.getString("endTime"));
                    m.setActivityType(result.getString("activityType"));
                    m.setLocation(result.getString("location"));
                }
            }
            finally {
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
        String sql = "DELETE FROM activity where 1=1;";
        PreparedStatement stmt = connection.getPreparedStatment(sql);
        connection.executeUpdateStatement(stmt);
        connection.closeConnection();
    }
}
