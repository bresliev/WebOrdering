package rs.invado.wo.dao.ocp;

// Generated Dec 9, 2012 5:42:26 PM by Hibernate Tools 3.4.0.CR1


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.ocp.OcpVpNaVd;
import rs.invado.wo.domain.ocp.OcpVpNaVdId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Home object for domain model class OcpVpNaVd.
 *
 * @author Hibernate Tools
 * @see rs.invado.wo.dao.ocp.OcpVpNaVdHome
 */
@Repository
@Transactional
//
public class OcpVpNaVdHome {

    private static final Log log = LogFactory.getLog(OcpVpNaVdHome.class);

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(OcpVpNaVd transientInstance) {
        log.debug("persisting OcpVpNaVd instance");
        try {
            entityManager.persist(transientInstance);
            entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(OcpVpNaVd persistentInstance) {
        log.debug("removing OcpVpNaVd instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public OcpVpNaVd merge(OcpVpNaVd detachedInstance) {
        log.debug("merging OcpVpNaVd instance");
        try {
            OcpVpNaVd result = entityManager.merge(detachedInstance);
            entityManager.flush();
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public OcpVpNaVd findById(OcpVpNaVdId id) {
        log.debug("getting OcpVpNaVd instance with id: " + id);
        try {
            OcpVpNaVd instance = entityManager.find(OcpVpNaVd.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
}
