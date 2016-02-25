package rs.invado.wo.dao.ocp;

// Generated Dec 9, 2012 5:42:26 PM by Hibernate Tools 3.4.0.CR1


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.ocp.OcpAtribut;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Home object for domain model class OcpAtribut.
 * @see rs.invado.wo.dao.ocp.OcpAtributHome
 * @author Hibernate Tools
 */
@Repository
@Transactional
public class OcpAtributHome {

	private static final Log log = LogFactory.getLog(OcpAtributHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(OcpAtribut transientInstance) {
		log.debug("persisting OcpAtribut instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(OcpAtribut persistentInstance) {
		log.debug("removing OcpAtribut instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public OcpAtribut merge(OcpAtribut detachedInstance) {
		log.debug("merging OcpAtribut instance");
		try {
			OcpAtribut result = entityManager.merge(detachedInstance);
            entityManager.flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public OcpAtribut findById(int id) {
		log.debug("getting OcpAtribut instance with id: " + id);
		try {
			OcpAtribut instance = entityManager.find(OcpAtribut.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
