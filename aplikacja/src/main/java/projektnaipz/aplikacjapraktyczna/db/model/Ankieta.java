package projektnaipz.aplikacjapraktyczna.db.model;

import java.util.List;

public class Ankieta {
    private String tytulAnkiety;
    private int autor;
    private String kodAnkiety;
    private List<Pytanie> listaPytan;
    private boolean czyOtwarta;

    public Ankieta(String tytulAnkiety, int autor, String kodAnkiety, List<Pytanie> listaPytan) {
        this.tytulAnkiety = tytulAnkiety;
        this.autor = autor;
        this.kodAnkiety = kodAnkiety;
        this.listaPytan = listaPytan;
        this.czyOtwarta = true;
    }


    public String getTytulAnkiety() {
        return tytulAnkiety;
    }

    public void setTytulAnkiety(String tytulAnkiety) {
        this.tytulAnkiety = tytulAnkiety;
    }

    public int getAutor() {
        return autor;
    }

    public void setAutor(int autor) {
        this.autor = autor;
    }

    public String getKodAnkiety() {
        return kodAnkiety;
    }

    public void setKodAnkiety(String kodAnkiety) {
        this.kodAnkiety = kodAnkiety;
    }

    public List<Pytanie> getListaPytan() {
        return listaPytan;
    }

    public void setListaPytan(List<Pytanie> listaPytan) {
        this.listaPytan = listaPytan;
    }

    public boolean getCzyOtwarta() {
        return czyOtwarta;
    }

    public void setCzyOtwarta(boolean czyOtwarta) {
        this.czyOtwarta = czyOtwarta;
    }
}
