package rs.invado.wo.dao.ocp;

// Generated Dec 9, 2012 5:42:26 PM by Hibernate Tools 3.4.0.CR1


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.ocp.OcpJedinicaMere;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Home object for domain model class OcpJedinicaMere.
 * @see rs.invado.wo.dao.ocp.OcpJedinicaMereHome
 * @author Hibernate Tools
 */
@Repository
@Transactional
//
public class OcpJedinicaMereHome {

	private static final Log log = LogFactory.getLog(OcpJedinicaMereHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(OcpJedinicaMere transientInstance) {
		log.debug("persisting OcpJedinicaMere instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(OcpJedinicaMere persistentInstance) {
		log.debug("removing OcpJedinicaMere instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public OcpJedinicaMere merge(OcpJedinicaMere detachedInstance) {
		log.debug("merging OcpJedinicaMere instance");
		try {
			OcpJedinicaMere result = entityManager.merge(detachedInstance);
            entityManager.flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public OcpJedinicaMere findById(int id) {
		log.debug("getting OcpJedinicaMere instance with id: " + id);
		try {
			OcpJedinicaMere instance = entityManager.find(
					OcpJedinicaMere.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
