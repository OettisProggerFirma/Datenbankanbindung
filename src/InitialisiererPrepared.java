import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by doetken on 05.10.2016.
 */
public class InitialisiererPrepared {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost/wetter";
        String user = "postgres";
        String pass = "password";
        String fileName = "";

        try (Connection con = DriverManager.getConnection(url, user, pass); Statement st = con.createStatement()) {
            for (String sql : InitialisiererPrepared.readSQLFile("./Unterlagen/wetter-db/wetterdaten_tabellen.sql")) {
                st.addBatch(sql);
            }
            st.executeBatch();

        } catch (SQLException e) {
            System.out.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }
    }

    private static String[] readSQLFile(String s) {
        return new String[0];
    }
}
