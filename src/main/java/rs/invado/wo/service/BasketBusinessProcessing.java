package rs.invado.wo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.dao.ocp.*;
import rs.invado.wo.dao.prod.ProdFinDokumentHome;
import rs.invado.wo.dao.prod.ProdMaxRabatiHome;
import rs.invado.wo.dao.prod.ProdNacinPlacanjaHome;
import rs.invado.wo.dao.prod.ProdPoreskaStopaHome;
import rs.invado.wo.dao.uz.*;
import rs.invado.wo.dao.wo.WoKompanijaKorisnikHome;
import rs.invado.wo.dao.wo.WoRezervacijaHome;
import rs.invado.wo.dao.wo.WoRezervacijaSastavaHome;
import rs.invado.wo.dao.wo.WoSetPoNacinPlacanjaHome;
import rs.invado.wo.domain.ocp.*;
import rs.invado.wo.domain.prod.ProdFinDokument;
import rs.invado.wo.domain.prod.ProdPpRabat;
import rs.invado.wo.domain.uz.*;
import rs.invado.wo.domain.wo.*;
import rs.invado.wo.dto.CompanySetting;
import rs.invado.wo.dto.User;
import rs.invado.wo.util.WOException;
import rs.invado.wo.util.WOExceptionCodes;
import rs.invado.wo.util.WOUtil;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User:
 * Date: 13.2.13.
 * Time: 22.54
 * To change this template use File | Settings | File Templates.
 */
@Repository
@Transactional
@Service
public class BasketBusinessProcessing {


    @Autowired
    private ProductService productService;
    @Autowired
    private WoRezervacijaHome woRezervacijaDAO;
    @Autowired
    private UzStanjeZalihaSkladistaHome uzStanjeZalihaSkladistaDAO;
    @Autowired
    private WoSetPoNacinPlacanjaHome woSetPoNacinPlacanjaDAO;
    @Autowired
    private UzSkladisteHome uzSkladisteDAO;
    @Autowired
    private UzDokumentHome uzDokumentDAO;
    @Autowired
    private ProdNacinPlacanjaHome prodNacinPlacanjaDAO;
    @Autowired
    private UzDokumentUsloviPlacanjaHome uzDokumentUsloviPlacanjaDAO;
    @Autowired
    private UzDokumentStavkaHome uzDokumentStavkaDAO;
    @Autowired
    private ProdFinDokumentHome prodFinDokumentDAO;
    @Autowired
    private UzDokumentOjHome uzDokumentOjDAO;
    @Autowired
    private ProdPoreskaStopaHome prodPoreskaStopaDAO;
    @Autowired
    private WoKompanijaKorisnikHome woKompanijaKorisnikDAO;
    @Autowired
    private OcpKlasifikacijaHome woKlasifikacijaDAO;
    @Autowired
    private UzDokumentStavkaPakovanjeHome uzDokumentStavkaPakovanjeDAO;
    @Inject
    private WoRezervacijaSastavaHome woRezervacijaSastavaDAO;
    @Inject
    private ProdMaxRabatiHome prodMaxRabatiDAO;
    @Inject
    private OcpKlasifikacijaProizvodaHome ocpKlasifikacijaProizvodaDAO;
    @Autowired
    private OcpLiceZaKontaktHome ocpLiceZaKontaktDAO;
    @Autowired
    private OcpTelefonskiBrojHome ocpTelefonskiBrojDAO;
    @Autowired
    private OcpAdresaIsporukeHome ocpAdresaIsporukeDAO;


    private enum ProAkcija {
        D, N
    }

    ;

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    private WoRezervacija getBasketElement(Map<String, WoRezervacija> basket, String basketIndex) {
        return basket.get(basketIndex);
    }

    private void addBasketElement(Map<String, WoRezervacija> basket, String basketIndex, WoRezervacija woRezervacija) {
        basket.put(basketIndex, woRezervacija);
    }

    private void updateExistingBasketElemnt(WoRezervacija woRezervacija, BigDecimal kolicinaZaRezervaciju, BigDecimal operand) {
        woRezervacija.setKolicina(woRezervacija.getKolicina().add(kolicinaZaRezervaciju.multiply(operand)));
        woRezervacija.setVrednost(woRezervacija.getKolicina().multiply(woRezervacija.getCena()).setScale(3, RoundingMode.HALF_EVEN));
        for (WoRezervacijaSastava woRezervacijaSastava : woRezervacija.getWoRezervacijaSastavaList())
            woRezervacijaSastava.setKolicinaUgradnje(woRezervacijaSastava.getKolicina().add(kolicinaZaRezervaciju.multiply(operand).multiply(woRezervacijaSastava.getKolicinaUgradnje())));
    }

