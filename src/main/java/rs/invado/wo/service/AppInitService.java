package rs.invado.wo.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ServletContextAware;
import rs.invado.wo.dao.ocp.OcpKlasifikacijaHome;
import rs.invado.wo.dao.uz.UzDozvPakovanjaHome;
import rs.invado.wo.dao.wo.WoDownloadFilesHome;
import rs.invado.wo.dao.wo.WoDownloadTipHome;
import rs.invado.wo.dao.wo.WoKompanijaKorisnikHome;
import rs.invado.wo.dao.wo.WoParametriHome;
import rs.invado.wo.dao.wo.WoDownloadGrupaHome;
import rs.invado.wo.domain.ocp.OcpKlasifikacija;
import rs.invado.wo.domain.wo.*;
import rs.invado.wo.dto.CompanySetting;

import javax.servlet.ServletContext;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikola
 * Date: 2.1.13.
 * Time: 20.12
 * To change this template use File | Settings | File Templates.
 */

@Transactional
@Service
public class AppInitService implements ServletContextAware {
    private static ServletContext ctx;
    private static final Log log = LogFactory.getLog(AppInitService.class);
    public static final String CompanySetting = "CompanySetting";


    @Autowired
    OcpKlasifikacijaHome ocpKlasifikacijaDAO;
    @Autowired
    WoKompanijaKorisnikHome woKompanijaKorisnikDAO;
    @Autowired
    WoParametriHome woParametriDAO;
    @Autowired
    UzDozvPakovanjaHome uzDozvPakovanjaDAO;
    @Autowired
    WoDownloadTipHome woDownloadTipDAO;
    @Autowired
    WoDownloadFilesHome woDownloadFilesDAO;
    @Autowired
    WoDownloadGrupaHome woDownloadGrupaDAO;


