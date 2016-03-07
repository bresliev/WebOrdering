package rs.invado.wo.domain.ocp;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Nikola on 01/03/2016.
 */

@Entity
@Table(name = "OCP_SASTAV_PROIZVODA", schema = "DAREX")
@NamedQueries({
        @NamedQuery(name = OcpSastavProizvoda.READ_BY_PROIZVOD_ULAZ,
                query = "SELECT x FROM OcpSastavProizvoda x WHERE x.proizvodUlaz = :proizvod")
})
public class OcpSastavProizvoda implements java.io.Serializable {

    public static final String READ_BY_PROIZVOD_ULAZ = "OcpSastavProizvoda.ReadByProizvodULaz";

    private OcpProizvod proizvodUlaz;
    private Integer proizvodIzlaz;
    private Integer sirina;
    private Integer cenaUlaz;
    private Integer id;
    private BigDecimal kolicinaUgradnje;

    private int maticnoSkladiste;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROIZVOD#_ULAZ", nullable = false, insertable = false, updatable = false)
    public OcpProizvod getProizvodUlaz() {
        return proizvodUlaz;
    }

    public void setProizvodUlaz(OcpProizvod proizvodUlaz) {
        this.proizvodUlaz = proizvodUlaz;
    }

    @Column(name = "PROIZVOD#_IZLAZ")
    public Integer getProizvodIzlaz() {
        return proizvodIzlaz;
    }

    public void setProizvodIzlaz(Integer proizvodIzlaz) {
        this.proizvodIzlaz = proizvodIzlaz;
    }

    @Column(name = "SIRINA")
    public Integer getSirina() {
        return sirina;
    }

    public void setSirina(Integer sirina) {
        this.sirina = sirina;
    }

    @Column(name = "CENA_ULAZ")
    public Integer getCenaUlaz() {
        return cenaUlaz;
    }

    public void setCenaUlaz(Integer cenaUlaz) {
        this.cenaUlaz = cenaUlaz;
    }

    @Id
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "KOLICINA_UGRADNJE")
    public BigDecimal getKolicinaUgradnje() {
        return kolicinaUgradnje;
    }

    public void setKolicinaUgradnje(BigDecimal kolicinaUgradnje) {
        this.kolicinaUgradnje = kolicinaUgradnje;
    }


    @Transient
    public int getMaticnoSkladiste() {
        return maticnoSkladiste;
    }

    public void setMaticnoSkladiste(int maticnoSkladiste) {
        this.maticnoSkladiste = maticnoSkladiste;
    }

}
