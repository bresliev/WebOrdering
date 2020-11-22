package rs.invado.wo.dao.ocp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.ocp.OcpProizvod;
import rs.invado.wo.domain.ocp.OcpSastavProizvoda;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by
 */
@Repository
@Transactional
public class OcpSastavProizvodaHome {
    private static final Log log = LogFactory.getLog(OcpSastavProizvodaHome.class);

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(OcpSastavProizvoda transientInstance) {
        log.debug("persisting OcpSastavProizvoda instance");
        try {
            entityManager.persist(transientInstance);  entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(OcpSastavProizvoda persistentInstance) {
        log.debug("removing OcpSastavProizvoda instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public OcpSastavProizvoda merge(OcpSastavProizvoda detachedInstance) {
        log.debug("merging OcpSastavProizvoda instance");
        try {
            OcpSastavProizvoda result = entityManager.merge(detachedInstance);
            entityManager.flush();
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public OcpSastavProizvoda findById(int id) {
        log.debug("getting OcpSastavProizvoda instance with id: " + id);
        try {
            OcpSastavProizvoda instance = entityManager.find(OcpSastavProizvoda.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }


    @Transactional(readOnly = true)
    public List<OcpSastavProizvoda> findByProizvod(OcpProizvod proizvod) {
        log.debug("getting UzStanjeZalihaSkladista instance with id: " + proizvod);
        try {
            List<OcpSastavProizvoda> list = entityManager.createNamedQuery(
                    OcpSastavProizvoda.READ_BY_PROIZVOD_ULAZ,
                    OcpSastavProizvoda.class)
                    .setParameter("proizvod", (proizvod))
                    .getResultList();
            return list;
        } catch (RuntimeException re) {
            log.error("get ba pro", re);
            throw re;
        }

    }
}
