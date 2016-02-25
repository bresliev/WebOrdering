package rs.invado.wo.dao.ocp;

// Generated Dec 9, 2012 5:42:26 PM by Hibernate Tools 3.4.0.CR1


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.ocp.OcpMesto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Home object for domain model class OcpMesto.
 * @see rs.invado.wo.dao.ocp.OcpMestoHome
 * @author Hibernate Tools
 */
@Repository     @Transactional
//
public class OcpMestoHome {

	private static final Log log = LogFactory.getLog(OcpMestoHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(OcpMesto transientInstance) {
		log.debug("persisting OcpMesto instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(OcpMesto persistentInstance) {
		log.debug("removing OcpMesto instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public OcpMesto merge(OcpMesto detachedInstance) {
		log.debug("merging OcpMesto instance");
		try {
			OcpMesto result = entityManager.merge(detachedInstance);
            entityManager.flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public OcpMesto findById(int id) {
		log.debug("getting OcpMesto instance with id: " + id);
		try {
			OcpMesto instance = entityManager.find(OcpMesto.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
