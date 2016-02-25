package rs.invado.wo.dao.ocp;

// Generated Dec 9, 2012 5:42:26 PM by Hibernate Tools 3.4.0.CR1


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.ocp.OcpKlasifikacijaProizvoda;
import rs.invado.wo.domain.ocp.OcpKlasifikacijaProizvodaId;
import rs.invado.wo.domain.ocp.OcpProizvod;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Home object for domain model class OcpKlasifikacijaProizvoda.
 *
 * @author Hibernate Tools
 * @see rs.invado.wo.dao.ocp.OcpKlasifikacijaProizvodaHome
 */
@Repository
@Transactional
//
public class OcpKlasifikacijaProizvodaHome {

    private static final Log log = LogFactory
            .getLog(OcpKlasifikacijaProizvodaHome.class);

    @PersistenceContext
    private EntityManager entityManager;

    public Session getSession() {
        Session session = entityManager.unwrap(Session.class);
        return session;
    }

    public void persist(OcpKlasifikacijaProizvoda transientInstance) {
        log.debug("persisting OcpKlasifikacijaProizvoda instance");
        try {
            entityManager.persist(transientInstance);
            entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(OcpKlasifikacijaProizvoda persistentInstance) {
        log.debug("removing OcpKlasifikacijaProizvoda instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public OcpKlasifikacijaProizvoda merge(
            OcpKlasifikacijaProizvoda detachedInstance) {
        log.debug("merging OcpKlasifikacijaProizvoda instance");
        try {
            OcpKlasifikacijaProizvoda result = entityManager
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public OcpKlasifikacijaProizvoda findById(OcpKlasifikacijaProizvodaId id) {
        log.debug("getting OcpKlasifikacijaProizvoda instance with id: " + id);
        try {
            OcpKlasifikacijaProizvoda instance = entityManager.find(
                    OcpKlasifikacijaProizvoda.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }




}
