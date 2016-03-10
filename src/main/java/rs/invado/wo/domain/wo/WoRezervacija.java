package rs.invado.wo.domain.wo;
// Generated Dec 11, 2012 10:59:44 PM by Hibernate Tools 3.4.0.CR1


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import rs.invado.wo.domain.ocp.OcpProizvod;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * WoRezervacija generated by hbm2java
 */
@Entity
@Table(name = "WO_REZERVACIJA", schema = "DAREX")
@SequenceGenerator(name = "rezervacija_seq",
        sequenceName = "WO_SEQ_REZERVACIJA_ID", allocationSize = 1)
public class WoRezervacija implements java.io.Serializable {


    private BigDecimal id;
    private int poslovniPartner;
    private String sessionid;
    private int idSkladista;
    private OcpProizvod proizvod;
    private BigDecimal kolicina;
    private int statusRezervacije;
    private Timestamp datumivreme;
    private int idjedinicemere;
    private BigDecimal cena;
    private BigDecimal rabat;
    private String akcija;
    private BigDecimal ekstraRabat;
    private WoUser woUser;
    private BigDecimal kolPoPakovanju;
    private OcpProizvod compositeArticle;
    private BigDecimal compositeArticleQuantity;
    private BigDecimal compositeArticlePackQuantity;
    private int compositeArticleUnitOfMeasure;
    private BigDecimal compositeArticlePrice;


    private BigDecimal vrednost;
    private BigDecimal vrednostSaPorezom;

    /*
    List<WoRezervacijaSastava> woRezervacijaSastavaList = new ArrayList<WoRezervacijaSastava>(0);
*/


    public WoRezervacija() {
    }


    public WoRezervacija(BigDecimal id) {
        this.id = id;
    }

    public WoRezervacija(BigDecimal id, int poslovniPartner, String sessionid, int idSkladista, OcpProizvod proizvod, BigDecimal kolicina, int statusRezervacije, Timestamp datumivreme, int idjedinicemere, BigDecimal cena, BigDecimal rabat, String akcija) {
        this.id = id;
        this.poslovniPartner = poslovniPartner;
        this.sessionid = sessionid;
        this.idSkladista = idSkladista;
        this.proizvod = proizvod;
        this.kolicina = kolicina;
        this.statusRezervacije = statusRezervacije;
        this.datumivreme = datumivreme;
        this.idjedinicemere = idjedinicemere;
        this.cena = cena;
        this.rabat = rabat;
        this.akcija = akcija;
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "rezervacija_seq")
    public BigDecimal getId() {
        return this.id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }


    @Column(name = "POSLOVNI_PARTNER#", precision = 22, scale = 0)
    public int getPoslovniPartner() {
        return this.poslovniPartner;
    }

    public void setPoslovniPartner(int poslovniPartner) {
        this.poslovniPartner = poslovniPartner;
    }


    @Column(name = "SESSIONID", length = 2000)
    public String getSessionid() {
        return this.sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }


    @Column(name = "ID_SKLADISTA", precision = 22, scale = 0)
    public int getIdSkladista() {
        return this.idSkladista;
    }

    public void setIdSkladista(int idSkladista) {
        this.idSkladista = idSkladista;
    }


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PROIZVOD#", referencedColumnName = "PROIZVOD#", insertable = true, updatable = true, nullable = false)
    public OcpProizvod getProizvod() {
        return this.proizvod;
    }

    public void setProizvod(OcpProizvod proizvod) {
        this.proizvod = proizvod;
    }


    @Column(name = "KOLICINA", precision = 22, scale = 0)
    public BigDecimal getKolicina() {
        return this.kolicina;
    }

    public void setKolicina(BigDecimal kolicina) {
        this.kolicina = kolicina;
    }


    @Column(name = "STATUS_REZERVACIJE", precision = 22, scale = 0)
    public int getStatusRezervacije() {
        return this.statusRezervacije;
    }

    public void setStatusRezervacije(int statusRezervacije) {
        this.statusRezervacije = statusRezervacije;
    }


    @Column(name = "DATUMIVREME")
    public Timestamp getDatumivreme() {
        return this.datumivreme;
    }

    public void setDatumivreme(Timestamp datumivreme) {
        this.datumivreme = datumivreme;
    }


