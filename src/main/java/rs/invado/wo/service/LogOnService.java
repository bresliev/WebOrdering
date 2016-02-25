package rs.invado.wo.service;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.dao.admin.AdmUserCinposHome;
import rs.invado.wo.dao.ocp.OcpProizvodHome;
import rs.invado.wo.dao.ocp.OcpValutaHome;
import rs.invado.wo.dao.prod.ProdCenovnikHome;
import rs.invado.wo.dao.wo.WoParametriHome;
import rs.invado.wo.dao.wo.WoPartnerSettingsHome;
import rs.invado.wo.dao.wo.WoUserHome;
import rs.invado.wo.dao.wo.WoUserRightsHome;
import rs.invado.wo.domain.admin.AdminUserCinpos;
import rs.invado.wo.domain.wo.WoKompanijaKorisnik;
import rs.invado.wo.domain.wo.WoPartnerSetting;
import rs.invado.wo.domain.wo.WoUser;
import rs.invado.wo.domain.wo.WoUserHasRight;
import rs.invado.wo.dto.CompanySetting;
import rs.invado.wo.dto.Proizvodi;
import rs.invado.wo.dto.User;
import rs.invado.wo.util.WOException;
import rs.invado.wo.util.WOExceptionCodes;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikola
 * Date: 2.1.13.
 * Time: 17.09
 * To change this template use File | Settings | File Templates.
 */
@Repository
@Transactional
public class LogOnService {

    @Autowired
    private WoUserHome woUserDAO;
    @Autowired
    private WoPartnerSettingsHome wpscDAO;
    @Autowired
    private ProdCenovnikHome prodCenovnikDAO;
    @Autowired
    private AdmUserCinposHome adminUserCinposDAO;
    @Autowired
    private OcpValutaHome ocpValutaDAO;
    @Autowired
    ProductService productService;
    @Autowired
    OcpProizvodHome ocpProizvodDAO;
    @Autowired
    WoParametriHome woParametriDAO;
    @Autowired
    WoUserRightsHome woUserRightsDAO;


