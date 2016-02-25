package rs.invado.wo.dao.wo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.wo.WoDownloadGrupa;
import rs.invado.wo.domain.wo.WoDownloadTip;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nikola
 * Date: 3/18/13
 * Time: 10:21 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
@Transactional
public class WoDownloadGrupaHome {
    private static final Log log = LogFactory.getLog(WoDownloadTipHome.class);

    @PersistenceContext
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    public void persist(WoDownloadGrupa transientInstance) {
        log.debug("persisting WoDownloadTip instance");
        try {
            entityManager.persist(transientInstance);  entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(WoDownloadGrupa persistentInstance) {
        log.debug("removing WoDownloadTip instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public WoDownloadGrupa merge(WoDownloadGrupa detachedInstance) {
        log.debug("merging WoDownloadTip instance");
        try {
            WoDownloadGrupa result = entityManager.merge(detachedInstance);
            entityManager.flush();
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public WoDownloadGrupa findById(Integer id) {
        log.debug("getting WoDownloadTip instance with id: " + id);
        try {
            WoDownloadGrupa instance = entityManager
                    .find(WoDownloadGrupa.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

}
