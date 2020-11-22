package rs.invado.wo.domain.ocp;

// Generated Dec 9, 2012 6:19:17 PM by Hibernate Tools 3.4.0.CR1

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import rs.invado.wo.dto.VrAtrProizvod;

import javax.persistence.*;

/**
 * OcpVrAtrProizvod generated by hbm2java
 */


@Entity
@Table(name = "OCP_VR_ATR_PROIZVOD", schema = "DAREX")
public class OcpVrAtrProizvod implements java.io.Serializable {

    private OcpVrAtrProizvodId id;
    private String vrednost;
    private OcpKlasifikacijaProizvoda ocpKlasifikacijaProizvoda = new OcpKlasifikacijaProizvoda();


    public OcpVrAtrProizvod() {
    }

    public OcpVrAtrProizvod(OcpVrAtrProizvodId id) {
        this.id = id;
    }

    public OcpVrAtrProizvod(OcpVrAtrProizvodId id, String vrednost) {
        this.id = id;
        this.vrednost = vrednost;
    }

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "proizvod", column = @Column(name = "PROIZVOD#", nullable = false, precision = 7, scale = 0)),
            @AttributeOverride(name = "klasifikacija", column = @Column(name = "KLASIFIKACIJA#", nullable = false, length = 15)),
            @AttributeOverride(name = "vrstaKlasifikacije", column = @Column(name = "VRSTA_KLASIFIKACIJE#", nullable = false, precision = 6, scale = 0)),
            @AttributeOverride(name = "atribut", column = @Column(name = "ATRIBUT#", nullable = false, precision = 6, scale = 0))})
    public OcpVrAtrProizvodId getId() {
        return this.id;
    }

    public void setId(OcpVrAtrProizvodId id) {
        this.id = id;
    }

    @Column(name = "VREDNOST", length = 30)
    public String getVrednost() {
        return this.vrednost;
    }

    public void setVrednost(String vrednost) {
        this.vrednost = vrednost;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({@JoinColumn(name = "PROIZVOD#", referencedColumnName = "PROIZVOD#", insertable = false, updatable = false, nullable = false),
            @JoinColumn(name = "VRSTA_KLASIFIKACIJE#", referencedColumnName = "VRSTA_KLASIFIKACIJE#", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "KLASIFIKACIJA#", referencedColumnName = "KLASIFIKACIJA#", nullable = false, insertable = false, updatable = false)})
    public OcpKlasifikacijaProizvoda getOcpKlasifikacijaProizvoda() {
        return ocpKlasifikacijaProizvoda;
    }

    public void setOcpKlasifikacijaProizvoda(OcpKlasifikacijaProizvoda ocpKlasifikacijaProizvoda) {
        this.ocpKlasifikacijaProizvoda = ocpKlasifikacijaProizvoda;
    }


}
