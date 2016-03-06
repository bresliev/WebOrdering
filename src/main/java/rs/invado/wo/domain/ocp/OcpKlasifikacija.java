package rs.invado.wo.domain.ocp;

// Generated Dec 9, 2012 6:19:17 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * OcpKlasifikacija generated by hbm2java
 */

@SqlResultSetMapping(name = "hijerarhija", entities = {@EntityResult(entityClass = rs.invado.wo.domain.ocp.OcpKlasifikacija.class,
        fields = {
                @FieldResult(name = "id.vrstaKlasifikacije", column = "VRSTA_KLASIFIKACIJE#"),
                @FieldResult(name = "id.klasifikacija", column = "KLASIFIKACIJA#"),
                @FieldResult(name = "naziv", column = "NAZIV"),
                @FieldResult(name = "klasifikacijaNad", column = "KLASIFIKACIJA#_NAD"),
                @FieldResult(name = "jedinicaMere", column = "JEDINICA_MERE#"),
                @FieldResult(name = "opis", column = "OPIS"),
                @FieldResult(name = "maxRabat", column = "MAX_RABAT"),
                @FieldResult(name = "prikazisifruwo", column = "PRIKAZISIFRUWO"),
                @FieldResult(name = "sort", column = "SORT"),
                @FieldResult(name = "sort1", column = "SORT1")})}, columns = {@ColumnResult(name = "nivo")})
@NamedNativeQueries({
        @NamedNativeQuery(name = "findSingleRootKlasifikacijaHierarchy", query = "  select distinct k.vrsta_klasifikacije#, k.klasifikacija#, "
                + " k.naziv, k.klasifikacija#_nad, k.jedinica_mere#, k.opis, k.max_rabat, k.prikazisifruwo, k.sort, k.sort1, level nivo "
                + " from Ocp_Klasifikacija k "
                + " where k.vrsta_Klasifikacije# = :vrstaKlasifikacije "
                + "    and sort is not null "
                + "  and ((level = :nivo and :nivo != 0)"
                + "  or (level=level and :nivo = 0))"
                + " start with ((k.klasifikacija# = :root and :root is not null and k.vrsta_Klasifikacije# = :vrstaKlasifikacije)"
                + " or (k.klasifikacija#_nad is null and :root is null and k.vrsta_Klasifikacije# = :vrstaKlasifikacije)) "
                + " connect by prior k.klasifikacija# =  klasifikacija#_Nad "
                + " order by k.sort, k.klasifikacija#, level  ", resultSetMapping = "hijerarhija"),
        @NamedNativeQuery(name = "findSingleRootKlasifikacijaHierarchyNameSorted", query = "  select distinct k.vrsta_klasifikacije#, k.klasifikacija#, "
                + " k.naziv, k.klasifikacija#_nad, k.jedinica_mere#, k.opis, k.max_rabat, k.prikazisifruwo, k.sort, k.sort1, level nivo "
                + " from Ocp_Klasifikacija k "
                + " where k.vrsta_Klasifikacije# = :vrstaKlasifikacije "
                + "    and sort is not null "
                + "  and ((level = :nivo and :nivo != 0)"
                + "  or (level=level and :nivo = 0))"
                + " start with ((k.klasifikacija# = :root and :root is not null and k.vrsta_Klasifikacije# = :vrstaKlasifikacije)"
                + " or (k.klasifikacija#_nad is null and :root is null and k.vrsta_Klasifikacije# = :vrstaKlasifikacije)) "
                + " connect by prior k.klasifikacija# =  klasifikacija#_Nad "
                + " order by k.sort, k.naziv, level  ", resultSetMapping = "hijerarhija"),
        @NamedNativeQuery(name = "findAllRootsKlasifikacijaHierarchy", query = "select distinct k.vrsta_klasifikacije#, k.klasifikacija#, "
                + " k.naziv, k.klasifikacija#_nad, k.jedinica_mere#, k.opis, k.max_rabat, k.prikazisifruwo, k.sort, k.sort1, level nivo "
                + " from Ocp_Klasifikacija k "
                + "    and sort is not null "
                + " where k.vrsta_Klasifikacije# = :vrstaKlasifikacije and :root is null "
                + " start with k.klasifikacija#_nad is null"
                + " connect by prior k.klasifikacija# =  klasifikacija#_Nad "
                + " order by k.sort, k.klasifikacija#, level  ", resultSetMapping = "hijerarhija"),
        @NamedNativeQuery(name = "findRootProdajnaKlasifikacijaForProizvod", query = " select k.* "
                + "           from (select distinct k.vrsta_klasifikacije#, k.klasifikacija#,k.naziv, k.klasifikacija#_nad, "
                + "                                   k.jedinica_mere#, k.opis, "
                + "                 nvl(k.max_rabat, 0) max_rabat, k.prikazisifruwo, k.sort, k.sort1"
                + "                                                 from Ocp_Klasifikacija k  "
                + "                                                  where k.VRSTA_KLASIFIKACIJE# = :vrklas "
                + "                                                 and exists  (select 1 from ocp_klasifikacija_proizvoda p "
                + "                                                              where p.proizvod# = :proizvod"
                + "                                                              and p.vrsta_klasifikacije# = k.VRSTA_KLASIFIKACIJE#"
                + "                                                              and p.KLASIFIKACIJA# like k.KLASIFIKACIJA#||'%') "
                + "                                                order by 7 desc , 2) k"
                + " where rownum = 1", resultClass = OcpKlasifikacija.class),
        @NamedNativeQuery(name = "findKlasifikacijaWithAction", query = " select distinct k.vrsta_klasifikacije#, k.klasifikacija#,k.naziv, "
                + "            k.klasifikacija#_nad, k.jedinica_mere#, k.opis, nvl(k.max_rabat, 0) max_rabat, k.prikazisifruwo, k.sort, k.sort1"
                + "           from Ocp_Klasifikacija k  "
                + "           where k.VRSTA_KLASIFIKACIJE# = :vrklas "
                + "              and sort is not null "
                + "           and exists  (select 1 from ocp_klasifikacija_proizvoda p, wo_artikli_na_akciji w "
                + "                        where w.tip_akcije = :tipAkcije "
                + "                        and id_kompanije_korisnik = :kompanijaKorisnik "
                + "                        and p.vrsta_klasifikacije# = k.VRSTA_KLASIFIKACIJE#"
                + "                        and p.KLASIFIKACIJA# like k.KLASIFIKACIJA#||'%'"
                + "                        and p.proizvod# = w.proizvod# "
                + "                          and w.datum_do > sysdate"
                + "                          and exists (select 1 "
                + "                                   from wo_partner_settings w, uz_stanje_zaliha_skladista u"
                + "                                   where w.poslovni_partner# = :partner"
                + "                                   and w.id_kompanija_korisnik = :kompanijaKorisnik "
                + "                                   and w.id_skladista = u.id_skladista"
                + "                                   and u.proizvod# = p.proizvod# "
                + "                                   and u.kolicina_po_stanju_z - u.rezervisana_kol >0)) "
                + "              and k.klasifikacija#_nad is NULL"
                + " order by k.sort", resultClass = OcpKlasifikacija.class)})

