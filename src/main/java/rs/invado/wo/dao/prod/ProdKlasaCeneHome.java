package rs.invado.wo.dao.prod;

// Generated Jan 20, 2013 9:55:29 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.prod.ProdKlasaCene;
import rs.invado.wo.domain.prod.ProdKlasaCeneId;

/**
 * Home object for domain model class ProdKlasaCene.
 * @see rs.invado.wo.domain.prod.ProdKlasaCene
 * @author Hibernate Tools
 */
 @Repository     @Transactional
 //
public class ProdKlasaCeneHome {

	private static final Log log = LogFactory.getLog(ProdKlasaCeneHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(ProdKlasaCene transientInstance) {
		log.debug("persisting ProdKlasaCene instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(ProdKlasaCene persistentInstance) {
		log.debug("removing ProdKlasaCene instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public ProdKlasaCene merge(ProdKlasaCene detachedInstance) {
		log.debug("merging ProdKlasaCene instance");
		try {
			ProdKlasaCene result = entityManager.merge(detachedInstance);
            entityManager.flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ProdKlasaCene findById(ProdKlasaCeneId id) {
		log.debug("getting ProdKlasaCene instance with id: " + id);
		try {
			ProdKlasaCene instance = entityManager
					.find(ProdKlasaCene.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
