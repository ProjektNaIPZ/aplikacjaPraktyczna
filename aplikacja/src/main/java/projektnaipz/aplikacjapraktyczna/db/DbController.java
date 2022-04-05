package projektnaipz.aplikacjapraktyczna.db;

import java.sql.*;

public class DbController {

    Connection conn = null;
    Statement st = null;

    public DbController(){
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/aplprakt", "root", "roma");
            System.out.println("Połączono!");

            st = conn.createStatement();

            st.execute("CREATE TABLE IF NOT EXISTS uzytkownicy " +
                    "(id INT(11) NOT NULL AUTO_INCREMENT, " +
                    "login VARCHAR(30) NOT NULL, " +
                    "haslo VARCHAR(200) NOT NULL, " +
                    "admin INT(1), " +
                    "CONSTRAINT id PRIMARY KEY (id))");

            st.executeUpdate("DELETE FROM uzytkownicy");

            st.executeUpdate("INSERT INTO uzytkownicy (login, haslo, admin) values ('admin', 'admin', '1')");

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

    public Uzytkownik findUserByLogin(String login){
        Uzytkownik u = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM uzytkownicy WHERE login = ?");
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next() == false) {
                return null;
            } else {
                u = new Uzytkownik(rs.getString("login"), rs.getString("haslo"), rs.getBoolean("admin"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return u;
    }
}