    public void increaseReservationCompositeObject(OcpProizvod ocpProizvod, int currentOJ, BigDecimal narucenaKolicina, String sessionId, User user,
                                                   BigDecimal pakovanje)
            throws WOException {


        BigDecimal aktuelnoPakovanje = pakovanje == null ? ocpProizvod.getKolicinaPoPakovanju() : pakovanje;
        String basketIndex = ocpProizvod.getProizvod() + "/" + aktuelnoPakovanje;
        BigDecimal rabatZaProizvod = new BigDecimal(0);

        WoKompanijaKorisnik woKompanijaKorisnik = woKompanijaKorisnikDAO.findByCoresponingOJ(currentOJ);
        Integer skladisteRezervacije = ocpProizvod.getMaticnoSkladiste();

        BigDecimal operacija = new BigDecimal(1);
        //insertuj stavku u wo_rezervacija za tekuću sesiju
        WoRezervacija woRezervacija = getBasketElement(user.getBasket(), basketIndex);
        if (woRezervacija != null) {
            updateExistingBasketElemnt(woRezervacija, narucenaKolicina.multiply(aktuelnoPakovanje), operacija);
            WoRezervacija woRezervacijaPersistent = woRezervacijaDAO.findByWoRezervacijaById(woRezervacija.getId());
            woRezervacijaPersistent.setKolicina(woRezervacija.getKolicina());
            user.setOrderValue(woRezervacija.getVrednost());
            user.setOrderValueWithTax(user.getOrderValue().add(user.getOrderValue().multiply(ocpProizvod.getStopaPoreza()
                    .divide(new BigDecimal("100")))).setScale(3, RoundingMode.HALF_EVEN));
            woRezervacijaDAO.persist(woRezervacijaPersistent);
        } else {
            woRezervacija = new WoRezervacija();
            woRezervacija.setAkcija(" ");
            if (ocpProizvod.getTipAkcije() != null) {
                if (ocpProizvod.getTipAkcije().equals(ProductService.PROIZVODI_NA_AKCIJI)
                        || ocpProizvod.getTipAkcije().equals(ProductService.PROIZVODI_NA_RASPRODAJI)
                        || ocpProizvod.getTipAkcije().equals(ProductService.IZDVOJENA_AKCIJA)) {
                    woRezervacija.setAkcija("D");
                }
            }
            woRezervacija.setCena(ocpProizvod.getCena());
            woRezervacija.setDatumivreme(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            woRezervacija.setIdjedinicemere(ocpProizvod.getJedinicaMere().getIdJediniceMere());
            woRezervacija.setIdSkladista(skladisteRezervacije);
            woRezervacija.setKolicina(narucenaKolicina.multiply(aktuelnoPakovanje));
            woRezervacija.setPoslovniPartner(user.getWoPartnerSetting().get(0).getPoslovniPartner().getPoslovniPartner());
            woRezervacija.setProizvod(ocpProizvod);
            woRezervacija.setStatusRezervacije(1);
            woRezervacija.setSessionid(sessionId);
            woRezervacija.setKolPoPakovanju(aktuelnoPakovanje);
            rabatZaProizvod = (productService.getRabatZaProizvod(ocpProizvod.getProizvod(), user.getWoPartnerSetting().get(0).getPoslovniPartner().getPoslovniPartner(), currentOJ)).getRabat();
            woRezervacija.setRabat(ocpProizvod.getMaxRabat().compareTo(new BigDecimal(-1)) != 0 && rabatZaProizvod.compareTo(ocpProizvod.getMaxRabat()) == 1 ? ocpProizvod.getMaxRabat() : rabatZaProizvod);
            if (woRezervacija.getRabat() == null || (ocpProizvod.getTipAkcije() != null && (ocpProizvod.getTipAkcije().equals(ProductService.PROIZVODI_NA_AKCIJI)
                    || ocpProizvod.getTipAkcije().equals(ProductService.IZDVOJENA_AKCIJA)
                    || ocpProizvod.getTipAkcije().equals(ProductService.PROIZVODI_NA_RASPRODAJI))))
                woRezervacija.setRabat(new BigDecimal(0));
            woRezervacija.setVrednost(woRezervacija.getKolicina().multiply(woRezervacija.getCena()).setScale(3, RoundingMode.HALF_EVEN));
            woRezervacija.setVrednostSaPorezom(woRezervacija.getVrednost()
                    .add(ocpProizvod.getStopaPoreza().multiply(woRezervacija.getVrednost().divide(new BigDecimal("100")))).setScale(3, RoundingMode.HALF_EVEN));
            user.setOrderValue(user.getOrderValue().add(woRezervacija.getCena().multiply(woRezervacija.getKolicina())).setScale(3, RoundingMode.HALF_EVEN));
            user.setOrderValueWithTax(user.getOrderValueWithTax().add(woRezervacija.getCena()
                    .multiply(narucenaKolicina.multiply(aktuelnoPakovanje)).add(woRezervacija.getCena()
                            .multiply(narucenaKolicina.multiply(aktuelnoPakovanje)).multiply(ocpProizvod.getStopaPoreza().divide(new BigDecimal("100"))))).setScale(3, RoundingMode.HALF_EVEN));
            woRezervacija.setWoUser(user.getWoUser());
            woRezervacijaDAO.persist(woRezervacija);
        }


        for (OcpSastavProizvoda ocpSastavProizvoda : ocpProizvod.getSastavProizvoda()) {
            skladisteRezervacije = ocpSastavProizvoda.getMaticnoSkladiste();
            //poevećaj rezervaciju u magacinu
            UzStanjeZalihaSkladistaId uzStanjeZalihaSkladistaId = new UzStanjeZalihaSkladistaId();
            uzStanjeZalihaSkladistaId.setIdSkladista(ocpSastavProizvoda.getMaticnoSkladiste());
            uzStanjeZalihaSkladistaId.setProizvod(ocpSastavProizvoda.getProizvodIzlaz().getProizvod());
            UzStanjeZalihaSkladista uzStanjeZalihaSkladista = uzStanjeZalihaSkladistaDAO.findById(uzStanjeZalihaSkladistaId);
            if (uzStanjeZalihaSkladista == null || narucenaKolicina.multiply(aktuelnoPakovanje).multiply(ocpSastavProizvoda.getKolicinaUgradnje())
                    .compareTo(WOUtil.trimToZero(uzStanjeZalihaSkladista.getKolicinaPoStanjuZ()).subtract(WOUtil.trimToZero(uzStanjeZalihaSkladista.getRezervisanaKol()))) == 1) {
                throw new WOException(WOExceptionCodes.WO_INSUFFICIENT_SKU_QUANTITY);
            } else {
                if (!woKompanijaKorisnik.getWoMapKompanijskaSkladistas().isEmpty()) {
                    for (WoMapKompanijskaSkladista woMapKompanijskaSkladista : woKompanijaKorisnik.getWoMapKompanijskaSkladistas()) {
                        if (woMapKompanijskaSkladista != null && woMapKompanijskaSkladista.getUzSkladisteRaspolozivo() != null)
                            if (woMapKompanijskaSkladista.getUzSkladisteRaspolozivo().getIdSkladista() == uzStanjeZalihaSkladistaId.getIdSkladista()) {
                                if (woMapKompanijskaSkladista.getRezervisiURaspolozivo().equals(1)) {
                                    //rezervi??i robu u magacinu koji daje raspolozivu kolicinu
                                    uzStanjeZalihaSkladistaDAO.azurirajRezervisanuKolicinu(uzStanjeZalihaSkladistaId, narucenaKolicina.multiply(ocpProizvod.getKolicinaPoPakovanju().multiply(ocpSastavProizvoda.getKolicinaUgradnje())).doubleValue(), 1);
                                }
                                uzStanjeZalihaSkladistaId.setIdSkladista(woMapKompanijskaSkladista.getUzSkladisteRezervacija().getIdSkladista());
                                uzStanjeZalihaSkladistaId.setProizvod(ocpProizvod.getProizvod());
                                //rezervi??i robu u magacinu na koji se polazni magacin mapira
                                uzStanjeZalihaSkladistaDAO.azurirajRezervisanuKolicinu(uzStanjeZalihaSkladistaId, narucenaKolicina.multiply(ocpProizvod.getKolicinaPoPakovanju().multiply(ocpSastavProizvoda.getKolicinaUgradnje())).doubleValue(), 1);
                                skladisteRezervacije = uzStanjeZalihaSkladistaId.getIdSkladista();
                            }
                    }

                } else {
                    uzStanjeZalihaSkladistaDAO.azurirajRezervisanuKolicinu(uzStanjeZalihaSkladistaId, narucenaKolicina.multiply(aktuelnoPakovanje).multiply(ocpSastavProizvoda.getKolicinaUgradnje()).doubleValue(), 1);
                }

            /*kolciina za rezervaciju se mno??i sa operacijom tako da se koli�?ina u objektu povećava ako je vrednost operacije 1,
  smanjuje ako je vrednost operacije -1 ??to je slu�?aj kod umanjenja*/

                //umanjiti kolicinu rasplozivu na objektu.
                ocpProizvod.setRaspolozivo(ocpProizvod.getRaspolozivo().subtract(narucenaKolicina.multiply(aktuelnoPakovanje)));
                ocpProizvod.setKolUAltJM(ocpProizvod.getRaspolozivo().divide(aktuelnoPakovanje, 0, RoundingMode.FLOOR).intValue());

                WoRezervacijaSastava woRezervacijaSastava = woRezervacijaSastavaDAO.findByWoRezervacijaByIdAndProizvod(woRezervacija, ocpSastavProizvoda.getProizvodIzlaz());
                /*
                for (WoRezervacijaSastava woRezervacijaSastavaExiting : woRezervacija.getWoRezervacijaSastavaList()) {
                    if (woRezervacijaSastavaExiting.getProizvod().getProizvod().equals(ocpSastavProizvoda.getProizvodIzlaz()) &&
                            woRezervacijaSastavaExiting.getWoRezervacija().getId() == woRezervacija.getId())
                        woRezervacijaSastava = woRezervacijaSastavaExiting;
                }*/

                if (woRezervacijaSastava != null) {
                    woRezervacijaSastava.setKolicina(woRezervacijaSastava.getKolicina().add(narucenaKolicina.multiply(ocpProizvod.getKolicinaPoPakovanju().multiply(ocpSastavProizvoda.getKolicinaUgradnje()))));
                    for (WoRezervacijaSastava woRezervacijaSastavaExisting : woRezervacija.getWoRezervacijaSastavaList()) {
                        if (woRezervacijaSastava.getProizvod().getProizvod().equals(woRezervacijaSastavaExisting.getProizvod().getProizvod())) {
                            woRezervacijaSastavaExisting.setKolicina(woRezervacijaSastava.getKolicina());
                        }
                    }
                } else {
                    woRezervacijaSastava = new WoRezervacijaSastava();
                    woRezervacijaSastava.setWoRezervacija(woRezervacija);
                    woRezervacijaSastava.setKolicina(narucenaKolicina.multiply(ocpProizvod.getKolicinaPoPakovanju().multiply(ocpSastavProizvoda.getKolicinaUgradnje())));
                    woRezervacijaSastava.setCena(ocpSastavProizvoda.getProizvodIzlaz().getCena());
                    rabatZaProizvod = (productService.getRabatZaProizvod(ocpSastavProizvoda.getProizvodIzlaz().getProizvod(), user.getWoPartnerSetting().get(0).getPoslovniPartner().getPoslovniPartner(), currentOJ)).getRabat();
                    ocpSastavProizvoda.getProizvodIzlaz().setOcpKlasifikacijaProizvoda(ocpKlasifikacijaProizvodaDAO.findByProizvod(ocpSastavProizvoda.getProizvodIzlaz()));
                    for (OcpKlasifikacijaProizvoda ocpKlasifikacija : ocpSastavProizvoda.getProizvodIzlaz().getOcpKlasifikacijaProizvoda()) {
                        ocpSastavProizvoda.getProizvodIzlaz().setMaxRabat(prodMaxRabatiDAO.findByKlasa(currentOJ, ocpKlasifikacija.getId().getVrstaKlasifikacije(), ocpKlasifikacija.getId().getKlasifikacija()).getMaxRabat());
                        if (ocpSastavProizvoda.getProizvodIzlaz().getMaxRabat().compareTo(new BigDecimal(-1)) != 0)
                            break;
                    }
                    woRezervacijaSastava.setRabat(ocpSastavProizvoda.getProizvodIzlaz().getMaxRabat().compareTo(new BigDecimal(-1)) != 0 && rabatZaProizvod.compareTo(ocpSastavProizvoda.getProizvodIzlaz().getMaxRabat()) == 1 ? ocpSastavProizvoda.getProizvodIzlaz().getMaxRabat() : rabatZaProizvod);
                    if (woRezervacijaSastava.getRabat() == null || (ocpSastavProizvoda.getProizvodIzlaz().getTipAkcije() != null && (ocpSastavProizvoda.getProizvodIzlaz().getTipAkcije().equals(ProductService.PROIZVODI_NA_AKCIJI)
                            || ocpSastavProizvoda.getProizvodIzlaz().getTipAkcije().equals(ProductService.IZDVOJENA_AKCIJA)
                            || ocpSastavProizvoda.getProizvodIzlaz().getTipAkcije().equals(ProductService.PROIZVODI_NA_RASPRODAJI))))
                        woRezervacijaSastava.setRabat(new BigDecimal(0));
                    woRezervacijaSastava.setIdjedinicemere(ocpSastavProizvoda.getProizvodIzlaz().getJedinicaMere().getIdJediniceMere());
                    woRezervacijaSastava.setIdSkladista(ocpSastavProizvoda.getMaticnoSkladiste());
                    woRezervacijaSastava.setKolPoPakovanju(narucenaKolicina);
                    woRezervacijaSastava.setKolicinaUgradnje(ocpSastavProizvoda.getKolicinaUgradnje());
                    woRezervacijaSastava.setProizvod(ocpSastavProizvoda.getProizvodIzlaz());
                    woRezervacijaSastava.setStatus(1);
                    woRezervacija.getWoRezervacijaSastavaList().add(woRezervacijaSastava);
                }

                woRezervacijaSastavaDAO.persist(woRezervacijaSastava);
            }
        }
        addBasketElement(user.getBasket(), basketIndex, woRezervacija);
    }


    public void increaseReservation(OcpProizvod ocpProizvod, int currentOJ, BigDecimal narucenaKolicina, String sessionId, User user,
                                    BigDecimal pakovanje)
            throws WOException {
        BigDecimal aktuelnoPakovanje;
        if (ocpProizvod.getJedinicaMereRezervacije().equals("ALTERNATIVNA")) {
            aktuelnoPakovanje = pakovanje == null ? ocpProizvod.getKolicinaPoPakovanju() : pakovanje;
        } else {
            aktuelnoPakovanje = new BigDecimal(1);
        }
        BigDecimal rabatZaProizvod = new BigDecimal(0);
        String basketIndex = ocpProizvod.getProizvod() + "/" + aktuelnoPakovanje;
        WoKompanijaKorisnik woKompanijaKorisnik = woKompanijaKorisnikDAO.findByCoresponingOJ(currentOJ);
        Integer skladisteRezervacije = ocpProizvod.getMaticnoSkladiste();
        //poevećaj rezervaciju u magacinu
        UzStanjeZalihaSkladistaId uzStanjeZalihaSkladistaId = new UzStanjeZalihaSkladistaId();
        uzStanjeZalihaSkladistaId.setIdSkladista(ocpProizvod.getMaticnoSkladiste());
        uzStanjeZalihaSkladistaId.setProizvod(ocpProizvod.getProizvod());
        UzStanjeZalihaSkladista uzStanjeZalihaSkladista = uzStanjeZalihaSkladistaDAO.findById(uzStanjeZalihaSkladistaId);
        if (ocpProizvod.getPrimeniJsklPakovanje() && narucenaKolicina.compareTo(ocpProizvod.getBrojPakovanja().get(aktuelnoPakovanje).getBrojPakovanja()) == 1) {
            throw new WOException(WOExceptionCodes.WO_INSUFFICIENT_SKU_QUANTITY);
        }
        if (uzStanjeZalihaSkladista == null || narucenaKolicina.multiply(aktuelnoPakovanje)
                .compareTo(WOUtil.trimToZero(uzStanjeZalihaSkladista.getKolicinaPoStanjuZ()).subtract(WOUtil.trimToZero(uzStanjeZalihaSkladista.getRezervisanaKol()))) == 1) {
            throw new WOException(WOExceptionCodes.WO_INSUFFICIENT_SKU_QUANTITY);
        } else {
            if (!woKompanijaKorisnik.getWoMapKompanijskaSkladistas().isEmpty()) {
                for (WoMapKompanijskaSkladista woMapKompanijskaSkladista : woKompanijaKorisnik.getWoMapKompanijskaSkladistas()) {
                    if (woMapKompanijskaSkladista != null && woMapKompanijskaSkladista.getUzSkladisteRaspolozivo() != null)
                        if (woMapKompanijskaSkladista.getUzSkladisteRaspolozivo().getIdSkladista() == uzStanjeZalihaSkladistaId.getIdSkladista()) {
                            if (woMapKompanijskaSkladista.getRezervisiURaspolozivo().equals(1)) {
                                //rezervi??i robu u magacinu koji daje raspolozivu kolicinu
                                uzStanjeZalihaSkladistaDAO.azurirajRezervisanuKolicinu(uzStanjeZalihaSkladistaId, narucenaKolicina.multiply(ocpProizvod.getKolicinaPoPakovanju()).doubleValue(), 1);
                            }
                            uzStanjeZalihaSkladistaId.setIdSkladista(woMapKompanijskaSkladista.getUzSkladisteRezervacija().getIdSkladista());
                            uzStanjeZalihaSkladistaId.setProizvod(ocpProizvod.getProizvod());
                            //rezervi??i robu u magacinu na koji se polazni magacin mapira
                            uzStanjeZalihaSkladistaDAO.azurirajRezervisanuKolicinu(uzStanjeZalihaSkladistaId, narucenaKolicina.multiply(ocpProizvod.getKolicinaPoPakovanju()).doubleValue(), 1);
                            skladisteRezervacije = uzStanjeZalihaSkladistaId.getIdSkladista();
                        }
                }

            } else {
                uzStanjeZalihaSkladistaDAO.azurirajRezervisanuKolicinu(uzStanjeZalihaSkladistaId, narucenaKolicina.multiply(aktuelnoPakovanje).doubleValue(), 1);
            }

            /*kolciina za rezervaciju se mno??i sa operacijom tako da se koli�?ina u objektu povećava ako je vrednost operacije 1,
  smanjuje ako je vrednost operacije -1 ??to je slu�?aj kod umanjenja*/
            BigDecimal operacija = new BigDecimal(1);
            //insertuj stavku u wo_rezervacija za tekuću sesiju
            WoRezervacija woRezervacija = getBasketElement(user.getBasket(), basketIndex);
            if (woRezervacija != null) {
                updateExistingBasketElemnt(woRezervacija, narucenaKolicina.multiply(aktuelnoPakovanje), operacija);
                WoRezervacija woRezervacijaPersistent = woRezervacijaDAO.findByWoRezervacijaById(woRezervacija.getId());
                woRezervacijaPersistent.setKolicina(woRezervacija.getKolicina());
                user.setOrderValue(woRezervacija.getVrednost());
                user.setOrderValueWithTax(user.getOrderValue().add(user.getOrderValue().multiply(ocpProizvod.getStopaPoreza()
                        .divide(new BigDecimal("100")))).setScale(3, RoundingMode.HALF_EVEN));
                woRezervacijaDAO.persist(woRezervacijaPersistent);
            } else {
                woRezervacija = new WoRezervacija();
                woRezervacija.setAkcija(" ");
                if (ocpProizvod.getTipAkcije() != null) {
                    if (ocpProizvod.getTipAkcije().equals(ProductService.PROIZVODI_NA_AKCIJI)
                            || ocpProizvod.getTipAkcije().equals(ProductService.PROIZVODI_NA_RASPRODAJI)
                            || ocpProizvod.getTipAkcije().equals(ProductService.IZDVOJENA_AKCIJA)) {
                        woRezervacija.setAkcija("D");
                    }
                }
                woRezervacija.setCena(ocpProizvod.getCena());
                woRezervacija.setDatumivreme(new Timestamp(Calendar.getInstance().getTimeInMillis()));
                woRezervacija.setIdjedinicemere(ocpProizvod.getJedinicaMere().getIdJediniceMere());
                woRezervacija.setIdSkladista(skladisteRezervacije);
                woRezervacija.setKolicina(narucenaKolicina.multiply(aktuelnoPakovanje));
                woRezervacija.setPoslovniPartner(user.getWoPartnerSetting().get(0).getPoslovniPartner().getPoslovniPartner());
                woRezervacija.setProizvod(ocpProizvod);
                woRezervacija.setStatusRezervacije(1);
                woRezervacija.setSessionid(sessionId);
                woRezervacija.setKolPoPakovanju(aktuelnoPakovanje);
                rabatZaProizvod = (productService.getRabatZaProizvod(ocpProizvod.getProizvod(), user.getWoPartnerSetting().get(0).getPoslovniPartner().getPoslovniPartner(), currentOJ)).getRabat();
                woRezervacija.setRabat(ocpProizvod.getMaxRabat().compareTo(new BigDecimal(-1)) != 0 && rabatZaProizvod.compareTo(ocpProizvod.getMaxRabat()) == 1
                        ? ocpProizvod.getMaxRabat() : rabatZaProizvod);
                if (woRezervacija.getRabat() == null || (ocpProizvod.getTipAkcije() != null && (ocpProizvod.getTipAkcije().equals(ProductService.PROIZVODI_NA_AKCIJI)
                        || ocpProizvod.getTipAkcije().equals(ProductService.IZDVOJENA_AKCIJA)
                        || ocpProizvod.getTipAkcije().equals(ProductService.PROIZVODI_NA_RASPRODAJI))))
                    woRezervacija.setRabat(new BigDecimal(0));
                woRezervacija.setVrednost(woRezervacija.getKolicina().multiply(woRezervacija.getCena()).setScale(3, RoundingMode.HALF_EVEN));
                woRezervacija.setVrednostSaPorezom(woRezervacija.getVrednost()
                        .add(ocpProizvod.getStopaPoreza().multiply(woRezervacija.getVrednost().divide(new BigDecimal("100")))).setScale(3, RoundingMode.HALF_EVEN));
                user.setOrderValue(user.getOrderValue().add(woRezervacija.getCena().multiply(woRezervacija.getKolicina())).setScale(3, RoundingMode.HALF_EVEN));
                user.setOrderValueWithTax(user.getOrderValueWithTax().add(woRezervacija.getCena()
                        .multiply(narucenaKolicina.multiply(aktuelnoPakovanje)).add(woRezervacija.getCena()
                                .multiply(narucenaKolicina.multiply(aktuelnoPakovanje)).multiply(ocpProizvod.getStopaPoreza().divide(new BigDecimal("100"))))).setScale(3, RoundingMode.HALF_EVEN));
                woRezervacija.setWoUser(user.getWoUser());
                woRezervacijaDAO.persist(woRezervacija);
                addBasketElement(user.getBasket(), basketIndex, woRezervacija);
            }
            //umanjiti kolicinu rasplozivu na objektu.
            ocpProizvod.setRaspolozivo(ocpProizvod.getRaspolozivo().subtract(narucenaKolicina.multiply(aktuelnoPakovanje)));
            ocpProizvod.setKolUAltJM(ocpProizvod.getRaspolozivo().divide(aktuelnoPakovanje, 0, RoundingMode.FLOOR).intValue());
        }

    }

    public void decreaseReservationCompositeObject(OcpProizvod ocpProizvod, int currentOJ, BigDecimal narucenaKolicina, String sessionId, User user,
                                                   String basketIndex) {

        // komentar dat u metodi increaseReservation
        BigDecimal operacija = new BigDecimal(-1);
        BigDecimal vrednost = new BigDecimal("0");
        BigDecimal novaVrednost = new BigDecimal("0");
        WoRezervacija woRezervacija = getBasketElement(user.getBasket(), basketIndex);
        WoKompanijaKorisnik woKompanijaKorisnik = woKompanijaKorisnikDAO.findByCoresponingOJ(currentOJ);

        if (woRezervacija != null) {
            /*Umanji rezervaciju */
            for (WoRezervacijaSastava woRezervacijaSastava : woRezervacija.getWoRezervacijaSastavaList()) {
                UzStanjeZalihaSkladistaId uzStanjeZalihaSkladistaId = new UzStanjeZalihaSkladistaId();
                uzStanjeZalihaSkladistaId.setProizvod(woRezervacijaSastava.getProizvod().getProizvod());
                if (!woKompanijaKorisnik.getWoMapKompanijskaSkladistas().isEmpty()) {
                    for (WoMapKompanijskaSkladista woMapKompanijskaSkladista : woKompanijaKorisnik.getWoMapKompanijskaSkladistas()) {
                        if (woMapKompanijskaSkladista.getUzSkladisteRaspolozivo().getIdSkladista() == woRezervacijaSastava.getIdSkladista()) {
                            if (woMapKompanijskaSkladista.getRezervisiURaspolozivo().equals(1)) {
                                //rezervi??i robu u magacinu koji daje raspolozivu kolicinu
                                uzStanjeZalihaSkladistaId.setIdSkladista(ocpProizvod.getMaticnoSkladiste());
                                uzStanjeZalihaSkladistaDAO.azurirajRezervisanuKolicinu(uzStanjeZalihaSkladistaId, narucenaKolicina.multiply(woRezervacijaSastava.getKolicinaUgradnje()).doubleValue(), -1);
                            }
                            uzStanjeZalihaSkladistaId.setIdSkladista(woMapKompanijskaSkladista.getUzSkladisteRezervacija().getIdSkladista());
                            //rezervi??i robu u magacinu na koji se polazni magacin mapira
                            uzStanjeZalihaSkladistaDAO.azurirajRezervisanuKolicinu(uzStanjeZalihaSkladistaId, narucenaKolicina.multiply(woRezervacijaSastava.getKolicinaUgradnje()).doubleValue(), -1);

                        }
                    }
                } else {
                    uzStanjeZalihaSkladistaId.setIdSkladista(woRezervacijaSastava.getIdSkladista());
                    uzStanjeZalihaSkladistaDAO.azurirajRezervisanuKolicinu(uzStanjeZalihaSkladistaId, narucenaKolicina.multiply(woRezervacijaSastava.getKolicinaUgradnje()).doubleValue(), -1);
                }
            }
            if (woRezervacija.getKolicina().compareTo(narucenaKolicina) == 0) {
                WoRezervacija woRezervacijaPersistent = woRezervacijaDAO.findByWoRezervacijaById(woRezervacija.getId());
                vrednost = woRezervacijaPersistent.getKolicina().multiply(woRezervacija.getCena()).setScale(3, RoundingMode.HALF_EVEN);
                user.setOrderValue(user.getOrderValue().subtract(woRezervacijaPersistent.getKolicina().multiply(woRezervacija.getCena())).setScale(3, RoundingMode.HALF_EVEN));
                user.getBasket().remove(basketIndex);
                woRezervacijaDAO.remove(woRezervacijaPersistent);

            } else {
                vrednost = woRezervacija.getVrednost();
                updateExistingBasketElemnt(woRezervacija, narucenaKolicina, operacija);
                user.setOrderValue(user.getOrderValue().subtract(vrednost.subtract(woRezervacija.getVrednost())).setScale(3, RoundingMode.HALF_EVEN));

            }
        }
        if (vrednost.doubleValue() != woRezervacija.getVrednost().doubleValue())
            novaVrednost = woRezervacija.getVrednost();

        user.setOrderValueWithTax(user.getOrderValueWithTax().subtract((vrednost.subtract(novaVrednost))
                .add((vrednost.subtract(novaVrednost)).multiply(ocpProizvod.getStopaPoreza()
                        .divide(new BigDecimal("100"))))).setScale(3, RoundingMode.HALF_EVEN));

        ocpProizvod.setRaspolozivo(ocpProizvod.getRaspolozivo().add(narucenaKolicina));
        ocpProizvod.setKolUAltJM(ocpProizvod.getRaspolozivo().divide(ocpProizvod.getKolicinaPoPakovanju(), 0, RoundingMode.FLOOR).intValue());


    }

    public void decreaseReservation(OcpProizvod ocpProizvod, int currentOJ, BigDecimal narucenaKolicina, String sessionId, User user,
                                    String basketIndex) {

        // komentar dat u metodi increaseReservation
        BigDecimal operacija = new BigDecimal(-1);
        BigDecimal vrednost = new BigDecimal("0");
        BigDecimal novaVrednost = new BigDecimal("0");
        WoRezervacija woRezervacija = getBasketElement(user.getBasket(), basketIndex);
        if (woRezervacija != null) {
            if (woRezervacija.getKolicina().compareTo(narucenaKolicina) == 0) {
                WoRezervacija woRezervacijaPersistent = woRezervacijaDAO.findByWoRezervacijaById(woRezervacija.getId());
                vrednost = woRezervacijaPersistent.getKolicina().multiply(woRezervacija.getCena()).setScale(3, RoundingMode.HALF_EVEN);
                user.setOrderValue(user.getOrderValue().subtract(woRezervacijaPersistent.getKolicina().multiply(woRezervacija.getCena())).setScale(3, RoundingMode.HALF_EVEN));
                user.getBasket().remove(basketIndex);
                woRezervacijaDAO.remove(woRezervacijaPersistent);

            } else {
                vrednost = woRezervacija.getVrednost();
                updateExistingBasketElemnt(woRezervacija, narucenaKolicina, operacija);
                user.setOrderValue(user.getOrderValue().subtract(vrednost.subtract(woRezervacija.getVrednost())).setScale(3, RoundingMode.HALF_EVEN));

            }
        }
        if (vrednost.doubleValue() != woRezervacija.getVrednost().doubleValue())
            novaVrednost = woRezervacija.getVrednost();

        user.setOrderValueWithTax(user.getOrderValueWithTax().subtract((vrednost.subtract(novaVrednost))
                .add((vrednost.subtract(novaVrednost)).multiply(ocpProizvod.getStopaPoreza()
                        .divide(new BigDecimal("100"))))).setScale(3, RoundingMode.HALF_EVEN));
        UzStanjeZalihaSkladistaId uzStanjeZalihaSkladistaId = new UzStanjeZalihaSkladistaId();

        uzStanjeZalihaSkladistaId.setProizvod(woRezervacija.getProizvod().getProizvod());
        WoKompanijaKorisnik woKompanijaKorisnik = woKompanijaKorisnikDAO.findByCoresponingOJ(currentOJ);
        if (!woRezervacija.getProizvod().getProveraZaliha().equals("SASTAV")) {
            if (!woKompanijaKorisnik.getWoMapKompanijskaSkladistas().isEmpty()) {
                for (WoMapKompanijskaSkladista woMapKompanijskaSkladista : woKompanijaKorisnik.getWoMapKompanijskaSkladistas()) {
                    if (woMapKompanijskaSkladista.getUzSkladisteRaspolozivo().getIdSkladista() == ocpProizvod.getMaticnoSkladiste()) {
                        if (woMapKompanijskaSkladista.getRezervisiURaspolozivo().equals(1)) {
                            //rezervi??i robu u magacinu koji daje raspolozivu kolicinu
                            uzStanjeZalihaSkladistaId.setIdSkladista(ocpProizvod.getMaticnoSkladiste());

                            uzStanjeZalihaSkladistaDAO.azurirajRezervisanuKolicinu(uzStanjeZalihaSkladistaId, narucenaKolicina.doubleValue(), -1);
                        }
                        uzStanjeZalihaSkladistaId.setIdSkladista(woMapKompanijskaSkladista.getUzSkladisteRezervacija().getIdSkladista());
                        //rezervi??i robu u magacinu na koji se polazni magacin mapira
                        uzStanjeZalihaSkladistaDAO.azurirajRezervisanuKolicinu(uzStanjeZalihaSkladistaId, narucenaKolicina.doubleValue(), -1);

                    }
                }

            } else {
                uzStanjeZalihaSkladistaId.setIdSkladista(woRezervacija.getIdSkladista());
                uzStanjeZalihaSkladistaDAO.azurirajRezervisanuKolicinu(uzStanjeZalihaSkladistaId, narucenaKolicina.doubleValue(), -1);
            }
        } else {

        }

        ocpProizvod.setRaspolozivo(ocpProizvod.getRaspolozivo().add(narucenaKolicina));
        ocpProizvod.setKolUAltJM(ocpProizvod.getRaspolozivo().divide(ocpProizvod.getKolicinaPoPakovanju(), 0, RoundingMode.FLOOR).intValue());
    }

    public void releaseReservation(String sessionId) {

        List<WoRezervacija> woRezervacijaList = woRezervacijaDAO.findBySessionIdAndDefaultStatus(sessionId);
        if (woRezervacijaList.size() != 0) {
            for (WoRezervacija woRezervacija : woRezervacijaList) {

                UzStanjeZalihaSkladistaId uzStanjeZalihaSkladistaId = new UzStanjeZalihaSkladistaId();
                uzStanjeZalihaSkladistaId.setIdSkladista(woRezervacija.getIdSkladista());
                uzStanjeZalihaSkladistaId.setProizvod(woRezervacija.getProizvod().getProizvod());

                uzStanjeZalihaSkladistaDAO.azurirajRezervisanuKolicinu(uzStanjeZalihaSkladistaId,
                        woRezervacija.getKolicina().doubleValue(), -1);

                woRezervacijaDAO.remove(woRezervacija);
            }
        }
    }


    private UzDokument createDocument(Integer woSkladiste, Map<String, UzDokumentId> dokumentaMap, UzDokument uzDokument, String nacinPlacanja, User user,
                                      CompanySetting cs, Integer OJ, Integer year, String adresa, Date datumPromene, String sessionId, int prevoz,
                                      String napomena, short datumValute, short index, BigDecimal kolicina, BigDecimal cena,
                                      BigDecimal rabat, BigDecimal ekstraRabat, OcpProizvod ocpProizvod, BigDecimal kolPoPakovanju) {


        UzSkladiste skl = uzSkladisteDAO.findById(woSkladiste);

        Integer partner = new Integer(0);
        if (!dokumentaMap.containsKey(skl.getIdSkladista() + "")) {
            uzDokument = new UzDokument();
            //insertuj novi dokument
            if (nacinPlacanja.equals("CAS")) {
                for (WoPartnerSetting woPartnerSetting : user.getWoPartnerSetting()) {
                    if (woPartnerSetting.getIdSkladista() == skl.getIdSkladista())
                        partner = woPartnerSetting.getKfl();
                }
                uzDokument.setIdSkladistaZa(uzSkladisteDAO.findByOjAndPurpose(skl.getOrganizacionaJedinicaJe(), 97).getIdSkladista());

            } else {
                partner = user.getWoPartnerSetting().get(0).getPoslovniPartner().getPoslovniPartner();
            }
            UzDokumentId id = new UzDokumentId();
            WoSetPoNacinPlacanja woSetPoNacinPlacanja = woSetPoNacinPlacanjaDAO.findByNacinPlacanjaAndParameters((WoParametri) cs.getKompanijskiParametri().get(OJ),
                    prodNacinPlacanjaDAO.findById(nacinPlacanja));
            id.setIdVd(woSetPoNacinPlacanja.getIdVdSkldok().shortValue());
            id.setIdDokumenta(uzDokumentOjDAO.spkSklDokumentSlozen(woSetPoNacinPlacanja.getIdVdSkldok(), OJ, year, skl.getIdSkladista()));
            uzDokument.setId(id);
            uzDokument.setRadnikU(Integer.valueOf(user.getWoUser().getRadnik().getRadbr()));
            uzDokument.setStatusDokumenta("E");
            uzDokument.setRadnikS(Integer.valueOf(user.getWoUser().getRadnik().getRadbr()));
            uzDokument.setUzSkladiste(skl);
            uzDokument.setPoslovniPartnerPk(partner);
            uzDokument.setOrganizacionaJedinicaKd(OJ);
            uzDokument.setOrganizacionaJedinicaPk(OJ);
            uzDokument.setPoslovniPartnerKd(partner);
            uzDokument.setPoslovniPartnerOt(user.getWoPartnerSetting().get(0).getPoslovniPartner().getPoslovniPartner());
            if (isInteger(adresa)) {
                uzDokument.setAdresaIsporukeRobe(ocpAdresaIsporukeDAO.findById(Integer.parseInt(adresa)).getAdresa());
            } else {
                uzDokument.setAdresaIsporukeRobe(adresa);
            }
            uzDokument.setDatumPromene(datumPromene);
            uzDokument.setSysDatumIVreme(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            uzDokument.setSpoljniBrojDokumenta(sessionId);
            uzDokument.setDatumOvere(datumPromene);
            uzDokument.setDatumIVremeNri(new Timestamp(System.currentTimeMillis()));
            uzDokument.setOrganizacionaJedinicaC(user.getWoPartnerSetting().get(0).getOrganizacionaJedinica());
            uzDokument.setIdCenovnik(user.getWoPartnerSetting().get(0).getIdCenovnik());
            uzDokument.setIdKlasaCene(user.getWoPartnerSetting().get(0).getIdKlasaCene());
            uzDokument.setOrganizacionaJedinicaReal(skl.getOrganizacionaJedinicaJe());
            uzDokument.setRadnikTp(Integer.valueOf(user.getWoUser().getRadnik().getRadbr()));
            uzDokument.setRadnikOv(Integer.valueOf(user.getWoUser().getRadnik().getRadbr()));
            uzDokument.setVrstaStavke(woSetPoNacinPlacanja.getVrstastavke());
            uzDokument.setVrstaPrevoza((uzDokument.getUzSkladiste().getIdSkladista() == 4) ? 5 : prevoz);


            uzDokument.setGodina(year);
            if (user.getWoUser().getUserType().equals("INTERNI")) {
                uzDokument.setInternaNapomena(napomena);
            }

            uzDokumentDAO.persist(uzDokument);

            dokumentaMap.put(skl.getIdSkladista() + "", id);

            UzDokumentUsloviPlacanjaId uzDokumentUsloviPlacanjaId = new UzDokumentUsloviPlacanjaId();
            uzDokumentUsloviPlacanjaId.setIdDokumenta(uzDokument.getId().getIdDokumenta());
            uzDokumentUsloviPlacanjaId.setIdVd(uzDokument.getId().getIdVd());
            UzDokumentUsloviPlacanja uzDokumentUsloviPlacanja = new UzDokumentUsloviPlacanja();
            uzDokumentUsloviPlacanja.setId(uzDokumentUsloviPlacanjaId);
            uzDokumentUsloviPlacanja.setProcKassaSkonto(new BigDecimal(0));
            uzDokumentUsloviPlacanja.setIdValute(user.getValuta().getIdValute());

            List<OcpLiceZaKontakt> ocpLiceZaKontakt = ocpLiceZaKontaktDAO.findByPartnerId(user.getWoPartnerSetting().get(0).getPoslovniPartner().getPoslovniPartner());
            if (ocpLiceZaKontakt != null && ocpLiceZaKontakt.size() > 0) {
                uzDokumentUsloviPlacanja.setNapomenaPr(ocpLiceZaKontakt.get(0).getIme() + " " + ocpLiceZaKontakt.get(0).getPrezime());
            }
            List<OcpTelefonskiBroj> ocpTelefonskiBroj = ocpTelefonskiBrojDAO.findByPartnerId(user.getWoPartnerSetting().get(0).getPoslovniPartner().getPoslovniPartner());
            //reklama je lice koje je poručilo
            if (user.getWoUser().getUserType().equals("INTERNI")) {
                uzDokumentUsloviPlacanja.setReklama(user.getWoUser().getRadnik().getIme() + " " + user.getWoUser().getRadnik().getPrezime()
                        + " " + (user.getWoUser().getTelefonMobilni()==null ? "" : user.getWoUser().getTelefonMobilni())
                        + " " + (user.getWoUser().getTelefon() == null ? "" : user.getWoUser().getTelefon()));
                if (ocpTelefonskiBroj != null && ocpTelefonskiBroj.size() > 0) {
                    uzDokumentUsloviPlacanja.setBrTelefona(ocpTelefonskiBroj.get(0).getTelefonskiBroj());
                    uzDokumentUsloviPlacanja.setNapomenaPr(uzDokumentUsloviPlacanja.getNapomenaPr() + " " + ocpTelefonskiBroj.get(0).getTelefonskiBroj());
                }
            } else {
                uzDokumentUsloviPlacanja.setReklama((user.getWoUser().getIme() == null || user.getWoUser().getIme().equals("") || user.getWoUser().getIme().equals(" "))
                        ? user.getWoUser().getIme() + " " + user.getWoUser().getPrezime() : user.getWoUser().getNickname()
                        + " " + (user.getWoUser().getTelefonMobilni()==null ? "" : user.getWoUser().getTelefonMobilni())
                        + " " + (user.getWoUser().getTelefon() == null ? "" : user.getWoUser().getTelefon()));

                if (ocpTelefonskiBroj != null && ocpTelefonskiBroj.size() > 0) {
                    uzDokumentUsloviPlacanja.setBrTelefona(ocpTelefonskiBroj.get(0).getTelefonskiBroj());
                    uzDokumentUsloviPlacanja.setNapomenaPr(uzDokumentUsloviPlacanja.getNapomenaPr() + " " + ocpTelefonskiBroj.get(0).getTelefonskiBroj());
                }
            }

            uzDokumentUsloviPlacanja.setNacinPor("WO");
            for (int i = 0; i < user.getWoUser().getOcpPoslovniPartner().getOcpAdresaIsporukes().size(); i++) {
                if (isInteger(adresa) && user.getWoUser().getOcpPoslovniPartner().getOcpAdresaIsporukes().get(i).getId() == Integer.parseInt(adresa))
                    uzDokumentUsloviPlacanja.setPrimalac(user.getWoUser().getOcpPoslovniPartner().getOcpAdresaIsporukes().get(i).getPrimalac());
            }

            if (nacinPlacanja.equals("CAS")) {
                uzDokumentUsloviPlacanja.setBrojDanaValute(0);
            } else {
                uzDokumentUsloviPlacanja.setBrojDanaValute(datumValute);
            }
            uzDokumentUsloviPlacanja.setNacinPlacanja(nacinPlacanja);
            uzDokumentUsloviPlacanja.setKreiratiFakturu(true);

            if (prevoz == 2) {
                switch (skl.getIdSkladista()) {
                    case 10:
                        uzDokumentUsloviPlacanja.setNacinIsporuke(2);
                        break;
                    case 4:
                        uzDokumentUsloviPlacanja.setNacinIsporuke(9);
                        break;
                    case 6:
                        uzDokumentUsloviPlacanja.setNacinIsporuke(9);
                        break;
                    case 5:
                        uzDokumentUsloviPlacanja.setNacinIsporuke(7);
                        break;
                    case 115:
                        uzDokumentUsloviPlacanja.setNacinIsporuke(8);
                        break;
                    case 145:
                        uzDokumentUsloviPlacanja.setNacinIsporuke(8);
                        break;
                    default:
                        uzDokumentUsloviPlacanja.setNacinIsporuke(7);
                        break;
                }
            }else{
                uzDokumentUsloviPlacanja.setNacinIsporuke(3);
            }
            uzDokumentUsloviPlacanjaDAO.persist(uzDokumentUsloviPlacanja);
        }

        UzDokumentStavka uzDokumentStavka = uzDokumentStavkaDAO.findStavkaDokIdProAndRabat(dokumentaMap.get(skl.getIdSkladista() + "").getIdDokumenta(),
                dokumentaMap.get(skl.getIdSkladista() + "").getIdVd(), ocpProizvod.getProizvod(), rabat);

        if (uzDokumentStavka != null) {
            uzDokumentStavka.setNavedKol(uzDokumentStavka.getNavedKol().add(kolicina));
            if (ocpProizvod.getPrimeniJsklPakovanje()) {
                UzDokumentStavkaPakovanje uzDokumentStavkaPakovanje = uzDokumentStavkaPakovanjeDAO.findPakovanjByStavka(dokumentaMap.get(skl.getIdSkladista() + "").getIdDokumenta(),
                        dokumentaMap.get(skl.getIdSkladista() + "").getIdVd(), uzDokumentStavka.getId().getRbStavke(), kolPoPakovanju);
                uzDokumentStavkaPakovanje.getId().setUskladistenaKol(uzDokumentStavkaPakovanje.getId().getUskladistenaKol().add(kolicina));
                uzDokumentStavkaPakovanje.getId().setBrojKomada(uzDokumentStavkaPakovanje.getId().getUskladistenaKol().divide(uzDokumentStavkaPakovanje.getId().getKolPoPakovanju()).longValue());
                uzDokumentStavkaPakovanjeDAO.persist(uzDokumentStavkaPakovanje);
            }
            uzDokumentStavkaDAO.persist(uzDokumentStavka);
        } else {

            UzDokumentStavkaId uzDokumentStavkaId = new UzDokumentStavkaId();
            uzDokumentStavkaId.setIdDokumenta(dokumentaMap.get(skl.getIdSkladista() + "").getIdDokumenta());
            uzDokumentStavkaId.setIdVd(dokumentaMap.get(skl.getIdSkladista() + "").getIdVd());
            uzDokumentStavkaId.setRbStavke(++index);
            uzDokumentStavka = new UzDokumentStavka();
            uzDokumentStavka.setId(uzDokumentStavkaId);
            uzDokumentStavka.setStatusStavke("A");
            uzDokumentStavka.setIdJediniceMere(ocpProizvod.getJedinicaMere().getIdJediniceMere());
            uzDokumentStavka.setProizvod(ocpProizvod.getProizvod());
            uzDokumentStavka.setNivoKvaliteta("Z");
            uzDokumentStavka.setVrstaPromene(0);
            uzDokumentStavka.setNavedKol(kolicina);
            if (nacinPlacanja.equals("CAS"))
// u slu?aju plaćanja ke?? na polazna cena mora biti sa ura?unatim porezom te se na cenu iz cenovnika poreska stopa
                uzDokumentStavka.setJedinicnaCena(cena.multiply(prodPoreskaStopaDAO.findPorezPerProizvod(ocpProizvod.getProizvod(), OJ, datumPromene)
                        .divide(new BigDecimal(100.0)).add(new BigDecimal(1.0)).setScale(2, RoundingMode.HALF_EVEN)));
            else
                uzDokumentStavka.setJedinicnaCena(cena);

            uzDokumentStavka.setVrednost(kolicina.multiply(uzDokumentStavka.getJedinicnaCena()).setScale(3, RoundingMode.HALF_EVEN));
            uzDokumentStavka.setVrstaProizvoda(16);
            uzDokumentStavka.setGodina(year);
            uzDokumentStavka.setRabat(rabat);
            uzDokumentStavka.setKol1(ekstraRabat);
            uzDokumentStavkaDAO.persist(uzDokumentStavka);

            if (ocpProizvod.getPrimeniJsklPakovanje()) {
                UzDokumentStavkaPakovanje uzDokumentStavkaPakovanje = new UzDokumentStavkaPakovanje();
                UzDokumentStavkaPakovanjeId uzDokumentStavkaPakovanjeId = new UzDokumentStavkaPakovanjeId();
                uzDokumentStavkaPakovanjeId.setIdVd(uzDokumentStavka.getId().getIdVd());
                uzDokumentStavkaPakovanjeId.setIdDokumenta(uzDokumentStavka.getId().getIdDokumenta());
                uzDokumentStavkaPakovanjeId.setRbStavke(uzDokumentStavka.getId().getRbStavke());
                uzDokumentStavkaPakovanjeId.setKolPoPakovanju(kolPoPakovanju);
                uzDokumentStavkaPakovanjeId.setUskladistenaKol(kolicina);
                uzDokumentStavkaPakovanjeId.setBrojKomada(kolicina.divide(kolPoPakovanju).longValue());
                uzDokumentStavkaPakovanje.setId(uzDokumentStavkaPakovanjeId);
                uzDokumentStavkaPakovanjeDAO.persist(uzDokumentStavkaPakovanje);
            }
        }
        return uzDokument;
    }

    private UzDokument createDocumentAVA(Integer woSkladiste, Map<String, UzDokumentId> dokumentaMap, UzDokument uzDokument, String nacinPlacanja, User user,
                                         CompanySetting cs, Integer OJ, Integer year, String adresa, Date datumPromene, String sessionId, int prevoz,
                                         String napomena, short datumValute, short index, BigDecimal kolicina, BigDecimal cena, BigDecimal rabat, BigDecimal ekstraRabat,
                                         OcpProizvod ocpProizvod, BigDecimal kolPoPakovanju, String woAkcija, ProAkcija akcija, short maxRabat) {

        UzSkladiste skl = uzSkladisteDAO.findById(woSkladiste);
        if (woAkcija.equals(akcija.toString())
                || (woAkcija == " " && akcija.toString().equals("N"))) {
            if (!dokumentaMap.containsKey(skl.getIdSkladista() + akcija.toString())) {
                //insertuj dokument
                Integer partner = user.getWoPartnerSetting().get(0).getPoslovniPartner().getPoslovniPartner();
                UzDokumentId id = new UzDokumentId();
                uzDokument = new UzDokument();
                WoSetPoNacinPlacanja woSetPoNacinPlacanja = woSetPoNacinPlacanjaDAO.findByNacinPlacanjaAndParameters((WoParametri) cs.getKompanijskiParametri().get(OJ),
                        prodNacinPlacanjaDAO.findById(nacinPlacanja));

                id.setIdVd(woSetPoNacinPlacanja.getIdVdSkldok().shortValue());
                id.setIdDokumenta(uzDokumentOjDAO.spkSklDokumentSlozen(woSetPoNacinPlacanja.getIdVdSkldok(), OJ, year, skl.getIdSkladista()));

                uzDokument.setId(id);
                uzDokument.setRadnikU(Integer.valueOf(user.getWoUser().getRadnik().getRadbr()));
                uzDokument.setStatusDokumenta("E");
                uzDokument.setRadnikS(Integer.valueOf(user.getWoUser().getRadnik().getRadbr()));
                uzDokument.setUzSkladiste(skl);
                uzDokument.setPoslovniPartnerPk(partner);
                uzDokument.setOrganizacionaJedinicaKd(OJ);
                uzDokument.setOrganizacionaJedinicaPk(OJ);
                uzDokument.setPoslovniPartnerKd(partner);
                uzDokument.setPoslovniPartnerOt(user.getWoPartnerSetting().get(0).getPoslovniPartner().getPoslovniPartner());
                if (isInteger(adresa)) {
                    uzDokument.setAdresaIsporukeRobe(ocpAdresaIsporukeDAO.findById(Integer.parseInt(adresa)).getAdresa());
                } else {
                    uzDokument.setAdresaIsporukeRobe(adresa);
                }
                uzDokument.setDatumPromene(datumPromene);
                uzDokument.setSysDatumIVreme(new Timestamp(Calendar.getInstance().getTimeInMillis()));
                uzDokument.setSpoljniBrojDokumenta(sessionId);
                uzDokument.setDatumOvere(datumPromene);
                uzDokument.setDatumIVremeNri(new Timestamp(System.currentTimeMillis()));
                uzDokument.setOrganizacionaJedinicaC(user.getWoPartnerSetting().get(0).getOrganizacionaJedinica());
                uzDokument.setIdCenovnik(user.getWoPartnerSetting().get(0).getIdCenovnik());
                uzDokument.setIdKlasaCene(user.getWoPartnerSetting().get(0).getIdKlasaCene());
                uzDokument.setOrganizacionaJedinicaReal(skl.getOrganizacionaJedinicaJe());
                uzDokument.setRadnikTp(Integer.valueOf(user.getWoUser().getRadnik().getRadbr()));
                uzDokument.setRadnikOv(Integer.valueOf(user.getWoUser().getRadnik().getRadbr()));
                //uzDokument.setIdSkladistaZa(uzSkladisteDAO.findByOjAndPurpose(skl.getOrganizacionaJedinicaJe(), 97).getIdSkladista());
                uzDokument.setVrstaStavke(woSetPoNacinPlacanja.getVrstastavke());
                uzDokument.setVrstaPrevoza((uzDokument.getUzSkladiste().getIdSkladista() == 4) ? 5 : prevoz);
                uzDokument.setGodina(year);
                if (user.getWoUser().getUserType().equals("INTERNI")) {
                    uzDokument.setInternaNapomena(napomena);
                } else {
                    uzDokument.setNapomena(adresa);
                }
                uzDokumentDAO.persist(uzDokument);
                dokumentaMap.put(skl.getIdSkladista() + akcija.toString(), id);
                UzDokumentUsloviPlacanjaId uzDokumentUsloviPlacanjaId = new UzDokumentUsloviPlacanjaId();
                uzDokumentUsloviPlacanjaId.setIdDokumenta(uzDokument.getId().getIdDokumenta());
                uzDokumentUsloviPlacanjaId.setIdVd(uzDokument.getId().getIdVd());
                UzDokumentUsloviPlacanja uzDokumentUsloviPlacanja = new UzDokumentUsloviPlacanja();
                uzDokumentUsloviPlacanja.setId(uzDokumentUsloviPlacanjaId);
                uzDokumentUsloviPlacanja.setIdValute(user.getValuta().getIdValute());
                uzDokumentUsloviPlacanja.setBrojDanaValute(0);
                uzDokumentUsloviPlacanja.setNacinPlacanja(nacinPlacanja);
                uzDokumentUsloviPlacanja.setKreiratiFakturu(false);

                if (prevoz == 2) {
                    switch (skl.getIdSkladista()) {
                        case 10:
                            uzDokumentUsloviPlacanja.setNacinIsporuke(2);
                            break;
                        case 4:
                            uzDokumentUsloviPlacanja.setNacinIsporuke(9);
                            break;
                        case 6:
                            uzDokumentUsloviPlacanja.setNacinIsporuke(9);
                            break;
                        case 5:
                            uzDokumentUsloviPlacanja.setNacinIsporuke(7);
                            break;
                        case 115:
                            uzDokumentUsloviPlacanja.setNacinIsporuke(8);
                            break;
                        case 145:
                            uzDokumentUsloviPlacanja.setNacinIsporuke(8);
                        default:
                            uzDokumentUsloviPlacanja.setNacinIsporuke(7);
                            break;
                    }
                }else{
                    uzDokumentUsloviPlacanja.setNacinIsporuke(3);
                }
                List<OcpLiceZaKontakt> ocpLiceZaKontakt = ocpLiceZaKontaktDAO.findByPartnerId(partner);
                if (ocpLiceZaKontakt != null && ocpLiceZaKontakt.size() > 0) {
                    uzDokumentUsloviPlacanja.setNapomenaPr(ocpLiceZaKontakt.get(0).getIme() + " " + ocpLiceZaKontakt.get(0).getPrezime());
                }
                List<OcpTelefonskiBroj> ocpTelefonskiBroj = ocpTelefonskiBrojDAO.findByPartnerId(partner);
                //reklama je lice koje je poručilo
                if (user.getWoUser().getUserType().equals("INTERNI")) {
                    uzDokumentUsloviPlacanja.setReklama(user.getWoUser().getRadnik().getIme() + " " + user.getWoUser().getRadnik().getPrezime()
                            + " " + (user.getWoUser().getTelefonMobilni()==null ? "" : user.getWoUser().getTelefonMobilni())
                            + " " + (user.getWoUser().getTelefon() == null ? "" : user.getWoUser().getTelefon()));
                    if (ocpTelefonskiBroj != null && ocpTelefonskiBroj.size() > 0) {
                        uzDokumentUsloviPlacanja.setBrTelefona(ocpTelefonskiBroj.get(0).getTelefonskiBroj());
                        uzDokumentUsloviPlacanja.setNapomenaPr(uzDokumentUsloviPlacanja.getNapomenaPr() + " " + ocpTelefonskiBroj.get(0).getTelefonskiBroj());
                    }
                } else {
                    uzDokumentUsloviPlacanja.setReklama((user.getWoUser().getIme() == null || user.getWoUser().getIme().equals("") || user.getWoUser().getIme().equals(" "))
                            ? user.getWoUser().getIme() + " " + user.getWoUser().getPrezime() : user.getWoUser().getNickname()
                            + " " + (user.getWoUser().getTelefonMobilni()==null ? "" : user.getWoUser().getTelefonMobilni())
                            + " " + (user.getWoUser().getTelefon() == null ? "" : user.getWoUser().getTelefon()));

                    if (ocpTelefonskiBroj != null && ocpTelefonskiBroj.size() > 0) {
                        uzDokumentUsloviPlacanja.setBrTelefona(ocpTelefonskiBroj.get(0).getTelefonskiBroj());
                        uzDokumentUsloviPlacanja.setNapomenaPr(uzDokumentUsloviPlacanja.getNapomenaPr() + " " + ocpTelefonskiBroj.get(0).getTelefonskiBroj());
                    }
                }


                uzDokumentUsloviPlacanja.setNacinPor("WO");
                for (int i = 0; i < user.getWoUser().getOcpPoslovniPartner().getOcpAdresaIsporukes().size(); i++) {
                    if (isInteger(adresa) && user.getWoUser().getOcpPoslovniPartner().getOcpAdresaIsporukes().get(i).getId() == Integer.parseInt(adresa))
                        uzDokumentUsloviPlacanja.setPrimalac(user.getWoUser().getOcpPoslovniPartner().getOcpAdresaIsporukes().get(i).getPrimalac());
                }

                if (woAkcija == " " && akcija.toString().equals("N") && maxRabat != 1
                        && user.getWoPartnerSetting().get(0).getApproveCassaSconto())
// kassa sconto u slu?aju avansnog plaćanja i kada nema proizvoda koji su na akciji ili imaju maksimalni rabat
                    uzDokumentUsloviPlacanja.setProcKassaSkonto(new BigDecimal(2));
                uzDokumentUsloviPlacanjaDAO.persist(uzDokumentUsloviPlacanja);

            }

            UzDokumentStavkaId uzDokumentStavkaId = new UzDokumentStavkaId();
            uzDokumentStavkaId.setIdDokumenta(dokumentaMap.get(skl.getIdSkladista() + akcija.toString()).getIdDokumenta());
            uzDokumentStavkaId.setIdVd(dokumentaMap.get(skl.getIdSkladista() + akcija.toString()).getIdVd());
            uzDokumentStavkaId.setRbStavke(++index);
            UzDokumentStavka uzDokumentStavka = new UzDokumentStavka();
            uzDokumentStavka.setId(uzDokumentStavkaId);
            uzDokumentStavka.setStatusStavke("A");
            uzDokumentStavka.setIdJediniceMere(ocpProizvod.getJedinicaMere().getIdJediniceMere());
            uzDokumentStavka.setProizvod(ocpProizvod.getProizvod());
            uzDokumentStavka.setNivoKvaliteta("Z");
            uzDokumentStavka.setVrstaPromene(0);
            uzDokumentStavka.setNavedKol(kolicina);
            uzDokumentStavka.setJedinicnaCena(cena);
            uzDokumentStavka.setVrednost(kolicina.multiply(cena).setScale(3, RoundingMode.HALF_EVEN));
            uzDokumentStavka.setVrstaProizvoda(16);
            uzDokumentStavka.setGodina(year);
            uzDokumentStavka.setRabat(rabat);
            uzDokumentStavka.setKol1(ekstraRabat);
            uzDokumentStavkaDAO.persist(uzDokumentStavka);

            if (ocpProizvod.getPrimeniJsklPakovanje()) {
                UzDokumentStavkaPakovanje uzDokumentStavkaPakovanje = new UzDokumentStavkaPakovanje();
                UzDokumentStavkaPakovanjeId uzDokumentStavkaPakovanjeId = new UzDokumentStavkaPakovanjeId();
                uzDokumentStavkaPakovanjeId.setIdVd(uzDokumentStavka.getId().getIdVd());
                uzDokumentStavkaPakovanjeId.setIdDokumenta(uzDokumentStavka.getId().getIdDokumenta());
                uzDokumentStavkaPakovanjeId.setRbStavke(uzDokumentStavka.getId().getRbStavke());
                uzDokumentStavkaPakovanjeId.setKolPoPakovanju(kolPoPakovanju);
                uzDokumentStavkaPakovanjeId.setUskladistenaKol(kolicina);
                uzDokumentStavkaPakovanjeId.setBrojKomada(kolicina.divide(kolPoPakovanju).longValue());
                uzDokumentStavkaPakovanje.setId(uzDokumentStavkaPakovanjeId);
                uzDokumentStavkaPakovanjeDAO.persist(uzDokumentStavkaPakovanje);
            }
        }
        return uzDokument;
    }


    public List<ProdFinDokument> chceckOutBasket(User user, CompanySetting cs, String nacinPlacanja, int prevoz, String addressEntered, String addressChosed, String napomena,
                                                 Integer OJ, String sessionId) {


        //kreiraj skladi??na dokumenta
        //kreiraj finansijkka dokumenta


        SimpleDateFormat formatDatum = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat formatDatumYear = new SimpleDateFormat("yyyy");

        Calendar datum = Calendar.getInstance();
        Integer year = Integer.valueOf(datum.get(Calendar.YEAR));
        Date datumPromene = datum.getTime();

        String adresa = (addressChosed == null || addressChosed.equals("") || addressChosed.equals("") || addressChosed.equals("-1")) ? addressEntered : addressChosed;


        UzSkladiste skl = new UzSkladiste();
        Map<String, UzDokumentId> dokumentaMap = new HashMap<String, UzDokumentId>();
        List<ProdFinDokument> prodFinDokuments = new ArrayList<ProdFinDokument>(0);
        Iterator it = cs.getKompanijskiParametri().get(OJ).getWoSetPoNacinPlacanja().iterator();
        while (it.hasNext()) {
            //odredi vrstu dokumenta na osnovu na�?in plaćanja
            WoSetPoNacinPlacanja woSetPoNacinPlacanja = (WoSetPoNacinPlacanja) it.next();
            if (woSetPoNacinPlacanja.getProdNacinPlacanja().getNacin().equals(nacinPlacanja)) {
                Integer idVd;
                idVd = woSetPoNacinPlacanja.getIdVdSkldok();
            }
        }
        it = user.getWoPartnerSetting().get(0).getPoslovniPartner().getProdPpRabats().iterator();
        short datumValute = 0;
        while (it.hasNext()) {
            //odredi maximalni rok plaćanja
            ProdPpRabat prodPpRabat = (ProdPpRabat) it.next();
            if (prodPpRabat.getDatumOvere() != null && prodPpRabat.getDatumDo().compareTo(datumPromene) == 1 && prodPpRabat.getIdVd() == null
                    && prodPpRabat.getOrganizacionaJedinica().compareTo(OJ) == 0) {
                datumValute = prodPpRabat.getMaxRokPlacanja();
            }
        }
        UzDokument uzDokument = new UzDokument();


        if (!nacinPlacanja.equals("AVA")) {
            it = user.getBasket().entrySet().iterator();
            short index = 1;
            while (it.hasNext()) {

                Map.Entry mapWoRezervacija = (Map.Entry) it.next();
                WoRezervacija woRezervacija = (WoRezervacija) mapWoRezervacija.getValue();
                WoRezervacija woRezervacijaPersistent = woRezervacijaDAO.findByWoRezervacijaById(woRezervacija.getId());
                woRezervacijaPersistent.setStatusRezervacije(2);
                woRezervacijaPersistent.setEkstraRabat(woRezervacija.getEkstraRabat());
                woRezervacijaDAO.persist(woRezervacijaPersistent);
                //ako nije na�?in plaćanja AVA onda sve stavke idu  na jednom dokumentu

                if (woRezervacija.getWoRezervacijaSastavaList().size() == 0) {
                    uzDokument = createDocument(woRezervacija.getIdSkladista(), dokumentaMap, uzDokument, nacinPlacanja, user, cs, OJ, year, adresa, datumPromene, sessionId, prevoz, napomena,
                            datumValute, index, woRezervacija.getKolicina(), woRezervacija.getCena(), woRezervacija.getRabat(), woRezervacija.getEkstraRabat(), woRezervacija.getProizvod(),
                            woRezervacija.getKolPoPakovanju());
                    index++;
                } else {
                    for (WoRezervacijaSastava woRezervacijaSastava : woRezervacija.getWoRezervacijaSastavaList()) {
                        uzDokument = createDocument(woRezervacijaSastava.getIdSkladista(), dokumentaMap, uzDokument, nacinPlacanja, user, cs, OJ, year, adresa, datumPromene, sessionId, prevoz, napomena,
                                datumValute, index, woRezervacijaSastava.getKolicina(), woRezervacijaSastava.getCena(), woRezervacijaSastava.getRabat(), woRezervacijaSastava.getEkstraRabat(),
                                woRezervacijaSastava.getProizvod(), woRezervacijaSastava.getKolPoPakovanju());
                        index++;
                    }

                }
            }
        } else {
            Map<String, UzDokument> dokPoAkciji = new HashMap<String, UzDokument>(0);
            for (ProAkcija akcija : ProAkcija.values()) {

                it = user.getBasket().entrySet().iterator();
                short index = 0;
                short maxRabat = 0;
                while (it.hasNext()) {
//ukoliko postoji proizvo sa maksimalnim dozvoljenim rabatom kassa sconto se ne dodeljuje
                    Map.Entry mapWoRezervacijaMaxRabat = (Map.Entry) it.next();
                    WoRezervacija woRezervacijaMaxRabat = (WoRezervacija) mapWoRezervacijaMaxRabat.getValue();
                    /*raniji način odredjivanja da li je dat max rabat ili ne. ovo pobristi posle testiranja
                    if (woRezervacijaMaxRabat.getRabat().add(new BigDecimal("2")).compareTo(woKlasifikacijaDAO.findRootKlasifikacijaByProizvod(woRezervacijaMaxRabat.getProizvod(), cs, OJ).getMaxRabat()) == 1
                            && woKlasifikacijaDAO.findRootKlasifikacijaByProizvod(woRezervacijaMaxRabat.getProizvod(), cs, OJ).getMaxRabat().compareTo(new BigDecimal("0")) != 0) {
                        maxRabat = 1;
                    }*/
                    OcpProizvod product = productService.getProizvodById(woRezervacijaMaxRabat.getProizvod().getProizvod(), user.getCeneProizvoda(), cs.getKompanijskiParametri().get(OJ),
                            0, 1, user.getWoPartnerSetting(), cs.getTrasportnaPakovanja(), OJ);
                    for (OcpKlasifikacijaProizvoda ocpKlasifikacijaProizvoda : product.getOcpKlasifikacijaProizvoda()) {
                        product.setMaxRabat(prodMaxRabatiDAO.findByKlasa(OJ, ocpKlasifikacijaProizvoda.getId().getVrstaKlasifikacije(), ocpKlasifikacijaProizvoda.getId().getKlasifikacija()).getMaxRabat());
                        if (product.getMaxRabat().compareTo(new BigDecimal(-1)) != 0)
                            break;
                    }
                    if (woRezervacijaMaxRabat.getRabat().compareTo(product.getMaxRabat()) == 0) {
                        maxRabat = 1;
                    }
                }

                it = user.getBasket().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry mapWoRezervacija = (Map.Entry) it.next();
                    WoRezervacija woRezervacija = (WoRezervacija) mapWoRezervacija.getValue();
                    WoRezervacija woRezervacijaPersistent = woRezervacijaDAO.findByWoRezervacijaById(woRezervacija.getId());
                    woRezervacijaPersistent.setStatusRezervacije(2);
                    woRezervacijaPersistent.setEkstraRabat(woRezervacija.getEkstraRabat());
                    woRezervacijaDAO.persist(woRezervacijaPersistent);
                    //ako nije na�?in plaćanja AVA onda sve stavke idu  na jednom dokumentu

                    if (woRezervacija.getWoRezervacijaSastavaList().size() == 0) {
                        createDocumentAVA(woRezervacija.getIdSkladista(), dokumentaMap, uzDokument, nacinPlacanja, user, cs, OJ, year, adresa, datumPromene, sessionId, prevoz,
                                napomena, datumValute, index, woRezervacija.getKolicina(), woRezervacija.getCena(), woRezervacija.getRabat(), woRezervacija.getEkstraRabat(),
                                woRezervacija.getProizvod(), woRezervacija.getKolPoPakovanju(), woRezervacija.getAkcija(), akcija, maxRabat);
                        index++;

                    } else {
                        for (WoRezervacijaSastava woRezervacijaSastava : woRezervacija.getWoRezervacijaSastavaList()) {
                            woRezervacijaSastava.getProizvod().setOcpKlasifikacijaProizvoda(ocpKlasifikacijaProizvodaDAO.findByProizvod(woRezervacijaSastava.getProizvod()));
                            for (OcpKlasifikacijaProizvoda ocpKlasifikacija : woRezervacijaSastava.getProizvod().getOcpKlasifikacijaProizvoda()) {
                                woRezervacijaSastava.getProizvod().setMaxRabat(prodMaxRabatiDAO.findByKlasa(OJ, ocpKlasifikacija.getId().getVrstaKlasifikacije(), ocpKlasifikacija.getId().getKlasifikacija()).getMaxRabat());
                                if (woRezervacijaSastava.getProizvod().getMaxRabat().compareTo(new BigDecimal(-1)) != 0)
                                    break;
                            }
                            if (woRezervacijaSastava.getRabat().compareTo(woRezervacijaSastava.getProizvod().getMaxRabat()) == 0) {
                                maxRabat = 1;
                            }
                            createDocumentAVA(woRezervacijaSastava.getIdSkladista(), dokumentaMap, uzDokument, nacinPlacanja, user, cs, OJ, year, adresa, datumPromene, sessionId, prevoz,
                                    napomena, datumValute, index, woRezervacijaSastava.getKolicina(), woRezervacijaSastava.getCena(), woRezervacijaSastava.getRabat(),
                                    woRezervacijaSastava.getEkstraRabat(), woRezervacijaSastava.getProizvod(), woRezervacijaSastava.getKolPoPakovanju(), woRezervacija.getAkcija(), akcija, maxRabat);
                            index++;
                        }
                    }
                }
            }

        }

        it = dokumentaMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry mapUzDokument = (Map.Entry) it.next();
            UzDokumentId uzDokumentIdN = (UzDokumentId) mapUzDokument.getValue();
            if (uzDokumentDAO.findEmptyDokument(uzDokumentIdN.getIdDokumenta()) != null) {
                UzDokumentUsloviPlacanjaId uzDokumentUsloviPlacanjaIdN = new UzDokumentUsloviPlacanjaId();
                uzDokumentUsloviPlacanjaIdN.setIdVd(uzDokumentIdN.getIdVd());
                uzDokumentUsloviPlacanjaIdN.setIdDokumenta(uzDokumentIdN.getIdDokumenta());
                uzDokumentUsloviPlacanjaDAO.remove(uzDokumentUsloviPlacanjaDAO.findById(uzDokumentUsloviPlacanjaIdN));
                uzDokumentDAO.remove(uzDokumentDAO.findById(uzDokumentIdN));
            } else {
                ProdFinDokument prodFinDokument =
                        prodFinDokumentDAO.insertFinDOkumentByStoredProcedure(Integer.valueOf(uzDokumentIdN.getIdVd() + ""),
                                uzDokumentIdN.getIdDokumenta());
                prodFinDokuments.add(prodFinDokument);
            }
        }
        return prodFinDokuments;
    }
}