package projektnaipz.aplikacjapraktyczna.db.model;

public class Uzytkownik {
    private final int id;
    private final String login;
    private final String haslo;
    private final boolean admin;

    public Uzytkownik(int id, String login, String haslo, boolean admin){
        this.id = id;
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

    public int getId() {
        return id;
    }
}
