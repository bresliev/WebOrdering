package rs.invado.wo.dao.ocp;

// Generated Dec 9, 2012 5:42:26 PM by Hibernate Tools 3.4.0.CR1


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.ocp.OcpValuta;
import rs.invado.wo.domain.wo.WoPartnerSetting;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Home object for domain model class OcpValuta.
 * @see rs.invado.wo.dao.ocp.OcpValutaHome
 * @author Hibernate Tools
 */
@Repository
@Transactional
//
public class OcpValutaHome {

	private static final Log log = LogFactory.getLog(OcpValutaHome.class);

	@PersistenceContext
	private EntityManager entityManager;
    
    private Session getSession(){
        return entityManager.unwrap(Session.class);
    }

	public void persist(OcpValuta transientInstance) {
		log.debug("persisting OcpValuta instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(OcpValuta persistentInstance) {
		log.debug("removing OcpValuta instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public OcpValuta merge(OcpValuta detachedInstance) {
		log.debug("merging OcpValuta instance");
		try {
			OcpValuta result = entityManager.merge(detachedInstance);
            entityManager.flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public OcpValuta findById(short id) {
		log.debug("getting OcpValuta instance with id: " + id);
		try {
			OcpValuta instance = entityManager.find(OcpValuta.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

    public OcpValuta findByKlasa(WoPartnerSetting woPartnerSetting, Integer OJ) {

         return (OcpValuta)getSession().createCriteria(OcpValuta.class)
                .createCriteria("klaseZaValutu")
                .add(Restrictions.eq("id.idKlasaCene", woPartnerSetting.getIdKlasaCene()))
                 .add(Restrictions.eq("id.organizacionaJedinica", OJ))
                .uniqueResult();
        
    }
}
