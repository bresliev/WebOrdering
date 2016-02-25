package rs.invado.wo.dao.wo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.wo.WoUser;
import rs.invado.wo.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: nikola
 * Date: 16.12.12.
 * Time: 18.51
 * To change this template use File | Settings | File Templates.
 */
@Repository
@Transactional
//
public class WoUserHome {


    private static final Log log = LogFactory
            .getLog(WoAutentifikacijaHome.class);

    @PersistenceContext
    private EntityManager entityManager;
    private Session getSession(){
        return entityManager.unwrap(Session.class);
    }

    public void persist(WoUser transientInstance) {
        log.debug("persisting WoUser instance");
        try {
            entityManager.persist(transientInstance);  entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(WoUser persistentInstance) {
        log.debug("removing WoUser instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public WoUser merge(WoUser detachedInstance) {
        log.debug("merging WoUser instance");
        try {
            WoUser result = entityManager.merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public WoUser findById(BigDecimal id) {
        log.debug("getting WoUser instance with id: " + id);
        try {
            WoUser instance = entityManager.find(
                    WoUser.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public WoUser autenticate(String userName, String password) {
        WoUser woUser = (WoUser) getSession().createCriteria(WoUser.class)
                .add(Restrictions.eq("userName", userName))
                .add(Restrictions.eq("password", password))
                .uniqueResult();
        return woUser;

    }

    public WoUser findUserByUsername(String userName) {
        WoUser woUser = (WoUser) getSession().createCriteria(WoUser.class)
                .add(Restrictions.eq("userName", userName))
                .uniqueResult();
        return woUser;

    }
}

