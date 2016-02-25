package rs.invado.wo.dao.uz;

// Generated Jan 1, 2013 5:03:17 PM by Hibernate Tools 3.4.0.CR1

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.uz.UzDozvPakovanja;
import rs.invado.wo.domain.uz.UzDozvPakovanjaId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Home object for domain model class UzDozvPakovanja.
 *
 * @author Hibernate Tools
 * @see rs.invado.wo.domain.uz.UzDozvPakovanja
 */
@Repository
@Transactional
//
public class UzDozvPakovanjaHome {

    private static final Log log = LogFactory.getLog(UzDozvPakovanjaHome.class);

    @PersistenceContext
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    public void persist(UzDozvPakovanja transientInstance) {
        log.debug("persisting UzDozvPakovanja instance");
        try {
            entityManager.persist(transientInstance);  entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(UzDozvPakovanja persistentInstance) {
        log.debug("removing UzDozvPakovanja instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public UzDozvPakovanja merge(UzDozvPakovanja detachedInstance) {
        log.debug("merging UzDozvPakovanja instance");
        try {
            UzDozvPakovanja result = entityManager.merge(detachedInstance);
            entityManager.flush();
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public UzDozvPakovanja findById(UzDozvPakovanjaId id) {
        log.debug("getting UzDozvPakovanja instance with id: " + id);
        try {
            UzDozvPakovanja instance = entityManager.find(
                    UzDozvPakovanja.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public Map<Integer, BigDecimal> findTransportnaPakovanjaNamed() {

        Map<Integer, BigDecimal> uzDozvPakovanjaMap = new HashMap<Integer, BigDecimal>();
        String namedQ = "findTransportnaPakovanja";
        Query query = entityManager.createNamedQuery(namedQ)
                .setParameter("parametar", "DA");
        Iterator i = query.getResultList().iterator();
        while (i.hasNext()) {
            Object[] parovi = (Object[]) i.next();
            uzDozvPakovanjaMap.put((Integer) parovi[0], (BigDecimal) parovi[1]);

        }

        return uzDozvPakovanjaMap;

    }

    public Map<Integer, BigDecimal> findTransportnaPakovanja() {

        Map<Integer, BigDecimal> uzDozvPakovanjaMap = new HashMap<Integer, BigDecimal>();

        List<Object[]> lo = getSession().createCriteria(UzDozvPakovanja.class)
                .add(Restrictions.eq("id.transportno", "DA"))
                .setProjection(Projections.projectionList()
                        .add(Property.forName("id.proizvodRef"))
                        .add(Property.forName("id.kolPoPakovanju"))).list();


        Iterator i = lo.iterator();
        while (i.hasNext())
        {

            Object[] parovi = (Object[])i.next();
            Integer proizvod = (Integer)parovi[0];
            BigDecimal kolicinaPoPakovanju = (BigDecimal)parovi[1];
            uzDozvPakovanjaMap.put(proizvod, kolicinaPoPakovanju);

        }
        return uzDozvPakovanjaMap;

    }
}
