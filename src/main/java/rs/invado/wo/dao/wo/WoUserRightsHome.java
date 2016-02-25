package rs.invado.wo.dao.wo;

// Generated Mar 6, 2013 10:35:50 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.wo.WoUserRights;

/**
 * Home object for domain model class WoUserRights.
 * @see rs.invado.wo.domain.wo.WoUserRights
 * @author Hibernate Tools
 */
@Repository
@Transactional
public class WoUserRightsHome {

	private static final Log log = LogFactory.getLog(WoUserRightsHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(WoUserRights transientInstance) {
		log.debug("persisting WoUserRights instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(WoUserRights persistentInstance) {
		log.debug("removing WoUserRights instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public WoUserRights merge(WoUserRights detachedInstance) {
		log.debug("merging WoUserRights instance");
		try {
			WoUserRights result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public WoUserRights findById(Integer id) {
		log.debug("getting WoUserRights instance with id: " + id);
		try {
			WoUserRights instance = entityManager.find(WoUserRights.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
