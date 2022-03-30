package projektnaipz.aplikacjapraktyczna.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Odpowiedzi {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String kod_ankiety;
    private String email_uzytkownika;
    private String id_transakcji;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKod_ankiety() {
        return kod_ankiety;
    }

    public void setKod_ankiety(String kod_ankiety) {
        this.kod_ankiety = kod_ankiety;
    }

    public String getEmail_uzytkownika() {
        return email_uzytkownika;
    }

    public void setEmail_uzytkownika(String email_uzytkownika) {
        this.email_uzytkownika = email_uzytkownika;
    }

    public String getId_transakcji() {
        return id_transakcji;
    }

    public void setId_transakcji(String id_transakcji) {
        this.id_transakcji = id_transakcji;
    }
}
