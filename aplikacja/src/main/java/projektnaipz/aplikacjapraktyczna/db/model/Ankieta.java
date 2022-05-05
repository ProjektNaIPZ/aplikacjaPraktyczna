package projektnaipz.aplikacjapraktyczna.db.model;

import java.util.ArrayList;
import java.util.List;

public class Ankieta {
    private int kodAnkiety;
    private String tytulAnkiety;
    private int autor;
    private List<Pytanie> listaPytan = new ArrayList<>();
    private boolean czyOtwarta;

    public Ankieta(int kodAnkiety, String tytulAnkiety, int autor, List<Pytanie> listaPytan) {
        this.kodAnkiety = kodAnkiety;
        this.tytulAnkiety = tytulAnkiety;
        this.autor = autor;
        this.listaPytan = listaPytan;
        this.czyOtwarta = true;
    }

    public Ankieta(){}

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

    public int getKodAnkiety() {
        return kodAnkiety;
    }

    public void setKodAnkiety(int kodAnkiety) {
        this.kodAnkiety = kodAnkiety;
    }
}
