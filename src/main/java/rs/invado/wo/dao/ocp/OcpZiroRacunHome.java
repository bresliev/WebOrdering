package rs.invado.wo.dao.ocp;

// Generated Dec 9, 2012 5:42:26 PM by Hibernate Tools 3.4.0.CR1


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.ocp.OcpZiroRacun;
import rs.invado.wo.domain.ocp.OcpZiroRacunId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Home object for domain model class OcpZiroRacun.
 *
 * @author Hibernate Tools
 * @see rs.invado.wo.dao.ocp.OcpZiroRacunHome
 */
@Repository
@Transactional
//
public class OcpZiroRacunHome {

    private static final Log log = LogFactory.getLog(OcpZiroRacunHome.class);

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(OcpZiroRacun transientInstance) {
        log.debug("persisting OcpZiroRacun instance");
        try {
            entityManager.persist(transientInstance);
            entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(OcpZiroRacun persistentInstance) {
        log.debug("removing OcpZiroRacun instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public OcpZiroRacun merge(OcpZiroRacun detachedInstance) {
        log.debug("merging OcpZiroRacun instance");
        try {
            OcpZiroRacun result = entityManager.merge(detachedInstance);
            entityManager.flush();
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public OcpZiroRacun findById(OcpZiroRacunId id) {
        log.debug("getting OcpZiroRacun instance with id: " + id);
        try {
            OcpZiroRacun instance = entityManager.find(OcpZiroRacun.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
}
