package rs.invado.wo.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.dao.ocp.OcpProizvodHome;
import rs.invado.wo.dao.wo.WoArtikliNaAkcijiHome;
import rs.invado.wo.domain.ocp.OcpProizvod;
import rs.invado.wo.domain.wo.WoArtikliNaAkciji;
import rs.invado.wo.domain.wo.WoUser;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;

import static org.junit.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User: nikola
 * Date: 16.12.12.
 * Time: 18.28
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml",
        "classpath:/META-INF/spring/applicationContext.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class WoArtikliNaAkcijiHomeTest {


    @Autowired
    private WoArtikliNaAkcijiHome woArtikliNaAkcijiDAO;
    @Autowired
    private OcpProizvodHome ocpProizvodHome;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void test() {
        fail("Not yet implemented");
    }

    @Test
    public void persistencija() {
        //Session session = entityManager.unwrap(Session.class);
        //HibernateUtil hu = new HibernateUtil(woArtikliNaAkcijiDAO.getEntityManager());
        WoArtikliNaAkciji artikal = new WoArtikliNaAkciji();
        OcpProizvod p = ocpProizvodHome.findById(534617);
        artikal.setOcpProizvod(p);
        WoUser user = new WoUser();
        user.setUserName("nikola");
        Calendar c = Calendar.getInstance();
        artikal.setDatumOd(c.getTime());
        c.add(Calendar.DATE, 60);
        artikal.setDatumDo(c.getTime());
        artikal.setTipAkcije("AKCIJA");
        woArtikliNaAkcijiDAO.persist(artikal);
        WoArtikliNaAkciji artikal1 = new WoArtikliNaAkciji();

        p = ocpProizvodHome.findById(513024);
        artikal1.setOcpProizvod(p);
        user.setUserName("nikola");
        c = Calendar.getInstance();
        artikal1.setDatumOd(c.getTime());
        artikal1.setTipAkcije("AKCIJA");
        woArtikliNaAkcijiDAO.persist(artikal1);
        //hu.commitTransaction();
    }

}
