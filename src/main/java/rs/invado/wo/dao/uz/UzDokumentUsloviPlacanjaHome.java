package rs.invado.wo.dao.uz;

// Generated Jan 1, 2013 5:03:17 PM by Hibernate Tools 3.4.0.CR1


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.uz.UzDokumentUsloviPlacanja;
import rs.invado.wo.domain.uz.UzDokumentUsloviPlacanjaId;

/**
 * Home object for domain model class UzDokumentUsloviPlacanja.
 * @see rs.invado.wo.domain.uz.UzDokumentUsloviPlacanja
 * @author Hibernate Tools
 */
@Repository     @Transactional
//
public class UzDokumentUsloviPlacanjaHome {

	private static final Log log = LogFactory
			.getLog(UzDokumentUsloviPlacanjaHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(UzDokumentUsloviPlacanja transientInstance) {
		log.debug("persisting UzDokumentUsloviPlacanja instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(UzDokumentUsloviPlacanja persistentInstance) {
		log.debug("removing UzDokumentUsloviPlacanja instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public UzDokumentUsloviPlacanja merge(
			UzDokumentUsloviPlacanja detachedInstance) {
		log.debug("merging UzDokumentUsloviPlacanja instance");
		try {
			UzDokumentUsloviPlacanja result = entityManager
					.merge(detachedInstance);
            entityManager.flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public UzDokumentUsloviPlacanja findById(UzDokumentUsloviPlacanjaId id) {
		log.debug("getting UzDokumentUsloviPlacanja instance with id: " + id);
		try {
			UzDokumentUsloviPlacanja instance = entityManager.find(
					UzDokumentUsloviPlacanja.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
