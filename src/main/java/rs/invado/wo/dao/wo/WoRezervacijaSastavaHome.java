package rs.invado.wo.dao.wo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.domain.ocp.OcpProizvod;
import rs.invado.wo.domain.wo.WoRezervacija;
import rs.invado.wo.domain.wo.WoRezervacijaSastava;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Nikola on 10/03/2016.
 */
    @Repository
    @Transactional
//
    public class WoRezervacijaSastavaHome {

        private static final Log log = LogFactory.getLog(WoRezervacijaSastavaHome.class);

        @PersistenceContext
        private EntityManager entityManager;

        public Session getSession(){
            return entityManager.unwrap(Session.class);
        }

        public void persist(WoRezervacijaSastava transientInstance) {
            log.debug("persisting WoRezervacijaSastava instance");
            try {
                entityManager.persist(transientInstance);
                entityManager.flush();
                log.debug("persist successful");
            } catch (RuntimeException re) {
                log.error("persist failed", re);
                throw re;
            }
        }

        public void remove(WoRezervacijaSastava persistentInstance) {
            log.debug("removing WoRezervacijaSastava instance");

            try {
                entityManager.remove(persistentInstance);
                log.debug("remove successful");
            } catch (RuntimeException re) {
                log.error("remove failed", re);
                throw re;
            }
        }

        public WoRezervacijaSastava merge(WoRezervacijaSastava detachedInstance) {
            log.debug("merging WoRezervacijaSastava instance");
            try {
                WoRezervacijaSastava result = entityManager.merge(detachedInstance);
                entityManager.flush();
                log.debug("merge successful");
                return result;
            } catch (RuntimeException re) {
                log.error("merge failed", re);
                throw re;
            }
        }

        public WoRezervacijaSastava findById(BigDecimal id) {
            log.debug("getting WoRezervacijaSastava instance with id: " + id);
            try {
                WoRezervacijaSastava instance = entityManager
                        .find(WoRezervacijaSastava.class, id);
                log.debug("get successful");
                return instance;
            } catch (RuntimeException re) {
                log.error("get failed", re);
                throw re;
            }
        }

        public List<WoRezervacijaSastava> findByWoRezervacijaNotChecked(WoRezervacijaSastava woRezervacijaSastava){
            return  getSession().createCriteria(WoRezervacijaSastava.class)
                    .add(Restrictions.eq("woRezervacijaSastava", woRezervacijaSastava))
                    .add(Restrictions.eq("statusRezervacije", 1))
                    .list();
        }

        public WoRezervacijaSastava findByWoRezervacijaAndProizvodNotChecked(WoRezervacija woRezervacija, OcpProizvod ocpProizvod){
            return  (WoRezervacijaSastava)getSession().createCriteria(WoRezervacijaSastava.class)
                    .add(Restrictions.eq("woRezervacija.id", woRezervacija.getId()))
                    .add(Restrictions.eq("proizvod.proizvod#", ocpProizvod.getProizvod())).uniqueResult();
        }

    }

