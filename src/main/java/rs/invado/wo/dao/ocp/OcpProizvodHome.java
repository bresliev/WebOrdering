package rs.invado.wo.dao.ocp;

// Generated Dec 9, 2012 5:42:26 PM by Hibernate Tools 3.4.0.CR1


import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.ocp.OcpKlasifikacija;
import rs.invado.wo.domain.ocp.OcpKlasifikacijaProizvoda;
import rs.invado.wo.domain.ocp.OcpProizvod;
import rs.invado.wo.domain.prod.ProdCenovnik;
import rs.invado.wo.domain.prod.ProdPoreskaStopa;
import rs.invado.wo.domain.uz.UzStanjeZalihaSkladista;
import rs.invado.wo.domain.wo.WoParametri;
import rs.invado.wo.domain.wo.WoPartnerSetting;
import rs.invado.wo.dto.CompanySetting;
import rs.invado.wo.dto.Proizvodi;

import javax.persistence.*;
import java.math.BigDecimal;
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

    private static final Log log = LogFactory.getLog(OcpProizvodHome.class);


    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private OcpKlasifikacijaHome ocpKlasifikacijaDAO;

    public Session getSession() {
        Session session = entityManager.unwrap(Session.class);
        return session;
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


    private int getRowCount(Criteria cr) {
        Criteria crRowCount = cr.setProjection(Projections.rowCount());
        int rowCount = Integer.valueOf(cr.uniqueResult().toString());
        return rowCount;
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
        List<OcpProizvod> lp = cr.setFirstResult(pageNo * pageSize)
                .setMaxResults(pageSize).list();
        int rowCount = 0;
        if (pageNo == 0)
            rowCount = getRowCount(cr);
        return new Proizvodi(lp, 0, rowCount);
    }

    public Proizvodi findProizvodiByName(String namePattern, int pageNo, int pageSize, WoParametri woParametri,
                                         List<WoPartnerSetting> woPartnerSettings) {
        Criteria cr = getSession().createCriteria(OcpProizvod.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .setFirstResult(pageNo * pageSize)
                .setMaxResults(pageSize)
                .add(Subqueries.propertyIn("pro.proizvod", getProPoSklSubquery(woPartnerSettings)))
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
        q.append(") and exists (select 1                    from wo_partner_settings w, uz_stanje_zaliha_skladista u                   where w.poslovni_partner# = :partner                   and w.id_kompanija_korisnik = :kompanija                   and w.id_skladista = u.id_skladista                   and u.proizvod# = p.proizvod#                    and u.kolicina_po_stanju_z - u.rezervisana_kol >0)   and (exists (select 1 from ocp_klasifikacija_proizvoda km                  where km.vrsta_klasifikacije# = :vrstaKlasifikacijeFilter                   and klasifikacija# like  :brand||'%%'                   and km.proizvod# = p.proizvod#)           or :brand is null ) order by naziv_proizvoda||dodatni_naziv");
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
        List<OcpProizvod> lp=new ArrayList<OcpProizvod>();
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
                                                List<WoPartnerSetting> woPartnerSettings, CompanySetting cs) {

        //potrebno je da pro ima i osnovnu jm i alernativnu. ukoliko nije svojstveno ondaje osnovnoa = alternativnoj, postaviti triger i uraditi upradte tabele.
        List<OcpProizvod> lp = new ArrayList<OcpProizvod>();
        int vrstaKlasifikacijeSort = 0;
        int rowCount = 0;
        String namedQ;
        Query q;
        List<OcpKlasifikacija> meni = cs.getMeni().get(woParametri.getWoKompanijaKorisnik().getCorrespondingOJ() + "");
        for (OcpKlasifikacija ocpKlasifikacija : meni)
            if (ocpKlasifikacija.getId().getKlasifikacija().equals(brand))
                vrstaKlasifikacijeSort = ocpKlasifikacija.getSortByClass().intValue();
        if (vrstaKlasifikacijeSort != 0) {
            namedQ = "findAllByBrandSorted";
            q = entityManager.createNamedQuery(namedQ)
                    .setParameter("partner", woPartnerSettings.get(0).getPoslovniPartner().getPoslovniPartner())
                    .setParameter("kompanija", woParametri.getWoKompanijaKorisnik().getId())
                    .setParameter("vrstaKlasifikacijeSort", vrstaKlasifikacijeSort)
                    .setParameter("vrstaKlasifikacijeMeni", woParametri.getVrstaKlasifikacijeMeni())
                    .setParameter("brand", brand);
        } else {
            namedQ = "findAllByBrandPriceSorted";
            q = entityManager.createNamedQuery(namedQ)
                    .setParameter("partner", woPartnerSettings.get(0).getPoslovniPartner().getPoslovniPartner())
                    .setParameter("kompanija", woParametri.getWoKompanijaKorisnik().getId())
                    .setParameter("ojc", woPartnerSettings.get(0).getOrganizacionaJedinica())
                    .setParameter("klc", woPartnerSettings.get(0).getIdKlasaCene())
                    .setParameter("cenovnik", woPartnerSettings.get(0).getIdCenovnik())
                    .setParameter("vrstaKlasifikacijeMeni", woParametri.getVrstaKlasifikacijeMeni())
                    .setParameter("brand", brand);
        }

        if (pageNo == 0) rowCount=q.getResultList().size();

        q.setFirstResult(pageNo * pageSize);
        q.setMaxResults(pageSize);

        Iterator i = q.getResultList().iterator();
        setTansientForSorted(i, lp);

        return new Proizvodi(lp, vrstaKlasifikacijeSort, rowCount);
    }


    public Proizvodi findProizvodiNaAkcijiSorted(String brandID, String tipAkcije, int pageNo, int pageSize, WoParametri woParametri,
                                                 List<WoPartnerSetting> woPartnerSettings) {
        //potrebno je da pro ima i osnovnu jm i alernativnu. ukoliko nije svojstveno ondaje osnovnoa = alternativnoj, postaviti triger i uraditi upradte tabele.
        List<OcpProizvod> lp = new ArrayList<OcpProizvod>();
        int vrstaKlasifikacijeSort = 0;
        int rowCount = 0;
        Query q;
        String namedQ = "findByActionTypePriceSorted";
        q = entityManager.createNamedQuery(namedQ)
                .setParameter("tipakcije", tipAkcije)
                .setParameter("partner", woPartnerSettings.get(0).getPoslovniPartner().getPoslovniPartner())
                .setParameter("kompanija", woParametri.getWoKompanijaKorisnik().getId())
                .setParameter("ojc", woPartnerSettings.get(0).getOrganizacionaJedinica())
                .setParameter("klc", woPartnerSettings.get(0).getIdKlasaCene())
                .setParameter("cenovnik", woPartnerSettings.get(0).getIdCenovnik())
                .setParameter("vrstaKlasifikacijeSort", woParametri.getVrstaKlasifikacijeMeni())
                .setParameter("klasifikacija", brandID);

        if (pageNo == 0) rowCount=q.getResultList().size();

        q.setFirstResult(pageNo * pageSize);
        q.setMaxResults(pageSize);

        Iterator i = q.getResultList().iterator();
        setTansientForSorted(i, lp);

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
                .setParameter("namePattern", namePattern)
                .setParameter("partner", woPartnerSettings.get(0).getPoslovniPartner().getPoslovniPartner())
                .setParameter("kompanija", woParametri.getWoKompanijaKorisnik().getId())
                .setParameter("ojc", woPartnerSettings.get(0).getOrganizacionaJedinica())
                .setParameter("klc", woPartnerSettings.get(0).getIdKlasaCene())
                .setParameter("cenovnik", woPartnerSettings.get(0).getIdCenovnik());

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
                .setParameter("namePattern", patternNaziv)
                .setParameter("partner", woPartnerSettings.get(0).getPoslovniPartner().getPoslovniPartner())
                .setParameter("kompanija", woParametri.getWoKompanijaKorisnik().getId())
                .setParameter("ojc", woPartnerSettings.get(0).getOrganizacionaJedinica())
                .setParameter("klc", woPartnerSettings.get(0).getIdKlasaCene())
                .setParameter("cenovnik", woPartnerSettings.get(0).getIdCenovnik())
                .setParameter("vrstaKlasifikacijeMeni", woParametri.getVrstaKlasifikacijeFilter())
                .setParameter("brand", brand);

        if (pageNo == 0) rowCount=q.getResultList().size();

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
        StringBuffer q = new StringBuffer("  select p.*  from Ocp_Proizvod p, prod_cenovnik c, prod_stavka_cenovnika cs  where ( ");
        String predicate = "";
        for (String pattern : patterns) {
            predicate = predicate + "lower(naziv_proizvoda||dodatni_naziv) like lower('%" + pattern + "%') and ";
        }
        q.append(predicate.substring(0, predicate.length() - 4));
        q.append(")  and exists (select 1 "
                + "                   from wo_partner_settings w, uz_stanje_zaliha_skladista u"
                + "                   where w.poslovni_partner# = :partner"
                + "                   and w.id_kompanija_korisnik = :kompanija"
                + "                   and w.id_skladista = u.id_skladista"
                + "                   and u.proizvod# = p.proizvod# "
                + "                   and u.kolicina_po_stanju_z - u.rezervisana_kol >0) "
                + "    and c.organizaciona_jedinica# = :ojc "
                +"     and c.id_klasa_cene = :klc "
                +"     and c.id_cenovnik = :cenovnik "
                +"     and c.datum_do is null "
                +"     and c.datum_ov is not null "
                +"     and c.organizaciona_jedinica# = cs.organizaciona_jedinica#"
                +"     and c.id_klasa_cene = cs.id_klasa_cene "
                +"     and c.id_cenovnik = cs.id_cenovnik"
                +"     and cs.proizvod# = p.proizvod#  "
                + "  and (exists (select 1 from ocp_klasifikacija_proizvoda km"
                + "                  where km.vrsta_klasifikacije# = :vrstaKlasifikacijeMeni"
                + "                   and km.klasifikacija# like  :brand||'%'"
                + "                   and km.proizvod# = p.proizvod#)"
                + "           or :brand is null )"
                + " order by cs.cena, p.naziv_proizvoda||p.dodatni_naziv ");

        String query = q.toString();
        Query qNative = entityManager.createNativeQuery(query, OcpProizvod.class)
                .setParameter("partner", woPartnerSettings.get(0).getPoslovniPartner().getPoslovniPartner())
                .setParameter("kompanija", woParametri.getWoKompanijaKorisnik().getId())
                .setParameter("ojc", woPartnerSettings.get(0).getOrganizacionaJedinica())
                .setParameter("klc", woPartnerSettings.get(0).getIdKlasaCene())
                .setParameter("cenovnik", woPartnerSettings.get(0).getIdCenovnik())
                .setParameter("vrstaKlasifikacijeMeni", woParametri.getVrstaKlasifikacijeFilter())
                .setParameter("brand", brand);

        if (pageNo == 0) rowCount= qNative.getResultList().size();

        qNative.setFirstResult(pageNo * pageSize);
        qNative.setMaxResults(pageSize);

        List<OcpProizvod> lp = qNative.getResultList();
        for (OcpProizvod proizvod : lp) {
            proizvod.setPunNazivProizvoda(proizvod.getNazivProizvoda() + " " + proizvod.getDodatniNaziv());
        }

        return new Proizvodi(lp, vrstaKlasifikacijeSort, rowCount);
    }
}