@Entity
@Table(name = "OCP_KLASIFIKACIJA", schema = "DAREX")
public class OcpKlasifikacija implements Serializable {

    private OcpKlasifikacijaId id;
    private String naziv;
    private String klasifikacijaNad;
    private Short jedinicaMere;
    private String opis;
    private BigDecimal maxRabat;
    private Boolean prikazisifruwo;
    private String sort;
    private Integer sort1;
    private Integer sortByClass;
    private transient Set ocpVrstaDokumentas = new HashSet(0);
    private transient Set ocpAtributZaKlasifikacijus = new HashSet(0);
    private transient Set ocpKlasifikacijaPps = new HashSet(0);
    private transient Set ocpAtributs = new HashSet(0);
    private transient Set ocpKlasifikacijaProizvoda = new HashSet(0);
    private BigDecimal nivo;

    public OcpKlasifikacija() {
    }

    public OcpKlasifikacija(OcpKlasifikacijaId id, String naziv) {
        this.id = id;
        this.naziv = naziv;
    }

    public OcpKlasifikacija(OcpKlasifikacijaId id, String naziv,
                            String klasifikacijaNad, Short jedinicaMere, String opis,
                            BigDecimal maxRabat, Boolean prikazisifruwo, String sort,
                            Set ocpVrstaDokumentas, Set ocpAtributZaKlasifikacijus,
                            Set ocpKlasifikacijaPps, Set ocpAtributs) {
        this.id = id;
        this.naziv = naziv;
        this.klasifikacijaNad = klasifikacijaNad;
        this.jedinicaMere = jedinicaMere;
        this.opis = opis;
        this.maxRabat = maxRabat;
        this.prikazisifruwo = prikazisifruwo;
        this.sort = sort;
        this.ocpVrstaDokumentas = ocpVrstaDokumentas;
        this.ocpAtributZaKlasifikacijus = ocpAtributZaKlasifikacijus;
        this.ocpKlasifikacijaPps = ocpKlasifikacijaPps;
        this.ocpAtributs = ocpAtributs;
    }

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "vrstaKlasifikacije", column = @Column(name = "VRSTA_KLASIFIKACIJE#", nullable = false, precision = 6, scale = 0)),
            @AttributeOverride(name = "klasifikacija", column = @Column(name = "KLASIFIKACIJA#", nullable = false, length = 15))})
    public OcpKlasifikacijaId getId() {
        return this.id;
    }

    public void setId(OcpKlasifikacijaId id) {
        this.id = id;
    }

    @Column(name = "NAZIV", nullable = false, length = 60)
    public String getNaziv() {
        return this.naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Column(name = "KLASIFIKACIJA#_NAD", length = 15)
    public String getKlasifikacijaNad() {
        return this.klasifikacijaNad;
    }

    public void setKlasifikacijaNad(String klasifikacijaNad) {
        this.klasifikacijaNad = klasifikacijaNad;
    }

    @Column(name = "JEDINICA_MERE#", precision = 3, scale = 0)
    public Short getJedinicaMere() {
        return this.jedinicaMere;
    }

    public void setJedinicaMere(Short jedinicaMere) {
        this.jedinicaMere = jedinicaMere;
    }

    @Column(name = "OPIS", length = 2000)
    public String getOpis() {
        return this.opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    @Column(name = "MAX_RABAT", precision = 17, scale = 3)
    public BigDecimal getMaxRabat() {
        return this.maxRabat;
    }

    public void setMaxRabat(BigDecimal maxRabat) {
        this.maxRabat = maxRabat;
    }

    @Column(name = "PRIKAZISIFRUWO", precision = 1, scale = 0)
    public Boolean getPrikazisifruwo() {
        return this.prikazisifruwo;
    }

    public void setPrikazisifruwo(Boolean prikazisifruwo) {
        this.prikazisifruwo = prikazisifruwo;
    }

    @Column(name = "SORT")
    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @ManyToMany(targetEntity = rs.invado.wo.domain.ocp.OcpVrstaDokumenta.class, fetch = FetchType.LAZY)
    @JoinTable(name = "OCP_KLASIFIKACIJA_NA_VD", schema = "DAREX", joinColumns = {
            @JoinColumn(name = "VRSTA_KLASIFIKACIJE#", nullable = false, updatable = false),
            @JoinColumn(name = "KLASIFIKACIJA#", nullable = false, updatable = false)}, inverseJoinColumns = {@JoinColumn(name = "ID_VD", nullable = false, updatable = false)})
    public Set getOcpVrstaDokumentas() {
        return this.ocpVrstaDokumentas;
    }

    public void setOcpVrstaDokumentas(Set ocpVrstaDokumentas) {
        this.ocpVrstaDokumentas = ocpVrstaDokumentas;
    }

    @OneToMany(targetEntity = rs.invado.wo.domain.ocp.OcpAtributZaKlasifikaciju.class, fetch = FetchType.LAZY, mappedBy = "ocpKlasifikacija")
    public Set getOcpAtributZaKlasifikacijus() {
        return this.ocpAtributZaKlasifikacijus;
    }

    public void setOcpAtributZaKlasifikacijus(Set ocpAtributZaKlasifikacijus) {
        this.ocpAtributZaKlasifikacijus = ocpAtributZaKlasifikacijus;
    }

    @OneToMany(targetEntity = OcpKlasifikacijaPp.class, fetch = FetchType.LAZY, mappedBy = "ocpKlasifikacija")
    public Set getOcpKlasifikacijaPps() {
        return this.ocpKlasifikacijaPps;
    }

    public void setOcpKlasifikacijaPps(Set ocpKlasifikacijaPps) {
        this.ocpKlasifikacijaPps = ocpKlasifikacijaPps;
    }

    @OneToMany(targetEntity = OcpAtribut.class, fetch = FetchType.LAZY, mappedBy = "ocpKlasifikacija")
    public Set getOcpAtributs() {
        return this.ocpAtributs;
    }

    public void setOcpAtributs(Set ocpAtributs) {
        this.ocpAtributs = ocpAtributs;
    }

    @Transient
    public BigDecimal getNivo() {
        return nivo;
    }

    public void setNivo(BigDecimal nivo) {
        this.nivo = nivo;
    }

    @OneToMany(targetEntity = OcpKlasifikacijaProizvoda.class, fetch = FetchType.LAZY, mappedBy = "ocpKlasifikacija")
    public Set getOcpKlasifikacijProizvoda() {
        return ocpKlasifikacijaProizvoda;
    }

    public void setOcpKlasifikacijProizvoda(Set ocpKlasifikacijProizvoda) {
        this.ocpKlasifikacijaProizvoda = ocpKlasifikacijProizvoda;
    }

    @Column(name = "SORT1")
    public Integer getSort1() {
        return sort1;
    }

    public void setSort1(Integer sort1) {
        this.sort1 = sort1;
    }

    @Transient
    public Integer getSortByClass() {
        return sortByClass;
    }

    public void setSortByClass(Integer sortByClass) {
        this.sortByClass = sortByClass;
    }
}
