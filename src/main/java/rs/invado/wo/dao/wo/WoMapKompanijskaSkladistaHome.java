package rs.invado.wo.dao.wo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.uz.UzSkladiste;
import rs.invado.wo.domain.wo.WoKompanijaKorisnik;
import rs.invado.wo.domain.wo.WoMapKompanijskaSkladista;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Nikola on 15/06/2016.
 */
@Repository
@Transactional
public class WoMapKompanijskaSkladistaHome {

    private static final Log log = LogFactory.getLog(WoMapKompanijskaSkladistaHome.class);

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(WoMapKompanijskaSkladista transientInstance) {
        log.debug("persisting WoMapKompanijskaSkladista instance");
        try {
            entityManager.persist(transientInstance);
            entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(WoMapKompanijskaSkladista persistentInstance) {
        log.debug("removing WoMapKompanijskaSkladista instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public WoMapKompanijskaSkladista merge(WoMapKompanijskaSkladista detachedInstance) {
        log.debug("merging WoMapKompanijskaSkladista instance");
        try {
            WoMapKompanijskaSkladista result = entityManager.merge(detachedInstance);
            entityManager.flush();
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public WoMapKompanijskaSkladista findById(Integer id) {
        log.debug("getting WoMapKompanijskaSkladista instance with id: " + id);
        try {
            WoMapKompanijskaSkladista instance = entityManager.find(WoMapKompanijskaSkladista.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public WoMapKompanijskaSkladista findActualSklRaspolozivo(UzSkladiste uzSkladisteRaspolozivo){
        Session session = entityManager.unwrap(Session.class);
        WoMapKompanijskaSkladista woMapKompanijskaSkladista = (WoMapKompanijskaSkladista) session.createCriteria(WoMapKompanijskaSkladista.class)
                .add(Restrictions.eq("uzSkladisteRaspolozivo", uzSkladisteRaspolozivo)).uniqueResult();
        return woMapKompanijskaSkladista;
    }

    public WoMapKompanijskaSkladista findActualSetOfParametersPerCompany(WoKompanijaKorisnik woKompanijaKorisnik){
        Session session = entityManager.unwrap(Session.class);
        WoMapKompanijskaSkladista parametri = (WoMapKompanijskaSkladista) session.createCriteria(WoMapKompanijskaSkladista.class)
                .add(Restrictions.isNull("datumDo"))
                .add(Restrictions.eq("woKompanijaKorisnik", woKompanijaKorisnik))
                .uniqueResult();
        parametri.getWoKompanijaKorisnik().getCorrespondingOJ();
        return parametri;
    }
}
