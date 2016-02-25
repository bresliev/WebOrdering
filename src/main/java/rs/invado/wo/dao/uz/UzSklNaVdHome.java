package rs.invado.wo.dao.uz;

// Generated Jan 1, 2013 5:03:17 PM by Hibernate Tools 3.4.0.CR1


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.uz.UzSklNaVd;
import rs.invado.wo.domain.uz.UzSklNaVdId;

/**
 * Home object for domain model class UzSklNaVd.
 * @see rs.invado.wo.domain.uz.UzSklNaVd
 * @author Hibernate Tools
 */
@Repository     @Transactional
//
public class UzSklNaVdHome {

	private static final Log log = LogFactory.getLog(UzSklNaVdHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(UzSklNaVd transientInstance) {
		log.debug("persisting UzSklNaVd instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(UzSklNaVd persistentInstance) {
		log.debug("removing UzSklNaVd instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public UzSklNaVd merge(UzSklNaVd detachedInstance) {
		log.debug("merging UzSklNaVd instance");
		try {
			UzSklNaVd result = entityManager.merge(detachedInstance);
            entityManager.flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public UzSklNaVd findById(UzSklNaVdId id) {
		log.debug("getting UzSklNaVd instance with id: " + id);
		try {
			UzSklNaVd instance = entityManager.find(UzSklNaVd.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
