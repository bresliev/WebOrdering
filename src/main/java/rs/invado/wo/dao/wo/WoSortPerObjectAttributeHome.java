package rs.invado.wo.dao.wo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.wo.WoSortPerObjectAttribute;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Nikola on 07/08/2016.
 */
@Repository
@Transactional
public class WoSortPerObjectAttributeHome {


    private static final Log log = LogFactory
            .getLog(WoAutentifikacijaHome.class);

    @PersistenceContext
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    public void persist(WoSortPerObjectAttribute transientInstance) {
        log.debug("persisting WoSortPerObjectAttribute instance");
        try {
            entityManager.persist(transientInstance);
            entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(WoSortPerObjectAttribute persistentInstance) {
        log.debug("removing WoSortPerObjectAttribute instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public WoSortPerObjectAttribute merge(WoSortPerObjectAttribute detachedInstance) {
        log.debug("merging WoSortPerObjectAttribute instance");
        try {
            WoSortPerObjectAttribute result = entityManager.merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public WoSortPerObjectAttribute findById(BigDecimal id) {
        log.debug("getting WoSortPerObjectAttribute instance with id: " + id);
        try {
            WoSortPerObjectAttribute instance = entityManager.find(
                    WoSortPerObjectAttribute.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List<WoSortPerObjectAttribute> findByWoClassId(Integer vrstaKlasifikacije, String brand) {
        try {

            String namedQ = "findWoAttributeSortByClass";
            Query q = entityManager.createNamedQuery(namedQ)
                    .setParameter("vrstaKlasifikacije", vrstaKlasifikacije)
                    .setParameter("brand", brand);

            List<WoSortPerObjectAttribute> instance = q.getResultList();
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
}
