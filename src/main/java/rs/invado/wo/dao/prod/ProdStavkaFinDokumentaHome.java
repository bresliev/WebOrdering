package rs.invado.wo.dao.prod;

// Generated Jan 20, 2013 11:37:50 PM by Hibernate Tools 3.4.0.CR1


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.prod.ProdStavkaFinDokumenta;
import rs.invado.wo.domain.prod.ProdStavkaFinDokumentaId;

/**
 * Home object for domain model class ProdStavkaFinDokumenta.
 * @see rs.invado.wo.domain.prod.ProdStavkaFinDokumenta
 * @author Hibernate Tools
 */
@Repository     @Transactional
//
public class ProdStavkaFinDokumentaHome {

	private static final Log log = LogFactory
			.getLog(ProdStavkaFinDokumentaHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(ProdStavkaFinDokumenta transientInstance) {
		log.debug("persisting ProdStavkaFinDokumenta instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(ProdStavkaFinDokumenta persistentInstance) {
		log.debug("removing ProdStavkaFinDokumenta instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public ProdStavkaFinDokumenta merge(ProdStavkaFinDokumenta detachedInstance) {
		log.debug("merging ProdStavkaFinDokumenta instance");
		try {
			ProdStavkaFinDokumenta result = entityManager
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ProdStavkaFinDokumenta findById(ProdStavkaFinDokumentaId id) {
		log.debug("getting ProdStavkaFinDokumenta instance with id: " + id);
		try {
			ProdStavkaFinDokumenta instance = entityManager.find(
					ProdStavkaFinDokumenta.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
