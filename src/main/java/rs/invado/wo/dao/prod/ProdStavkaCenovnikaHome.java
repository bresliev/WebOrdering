package rs.invado.wo.dao.prod;

// Generated Dec 11, 2012 10:40:41 PM by Hibernate Tools 3.4.0.CR1

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.prod.ProdStavkaCenovnika;
import rs.invado.wo.domain.prod.ProdStavkaCenovnikaId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Home object for domain model class ProdStavkaCenovnika.
 * @see rs.invado.wo.domain.prod.ProdStavkaCenovnika
 * @author Hibernate Tools
 */
@Repository     @Transactional
//
public class ProdStavkaCenovnikaHome {

	private static final Log log = LogFactory
			.getLog(ProdStavkaCenovnikaHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(ProdStavkaCenovnika transientInstance) {
		log.debug("persisting ProdStavkaCenovnika instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(ProdStavkaCenovnika persistentInstance) {
		log.debug("removing ProdStavkaCenovnika instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public ProdStavkaCenovnika merge(ProdStavkaCenovnika detachedInstance) {
		log.debug("merging ProdStavkaCenovnika instance");
		try {
			ProdStavkaCenovnika result = entityManager.merge(detachedInstance);
            entityManager.flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ProdStavkaCenovnika findById(ProdStavkaCenovnikaId id) {
		log.debug("getting ProdStavkaCenovnika instance with id: " + id);
		try {
			ProdStavkaCenovnika instance = entityManager.find(
					ProdStavkaCenovnika.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