    private void setListaNajprodavanijih(CompanySetting cs, List<WoPartnerSetting> wpsc, User user) {
        //Map najprodavanijih artikala za menjajuću listu
        Map<String, Proizvodi> listaNajprodavanijih = new HashMap<String, Proizvodi>();
        Iterator it = cs.getKompanijaKorisnikMap().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            WoKompanijaKorisnik kompanija = (WoKompanijaKorisnik) pairs.getValue();
            listaNajprodavanijih.put(kompanija.getCorrespondingOJ() + "", productService.getProzivodiNaAkciji(ProductService.NAJPRODAVANIJE,
                    user.getCeneProizvoda(), 0, 10000, woParametriDAO.findActualSetOfParametersPerCompany(kompanija), wpsc,
                    cs.getTrasportnaPakovanja()));
        }
        cs.setListaNajprodavanijih(listaNajprodavanijih);
    }

    public User logOn(String userName, String password, CompanySetting cs, Integer OJ) throws WOException {
        User user = new User();
        WoUser woUser = woUserDAO.findUserByUsername(userName);
        //WoUser woUser = woUserDAO.autenticate(userName, password);
        if (woUser == null)
            throw new WOException(WOExceptionCodes.WO_UNEXESTING_USER);
        if (woUser.getUserType().equals(WoUser.USER_EXTERNAL)) {
            //slučaj eksternog korisnika
            if (!woUser.getPassword().equals(password)) {
                throw new WOException(WOExceptionCodes.WO_UNEXESTING_USER);
            }
            user.setWoUser(woUser);

        } else if (woUser.getUserType().equals(WoUser.USER_INTERNAL)) {

            if (adminUserCinposDAO.findByIdParameters(Integer.valueOf(woUser.getRadnik().getRadbr()),
                    AdminUserCinpos.OBJEKAT_ANALITIKE, OJ + "") == null)
                throw new WOException(WOExceptionCodes.WO_UNAUTHORIZED_USER);

            //slučaj internog korisnika, potrebno je kreritai konekciju
            //if (woUser.getRadnik().getObrJedinica())
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(
                        "jdbc:oracle:thin:@10.10.10.170:1526:darex", userName, password);
                /*connection = DriverManager.getConnection(
                        "jdbc:oracle:thin:@10.10.10.219:1521:test", userName, password);*/

                if (connection == null) {
                    throw new   WOException(WOExceptionCodes.WO_UNEXESTING_USER);
                } else {
                    connection.close();
                    user.setWoUser(woUser);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new WOException(WOExceptionCodes.WO_UNEXESTING_USER);
            }
        }
        List<WoPartnerSetting> wpsc = wpscDAO.findByPartnerIdForCurrentCompany(woUser.getOcpPoslovniPartner().getPoslovniPartner(),
                cs.getKompanijaKorisnikMap().get(OJ));
        if (wpsc.size() == 0)
            throw new WOException(WOExceptionCodes.WO_UNAUTHORIZED_USER);
        for (int i = 0; i < woUser.getOcpPoslovniPartner().getProdPpRabats().size(); i++) {

            if (woUser.getOcpPoslovniPartner().getProdPpRabats().get(i).getDatumOvere() != null && woUser.getOcpPoslovniPartner().getProdPpRabats().get(i).getDatumDo().after(new java.sql.Date(new java.util.Date().getTime())) &&
                    woUser.getOcpPoslovniPartner().getProdPpRabats().get(i).getOrganizacionaJedinica() == OJ.intValue() &&
                    woUser.getOcpPoslovniPartner().getProdPpRabats().get(i).getMaxRokPlacanja() > 0) {
                for (WoPartnerSetting setting : wpsc) {
                    setting.setMaxRokPlacanja(woUser.getOcpPoslovniPartner().getProdPpRabats().get(i).getMaxRokPlacanja());
                }
            }
        }
        user.setWoPartnerSetting(wpsc);
        Map<Integer, BigDecimal> ceneProizvoda = prodCenovnikDAO.findCeneMapped(wpsc.get(0));
        user.setCeneProizvoda(ceneProizvoda);
        user.setProdCenovnik(prodCenovnikDAO.findCenovnikAktuelni(wpsc.get(0)));
        user.setValuta(ocpValutaDAO.findByKlasa(wpsc.get(0), OJ));

//ovde
        Map<String, Boolean> woUserHasRights = new HashMap<String, Boolean>();
        for (WoUserHasRight rights : woUser.getWoUserHasRights())
            woUserHasRights.put(woUserRightsDAO.findById(rights.getId().getRigthId()).getAbbreviation(), rights.getValue());

        user.setWoUserHasRights(woUserHasRights);
        setListaNajprodavanijih(cs, wpsc, user);
        return user;
    }

    public void changePartner(int newPartnerId, CompanySetting cs, Integer OJ, User user) {
        List<WoPartnerSetting> wpsc = wpscDAO.findByPartnerIdForCurrentCompany(newPartnerId,
                cs.getKompanijaKorisnikMap().get(OJ));
        for (WoPartnerSetting setting: wpsc){
            setting.setMaxRokPlacanja(wpsc.get(0).getPoslovniPartner().getProdPpRabats().get(0).getMaxRokPlacanja());
        }

        if (wpsc != null && wpsc.size() > 0) {
            user.setWoPartnerSetting(wpsc);
            Map<Integer, BigDecimal> ceneProizvoda = prodCenovnikDAO.findCeneMapped(wpsc.get(0));
            user.setCeneProizvoda(ceneProizvoda);
            user.setProdCenovnik(prodCenovnikDAO.findCenovnikAktuelni(wpsc.get(0)));
            user.setValuta(ocpValutaDAO.findByKlasa(wpsc.get(0), OJ));
            user.getWoUser().setOcpPoslovniPartner(wpsc.get(0).getPoslovniPartner());
            user.setWoPartnerSetting(wpsc);
            setListaNajprodavanijih(cs, wpsc, user);
        }
    }
}
