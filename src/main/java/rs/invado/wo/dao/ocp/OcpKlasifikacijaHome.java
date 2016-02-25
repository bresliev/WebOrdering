package rs.invado.wo.dao.ocp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.ocp.OcpKlasifikacija;
import rs.invado.wo.domain.ocp.OcpKlasifikacijaId;
import rs.invado.wo.domain.ocp.OcpProizvod;
import rs.invado.wo.domain.wo.WoParametri;
import rs.invado.wo.dto.CompanySetting;
import rs.invado.wo.dto.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Home object for domain model class OcpKlasifikacija.
 *
 * @author Hibernate Tools
 * @see rs.invado.wo.dao.ocp.OcpKlasifikacijaHome
 */

@Repository
@Transactional
//
public class OcpKlasifikacijaHome {

    private static final Log log = LogFactory
            .getLog(OcpKlasifikacijaHome.class);

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(OcpKlasifikacija transientInstance) {
        log.debug("persisting OcpKlasifikacija instance");
        try {
            entityManager.persist(transientInstance);
            entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(OcpKlasifikacija persistentInstance) {
        log.debug("removing OcpKlasifikacija instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public OcpKlasifikacija merge(OcpKlasifikacija detachedInstance) {
        log.debug("merging OcpKlasifikacija instance");
        try {
            OcpKlasifikacija result = entityManager.merge(detachedInstance);
            entityManager.flush();
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public OcpKlasifikacija findById(OcpKlasifikacijaId id) {
        log.debug("getting OcpKlasifikacija instance with id: " + id);
        try {
            OcpKlasifikacija instance = entityManager.find(
                    OcpKlasifikacija.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public OcpKlasifikacija findById(String vrstaKlasifikacije) {
        OcpKlasifikacija r = new OcpKlasifikacija();
        return r;

    }

    public List<OcpKlasifikacija> findHierarchy(int vrstaKlasifikacije, String root, int level, String secondsort) {

        List<OcpKlasifikacija> hijerarhijaKlasa = new ArrayList<OcpKlasifikacija>();
        Integer sort1 = new Integer(0);
        String namedQ = "";
        if (secondsort.equals("naziv")) {
            namedQ = "findSingleRootKlasifikacijaHierarchyNameSorted";
        } else {
            namedQ = "findSingleRootKlasifikacijaHierarchy";
        }

        Query q = entityManager.createNamedQuery(namedQ)
                .setParameter("root", root)
                .setParameter("vrstaKlasifikacije", vrstaKlasifikacije)
                .setParameter("nivo", level);

        Iterator i = q.getResultList().iterator();
        while (i.hasNext()) {
            Object[] parovi = (Object[]) i.next();
            OcpKlasifikacija klasa = (OcpKlasifikacija) parovi[0];
            if (klasa.getKlasifikacijaNad() == null) {
                if (klasa.getSort1() != null) {
                    sort1 = new Integer(klasa.getSort1());
                } else if (klasa.getSort1() == null) {
                    sort1 = new Integer(0);
                }
            }
            klasa.setSortByClass(sort1);
            klasa.setNivo((BigDecimal) parovi[1]);
            hijerarhijaKlasa.add(klasa);
        }

        return hijerarhijaKlasa;

    }

    public OcpKlasifikacija findRootKlasifikacijaByProizvod(OcpProizvod ocpProizvod, CompanySetting cs, Integer Oj) {
        String namedQ = "findRootProdajnaKlasifikacijaForProizvod";
        Query q = entityManager.createNamedQuery(namedQ)
                .setParameter("proizvod", ocpProizvod.getProizvod())
                .setParameter("vrklas", cs.getKompanijskiParametri().get(Oj).getVrstaKlasifikacijeFilter());
        return (OcpKlasifikacija) q.getSingleResult();
    }

    public List<OcpKlasifikacija> findKlasifikacijaWithAction(String tipAkcije, CompanySetting cs, int oj, User user) {
        WoParametri woParametri = cs.getKompanijskiParametri().get(oj);
        String namedQ = "findKlasifikacijaWithAction";
        List<OcpKlasifikacija> hijerarhijaKlasa = new ArrayList<OcpKlasifikacija>();
        Query q = entityManager.createNamedQuery(namedQ)
                .setParameter("vrklas", woParametri.getVrstaKlasifikacijeMeni())
                .setParameter("tipAkcije", tipAkcije)
                .setParameter("kompanijaKorisnik", woParametri.getWoKompanijaKorisnik().getId())
                .setParameter("partner", user.getWoPartnerSetting().get(0).getPoslovniPartner().getPoslovniPartner());
        hijerarhijaKlasa = q.getResultList();
        return hijerarhijaKlasa;
    }
}