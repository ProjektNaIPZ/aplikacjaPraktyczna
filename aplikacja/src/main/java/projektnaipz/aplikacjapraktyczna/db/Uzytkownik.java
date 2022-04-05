package projektnaipz.aplikacjapraktyczna.db;

public class Uzytkownik {
    private final String login;
    private final String haslo;
    private final boolean admin;

    public Uzytkownik(String login, String haslo, boolean admin){
        this.login = login;
        this.haslo = haslo;
        this.admin = admin;
    }

    public String getLogin() {
        return login;
    }

    public String getHaslo() {
        return haslo;
    }

    public boolean isAdmin() {
        return admin;
    }
}
