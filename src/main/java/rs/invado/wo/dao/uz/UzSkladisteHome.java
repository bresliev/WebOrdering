package rs.invado.wo.dao.uz;

// Generated Jan 1, 2013 5:03:17 PM by Hibernate Tools 3.4.0.CR1


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.uz.UzSkladiste;

/**
 * Home object for domain model class UzSkladiste.
 * @see rs.invado.wo.domain.uz.UzSkladiste
 * @author Hibernate Tools
 */
@Repository
@Transactional
//
public class UzSkladisteHome {

	private static final Log log = LogFactory.getLog(UzSkladisteHome.class);

	@PersistenceContext
	private EntityManager entityManager;
    
    private Session getSession(){
        return entityManager.unwrap(Session.class);
    }

	public void persist(UzSkladiste transientInstance) {
		log.debug("persisting UzSkladiste instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(UzSkladiste persistentInstance) {
		log.debug("removing UzSkladiste instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public UzSkladiste merge(UzSkladiste detachedInstance) {
		log.debug("merging UzSkladiste instance");
		try {
			UzSkladiste result = entityManager.merge(detachedInstance);
            entityManager.flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public UzSkladiste findById(int id) {
		log.debug("getting UzSkladiste instance with id: " + id);
		try {
			UzSkladiste instance = entityManager.find(UzSkladiste.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
    
    public UzSkladiste findByOjAndPurpose(Integer Oj, int namena){
        Session s = getSession();
        return (UzSkladiste)s.createCriteria(UzSkladiste.class)
                .add(Restrictions.eq("organizacionaJedinicaJe", Oj))
                .add(Restrictions.eq("idNameneSkladista", namena))
                .uniqueResult();
    }
}
