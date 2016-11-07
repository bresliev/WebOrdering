package rs.invado.wo.dao.ocp;

// Generated Dec 9, 2012 5:42:26 PM by Hibernate Tools 3.4.0.CR1


import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.dao.uz.UzStanjeZalihaSkladistaHome;
import rs.invado.wo.dao.wo.WoSortPerObjectAttributeHome;
import rs.invado.wo.domain.ocp.OcpKlasifikacija;
import rs.invado.wo.domain.ocp.OcpProizvod;
import rs.invado.wo.domain.ocp.OcpSastavProizvoda;
import rs.invado.wo.domain.ocp.OcpVrAtrProizvod;
import rs.invado.wo.domain.prod.ProdPoreskaStopa;
import rs.invado.wo.domain.uz.UzStanjeZalihaSkladista;
import rs.invado.wo.domain.wo.WoParametri;
import rs.invado.wo.domain.wo.WoPartnerSetting;
import rs.invado.wo.domain.wo.WoSortPerObjectAttribute;
import rs.invado.wo.dto.CompanySetting;
import rs.invado.wo.dto.Proizvodi;
import rs.invado.wo.util.WoConfigSingleton;

import javax.inject.Inject;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

/**
 * Home object for domain model class OcpProizvod.
 *
 * @author Hibernate Tools
 * @see rs.invado.wo.dao.ocp.OcpProizvodHome
 */


@Repository
@Transactional
public class OcpProizvodHome {

    @Inject
    WoConfigSingleton woConfigSingleton;

    private static final Log log = LogFactory.getLog(OcpProizvodHome.class);


    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    private OcpKlasifikacijaHome ocpKlasifikacijaDAO;
    @Inject
    private OcpJedinicaMereHome ocpJedinicaMereDAO;
    @Inject
    private WoSortPerObjectAttributeHome woSortPerObjectAttributeDAO;
    @Inject
    private OcpKlasifikacijaProizvodaHome ocpKlasifikacijaProizvodaDAO;
    @Inject
    private UzStanjeZalihaSkladistaHome uzStanjeZalihaSkladistaDAO;
    @Inject
    private OcpSastavProizvodaHome ocpSastavProizvodaDAO;

    public static String getNamedQueryString(EntityManager em, String queryName) throws SQLException {
        Query tmpQuery = em.createNamedQuery(queryName);
        SQLQuery sqlQuery = tmpQuery.unwrap(SQLQuery.class);
        String queryString = sqlQuery.getQueryString();
        return queryString;
    }

