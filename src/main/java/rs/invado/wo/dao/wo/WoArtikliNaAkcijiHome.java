package rs.invado.wo.dao.wo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.wo.WoArtikliNaAkciji;
import rs.invado.wo.domain.wo.WoParametri;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikola
 * Date: 16.12.12.
 * Time: 18.21
 * To change this template use File | Settings | File Templates.
 */
@Repository
@Transactional
//
public class WoArtikliNaAkcijiHome {
    private static final Log log = LogFactory
            .getLog(WoAutentifikacijaHome.class);


    @PersistenceContext
    private EntityManager entityManager;

    public void persist(WoArtikliNaAkciji transientInstance) {
        log.debug("persisting WoArtikliNaAkcijiHome instance");
        try {
            entityManager.persist(transientInstance);  entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(WoArtikliNaAkciji persistentInstance) {
        log.debug("removing WoArtikliNaAkcijiHome instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public WoArtikliNaAkciji merge(WoArtikliNaAkciji detachedInstance) {
        log.debug("merging WoArtikliNaAkcijiHome instance");
        try {
            WoArtikliNaAkciji result = entityManager.merge(detachedInstance);
            entityManager.flush();
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public WoArtikliNaAkciji findById(int id) {
        log.debug("getting WoArtikliNaAkcijiHome instance with id: " + id);
        try {

            WoArtikliNaAkciji instance = entityManager.find(
                    WoArtikliNaAkciji.class, id);
            log.debug("get successful");

            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List<WoArtikliNaAkciji> findArtikliNaAkcijiAktivno(Date datumDo, WoParametri woParametri) {
        List<WoArtikliNaAkciji> lanaa = entityManager.unwrap(Session.class).createCriteria(WoArtikliNaAkciji.class)
                .add(Restrictions.ge("datumDo", datumDo))
                .add(Restrictions.eq("idKompanijeKorisnik", woParametri.getWoKompanijaKorisnik().getId()))
                .add(Restrictions.ne("tipAkcije","NAJPRODAVANIJE"))
                .list();
        return lanaa;
    }

}
