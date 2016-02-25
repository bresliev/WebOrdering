package rs.invado.wo.mvc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rs.invado.wo.domain.ocp.OcpProizvod;
import rs.invado.wo.domain.prod.ProdFinDokument;
import rs.invado.wo.domain.wo.WoRezervacija;
import rs.invado.wo.dto.CompanySetting;
import rs.invado.wo.dto.IncreaseReservation;
import rs.invado.wo.dto.User;
import rs.invado.wo.service.AppInitService;
import rs.invado.wo.service.BasketBusinessProcessing;
import rs.invado.wo.service.CommonService;
import rs.invado.wo.service.ProductService;
import rs.invado.wo.util.WOException;
import rs.invado.wo.util.WOExceptionCodes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Djordje
 * Date: 1/21/13
 * Time: 4:38 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class BasketControllerN {
    private static final Log log = LogFactory.getLog(BasketControllerN.class);
    @Autowired
    BasketBusinessProcessing basketService;
    @Autowired
    ProductService productService;
    @Autowired
    CommonService commonService;

    @RequestMapping(value = "/basket", method = RequestMethod.GET)
    public String getKorpa(HttpServletRequest req, HttpSession session, Model model) {
        User user = (User) req.getSession().getAttribute("loginUser");
        if (user.getBasket() == null || user.getBasket().size() == 0) req.setAttribute("errorMsg", "Korpa je prazna!");
        req.setAttribute("url", req.getServletPath());
        model.addAttribute("najprodavanije", getNajprodavanijeList(session));
        return "korpaView";
    }


    @RequestMapping(value = "/addToBasket", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String addToBasket(HttpSession session, String productId, String orderedQuantity, BigDecimal pakovanje) {
        log.info("proizvod=" + productId + " orderedQuantity="+orderedQuantity + " pakovanje=" + pakovanje);
        ServletContext ctx = AppInitService.getServletConfig();
        CompanySetting companySetting = (CompanySetting) ctx.getAttribute(AppInitService.CompanySetting);
        User user = (User) session.getAttribute("loginUser");
        Integer oj = Integer.parseInt((String) session.getAttribute("oj"));

        OcpProizvod product = productService.getProizvodById(Integer.parseInt(productId), user.getCeneProizvoda(), companySetting.getKompanijskiParametri().get(oj), user.getWoPartnerSetting(), companySetting.getTrasportnaPakovanja());
        BigDecimal orderedQ = new BigDecimal(orderedQuantity);
        IncreaseReservation increaseReservation = null;
        try {
            log.info("korpa pre " + user.getBasket().size() + "  raspolozivo=" + product.getRaspolozivo() + "   session=" + session.getId()+" sa pakovanjem "+pakovanje);
            basketService.increaseReservation(product, oj, orderedQ, session.getId(), user, pakovanje);
            product = productService.getProizvodById(Integer.parseInt(productId), user.getCeneProizvoda(), companySetting.getKompanijskiParametri().get(oj), user.getWoPartnerSetting(), companySetting.getTrasportnaPakovanja());
            session.setAttribute("loginUser", user);
            log.info("  korpa posle " + user.getBasket().size() + "  raspolozivo=" + product.getRaspolozivo() + "   session=" + session.getId());
            increaseReservation = new IncreaseReservation(user.getBasket().size(), user.getOrderValueWithTax(), product.getRaspolozivo(), product.getJedinicaMere().getSkracenaOznaka(), product.getKolUAltJM(),
                    product.getJedinicaMereAltRef().getSkracenaOznaka(), product.getPrimeniJsklPakovanje(), product.getJsklPakovanja(), product.getBrojPakovanja());
        } catch (WOException e) {
            increaseReservation = new IncreaseReservation(WOExceptionCodes.getInstance().getErrorMessage(e.getErrorCode()),user.getBasket().size(), user.getOrderValueWithTax(), product.getRaspolozivo(), product.getJedinicaMere().getSkracenaOznaka(), product.getKolUAltJM(), product.getJedinicaMereAltRef().getSkracenaOznaka());
            log.error("proizvod=" + productId + " sessionId=" + session.getId() + "  " + WOExceptionCodes.getInstance().getErrorMessage(e.getErrorCode()));
        } catch (Exception e) {
            increaseReservation = new IncreaseReservation(e.getMessage(),user.getBasket().size(), user.getOrderValueWithTax(), product.getRaspolozivo(), product.getJedinicaMere().getSkracenaOznaka(), product.getKolUAltJM(), product.getJedinicaMereAltRef().getSkracenaOznaka());
            log.error("proizvod=" + productId + " sessionId=" + session.getId() + "  " + e.getMessage(), e);
        }
        Gson gson = new GsonBuilder().create();
        return gson.toJson(increaseReservation);
    }


    @RequestMapping(value = "/removeFromBasket", method = RequestMethod.GET)
    public String removeFromBasket(HttpSession session, String productId) {
        ServletContext ctx = AppInitService.getServletConfig();
        CompanySetting companySetting = (CompanySetting) ctx.getAttribute(AppInitService.CompanySetting);
        User user = (User) session.getAttribute("loginUser");
        Integer oj = Integer.parseInt((String) session.getAttribute("oj"));


        BigDecimal kol = user.getBasket().get(productId).getKolicina();
        OcpProizvod product = productService.getProizvodById(Integer.parseInt(productId.split("/")[0]), user.getCeneProizvoda(), companySetting.getKompanijskiParametri().get(oj), user.getWoPartnerSetting(), companySetting.getTrasportnaPakovanja());
        basketService.decreaseReservation(product, oj, kol, session.getId(), user, productId);
        if (user.getBasket() != null && user.getBasket().size() == 0) {
            user.setBasket(new LinkedHashMap<String, WoRezervacija>());
            user.setOrderValue(new BigDecimal(0.00));
            user.setOrderValueWithTax(new BigDecimal(0.00));
        }
        session.setAttribute("loginUser", user);
        return "redirect:basket";

    }

    @RequestMapping(value = "/removeBasket", method = RequestMethod.GET)
    public String removeBasket(HttpSession session) {
        ServletContext ctx = AppInitService.getServletConfig();
        CompanySetting companySetting = (CompanySetting) ctx.getAttribute(AppInitService.CompanySetting);
        Integer oj = Integer.parseInt((String) session.getAttribute("oj"));

        User user = (User) session.getAttribute("loginUser");
        Map<String, WoRezervacija> temp = new HashMap<String, WoRezervacija>(user.getBasket());
        Set<String> keys = temp.keySet();
        for (String key : keys) {
            WoRezervacija rezervacija = user.getBasket().get(key);
            String basketIndex=rezervacija.getProizvod().getProizvod()+ "/" + (rezervacija.getKolPoPakovanju()!=null?rezervacija.getKolPoPakovanju():rezervacija.getProizvod().getKolicinaPoPakovanju());
            basketService.decreaseReservation(rezervacija.getProizvod(), oj, rezervacija.getKolicina(), session.getId(), user, basketIndex);
        }
        user.setBasket(new LinkedHashMap<String, WoRezervacija>());
        user.setOrderValue(new BigDecimal(0.00));
        user.setOrderValueWithTax(new BigDecimal(0.00));
        session.setAttribute("loginUser", user);
        return "redirect:basket";
    }


    @RequestMapping(value = "/chceckOutBasket", method = RequestMethod.POST)
    public String chceckOutBasket(HttpSession session, HttpServletRequest req, String nacinPlacanja, Integer nacinTransporta, String adresa, String napomena, String [] dodatniRabat, Model model) {
        try {
            ServletContext ctx = AppInitService.getServletConfig();
            CompanySetting companySetting = (CompanySetting) ctx.getAttribute(AppInitService.CompanySetting);
            User user = (User) session.getAttribute("loginUser");
            Integer oj = Integer.parseInt((String) session.getAttribute("oj"));

            if ("INTERNI".equals(user.getWoUser().getUserType()) && dodatniRabat != null && dodatniRabat.length > 0) {
                int index=0;
                Set<String> items=user.getBasket().keySet();
                for(String itemKey:items) {
                    if (dodatniRabat[index] != null && !"".equals(dodatniRabat[index].trim()))
                        user.getBasket().get(itemKey).setEkstraRabat(new BigDecimal(dodatniRabat[index]));
                    index++;
                }
            }

            List<ProdFinDokument> fakture = basketService.chceckOutBasket(user, companySetting, nacinPlacanja, nacinTransporta, adresa, napomena, oj, session.getId());
            List<String> faktureList = new ArrayList<String>();
            for (ProdFinDokument f : fakture) {
                faktureList.add(f.getId().getIdFinDokumenta() + "/" + f.getId().getOrganizacionaJedinica() + "/" + f.getId().getGodina() + "/" + f.getId().getIdVd());
                log.info(f.getId().getIdFinDokumenta() + "/" + f.getId().getOrganizacionaJedinica() + "/" + f.getId().getGodina() + "/" + f.getId().getIdVd());
            }
            session.setAttribute("fakture", fakture);
            req.setAttribute("protvrdaKorpe", "success");
            user.setBasket(new LinkedHashMap<String, WoRezervacija>());
            user.setOrderValue(new BigDecimal(0.00));
            user.setOrderValueWithTax(new BigDecimal(0.00));
            String reportUrl = "http://" + req.getLocalAddr() + ":" + req.getLocalPort() + "/WebOrdering/predracun";
            commonService.sendEmailConfirmation(reportUrl, companySetting.getKompanijskiParametri().get(oj), user, faktureList);

        } catch (Exception e) {
            log.error("nacinPlacanja=" + nacinPlacanja + " nacinTransporta=" + nacinTransporta + " adresa=" + adresa + " sessionId=" + session.getId() + "\n" + e.getMessage(), e);
        }
        model.addAttribute("najprodavanije", getNajprodavanijeList(session));
        return "korpaView";
    }

    private List<OcpProizvod> getNajprodavanijeList(HttpSession session) {
        Random rn = new Random();
        ServletContext ctx = AppInitService.getServletConfig();
        CompanySetting companySetting = (CompanySetting) ctx.getAttribute(AppInitService.CompanySetting);
        String oj = (String) session.getAttribute("oj");
        List<OcpProizvod> najprodavanije = companySetting.getListaNajprodavanijih().get(oj).getProizvodList();
        List<OcpProizvod> result = new ArrayList<OcpProizvod>();
        if (najprodavanije != null && najprodavanije.size() > 0) {
            int range = najprodavanije.size();
            OcpProizvod pro=null;
            int index=0;
            if (range>4) index=4;
            else index=range;
            for (int i = 0; i < index; i++) {
                while (pro==null || result.contains(pro)) pro = najprodavanije.get(rn.nextInt(range));
                result.add(pro);
            }
        }
        return result;

    }


}

