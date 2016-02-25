package rs.invado.wo.dao.ocp;

// Generated Dec 9, 2012 5:42:26 PM by Hibernate Tools 3.4.0.CR1


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.ocp.OcpOrganizacionaJedinica;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

/**
 * Home object for domain model class OcpOrganizacionaJedinica.
 *
 * @author Hibernate Tools
 * @see rs.invado.wo.dao.ocp.OcpOrganizacionaJedinicaHome
 */
@Repository     @Transactional
//
public class OcpOrganizacionaJedinicaHome {

    private static final Log log = LogFactory
            .getLog(OcpOrganizacionaJedinicaHome.class);

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(OcpOrganizacionaJedinica transientInstance) {
        log.debug("persisting OcpOrganizacionaJedinica instance");
        try {
            entityManager.persist(transientInstance);  entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(OcpOrganizacionaJedinica persistentInstance) {
        log.debug("removing OcpOrganizacionaJedinica instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public OcpOrganizacionaJedinica merge(
            OcpOrganizacionaJedinica detachedInstance) {
        log.debug("merging OcpOrganizacionaJedinica instance");
        try {
            OcpOrganizacionaJedinica result = entityManager
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public OcpOrganizacionaJedinica findById(int id) {
        log.debug("getting OcpOrganizacionaJedinica instance with id: " + id);
        try {
            OcpOrganizacionaJedinica instance = entityManager.find(
                    OcpOrganizacionaJedinica.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public boolean checkPripadnostHijerarhiji(int OJ, int rootOJ) {

        String namedQ = "proveraOJHijerarhije";
        try {
            Object o  = entityManager.createNamedQuery(namedQ)
                    .setParameter("oj", OJ)
                    .setParameter("rootOJ", rootOJ)
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        } catch (NonUniqueResultException e) {
            return false;
        }
    }
}
