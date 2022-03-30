package projektnaipz.aplikacjapraktyczna.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ankieta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String kod_ankiety;
    private String id_transakcji;
    private String id_pierwszego_bloku;
    private String id_ostatniego_bloku;

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

    public String getId_transakcji() {
        return id_transakcji;
    }

    public void setId_transakcji(String id_transakcji) {
        this.id_transakcji = id_transakcji;
    }

    public String getId_pierwszego_bloku() {
        return id_pierwszego_bloku;
    }

    public void setId_pierwszego_bloku(String id_pierwszego_bloku) {
        this.id_pierwszego_bloku = id_pierwszego_bloku;
    }

    public String getId_ostatniego_bloku() {
        return id_ostatniego_bloku;
    }

    public void setId_ostatniego_bloku(String id_ostatniego_bloku) {
        this.id_ostatniego_bloku = id_ostatniego_bloku;
    }
}
