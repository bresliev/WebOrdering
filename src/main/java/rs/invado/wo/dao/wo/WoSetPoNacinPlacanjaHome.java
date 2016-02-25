package rs.invado.wo.dao.wo;

// Generated Jan 19, 2013 2:16:09 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.prod.ProdNacinPlacanja;
import rs.invado.wo.domain.wo.WoParametri;
import rs.invado.wo.domain.wo.WoSetPoNacinPlacanja;

/**
 * Home object for domain model class WoSetPoNacinPlacanja.
 * @see rs.invado.wo.domain.wo.WoSetPoNacinPlacanja
 * @author Hibernate Tools
 */
@Repository
@Transactional
//
public class WoSetPoNacinPlacanjaHome {

	private static final Log log = LogFactory
			.getLog(WoSetPoNacinPlacanjaHome.class);

	@PersistenceContext
	private EntityManager entityManager;
    
    private Session getSession(){
        return entityManager.unwrap(Session.class);
    }

	public void persist(WoSetPoNacinPlacanja transientInstance) {
		log.debug("persisting WoSetPoNacinPlacanja instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(WoSetPoNacinPlacanja persistentInstance) {
		log.debug("removing WoSetPoNacinPlacanja instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public WoSetPoNacinPlacanja merge(WoSetPoNacinPlacanja detachedInstance) {
		log.debug("merging WoSetPoNacinPlacanja instance");
		try {
			WoSetPoNacinPlacanja result = entityManager.merge(detachedInstance);
            entityManager.flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public WoSetPoNacinPlacanja findById(BigDecimal id) {
		log.debug("getting WoSetPoNacinPlacanja instance with id: " + id);
		try {
			WoSetPoNacinPlacanja instance = entityManager.find(
					WoSetPoNacinPlacanja.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
    
    public WoSetPoNacinPlacanja findByNacinPlacanjaAndParameters(WoParametri parametri, ProdNacinPlacanja prodNacinPlacanja){
        
        return (WoSetPoNacinPlacanja) getSession().createCriteria(WoSetPoNacinPlacanja.class)
                .add(Restrictions.eq("parametri", parametri))
                .add(Restrictions.eq("prodNacinPlacanja", prodNacinPlacanja))
                .uniqueResult();
    }
}
