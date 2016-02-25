package rs.invado.wo.dao.ocp;

// Generated Dec 9, 2012 5:42:26 PM by Hibernate Tools 3.4.0.CR1


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.ocp.OcpVrAtrProizvod;
import rs.invado.wo.domain.ocp.OcpVrAtrProizvodId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Home object for domain model class OcpVrAtrProizvod.
 *
 * @author Hibernate Tools
 * @see rs.invado.wo.dao.ocp.OcpVrAtrProizvodHome
 */
@Repository
@Transactional
//
public class OcpVrAtrProizvodHome {

    private static final Log log = LogFactory
            .getLog(OcpVrAtrProizvodHome.class);

    @PersistenceContext
    private EntityManager entityManager;


    public Session getSession() {
        Session session = entityManager.unwrap(Session.class);
        return session;
    }

    public void persist(OcpVrAtrProizvod transientInstance) {
        log.debug("persisting OcpVrAtrProizvod instance");
        try {
            entityManager.persist(transientInstance);
            entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(OcpVrAtrProizvod persistentInstance) {
        log.debug("removing OcpVrAtrProizvod instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public OcpVrAtrProizvod merge(OcpVrAtrProizvod detachedInstance) {
        log.debug("merging OcpVrAtrProizvod instance");
        try {
            OcpVrAtrProizvod result = entityManager.merge(detachedInstance);
            entityManager.flush();
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public OcpVrAtrProizvod findById(OcpVrAtrProizvodId id) {
        log.debug("getting OcpVrAtrProizvod instance with id: " + id);
        try {
            OcpVrAtrProizvod instance = entityManager.find(
                    OcpVrAtrProizvod.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public OcpVrAtrProizvod findByNivoAtributa(int vrstaKlasifikacija, String klasifikacija, int proizvod, int atribut) {
        List<String> klase = new ArrayList<String>();
        for (int i = 1; i <= klasifikacija.length(); i++) {
            klase.add(klasifikacija.substring(0, i));
        }
        OcpVrAtrProizvod vap = (OcpVrAtrProizvod) getSession().createCriteria(OcpVrAtrProizvod.class)
                .add(Restrictions.eq("id.vrstaKlasifikacije", vrstaKlasifikacija))
                .add(Restrictions.eq("id.atribut", atribut))
                .add(Restrictions.eq("id.proizvod", proizvod))
                .add(Restrictions.in("id.klasifikacija", klase))
                .uniqueResult();
        return vap;


    }
}
