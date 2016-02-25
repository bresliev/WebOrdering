package rs.invado.wo.dao.wo;

// Generated Dec 11, 2012 10:59:44 PM by Hibernate Tools 3.4.0.CR1

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.wo.WoKompanijaKorisnik;
import rs.invado.wo.domain.wo.WoPartnerSetting;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

/**
 * Home object for domain model class WoPartnerSetting.
 * @see rs.invado.wo.domain.wo.WoPartnerSetting
 * @author Hibernate Tools
 */

@Repository
@Transactional
//
public class WoPartnerSettingsHome {

	private static final Log log = LogFactory
			.getLog(WoPartnerSettingsHome.class);

	@PersistenceContext
	private EntityManager entityManager;

    public Session getSession() {
        Session session = entityManager.unwrap(Session.class);
        return session;
    }

	public void persist(WoPartnerSetting transientInstance) {
		log.debug("persisting WoPartnerSetting instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(WoPartnerSetting persistentInstance) {
		log.debug("removing WoPartnerSetting instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public WoPartnerSetting merge(
			WoPartnerSetting detachedInstance) {
		log.debug("merging WoPartnerSetting instance");
		try {
			WoPartnerSetting result = entityManager
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public WoPartnerSetting findById(BigDecimal id) {
		log.debug("getting WoPartnerSetting instance with id: " + id);
		try {
			WoPartnerSetting instance = entityManager.find(
					WoPartnerSetting.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

    public List<WoPartnerSetting> findByPartnerId(int partnerId) {
        log.debug("getting WoPartnerSetting instance with id: " + partnerId);
        try {
            List<WoPartnerSetting> instances = getSession().createCriteria(WoPartnerSetting.class)
                    .add(Restrictions.eq("poslovniPartner", partnerId ))
                    .list();
            log.debug("get successful");
            return instances;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List<WoPartnerSetting> findByPartnerIdForCurrentCompany(int partnerId, WoKompanijaKorisnik kompanijeKorisnik) {
        log.debug("getting WoPartnerSetting instance with id: " + partnerId);
        try {
            List<WoPartnerSetting> instances = getSession().createCriteria(WoPartnerSetting.class)
                    .add(Restrictions.eq("poslovniPartner.poslovniPartner", partnerId ))
                    .add(Restrictions.eq("woKompanijaKorisnik",kompanijeKorisnik.getId() ))
                    .list();
            log.debug("get successful");
            return instances;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
}
