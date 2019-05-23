package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Database.Model.UserModel;

/**
 * Created by haoyucn on 5/23/19.
 */

public class userDAO {

    public boolean addUser(String id, String password, String email, int age, String profession) {
        SQLDBConnection connection = new SQLDBConnection();
        Boolean result = null;
        String statement = "INSERT INTO users (username, password, email, age, profession) " +
                "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.getPreparedStatment(statement);

        try {
            ps.setString(1,id);
            ps.setString(2, password);
            ps.setString(3, email);
            ps.setInt(4, age);
            ps.setString(5,profession);
            result = connection.executeUpdateStatement(ps);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        if (result == null) {
            return false;
        }
        return false;
    }

    public UserModel getUserByUserName(String username) {
        ResultSet rs = null;
        String statement = "Select username, password, email, age, profession from user where username = "
    }
}
