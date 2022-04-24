package projektnaipz.aplikacjapraktyczna.db.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Pytanie {
    @JsonProperty("tresc_pytania")
    private String trescPytania;
    @JsonProperty("lista_odp")
    private List<String> listaOdp = new ArrayList<>();
    @JsonProperty("liczba_punktow")
    private Integer liczbaPunktow;

    public Pytanie(String trescPytania, List<String> listaOdp, Integer liczbaPunktow) {
        this.trescPytania = trescPytania;
        this.listaOdp = listaOdp;
        this.liczbaPunktow = liczbaPunktow;
    }
    public Pytanie(){}

    public String getTrescPytania() {
        return trescPytania;
    }

    public void setTrescPytania(String trescPytania) {
        this.trescPytania = trescPytania;
    }

    public List<String> getListaOdp() {
        return listaOdp;
    }

    public void setListaOdp(List<String> listaOdp) {
        this.listaOdp = listaOdp;
    }

    public Integer getLiczbaPunktow() {
        return liczbaPunktow;
    }

    public void setLiczbaPunktow(Integer liczbaPunktow) {
        this.liczbaPunktow = liczbaPunktow;
    }
}
