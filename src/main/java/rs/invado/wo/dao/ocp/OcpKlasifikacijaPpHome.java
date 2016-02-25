package rs.invado.wo.dao.ocp;

// Generated Dec 9, 2012 5:42:26 PM by Hibernate Tools 3.4.0.CR1


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.ocp.OcpKlasifikacijaPp;
import rs.invado.wo.domain.ocp.OcpKlasifikacijaPpId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Home object for domain model class OcpKlasifikacijaPp.
 * @see rs.invado.wo.dao.ocp.OcpKlasifikacijaPpHome
 * @author Hibernate Tools
 */
@Repository
@Transactional
//
public class OcpKlasifikacijaPpHome {

	private static final Log log = LogFactory
			.getLog(OcpKlasifikacijaPpHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(OcpKlasifikacijaPp transientInstance) {
		log.debug("persisting OcpKlasifikacijaPp instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(OcpKlasifikacijaPp persistentInstance) {
		log.debug("removing OcpKlasifikacijaPp instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public OcpKlasifikacijaPp merge(OcpKlasifikacijaPp detachedInstance) {
		log.debug("merging OcpKlasifikacijaPp instance");
		try {
			OcpKlasifikacijaPp result = entityManager.merge(detachedInstance);
            entityManager.flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public OcpKlasifikacijaPp findById(OcpKlasifikacijaPpId id) {
		log.debug("getting OcpKlasifikacijaPp instance with id: " + id);
		try {
			OcpKlasifikacijaPp instance = entityManager.find(
					OcpKlasifikacijaPp.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
