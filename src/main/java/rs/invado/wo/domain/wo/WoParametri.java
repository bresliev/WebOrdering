package rs.invado.wo.domain.wo;

// Generated Dec 11, 2012 10:59:44 PM by Hibernate Tools 3.4.0.CR1

import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * WoParametri generated by hbm2java
 * tabela alterovana i dodati atributi datum_od, datum_do i id
 */
@Entity
@Table(name = "WO_PARAMETRI", schema = "DAREX")
@SequenceGenerator(   name = "woparametri_seq",
        sequenceName = "WO_ID_SEQ", allocationSize = 1)
public class WoParametri implements java.io.Serializable {


    private Integer id;
    private int vrstaKlasifikacijeMeni;
    private int vrstaKlasifikacijeFilter;
    private int drugiNivoFiltera;
    private Short idVdGen;
    private BigDecimal vrstaStavke;
    private BigDecimal virmanIdVd;
    private BigDecimal gotovinaIdVd;
    private String mailserver;
    private String womailaddress;
    private String mailaddress;
    private String password;
    private Date datumDo;
    private Integer mailServerPort;
    private String confirmMailContent;
    private Integer skladisteRezervacije;
    private WoKompanijaKorisnik woKompanijaKorisnik;
    private String jedinicaMereRezervacije;
    private String klasaCene;
    private List<WoSetPoNacinPlacanja> woSetPoNacinPlacanja = new ArrayList<WoSetPoNacinPlacanja>(0);

    public WoParametri() {
    }

    public WoParametri(Integer id, int vrstaKlasifikacijeMeni,
                         int vrstaKlasifikacijeFilter, int drugiNivoFiltera,
                         Short idVdGen, BigDecimal vrstaStavke, BigDecimal virmanIdVd,
                         BigDecimal gotovinaIdVd, String mailserver, String womailaddress,
                         String mailaddress, String password, Date datumOd, Date datumDo,
                         WoKompanijaKorisnik woKompanijaKorisnik) {
        this.id = id;
        this.vrstaKlasifikacijeMeni = vrstaKlasifikacijeMeni;
        this.vrstaKlasifikacijeFilter = vrstaKlasifikacijeFilter;
        this.drugiNivoFiltera = drugiNivoFiltera;
        this.idVdGen = idVdGen;
        this.vrstaStavke = vrstaStavke;
        this.virmanIdVd = virmanIdVd;
        this.gotovinaIdVd = gotovinaIdVd;
        this.mailserver = mailserver;
        this.womailaddress = womailaddress;
        this.mailaddress = mailaddress;
        this.password = password;
        this.datumDo = datumDo;
        this.woKompanijaKorisnik = woKompanijaKorisnik;
    }

