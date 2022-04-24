package projektnaipz.aplikacjapraktyczna.db.model;

public class Uzytkownik {
    private int id;
    private String login;
    private String haslo;
    private boolean admin;

    public Uzytkownik(int id, String login, String haslo, boolean admin){
        this.id = id;
        this.login = login;
        this.haslo = haslo;
        this.admin = admin;
    }

    public Uzytkownik() {}

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
