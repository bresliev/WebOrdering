package rs.invado.wo.dto;

/**
 * Created by Nikola on 18/06/2016.
 */
public class VrAtrProizvod {

    private Integer vrstaKlasifikacije;
    private String klasifikacija;
    private Integer proizvod;
    private Integer atribut;
    private String vrednost;


    public VrAtrProizvod(int vrstaKlasifikacije, String klasifikacija, int proizvod, int atribut, String vrednost) {
        this.vrstaKlasifikacije = vrstaKlasifikacije;
        this.klasifikacija = klasifikacija;
        this.proizvod = proizvod;
        this.atribut = atribut;
        this.vrednost = vrednost;
    }

    public int getVrstaKlasifikacije() {
        return vrstaKlasifikacije;
    }

    public void setVrstaKlasifikacije(int vrstaKlasifikacije) {
        this.vrstaKlasifikacije = vrstaKlasifikacije;
    }

    public String getKlasifikacija() {
        return klasifikacija;
    }

    public void setKlasifikacija(String klasifikacija) {
        this.klasifikacija = klasifikacija;
    }

    public int getProizvod() {
        return proizvod;
    }

    public void setProizvod(int proizvod) {
        this.proizvod = proizvod;
    }

    public int getAtribut() {
        return atribut;
    }

    public void setAtribut(int atribut) {
        this.atribut = atribut;
    }

    public String getVrednost() {
        return vrednost;
    }

    public void setVrednost(String vrednost) {
        this.vrednost = vrednost;
    }
}
