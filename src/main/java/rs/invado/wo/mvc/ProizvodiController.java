package rs.invado.wo.mvc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javassist.Modifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import rs.invado.wo.domain.ocp.OcpKlasifikacija;
import rs.invado.wo.domain.ocp.OcpProizvod;
import rs.invado.wo.dto.CompanySetting;
import rs.invado.wo.dto.Proizvodi;
import rs.invado.wo.dto.User;
import rs.invado.wo.service.AppInitService;
import rs.invado.wo.service.ProductService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
public class ProizvodiController {
    private static final String ACTION_MENI = "MENI";
    private static final String ACTION_AKCIJA = "AKCIJA";
    private static final String ACTION_NOVO = "NOVO";
    private static final String ACTION_RASPRODAJA = "RASPRODAJA";
    private static final String ACTION_NAJAKTUELNIJE = "NAJAKTUELNIJE";
    private static final String ACTION_FILTER = "FILTER";


    @Autowired
    ProductService productService;


    @RequestMapping(value = "/getProductsJSON", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    String getProductsJSON(HttpSession session, String formAction, String brandId, int pageNo, int perPage, String namePattern, String productId) {
        ServletContext ctx = AppInitService.getServletConfig();
        CompanySetting companySetting = (CompanySetting) ctx.getAttribute(AppInitService.CompanySetting);
        User user = (User) session.getAttribute("loginUser");
        session.setAttribute("perPage", perPage);
        Integer oj = Integer.parseInt((String) session.getAttribute("oj"));
        Proizvodi proizvodi = null;
        if (ACTION_MENI.equals(formAction)) {
            proizvodi = productService.getProizvodiZaBrendSorted(brandId, user.getCeneProizvoda(), pageNo, perPage, companySetting.getKompanijskiParametri().get(oj), user.getWoPartnerSetting(), companySetting.getTrasportnaPakovanja(), companySetting, oj);
        } else if (ACTION_AKCIJA.equals(formAction)) {
            proizvodi = productService.getProzivodiNaAkcijiSorted(brandId, ProductService.PROIZVODI_NA_AKCIJI, user.getCeneProizvoda(), pageNo, perPage, companySetting.getKompanijskiParametri().get(oj), user.getWoPartnerSetting(), companySetting.getTrasportnaPakovanja());
        } else if (ACTION_NOVO.equals(formAction)) {
            proizvodi = productService.getProzivodiNaAkcijiSorted(brandId, ProductService.PROIZVODI_NOVO, user.getCeneProizvoda(), pageNo, perPage, companySetting.getKompanijskiParametri().get(oj), user.getWoPartnerSetting(), companySetting.getTrasportnaPakovanja());
        } else if (ACTION_NAJAKTUELNIJE.equals(formAction)) {
            proizvodi = productService.getProzivodiNaAkcijiSorted(brandId, ProductService.PROIZVODI_AKTUELNO, user.getCeneProizvoda(), pageNo, perPage, companySetting.getKompanijskiParametri().get(oj), user.getWoPartnerSetting(), companySetting.getTrasportnaPakovanja());
        } else if (ACTION_RASPRODAJA.equals(formAction)) {
            proizvodi = productService.getProzivodiNaAkcijiSorted(brandId, ProductService.PROIZVODI_NA_RASPRODAJI, user.getCeneProizvoda(), pageNo, perPage, companySetting.getKompanijskiParametri().get(oj), user.getWoPartnerSetting(), companySetting.getTrasportnaPakovanja());
        } else { //if (ACTION_FILTER.equals(formAction))
            if (productId != null && !"".equals(productId)) {
                OcpProizvod pro = productService.getProizvodById(Integer.parseInt(productId), user.getCeneProizvoda(), companySetting.getKompanijskiParametri().get(oj), user.getWoPartnerSetting(), companySetting.getTrasportnaPakovanja());
                if (pro != null) {
                    List lp = new ArrayList<OcpProizvod>();
                    lp.add(pro);
                    proizvodi = new Proizvodi(lp, 0, 1);
                }
            } else
                proizvodi = productService.getFilterProizvodiSorted(brandId, namePattern, user.getCeneProizvoda(), pageNo, perPage, companySetting.getKompanijskiParametri().get(oj), user.getWoPartnerSetting(), companySetting.getTrasportnaPakovanja());
        }
        Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC).create();
        return gson.toJson(proizvodi).replace("undefined","");
    }

    @RequestMapping(value = "/getFilterDrugiNivo", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    String getFilterDrugiNivo(@RequestParam String brandId) {
        ServletContext ctx = AppInitService.getServletConfig();
        CompanySetting companySetting = (CompanySetting) ctx.getAttribute(AppInitService.CompanySetting);
        List<OcpKlasifikacija> drugiNivo = companySetting.getFilterDrugiNivo().get(brandId);
        Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC).create();
        return gson.toJson(drugiNivo);
    }


    @RequestMapping(value = "/getProductsByName", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String getProductsByName(@RequestParam String term, String brandId, HttpSession session) {
        List<OcpProizvod> productList = null;
        User user = (User) session.getAttribute("loginUser");
        ServletContext ctx = AppInitService.getServletConfig();
        CompanySetting companySetting = (CompanySetting) ctx.getAttribute(AppInitService.CompanySetting);
        Integer oj = Integer.parseInt((String) session.getAttribute("oj"));
        /*if (brandId != null) {
            productList = productService.getProizvodiByNameAndByBrend(term, brandId, companySetting.getKompanijskiParametri().get(oj),
                    user.getWoPartnerSetting());
        } else productList= productService.getProizvoByNameSorted(term, user.getCeneProizvoda(), companySetting.getKompanijskiParametri().get(oj), user.getWoPartnerSetting(), companySetting.getTrasportnaPakovanja());*/

        productList = productService.getProizvodiByNameAndByBrend(term, brandId, companySetting.getKompanijskiParametri().get(oj), user.getWoPartnerSetting());

        Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC).create();
        return "{\"products\":" + gson.toJson(productList) + "}";
    }

}
