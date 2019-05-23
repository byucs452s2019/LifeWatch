package Server;
import java.util.Calendar;

import Database.*;
import Database.Model.MotionModel;
import Database.Model.UserModel;

/**
 * Created by haoyucn on 5/23/19.
 */

public class Server {

    public static void main(String[] argvs) {
        SQLDBConnection conn =  new SQLDBConnection();
        conn.createDBFile();
        UserModel user = new UserModel("hao", "qqqqqq", "@@@", 26, "weeeb");
        UserDAO userDAO = new UserDAO();
        MotionDAO motionDAO = new MotionDAO();

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

        UserModel cuser = userDAO.getUserByUserName("hao");
        if (cuser == null) {
            System.out.println("failed to get user");
        }
        else {
            System.out.println(cuser.toString());
        }

        MotionModel dummy = new MotionModel();
        dummy.setStartTime(101010);
        dummy.setAvgHumdity(50);

        motionDAO.addMotionEvent("jason", dummy);
        MotionModel mm = motionDAO.getMotionEvent("jason", "101010");
        if (mm == null) {
            System.out.println("failed to get motion");
        }
        else {
            System.out.println(mm.getAvgHumidity());
        }
        motionDAO.deleteMotionEvent("jason", "101010");
        mm = motionDAO.getMotionEvent("jason", "101010");
        if (mm == null) {
            System.out.println("no motion found after deleting");
        }
        else {
            System.out.println(mm.getAvgHumidity());
        }

    }
}
