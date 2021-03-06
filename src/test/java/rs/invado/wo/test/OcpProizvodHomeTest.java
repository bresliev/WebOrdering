package rs.invado.wo.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.dao.ocp.OcpLiceZaKontaktHome;
import rs.invado.wo.dao.ocp.OcpProizvodHome;
import rs.invado.wo.dao.ocp.OcpSastavProizvodaHome;
import rs.invado.wo.dao.prod.ProdCenovnikHome;
import rs.invado.wo.dao.prod.ProdMaxRabatiHome;
import rs.invado.wo.dao.prod.ProdNacinPlacanjaHome;
import rs.invado.wo.dao.uz.UzStanjeZalihaSkladistaHome;
import rs.invado.wo.dao.uz.UzZaliheJsklHome;
import rs.invado.wo.dao.wo.WoParametriHome;
import rs.invado.wo.dao.wo.WoRezervacijaHome;
import rs.invado.wo.dao.wo.WoSetPoNacinPlacanjaHome;
import rs.invado.wo.dao.wo.WoSortPerObjectAttributeHome;
import rs.invado.wo.domain.ocp.OcpLiceZaKontakt;
import rs.invado.wo.domain.ocp.OcpProizvod;
import rs.invado.wo.domain.prod.ProdMaxRabati;
import rs.invado.wo.domain.prod.ProdNacinPlacanja;
import rs.invado.wo.domain.wo.WoParametri;
import rs.invado.wo.domain.wo.WoPartnerSetting;
import rs.invado.wo.domain.wo.WoSetPoNacinPlacanja;
import rs.invado.wo.dto.CompanySetting;
import rs.invado.wo.dto.Proizvodi;
import rs.invado.wo.dto.User;
import rs.invado.wo.service.AppInitService;
import rs.invado.wo.service.BasketBusinessProcessing;
import rs.invado.wo.service.LogOnService;
import rs.invado.wo.service.ProductService;
import rs.invado.wo.util.WOException;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.fail;


