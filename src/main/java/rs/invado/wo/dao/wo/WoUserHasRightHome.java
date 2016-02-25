package rs.invado.wo.dao.wo;

// Generated Mar 6, 2013 10:35:50 PM by Hibernate Tools 3.4.0.CR1


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.wo.WoUserHasRight;
import rs.invado.wo.domain.wo.WoUserHasRightId;

/**
 * Home object for domain model class WoUserHasRight.
 * @see rs.invado.wo.domain.wo.WoUserHasRight
 * @author Hibernate Tools
 */
@Repository
@Transactional
//
public class WoUserHasRightHome {

	private static final Log log = LogFactory.getLog(WoUserHasRightHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(WoUserHasRight transientInstance) {
		log.debug("persisting WoUserHasRight instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(WoUserHasRight persistentInstance) {
		log.debug("removing WoUserHasRight instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public WoUserHasRight merge(WoUserHasRight detachedInstance) {
		log.debug("merging WoUserHasRight instance");
		try {
			WoUserHasRight result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public WoUserHasRight findById(WoUserHasRightId id) {
		log.debug("getting WoUserHasRight instance with id: " + id);
		try {
			WoUserHasRight instance = entityManager.find(WoUserHasRight.class,
					id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
