package rs.invado.wo.dao.admin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.admin.AdminUserCinpos;
import rs.invado.wo.domain.admin.AdminUserCinposId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by IntelliJ IDEA.
 * User: nikola
 * Date: 18.1.13.
 * Time: 23.13
 * To change this template use File | Settings | File Templates.
 */
@Repository
@Transactional
public class AdmUserCinposHome {

    private static final Log log = LogFactory.getLog(AdminUserCinpos.class);

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(AdminUserCinpos transientInstance) {
        log.debug("persisting AdminUserCinpos instance");
        try {
            entityManager.persist(transientInstance);  entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }



    public void remove(AdminUserCinpos persistentInstance) {
        log.debug("removing KeRadnici instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public AdminUserCinpos merge(AdminUserCinpos detachedInstance) {
        log.debug("merging KeRadnici instance");
        try {
            AdminUserCinpos result = entityManager.merge(detachedInstance);
            entityManager.flush();
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }
                                                               
    public AdminUserCinpos findById(AdminUserCinposId id) {
        log.debug("getting KeRadnici instance with id: " + id);
        try {
            AdminUserCinpos instance = entityManager.find(AdminUserCinpos.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

   public AdminUserCinpos findByIdParameters(Integer radnik, String objekatAnalitike, String idCinpos){

       AdminUserCinposId id = new AdminUserCinposId(radnik, objekatAnalitike, idCinpos);
       return findById(id);
   }
   
}
