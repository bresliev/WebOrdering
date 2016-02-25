package rs.invado.wo.dao.uz;

// Generated Jan 1, 2013 5:03:17 PM by Hibernate Tools 3.4.0.CR1


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.uz.UzOperacijeNaDokumentu;
import rs.invado.wo.domain.uz.UzOperacijeNaDokumentuId;

/**
 * Home object for domain model class UzOperacijeNaDokumentu.
 * @see rs.invado.wo.domain.uz.UzOperacijeNaDokumentu
 * @author Hibernate Tools
 */
@Repository     @Transactional
//
public class UzOperacijeNaDokumentuHome {

	private static final Log log = LogFactory
			.getLog(UzOperacijeNaDokumentuHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(UzOperacijeNaDokumentu transientInstance) {
		log.debug("persisting UzOperacijeNaDokumentu instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(UzOperacijeNaDokumentu persistentInstance) {
		log.debug("removing UzOperacijeNaDokumentu instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public UzOperacijeNaDokumentu merge(UzOperacijeNaDokumentu detachedInstance) {
		log.debug("merging UzOperacijeNaDokumentu instance");
		try {
			UzOperacijeNaDokumentu result = entityManager
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public UzOperacijeNaDokumentu findById(UzOperacijeNaDokumentuId id) {
		log.debug("getting UzOperacijeNaDokumentu instance with id: " + id);
		try {
			UzOperacijeNaDokumentu instance = entityManager.find(
					UzOperacijeNaDokumentu.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
