package rs.invado.wo.dao.prod;

// Generated Jan 19, 2013 4:01:41 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.prod.ProdNacinPlacanja;

/**
 * Home object for domain model class ProdNacinPlacanja.
 * @see rs.invado.wo.domain.prod.ProdNacinPlacanja
 * @author Hibernate Tools
 */
@Repository     @Transactional
//
public class ProdNacinPlacanjaHome {

	private static final Log log = LogFactory
			.getLog(ProdNacinPlacanjaHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(ProdNacinPlacanja transientInstance) {
		log.debug("persisting ProdNacinPlacanja instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(ProdNacinPlacanja persistentInstance) {
		log.debug("removing ProdNacinPlacanja instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public ProdNacinPlacanja merge(ProdNacinPlacanja detachedInstance) {
		log.debug("merging ProdNacinPlacanja instance");
		try {
			ProdNacinPlacanja result = entityManager.merge(detachedInstance);
            entityManager.flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ProdNacinPlacanja findById(String id) {
		log.debug("getting ProdNacinPlacanja instance with id: " + id);
		try {
			ProdNacinPlacanja instance = entityManager.find(
					ProdNacinPlacanja.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
