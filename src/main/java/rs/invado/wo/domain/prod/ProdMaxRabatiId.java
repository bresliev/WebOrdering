package rs.invado.wo.domain.prod;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by Nikola on 17/09/2016.
 */
@Embeddable
public class ProdMaxRabatiId implements Serializable{

    private int organizacionaJedinica;
    private int vrstaKlasifikacije;
    private String klasifikacija;

    @Column(name="ORGANIZACIONA_JEDINICA#", nullable=false, precision=5, scale=0)
    public int getOrganizacionaJedinica() {
        return organizacionaJedinica;
    }

    public void setOrganizacionaJedinica(int organizacionaJedinica) {
        this.organizacionaJedinica = organizacionaJedinica;
    }

    @Column(name="VRSTA_KLASIFIKACIJE#", nullable=false, precision=5, scale=0)
    public int getVrstaKlasifikacije() {
        return vrstaKlasifikacije;
    }

    public void setVrstaKlasifikacije(int vrstaKlasifikacije) {
        this.vrstaKlasifikacije = vrstaKlasifikacije;
    }

    @Column(name="KLASIFIKACIJE#", nullable=false, precision=5, scale=0)
    public String getKlasifikacija() {
        return klasifikacija;
    }

    public void setKlasifikacija(String klasifikacija) {
        this.klasifikacija = klasifikacija;
    }
}
