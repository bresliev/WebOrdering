package rs.invado.wo.mvc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javassist.Modifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import rs.invado.wo.domain.ocp.OcpKlasifikacija;
import rs.invado.wo.domain.ocp.OcpProizvod;
import rs.invado.wo.domain.wo.WoDownloadFiles;
import rs.invado.wo.domain.wo.WoDownloadTip;
import rs.invado.wo.dto.CompanySetting;
import rs.invado.wo.dto.Proizvodi;
import rs.invado.wo.dto.User;
import rs.invado.wo.service.AppInitService;
import rs.invado.wo.service.CommonService;
import rs.invado.wo.service.ProductService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Djordje
 * Date: 1/21/13
 * Time: 2:33 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class CommonController {
    @Autowired
    ProductService productService;
    @Autowired
    CommonService commonService;

    @RequestMapping(value = "home")
    public String getHomePage(HttpSession session, Model model) {
        ServletContext ctx = AppInitService.getServletConfig();
        CompanySetting companySetting = (CompanySetting) ctx.getAttribute(AppInitService.CompanySetting);
        User user = (User) session.getAttribute("loginUser");
        Integer oj = Integer.parseInt((String) session.getAttribute("oj"));
        Proizvodi proizvodi = productService.getProzivodiNaAkciji(ProductService.IZDVOJENA_AKCIJA, user.getCeneProizvoda(), 0, 3, companySetting.getKompanijskiParametri().get(oj), user.getWoPartnerSetting(), companySetting.getTrasportnaPakovanja());
        model.addAttribute("vesti", commonService.getVestiAktuelno(oj));
        model.addAttribute("flashSize", commonService.getFlashSize(oj).getNaslovnaflash());
        model.addAttribute("productList", proizvodi.getProizvodList());
        model.addAttribute("url", "/home");
        model.addAttribute("najprodavanije", getNajprodavanijeList(session));
        return "naslovnaView";
    }

    @RequestMapping(value = "contact")
    public String getContactsPage(HttpSession session, Model model) {
        Integer oj = Integer.parseInt((String) session.getAttribute("oj"));
        model.addAttribute("url", "/contact");
        model.addAttribute("flashSize", commonService.getFlashSize(oj).getKontaktflash());
        model.addAttribute("najprodavanije", getNajprodavanijeList(session));
        return "contactView";
    }

    @RequestMapping(value = "support")
    public String getSupportPage(HttpSession session, Model model) {
        Integer oj = Integer.parseInt((String) session.getAttribute("oj"));
        model.addAttribute("url", "/support");
        model.addAttribute("flashSize", commonService.getFlashSize(oj).getPodrskaflash());
        model.addAttribute("najprodavanije", getNajprodavanijeList(session));
        return "supportView";
    }
 /*
    @RequestMapping(value = "/getGroupsJSON", method = RequestMethod.GET, produces = "application/json")
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
            proizvodi = productService.getProzivodiNaAkcijiSorted(ProductService.PROIZVODI_NA_AKCIJI, user.getCeneProizvoda(), pageNo, perPage, companySetting.getKompanijskiParametri().get(oj), user.getWoPartnerSetting(), companySetting.getTrasportnaPakovanja());
        } else if (ACTION_NOVO.equals(formAction)) {
            proizvodi = productService.getProzivodiNaAkcijiSorted(ProductService.PROIZVODI_NOVO, user.getCeneProizvoda(), pageNo, perPage, companySetting.getKompanijskiParametri().get(oj), user.getWoPartnerSetting(), companySetting.getTrasportnaPakovanja());
        } else if (ACTION_NAJAKTUELNIJE.equals(formAction)) {
            proizvodi = productService.getProzivodiNaAkcijiSorted(ProductService.PROIZVODI_AKTUELNO, user.getCeneProizvoda(), pageNo, perPage, companySetting.getKompanijskiParametri().get(oj), user.getWoPartnerSetting(), companySetting.getTrasportnaPakovanja());
        } else if (ACTION_RASPRODAJA.equals(formAction)) {
            proizvodi = productService.getProzivodiNaAkcijiSorted(ProductService.PROIZVODI_NA_RASPRODAJI, user.getCeneProizvoda(), pageNo, perPage, companySetting.getKompanijskiParametri().get(oj), user.getWoPartnerSetting(), companySetting.getTrasportnaPakovanja());
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
    */

    @RequestMapping(value = "/getGroupsJSON", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String getProdOnActionPage(HttpSession session, Model model, String formAction) {
        ServletContext ctx = AppInitService.getServletConfig();
        CompanySetting companySetting = (CompanySetting) ctx.getAttribute(AppInitService.CompanySetting);
        Integer oj = Integer.parseInt((String) session.getAttribute("oj"));
        User user = (User) session.getAttribute("loginUser");
        List<OcpKlasifikacija> listaKlasa = commonService.findKlasifikacijaWithAction(companySetting, formAction, oj.intValue(), user);

        model.addAttribute("groupList", listaKlasa);
        model.addAttribute("url", "/akcija");
        model.addAttribute("flashSize", commonService.getFlashSize(oj).getPodrskaflash());
        model.addAttribute("najprodavanije", getNajprodavanijeList(session));
        Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC).create();
        return gson.toJson(listaKlasa);
    }

    @RequestMapping(value = "download")
    public String getDownload(HttpSession session, Model model, String tipDownload) {
        ServletContext ctx = AppInitService.getServletConfig();
        CompanySetting companySetting = (CompanySetting) ctx.getAttribute(AppInitService.CompanySetting);
        String oj = (String) session.getAttribute("oj");
        List<WoDownloadTip> downloadTipList = companySetting.getDownloadTip().get(oj);
        if (downloadTipList != null && downloadTipList.size() > 0) {
            String naziv = "";
            if (tipDownload == null) {
                tipDownload = "" + downloadTipList.get(0).getId();
                naziv = downloadTipList.get(0).getNaziv();
            } else {
                for (WoDownloadTip t : downloadTipList) {
                    if (tipDownload.equals(t.getId() + "")) {
                        naziv = t.getNaziv();
                        break;
                    }
                }
            }
            Map<String, List<WoDownloadFiles>> downloadFilesList = companySetting.getDownloadFilesZaTip().get(tipDownload);
            model.addAttribute("downloadFiles", downloadFilesList);
            model.addAttribute("naziv", naziv);
        }
        model.addAttribute("url", "/download");
        model.addAttribute("najprodavanije", getNajprodavanijeList(session));
        return "downloadView";
    }

    @RequestMapping(value = "oprema")
    public String getOpremaIVozila(HttpSession session, Model model) {
        model.addAttribute("url", "/oprema");
        model.addAttribute("najprodavanije", getNajprodavanijeList(session));
        return "opremaView";
    }

    @RequestMapping(value = "/getPartnersByName", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String getPartnersByName(@RequestParam String term) {
        Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC).create();
        return "{\"partners\":" + gson.toJson(commonService.findPartnerByName(term)) + "}";
    }


    @RequestMapping(value = "/getNajprodavanije", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String getNajprodavanije(HttpSession session) {
        Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC).create();
        return gson.toJson(getNajprodavanijeList(session));
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
