package Server;

import Database.*;
import Database.Model.ActivityModel;
import Database.Model.MotionModel;
import Database.Model.UserModel;

/**
 * Created by haoyucn on 5/23/19.
 */

public class Server {

    public static void main(String[] argvs) {
        SQLDBConnection conn =  new SQLDBConnection();
        conn.createDBFile();
        UserDAO userDAO = new UserDAO();
        MotionDAO motionDAO = new MotionDAO();
        ActivityDAO activityDAO = new ActivityDAO();

        userDAO.clear();
        motionDAO.clear();
        activityDAO.clear();

        // populating the DAO
        UserModel user = new UserModel("J", "password", "@@@", 16, "weeb");
        userDAO.insert(user);
        user = new UserModel("T", "password", "@@@", 18, "weeb");
        userDAO.insert(user);
        user = new UserModel("M", "password", "@@@", 20, "weeb");
        userDAO.insert(user);

        // testing update user table
        user = new UserModel("R", "password", "@@@", 22, "weeb");
        int updateStat = userDAO.update(user);
        System.out.println(user.toString() + " update return stat: " + String.valueOf(updateStat));
        userDAO.insert(user);
        user.setAge(1);
        updateStat = userDAO.update(user);
        System.out.println(user.toString() + " update return stat: " + String.valueOf(updateStat));

        // testing motion table
        MotionModel motion = new MotionModel();
        motion.setStartTime(101010);
        motion.setAvgHumdity(50);
        motionDAO.insert("J", motion);
        MotionModel mm = motionDAO.getMotion("J", 101010);
        if (mm == null) {
            System.out.println("failed to get motion");
        }
        else {
            System.out.println(mm.getAvgHumidity());
        }

        motion.setStartTime(2020);
        updateStat = motionDAO.update("J", 101010, motion);
        System.out.println("motion update stat: " +String.valueOf(updateStat));
        motionDAO.deleteMotion("J", 101010);
        mm = motionDAO.getMotion("J",101010);
        if (mm == null) {
            System.out.println("no motion found after deleting");
        }
        else {
            System.out.println(mm.getAvgHumidity());
        }



        System.out.println("\n\n\ntesting activity");
        ActivityModel activity = new ActivityModel("J", 101010, 101010, "Born", "world");
        activityDAO.insert(activity);
        activity = activityDAO.getActivity("J", 101010);
        System.out.println("activity got: " + activity.toString());
        activity.setActivityType("born in");
        activityDAO.update(101010, 101010, activity);
        activity = activityDAO.getActivity("J", 101010);
        System.out.println("activity got: " + activity.toString());

//        userDAO.clear();
//        motionDAO.clear();
//        activityDAO.clear();
    }
}
