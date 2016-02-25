package rs.invado.wo.dao.prod;

// Generated Jan 7, 2013 9:06:10 PM by Hibernate Tools 3.4.0.CR1


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.prod.ProdPpRabatStavka;
import rs.invado.wo.domain.prod.ProdPpRabatStavkaId;

/**
 * Home object for domain model class ProdPpRabatStavka.
 * @see rs.invado.wo.domain.prod.ProdPpRabatStavka
 * @author Hibernate Tools
 */
@Repository
@Transactional
//
public class ProdPpRabatStavkaHome {

	private static final Log log = LogFactory
			.getLog(ProdPpRabatStavkaHome.class);

	@PersistenceContext
	private EntityManager entityManager;
    
    private Session getSession(){
        return entityManager.unwrap(Session.class);
    }

	public void persist(ProdPpRabatStavka transientInstance) {
		log.debug("persisting ProdPpRabatStavka instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(ProdPpRabatStavka persistentInstance) {
		log.debug("removing ProdPpRabatStavka instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public ProdPpRabatStavka merge(ProdPpRabatStavka detachedInstance) {
		log.debug("merging ProdPpRabatStavka instance");
		try {
			ProdPpRabatStavka result = entityManager.merge(detachedInstance);
            entityManager.flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ProdPpRabatStavka findById(ProdPpRabatStavkaId id) {
		log.debug("getting ProdPpRabatStavka instance with id: " + id);
		try {
			ProdPpRabatStavka instance = entityManager.find(
					ProdPpRabatStavka.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

    public Object findRabatZaProizvod(Integer proizvodId, int poslovniPartnerId, int oj){
        return getSession().getNamedQuery("findRabatZaProizvod")
                .setParameter("idProizvoda", proizvodId)
                .setParameter("poslovniPartner", poslovniPartnerId)
                .setParameter("oj", oj)
                .list();
    }
}
