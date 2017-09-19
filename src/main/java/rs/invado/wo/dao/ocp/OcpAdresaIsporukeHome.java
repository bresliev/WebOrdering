package rs.invado.wo.dao.ocp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.wo.WoKompanijaKorisnik;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Order;
import java.math.BigDecimal;
import java.util.List;

import rs.invado.wo.domain.ocp.OcpAdresaIsporuke;

/**
 * Created by Nikola on 17/09/2017.
 */

@Repository
@Transactional

public class OcpAdresaIsporukeHome {


    private static final Log log = LogFactory
            .getLog(OcpAdresaIsporukeHome.class);

    @PersistenceContext
    private EntityManager entityManager;

    public Session getSession() {
        Session session = entityManager.unwrap(Session.class);
        return session;
    }

    public void persist(OcpAdresaIsporuke transientInstance) {
        log.debug("persisting OcpAdresaIsporuke instance");
        try {
            entityManager.persist(transientInstance);
            entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(OcpAdresaIsporuke persistentInstance) {
        log.debug("removing OcpAdresaIsporuke instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public OcpAdresaIsporuke merge(
            OcpAdresaIsporuke detachedInstance) {
        log.debug("merging OcpAdresaIsporuke instance");
        try {
            OcpAdresaIsporuke result = entityManager
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public OcpAdresaIsporuke findById(BigDecimal id) {
        log.debug("getting OcpAdresaIsporuke instance with id: " + id);
        try {
            OcpAdresaIsporuke instance = entityManager.find(
                    OcpAdresaIsporuke.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }



    public List<OcpAdresaIsporuke> findByPartnerId(int partnerId) {
        log.info("getting OcpAdresaIsporuke instance with id: " + partnerId);
        try {

            Criteria cr = getSession().createCriteria(OcpAdresaIsporuke.class)
                    .add(Restrictions.eq("poslovniPartner.poslovniPartner", partnerId))
                    .add(Restrictions.eq("aktivna", "DA"))
                    .addOrder(org.hibernate.criterion.Order.asc("prioritet"));
            cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            List<OcpAdresaIsporuke> instances = cr.list();

            log.debug("get successful");
            return instances;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
}


