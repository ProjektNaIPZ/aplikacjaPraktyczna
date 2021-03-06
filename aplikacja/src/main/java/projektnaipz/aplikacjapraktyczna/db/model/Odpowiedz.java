package projektnaipz.aplikacjapraktyczna.db.model;

import java.util.ArrayList;
import java.util.List;

public class Odpowiedz {

    private int idAnkietowanego;
    private int kodAnkiety;
    private List<Integer> listaPkt = new ArrayList<>();

    public Odpowiedz(int idAnkietowanego, int idAnkiety, List<Integer> listaPkt) {
        this.idAnkietowanego = idAnkietowanego;
        this.kodAnkiety = idAnkiety;
        this.listaPkt = listaPkt;
    }

    public Odpowiedz(){}

    public List<Integer> getListaPkt() {
        return listaPkt;
    }

    public void setListaPkt(List<Integer> listaPkt) {this.listaPkt = listaPkt;}

    public int getIdAnkietowanego() {
        return idAnkietowanego;
    }

    public void setIdAnkietowanego(int idAnkietowanego) {
        this.idAnkietowanego = idAnkietowanego;
    }

    public int getKodAnkiety() {
        return kodAnkiety;
    }

    public void setKodAnkiety(int kodAnkiety) {
        this.kodAnkiety = kodAnkiety;
    }
}