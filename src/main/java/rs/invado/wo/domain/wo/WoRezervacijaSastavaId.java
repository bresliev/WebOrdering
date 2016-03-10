package rs.invado.wo.domain.wo;

import rs.invado.wo.domain.ocp.OcpProizvod;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by Nikola on 10/03/2016.
 */
@Embeddable
public class WoRezervacijaSastavaId implements Serializable {

    @Column(name = "WO_REZERVACIJA_ID")
    private WoRezervacija woRezervacija;
    @Column(name = "PROIZVOD#")
    private OcpProizvod proizvod;


    public WoRezervacijaSastavaId() {
    }

    public WoRezervacijaSastavaId(WoRezervacija woRezervacija, OcpProizvod proizvod) {
        this.woRezervacija = woRezervacija;
        this.proizvod = proizvod;
    }

    public WoRezervacija getWoRezervacija() {
        return woRezervacija;
    }

    public void setWoRezervacija(WoRezervacija woRezervacija) {
        this.woRezervacija = woRezervacija;
    }

    public OcpProizvod getProizvod() {
        return proizvod;
    }

    public void setProizvod(OcpProizvod proizvod) {
        this.proizvod = proizvod;
    }
}
