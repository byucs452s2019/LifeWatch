package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Database.Model.UserModel;

/**
 * Created by haoyucn on 5/23/19.
 */

public class UserDAO {

    public boolean insert (UserModel user) {
        SQLDBConnection connection = new SQLDBConnection();
        Boolean result = false;
        String statement = "INSERT INTO user (username, password, email, age, profession) " +
                "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.getPreparedStatment(statement);

        try {
            ps.setString(1,user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setInt(4, user.getAge());
            ps.setString(5,user.getProfession());
            result = connection.executeUpdateStatement(ps);
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }
        connection.closeConnection();
        return result;
    }

    public UserModel getUserByUserName(String username) {
        SQLDBConnection connection = new SQLDBConnection();
        UserModel userModel = null;
        String statement = "Select username, password, email, age, profession from user where username = ?;";
        PreparedStatement ps = connection.getPreparedStatment(statement);
        try {
            ps.setString(1,username);
            ResultSet resultSet = connection.executeQueryStatement(ps);
            if (resultSet == null) {
                System.out.println("reached a null statement");
            }
            if (resultSet.next()) {
                userModel = new UserModel(resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getInt("age"),
                        resultSet.getString("profession"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            userModel = null;
        }
        connection.closeConnection();
        return userModel;
    }

    public boolean update(){
        SQLDBConnection connection = new SQLDBConnection();
        Boolean result = false;
        String statement = "Update user Set email = 'yeah' where username = 'hao';";
        PreparedStatement ps = connection.getPreparedStatment(statement);
        try {
            result = connection.executeUpdateStatement(ps);
            if (result) {
                System.out.println("success");
            }
            else {
                System.out.println("failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        connection.closeConnection();
        return result;
    }
}
