package rs.invado.wo.test;

import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.dao.wo.WoKompanijaKorisnikHome;
import rs.invado.wo.dao.wo.WoParametriHome;
import rs.invado.wo.domain.ocp.OcpPoslovniPartner;
import rs.invado.wo.domain.wo.WoKompanijaKorisnik;
import rs.invado.wo.domain.wo.WoParametri;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: nikola
 * Date: 16.12.12.
 * Time: 21.34
 * To change this template use File | Settings | File Templates.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml",
        "classpath:/META-INF/spring/applicationContext.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class WoParametriTest {

    @Autowired
    private WoParametriHome woParametriDAO;
    @Autowired
    private WoKompanijaKorisnikHome woKompanijaKorisnikHome;
    @PersistenceContext
    private EntityManager entityManager;


    @Test
    public void testFind(){
         WoParametri item = woParametriDAO.findActualSetOfParameters();
        System.out.println(item.getDatumDo()+" "+item.getMailaddress());
    }
    
    @Test 
    public  void woKonpanijaInsert(){
        
        Session  session = entityManager.unwrap(Session.class);
        WoKompanijaKorisnik wokk = new WoKompanijaKorisnik();
        wokk.setCorrespondingOJ(40);
        wokk.setAdresa("Autotput Beograd - Zagreb bb");
        wokk.setDatumRegistracije(Calendar.getInstance().getTime());
        wokk.setEmail("darex@darex.rs");
        wokk.setMaticniBroj("123456489");
        wokk.setNaziv("Darex Itison D.O.O");
        wokk.setOfficialEmail("darex@darex.rs");
        wokk.setPIB("9876543210");
        OcpPoslovniPartner pp = new OcpPoslovniPartner();
        pp.setPoslovniPartner(133166);

        wokk.setOcpPoslovniPartnerMaticni(pp);
        woKompanijaKorisnikHome.persist(wokk);

    }


}
