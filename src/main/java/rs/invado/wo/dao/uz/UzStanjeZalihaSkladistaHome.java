package rs.invado.wo.dao.uz;

// Generated Jan 1, 2013 5:03:17 PM by Hibernate Tools 3.4.0.CR1


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.uz.UzStanjeZalihaSkladista;
import rs.invado.wo.domain.uz.UzStanjeZalihaSkladistaId;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;

/**
 * Home object for domain model class UzStanjeZalihaSkladista.
 *
 * @author Hibernate Tools
 * @see rs.invado.wo.domain.uz.UzStanjeZalihaSkladista
 */
@Repository
@Transactional
//
public class UzStanjeZalihaSkladistaHome {

    private static final Log log = LogFactory
            .getLog(UzStanjeZalihaSkladistaHome.class);

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(UzStanjeZalihaSkladista transientInstance) {
        log.debug("persisting UzStanjeZalihaSkladista instance");
        try {
            entityManager.persist(transientInstance);
            entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(UzStanjeZalihaSkladista persistentInstance) {
        log.debug("removing UzStanjeZalihaSkladista instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public UzStanjeZalihaSkladista merge(
            UzStanjeZalihaSkladista detachedInstance) {
        log.debug("merging UzStanjeZalihaSkladista instance");
        try {
            UzStanjeZalihaSkladista result = entityManager
                    .merge(detachedInstance);
            entityManager.flush();
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public UzStanjeZalihaSkladista findById(UzStanjeZalihaSkladistaId id) {
        log.debug("getting UzStanjeZalihaSkladista instance with id: " + id);
        try {
            UzStanjeZalihaSkladista instance = entityManager.find(
                    UzStanjeZalihaSkladista.class, id, LockModeType.PESSIMISTIC_WRITE);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public void azurirajRezervisanuKolicinu(UzStanjeZalihaSkladistaId id, double kolicina, int povecajIliUmanji) {

        UzStanjeZalihaSkladista uzStanjeZalihaSkladista = findById(id);
        if (uzStanjeZalihaSkladista != null) {
            BigDecimal kolicinaZaRezervaciju = uzStanjeZalihaSkladista.getRezervisanaKol().add(BigDecimal.valueOf(povecajIliUmanji * kolicina));
            uzStanjeZalihaSkladista.setRezervisanaKol(kolicinaZaRezervaciju);
            merge(uzStanjeZalihaSkladista);
        }else{
            UzStanjeZalihaSkladista uzStanjeZalihaSkladistaP = new UzStanjeZalihaSkladista();
            uzStanjeZalihaSkladistaP.setId(id);
            uzStanjeZalihaSkladistaP.setRezervisanaKol(new BigDecimal(kolicina));
            uzStanjeZalihaSkladistaP.setKolicinaPoStanjuZ(new BigDecimal(0.0));
            uzStanjeZalihaSkladistaP.setUkupnoIzdataKol(new BigDecimal(0.0));
            uzStanjeZalihaSkladistaP.setUkupnoPrimljenaKol(new BigDecimal(0.0));
            uzStanjeZalihaSkladistaP.setPocStProizvodaUSkl(new BigDecimal(0.0));
            persist(uzStanjeZalihaSkladistaP);
        }

    }

    public void povecajRezervisanuKolicinu(UzStanjeZalihaSkladistaId id, double kolicina) {
        azurirajRezervisanuKolicinu(id, kolicina, 1);
    }

    public void umanjiRezervisanuKolicinu(UzStanjeZalihaSkladistaId id, double kolicina) {
        azurirajRezervisanuKolicinu(id, kolicina, -1);
    }

}
