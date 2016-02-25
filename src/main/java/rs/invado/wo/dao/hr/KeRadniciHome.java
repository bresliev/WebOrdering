package rs.invado.wo.dao.hr;

// Generated Jan 15, 2013 10:02:35 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.hr.KeRadnici;

/**
 * Home object for domain model class KeRadnici.
 * @see rs.invado.wo.domain.hr.KeRadnici
 * @author Hibernate Tools
 */
@Repository
@Transactional
public class KeRadniciHome {

	private static final Log log = LogFactory.getLog(KeRadniciHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(KeRadnici transientInstance) {
		log.debug("persisting KeRadnici instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(KeRadnici persistentInstance) {
		log.debug("removing KeRadnici instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public KeRadnici merge(KeRadnici detachedInstance) {
		log.debug("merging KeRadnici instance");
		try {
			KeRadnici result = entityManager.merge(detachedInstance);
            entityManager.flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public KeRadnici findById(String id) {
		log.debug("getting KeRadnici instance with id: " + id);
		try {
			KeRadnici instance = entityManager.find(KeRadnici.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
