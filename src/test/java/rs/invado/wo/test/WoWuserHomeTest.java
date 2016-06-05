package rs.invado.wo.test;

import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.dao.hr.KeRadniciHome;
import rs.invado.wo.domain.hr.KeRadnici;
import rs.invado.wo.domain.wo.WoUser;
import rs.invado.wo.dao.wo.WoUserHome;
import rs.invado.wo.util.EtoConf;
import rs.invado.wo.util.WoConfigSingleton;


import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

import static org.junit.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User:
 * Date: 16.12.12.
 * Time: 18.53
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/test-context.xml",
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


    @Value("${wo.mailaPassword}")
    private String mailaPassword;
    @Value("${wo.woMailAddress}")
    private String woMailAddress;
    @Value("${wo.idDocForCash}")
    private String idDocForCash;
    @Value("${wo.idDocForVirman}")
    private String idDocForVirman;
    @Value("${wo.vrstaStavke}")
    private String vrstaStavke;
    @Value("${wo.idDocGen}")
    private String idDocGen;
    @Value("${wo.secondFilterlevel}")
    private String secondFilterlevel;
    @Value("${wo.idFilterTypeKlasification}")
    private String idFilterTypeKlasification;
    @Value("${wo.idMenuTypeCLasification}")
    private String idMenuTypeCLasification;


    @Test
    public void test() {

        EtoConf woConfigSingleton = new EtoConf();
        System.out.println("lokalna varijabal je: "+mailaPassword);
        System.out.println("singleton varijabla je : "+woConfigSingleton.getMailaPassword());
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
