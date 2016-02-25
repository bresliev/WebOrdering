package rs.invado.wo.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import rs.invado.wo.dao.ocp.OcpKlasifikacijaHome;
import rs.invado.wo.dao.uz.UzDozvPakovanjaHome;
import rs.invado.wo.domain.ocp.OcpKlasifikacija;
import rs.invado.wo.domain.uz.UzDozvPakovanja;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-context.xml",
		"classpath:/META-INF/spring/applicationContext.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
    public class OcpKlasifikacijaHomeTest {

	@Autowired
	private OcpKlasifikacijaHome ocpKlasifikacijaDAO;
    @Autowired
    private UzDozvPakovanjaHome uzDozvPakovanjaDAO;

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindByNativeQuery() {

		/*List<OcpKlasifikacija> l = ocpKlasifikacijaDAO.findHierarchy(2, "38", 2);
        for (OcpKlasifikacija kl: l)
        System.out.println(kl.getId().getKlasifikacija()+" "+kl.getNaziv()); */
        Map<Integer, BigDecimal> dp = uzDozvPakovanjaDAO.findTransportnaPakovanja();
        Iterator it = dp.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pairs = (Map.Entry)it.next();
            System.out.println(" Pkovana "+pairs.getKey() + " = " + pairs.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }

	}

}
