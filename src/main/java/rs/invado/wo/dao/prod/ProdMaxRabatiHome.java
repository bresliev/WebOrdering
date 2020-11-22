package rs.invado.wo.dao.prod;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

// Generated Jan 20, 2013 9:55:29 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.prod.ProdMaxRabati;
import rs.invado.wo.domain.prod.ProdMaxRabatiId;

import java.math.BigDecimal;
import java.sql.SQLException;

@Repository
@Transactional
//
public class ProdMaxRabatiHome {

    private static final Log log = LogFactory.getLog(ProdMaxRabatiHome.class);

    @PersistenceContext
    private EntityManager entityManager;


    public static String getNamedQueryString(EntityManager em, String queryName) throws SQLException {
        Query tmpQuery = em.createNamedQuery(queryName);
        SQLQuery sqlQuery = tmpQuery.unwrap(SQLQuery.class);
        String queryString = sqlQuery.getQueryString();
        return queryString;
    }

    public void persist(ProdMaxRabati transientInstance) {
        log.debug("persisting ProdMaxRabati instance");
        try {
            entityManager.persist(transientInstance);
            entityManager.flush();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(ProdMaxRabati persistentInstance) {
        log.debug("removing ProdMaxRabati instance");
        try {
            entityManager.remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public ProdMaxRabati merge(ProdMaxRabati detachedInstance) {
        log.debug("merging ProdMaxRabati instance");
        try {
            ProdMaxRabati result = entityManager.merge(detachedInstance);
            entityManager.flush();
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public ProdMaxRabati findById(ProdMaxRabatiId id) {
        log.debug("getting ProdMaxRabati instance with id: " + id);
        try {
            ProdMaxRabati instance = entityManager
                    .find(ProdMaxRabati.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public ProdMaxRabati findByKlasa(Integer organizacionaJedinica, Integer vrstaKlasifikacije, String klasifikacija) {
        try {
            return entityManager.createNamedQuery("findMaxRabatZaProizvod", ProdMaxRabati.class)
                    .setParameter("oj", organizacionaJedinica)
                    .setParameter("vk", vrstaKlasifikacije)
                    .setParameter("klasa", klasifikacija)
                    .getSingleResult();
        }catch(NoResultException e){
            ProdMaxRabati prodMaxRabati = new ProdMaxRabati();
            prodMaxRabati.setMaxRabat(new BigDecimal(-1));
            return prodMaxRabati;
        }
    }
}

