package rs.invado.wo.dao.ocp;

// Generated Dec 9, 2012 5:42:26 PM by Hibernate Tools 3.4.0.CR1


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.ocp.OcpProizvodJeVrste;
import rs.invado.wo.domain.ocp.OcpProizvodJeVrsteId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Home object for domain model class OcpProizvodJeVrste.
 *
 * @author Hibernate Tools
 * @see rs.invado.wo.dao.ocp.OcpProizvodJeVrsteHome
 */
@Repository
@Transactional
//
public class OcpProizvodJeVrsteHome {

    private static final Log log = LogFactory
            .getLog(OcpProizvodJeVrsteHome.class);

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(OcpProizvodJeVrste transientInstance) {
        log.debug("persisting OcpProizvodJeVrste instance");
        try {
            entityManager.persist(transientInstance);
            entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(OcpProizvodJeVrste persistentInstance) {
        log.debug("removing OcpProizvodJeVrste instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public OcpProizvodJeVrste merge(OcpProizvodJeVrste detachedInstance) {
        log.debug("merging OcpProizvodJeVrste instance");
        try {
            OcpProizvodJeVrste result = entityManager.merge(detachedInstance);
            entityManager.flush();
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public OcpProizvodJeVrste findById(OcpProizvodJeVrsteId id) {
        log.debug("getting OcpProizvodJeVrste instance with id: " + id);
        try {
            OcpProizvodJeVrste instance = entityManager.find(
                    OcpProizvodJeVrste.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
}