    public CompanySetting initApp() {

        CompanySetting cs = new CompanySetting();
        //lista kompaija korisnika aplikacije
        List<WoKompanijaKorisnik> kompanije = woKompanijaKorisnikDAO.findAllCompanies();
        //mapa objekata kompanija korisnika apliakcija. Key mape je id kompanije
        Map<Integer, WoKompanijaKorisnik> companyMap = new HashMap<Integer, WoKompanijaKorisnik>(0);
        // Mapa setinga karakterističnih za svaku kompaniju korinsik. Key mape je id kompanije. Ova je mapa veoma važna jer
        //sadrži i načine plaćanje preko parametera.
        Map<Integer, WoParametri> companySettingsMap = new HashMap<Integer, WoParametri>(0);
        //Mapa klasifikacija za meni po kompanijama korisnicima. Key je id kompanije
        Map<String, List<OcpKlasifikacija>> meniji = new HashMap<String, List<OcpKlasifikacija>>(0);
        //Klasifikacija za prvi nivo filtera. Key je id kompanije
        Map<String, List<OcpKlasifikacija>> filterPrviNivo = new HashMap<String, List<OcpKlasifikacija>>(0);
        //Klasifikacija za drugi nivo filtera. Key je u obliku 'idKompanije/nadrdjenaklasa'. Npr 49/01
        Map<String, List<OcpKlasifikacija>> filterDrugiNivo = new HashMap<String, List<OcpKlasifikacija>>(0);
        //Transportna pakovanja za sve proizvode koji imaju definisana pakovanja. Key je id proivzoda
        Map<Integer, BigDecimal> transportnaPakovanja = new HashMap<Integer, BigDecimal>(0);
        //Mapa tipova za download.Key je kompanija.
        Map<String, List<WoDownloadTip>> downloadTip = new HashMap<String, List<WoDownloadTip>>(0);
        //Mapa fajlova za odgovarajući tip. Key je tip za download.
        Map<String, Map<String, List<WoDownloadFiles>>> downloadFilesZaTip = new HashMap<String, Map<String, List<WoDownloadFiles>>>(0);

        for (WoKompanijaKorisnik kompanija : kompanije) {
            companyMap.put(kompanija.getCorrespondingOJ(), kompanija);
            companySettingsMap.put(kompanija.getCorrespondingOJ(),woParametriDAO.findActualSetOfParametersPerCompany(kompanija));
            if (companySettingsMap.get(kompanija.getCorrespondingOJ()) != null) {
                meniji.put(kompanija.getCorrespondingOJ() + "",
                        ocpKlasifikacijaDAO.findHierarchy(companySettingsMap.get(kompanija.getCorrespondingOJ()).getVrstaKlasifikacijeMeni(), null, 0, "klasifikacija#" ));
                List<OcpKlasifikacija> listaPrviNivo = ocpKlasifikacijaDAO.findHierarchy(companySettingsMap.get(kompanija.getCorrespondingOJ()).getVrstaKlasifikacijeFilter(),
                        null, 1, "naziv");

                filterPrviNivo.put(kompanija.getCorrespondingOJ() + "", listaPrviNivo);
                for (OcpKlasifikacija prviNivoKlasa : listaPrviNivo) {
                    filterDrugiNivo.put(kompanija.getCorrespondingOJ() + "/" + prviNivoKlasa.getId().getKlasifikacija(),
                            ocpKlasifikacijaDAO.findHierarchy(companySettingsMap.get(kompanija.getCorrespondingOJ()).getVrstaKlasifikacijeFilter(),
                                    prviNivoKlasa.getId().getKlasifikacija(), companySettingsMap.get(kompanija.getCorrespondingOJ()).getDrugiNivoFiltera(), "klasifikacijca#"));
                }
            }

            transportnaPakovanja = uzDozvPakovanjaDAO.findTransportnaPakovanja();
            //setuj mape za download
            List<WoDownloadTip> downloadTips = woDownloadTipDAO.findActivePerCompany(new BigDecimal(kompanija.getId().intValue()));
            downloadTip.put(kompanija.getCorrespondingOJ() + "", downloadTips);
            for (WoDownloadTip tip : downloadTips) {
                //Mapa fajlova po bradnu
                Map<String, List<WoDownloadFiles>> downloadFilesZaBrand = new LinkedHashMap<String, List<WoDownloadFiles>>(0);
                downloadFilesZaTip.put(tip.getId() + "", null);
                List<WoDownloadFiles> woDownloadFiles = woDownloadFilesDAO.findWoDownloadFIlesPerTipAndCompany(tip.getId());
                for (WoDownloadFiles woDownloadFileForBrand : woDownloadFiles) {
                    WoDownloadGrupa woDownloadGrupa = woDownloadGrupaDAO.findById(woDownloadFileForBrand.getWoDownloadGrupa().getId());
                    /*
                    OcpKlasifikacijaId brandId = new OcpKlasifikacijaId();
                    brandId.setKlasifikacija(woDownloadFileForBrand.getKlasifikacija());
                    brandId.setVrstaKlasifikacije(woDownloadFileForBrand.getVrstaKalsifikacija().intValue());
                    OcpKlasifikacija ocpKlasifikacija = ocpKlasifikacijaDAO.findById(brandId);  */
                    if (downloadFilesZaBrand.containsKey(woDownloadGrupa.getNaziv())) {
                        downloadFilesZaBrand.get(woDownloadGrupa.getNaziv()).add(woDownloadFileForBrand);
                    } else {
                        List<WoDownloadFiles> woDownloadFilesList = new ArrayList<WoDownloadFiles>(0);
                        woDownloadFilesList.add(woDownloadFileForBrand);
                        downloadFilesZaBrand.put(woDownloadGrupa.getNaziv(), woDownloadFilesList);
                    }
                }
                downloadFilesZaTip.put(tip.getId() + "", downloadFilesZaBrand);
            }
        }
        cs.setKompanijaKorisnikMap(companyMap);
        cs.setKompanijskiParametri(companySettingsMap);
        cs.setMeni(meniji);
        cs.setFilterPrviNivo(filterPrviNivo);
        cs.setFilterDrugiNivo(filterDrugiNivo);
        cs.setTrasportnaPakovanja(transportnaPakovanja);
        cs.setDownloadTip(downloadTip);
        cs.setDownloadFilesZaTip(downloadFilesZaTip);
        return cs;
    }

    public void setServletContext(ServletContext servletContext) {
        log.info("=================WebOrdering inicijalizacija===================");
        servletContext.setAttribute("CompanySetting", initApp());
        ctx = servletContext;
    }

    public static ServletContext getServletConfig() {
        return ctx;
    }
}
