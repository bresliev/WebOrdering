package rs.invado.wo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.dao.ocp.OcpKlasifikacijaHome;
import rs.invado.wo.dao.prod.ProdFinDokumentHome;
import rs.invado.wo.dao.prod.ProdNacinPlacanjaHome;
import rs.invado.wo.dao.prod.ProdPoreskaStopaHome;
import rs.invado.wo.dao.uz.*;
import rs.invado.wo.dao.wo.WoKompanijaKorisnikHome;
import rs.invado.wo.dao.wo.WoRezervacijaHome;
import rs.invado.wo.dao.wo.WoSetPoNacinPlacanjaHome;
import rs.invado.wo.domain.ocp.OcpProizvod;
import rs.invado.wo.domain.prod.ProdFinDokument;
import rs.invado.wo.domain.prod.ProdPpRabat;
import rs.invado.wo.domain.uz.*;
import rs.invado.wo.domain.wo.*;
import rs.invado.wo.dto.CompanySetting;
import rs.invado.wo.dto.User;
import rs.invado.wo.util.WOException;
import rs.invado.wo.util.WOExceptionCodes;
import rs.invado.wo.util.WOUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikola
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


    private enum ProAkcija {
        D, N
    }

    ;

    private WoRezervacija getBasketElement(Map<String, WoRezervacija> basket, String basketIndex) {
        return basket.get(basketIndex);
    }

    private void addBasketElement(Map<String, WoRezervacija> basket, String basketIndex, WoRezervacija woRezervacija) {
        basket.put(basketIndex, woRezervacija);
    }

    private void updateExistingBasketElemnt(WoRezervacija woRezervacija, BigDecimal kolicinaZaRezervaciju, BigDecimal operand) {
        woRezervacija.setKolicina(woRezervacija.getKolicina().add(kolicinaZaRezervaciju.multiply(operand)));
        woRezervacija.setVrednost(woRezervacija.getKolicina().multiply(woRezervacija.getCena()).setScale(3, RoundingMode.HALF_EVEN));
    }

    public void increaseReservation(OcpProizvod ocpProizvod, int currentOJ, BigDecimal narucenaKolicina, String sessionId, User user,
                                    BigDecimal pakovanje)
            throws WOException {

                   BigDecimal aktuelnoPakovanje = pakovanje == null ? ocpProizvod.getKolicinaPoPakovanju() : pakovanje;
        String basketIndex = ocpProizvod.getProizvod() + "/" + aktuelnoPakovanje;

        WoKompanijaKorisnik woKompanijaKorisnik = woKompanijaKorisnikDAO.findByCoresponingOJ(currentOJ);
        Integer skladisteRezervacije = ocpProizvod.getMaticnoSkladiste();
        //poeveÄ‡aj rezervaciju u magacinu
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
                        if (woMapKompanijskaSkladista.isRezervisiURaspolozivo()) {
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

            /*kolciina za rezervaciju se mno??i sa operacijom tako da se koliÄ?ina u objektu poveÄ‡ava ako je vrednost operacije 1,
  smanjuje ako je vrednost operacije -1 ??to je sluÄ?aj kod umanjenja*/
            BigDecimal operacija = new BigDecimal(1);
            //insertuj stavku u wo_rezervacija za tekuÄ‡u sesiju
            WoRezervacija woRezervacija = getBasketElement(user.getBasket(), basketIndex);
            if (woRezervacija != null) {
                updateExistingBasketElemnt(woRezervacija, narucenaKolicina.multiply(aktuelnoPakovanje), operacija);
                WoRezervacija woRezervacijaPersistent = woRezervacijaDAO.findById(woRezervacija.getId());
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

                woRezervacija.setRabat((productService.getRabatZaProizvod(ocpProizvod.getProizvod(), user.getWoPartnerSetting().get(0).getPoslovniPartner().getPoslovniPartner(), currentOJ)).getRabat());
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

    public void decreaseReservation(OcpProizvod ocpProizvod, int currentOJ, BigDecimal narucenaKolicina, String sessionId, User user,
                                    String basketIndex) {

        // komentar dat u metodi increaseReservation
        BigDecimal operacija = new BigDecimal(-1);
        BigDecimal vrednost = new BigDecimal("0");
        BigDecimal novaVrednost = new BigDecimal("0");
        WoRezervacija woRezervacija = getBasketElement(user.getBasket(), basketIndex);
        if (woRezervacija != null) {
            if (woRezervacija.getKolicina().compareTo(narucenaKolicina) == 0) {
                WoRezervacija woRezervacijaPersistent = woRezervacijaDAO.findById(woRezervacija.getId());
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
        if (!woKompanijaKorisnik.getWoMapKompanijskaSkladistas().isEmpty()) {
            for (WoMapKompanijskaSkladista woMapKompanijskaSkladista : woKompanijaKorisnik.getWoMapKompanijskaSkladistas()) {
                if (woMapKompanijskaSkladista.getUzSkladisteRaspolozivo().getIdSkladista() == ocpProizvod.getMaticnoSkladiste()) {
                    if (woMapKompanijskaSkladista.isRezervisiURaspolozivo()) {
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


    public List<ProdFinDokument> chceckOutBasket(User user, CompanySetting cs, String nacinPlacanja, int prevoz, String adresa, String napomena,
                                                 Integer OJ, String sessionId) {


        //kreiraj skladi??na dokumenta
        //kreiraj finansijkka dokumenta

        SimpleDateFormat formatDatum = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat formatDatumYear = new SimpleDateFormat("yyyy");

        Calendar datum = Calendar.getInstance();
        Integer year = Integer.valueOf(datum.get(Calendar.YEAR));
        Date datumPromene = datum.getTime();


        UzSkladiste skl = new UzSkladiste();
        Map<String, UzDokumentId> dokumentaMap = new HashMap<String, UzDokumentId>();
        List<ProdFinDokument> prodFinDokuments = new ArrayList<ProdFinDokument>(0);
        Iterator it = cs.getKompanijskiParametri().get(OJ).getWoSetPoNacinPlacanja().iterator();
        while (it.hasNext()) {
            //odredi vrstu dokumenta na osnovu naÄ?in plaÄ‡anja
            WoSetPoNacinPlacanja woSetPoNacinPlacanja = (WoSetPoNacinPlacanja) it.next();
            if (woSetPoNacinPlacanja.getProdNacinPlacanja().getNacin().equals(nacinPlacanja)) {
                Integer idVd;
                idVd = woSetPoNacinPlacanja.getIdVdSkldok();
            }
        }
        it = user.getWoPartnerSetting().get(0).getPoslovniPartner().getProdPpRabats().iterator();
        short datumValute = 0;
        while (it.hasNext()) {
            //odredi maximalni rok plaÄ‡anja
            ProdPpRabat prodPpRabat = (ProdPpRabat) it.next();
            if (prodPpRabat.getDatumOvere() != null && prodPpRabat.getDatumDo().compareTo(datumPromene) == 1 && prodPpRabat.getIdVd() == null
                    && prodPpRabat.getOrganizacionaJedinica().compareTo(OJ) == 0) {
                datumValute = prodPpRabat.getMaxRokPlacanja();
            }
        }
        UzDokument uzDokument = new UzDokument();


        if (!nacinPlacanja.equals("AVA")) {
            it = user.getBasket().entrySet().iterator();
            short index = 0;
            while (it.hasNext()) {

                Map.Entry mapWoRezervacija = (Map.Entry) it.next();
                WoRezervacija woRezervacija = (WoRezervacija) mapWoRezervacija.getValue();
                WoRezervacija woRezervacijaPersistent = woRezervacijaDAO.findById(woRezervacija.getId());
                woRezervacijaPersistent.setStatusRezervacije(2);
                woRezervacijaPersistent.setEkstraRabat(woRezervacija.getEkstraRabat());
                woRezervacijaDAO.persist(woRezervacijaPersistent);
                //ako nije naÄ?in plaÄ‡anja AVA onda sve stavke idu  na jednom dokumentu
                skl = uzSkladisteDAO.findById(woRezervacija.getIdSkladista());

                Integer partner = new Integer(0);
                if (!dokumentaMap.containsKey(skl.getIdSkladista() + "")) {
                    uzDokument = new UzDokument();
                    //insertuj novi dokument
                    if (nacinPlacanja.equals("CAS")) {
                        partner = user.getWoPartnerSetting().get(0).getKfl();
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
                    uzDokument.setAdresaIsporukeRobe(adresa);
                    uzDokument.setDatumPromene(datumPromene);
                    uzDokument.setSysDatumIVreme(new Timestamp(Calendar.getInstance().getTimeInMillis()));
                    uzDokument.setSpoljniBrojDokumenta(sessionId);
                    uzDokument.setDatumOvere(datumPromene);
                    uzDokument.setOrganizacionaJedinicaC(user.getWoPartnerSetting().get(0).getOrganizacionaJedinica());
                    uzDokument.setIdCenovnik(user.getWoPartnerSetting().get(0).getIdCenovnik());
                    uzDokument.setIdKlasaCene(user.getWoPartnerSetting().get(0).getIdKlasaCene());
                    uzDokument.setOrganizacionaJedinicaReal(skl.getOrganizacionaJedinicaJe());
                    uzDokument.setRadnikTp(Integer.valueOf(user.getWoUser().getRadnik().getRadbr()));
                    uzDokument.setRadnikOv(Integer.valueOf(user.getWoUser().getRadnik().getRadbr()));
                    uzDokument.setVrstaStavke(woSetPoNacinPlacanja.getVrstastavke());
                    uzDokument.setVrstaPrevoza(prevoz);
                    uzDokument.setGodina(year);
                    if (user.getWoUser().getUserType().equals("INTERNI")) {
                        uzDokument.setInternaNapomena(napomena);
                    } else {
                        uzDokument.setNapomena(adresa);
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
                    if (nacinPlacanja.equals("CAS")) {
                        uzDokumentUsloviPlacanja.setBrojDanaValute(0);
                    } else {
                        uzDokumentUsloviPlacanja.setBrojDanaValute(datumValute);
                    }
                    uzDokumentUsloviPlacanja.setNacinPlacanja(nacinPlacanja);
                    uzDokumentUsloviPlacanja.setKreiratiFakturu(true);

                    uzDokumentUsloviPlacanjaDAO.persist(uzDokumentUsloviPlacanja);
                }

                UzDokumentStavkaId uzDokumentStavkaId = new UzDokumentStavkaId();
                uzDokumentStavkaId.setIdDokumenta(dokumentaMap.get(skl.getIdSkladista() + "").getIdDokumenta());
                uzDokumentStavkaId.setIdVd(dokumentaMap.get(skl.getIdSkladista() + "").getIdVd());
                uzDokumentStavkaId.setRbStavke(++index);
                UzDokumentStavka uzDokumentStavka = new UzDokumentStavka();
                uzDokumentStavka.setId(uzDokumentStavkaId);
                uzDokumentStavka.setStatusStavke("A");
                uzDokumentStavka.setIdJediniceMere(woRezervacija.getIdjedinicemere());
                uzDokumentStavka.setProizvod(woRezervacija.getProizvod().getProizvod());
                uzDokumentStavka.setNivoKvaliteta("Z");
                uzDokumentStavka.setVrstaPromene(0);
                uzDokumentStavka.setNavedKol(woRezervacija.getKolicina());
                if (nacinPlacanja.equals("CAS"))
// u sluÄ?aju plaÄ‡anja ke?? na polazna cena mora biti sa uraÄ?unatim porezom te se na cenu iz cenovnika poreska stopa
                    uzDokumentStavka.setJedinicnaCena(woRezervacija.getCena()
                            .multiply(prodPoreskaStopaDAO.findPorezPerProizvod(woRezervacija.getProizvod().getProizvod(), OJ, datumPromene)
                                    .divide(new BigDecimal(100.0)).add(new BigDecimal(1.0)).setScale(2, RoundingMode.HALF_EVEN)));
                else
                    uzDokumentStavka.setJedinicnaCena(woRezervacija.getCena());

                uzDokumentStavka.setVrednost(woRezervacija.getKolicina().multiply(uzDokumentStavka.getJedinicnaCena()).setScale(3, RoundingMode.HALF_EVEN));
                uzDokumentStavka.setVrstaProizvoda(16);
                uzDokumentStavka.setGodina(year);
                uzDokumentStavka.setRabat(woRezervacija.getRabat());
                uzDokumentStavka.setKol1(woRezervacija.getEkstraRabat());

                uzDokumentStavkaDAO.persist(uzDokumentStavka);

                if (woRezervacija.getProizvod().getPrimeniJsklPakovanje()) {
                    UzDokumentStavkaPakovanje uzDokumentStavkaPakovanje = new UzDokumentStavkaPakovanje();
                    UzDokumentStavkaPakovanjeId uzDokumentStavkaPakovanjeId = new UzDokumentStavkaPakovanjeId();
                    uzDokumentStavkaPakovanjeId.setIdVd(uzDokumentStavka.getId().getIdVd());
                    uzDokumentStavkaPakovanjeId.setIdDokumenta(uzDokumentStavka.getId().getIdDokumenta());
                    uzDokumentStavkaPakovanjeId.setRbStavke(uzDokumentStavka.getId().getRbStavke());
                    uzDokumentStavkaPakovanjeId.setKolPoPakovanju(woRezervacija.getKolPoPakovanju());
                    uzDokumentStavkaPakovanjeId.setUskladistenaKol(woRezervacija.getKolicina());
                    uzDokumentStavkaPakovanjeId.setBrojKomada(woRezervacija.getKolicina().divide(woRezervacija.getKolPoPakovanju()).longValue());
                    uzDokumentStavkaPakovanje.setId(uzDokumentStavkaPakovanjeId);
                    uzDokumentStavkaPakovanjeDAO.persist(uzDokumentStavkaPakovanje);
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
                    if (woRezervacijaMaxRabat.getRabat().add(new BigDecimal("2")).compareTo(woKlasifikacijaDAO.findRootKlasifikacijaByProizvod(woRezervacijaMaxRabat.getProizvod(), cs, OJ).getMaxRabat()) == 1
                            && woKlasifikacijaDAO.findRootKlasifikacijaByProizvod(woRezervacijaMaxRabat.getProizvod(), cs, OJ).getMaxRabat().compareTo(new BigDecimal("0")) != 0) {
                        maxRabat = 1;
                    }
                }

                it = user.getBasket().entrySet().iterator();
                while (it.hasNext()) {

                    Map.Entry mapWoRezervacija = (Map.Entry) it.next();
                    WoRezervacija woRezervacija = (WoRezervacija) mapWoRezervacija.getValue();
                    WoRezervacija woRezervacijaPersistent = woRezervacijaDAO.findById(woRezervacija.getId());
                    woRezervacijaPersistent.setStatusRezervacije(2);
                    woRezervacijaPersistent.setEkstraRabat(woRezervacija.getEkstraRabat());
                    woRezervacijaDAO.persist(woRezervacijaPersistent);
                    //ako nije naÄ?in plaÄ‡anja AVA onda sve stavke idu  na jednom dokumentu
                    skl = uzSkladisteDAO.findById(woRezervacija.getIdSkladista());
                    if (woRezervacija.getAkcija().equals(akcija.toString())
                            || (woRezervacija.getAkcija() == " " && akcija.toString().equals("N"))) {
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
                            uzDokument.setAdresaIsporukeRobe(adresa);
                            uzDokument.setDatumPromene(datumPromene);
                            uzDokument.setSysDatumIVreme(new Timestamp(Calendar.getInstance().getTimeInMillis()));
                            uzDokument.setSpoljniBrojDokumenta(sessionId);
                            uzDokument.setDatumOvere(datumPromene);
                            uzDokument.setOrganizacionaJedinicaC(user.getWoPartnerSetting().get(0).getOrganizacionaJedinica());
                            uzDokument.setIdCenovnik(user.getWoPartnerSetting().get(0).getIdCenovnik());
                            uzDokument.setIdKlasaCene(user.getWoPartnerSetting().get(0).getIdKlasaCene());
                            uzDokument.setOrganizacionaJedinicaReal(skl.getOrganizacionaJedinicaJe());
                            uzDokument.setRadnikTp(Integer.valueOf(user.getWoUser().getRadnik().getRadbr()));
                            uzDokument.setRadnikOv(Integer.valueOf(user.getWoUser().getRadnik().getRadbr()));
                            //uzDokument.setIdSkladistaZa(uzSkladisteDAO.findByOjAndPurpose(skl.getOrganizacionaJedinicaJe(), 97).getIdSkladista());
                            uzDokument.setVrstaStavke(woSetPoNacinPlacanja.getVrstastavke());
                            uzDokument.setVrstaPrevoza(prevoz);
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
                            if (woRezervacija.getAkcija() == " " && akcija.toString().equals("N") && maxRabat != 1
                                    && user.getWoPartnerSetting().get(0).getApproveCassaSconto())
// kassa sconto u sluÄ?aju avansnog plaÄ‡anja i kada nema proizvoda koji su na akciji ili imaju maksimalni rabat
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
                        uzDokumentStavka.setIdJediniceMere(woRezervacija.getIdjedinicemere());
                        uzDokumentStavka.setProizvod(woRezervacija.getProizvod().getProizvod());
                        uzDokumentStavka.setNivoKvaliteta("Z");
                        uzDokumentStavka.setVrstaPromene(0);
                        uzDokumentStavka.setNavedKol(woRezervacija.getKolicina());
                        uzDokumentStavka.setJedinicnaCena(woRezervacija.getCena());
                        uzDokumentStavka.setVrednost(woRezervacija.getKolicina().multiply(woRezervacija.getCena()).setScale(3, RoundingMode.HALF_EVEN));
                        uzDokumentStavka.setVrstaProizvoda(16);
                        uzDokumentStavka.setGodina(year);
                        uzDokumentStavka.setRabat(woRezervacija.getRabat());
                        uzDokumentStavka.setKol1(woRezervacija.getEkstraRabat());

                        uzDokumentStavkaDAO.persist(uzDokumentStavka);

                        if (woRezervacija.getProizvod().getPrimeniJsklPakovanje()) {
                            UzDokumentStavkaPakovanje uzDokumentStavkaPakovanje = new UzDokumentStavkaPakovanje();
                            UzDokumentStavkaPakovanjeId uzDokumentStavkaPakovanjeId = new UzDokumentStavkaPakovanjeId();
                            uzDokumentStavkaPakovanjeId.setIdVd(uzDokumentStavka.getId().getIdVd());
                            uzDokumentStavkaPakovanjeId.setIdDokumenta(uzDokumentStavka.getId().getIdDokumenta());
                            uzDokumentStavkaPakovanjeId.setRbStavke(uzDokumentStavka.getId().getRbStavke());
                            uzDokumentStavkaPakovanjeId.setKolPoPakovanju(woRezervacija.getKolPoPakovanju());
                            uzDokumentStavkaPakovanjeId.setUskladistenaKol(woRezervacija.getKolicina());
                            uzDokumentStavkaPakovanjeId.setBrojKomada(woRezervacija.getKolicina().divide(woRezervacija.getKolPoPakovanju()).longValue());
                            uzDokumentStavkaPakovanje.setId(uzDokumentStavkaPakovanjeId);
                            uzDokumentStavkaPakovanjeDAO.persist(uzDokumentStavkaPakovanje);
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