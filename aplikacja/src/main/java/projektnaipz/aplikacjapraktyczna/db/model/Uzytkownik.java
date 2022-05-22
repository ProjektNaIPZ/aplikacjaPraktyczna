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

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
