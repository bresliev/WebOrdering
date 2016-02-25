package rs.invado.wo.dao.wo;

// Generated Jan 13, 2013 6:20:53 PM by Hibernate Tools 3.4.0.CR1

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.wo.WoDownloadTip;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

/**
 * Home object for domain model class WoDownloadTip.
 *
 * @author Hibernate Tools
 * @see rs.invado.wo.domain.wo.WoDownloadTip
 */
@Repository
@Transactional
//
public class WoDownloadTipHome {

    private static final Log log = LogFactory.getLog(WoDownloadTipHome.class);

    @PersistenceContext
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    public void persist(WoDownloadTip transientInstance) {
        log.debug("persisting WoDownloadTip instance");
        try {
            entityManager.persist(transientInstance);  entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(WoDownloadTip persistentInstance) {
        log.debug("removing WoDownloadTip instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public WoDownloadTip merge(WoDownloadTip detachedInstance) {
        log.debug("merging WoDownloadTip instance");
        try {
            WoDownloadTip result = entityManager.merge(detachedInstance);
            entityManager.flush();
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public WoDownloadTip findById(BigDecimal id) {
        log.debug("getting WoDownloadTip instance with id: " + id);
        try {
            WoDownloadTip instance = entityManager
                    .find(WoDownloadTip.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List<WoDownloadTip> findActivePerCompany(BigDecimal companyId) {

        return getSession().createCriteria(WoDownloadTip.class)
                .add(Restrictions.or(Restrictions.isNull("vaziDo"),
                        Restrictions.lt("vaziDo", Calendar.getInstance().getTime())))
                .add(Restrictions.eq("idKompanijeKorisnik", companyId)).list();
    }

    public List<WoDownloadTip> findActivePerOJ(BigDecimal ojId) {

        return getSession().createCriteria(WoDownloadTip.class)
                .add(Restrictions.or(Restrictions.isNull("vaziDo"),
                        Restrictions.lt("vaziDo", Calendar.getInstance().getTime())))
                .add(Restrictions.eq("organizacionaJedinica", ojId)).list();
    }
}