@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:/META-INF/applicationContext.xml"})
@ContextConfiguration(locations = {"classpath:/META-INF/testApplicationContext.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class OcpProizvodHomeTest {

    @Inject
    private OcpProizvodHome ocpProizvodDAO;
    @Inject
    private ProdCenovnikHome prodCenovnikDAO;
    @Inject
    private ProductService ps;
    @Inject
    private LogOnService logOnService;
    @Inject
    private AppInitService appInitService;
    @Inject
    private WoRezervacijaHome woRezervacijaDAO;
    @Inject
    private ProdNacinPlacanjaHome prodNacinPlacanjaDAO;
    @Inject
    private WoParametriHome woParametriDAO;
    @Inject
    private WoSetPoNacinPlacanjaHome woSetPoNacinPlacanjaDao;
    @Inject
    private UzZaliheJsklHome uzZaliheJsklDAO;
    @Inject
    private UzStanjeZalihaSkladistaHome uzStanjeZalihaSkladistaDAO;
    @Inject
    private OcpSastavProizvodaHome ocpSastavProizvodaDAO;
    @Inject
    private BasketBusinessProcessing basketBusinessProcessing;
    @Inject
    private WoSortPerObjectAttributeHome woSortPerObjectAttributeDAO;
    @Inject
    private ProdMaxRabatiHome prodMaxRabatiDAO;
    @Autowired
    private OcpLiceZaKontaktHome ocpLiceZaKontaktDAO;


    /*@PersistenceContext
    private EntityManager entityManager;    */

    @Test
    public void test() {
        fail("Not yet implemented");
    }


    @Test
    public void findPoBrandHashCena() {
        WoPartnerSetting wpsc = new WoPartnerSetting();
        wpsc.setIdCenovnik(18);
        wpsc.setIdKlasaCene(18);
        wpsc.setOrganizacionaJedinica(20);
        Map<Integer, BigDecimal> m = prodCenovnikDAO.findCeneMapped(wpsc, null);
        int i = 0;
        // Proizvodi p = ocpProizvodDAO.findProizvodiZaBrend("0101", m, 0, 10);
        //List<OcpProizvod> p = ocpProizvodDAO.findProizvodiByName("iverica opleme", 1, 0,  cs.getKompanijskiParametri().get(19));
        //List<OcpProizvod> p = ocpProizvodDAO.findProizvodiNaAkciji("AKCIJA", m);
        /* for (OcpProizvod item : p.getProizvodList()) {
        System.out.println(item.getProizvod() + " Proizvod " + item.getNazivProizvoda() + item.getDodatniNaziv() + " " + item.getCena()
                + " tipa " + item.getTipAkcije() + " " + item.getDezenIstruktira() + " " + item.getProizvodjac());
        /*for (OcpVrAtrProizvod vra : item.getVrAtrProizvod())
      System.out.println(vra.getId().getAtribut() +" "+vra.getVrednost());*/



    }

    @Test
    public void testProductServiceSort() {
        Integer oj = new Integer(19);
        System.out.println("bas prvi");
        CompanySetting cs = appInitService.initApp();

        try {
            String cut = "";
            User user = logOnService.logOn("milomirtankosic01", "11111111", cs, oj);
            Map<Integer, BigDecimal> m = prodCenovnikDAO.findCeneMapped(user.getWoPartnerSetting().get(0), cs.getKompanijskiParametri().get(20));

            //List<WoSortPerObjectAttribute> woSortPerObjectAttributes = woSortPerObjectAttributeDAO.findByWoClassId(new Integer(22), "000101");
            /*for (WoSortPerObjectAttribute woSortPerObjectAttribute : woSortPerObjectAttributes)
                cut = cut +" "+woSortPerObjectAttribute.getAttributeId()+" "+woSortPerObjectAttribute.getOrderType() + ",";
            cut = cut.substring(0, cut.lastIndexOf(","));
            System.out.println(cut);*/
            Proizvodi proizvodi = null;

            //proizvodi = ps.getProizvodiZaBrendSorted("000104", m, 0, 100,  cs.getKompanijskiParametri().get(oj), user.getWoPartnerSetting(),  cs.getTrasportnaPakovanja(), cs, oj);
            proizvodi = ps.getProzivodiNaAkciji("NAJPRODAVANIJE", m, 0, 100, cs.getKompanijskiParametri().get(oj), user.getWoPartnerSetting(), cs.getTrasportnaPakovanja(), oj);

            System.out.println("velicina li ste je " + proizvodi.getProizvodList().size());

            for (OcpProizvod proizvod : proizvodi.getProizvodList()) {
                System.out.println("klasifikacija ima "+proizvod.getOcpKlasifikacijaProizvoda().size());
                System.out.println("klasifikacija je " + proizvod.getSortKlasa() + " " + proizvod.getPunNazivProizvoda() + " " + proizvod.getCena() + " " + proizvod.getProizvod() + " " + proizvod.getSortKlasaComposite()
                        + " " + proizvod.getRaspolozivo());
            }

/*
            proizvodi = ocpProizvodDAO.findProizvodiZaBrendSortedNativeTest("000101", 0, 100, cs.getKompanijskiParametri().get(19), user.getWoPartnerSetting(), cs);
            for (OcpProizvod proizvod : proizvodi.getProizvodList())
                System.out.println("klasifikacija je " + proizvod.getSortKlasa() + " " + proizvod.getPunNazivProizvoda()+" "+proizvod.getCena()+" "+proizvod.getProizvod() + " " + proizvod.getSortKlasaComposite());
*/

            /*Proizvodi proizvodi = ocpProizvodDAO.findProizvodiZaBrendSorted("003401", 0, 15, cs.getKompanijskiParametri().get(19),
                    user.getWoPartnerSetting(), cs);*/

            /*
            for (OcpProizvod item : proizvodi.getProizvodList()) {
                if (item.getProizvod().equals(new Integer("334561"))) {
                    basketBusinessProcessing.increaseReservationCompositeObject(item, 19, new BigDecimal(1), "-125", user, new BigDecimal("1"));

                }
            }
*/
            /*
            Proizvodi proizvodi = ocpProizvodDAO.findProizvodiByName("Fioka", 0, 15, cs.getKompanijskiParametri().get(19),
                    user.getWoPartnerSetting());

                    cs.getKompanijskiParametri().get(19), user.getWoPartnerSetting(), cs.getTrasportnaPakovanja(), cs, oj);
            for (OcpProizvod item : proizvodi.getProizvodList()) {
                System.out.println(" Proizvod je 1001 " + item.getProizvod() + " Raspolozivo za  "+ item.getPunNazivProizvoda());

            }

            proizvodi = ps.getProizvodiZaBrendSorted("1002", user.getCeneProizvoda(), 0, 1500,
                    cs.getKompanijskiParametri().get(19), user.getWoPartnerSetting(), cs.getTrasportnaPakovanja(), cs, oj);
            for (OcpProizvod item : proizvodi.getProizvodList()) {
                System.out.println(" Proizvod je 1002 " + item.getProizvod() + " Raspolozivo za  "+ item.getPunNazivProizvoda());

            }

            proizvodi = ocpProizvodDAO.findProizvodiZaBrendSorted("0103", 1, 15, cs.getKompanijskiParametri().get(19),
                    user.getWoPartnerSetting(), cs);
            for (OcpProizvod item : proizvodi.getProizvodList()) {
                System.out.println(" Proizvod je 0103 " + item.getProizvod() + " Raspolozivo za  "+ item.getPunNazivProizvoda());

            }



            proizvodi = ocpProizvodDAO.findProizvodiZaBrendSorted("0103", 2, 15, cs.getKompanijskiParametri().get(19),
                    user.getWoPartnerSetting(), cs);
            for (OcpProizvod item : proizvodi.getProizvodList()) {
                System.out.println(" Proizvod je 1 " + item.getProizvod() + " Raspolozivo za  "+ item.getPunNazivProizvoda());

            }
            proizvodi = ocpProizvodDAO.findProizvodiZaBrendSorted("0103", 3, 15, cs.getKompanijskiParametri().get(19),
                    user.getWoPartnerSetting(), cs);
            for (OcpProizvod item : proizvodi.getProizvodList()) {
                System.out.println(" Proizvod je 2 " + item.getProizvod() + " Raspolozivo za  "+ item.getPunNazivProizvoda());

            }
            proizvodi = ocpProizvodDAO.findProizvodiZaBrendSorted("0103", 4, 15, cs.getKompanijskiParametri().get(19),
                    user.getWoPartnerSetting(), cs);
            for (OcpProizvod item : proizvodi.getProizvodList()) {
                System.out.println(" Proizvod je 3 " + item.getProizvod() + " Raspolozivo za  "+ item.getPunNazivProizvoda());

            }   */

        } catch (WOException e) {
            System.out.println((e.getMessage()));
        }
    }

    @Test
    public void btvz() {

        BigDecimal v1 = new BigDecimal(5);
        BigDecimal v2 = new BigDecimal(2);
        BigDecimal v3 = new BigDecimal(3);

        System.out.println(v1.subtract(v2));
        System.out.println(v1.subtract(v2).divide(v3));
    }

    @Test
    public void testProductService() throws SQLException {
        Integer oj = new Integer(19);
        CompanySetting cs = null;//appInitService.initApp();
        try {
            User user = logOnService.logOn("milomirtankosic01", "11111111", cs, oj);


            /*CompanySetting cs =  new CompanySetting();
            User user = new User();  */
            Map<Integer, BigDecimal> m = prodCenovnikDAO.findCeneMapped(user.getWoPartnerSetting().get(0), cs.getKompanijskiParametri().get(0));
            //Proizvodi proizvodi = ps.getProizvodiZaBrend("0101", m, 0, 10) ;
        /*Proizvodi proizvodi = ps.getProzivodiNaAkcijiSorted("AKTUELNO", m, 0, 100); */

        Proizvodi proizvodi = ocpProizvodDAO.findProizvodiZaBrendSorted("0101", 0, 1050, cs.getKompanijskiParametri().get(19), user.getWoPartnerSetting(), cs);
            List<String> patterns = new ArrayList<String>();
            patterns.add("iver");
            patterns.add("oplem");
            String brand;
            proizvodi = ocpProizvodDAO.findFilterProizvodiByNamePatternsSorted(null, patterns, 0, 1500, cs.getKompanijskiParametri().get(19),
                    user.getWoPartnerSetting());
            for (OcpProizvod item : proizvodi.getProizvodList()) {
                System.out.println(" Proizvod je " + item.getProizvod() + " Raspolozivo za  "
                                + " jm osn je " + item.getPunNazivProizvoda()
                                + "i alt jem je " + item.getJedinicaMere().getSkracenaOznaka()
                                + " " + item.getSortKlasa()
                                + " " + item.getCena()

                );
                //List<String> proizvodi = ps.findProizvodByFullName("iverica");
          /*       Proizvodi proizvodi = ocpProizvodDAO.findProizvodiByNameSorted("abs", 0, 1050, cs.getKompanijskiParametri().get(19),
                user.getWoPartnerSetting());   */
        /* Proizvodi proizvodi = ps.getFilterProizvodi("0101", "iverica", m, 0, 1000, cs.getKompanijskiParametri().get(oj),
     user.getWoPartnerSetting(), cs.getTrasportnaPakovanja());  */
        /*Proizvodi proizvodi = ps.getProzivodiNaAkcijiSorted("AKCIJA", m, 0, 50, cs.getKompanijskiParametri().get(oj),
       user.getWoPartnerSetting(), cs.getTrasportnaPakovanja()); */
        /* OcpProizvod ocpProizvod = ps.getProizvodById(29645, m, cs.getKompanijskiParametri().get(oj), user.getWoPartnerSetting(),
              cs.getTrasportnaPakovanja());
      BigDecimal narucenaKolicina = new BigDecimal("10");
      BigDecimal cena = new BigDecimal("0.357");
      BigDecimal id = new BigDecimal(323942);
      WoRezervacija woRezervacija = new WoRezervacija();
      woRezervacija.setId(id);  */
        /*woRezervacija.setAkcija("");
   if (ocpProizvod.getTipAkcije() != null && ocpProizvod.getTipAkcije().equals(ProductService.PROIZVODI_NA_AKCIJI))
       woRezervacija.setAkcija("D");
   woRezervacija.setCena(cena);
   woRezervacija.setDatumivreme(Calendar.getInstance().getTime());
   woRezervacija.setIdjedinicemere(ocpProizvod.getJedinicaMere().getIdJediniceMere());
   woRezervacija.setIdSkladista(ocpProizvod.getMaticnoSkladiste());
   woRezervacija.setKolicina(narucenaKolicina);
   woRezervacija.setPoslovniPartner(user.getWoPartnerSetting().get(0).getPoslovniPartner());
   woRezervacija.setProizvod(ocpProizvod.getProizvod());
   woRezervacija.setStatusRezervacije(1);
   woRezervacija.setSessionid("181818181");
   woRezervacija.setRabat((ps.getRabatZaProizvod(ocpProizvod.getProizvod(), user.getWoPartnerSetting().get(0).getPoslovniPartner(), oj)).getRabat());
   if (woRezervacija.getRabat() == null)
       woRezervacija.setRabat(new BigDecimal(0));*/
        /*woRezervacijaDAO.merge(woRezervacija);


     OcpProizvod item = ocpProizvod;*/

                //System.out.println("proizvod je "+item);
            /*for (OcpVrAtrProizvod vra : item.getVrAtrProizvod())
          System.out.println(vra.getId().getAtribut() +" "+vra.getVrednost());*/
            }

        } catch (WOException e) {
            System.out.println((e.getMessage()));
        }
    }

    @Test
    public void testLogOn() {
        Integer oj = new Integer(19);
        CompanySetting cs = null; //appInitService.initApp();
        try {
            User user = logOnService.logOn("vera", "vera", cs, oj);
            System.out.println(" kornisk je identifiovan " + user.getWoUser().getNickname());
            for (WoPartnerSetting partnerSetting : user.getWoPartnerSetting())
                System.out.println(" za partnera " + partnerSetting.getPoslovniPartner() + " imamo " + partnerSetting.getIdSkladista());
            Iterator it = user.getCeneProizvoda().entrySet().iterator();
            /*while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();
                System.out.println(" cenovnik sadrži sledeće " + pairs.getKey() + " = " + pairs.getValue());
                it.remove(); // avoids a ConcurrentModificationException
            }     */

        } catch (WOException e) {
            e.getStackTrace();
        }

    }

    @Test
    public void testServise() {
        CompanySetting cs = null;// appInitService.initApp();
        System.out.println(" prosao je init ");
        Iterator it = cs.getKompanijaKorisnikMap().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println(" Kompanije " + pairs.getKey() + " = " + pairs.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
        /*
        it = cs.getKompanijskiParametri().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println(" Parametri " + pairs.getKey() + " = " + pairs.getValue());
            Iterator it1 = ((WoParametri)pairs.getValue()).getWoSetPoNacinPlacanja().iterator();
            while(it1.hasNext())
                System.out.println("PLACANJE JE "+ ((WoSetPoNacinPlacanja)it1.next()).getProdNacinPlacanja().getNaziv());
            it.remove(); // avoids a ConcurrentModificationException
        }

        it = cs.getMeni().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println(" Meni " + pairs.getKey() + " = " + pairs.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }

        it = cs.getFilterPrviNivo().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println(" Prvi Nivo " + pairs.getKey() + " = " + pairs.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }

        it = cs.getFilterDrugiNivo().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println(" Drugi Nivo " + pairs.getKey() + " = " + pairs.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
        */
        it = cs.getDownloadTip().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println(" Tipovi D " + pairs.getKey() + " = " + pairs.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }

        it = cs.getDownloadFilesZaTip().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println(" Tipovi D Fajlovi " + pairs.getKey() + " = " + pairs.getValue());
            it.remove(); // avoids a ConcurrentModificationException
            //Iterator it1 = pairs.getValue().entrySet().iterator();
            //Map.Entry pairs1 = (Map.Entry)
        }

    }

    @Test
    public void nekiTest() {

        BigDecimal bd = new BigDecimal(3.556);
        System.out.println(bd.divide(new BigDecimal(1.0), BigDecimal.ROUND_FLOOR));
    }

    @Test
    public void nekiNoviTest() {

        MathContext mc = new MathContext(1);

        BigDecimal obj_0 = new BigDecimal(.050),
                obj_1 = new BigDecimal(40.022);
        System.out.println("obj_0 value : " + obj_0);
        System.out.println("method generated " +
                "result : " + obj_0.round(mc));

        System.out.println("obj_1 value : " + obj_1);
        System.out.println("method generated result" +
                " : " + obj_1.round(mc));

        obj_0 = new BigDecimal(0.1220);
        obj_1 = new BigDecimal(.636);

        System.out.println("\nobj_0 value : " + obj_0);
        System.out.println("method generated result" +
                " : " + obj_0.round(mc));

        System.out.println("obj_1 value : " + obj_1);
        System.out.println("method generated result" +
                " : " + obj_1.round(mc));

        obj_0 = new BigDecimal(5.0);
        obj_1 = new BigDecimal(-1.0);

        System.out.println("\nobj_0 value : " + obj_0);
        System.out.println("method generated result" +
                " : " + obj_0.round(mc));

        System.out.println("obj_1 value : " + obj_1);
        System.out.println("method generated result" +
                " : " + obj_1.round(mc));

    }

    @Test
    public void nacnPlacanjaTest() {

        WoParametri woParametri = woParametriDAO.findById(new Integer(2));
        if (woParametri == null) System.out.println("nema parametara jbt");
        ProdNacinPlacanja prodNacinPlacanja = prodNacinPlacanjaDAO.findById("CAS");
        WoSetPoNacinPlacanja woSetPoNacinPlacanja = new WoSetPoNacinPlacanja();
        woSetPoNacinPlacanja.setParametri(woParametri);
        woSetPoNacinPlacanja.setProdNacinPlacanja(prodNacinPlacanja);
        woSetPoNacinPlacanja.setIdVdSkldok(new Integer(163));
        woSetPoNacinPlacanja.setVrstastavke(new Integer(25));
        woSetPoNacinPlacanja.setIdVdFindok(new Integer(500));
        woSetPoNacinPlacanjaDao.persist(woSetPoNacinPlacanja);


    }


    @Test
    public void tstPak() throws SQLException {

        ProdMaxRabati p = prodMaxRabatiDAO.findByKlasa(19, 2, "55555");

        System.out.println("max rabat je "+p.getMaxRabat());


    }

}
