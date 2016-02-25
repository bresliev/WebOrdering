package rs.invado.wo.dao.ocp;

// Generated Dec 9, 2012 5:42:26 PM by Hibernate Tools 3.4.0.CR1


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.ocp.OcpVrstaDokumenta;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Home object for domain model class OcpVrstaDokumenta.
 *
 * @author Hibernate Tools
 * @see rs.invado.wo.dao.ocp.OcpVrstaDokumentaHome
 */
@Repository
@Transactional
//
public class OcpVrstaDokumentaHome {

    private static final Log log = LogFactory
            .getLog(OcpVrstaDokumentaHome.class);

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(OcpVrstaDokumenta transientInstance) {
        log.debug("persisting OcpVrstaDokumenta instance");
        try {
            entityManager.persist(transientInstance);
            entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(OcpVrstaDokumenta persistentInstance) {
        log.debug("removing OcpVrstaDokumenta instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public OcpVrstaDokumenta merge(OcpVrstaDokumenta detachedInstance) {
        log.debug("merging OcpVrstaDokumenta instance");
        try {
            OcpVrstaDokumenta result = entityManager.merge(detachedInstance);
            entityManager.flush();
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public OcpVrstaDokumenta findById(short id) {
        log.debug("getting OcpVrstaDokumenta instance with id: " + id);
        try {
            OcpVrstaDokumenta instance = entityManager.find(
                    OcpVrstaDokumenta.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
}
