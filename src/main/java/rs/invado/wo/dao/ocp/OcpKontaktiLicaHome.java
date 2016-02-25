package rs.invado.wo.dao.ocp;

// Generated Dec 9, 2012 5:42:26 PM by Hibernate Tools 3.4.0.CR1


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.ocp.OcpKontaktiLica;
import rs.invado.wo.domain.ocp.OcpKontaktiLicaId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Home object for domain model class OcpKontaktiLica.
 * @see rs.invado.wo.dao.ocp.OcpKontaktiLicaHome
 * @author Hibernate Tools
 */
@Repository     @Transactional
//
public class OcpKontaktiLicaHome {

	private static final Log log = LogFactory.getLog(OcpKontaktiLicaHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(OcpKontaktiLica transientInstance) {
		log.debug("persisting OcpKontaktiLica instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(OcpKontaktiLica persistentInstance) {
		log.debug("removing OcpKontaktiLica instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public OcpKontaktiLica merge(OcpKontaktiLica detachedInstance) {
		log.debug("merging OcpKontaktiLica instance");
		try {
			OcpKontaktiLica result = entityManager.merge(detachedInstance);
            entityManager.flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public OcpKontaktiLica findById(OcpKontaktiLicaId id) {
		log.debug("getting OcpKontaktiLica instance with id: " + id);
		try {
			OcpKontaktiLica instance = entityManager.find(
					OcpKontaktiLica.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
