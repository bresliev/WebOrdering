package rs.invado.wo.domain.wo;

import rs.invado.wo.domain.ocp.OcpProizvod;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Nikola on 10/03/2016.
 */
@Entity
@Table(name = "WO_REZERVACIJA_SASTAVA", schema = "DAREX")
//@IdClass(WoRezervacijaSastavaId.class)
@NamedQueries({
        @NamedQuery(name = WoRezervacijaSastava.READ_BY_REZERVACIJA_AND_PRO,
                query = "SELECT x FROM WoRezervacijaSastava x WHERE x.woRezervacija.id = :woId and x.proizvod.proizvod = :proizvod")
})

@SequenceGenerator(name = "sastav_rezervacija_seq",
        sequenceName = "WO_SEQ_SASTAV_REZERVACIJA_ID", allocationSize = 1)
public class WoRezervacijaSastava {

    public static final String READ_BY_REZERVACIJA_AND_PRO = "WoRezervacijaSastava.ReadByRezervacijaAndPro";

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sastav_rezervacija_seq")
    private BigDecimal id;
    //@Id
    @ManyToOne
    @JoinColumns(value = {
            @JoinColumn(name = "WO_REZERVACIJA_ID", referencedColumnName = "ID")})
    private WoRezervacija woRezervacija;
    //@Id
    @ManyToOne
    @JoinColumns(value = {
            @JoinColumn(name = "PROIZVOD#", referencedColumnName = "PROIZVOD#")})
    private OcpProizvod proizvod;
    @Column(name = "ID_SKLADISTA")
    private int idSkladista;
    @Column(name = "KOLICINA")
    private BigDecimal kolicina;
    @Column(name = "ID_JEDINICE_MERE")
    private int idjedinicemere;
    @Column(name = "CENA")
    private BigDecimal cena;
    @Column(name = "RABAT")
    private BigDecimal rabat;
    @Column(name = "EKSTRA_RABAT")
    private BigDecimal ekstraRabat;
    @Column(name = "KOLPOPAKOVANJU")
    private BigDecimal kolPoPakovanju;
    @Column(name = "KOLICINA_UGRADNJE")
    private BigDecimal kolicinaUgradnje;
    @Column(name = "STATUS")
    private int status;



    public BigDecimal getId() {
        return this.id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
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

    public int getIdSkladista() {
        return idSkladista;
    }

    public void setIdSkladista(int idSkladista) {
        this.idSkladista = idSkladista;
    }

    public BigDecimal getKolicina() {
        return kolicina;
    }

    public void setKolicina(BigDecimal kolicina) {
        this.kolicina = kolicina;
    }

    public int getIdjedinicemere() {
        return idjedinicemere;
    }

    public void setIdjedinicemere(int idjedinicemere) {
        this.idjedinicemere = idjedinicemere;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }

    public BigDecimal getRabat() {
        return rabat;
    }

    public void setRabat(BigDecimal rabat) {
        this.rabat = rabat;
    }

    public BigDecimal getEkstraRabat() {
        return ekstraRabat;
    }

    public void setEkstraRabat(BigDecimal ekstraRabat) {
        this.ekstraRabat = ekstraRabat;
    }

    public BigDecimal getKolPoPakovanju() {
        return kolPoPakovanju;
    }

    public void setKolPoPakovanju(BigDecimal kolPoPakovanju) {
        this.kolPoPakovanju = kolPoPakovanju;
    }

    public BigDecimal getKolicinaUgradnje() {
        return kolicinaUgradnje;
    }

    public void setKolicinaUgradnje(BigDecimal kolicinaUgradnje) {
        this.kolicinaUgradnje = kolicinaUgradnje;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
