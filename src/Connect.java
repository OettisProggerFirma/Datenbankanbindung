import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Scanner;

/**
 * Created by doetken on 28.09.2016.
 */
public class Connect {
    public static void main(String[] args) {
        String url="jdbc:postgresql://localhost/wetter";
        String user="postgres";
        String pass="password";

        try (Connection con= DriverManager.getConnection(url, user, pass))
        {
            DatabaseMetaData meta =con.getMetaData();
            System.out.println(meta.getDatabaseProductName()+" "+meta.getDatabaseProductVersion());

            System.out.println("Enter drücken...");
            String s = new Scanner(System.in).nextLine();
        } catch (SQLException e){
            System.err.print("Fehler: " + e.getLocalizedMessage()+"("+e.getSQLState()+")");
        }
//Test
    }
}
