package rs.invado.wo.dao.wo;

// Generated Dec 11, 2012 10:59:44 PM by Hibernate Tools 3.4.0.CR1

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.wo.WoRezervacija;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

/**
 * Home object for domain model class WoRezervacija.
 * @see rs.invado.wo.domain.wo.WoRezervacija
 * @author Hibernate Tools
 */
@Repository
@Transactional
//
public class WoRezervacijaHome {

	private static final Log log = LogFactory.getLog(WoRezervacijaHome.class);

	@PersistenceContext
	private EntityManager entityManager;

    public Session getSession(){
        return entityManager.unwrap(Session.class);
    }
        
	public void persist(WoRezervacija transientInstance) {
		log.debug("persisting WoRezervacija instance");
      	try {
			entityManager.persist(transientInstance);
            entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(WoRezervacija persistentInstance) {
		log.debug("removing WoRezervacija instance");

		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public WoRezervacija merge(WoRezervacija detachedInstance) {
		log.debug("merging WoRezervacija instance");
		try {
			WoRezervacija result = entityManager.merge(detachedInstance);
            entityManager.flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public WoRezervacija findById(BigDecimal id) {
		log.debug("getting WoRezervacija instance with id: " + id);
		try {
			WoRezervacija instance = entityManager
					.find(WoRezervacija.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
    
    public List<WoRezervacija> findBySessionIdAndDefaultStatus(String sessionId){
        return  getSession().createCriteria(WoRezervacija.class)
                .add(Restrictions.eq("sessionid", sessionId))
                .add(Restrictions.eq("statusRezervacije", 1))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    public List<WoRezervacija> findBySessionId(String sessionId){
        return  getSession().createCriteria(WoRezervacija.class)
                .add(Restrictions.eq("sessionid", sessionId))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }
    
}
