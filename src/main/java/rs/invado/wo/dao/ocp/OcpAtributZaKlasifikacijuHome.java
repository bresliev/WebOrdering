package rs.invado.wo.dao.ocp;

// Generated Dec 9, 2012 5:42:26 PM by Hibernate Tools 3.4.0.CR1


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.ocp.OcpAtributZaKlasifikaciju;
import rs.invado.wo.domain.ocp.OcpAtributZaKlasifikacijuId;

/**
 * Home object for domain model class OcpAtributZaKlasifikaciju.
 * @see rs.invado.wo.dao.ocp.OcpAtributZaKlasifikaciju
 * @author Hibernate Tools
 */
@Repository
@Transactional
//
public class OcpAtributZaKlasifikacijuHome {

	private static final Log log = LogFactory
			.getLog(OcpAtributZaKlasifikacijuHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(OcpAtributZaKlasifikaciju transientInstance) {
		log.debug("persisting OcpAtributZaKlasifikaciju instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(OcpAtributZaKlasifikaciju persistentInstance) {
		log.debug("removing OcpAtributZaKlasifikaciju instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public OcpAtributZaKlasifikaciju merge(
			OcpAtributZaKlasifikaciju detachedInstance) {
		log.debug("merging OcpAtributZaKlasifikaciju instance");
		try {
			OcpAtributZaKlasifikaciju result = entityManager
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public OcpAtributZaKlasifikaciju findById(OcpAtributZaKlasifikacijuId id) {
		log.debug("getting OcpAtributZaKlasifikaciju instance with id: " + id);
		try {
			OcpAtributZaKlasifikaciju instance = entityManager.find(
					OcpAtributZaKlasifikaciju.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
