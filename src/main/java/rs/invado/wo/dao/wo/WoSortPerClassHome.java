package rs.invado.wo.dao.wo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.wo.WoSortPerClass;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;

/**
 * Created by Nikola on 07/08/2016.
 */
@Repository
@Transactional
public class WoSortPerClassHome {


    private static final Log log = LogFactory
            .getLog(WoAutentifikacijaHome.class);

    @PersistenceContext
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    public void persist(WoSortPerClass transientInstance) {
        log.debug("persisting WoSortPerClass instance");
        try {
            entityManager.persist(transientInstance);
            entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(WoSortPerClass persistentInstance) {
        log.debug("removing WoSortPerClass instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public WoSortPerClass merge(WoSortPerClass detachedInstance) {
        log.debug("merging WoSortPerClass instance");
        try {
            WoSortPerClass result = entityManager.merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public WoSortPerClass findById(BigDecimal id) {
        log.debug("getting WoSortPerClass instance with id: " + id);
        try {
            WoSortPerClass instance = entityManager.find(
                    WoSortPerClass.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }


}
