package rs.invado.wo.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rs.invado.wo.dao.ocp.OcpKlasifikacijaHome;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml",
        "classpath:/META-INF/spring/applicationContext.xml"})
public class KlasifikacijaDaoTest {
    @Autowired
    private OcpKlasifikacijaHome klasifikacijaHome;
    /*
    @Test
    public void testFindById() {
        List<OcpKlasifikacija> menu = klasifikacijaHome.findHierarchy(7, null);
        for (OcpKlasifikacija k : menu) {
            System.out.println(k.getNivo() + " " + k.getNaziv());

        }

        return;
    }
        */
}
