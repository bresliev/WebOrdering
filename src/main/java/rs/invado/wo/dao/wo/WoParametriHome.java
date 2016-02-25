package rs.invado.wo.dao.wo;

// Generated Dec 11, 2012 10:59:44 PM by Hibernate Tools 3.4.0.CR1

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.wo.WoKompanijaKorisnik;
import rs.invado.wo.domain.wo.WoParametri;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;

/**
 * Home object for domain model class WoParametri.
 * @see rs.invado.wo.domain.wo.WoParametri
 * @author Hibernate Tools
 */


@Repository
@Transactional
//
public class WoParametriHome {

	private static final Log log = LogFactory.getLog(WoParametriHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(WoParametri transientInstance) {
		log.debug("persisting WoParametri instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(WoParametri persistentInstance) {
		log.debug("removing WoParametri instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public WoParametri merge(WoParametri detachedInstance) {
		log.debug("merging WoParametri instance");
		try {
			WoParametri result = entityManager.merge(detachedInstance);
            entityManager.flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public WoParametri findById(Integer id) {
		log.debug("getting WoParametri instance with id: " + id);
		try {
			WoParametri instance = entityManager.find(WoParametri.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
    
    public WoParametri findActualSetOfParameters(){
        Session session = entityManager.unwrap(Session.class);
        WoParametri parametri = (WoParametri) session.createCriteria(WoParametri.class)
                .add(Restrictions.isNull("datumDo")).uniqueResult();
        return parametri;
    }

    public WoParametri findActualSetOfParametersPerCompany(WoKompanijaKorisnik woKompanijaKorisnik){
        Session session = entityManager.unwrap(Session.class);
        WoParametri parametri = (WoParametri) session.createCriteria(WoParametri.class)
                .add(Restrictions.isNull("datumDo"))
                .add(Restrictions.eq("woKompanijaKorisnik", woKompanijaKorisnik))
                .uniqueResult();
        parametri.getWoKompanijaKorisnik().getCorrespondingOJ();
        return parametri;
    }
}
