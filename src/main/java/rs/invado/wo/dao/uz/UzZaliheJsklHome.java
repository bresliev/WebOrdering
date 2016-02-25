package rs.invado.wo.dao.uz;

// Generated Jan 1, 2013 5:03:17 PM by Hibernate Tools 3.4.0.CR1


import org.apache.commons.collections.list.SetUniqueList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.uz.UzZaliheJskl;
import rs.invado.wo.domain.uz.UzZaliheJsklId;
import rs.invado.wo.dto.ZalihaPoPakovanjima;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.*;

/**
 * Home object for domain model class UzZaliheJskl.
 *
 * @author Hibernate Tools
 * @see rs.invado.wo.domain.uz.UzZaliheJskl
 */
@Repository
@Transactional
//
public class UzZaliheJsklHome {

    private static final Log log = LogFactory.getLog(UzZaliheJsklHome.class);

    @PersistenceContext
    private EntityManager entityManager;

    public Session getSession() {
        Session session = entityManager.unwrap(Session.class);
        return session;
    }

    public void persist(UzZaliheJskl transientInstance) {
        log.debug("persisting UzZaliheJskl instance");
        try {
            entityManager.persist(transientInstance);
            entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(UzZaliheJskl persistentInstance) {
        log.debug("removing UzZaliheJskl instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public UzZaliheJskl merge(UzZaliheJskl detachedInstance) {
        log.debug("merging UzZaliheJskl instance");
        try {
            UzZaliheJskl result = entityManager.merge(detachedInstance);
            entityManager.flush();
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public UzZaliheJskl findById(UzZaliheJsklId id) {
        log.debug("getting UzZaliheJskl instance with id: " + id);
        try {
            UzZaliheJskl instance = entityManager.find(UzZaliheJskl.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List<BigDecimal> findJsklPakPerPro(Integer proizvod, Integer skl) {
        //potrebno je da pro ima i osnovnu jm i alernativnu. ukoliko nije svojstveno ondaje osnovnoa = alternativnoj, postaviti triger i uraditi upradte tabele.
        List<UzZaliheJskl> zal = new ArrayList<UzZaliheJskl>();
        List<BigDecimal> pak = new ArrayList<BigDecimal>();
        int vrstaKlasifikacijeSort = 0;
        int rowCount = 0;
        Query q;
        String namedQ = "distincJsklPakPerPro";
        q = entityManager.createNamedQuery(namedQ)
                .setParameter("proizvod", proizvod.intValue())
                .setParameter("skladiste", skl.intValue());
        zal = q.getResultList();

        for (UzZaliheJskl zaliha : zal) {
            pak.add(zaliha.getKolicinaPoPovezu());
        }
        return SetUniqueList.decorate(pak);

    }


    public Map<BigDecimal, ZalihaPoPakovanjima> findJsklPakPerPro(Integer proizvod, Integer skl, BigDecimal kolPoPak) {
        //potrebno je da pro ima i osnovnu jm i alernativnu. ukoliko nije svojstveno ondaje osnovnoa = alternativnoj, postaviti triger i uraditi upradte tabele.
        BigDecimal zal;
        Query q;
        String namedQ = "distinctJsklPakAndKolPerPro";
        q = entityManager.createNamedQuery(namedQ)
                .setParameter("proizvod", proizvod.intValue())
                .setParameter("skladiste", skl.intValue());


        Map<BigDecimal, ZalihaPoPakovanjima> brojPakovanja = new HashMap<BigDecimal, ZalihaPoPakovanjima>();
        Iterator i = q.getResultList().iterator();
        while (i.hasNext()) {
            Object[] parovi = (Object[]) i.next();
            ZalihaPoPakovanjima zalihaPoPakovanjima = new ZalihaPoPakovanjima();
            zalihaPoPakovanjima.setKolPoPakovanju((BigDecimal) parovi[0]);
            zalihaPoPakovanjima.setBrojPakovanja((BigDecimal) parovi[1]);
            zalihaPoPakovanjima.setKolPoPovezu((BigDecimal) parovi[2]);
            brojPakovanja.put((BigDecimal) parovi[0], zalihaPoPakovanjima);
        }

        return brojPakovanja;

    }
}