    public Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    public void persist(OcpProizvod transientInstance) {
        log.debug("persisting OcpProizvod instance");
        try {
            entityManager.persist(transientInstance);
            entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(OcpProizvod persistentInstance) {
        log.debug("removing OcpProizvod instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public OcpProizvod merge(OcpProizvod detachedInstance) {
        log.debug("merging OcpProizvod instance");
        try {
            OcpProizvod result = entityManager.merge(detachedInstance);
            entityManager.flush();
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public OcpProizvod findById(int id) {
        log.debug("getting OcpProizvod instance with id: " + id);
        try {
            OcpProizvod instance = entityManager.find(OcpProizvod.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }


    public OcpProizvod getProizvodFromNativeQueryObject(Object[] object) {

        OcpProizvod proizvod = new OcpProizvod();
        proizvod.setProizvod(object[0] != null ? Integer.valueOf(object[0].toString()) : null);
        proizvod.setNazivProizvoda(object[1] != null ? object[1].toString() : null);
        proizvod.setRadnik(object[5] != null ? Integer.valueOf(object[5].toString()) : null);
        proizvod.setJedinicaMere(object[6] != null ? ocpJedinicaMereDAO.findById(Integer.valueOf(object[6].toString())) : null);
        proizvod.setDodatniNaziv(object[7] != null ? object[7].toString() : null);
        proizvod.setJedinicaMereAltRef(object[33] != null ? ocpJedinicaMereDAO.findById(Integer.valueOf(object[33].toString())) : null);
        proizvod.setCena(object[34] != null ? new BigDecimal(object[34].toString()) : null);
        proizvod.setTipAkcije(object[37] != null ? object[37].toString() : null);
        proizvod.setDezenIstruktira(object[35] != null ? object[35].toString() : null);
        proizvod.setProizvodjac(object[36] != null ? object[36].toString() : null);
        proizvod.setKolicinaPoPakovanju(object[38] != null ? new BigDecimal(object[38].toString()) : null);
        proizvod.setRaspolozivo(object[39] != null ? new BigDecimal(object[39].toString()) : null);
        proizvod.setKolUAltJM(object[42] != null ? Integer.valueOf(object[42].toString()) : null);
        proizvod.setStopaPoreza(object[43] != null ? new BigDecimal(object[43].toString()) : null);
        proizvod.setSortKlasa(object[44] != null ? object[44].toString() : null);
        proizvod.setJedinicaMereRezervacije(object[45] != null ? object[45].toString() : null);
        proizvod.setPunNazivProizvoda(proizvod.getNazivProizvoda() + " " + proizvod.getDodatniNaziv());
        proizvod.setOcpKlasifikacijaProizvoda(ocpKlasifikacijaProizvodaDAO.findByProizvod(proizvod));
        proizvod.setVrednostSortAtributa(object[46] != null ? object[46].toString() : null);
        proizvod.setUzStanjeZalihaSkladistas(uzStanjeZalihaSkladistaDAO.findByProizvod(proizvod.getProizvod()));
        proizvod.setSastavProizvoda(ocpSastavProizvodaDAO.findByProizvod(proizvod));

        return proizvod;
    }

    private int getRowCount(Criteria cr) {
        Criteria crRowCount = cr.setProjection(Projections.rowCount());
        return Integer.valueOf(cr.uniqueResult().toString());
    }


    private DetachedCriteria getProPoSklSubquery(List<WoPartnerSetting> woPartnerSettings) {


        Integer[] skladista = new Integer[woPartnerSettings.size()];
        for (int i = 0; i < woPartnerSettings.size(); i++) {
            skladista[i] = Integer.valueOf(woPartnerSettings.get(i).getIdSkladista());
        }
        DetachedCriteria subquery = DetachedCriteria.forClass(UzStanjeZalihaSkladista.class, "skl")
                .setProjection(Projections.property("skl.id.proizvod"))
                .add(Restrictions.in("skl.id.idSkladista", skladista))
                .add(Restrictions.gt("skl.raspolozivoStanje", BigDecimal.valueOf(0.0d)));
        return subquery;
    }

    public Proizvodi findProizvodiNaAkciji(String tipAkcije, int pageNo, int pageSize, WoParametri woParametri,
                                           List<WoPartnerSetting> woPartnerSettings) {
        Session session = getSession();
        Criteria cr = session.createCriteria(OcpProizvod.class, "pro")
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .setFirstResult(pageNo * pageSize)
                .setMaxResults(pageSize)
                .add(Subqueries.propertyIn("pro.proizvod", getProPoSklSubquery(woPartnerSettings)))
                .createCriteria("pro.ocpKlasifikacijaProizvoda", "klPro")
                .add(Restrictions.eq("klPro.id.vrstaKlasifikacije", woParametri.getVrstaKlasifikacijeFilter()))
                .addOrder(Order.asc("klPro.id.klasifikacija"))
                .createCriteria("pro.woArtikliNaAkcijis", "wana")
                .add(Restrictions.eq("wana.idKompanijeKorisnik", woParametri.getWoKompanijaKorisnik().getId()))
                .add(Restrictions.like("wana.tipAkcije", tipAkcije, MatchMode.ANYWHERE))
                .add(Restrictions.or(Restrictions.ge("wana.datumDo", DateUtils.truncate(new Date(), Calendar.DATE)),
                        Restrictions.isNull("wana.datumDo")))
                .addOrder(Order.asc("pro.proizvod"));
        List<OcpProizvod> lp = cr.setFirstResult(pageNo * pageSize).setMaxResults(pageSize).list();
        int rowCount = 0;
        if (pageNo == 0)
            rowCount = getRowCount(cr);
        return new Proizvodi(lp, 0, rowCount);
    }

    private DetachedCriteria getNonSKUArticlesSubquery() {
/**pronalazi artikle za koje se ne proverava stanje na zalihama*/

        DetachedCriteria subquery = DetachedCriteria.forClass(OcpVrAtrProizvod.class, "atributeValues")
                .setProjection(Projections.property("atributeValues.id.proizvod"))
                .add(Restrictions.eq("atributeValues.id.atribut", woConfigSingleton.getAttributes()[4]))
                .add(Restrictions.eq("atributeValues.vrednost", "NE"));
        return subquery;
    }

    public Proizvodi findProizvodiByName(String namePattern, int pageNo, int pageSize, WoParametri woParametri,
                                         List<WoPartnerSetting> woPartnerSettings) {
        Criteria cr = getSession().createCriteria(OcpProizvod.class, "pro")
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .setFirstResult(pageNo * pageSize)
                .setMaxResults(pageSize)
                .add(Restrictions.or(Subqueries.propertyIn("pro.proizvod", getProPoSklSubquery(woPartnerSettings)),
                        Subqueries.propertyIn("pro.proizvod", getNonSKUArticlesSubquery())))
                .add(Restrictions.or(Restrictions.like("nazivProizvoda", namePattern, MatchMode.ANYWHERE).ignoreCase(),
                        Restrictions.like("dodatniNaziv", namePattern, MatchMode.ANYWHERE).ignoreCase()))
                .addOrder(Order.asc("nazivProizvoda"))
                .addOrder(Order.asc("dodatniNaziv"))
                .addOrder(Order.asc("proizvod"));
        List<OcpProizvod> lp = cr.setFirstResult(pageNo * pageSize)
                .setMaxResults(pageSize).list();
        int rowCount = 0;
        if (pageNo == 0)
            rowCount = getRowCount(cr);
        return new Proizvodi(lp, 0, rowCount);
    }


    public Proizvodi findFilterProizvodi(String brand, String patternNaziv, int pageNo, int pageSize, WoParametri woParametri,
                                         List<WoPartnerSetting> woPartnerSettings) {
        Session session = getSession();


        Criteria cr = session.createCriteria(OcpProizvod.class, "pro")
                .add(Restrictions.like("punNazivProizvoda", patternNaziv, MatchMode.ANYWHERE).ignoreCase())
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .setFirstResult(pageNo * pageSize)
                .setMaxResults(pageSize)
                .add(Subqueries.propertyIn("pro.proizvod", getProPoSklSubquery(woPartnerSettings)))
                .createCriteria("pro.ocpKlasifikacijaProizvoda", "okp")
                .add(Restrictions.like("okp.id.klasifikacija", brand + "%"))
                .add(Restrictions.eq("okp.id.vrstaKlasifikacije", woParametri.getVrstaKlasifikacijeFilter()))
                .addOrder(Order.asc("pro.proizvod"));

        List<OcpProizvod> lp = cr.setFirstResult(pageNo * pageSize)
                .setMaxResults(pageSize).list();
        int rowCount = 0;
        if (pageNo == 0)
            rowCount = getRowCount(cr);
        return new Proizvodi(lp, 0, rowCount);
    }

    public BigDecimal findStopaPorezaZaProizvod(int OJ, int proizvod) {

        String namedQ = "stopaPoreza";
        try {
            ProdPoreskaStopa o = (ProdPoreskaStopa) entityManager.createNamedQuery(namedQ)
                    .setParameter("oj", OJ)
                    .setParameter("proizvod", proizvod)
                    .getSingleResult();
            return o.getStopaPoreza();
        } catch (NoResultException e) {
            e.getStackTrace();
        } catch (NonUniqueResultException e) {
            e.getStackTrace();
        }
        return null;
    }


    public List<OcpProizvod> findProizvodiByNamePatternAndByBrand(List<String> patterns, String brand, WoParametri woParametri,
                                                                  List<WoPartnerSetting> woPartnerSettings) {


        StringBuffer q = new StringBuffer("  select *  from Ocp_Proizvod p  where ( ");
        String predicate = "";
        for (String pattern : patterns) {
            predicate = predicate + "lower(naziv_proizvoda||dodatni_naziv) like lower('%" + pattern + "%') and ";
        }
        q.append(predicate.substring(0, predicate.length() - 4));
        q.append(") and exists (select 1                    " +
                "           from wo_partner_settings w, uz_stanje_zaliha_skladista u                   " +
                "           where w.poslovni_partner# = :partner                   " +
                "           and w.id_kompanija_korisnik = :kompanija                   " +
                "           and w.id_skladista = u.id_skladista                   " +
                "           and u.proizvod# = p.proizvod#                    " +
                "           and u.kolicina_po_stanju_z - u.rezervisana_kol >0)   " +
                "   and (exists (select 1 from ocp_klasifikacija_proizvoda km                  " +
                "               where km.vrsta_klasifikacije# = :vrstaKlasifikacijeFilter                  " +
                "               and klasifikacija# like  :brand||'%%'                  " +
                "               and km.proizvod# = p.proizvod#)           or :brand is null ) " +
                "order by naziv_proizvoda||dodatni_naziv");
        String query = q.toString();


        List<OcpProizvod> lp = (List<OcpProizvod>) entityManager.createNativeQuery(query, OcpProizvod.class)
                .setParameter("partner", woPartnerSettings.get(0).getPoslovniPartner().getPoslovniPartner())
                .setParameter("kompanija", woParametri.getWoKompanijaKorisnik().getId())
                .setParameter("vrstaKlasifikacijeFilter", woParametri.getVrstaKlasifikacijeFilter())
                .setParameter("brand", brand).getResultList();
        for (OcpProizvod proizvod : lp)
            proizvod.setPunNazivProizvoda(proizvod.getNazivProizvoda() + " " + proizvod.getDodatniNaziv());
        return lp;
    }


    public List<OcpProizvod> findProizvodiByNameAndByBrand(String namePattern, String brand, WoParametri woParametri,
                                                           List<WoPartnerSetting> woPartnerSettings) {


        String namedQ = "findAllByBrandOrNameAutoComplete";
        Query q = entityManager.createNamedQuery(namedQ)
                .setParameter("namePattern", namePattern)
                .setParameter("partner", woPartnerSettings.get(0).getPoslovniPartner().getPoslovniPartner())
                .setParameter("kompanija", woParametri.getWoKompanijaKorisnik().getId())
                .setParameter("vrstaKlasifikacijeFilter", woParametri.getVrstaKlasifikacijeFilter())
                .setParameter("brand", brand);
        List<OcpProizvod> lp = new ArrayList<OcpProizvod>();
        Iterator i = q.getResultList().iterator();
        setTansientForSorted(i, lp);
        return lp;
    }


    //Metoda sa upitima za sortiranje
    private void setTansientForSorted(Iterator i, List<OcpProizvod> lp) {
        while (i.hasNext()) {
            Object[] parovi = (Object[]) i.next();
            OcpProizvod proizvod = (OcpProizvod) parovi[0];
            proizvod.setCena((BigDecimal) parovi[1]);
            proizvod.setDezenIstruktira((String) parovi[2]);
            proizvod.setProizvodjac((String) parovi[3]);
            proizvod.setTipAkcije((String) parovi[4]);
            proizvod.setKolicinaPoPakovanju((BigDecimal) parovi[5]);
            proizvod.setRaspolozivo((BigDecimal) parovi[6]);
            proizvod.setPunNazivProizvoda(proizvod.getNazivProizvoda() + proizvod.getDodatniNaziv());
            proizvod.setKolUAltJM((Integer) parovi[9]);
            proizvod.setStopaPoreza((BigDecimal) parovi[10]);
            proizvod.setSortKlasa((String) parovi[11]);


            lp.add(proizvod);
        }

    }


    public Proizvodi findProizvodiZaBrendSorted(String brand, int pageNo, int pageSize, WoParametri woParametri,
                                                List<WoPartnerSetting> woPartnerSettings, CompanySetting cs) throws SQLException {

        //potrebno je da pro ima i osnovnu jm i alernativnu. ukoliko nije svojstveno ondaje osnovnoa = alternativnoj, postaviti triger i uraditi upradte tabele.
        List<OcpProizvod> lp = new ArrayList<OcpProizvod>();
        int vrstaKlasifikacijeSort = 0;
        int rowCount = 0;
        String namedQ;
        Query q;
        List<WoSortPerObjectAttribute> woSortPerObjectAttributes = woSortPerObjectAttributeDAO.findByWoClassId(woParametri.getVrstaKlasifikacijeMeni(), brand);
        String cut = "";
        for (WoSortPerObjectAttribute woSortPerObjectAttribute : woSortPerObjectAttributes) {
            cut = cut + " " + (woSortPerObjectAttribute.getAttributeOwner().equals("CLASS") ? "47" : woSortPerObjectAttribute.getAttributeId()) + " " + woSortPerObjectAttribute.getOrderType() + ",";
        }
        cut = cut + "1";
        namedQ = "findAllByBrandSorted";
        StringBuilder query = new StringBuilder(getNamedQueryString(entityManager, namedQ));
        query.replace(query.indexOf("cut"), query.indexOf("cut") + 3, cut);
        q = entityManager.createNativeQuery(query.toString())
                .setParameter("partner", woPartnerSettings.get(0).getPoslovniPartner().getPoslovniPartner())
                .setParameter("kompanija", woParametri.getWoKompanijaKorisnik().getId())
                .setParameter("vrstaKlasifikacijeMeni", woParametri.getVrstaKlasifikacijeMeni())
                .setParameter("proveraZaliha", woConfigSingleton.getAttributes()[4])
                .setParameter("brand", brand)
                .setParameter("klasaCene", woParametri.getKlasaCene())
                .setParameter("ojc", woPartnerSettings.get(0).getOrganizacionaJedinica())
                .setParameter("klc", woPartnerSettings.get(0).getIdKlasaCene())
                .setParameter("cenovnik", woPartnerSettings.get(0).getIdCenovnik());

        List<Object[]> cdList = q.getResultList();
        if (pageNo == 0) rowCount = cdList.size();
        //for (Object[] o : cdList)
        for (int i = pageNo * pageSize; i < pageNo * pageSize + pageSize && i < cdList.size(); i++) {
            lp.add(getProizvodFromNativeQueryObject(cdList.get(i)));
        }

        return new Proizvodi(lp, vrstaKlasifikacijeSort, rowCount);
    }

    public Proizvodi findProizvodiNaAkcijiSorted(String brandID, String tipAkcije, int pageNo, int pageSize, WoParametri woParametri,
                                                 List<WoPartnerSetting> woPartnerSettings) throws SQLException {
        //potrebno je da pro ima i osnovnu jm i alernativnu. ukoliko nije svojstveno ondaje osnovnoa = alternativnoj, postaviti triger i uraditi upradte tabele.
        List<OcpProizvod> lp = new ArrayList<OcpProizvod>();
        int vrstaKlasifikacijeSort = 0;
        int rowCount = 0;
        String namedQ;
        Query q;
        List<WoSortPerObjectAttribute> woSortPerObjectAttributes = woSortPerObjectAttributeDAO.findByWoClassId(woParametri.getVrstaKlasifikacijeMeni(), brandID);
        String cut = "";
        for (WoSortPerObjectAttribute woSortPerObjectAttribute : woSortPerObjectAttributes)
            cut = cut + " " + (woSortPerObjectAttribute.getAttributeOwner().equals("CLASS") ? "47" : woSortPerObjectAttribute.getAttributeId()) + " " + woSortPerObjectAttribute.getOrderType() + ",";
        cut = cut + "1";
        namedQ = "findByActionTypePriceSorted";
        StringBuilder query = new StringBuilder(getNamedQueryString(entityManager, namedQ));
        query.replace(query.indexOf("cut"), query.indexOf("cut") + 3, cut);
        q = entityManager.createNativeQuery(query.toString())
                .setParameter("partner", woPartnerSettings.get(0).getPoslovniPartner().getPoslovniPartner())
                .setParameter("kompanija", woParametri.getWoKompanijaKorisnik().getId())
                .setParameter("vrstaKlasifikacijeMeni", woParametri.getVrstaKlasifikacijeMeni())
                .setParameter("proveraZaliha", woConfigSingleton.getAttributes()[4])
                .setParameter("brand", brandID)
                .setParameter("klasaCene", woParametri.getKlasaCene())
                .setParameter("ojc", woPartnerSettings.get(0).getOrganizacionaJedinica())
                .setParameter("klc", woPartnerSettings.get(0).getIdKlasaCene())
                .setParameter("cenovnik", woPartnerSettings.get(0).getIdCenovnik())
                .setParameter("tipakcije", tipAkcije);

        List<Object[]> cdList = q.getResultList();
        if (pageNo == 0) rowCount = cdList.size();
        //for (Object[] o : cdList)
        for (int i = pageNo * pageSize; i < pageNo * pageSize + pageSize && i < cdList.size(); i++) {
            lp.add(getProizvodFromNativeQueryObject(cdList.get(i)));
        }

        return new Proizvodi(lp, vrstaKlasifikacijeSort, rowCount);
    }


    public List<OcpProizvod> findProizvodiByNameSorted(String namePattern, WoParametri woParametri,
                                                       List<WoPartnerSetting> woPartnerSettings) {
        //potrebno je da pro ima i osnovnu jm i alernativnu. ukoliko nije svojstveno ondaje osnovnoa = alternativnoj, postaviti triger i uraditi upradte tabele.
        List<OcpProizvod> lp = new ArrayList<OcpProizvod>();
        int vrstaKlasifikacijeSort = 0;
        int rowCount = 0;
        Query q;
        String namedQ = "findByNamePriceSorted";
        q = entityManager.createNamedQuery(namedQ)
                .setParameter("klasaCene", woParametri.getKlasaCene())
                .setParameter("namePattern", namePattern)
                .setParameter("partner", woPartnerSettings.get(0).getPoslovniPartner().getPoslovniPartner())
                .setParameter("kompanija", woParametri.getWoKompanijaKorisnik().getId())
                .setParameter("ojc", woPartnerSettings.get(0).getOrganizacionaJedinica())
                .setParameter("klc", woPartnerSettings.get(0).getIdKlasaCene())
                .setParameter("cenovnik", woPartnerSettings.get(0).getIdCenovnik())
                .setParameter("proveraZaliha", woConfigSingleton.getAttributes()[4]);

        Iterator i = q.getResultList().iterator();

        setTansientForSorted(i, lp);

        return lp;
        //return q.getResultList();

    }

    public Proizvodi findFilterProizvodiSorted(String brand, String patternNaziv, int pageNo, int pageSize, WoParametri woParametri,
                                               List<WoPartnerSetting> woPartnerSettings) {
        Session session = getSession();
        int rowCount = 0;
        int vrstaKlasifikacijeSort = 0;
        String namedQ = "findAllByBrandOrNamePriceSorted";
        Query q = entityManager.createNamedQuery(namedQ)
                .setParameter("klasaCene", woParametri.getKlasaCene())
                .setParameter("namePattern", patternNaziv)
                .setParameter("partner", woPartnerSettings.get(0).getPoslovniPartner().getPoslovniPartner())
                .setParameter("kompanija", woParametri.getWoKompanijaKorisnik().getId())
                .setParameter("ojc", woPartnerSettings.get(0).getOrganizacionaJedinica())
                .setParameter("klc", woPartnerSettings.get(0).getIdKlasaCene())
                .setParameter("cenovnik", woPartnerSettings.get(0).getIdCenovnik())
                .setParameter("vrstaKlasifikacijeMeni", woParametri.getVrstaKlasifikacijeFilter())
                .setParameter("brand", brand)
                .setParameter("proveraZaliha", woConfigSingleton.getAttributes()[4]);

        if (pageNo == 0) rowCount = q.getResultList().size();

        q.setFirstResult(pageNo * pageSize);
        q.setMaxResults(pageSize);

        List<OcpProizvod> lp = new ArrayList<OcpProizvod>();
        Iterator i = q.getResultList().iterator();
        setTansientForSorted(i, lp);

        return new Proizvodi(lp, vrstaKlasifikacijeSort, rowCount);
    }


    public Proizvodi findFilterProizvodiByNamePatternsSorted(String brand, List<String> patterns, int pageNo, int pageSize, WoParametri woParametri,
                                                             List<WoPartnerSetting> woPartnerSettings) {
        Session session = getSession();
        int rowCount = 0;
        int vrstaKlasifikacijeSort = 0;
        StringBuffer q = new StringBuffer("  select p.*  from Ocp_Proizvod p, ocp_vr_atr_proizvod a, WoProdCene c  where ( ");
        String predicate = "";
        for (String pattern : patterns) {
            predicate = predicate + "lower(naziv_proizvoda||dodatni_naziv) like lower('%" + pattern + "%') and ";
        }
        q.append(predicate.substring(0, predicate.length() - 4));
        q.append(") and a.atribut# = :proveraZaliha "
                + "  and a.proizvod# = p.proizvod# "
                + "   and ((exists (select 1 "
                + "              from wo_partner_settings w, uz_stanje_zaliha_skladista u"
                + "              where w.poslovni_partner# = :partner"
                + "              and w.id_kompanija_korisnik = :kompanija"
                + "              and w.id_skladista = u.id_skladista"
                + "              and u.proizvod# = p.proizvod# "
                + "              and u.kolicina_po_stanju_z - u.rezervisana_kol >0) "
                + "        and a.vrednost = 'DA')"
                + "        or (exists (select 1"
                + "              from wo_partner_settings w, uz_stanje_zaliha_skladista u, ocp_sastav_proizvoda s, uz_dozv_pakovanja pak "
                + "              where w.poslovni_partner# = :partner"
                + "              and w.id_kompanija_korisnik = :kompanija"
                + "              and w.id_skladista = u.id_skladista"
                + "              and s.proizvod#_ulaz = pak.proizvod_ref"
                + "              and pak.transportno = 'DA'"
                + "              and s.proizvod#_ulaz = p.proizvod#"
                + "              and u.proizvod#  = s.proizvod#_izlaz"
                + "              and ((u.kolicina_po_stanju_z - u.rezervisana_kol)/s.kolicina_ugradnje)/pak.kol_po_pakovanju > 1 ) and a.vrednost = 'SASTAV')"
                + "       or a.vrednost = 'NE') "
                + "     and c.klasaCene = :klasaCene"
                + "    and c.organizaciona_jedinica# = :ojc "
                + "     and c.id_klasa_cene = :klc "
                + "     and c.id_cenovnik = :cenovnik "
                + "     and c.datum_do is null "
                + "     and c.datum_ov is not null "
                + "     and c.proizvod# = p.proizvod#  "
                + "  and (exists (select 1 from ocp_klasifikacija_proizvoda km"
                + "                  where km.vrsta_klasifikacije# = :vrstaKlasifikacijeMeni"
                + "                   and km.klasifikacija# like  :brand||'%'"
                + "                   and km.proizvod# = p.proizvod#)"
                + "           or :brand is null )"
                + " order by c.cena, p.naziv_proizvoda||p.dodatni_naziv ");

        String query = q.toString();
        Query qNative = entityManager.createNativeQuery(query, OcpProizvod.class)
                .setParameter("klasaCene", woParametri.getKlasaCene())
                .setParameter("partner", woPartnerSettings.get(0).getPoslovniPartner().getPoslovniPartner())
                .setParameter("kompanija", woParametri.getWoKompanijaKorisnik().getId())
                .setParameter("ojc", woPartnerSettings.get(0).getOrganizacionaJedinica())
                .setParameter("klc", woPartnerSettings.get(0).getIdKlasaCene())
                .setParameter("cenovnik", woPartnerSettings.get(0).getIdCenovnik())
                .setParameter("vrstaKlasifikacijeMeni", woParametri.getVrstaKlasifikacijeFilter())
                .setParameter("brand", brand)
                .setParameter("proveraZaliha", woConfigSingleton.getAttributes()[4]);

        if (pageNo == 0) rowCount = qNative.getResultList().size();

        qNative.setFirstResult(pageNo * pageSize);
        qNative.setMaxResults(pageSize);

        List<OcpProizvod> lp = qNative.getResultList();
        for (OcpProizvod proizvod : lp) {
            proizvod.setPunNazivProizvoda(proizvod.getNazivProizvoda() + " " + proizvod.getDodatniNaziv());
        }

        return new Proizvodi(lp, vrstaKlasifikacijeSort, rowCount);
    }

    public OcpProizvod findFilterProizvodiById(Integer id, int pageNo, int pageSize, WoParametri woParametri,
                                               List<WoPartnerSetting> woPartnerSettings) {
        Session session = getSession();
        int rowCount = 0;
        int vrstaKlasifikacijeSort = 0;
        OcpProizvod ocpProizvod = null;
        StringBuffer q = new StringBuffer("  select p.*  from Ocp_Proizvod p, ocp_vr_atr_proizvod a, WoProdCene c  where ( ");
        String predicate = "";
        predicate = predicate + " p.proizvod# = " + id.intValue() + " and ";

        q.append(predicate.substring(0, predicate.length() - 4));
        q.append(") and a.atribut# = :proveraZaliha "
                + "  and a.proizvod# = p.proizvod# "
                + "   and ((exists (select 1 "
                + "              from wo_partner_settings w, uz_stanje_zaliha_skladista u"
                + "              where w.poslovni_partner# = :partner"
                + "              and w.id_kompanija_korisnik = :kompanija"
                + "              and w.id_skladista = u.id_skladista"
                + "              and u.proizvod# = p.proizvod# "
                + "              and u.kolicina_po_stanju_z - u.rezervisana_kol >0) "
                + "        and a.vrednost = 'DA')"
                + "        or (exists (select 1"
                + "              from wo_partner_settings w, uz_stanje_zaliha_skladista u, ocp_sastav_proizvoda s, uz_dozv_pakovanja pak "
                + "              where w.poslovni_partner# = :partner"
                + "              and w.id_kompanija_korisnik = :kompanija"
                + "              and w.id_skladista = u.id_skladista"
                + "              and s.proizvod#_ulaz = pak.proizvod_ref"
                + "              and pak.transportno = 'DA'"
                + "              and s.proizvod#_ulaz = p.proizvod#"
                + "              and u.proizvod#  = s.proizvod#_izlaz"
                + "              and ((u.kolicina_po_stanju_z - u.rezervisana_kol)/s.kolicina_ugradnje)/pak.kol_po_pakovanju > 1 ) and a.vrednost = 'SASTAV')"
                + "       or a.vrednost = 'NE') "
                + "     and c.klasaCene = :klasaCene"
                + "     and c.organizaciona_jedinica# = :ojc "
                + "     and c.id_klasa_cene = :klc "
                + "     and c.id_cenovnik = :cenovnik "
                + "     and c.datum_do is null "
                + "     and c.datum_ov is not null "
                + "     and c.proizvod# = p.proizvod#  "
                + " order by c.cena, p.naziv_proizvoda||p.dodatni_naziv ");

        String query = q.toString();
        Query qNative = entityManager.createNativeQuery(query, OcpProizvod.class)
                .setParameter("klasaCene", woParametri.getKlasaCene())
                .setParameter("partner", woPartnerSettings.get(0).getPoslovniPartner().getPoslovniPartner())
                .setParameter("kompanija", woParametri.getWoKompanijaKorisnik().getId())
                .setParameter("ojc", woPartnerSettings.get(0).getOrganizacionaJedinica())
                .setParameter("klc", woPartnerSettings.get(0).getIdKlasaCene())
                .setParameter("cenovnik", woPartnerSettings.get(0).getIdCenovnik())
                .setParameter("proveraZaliha", woConfigSingleton.getAttributes()[4]);

        if (pageNo == 0) rowCount = qNative.getResultList().size();
        qNative.setFirstResult(pageNo * pageSize);
        qNative.setMaxResults(pageSize);

        try {
            ocpProizvod = (OcpProizvod) qNative.getSingleResult();
            ocpProizvod.setPunNazivProizvoda(ocpProizvod.getNazivProizvoda() + " " + ocpProizvod.getDodatniNaziv());
            return ocpProizvod;
        } catch (NoResultException ex) {
            return null;
        }
    }

    public OcpProizvod findProizvodiByIdR(Integer id, int pageNo, int pageSize, WoParametri woParametri,
                                               List<WoPartnerSetting> woPartnerSettings) {
        Session session = getSession();
        int rowCount = 0;
        int vrstaKlasifikacijeSort = 0;
        OcpProizvod ocpProizvod = null;
        StringBuffer q = new StringBuffer("  select p.*  from Ocp_Proizvod p, ocp_vr_atr_proizvod a, WoProdCene c  where ( ");
        String predicate = "";
        predicate = predicate + " p.proizvod# = " + id.intValue() + " and ";

        q.append(predicate.substring(0, predicate.length() - 4));
        q.append(") and a.atribut# = :proveraZaliha "
                + "  and a.proizvod# = p.proizvod# "
                + "   and ((exists (select 1 "
                + "              from wo_partner_settings w, uz_stanje_zaliha_skladista u"
                + "              where w.poslovni_partner# = :partner"
                + "              and w.id_kompanija_korisnik = :kompanija"
                + "              and w.id_skladista = u.id_skladista"
                + "              and u.proizvod# = p.proizvod# ) "
                + "        and a.vrednost = 'DA')"
                + "        or (exists (select 1"
                + "              from wo_partner_settings w, uz_stanje_zaliha_skladista u, ocp_sastav_proizvoda s, uz_dozv_pakovanja pak "
                + "              where w.poslovni_partner# = :partner"
                + "              and w.id_kompanija_korisnik = :kompanija"
                + "              and w.id_skladista = u.id_skladista"
                + "              and s.proizvod#_ulaz = pak.proizvod_ref"
                + "              and pak.transportno = 'DA'"
                + "              and s.proizvod#_ulaz = p.proizvod#"
                + "              and u.proizvod#  = s.proizvod#_izlaz) and a.vrednost = 'SASTAV')"
                + "       or a.vrednost = 'NE') "
                + "     and c.klasaCene = :klasaCene"
                + "     and c.organizaciona_jedinica# = :ojc "
                + "     and c.id_klasa_cene = :klc "
                + "     and c.id_cenovnik = :cenovnik "
                + "     and c.datum_do is null "
                + "     and c.datum_ov is not null "
                + "     and c.proizvod# = p.proizvod#  "
                + " order by c.cena, p.naziv_proizvoda||p.dodatni_naziv ");

        String query = q.toString();
        Query qNative = entityManager.createNativeQuery(query, OcpProizvod.class)
                .setParameter("klasaCene", woParametri.getKlasaCene())
                .setParameter("partner", woPartnerSettings.get(0).getPoslovniPartner().getPoslovniPartner())
                .setParameter("kompanija", woParametri.getWoKompanijaKorisnik().getId())
                .setParameter("ojc", woPartnerSettings.get(0).getOrganizacionaJedinica())
                .setParameter("klc", woPartnerSettings.get(0).getIdKlasaCene())
                .setParameter("cenovnik", woPartnerSettings.get(0).getIdCenovnik())
                .setParameter("proveraZaliha", woConfigSingleton.getAttributes()[4]);

        if (pageNo == 0) rowCount = qNative.getResultList().size();
        qNative.setFirstResult(pageNo * pageSize);
        qNative.setMaxResults(pageSize);

        try {
            ocpProizvod = (OcpProizvod) qNative.getSingleResult();
            ocpProizvod.setPunNazivProizvoda(ocpProizvod.getNazivProizvoda() + " " + ocpProizvod.getDodatniNaziv());
            return ocpProizvod;
        } catch (NoResultException ex) {
            return null;
        }
    }
}
