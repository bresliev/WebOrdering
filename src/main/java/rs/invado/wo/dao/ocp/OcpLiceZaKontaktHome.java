package rs.invado.wo.dao.ocp;

// Generated Dec 9, 2012 5:42:26 PM by Hibernate Tools 3.4.0.CR1


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.ocp.OcpLiceZaKontakt;
import rs.invado.wo.domain.ocp.OcpLiceZaKontaktId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Home object for domain model class OcpLiceZaKontakt.
 * @see rs.invado.wo.dao.ocp.OcpLiceZaKontaktHome
 * @author Hibernate Tools
 */
@Repository     @Transactional
//
public class OcpLiceZaKontaktHome {

	private static final Log log = LogFactory
			.getLog(OcpLiceZaKontaktHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(OcpLiceZaKontakt transientInstance) {
		log.debug("persisting OcpLiceZaKontakt instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(OcpLiceZaKontakt persistentInstance) {
		log.debug("removing OcpLiceZaKontakt instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public OcpLiceZaKontakt merge(OcpLiceZaKontakt detachedInstance) {
		log.debug("merging OcpLiceZaKontakt instance");
		try {
			OcpLiceZaKontakt result = entityManager.merge(detachedInstance);
            entityManager.flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public OcpLiceZaKontakt findById(OcpLiceZaKontaktId id) {
		log.debug("getting OcpLiceZaKontakt instance with id: " + id);
		try {
			OcpLiceZaKontakt instance = entityManager.find(
					OcpLiceZaKontakt.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
