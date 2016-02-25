package rs.invado.wo.dao.wo;

// Generated Jan 13, 2013 6:20:53 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.uz.UzStanjeZalihaSkladista;
import rs.invado.wo.domain.wo.WoDownloadFiles;
import rs.invado.wo.domain.wo.WoDownloadFilesId;
import rs.invado.wo.domain.wo.WoDownloadTip;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Home object for domain model class WoDownloadFiles.
 * @see rs.invado.wo.domain.wo.WoDownloadFiles
 * @author Hibernate Tools
 */
@Repository
@Transactional
//
public class WoDownloadFilesHome {

	private static final Log log = LogFactory.getLog(WoDownloadFilesHome.class);

	@PersistenceContext
	private EntityManager entityManager;


    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }
	public void persist(WoDownloadFiles transientInstance) {
		log.debug("persisting WoDownloadFiles instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(WoDownloadFiles persistentInstance) {
		log.debug("removing WoDownloadFiles instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public WoDownloadFiles merge(WoDownloadFiles detachedInstance) {
		log.debug("merging WoDownloadFiles instance");
		try {
			WoDownloadFiles result = entityManager.merge(detachedInstance);
            entityManager.flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public WoDownloadFiles findById(WoDownloadFilesId id) {
		log.debug("getting WoDownloadFiles instance with id: " + id);
		try {
			WoDownloadFiles instance = entityManager.find(
					WoDownloadFiles.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
    
    public List<WoDownloadFiles> findWoDownloadFIlesPerTipAndCompany(BigDecimal idTipa){
        
        return getSession().createCriteria(WoDownloadFiles.class, "file")
                .add(Restrictions.eq("id.idTipa", idTipa))
                .createCriteria("woDownloadGrupa", "wdg")
                .addOrder(Order.asc("wdg.sort"))
                .addOrder(Order.asc("file.sort"))
                .list();
    }
    
    public List<WoDownloadFiles> findAktuelnoByCompany(int OJ){

        return getSession().createCriteria(WoDownloadFiles.class)
                .add(Restrictions.eq("aktuelno", "DA"))
                .addOrder(Order.asc("sort"))
                .createCriteria("woDownloadTip", "wdt")
                .add(Restrictions.eq("wdt.organizacionaJedinica", OJ)).list();

    }

    public List<WoDownloadFiles> findAktuelnoByCompanyAndGroup(BigDecimal idTipa){


        return getSession().createCriteria(WoDownloadFiles.class, "file")
                .add(Restrictions.eq("aktuelno", "DA"))
                .add(Restrictions.eq("id.idTipa", idTipa))
                .createCriteria("woDownloadGrupa", "wdg")
                .addOrder(Order.asc("wdg.sort"))
                .addOrder(Order.asc("file.sort"))
                .list();

    }
}
