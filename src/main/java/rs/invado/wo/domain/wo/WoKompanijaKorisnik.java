package rs.invado.wo.domain.wo;

import org.hibernate.annotations.IndexColumn;
import rs.invado.wo.domain.ocp.OcpPoslovniPartner;

import javax.persistence.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikola
 * Date: 19.12.12.
 * Time: 22.12
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "WO_KOMPANIJA_KORISNIK", schema = "DAREX")
@SequenceGenerator(name = "woparametri_seq",
        sequenceName = "WO_ID_SEQ", allocationSize = 1)
public class WoKompanijaKorisnik {

    private Integer id;
    private String naziv;
    private int correspondingOJ;
    private String adresa;
    private String officialEmail;
    private String email;
    private Date datumRegistracije;
    private String PIB;
    private String maticniBroj;
    private String logoPath;
    private String naslovnaflash;
    private String kontaktflash;
    private String podrskaflash;
    private OcpPoslovniPartner ocpPoslovniPartnerMaticni;
    private List<WoPartnerSetting> woPartnerSettings = new ArrayList<WoPartnerSetting>(0);
    private List<WoParametri> woParametris = new ArrayList<WoParametri>(0);
    private Set<WoMapKompanijskaSkladista> woMapKompanijskaSkladistas = new HashSet<WoMapKompanijskaSkladista>(0);



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

    @Column(name = "NAZIV", nullable = false)
    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Column(name = "CORRESPONDING_OJ")
    public int getCorrespondingOJ() {
        return correspondingOJ;
    }

    public void setCorrespondingOJ(int correspondingOJ) {
        this.correspondingOJ = correspondingOJ;
    }

    @Column(name = "ADRESA")
    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    @Column(name = "OFFCIAL_EMAIL")
    public String getOfficialEmail() {
        return officialEmail;
    }

    public void setOfficialEmail(String officialEmail) {
        this.officialEmail = officialEmail;
    }

    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "DATUM_REGISTRACIJE")
    public Date getDatumRegistracije() {
        return datumRegistracije;
    }

    public void setDatumRegistracije(Date datumRegistracije) {
        this.datumRegistracije = datumRegistracije;
    }

    @Column(name = "PIB")
    public String getPIB() {
        return PIB;
    }

    public void setPIB(String PIB) {
        this.PIB = PIB;
    }

    @Column(name = "MATICNI_BROJ")
    public String getMaticniBroj() {
        return maticniBroj;
    }

    public void setMaticniBroj(String maticniBroj) {
        this.maticniBroj = maticniBroj;
    }

    @Column(name = "LOGO_PATH")
    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    @Column(name = "NASLOVNAFLASH")
    public String getNaslovnaflash() {
        return naslovnaflash;
    }

    public void setNaslovnaflash(String naslovnaflash) {
        this.naslovnaflash = naslovnaflash;
    }

    @Column(name = "KONTAKTFLASH")
    public String getKontaktflash() {
        return kontaktflash;
    }

    public void setKontaktflash(String kontaktflash) {
        this.kontaktflash = kontaktflash;
    }

    @Column(name = "PODRSKAFLASH")
    public String getPodrskaflash() {
        return podrskaflash;
    }

    public void setPodrskaflash(String podrskaflash) {
        this.podrskaflash = podrskaflash;
    }

    @OneToMany(targetEntity = WoPartnerSetting.class, fetch = FetchType.LAZY, mappedBy = "woKompanijaKorisnik")
    public List<WoPartnerSetting> getWoPartnerSettings() {
        return woPartnerSettings;
    }

    public void setWoPartnerSettings(List<WoPartnerSetting> woPartnerSettings) {
        this.woPartnerSettings = woPartnerSettings;
    }

    @OneToMany(targetEntity = WoParametri.class, fetch = FetchType.LAZY, mappedBy = "woKompanijaKorisnik")
    public List<WoParametri> getWoParametris() {
        return woParametris;
    }

    public void setWoParametris(List<WoParametri> woParametris) {
        this.woParametris = woParametris;
    }

    @OneToOne
    @JoinColumn(name = "POSLOVNI_PARTNER#", nullable = true, insertable = true, updatable = true)
    public OcpPoslovniPartner getOcpPoslovniPartnerMaticni() {
        return ocpPoslovniPartnerMaticni;
    }

    public void setOcpPoslovniPartnerMaticni(OcpPoslovniPartner ocpPoslovniPartnerMaticni) {
        this.ocpPoslovniPartnerMaticni = ocpPoslovniPartnerMaticni;
    }

    @OneToMany(targetEntity = WoMapKompanijskaSkladista.class, fetch = FetchType.EAGER, mappedBy = "woKompanijaKorisnik")
    public Set<WoMapKompanijskaSkladista> getWoMapKompanijskaSkladistas() {
        return woMapKompanijskaSkladistas;
    }

    public void setWoMapKompanijskaSkladistas(Set<WoMapKompanijskaSkladista> woMapKompanijskaSkladistas) {
        this.woMapKompanijskaSkladistas = woMapKompanijskaSkladistas;
    }
}
