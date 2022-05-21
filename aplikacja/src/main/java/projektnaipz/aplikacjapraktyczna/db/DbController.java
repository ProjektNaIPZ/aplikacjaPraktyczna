package projektnaipz.aplikacjapraktyczna.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import projektnaipz.aplikacjapraktyczna.db.model.Ankieta;
import projektnaipz.aplikacjapraktyczna.db.model.Odpowiedz;
import projektnaipz.aplikacjapraktyczna.db.model.Uzytkownik;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbController {

    Connection conn = null;
    Statement st = null;
    ObjectMapper objectMapper = null;

    public DbController(){
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/aplprakt", "root", "roma");
            System.out.println("Połączono z db!");

            objectMapper = new ObjectMapper();
            st = conn.createStatement();

//            st.executeUpdate("DROP TABLE odpowiedzi");
//            st.executeUpdate("DROP TABLE ankiety");
//            st.executeUpdate("DROP TABLE uzytkownicy");

            st.execute("CREATE TABLE IF NOT EXISTS uzytkownicy " +
                    "(id INT(11) NOT NULL AUTO_INCREMENT, " +
                    "login VARCHAR(30) NOT NULL, " +
                    "haslo VARCHAR(255) NOT NULL, " +
                    "obiekt_uzytkownik json DEFAULT NULL, " +
                    "admin INT(1), " +
                    "CONSTRAINT id PRIMARY KEY (id))");

            st.execute("CREATE TABLE IF NOT EXISTS ankiety " +
                    "(kod_ankiety INT(11) NOT NULL, " +
                    "tytul_ankiety VARCHAR(255) NOT NULL, " +
                    "id_autora INT NOT NULL, " +
                    "FOREIGN KEY (id_autora) REFERENCES uzytkownicy(id), " +
                    "czy_otwarta INT(1) NOT NULL, " +
                    "obiekt_ankieta json DEFAULT NULL, " +
                    "CONSTRAINT kod_ankiety PRIMARY KEY (kod_ankiety))");

            st.execute("CREATE TABLE IF NOT EXISTS odpowiedzi " +
                    "(id INT(11) NOT NULL AUTO_INCREMENT, " +
                    "kod_ankiety INT NOT NULL, " +
                    "FOREIGN KEY (kod_ankiety) REFERENCES ankiety(kod_ankiety), " +
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

        //aktualizacja rekordu o obiekt_uzytkownik
        Uzytkownik u = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM uzytkownicy WHERE login = ?");
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u = new Uzytkownik(rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("haslo"),
                        rs.getBoolean("admin"));
                PreparedStatement upd = conn.prepareStatement("update uzytkownicy set obiekt_uzytkownik = ? where login = ?");
                upd.setString(1, toJson(u));
                upd.setString(2, login);
                upd.executeUpdate();
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
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
                    "INSERT INTO ankiety (kod_ankiety, tytul_ankiety, id_autora, czy_otwarta, obiekt_ankieta) " +
                            "values (?,?,?,?,?)");
            ps.setInt(1, ankieta.getKodAnkiety());
            ps.setString(2, ankieta.getTytulAnkiety());
            ps.setInt(3, ankieta.getAutor());
            ps.setBoolean(4, ankieta.getCzyOtwarta());
            ps.setString(5, toJson(ankieta));
            ps.executeUpdate();
        } catch (SQLException | JsonProcessingException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Dodano ankiete " + ankieta.getTytulAnkiety());
    }

    public Ankieta getAnkietaByKod(int kod){
        Ankieta a = new Ankieta();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM ankiety WHERE kod_ankiety = ?");
            ps.setInt(1, kod);
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
                    "INSERT INTO odpowiedzi (kod_ankiety, id_ankietowanego, obiekt_odpowiedz) " +
                            "values (?,?,?)");
            ps.setInt(1, odpowiedz.getKodAnkiety());
            ps.setInt(2, odpowiedz.getIdAnkietowanego());
            ps.setString(3, toJson(odpowiedz));
            ps.executeUpdate();
        } catch (SQLException | JsonProcessingException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Dodano odpowiedz.");
    }

    public boolean checkForAnswer(int idAnkietowanego, int kodAnkiety) {
        Ankieta a = new Ankieta();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `odpowiedzi` WHERE id_ankietowanego = ? AND kod_ankiety = ?");
            ps.setInt(1, idAnkietowanego);
            ps.setInt(2, kodAnkiety);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public List<Ankieta> getAnkietyByUser(int idAutora) {
        List<Ankieta> lista = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `ankiety` WHERE id_autora = ?");
            ps.setInt(1, idAutora);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String ankietaJson = rs.getString("obiekt_ankieta");
                Ankieta a = new Ankieta();
                a = objectMapper.readValue(ankietaJson, a.getClass());
                lista.add(a);
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
        return lista;
    }

    public List<Ankieta> getAllAnkiety() {
        List<Ankieta> lista = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `ankiety`");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String ankietaJson = rs.getString("obiekt_ankieta");
                Ankieta a = new Ankieta();
                a = objectMapper.readValue(ankietaJson, a.getClass());
                lista.add(a);
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
        return lista;
    }

    public void flipAccessByKodAnkiety(int kodAnkiety){
        Ankieta a = new Ankieta();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM ankiety WHERE kod_ankiety = ?");
            ps.setInt(1, kodAnkiety);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String ankietaJson = rs.getString("obiekt_ankieta");
                a = objectMapper.readValue(ankietaJson, a.getClass());
                a.setCzyOtwarta(!a.getCzyOtwarta());
                PreparedStatement upd = conn.prepareStatement("update ankiety set czy_otwarta = ?, obiekt_ankieta = ? where kod_ankiety = ?");
                upd.setBoolean(1, a.getCzyOtwarta());
                upd.setString(2, toJson(a));
                upd.setInt(3, kodAnkiety);
                upd.executeUpdate();
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Odpowiedz> getOdpByKodAnkiety(int kodAnkiety) {
        List<Odpowiedz> lista = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `odpowiedzi` WHERE kod_ankiety = ?");
            ps.setInt(1, kodAnkiety);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String odpowiedzJson = rs.getString("obiekt_odpowiedz");
                Odpowiedz odp = new Odpowiedz();
                odp = objectMapper.readValue(odpowiedzJson, odp.getClass());
                lista.add(odp);
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
        return lista;
    }

    private String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }
}