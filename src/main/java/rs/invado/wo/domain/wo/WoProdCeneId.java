package rs.invado.wo.domain.wo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by Nikola on 14/06/2016.
 */
@Embeddable
public class WoProdCeneId implements java.io.Serializable{

    private String klasaCene;
    private int organizacionaJedinica;
    private int idCenovnik;
    private int idKlasaCene;

    @Column(name = "KLASACENE")
    public String getKlasaCene() {
        return klasaCene;
    }

    public void setKlasaCene(String klasaCene) {
        this.klasaCene = klasaCene;
    }

    @Column(name = "ORGANIZACIONA_JEDINICA#")
    public int getOrganizacionaJedinica() {
        return organizacionaJedinica;
    }

    public void setOrganizacionaJedinica(int organizacionaJedinica) {
        this.organizacionaJedinica = organizacionaJedinica;
    }

    @Column(name = "ID_CENOVNIK")
    public int getIdCenovnik() {
        return idCenovnik;
    }

    public void setIdCenovnik(int idCenovnik) {
        this.idCenovnik = idCenovnik;
    }

    @Column(name = "ID_KLASA_CENE")
    public int getIdKlasaCene() {
        return idKlasaCene;
    }

    public void setIdKlasaCene(int idKlasaCene) {
        this.idKlasaCene = idKlasaCene;
    }

}
