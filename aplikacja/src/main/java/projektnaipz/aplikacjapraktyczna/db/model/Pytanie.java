package projektnaipz.aplikacjapraktyczna.db.model;

import java.util.List;

public class Pytanie {
    public String trescPytania;
    public List<String> listaOdp;
    public Integer liczbaPunktow;


    public Pytanie(String trescPytania, List<String> listaOdp, Integer punkty) {
        this.trescPytania = trescPytania;
        this.listaOdp = listaOdp;
        this.liczbaPunktow = punkty;
    }
}
