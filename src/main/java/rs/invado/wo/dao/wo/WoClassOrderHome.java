package rs.invado.wo.dao.wo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.wo.WoClassOrder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;

/**
 * Created by Nikola on 07/08/2016.
 */
@Repository
@Transactional
public class WoClassOrderHome {

    private static final Log log = LogFactory
            .getLog(WoAutentifikacijaHome.class);

    @PersistenceContext
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    public void persist(WoClassOrder transientInstance) {
        log.debug("persisting WoClassOrder instance");
        try {
            entityManager.persist(transientInstance);
            entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(WoClassOrder persistentInstance) {
        log.debug("removing WoClassOrder instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public WoClassOrder merge(WoClassOrder detachedInstance) {
        log.debug("merging WoClassOrder instance");
        try {
            WoClassOrder result = entityManager.merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public WoClassOrder findById(BigDecimal id) {
        log.debug("getting WoClassOrder instance with id: " + id);
        try {
            WoClassOrder instance = entityManager.find(
                    WoClassOrder.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
}
