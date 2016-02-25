package rs.invado.wo.util;


import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by IntelliJ IDEA.
 * User: nikola
 * Date: 20.12.12.
 * Time: 22.19
 * To change this template use File | Settings | File Templates.
 */

public class HibernateUtil {
    private static HibernateUtil ourInstance = new HibernateUtil();

    @PersistenceContext
    private EntityManager entityManager;

    public HibernateUtil() {

    }

    public static HibernateUtil getInstance() {
        return ourInstance;
    }

    public HibernateUtil(EntityManager entityManger){

        this.entityManager = entityManger;
    }
    public Session beginTransaction() {
        Session hibernateSession = this.getSession();
        hibernateSession.beginTransaction();
        return hibernateSession;
    }

    public void commitTransaction() {
        this.getSession().getTransaction().commit();
    }

    public void rollbackTransaction() {
        this.getSession().getTransaction().rollback();
    }

    public void closeSession() {
        this.getSession().close();
    }

    public Session getSession() {

        Session hibernateSession = entityManager.unwrap(Session.class);
        return hibernateSession;
    }

}

