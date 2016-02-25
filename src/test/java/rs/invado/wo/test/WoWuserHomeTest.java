package rs.invado.wo.test;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.dao.hr.KeRadniciHome;
import rs.invado.wo.domain.hr.KeRadnici;
import rs.invado.wo.domain.ocp.OcpPoslovniPartner;
import rs.invado.wo.domain.wo.WoUser;
import rs.invado.wo.dao.wo.WoUserHome;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User: nikola
 * Date: 16.12.12.
 * Time: 18.53
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-context.xml",
        "classpath:/META-INF/spring/applicationContext.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class WoWuserHomeTest {

    @Autowired
    private WoUserHome woUserDAO;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private KeRadniciHome keRadniciDAO;

    @Test
    public void test() {
        fail("Not yet implemented");
    }

    @Test
    public void persistencija(){
        Session session = entityManager.unwrap(Session.class);
        //Transaction transaction = session.getTransaction();
        KeRadnici radnik =   keRadniciDAO.findById("345");
        WoUser item = new WoUser();
        item.setUserName("UROSDJUKIC");
        item.setPassword("");
        item.setDatumRegistracije(new Date());
        item.setUserType("INTERNI");
        //OcpPoslovniPartner ocpp = new OcpPoslovniPartner();
        //ocpp.setPoslovniPartner();
        item.setRadnik(radnik);
        //item.setOcpPoslovniPartner(ocpp);
        item.setEmail("urosdjukic@darex.rs");
        item.setNickname("neko");
        item.setTelefonMobilni("+38163454545");
        woUserDAO.persist(item);

        //WoUser itemN = woUserDAO.findById(();
        //System.out.println(itemN.getUserName());
        //transaction.commit();



    }
}
