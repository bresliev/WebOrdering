package rs.invado.wo.dao.uz;

// Generated Jan 1, 2013 5:03:17 PM by Hibernate Tools 3.4.0.CR1


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.prod.ProdPpRabatStavka;
import rs.invado.wo.domain.uz.UzDokumentOj;
import rs.invado.wo.domain.uz.UzDokumentOjId;

import java.util.List;

/**
 * Home object for domain model class UzDokumentOj.
 * @see rs.invado.wo.domain.uz.UzDokumentOj
 * @author Hibernate Tools
 */
@Repository
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UzDokumentOjHome {

	private static final Log log = LogFactory.getLog(UzDokumentOjHome.class);

	@PersistenceContext
	private EntityManager entityManager;
    
    private Session getSession(){
        return entityManager.unwrap(Session.class);
    }

	public void persist(UzDokumentOj transientInstance) {
		log.debug("persisting UzDokumentOj instance");
		try {
			entityManager.persist(transientInstance);  entityManager.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(UzDokumentOj persistentInstance) {
		log.debug("removing UzDokumentOj instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public UzDokumentOj merge(UzDokumentOj detachedInstance) {
		log.debug("merging UzDokumentOj instance");
		try {
			UzDokumentOj result = entityManager.merge(detachedInstance);
            entityManager.flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public UzDokumentOj findById(UzDokumentOjId id) {
		log.debug("getting UzDokumentOj instance with id: " + id);
		try {
			UzDokumentOj instance = entityManager.find(UzDokumentOj.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

    public Object spkSklDokumentSlozenQ(Integer idVd, Integer oj, Integer godina, Integer idskladista){
        String q = "spkSklDokumentSlozen";
        Object o =  entityManager.createNamedQuery(q)
                .setParameter("p_IdVd", idVd)
                .setParameter("p_oj", oj)
                .setParameter("p_godina", godina)
                .setParameter("p_idskladista", idskladista).getResultList();
        entityManager.flush();
        return o;


    }
    
    public String spkSklDokumentSlozen (Integer idVd, Integer oj, Integer godina, Integer idskladista){
        List<UzDokumentOj> uzDokumentOjs = (List<UzDokumentOj>)  spkSklDokumentSlozenQ(idVd, oj, godina, idskladista);
        return uzDokumentOjs.get(0).getPoslBrDokNab()+"/"+uzDokumentOjs.get(0).getId().getOrganizacionaJedinica()
                +"/"+godina+"/"+idVd;
    }
}
