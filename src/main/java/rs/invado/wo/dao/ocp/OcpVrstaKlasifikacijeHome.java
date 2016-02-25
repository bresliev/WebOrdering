package rs.invado.wo.dao.ocp;

// Generated Dec 9, 2012 5:42:26 PM by Hibernate Tools 3.4.0.CR1


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.ocp.OcpVrstaKlasifikacije;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Home object for domain model class OcpVrstaKlasifikacije.
 *
 * @author Hibernate Tools
 * @see rs.invado.wo.dao.ocp.OcpVrstaKlasifikacijeHome
 */
@Repository
@Transactional
//
public class OcpVrstaKlasifikacijeHome {

    private static final Log log = LogFactory
            .getLog(OcpVrstaKlasifikacijeHome.class);

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(OcpVrstaKlasifikacije transientInstance) {
        log.debug("persisting OcpVrstaKlasifikacije instance");
        try {
            entityManager.persist(transientInstance);
            entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(OcpVrstaKlasifikacije persistentInstance) {
        log.debug("removing OcpVrstaKlasifikacije instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public OcpVrstaKlasifikacije merge(OcpVrstaKlasifikacije detachedInstance) {
        log.debug("merging OcpVrstaKlasifikacije instance");
        try {
            OcpVrstaKlasifikacije result = entityManager
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public OcpVrstaKlasifikacije findById(int id) {
        log.debug("getting OcpVrstaKlasifikacije instance with id: " + id);
        try {
            OcpVrstaKlasifikacije instance = entityManager.find(
                    OcpVrstaKlasifikacije.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
}
