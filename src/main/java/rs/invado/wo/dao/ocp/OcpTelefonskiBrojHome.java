package rs.invado.wo.dao.ocp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.ocp.OcpTelefonskiBroj;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Nikola on 23/09/2017.
 */

@Repository
@Transactional
public class OcpTelefonskiBrojHome {
     Log log = LogFactory.getLog(OcpTelefonskiBrojHome.class);

    @PersistenceContext
    private EntityManager entityManager;

    public Session getSession() {
        Session session = entityManager.unwrap(Session.class);
        return session;
    }

    public void persist(OcpTelefonskiBroj transientInstance) {
        log.debug("persisting OcpTelefonskiBroj instance");
        try {
            entityManager.persist(transientInstance);  entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(OcpTelefonskiBroj persistentInstance) {
        log.debug("removing OcpTelefonskiBroj instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public OcpTelefonskiBroj merge(OcpTelefonskiBroj detachedInstance) {
        log.debug("merging OcpTelefonskiBroj instance");
        try {
            OcpTelefonskiBroj result = entityManager.merge(detachedInstance);
            entityManager.flush();
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public OcpTelefonskiBroj findById(int id) {
        log.debug("getting OcpTelefonskiBroj instance with id: " + id);
        try {
            OcpTelefonskiBroj instance = entityManager.find(OcpTelefonskiBroj.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List<OcpTelefonskiBroj> findByPartnerId(int partnerId) {
        log.info("getting OcpTelefonskiBroj instance with id: " + partnerId);
        try {

            Criteria cr = getSession().createCriteria(OcpTelefonskiBroj.class)
                    .add(Restrictions.eq("poslovniPartner.poslovniPartner", partnerId));
            cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            List<OcpTelefonskiBroj> instances = cr.list();

            log.debug("get successful");
            return instances;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public OcpTelefonskiBroj findFirsByPartnerId(int partnerId) {
        log.info("getting OcpTelefonskiBroj instance with id: " + partnerId);
        try {

            Criteria cr = getSession().createCriteria(OcpTelefonskiBroj.class)
                    .add(Restrictions.eq("poslovniPartner.poslovniPartner", partnerId))
                    .setFirstResult(0)
                    .setMaxResults(1);
            OcpTelefonskiBroj instance = (OcpTelefonskiBroj) cr.list();

            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
}
