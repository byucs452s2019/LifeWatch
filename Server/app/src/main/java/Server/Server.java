package Server;
import Database.*;
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

//        if (userDAO.insert(user)){
//            System.out.println("successfully added user");
//        }
//        else {
//            System.out.println("failed to add user");
//        }
        userDAO.update();

        UserModel cuser = userDAO.getUserByUserName("hao");
        if (cuser == null) {
            System.out.println("failed to get user");
        }
        else {
            System.out.println(cuser.toString());
        }
    }
}
