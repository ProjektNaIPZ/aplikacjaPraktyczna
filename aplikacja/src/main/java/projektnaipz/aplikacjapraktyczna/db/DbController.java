package projektnaipz.aplikacjapraktyczna.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import projektnaipz.aplikacjapraktyczna.db.model.Ankieta;
import projektnaipz.aplikacjapraktyczna.db.model.Odpowiedz;
import projektnaipz.aplikacjapraktyczna.db.model.Uzytkownik;

import java.io.IOException;
import java.sql.*;

public class DbController {

    Connection conn = null;
    Statement st = null;
    ObjectMapper objectMapper = null;

    public DbController(){
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/aplprakt", "root", "roma");
            System.out.println("Połączono!");

            objectMapper = new ObjectMapper();
            st = conn.createStatement();

//            st.executeUpdate("DROP TABLE odpowiedzi");
//            st.executeUpdate("DROP TABLE ankiety");
//            st.executeUpdate("DROP TABLE uzytkownicy");

            st.execute("CREATE TABLE IF NOT EXISTS uzytkownicy " +
                    "(id INT(11) NOT NULL AUTO_INCREMENT, " +
                    "login VARCHAR(30) NOT NULL, " +
                    "haslo VARCHAR(255) NOT NULL, " +
                    "admin INT(1), " +
                    "CONSTRAINT id PRIMARY KEY (id))");

            st.execute("CREATE TABLE IF NOT EXISTS ankiety " +
                    "(id INT(11) NOT NULL AUTO_INCREMENT, " +
                    "tytul_ankiety VARCHAR(255) NOT NULL, " +
                    "id_autora INT NOT NULL, " +
                    "FOREIGN KEY (id_autora) REFERENCES uzytkownicy(id), " +
                    "kod_ankiety VARCHAR(255) NOT NULL, " +
                    "czy_otwarta INT(1) NOT NULL, " +
                    "obiekt_ankieta json DEFAULT NULL, " +
                    "CONSTRAINT id PRIMARY KEY (id))");

            st.execute("CREATE TABLE IF NOT EXISTS odpowiedzi " +
                    "(id INT(11) NOT NULL AUTO_INCREMENT, " +
                    "id_ankiety INT NOT NULL, " +
                    "FOREIGN KEY (id_ankiety) REFERENCES ankiety(id), " +
                    "id_ankietowanego INT NOT NULL, " +
                    "FOREIGN KEY (id_ankietowanego) REFERENCES uzytkownicy(id), " +
                    "obiekt_odpowiedz json DEFAULT NULL, " +
                    "CONSTRAINT id PRIMARY KEY (id))");

//            st.executeUpdate("DELETE FROM uzytkownicy");
//            st.executeUpdate("DELETE FROM ankiety");
//            st.executeUpdate("DELETE FROM odpowiedzi");
//            st.executeUpdate("INSERT INTO uzytkownicy (login, haslo, admin) values ('admin', 'admin', '1')");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void register(String login, String haslo){
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO uzytkownicy (login, haslo, admin) values (?,?,0)");
            ps.setString(1, login);
            ps.setString(2, haslo);
            ps.executeUpdate();
        } catch (SQLException throwables) {
        throwables.printStackTrace();
        }
        System.out.println("Zarejestrowano " + login + " " + haslo);
    }

    public Uzytkownik getUserByLogin(String login){
        Uzytkownik u = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM uzytkownicy WHERE login = ?");
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            } else {
                u = new Uzytkownik(rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("haslo"),
                        rs.getBoolean("admin"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return u;
    }

    public Uzytkownik getUserById(int id){
        Uzytkownik u = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM uzytkownicy WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            } else {
                u = new Uzytkownik(rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("haslo"),
                        rs.getBoolean("admin"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return u;
    }

    public void addAnkieta(Ankieta ankieta) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO ankiety (tytul_ankiety, id_autora, kod_ankiety, czy_otwarta, obiekt_ankieta) " +
                            "values (?,?,?,?,?)");
            ps.setString(1, ankieta.getTytulAnkiety());
            ps.setInt(2, ankieta.getAutor());
            ps.setString(3, ankieta.getKodAnkiety());
            ps.setBoolean(4, ankieta.getCzyOtwarta());
            ps.setString(5, toJson(ankieta));
            ps.executeUpdate();
        } catch (SQLException | JsonProcessingException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Dodano ankiete " + ankieta.getTytulAnkiety());
    }

    public Ankieta getAnkietaByKod(String kod){
        Ankieta a = new Ankieta();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM ankiety WHERE kod_ankiety = ?");
            ps.setString(1, kod);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            } else {
                String ankietaJson = rs.getString("obiekt_ankieta");
                a = objectMapper.readValue(ankietaJson, a.getClass());
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
        return a;
    }

    public void addOdp(Odpowiedz odpowiedz) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO odpowiedzi (id_ankiety, id_ankietowanego, obiekt_odpowiedz) " +
                            "values (?,?,?)");
            ps.setInt(1, odpowiedz.getIdAnkiety());
            ps.setInt(2, odpowiedz.getIdAnkietowanego());
            ps.setString(3, toJson(odpowiedz));
            ps.executeUpdate();
        } catch (SQLException | JsonProcessingException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Dodano odpowiedz.");
    }

//    st.execute("CREATE TABLE IF NOT EXISTS odpowiedzi " +
//            "(id INT(11) NOT NULL AUTO_INCREMENT, " +
//            "id_ankiety INT NOT NULL, " +
//            "FOREIGN KEY (id_ankiety) REFERENCES ankiety(id), " +
//            "id_ankietowanego INT NOT NULL, " +
//            "FOREIGN KEY (id_ankietowanego) REFERENCES uzytkownicy(id), " +
//            "obiekt_odpowiedz json DEFAULT NULL, " +
//            "CONSTRAINT id PRIMARY KEY (id))");

    public boolean checkForAnswer(int idAnkietowanego, int idAnkiety) {
        Ankieta a = new Ankieta();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `odpowiedzi` WHERE id_ankietowanego = ? AND id_ankiety = ?");
            ps.setInt(1, idAnkietowanego);
            ps.setInt(2, idAnkiety);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    private String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }
}