package rs.invado.wo.domain.wo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Nikola on 07/08/2016.
 */
@Entity
@Table(name = "WO_SORT_PER_CLASS", schema = "DAREX"
)
public class WoSortPerClass {

    private Integer id;
    private Integer woVrstaKlasifikacije;
    private String woKlasifikacija;
    private Integer ocpVrstaKlasifikacije;
    private Integer idKompanijeKorisnik;

    @Id
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "WO_VRSTA_KLASIFIKACIJE#")
    public Integer getWoVrstaKlasifikacije() {
        return woVrstaKlasifikacije;
    }

    public void setWoVrstaKlasifikacije(Integer woVrstaKlasifikacije) {
        this.woVrstaKlasifikacije = woVrstaKlasifikacije;
    }

    @Column(name = "WO_KLASIFIKACIJA#")
    public String getWoKlasifikacija() {
        return woKlasifikacija;
    }

    public void setWoKlasifikacija(String woKlasifikacija) {
        this.woKlasifikacija = woKlasifikacija;
    }

    @Column(name = "OCP_VRSTA_KLASIFIKACIJE#")
    public Integer getOcpVrstaKlasifikacije() {
        return ocpVrstaKlasifikacije;
    }

    public void setOcpVrstaKlasifikacije(Integer ocpVrstaKlasifikacije) {
        this.ocpVrstaKlasifikacije = ocpVrstaKlasifikacije;
    }

    @Column(name = "ID_KOMPANIJE_KORISNIK")
    public Integer getIdKompanijeKorisnik() {
        return idKompanijeKorisnik;
    }

    public void setIdKompanijeKorisnik(Integer idKompanijeKorisnik) {
        this.idKompanijeKorisnik = idKompanijeKorisnik;
    }
}
