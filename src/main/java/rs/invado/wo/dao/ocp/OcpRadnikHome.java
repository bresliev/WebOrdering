package rs.invado.wo.dao.ocp;

// Generated Dec 9, 2012 5:42:26 PM by Hibernate Tools 3.4.0.CR1


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.ocp.OcpRadnik;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Home object for domain model class OcpRadnik.
 *
 * @author Hibernate Tools
 * @see rs.invado.wo.dao.ocp.OcpRadnikHome
 */
@Repository
@Transactional
//
public class OcpRadnikHome {

    private static final Log log = LogFactory.getLog(OcpRadnikHome.class);

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(OcpRadnik transientInstance) {
        log.debug("persisting OcpRadnik instance");
        try {
            entityManager.persist(transientInstance);
            entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(OcpRadnik persistentInstance) {
        log.debug("removing OcpRadnik instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public OcpRadnik merge(OcpRadnik detachedInstance) {
        log.debug("merging OcpRadnik instance");
        try {
            OcpRadnik result = entityManager.merge(detachedInstance);
            entityManager.flush();
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public OcpRadnik findById(int id) {
        log.debug("getting OcpRadnik instance with id: " + id);
        try {
            OcpRadnik instance = entityManager.find(OcpRadnik.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
}
