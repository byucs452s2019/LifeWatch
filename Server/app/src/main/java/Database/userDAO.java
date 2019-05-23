package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by haoyucn on 5/23/19.
 */

public class userDAO {
    SQLDBConnection connection = new SQLDBConnection();
    public boolean addUser(String id, String password, String email, int age, String profession) {
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

    public
}