    @Column(name = "IDJEDINICEMERE", precision = 22, scale = 0)
    public int getIdjedinicemere() {
        return this.idjedinicemere;
    }

    public void setIdjedinicemere(int idjedinicemere) {
        this.idjedinicemere = idjedinicemere;
    }


    @Column(name = "CENA", precision = 22, scale = 0)
    public BigDecimal getCena() {
        return this.cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }


    @Column(name = "RABAT", precision = 22, scale = 0)
    public BigDecimal getRabat() {
        return this.rabat;
    }

    public void setRabat(BigDecimal rabat) {
        this.rabat = rabat;
    }


    @Column(name = "AKCIJA", length = 20)
    public String getAkcija() {
        return this.akcija;
    }

    public void setAkcija(String akcija) {
        this.akcija = akcija;
    }

    @Column(name = "EKSTRA_RABAT", precision = 22, scale = 0)
    public BigDecimal getEkstraRabat() {
        return ekstraRabat;
    }

    public void setEkstraRabat(BigDecimal ekstraRabat) {
        this.ekstraRabat = ekstraRabat;
    }


    @Column(name = "KOLPOPAKOVANJU", precision = 22, scale = 0)
    public BigDecimal getKolPoPakovanju() {
        return kolPoPakovanju;
    }

    public void setKolPoPakovanju(BigDecimal kolPoPakovanju) {
        this.kolPoPakovanju = kolPoPakovanju;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", insertable = true, updatable = true, nullable = false)
    public WoUser getWoUser() {
        return woUser;
    }

    public void setWoUser(WoUser woUser) {
        this.woUser = woUser;
    }


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPOSITEARTICLE", referencedColumnName = "PROIZVOD#", insertable = true, updatable = true, nullable = false)
    public OcpProizvod getCompositeArticle() {
        return compositeArticle;
    }

    public void setCompositeArticle(OcpProizvod compositeArticle) {
        this.compositeArticle = compositeArticle;
    }

    @Column(name = "COMPOSITEARTICLEQUANTITY")
    public BigDecimal getCompositeArticleQuantity() {
        return compositeArticleQuantity;
    }

    public void setCompositeArticleQuantity(BigDecimal compositeArticleQuantity) {
        this.compositeArticleQuantity = compositeArticleQuantity;
    }

    @Column(name = "COMPOSITEARTICLEPACKQUANTITY")
    public BigDecimal getCompositeArticlePackQuantity() {
        return compositeArticlePackQuantity;
    }

    public void setCompositeArticlePackQuantity(BigDecimal compositeArticlePackQuantity) {
        this.compositeArticlePackQuantity = compositeArticlePackQuantity;
    }

    @Column(name = "COMPOSITEARTICLEUNITOFMEASURE")
    public int getCompositeArticleUnitOfMeasure() {
        return compositeArticleUnitOfMeasure;
    }

    @Column(name = "COMPOSITEARTICLEPRICE")
    public BigDecimal getCompositeArticlePrice() {
        return compositeArticlePrice;
    }

    public void setCompositeArticlePrice(BigDecimal compositeArticlePrice) {
        this.compositeArticlePrice = compositeArticlePrice;
    }

    public void setCompositeArticleUnitOfMeasure(int compositeArticleUnitOfMeasure) {
        this.compositeArticleUnitOfMeasure = compositeArticleUnitOfMeasure;
    }
/*
    @OneToMany(targetEntity = WoRezervacijaSastava.class, mappedBy = "woRezervacija")
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<WoRezervacijaSastava> getWoRezervacijaSastavaList() {
        return woRezervacijaSastavaList;
    }

    public void setWoRezervacijaSastavaList(List<WoRezervacijaSastava> woRezervacijaSastavaList) {
        this.woRezervacijaSastavaList = woRezervacijaSastavaList;
    }
*/
    @Transient
    public BigDecimal getVrednost() {
        return vrednost;
    }

    public void setVrednost(BigDecimal vrednost) {
        this.vrednost = vrednost;
    }

    @Transient
    public BigDecimal getVrednostSaPorezom() {
        return vrednostSaPorezom;
    }

    public void setVrednostSaPorezom(BigDecimal vrednostSaPorezom) {
        this.vrednostSaPorezom = vrednostSaPorezom;
    }

}


