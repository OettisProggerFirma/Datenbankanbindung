import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by doetken on 05.10.2016.
 */
public class InitialisiererPrepared {
    private static String stationCSV = "./Unterlagen/wetter-db/wetterdaten_Wetterstation.csv";
    private static String messungCSV = "./Unterlagen/wetter-db/wetterdaten_Wettermessung.csv";

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost/wetter";
        String user = "postgres";
        String pass = "password";
        try (Connection con = DriverManager.getConnection(url, user, pass)) {
            InitialisiererPrepared.fillStationen(InitialisiererPrepared.readCSVFile(stationCSV), con);
            InitialisiererPrepared.fillMessung(InitialisiererPrepared.readCSVFile(messungCSV), con);

        } catch (SQLException e) {
            System.out.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }

    }

    private static List<String> readCSVFile(String filename) {
        List<String> liste = new ArrayList<String>();

        try {
            BufferedReader bfr = new BufferedReader(new FileReader(filename));
            String zeile = "";

            while ((zeile = bfr.readLine()) != null) {
                if (zeile.contains("Stations_ID") || zeile.contains("S_ID")) {
                    continue;
                }
                zeile = zeile.trim();
                if (zeile.length() > 0) {
                    liste.add(zeile);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return liste;
    }

    private static void fillStationen(List<String> daten, Connection con) {
        String sqlString = "INSERT INTO wetterstation (s_id, standort, geo_breite, geo_laenge, hoehe, betreiber) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement pst = con.prepareStatement(sqlString)) {
            for (String s : daten) {
                String[] splitter = s.split(";");
                if (splitter.length > 0) {
                    pst.setInt(1, Integer.parseInt(splitter[0]));
                } else {
                    pst.setNull(1, JDBCType.INTEGER.getVendorTypeNumber());
                }
                if (splitter.length > 1) {
                    pst.setString(2, splitter[1]);
                } else {
//                    todo varchar
                    pst.setNull(2, JDBCType.VARCHAR.getVendorTypeNumber());
                }
                if (splitter.length > 2) {
                    pst.setDouble(3, Double.parseDouble(splitter[2].replace(',', '.')));
                } else {
                    pst.setNull(3, JDBCType.NUMERIC.getVendorTypeNumber());
                }
                if (splitter.length > 3) {
                    pst.setDouble(4, Double.parseDouble(splitter[3].replace(',', '.')));
                } else {
                    pst.setNull(4, JDBCType.NUMERIC.getVendorTypeNumber());
                }
                if (splitter.length > 4) {
                    pst.setInt(5, Integer.parseInt(splitter[4]));
                } else {
                    pst.setNull(5, JDBCType.INTEGER.getVendorTypeNumber());
                }
                if (splitter.length > 5) {
                    pst.setString(6, splitter[5]);
                } else {
                    pst.setNull(6, JDBCType.VARCHAR.getVendorTypeNumber());
                }
                pst.addBatch();
            }
            pst.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void fillMessung(List<String> daten, Connection con) {
        String sqlString = "INSERT INTO wettermessung (fk_s_id, datum, qualitaet, min_5cm, min_2m, mittel_2m, max_2m, relative_feuchte, mittel_windstaerke, max_windstaerke, sonnenscheindauer, mittel_bedeckungsgrad, niederschlagshoehe, mittel_luftdruck) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement pst2 = con.prepareStatement(sqlString)) {
            for (String s2 : daten) {
                String[] splitter2 = s2.split(";");

                pst2.setInt(1, Integer.parseInt(splitter2[0]));
                pst2.setDate(2, Date.valueOf(splitter2[1]));
                pst2.setInt(3, Integer.parseInt(splitter2[2]));
                pst2.setDouble(4, Double.parseDouble(splitter2[3].replace(',', '.')));
                pst2.setDouble(5, Double.parseDouble(splitter2[4].replace(',', '.')));
                pst2.setDouble(6, Double.parseDouble(splitter2[5].replace(',', '.')));
                pst2.setDouble(7, Double.parseDouble(splitter2[6].replace(',', '.')));
                pst2.setDouble(8, Double.parseDouble(splitter2[7].replace(',', '.')));
                pst2.setInt(9, Integer.parseInt(splitter2[8]));
                pst2.setDouble(10, Double.parseDouble(splitter2[9].replace(',', '.')));
                pst2.setDouble(11, Double.parseDouble(splitter2[10].replace(',', '.')));
                pst2.setDouble(12, Double.parseDouble(splitter2[11].replace(',', '.')));
                pst2.setDouble(13, Double.parseDouble(splitter2[12].replace(',', '.')));
                pst2.setDouble(14, Double.parseDouble(splitter2[13].replace(',', '.')));
                pst2.addBatch();
            }
            pst2.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
