package rs.invado.wo.dao.uz;

// Generated Jan 1, 2013 5:03:17 PM by Hibernate Tools 3.4.0.CR1


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.uz.UzStanjeZalihaProizvoda;
import rs.invado.wo.domain.uz.UzStanjeZalihaProizvodaId;

/**
 * Home object for domain model class UzStanjeZalihaProizvoda.
 * @see rs.invado.wo.domain.uz.UzStanjeZalihaProizvoda
 * @author Hibernate Tools
 */
@Repository     @Transactional
//
public class UzStanjeZalihaProizvodaHome {

	private static final Log log = LogFactory
			.getLog(UzStanjeZalihaProizvodaHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(UzStanjeZalihaProizvoda transientInstance) {
		log.debug("persisting UzStanjeZalihaProizvoda instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(UzStanjeZalihaProizvoda persistentInstance) {
		log.debug("removing UzStanjeZalihaProizvoda instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public UzStanjeZalihaProizvoda merge(
			UzStanjeZalihaProizvoda detachedInstance) {
		log.debug("merging UzStanjeZalihaProizvoda instance");
		try {
			UzStanjeZalihaProizvoda result = entityManager
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public UzStanjeZalihaProizvoda findById(UzStanjeZalihaProizvodaId id) {
		log.debug("getting UzStanjeZalihaProizvoda instance with id: " + id);
		try {
			UzStanjeZalihaProizvoda instance = entityManager.find(
					UzStanjeZalihaProizvoda.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
