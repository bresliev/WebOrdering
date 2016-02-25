package rs.invado.wo.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.dao.prod.ProdCenovnikHome;
import rs.invado.wo.domain.wo.WoPartnerSetting;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikola
 * Date: 16.12.12.
 * Time: 22.55
 * To change this template use File | Settings | File Templates.
 */



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml",
        "classpath:/META-INF/spring/applicationContext.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class ProdCenovnikHomeTest {



        @Autowired
        private ProdCenovnikHome prodCenovnikDAO;
        @PersistenceContext
        private EntityManager entityManager;


        @Test
        public void testFind(){
            System.out.println(" prvi prvi ");
            WoPartnerSetting wpsc = new WoPartnerSetting();
            System.out.println(" prvi drugi ");
            wpsc.setIdCenovnik(18);
            System.out.println(" prvi treći ");
            wpsc.setIdKlasaCene( 18);
            System.out.println(" prvi četvrti ");
            wpsc.setOrganizacionaJedinica(19);
            System.out.println(" prvi peti ");
            if (wpsc==null) System.out.println(" nije mi kreirao objekat ");
            Map<Integer, BigDecimal> m = prodCenovnikDAO.findCeneMapped(wpsc);
            for (Map.Entry<Integer, BigDecimal>  entry : m.entrySet())
                System.out.println(entry.getKey()+" "+entry.getValue());


        }
    }


