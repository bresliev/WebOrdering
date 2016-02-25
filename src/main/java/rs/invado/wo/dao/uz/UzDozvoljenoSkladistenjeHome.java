package rs.invado.wo.dao.uz;

// Generated Jan 1, 2013 5:03:17 PM by Hibernate Tools 3.4.0.CR1


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.uz.UzDozvoljenoSkladistenje;
import rs.invado.wo.domain.uz.UzDozvoljenoSkladistenjeId;

/**
 * Home object for domain model class UzDozvoljenoSkladistenje.
 * @see rs.invado.wo.domain.uz.UzDozvoljenoSkladistenje
 * @author Hibernate Tools
 */
@Repository     @Transactional
//
public class UzDozvoljenoSkladistenjeHome {

	private static final Log log = LogFactory
			.getLog(UzDozvoljenoSkladistenjeHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(UzDozvoljenoSkladistenje transientInstance) {
		log.debug("persisting UzDozvoljenoSkladistenje instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(UzDozvoljenoSkladistenje persistentInstance) {
		log.debug("removing UzDozvoljenoSkladistenje instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public UzDozvoljenoSkladistenje merge(
			UzDozvoljenoSkladistenje detachedInstance) {
		log.debug("merging UzDozvoljenoSkladistenje instance");
		try {
			UzDozvoljenoSkladistenje result = entityManager
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public UzDozvoljenoSkladistenje findById(UzDozvoljenoSkladistenjeId id) {
		log.debug("getting UzDozvoljenoSkladistenje instance with id: " + id);
		try {
			UzDozvoljenoSkladistenje instance = entityManager.find(
					UzDozvoljenoSkladistenje.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
