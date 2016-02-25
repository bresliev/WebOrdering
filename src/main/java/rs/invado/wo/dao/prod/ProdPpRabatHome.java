package rs.invado.wo.dao.prod;

// Generated Jan 7, 2013 9:06:10 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.prod.ProdPpRabat;

/**
 * Home object for domain model class ProdPpRabat.
 * @see rs.invado.wo.domain.prod.ProdPpRabat
 * @author Hibernate Tools
 */
@Repository
@Transactional
//
public class ProdPpRabatHome {

	private static final Log log = LogFactory.getLog(ProdPpRabatHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(ProdPpRabat transientInstance) {
		log.debug("persisting ProdPpRabat instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(ProdPpRabat persistentInstance) {
		log.debug("removing ProdPpRabat instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public ProdPpRabat merge(ProdPpRabat detachedInstance) {
		log.debug("merging ProdPpRabat instance");
		try {
			ProdPpRabat result = entityManager.merge(detachedInstance);
            entityManager.flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ProdPpRabat findById(int id) {
		log.debug("getting ProdPpRabat instance with id: " + id);
		try {
			ProdPpRabat instance = entityManager.find(ProdPpRabat.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
