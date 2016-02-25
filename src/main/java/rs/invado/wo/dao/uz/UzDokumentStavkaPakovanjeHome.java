package rs.invado.wo.dao.uz;

// Generated Jan 1, 2013 5:03:17 PM by Hibernate Tools 3.4.0.CR1


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.uz.UzDokumentStavkaPakovanje;
import rs.invado.wo.domain.uz.UzDokumentStavkaPakovanjeId;

/**
 * Home object for domain model class UzDokumentStavkaPakovanje.
 * @see rs.invado.wo.domain.uz.UzDokumentStavkaPakovanje
 * @author Hibernate Tools
 */
@Repository     @Transactional
//
public class UzDokumentStavkaPakovanjeHome {

	private static final Log log = LogFactory
			.getLog(UzDokumentStavkaPakovanjeHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(UzDokumentStavkaPakovanje transientInstance) {
		log.debug("persisting UzDokumentStavkaPakovanje instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(UzDokumentStavkaPakovanje persistentInstance) {
		log.debug("removing UzDokumentStavkaPakovanje instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public UzDokumentStavkaPakovanje merge(
			UzDokumentStavkaPakovanje detachedInstance) {
		log.debug("merging UzDokumentStavkaPakovanje instance");
		try {
			UzDokumentStavkaPakovanje result = entityManager
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public UzDokumentStavkaPakovanje findById(UzDokumentStavkaPakovanjeId id) {
		log.debug("getting UzDokumentStavkaPakovanje instance with id: " + id);
		try {
			UzDokumentStavkaPakovanje instance = entityManager.find(
					UzDokumentStavkaPakovanje.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
