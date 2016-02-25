package rs.invado.wo.dao.wo;

// Generated Dec 11, 2012 10:59:44 PM by Hibernate Tools 3.4.0.CR1

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.wo.WoAutentifikacija;
import rs.invado.wo.domain.wo.WoAutentifikacijaId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Home object for domain model class WoAutentifikacija.
 * @see rs.invado.wo.domain.wo.WoAutentifikacija
 * @author Hibernate Tools
 */
@Repository     @Transactional
//
public class WoAutentifikacijaHome {

	private static final Log log = LogFactory
			.getLog(WoAutentifikacijaHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(WoAutentifikacija transientInstance) {
		log.debug("persisting WoAutentifikacija instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(WoAutentifikacija persistentInstance) {
		log.debug("removing WoAutentifikacija instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public WoAutentifikacija merge(WoAutentifikacija detachedInstance) {
		log.debug("merging WoAutentifikacija instance");
		try {
			WoAutentifikacija result = entityManager.merge(detachedInstance);
            entityManager.flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public WoAutentifikacija findById(WoAutentifikacijaId id) {
		log.debug("getting WoAutentifikacija instance with id: " + id);
		try {
			WoAutentifikacija instance = entityManager.find(
					WoAutentifikacija.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
