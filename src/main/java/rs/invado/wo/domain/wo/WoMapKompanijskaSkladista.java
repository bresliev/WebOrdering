package rs.invado.wo.domain.wo;

import rs.invado.wo.domain.uz.UzSkladiste;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: nikola
 * Date: 5/8/13
 * Time: 4:38 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "WO_MAP_KOMPANIJSKA_SKLADISTA", schema = "DAREX")
@SequenceGenerator(name = "woparametri_seq",
        sequenceName = "WO_ID_SEQ", allocationSize = 1)
public class WoMapKompanijskaSkladista {

    private Integer id;
    private WoKompanijaKorisnik woKompanijaKorisnik;
    private UzSkladiste uzSkladisteRaspolozivo;
    private UzSkladiste uzSkladisteRezervacija;
    private boolean rezervisiURaspolozivo;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "woparametri_seq")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "ID_KOMPANIJE_KORISNIK", referencedColumnName = "ID", nullable=false, updatable=false, insertable=false)
    public WoKompanijaKorisnik getWoKompanijaKorisnik() {
        return woKompanijaKorisnik;
    }

    public void setWoKompanijaKorisnik(WoKompanijaKorisnik woKompanijaKorisnik) {
        this.woKompanijaKorisnik = woKompanijaKorisnik;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "ID_SKLADISTA_RASPOLOZIVO", referencedColumnName = "ID_SKLADISTA", nullable = false, updatable = false, insertable = false)
    public UzSkladiste getUzSkladisteRaspolozivo() {
        return uzSkladisteRaspolozivo;
    }

    public void setUzSkladisteRaspolozivo(UzSkladiste uzSkladisteRaspolozivo) {
        this.uzSkladisteRaspolozivo = uzSkladisteRaspolozivo;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "ID_SKLADISTA_REZERVACIJA", referencedColumnName = "ID_SKLADISTA", nullable = false, updatable = false, insertable = false)
    public UzSkladiste getUzSkladisteRezervacija() {
        return uzSkladisteRezervacija;
    }

    public void setUzSkladisteRezervacija(UzSkladiste uzSkladisteRezervacija) {
        this.uzSkladisteRezervacija = uzSkladisteRezervacija;
    }

    @Column(name = "REZERVISI_U_RASPOLOZIVO")
    public boolean isRezervisiURaspolozivo() {
        return rezervisiURaspolozivo;
    }

    public void setRezervisiURaspolozivo(boolean rezervisiURaspolozivo) {
        this.rezervisiURaspolozivo = rezervisiURaspolozivo;
    }
}
