package rs.invado.wo.dao.prod;

// Generated Dec 11, 2012 10:40:41 PM by Hibernate Tools 3.4.0.CR1

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.prod.ProdCenovnik;
import rs.invado.wo.domain.prod.ProdCenovnikId;
import rs.invado.wo.domain.wo.WoPartnerSetting;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Home object for domain model class ProdCenovnik.
 *
 * @author Hibernate Tools
 * @see rs.invado.wo.domain.prod.ProdCenovnik
 */
@Repository
@Transactional
//
public class ProdCenovnikHome {

    private static final Log log = LogFactory.getLog(ProdCenovnikHome.class);

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(ProdCenovnik transientInstance) {
        log.debug("persisting ProdCenovnik instance");
        try {
            entityManager.persist(transientInstance);  entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(ProdCenovnik persistentInstance) {
        log.debug("removing ProdCenovnik instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public ProdCenovnik merge(ProdCenovnik detachedInstance) {
        log.debug("merging ProdCenovnik instance");
        try {
            ProdCenovnik result = entityManager.merge(detachedInstance);
            entityManager.flush();
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public ProdCenovnik findById(ProdCenovnikId id) {
        log.debug("getting ProdCenovnik instance with id: " + id);
        try {
            ProdCenovnik instance = entityManager.find(ProdCenovnik.class, id).getProdCenovnik();
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
// woPartnerSkladisteCenovnik će biti globalni objekat
    public HashMap findCeneMapped(WoPartnerSetting woPartnerSetting) {
        Session session = entityManager.unwrap(Session.class);
        HashMap mapaCena = new HashMap();
        List items = session.createQuery("select psc.ocpProizvod.proizvod, psc.cena " +
                " from ProdCenovnik pc" +
                " inner join pc.prodStavkaCenovnikas psc" +
                " where pc.id.idKlasaCene = :klasa" +
                " and pc.id.idCenovnik = :cenovnik" +
                " and pc.id.organizacionaJedinica = :oj" +
                " and pc.datumDo is null")
                .setParameter("klasa", woPartnerSetting.getIdKlasaCene())
                .setParameter("cenovnik", woPartnerSetting.getIdCenovnik())
                .setParameter("oj", woPartnerSetting.getOrganizacionaJedinica()).list();
        Iterator i = items.iterator();
        while (i.hasNext()){
            Object[] o = (Object[]) i.next();
            mapaCena.put(o[0], o[1]);
        }
        return mapaCena;
    }

    public ProdCenovnik findCenovnikAktuelni(WoPartnerSetting woPartnerSetting) {
        Session session = entityManager.unwrap(Session.class);

        return (ProdCenovnik)session.createCriteria(ProdCenovnik.class)
                .add(Restrictions.eq("id.idKlasaCene", woPartnerSetting.getIdKlasaCene()))
                .add(Restrictions.eq("id.idCenovnik", woPartnerSetting.getIdCenovnik()))
                .add(Restrictions.eq("id.organizacionaJedinica", woPartnerSetting.getOrganizacionaJedinica()))
                .add(Restrictions.isNull("datumDo"))
                .uniqueResult();
    }
}
