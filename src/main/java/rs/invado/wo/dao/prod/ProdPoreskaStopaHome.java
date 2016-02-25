package rs.invado.wo.dao.prod;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.ocp.OcpKlasifikacija;
import rs.invado.wo.domain.prod.ProdKlasaCene;
import rs.invado.wo.domain.prod.ProdKlasaCeneId;
import rs.invado.wo.domain.prod.ProdPoreskaStopa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: nikola
 * Date: 14.3.13.
 * Time: 22.43
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Repository
public class ProdPoreskaStopaHome {

    private static final Log log = LogFactory.getLog(ProdKlasaCeneHome.class);

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(ProdPoreskaStopa transientInstance) {
        log.debug("persisting ProdPoreskaStopa instance");
        try {
            entityManager.persist(transientInstance);  entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(ProdPoreskaStopa persistentInstance) {
        log.debug("removing ProdPoreskaStopa instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public ProdPoreskaStopa merge(ProdPoreskaStopa detachedInstance) {
        log.debug("merging ProdKlasaCene instance");
        try {
            ProdPoreskaStopa result = entityManager.merge(detachedInstance);
            entityManager.flush();
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public ProdPoreskaStopa findById(ProdPoreskaStopa id) {
        log.debug("getting ProdPoreskaStopa instance with id: " + id);
        try {
            ProdPoreskaStopa instance = entityManager
                    .find(ProdPoreskaStopa.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }  
    
    public BigDecimal findPorezPerProizvod(Integer proizvod, Integer oj, Date datum){

        BigDecimal porez = new BigDecimal(1);
        ProdPoreskaStopa stopa = new ProdPoreskaStopa();
        String namedQ = "findPorezPerProizvod";
        Query q = entityManager.createNamedQuery(namedQ)
                .setParameter("proizvod", proizvod)
                .setParameter("oj", oj)
                .setParameter("datum", datum);

        Iterator i = q.getResultList().iterator();
        while (i.hasNext())
        {
            stopa = (ProdPoreskaStopa) i.next();
        }

        return stopa.getStopaPoreza();

    }
}