    @Id
    @Column(name="ID", nullable=false, unique=true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "woparametri_seq")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "VRSTA_KLASIFIKACIJE_MENI", precision = 22, scale = 0)
    public int getVrstaKlasifikacijeMeni() {
        return this.vrstaKlasifikacijeMeni;
    }

    public void setVrstaKlasifikacijeMeni(int vrstaKlasifikacijeMeni) {
        this.vrstaKlasifikacijeMeni = vrstaKlasifikacijeMeni;
    }

    @Column(name = "VRSTA_KLASIFIKACIJE_FILTER", precision = 22, scale = 0)
    public int getVrstaKlasifikacijeFilter() {
        return this.vrstaKlasifikacijeFilter;
    }

    public void setVrstaKlasifikacijeFilter(int vrstaKlasifikacijeFilter) {
        this.vrstaKlasifikacijeFilter = vrstaKlasifikacijeFilter;
    }

    @Column(name = "DRUGI_NIVO_FILTERA", precision = 22, scale = 0)
    public int getDrugiNivoFiltera() {
        return this.drugiNivoFiltera;
    }

    public void setDrugiNivoFiltera(int drugiNivoFiltera) {
        this.drugiNivoFiltera = drugiNivoFiltera;
    }

    @Column(name = "ID_VD_GEN", precision = 3, scale = 0)
    public Short getIdVdGen() {
        return this.idVdGen;
    }

    public void setIdVdGen(Short idVdGen) {
        this.idVdGen = idVdGen;
    }

    @Column(name = "VRSTA_STAVKE", precision = 22, scale = 0)
    public BigDecimal getVrstaStavke() {
        return this.vrstaStavke;
    }

    public void setVrstaStavke(BigDecimal vrstaStavke) {
        this.vrstaStavke = vrstaStavke;
    }

    @Column(name = "VIRMAN_ID_VD", precision = 22, scale = 0)
    public BigDecimal getVirmanIdVd() {
        return this.virmanIdVd;
    }

    public void setVirmanIdVd(BigDecimal virmanIdVd) {
        this.virmanIdVd = virmanIdVd;
    }

    @Column(name = "GOTOVINA_ID_VD", precision = 22, scale = 0)
    public BigDecimal getGotovinaIdVd() {
        return this.gotovinaIdVd;
    }

    public void setGotovinaIdVd(BigDecimal gotovinaIdVd) {
        this.gotovinaIdVd = gotovinaIdVd;
    }

    @Column(name = "MAILSERVER", length = 100)
    public String getMailserver() {
        return this.mailserver;
    }

    public void setMailserver(String mailserver) {
        this.mailserver = mailserver;
    }

    @Column(name = "WOMAILADDRESS", length = 100)
    public String getWomailaddress() {
        return this.womailaddress;
    }

    public void setWomailaddress(String womailaddress) {
        this.womailaddress = womailaddress;
    }

    @Column(name = "MAILADDRESS", length = 2000)
    public String getMailaddress() {
        return this.mailaddress;
    }

    public void setMailaddress(String mailaddress) {
        this.mailaddress = mailaddress;
    }

    @Column(name = "PASSWORD", length = 100)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "DATUM_DO", nullable = true)
    public Date getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(Date datumDo) {
        this.datumDo = datumDo;
    }

    @Column(name = "MAILSERVERPORT")
    public Integer getMailServerPort() {
        return mailServerPort;
    }

    public void setMailServerPort(Integer mailServerPort) {
        this.mailServerPort = mailServerPort;
    }

    @Column(name = "CONFIRMMESSAGECONTENT")
    public String getConfirmMailContent() {
        return confirmMailContent;
    }

    public void setConfirmMailContent(String confirmMailContent) {
        this.confirmMailContent = confirmMailContent;
    }

    @Column(name = "JEDINICA_MERE_REZERVACIJE")
    public String getJedinicaMereRezervacije() {
        return jedinicaMereRezervacije;
    }

    public void setJedinicaMereRezervacije(String jedinicaMereRezervacije) {
        this.jedinicaMereRezervacije = jedinicaMereRezervacije;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name =  "ID_KOMPANIJA_KORISNIK", referencedColumnName = "ID", insertable = true, updatable = true)
    public WoKompanijaKorisnik getWoKompanijaKorisnik() {
        return woKompanijaKorisnik;
    }

    public void setWoKompanijaKorisnik(WoKompanijaKorisnik woKompanijaKorisnik) {
        this.woKompanijaKorisnik = woKompanijaKorisnik;
    }


    @OneToMany(targetEntity = WoSetPoNacinPlacanja.class, fetch = FetchType.EAGER, mappedBy = "parametri")
    public List<WoSetPoNacinPlacanja> getWoSetPoNacinPlacanja() {

        return woSetPoNacinPlacanja;
    }

    public void setWoSetPoNacinPlacanja(List<WoSetPoNacinPlacanja> woSetPoNacinPlacanja) {
        this.woSetPoNacinPlacanja = woSetPoNacinPlacanja;
    }

    @Column(name="KLASA_CENE")
    public String getKlasaCene() {
        return klasaCene;
    }

    public void setKlasaCene(String klasaCene) {
        this.klasaCene = klasaCene;
    }
}
