import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by doetken on 28.09.2016.
 */
public class Initialisierer {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost/wetter";
        String user = "postgres";
        String pass = "password";
        String fileName = "";
        for (String befehl : Initialisierer.readSQLFile("./Unterlagen/wetter-db/wetterdaten_tabellen.sql")) {
            System.out.println(befehl);
        }
//        Initialisierer.readSQLFile();

        try (Connection con = DriverManager.getConnection(url, user, pass); Statement st = con.createStatement()) {

        } catch (SQLException e) {
            System.out.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }

    }

    private static List<String> readSQLFile(String fileName) {
        List<String> ergebnis = new ArrayList<>();
        try (BufferedReader rd = new BufferedReader(new FileReader(fileName))) {
            String zeile = "";
            String befehl = "";

            while ((zeile = rd.readLine()) != null) {
                befehl += zeile;
                if (befehl.endsWith(";")) {
                    ergebnis.add(befehl);
                    befehl = "";
                }
                befehl.trim();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ergebnis;
    }

}