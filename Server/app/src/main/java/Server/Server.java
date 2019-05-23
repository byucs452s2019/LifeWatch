package Server;
import Database.*;
/**
 * Created by haoyucn on 5/23/19.
 */

public class Server {

    public static void main(String[] argvs) {
        SQLDBConnection conn =  new SQLDBConnection();
        conn.createDBFile();

    }
}
