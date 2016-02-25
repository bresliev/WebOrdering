package rs.invado.wo.dao.wo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.prod.ProdCenovnik;
import rs.invado.wo.domain.prod.ProdCenovnikId;
import rs.invado.wo.domain.wo.WoKompanijaKorisnik;
import rs.invado.wo.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikola
 * Date: 20.12.12.
 * Time: 20.29
 * To change this template use File | Settings | File Templates.
 */
@Repository
@Transactional
//
public class WoKompanijaKorisnikHome {

    private static final Log log = LogFactory.getLog(WoKompanijaKorisnikHome.class);

    @PersistenceContext
    private EntityManager entityManager;
    
    private Session getSession(){
        return entityManager.unwrap(Session.class);
    }

    public void persist(WoKompanijaKorisnik transientInstance) {
        log.debug("persisting WoKompanijaKorisnik instance");
        try {
            entityManager.persist(transientInstance);  entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(WoKompanijaKorisnik persistentInstance) {
        log.debug("removing ProdCenovnik instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public WoKompanijaKorisnik merge(WoKompanijaKorisnik detachedInstance) {
        log.debug("merging ProdCenovnik instance");
        try {
            WoKompanijaKorisnik result = entityManager.merge(detachedInstance);
            entityManager.flush();
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public WoKompanijaKorisnik findById(BigInteger id) {
        log.debug("getting WoKompanijaKorisnik instance with id: " + id);
        try {
            WoKompanijaKorisnik instance = entityManager.find(WoKompanijaKorisnik.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    public WoKompanijaKorisnik findByCoresponingOJ(int correspondingOJ){
       WoKompanijaKorisnik woKompanijaKorisnik = (WoKompanijaKorisnik) getSession().createCriteria(WoKompanijaKorisnik.class)
                .add(Restrictions.eq("correspondingOJ", correspondingOJ)).uniqueResult();
        return woKompanijaKorisnik;
    }
    
    public List<WoKompanijaKorisnik> findAllCompanies(){
        return getSession().createCriteria(WoKompanijaKorisnik.class).list();
    }
}
