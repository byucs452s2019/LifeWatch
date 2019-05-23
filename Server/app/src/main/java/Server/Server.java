package Server;
import java.util.Calendar;

import Database.*;
import Database.Model.UserModel;

/**
 * Created by haoyucn on 5/23/19.
 */

public class Server {

    public static void main(String[] argvs) {
        SQLDBConnection conn =  new SQLDBConnection();
        conn.createDBFile();

        // populatiing the DAO
        UserDAO userDAO = new UserDAO();
        UserModel user = new UserModel("J", "password", "@@@", 16, "weeb");
        userDAO.insert(user);
        user = new UserModel("T", "password", "@@@", 18, "weeb");
        userDAO.insert(user);
        user = new UserModel("M", "password", "@@@", 20, "weeb");
        userDAO.insert(user);
        user = new UserModel("R", "password", "@@@", 22, "weeb");
        userDAO.insert(user);

        MotionDAO motionDAO = new MotionDAO();
    }
}
