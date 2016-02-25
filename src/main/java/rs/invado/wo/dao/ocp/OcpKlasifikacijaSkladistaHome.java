package rs.invado.wo.dao.ocp;

// Generated Dec 9, 2012 5:42:26 PM by Hibernate Tools 3.4.0.CR1


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.ocp.OcpKlasifikacijaSkladista;
import rs.invado.wo.domain.ocp.OcpKlasifikacijaSkladistaId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Home object for domain model class OcpKlasifikacijaSkladista.
 * @see rs.invado.wo.dao.ocp.OcpKlasifikacijaSkladistaHome
 * @author Hibernate Tools
 */
@Repository     @Transactional
//
public class OcpKlasifikacijaSkladistaHome {

	private static final Log log = LogFactory
			.getLog(OcpKlasifikacijaSkladistaHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(OcpKlasifikacijaSkladista transientInstance) {
		log.debug("persisting OcpKlasifikacijaSkladista instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(OcpKlasifikacijaSkladista persistentInstance) {
		log.debug("removing OcpKlasifikacijaSkladista instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public OcpKlasifikacijaSkladista merge(
			OcpKlasifikacijaSkladista detachedInstance) {
		log.debug("merging OcpKlasifikacijaSkladista instance");
		try {
			OcpKlasifikacijaSkladista result = entityManager
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public OcpKlasifikacijaSkladista findById(OcpKlasifikacijaSkladistaId id) {
		log.debug("getting OcpKlasifikacijaSkladista instance with id: " + id);
		try {
			OcpKlasifikacijaSkladista instance = entityManager.find(
					OcpKlasifikacijaSkladista.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
