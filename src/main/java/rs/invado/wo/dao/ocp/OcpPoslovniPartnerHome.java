package rs.invado.wo.dao.ocp;

// Generated Dec 9, 2012 5:42:26 PM by Hibernate Tools 3.4.0.CR1


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.ocp.OcpPoslovniPartner;
import rs.invado.wo.domain.wo.WoPartnerSetting;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Home object for domain model class OcpPoslovniPartner.
 *
 * @author Hibernate Tools
 * @see rs.invado.wo.dao.ocp.OcpPoslovniPartnerHome
 */
@Repository
@Transactional
//
public class OcpPoslovniPartnerHome {

    private static final Log log = LogFactory
            .getLog(OcpPoslovniPartnerHome.class);

    @PersistenceContext
    private EntityManager entityManager;


    public Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    public void persist(OcpPoslovniPartner transientInstance) {
        log.debug("persisting OcpPoslovniPartner instance");
        try {
            entityManager.persist(transientInstance);
            entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(OcpPoslovniPartner persistentInstance) {
        log.debug("removing OcpPoslovniPartner instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public OcpPoslovniPartner merge(OcpPoslovniPartner detachedInstance) {
        log.debug("merging OcpPoslovniPartner instance");
        try {
            OcpPoslovniPartner result = entityManager.merge(detachedInstance);
            entityManager.flush();
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public OcpPoslovniPartner findById(int id) {
        log.debug("getting OcpPoslovniPartner instance with id: " + id);
        try {
            OcpPoslovniPartner instance = entityManager.find(
                    OcpPoslovniPartner.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    private DetachedCriteria getWOPartnerSubquery() {

        DetachedCriteria subquery = DetachedCriteria.forClass(WoPartnerSetting.class, "wps")
                .setProjection(Projections.property("wps.poslovniPartner.poslovniPartner"));
        return subquery;
    }

    public List<OcpPoslovniPartner> findPartnerByName(String namePattern) {
        List<OcpPoslovniPartner> lp = getSession().createCriteria(OcpPoslovniPartner.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.like("naziv", namePattern, MatchMode.ANYWHERE).ignoreCase())
                .add(Subqueries.propertyIn("poslovniPartner", getWOPartnerSubquery()))
                .addOrder(Order.asc("naziv")).list();
        return lp;
    }
}
