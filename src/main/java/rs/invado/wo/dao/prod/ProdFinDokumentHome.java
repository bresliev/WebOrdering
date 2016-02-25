package rs.invado.wo.dao.prod;

// Generated Jan 20, 2013 11:37:50 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.prod.ProdFinDokument;
import rs.invado.wo.domain.prod.ProdFinDokumentId;

/**
 * Home object for domain model class ProdFinDokument.
 * @see rs.invado.wo.domain.prod.ProdFinDokument
 * @author Hibernate Tools
 */
@Repository     @Transactional
//
public class ProdFinDokumentHome {

	private static final Log log = LogFactory.getLog(ProdFinDokumentHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(ProdFinDokument transientInstance) {
		log.debug("persisting ProdFinDokument instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(ProdFinDokument persistentInstance) {
		log.debug("removing ProdFinDokument instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public ProdFinDokument merge(ProdFinDokument detachedInstance) {
		log.debug("merging ProdFinDokument instance");
		try {
			ProdFinDokument result = entityManager.merge(detachedInstance);
            entityManager.flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ProdFinDokument findById(ProdFinDokumentId id) {
		log.debug("getting ProdFinDokument instance with id: " + id);
		try {
			ProdFinDokument instance = entityManager.find(
					ProdFinDokument.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

    private Session getSession(){
        return entityManager.unwrap(Session.class);
    }
    public ProdFinDokument insertFinDOkumentByStoredProcedure(Integer idVd, String idSklDokumenta ){

        return (ProdFinDokument) getSession().getNamedQuery("insertFinDokument")
                .setParameter("vdotp", idVd)
                .setParameter("idotp", idSklDokumenta)
                .uniqueResult();
    }
}
