package rs.invado.wo.service;


import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.dao.ocp.OcpProizvodHome;
import rs.invado.wo.dao.ocp.OcpVrAtrProizvodHome;
import rs.invado.wo.dao.prod.ProdPpRabatStavkaHome;
import rs.invado.wo.dao.uz.UzStanjeZalihaSkladistaHome;
import rs.invado.wo.dao.uz.UzZaliheJsklHome;
import rs.invado.wo.dao.wo.WoArtikliNaAkcijiHome;
import rs.invado.wo.domain.ocp.OcpKlasifikacijaProizvoda;
import rs.invado.wo.domain.ocp.OcpProizvod;
import rs.invado.wo.domain.ocp.OcpSastavProizvoda;
import rs.invado.wo.domain.ocp.OcpVrAtrProizvod;
import rs.invado.wo.domain.prod.ProdPpRabatStavka;
import rs.invado.wo.domain.uz.UzStanjeZalihaSkladista;
import rs.invado.wo.domain.wo.WoArtikliNaAkciji;
import rs.invado.wo.domain.wo.WoParametri;
import rs.invado.wo.domain.wo.WoPartnerSetting;
import rs.invado.wo.dto.CompanySetting;
import rs.invado.wo.dto.Proizvodi;
import rs.invado.wo.util.WoConfigSingleton;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@Transactional
public class ProductService {

    @Autowired
    private OcpProizvodHome ocpProizvodDAO;
    @Autowired
    private OcpVrAtrProizvodHome ocpVrAtrProizvodDAO;
    @Autowired
    private WoArtikliNaAkcijiHome woArtikliNaAkcijiDAO;
    @Autowired
    private ProdPpRabatStavkaHome prodPpRabatStavkaDAO;
    @Autowired
    private UzZaliheJsklHome uzZaliheJsklDAO;
    @Inject
    private UzStanjeZalihaSkladistaHome uzStanjeZalihaSkladistaDAO;
    @Inject
    WoConfigSingleton woConfigSingleton;


    /* za sada imamo četiri različita tipa akcije prodaje proizvoda i to:
       AKCIJA
       RASPRODAJA
       NOVO
       AKTUELNO
      za svaku vrstu akcije postoji konstanta u servis klasi koja se proselđuje u skladu
      sa odabranom akcijom na stranici. Za sve vrste akcije se pozva jedna metoda ali se
      svaki put prenosi različita vrenost za konkretnu akciju.
    */

    public final static String PROIZVODI_NA_AKCIJI = "AKCIJA";
    public final static String PROIZVODI_NA_RASPRODAJI = "RASPRODAJA";
    public final static String PROIZVODI_NOVO = "NOVO";
    public final static String PROIZVODI_AKTUELNO = "AKTUELNO";
    public final static String PROIZVOD_DEKOR_MESECA = "DEKOR_MESECA";
    public final static String IZDVOJENA_AKCIJA = "IZDVOJENA_AKCIJA";
    public final static String NAJPRODAVANIJE = "NAJPRODAVANIJE";

    public void setTransAtrZaPro(List<OcpProizvod> lp, Map<Integer, BigDecimal> mapaCena, List<WoPartnerSetting> woPartnerSetting,
                                 Map<Integer, BigDecimal> transportnaPakovanjaProizvoda, WoParametri woParametri) {
        //Izvuci količinu po pakovanju
        Map<Integer, BigDecimal> m = mapaCena;
        List<WoArtikliNaAkciji> laa = woArtikliNaAkcijiDAO.findArtikliNaAkcijiAktivno(DateUtils.truncate(new Date(), Calendar.DATE),
                woParametri);
        Map<Integer, String> laaMap = new HashMap<Integer, String>();
        for (WoArtikliNaAkciji o : laa) {
            laaMap.put((Integer) o.getOcpProizvod().getProizvod(), (String) o.getTipAkcije());
        }
        int i = 0;
        int obradjenoSkl = 0;
        for (OcpProizvod item : lp) {
            obradjenoSkl = 0;
            //Setuj porez
            item.setStopaPoreza(ocpProizvodDAO.findStopaPorezaZaProizvod(woPartnerSetting.get(0).getOrganizacionaJedinica(), item.getProizvod()));

            System.out.println("cene i pro "+item.getProizvod()+" "+mapaCena.get(item.getProizvod()));
            // setuj cene
            item.setCena((BigDecimal) mapaCena.get(item.getProizvod()));

            //setuj atribute
            String dezenIStruktura = "";
            String vrAtr = null;
            for (OcpKlasifikacijaProizvoda klasifikacijaProizvoda : item.getOcpKlasifikacijaProizvoda()) {
                OcpVrAtrProizvod vatp = new OcpVrAtrProizvod();
                //parametrizuj da čita samo atribute za klasifiakciju 1
                if (klasifikacijaProizvoda.getId().getVrstaKlasifikacije() == 1) {
                    vatp = ocpVrAtrProizvodDAO.findByNivoAtributa(klasifikacijaProizvoda.getId().getVrstaKlasifikacije(),
                            klasifikacijaProizvoda.getId().getKlasifikacija(), klasifikacijaProizvoda.getId().getProizvod(),
                            Integer.valueOf(woConfigSingleton.getAttributes()[0]));
                    if (vatp != null) dezenIStruktura = dezenIStruktura + " " + vatp.getVrednost();
                    vatp = null;
                    vatp = ocpVrAtrProizvodDAO.findByNivoAtributa(klasifikacijaProizvoda.getId().getVrstaKlasifikacije(),
                            klasifikacijaProizvoda.getId().getKlasifikacija(), klasifikacijaProizvoda.getId().getProizvod(),
                            Integer.valueOf(woConfigSingleton.getAttributes()[1]));
                    if (vatp != null) dezenIStruktura = dezenIStruktura + " " + vatp.getVrednost();
                    vatp = null;
                    vatp = ocpVrAtrProizvodDAO.findByNivoAtributa(klasifikacijaProizvoda.getId().getVrstaKlasifikacije(),
                            klasifikacijaProizvoda.getId().getKlasifikacija(), klasifikacijaProizvoda.getId().getProizvod(),
                            Integer.valueOf(woConfigSingleton.getAttributes()[2]));
                    if (vatp != null) item.setProizvodjac(vatp.getVrednost());
                    item.setDezenIstruktira(dezenIStruktura);
                    vatp = null;
                }

                if (klasifikacijaProizvoda.getId().getVrstaKlasifikacije() == woParametri.getVrstaKlasifikacijeMeni()) {
                    vatp = ocpVrAtrProizvodDAO.findByNivoAtributa(klasifikacijaProizvoda.getId().getVrstaKlasifikacije(),
                            klasifikacijaProizvoda.getId().getKlasifikacija(), klasifikacijaProizvoda.getId().getProizvod(),
                            Integer.valueOf(woConfigSingleton.getAttributes()[3]));
                    if (vatp != null && vatp.getVrednost().equals("DA")) {
                        item.setPrimeniJsklPakovanje(true);
                    } else {
                        item.setPrimeniJsklPakovanje(false);
                    }
                    vatp = null;
                    vatp = ocpVrAtrProizvodDAO.findByNivoAtributa(klasifikacijaProizvoda.getId().getVrstaKlasifikacije(),
                            klasifikacijaProizvoda.getId().getKlasifikacija(), klasifikacijaProizvoda.getId().getProizvod(),
                            Integer.valueOf(woConfigSingleton.getAttributes()[4]));
                    if (vatp != null)
                        item.setProveraZaliha(vatp.getVrednost());
                    vatp = null;
                }
            }

            //Setuj default pakovanja
            if (transportnaPakovanjaProizvoda != null)
                item.setKolicinaPoPakovanju(transportnaPakovanjaProizvoda.get(item.getProizvod()));
            //izračunaj raspolozivu kolicinu
            BigDecimal raspolozivaKolicina = new BigDecimal(0.0);

            Set<Integer> skladista = new HashSet<Integer>();
            for (WoPartnerSetting wps : woPartnerSetting) {
                skladista.add(new Integer(wps.getIdSkladista()));
            }

            for (Integer wps : skladista)
                if (item.getProveraZaliha().equals("DA")) {
                    for (UzStanjeZalihaSkladista uzskl : item.getUzStanjeZalihaSkladistas()) {
                        if (uzskl.getId().getIdSkladista() == wps.intValue() &&
                                wps.intValue() != obradjenoSkl &&
                                uzskl.getKolicinaPoStanjuZ().compareTo(new BigDecimal(0.0)) == 1) {
                            raspolozivaKolicina = raspolozivaKolicina.add(uzskl.getKolicinaPoStanjuZ().subtract(uzskl.getRezervisanaKol()));
                            item.setMaticnoSkladiste(uzskl.getId().getIdSkladista());
                            obradjenoSkl = wps.intValue();
                            if (item.getPrimeniJsklPakovanje()) {
                                item.setBrojPakovanja(uzZaliheJsklDAO.findJsklPakPerPro(item.getProizvod(), item.getMaticnoSkladiste(), null));
                                List<BigDecimal> pakNaZal = new ArrayList<BigDecimal>();
                                Iterator it = item.getBrojPakovanja().entrySet().iterator();
                                while (it.hasNext()) {
                                    Map.Entry pairs = (Map.Entry) it.next();
                                    pakNaZal.add((BigDecimal) pairs.getKey());
                                }
                                item.setJsklPakovanja(pakNaZal);
                                //}
                            }
                        }
                    }
                    if (item.getKolicinaPoPakovanju() == null)
                        item.setKolicinaPoPakovanju(new BigDecimal(1.0));
                    item.setKolUAltJM(raspolozivaKolicina.divide(item.getKolicinaPoPakovanju(), 0, RoundingMode.FLOOR).intValue());
                    item.setRaspolozivo(raspolozivaKolicina);
                } else if (item.getProveraZaliha().equals("SASTAV")) {
                    for (OcpSastavProizvoda ocpSastavProizvoda : item.getSastavProizvoda()) {
                        obradjenoSkl = 0;
                        List<UzStanjeZalihaSkladista> uzSklList = uzStanjeZalihaSkladistaDAO.findByProizvod(ocpSastavProizvoda.getProizvodIzlaz().getProizvod());
                        for (UzStanjeZalihaSkladista uzskl : uzSklList) {
                            if (uzskl.getId().getIdSkladista() == wps.intValue() &&
                                    wps.intValue() != obradjenoSkl &&
                                    uzskl.getKolicinaPoStanjuZ().subtract(uzskl.getRezervisanaKol()).divide(ocpSastavProizvoda.getKolicinaUgradnje(), 0, BigDecimal.ROUND_FLOOR).compareTo(new BigDecimal(1.0))
                                            != -1) {
                                /*-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.*/
                                raspolozivaKolicina = uzskl.getKolicinaPoStanjuZ().subtract(uzskl.getRezervisanaKol()).divide(ocpSastavProizvoda.getKolicinaUgradnje(), 0, RoundingMode.FLOOR).compareTo(raspolozivaKolicina)
                                        == -1 ? (uzskl.getKolicinaPoStanjuZ().subtract(uzskl.getRezervisanaKol()).divide(ocpSastavProizvoda.getKolicinaUgradnje(), 0, RoundingMode.FLOOR)).setScale(0, RoundingMode.FLOOR) :
                                        (raspolozivaKolicina.compareTo(new BigDecimal(0.0)) == 0 ? (uzskl.getKolicinaPoStanjuZ().subtract(uzskl.getRezervisanaKol()).divide(ocpSastavProizvoda.getKolicinaUgradnje(), 0, RoundingMode.FLOOR).setScale(0, RoundingMode.FLOOR)) : raspolozivaKolicina);
                                ocpSastavProizvoda.setMaticnoSkladiste(uzskl.getId().getIdSkladista());
                                ocpSastavProizvoda.getProizvodIzlaz().setPrimeniJsklPakovanje(false);
                                ocpSastavProizvoda.setRaspolozivaKolicina(uzskl.getKolicinaPoStanjuZ().subtract(uzskl.getRezervisanaKol()).divide(ocpSastavProizvoda.getKolicinaUgradnje(), 0, RoundingMode.FLOOR));
                                obradjenoSkl = wps.intValue();
                                if (item.getPrimeniJsklPakovanje()) {
                                    item.setBrojPakovanja(uzZaliheJsklDAO.findJsklPakPerPro(item.getProizvod(), item.getMaticnoSkladiste(), null));
                                    List<BigDecimal> pakNaZal = new ArrayList<BigDecimal>();
                                    Iterator it = item.getBrojPakovanja().entrySet().iterator();
                                    while (it.hasNext()) {
                                        Map.Entry pairs = (Map.Entry) it.next();
                                        pakNaZal.add((BigDecimal) pairs.getKey());
                                    }
                                    item.setJsklPakovanja(pakNaZal);
                                    //}
                                }
                            }
                        }
                    }

                    for (OcpSastavProizvoda ocpSastavProizvoda : item.getSastavProizvoda()) {
                        if (ocpSastavProizvoda.getRaspolozivaKolicina() == null) {
                            raspolozivaKolicina = BigDecimal.ZERO;
                            break;
                        }
                    }
                    if (item.getKolicinaPoPakovanju() == null)
                        item.setKolicinaPoPakovanju(new BigDecimal(1.0));
                    item.setKolUAltJM(raspolozivaKolicina.divide(item.getKolicinaPoPakovanju(), 0, RoundingMode.FLOOR).intValue());

                    item.setRaspolozivo(raspolozivaKolicina);
                    /*setuj cenu za sastav */
                    item.setCena(new BigDecimal("0.0"));
                    for (OcpSastavProizvoda proizvodSastav : item.getSastavProizvoda()) {
                        proizvodSastav.getProizvodIzlaz().setCena((BigDecimal) mapaCena.get(proizvodSastav.getProizvodIzlaz().getProizvod()));
                        item.setCena(item.getCena().add(proizvodSastav.getProizvodIzlaz().getCena().multiply(proizvodSastav.getKolicinaUgradnje())));
                    }

                }
            //setuj tip akcije
            if (laaMap.containsKey(item.getProizvod())) {
                item.setTipAkcije(laaMap.get(item.getProizvod()).toString());

            }
        }
    }

    public Proizvodi getProizvodiZaBrendSorted(String brendId, Map<Integer, BigDecimal> cenovnik, int pageNo, int pageSize,
                                               WoParametri woParametri, List<WoPartnerSetting> woPartnerSetting,
                                               Map<Integer, BigDecimal> transortnaPakovanjaProizvoda, CompanySetting cs, Integer oj) {

        Proizvodi proizvodi = ocpProizvodDAO.findProizvodiZaBrendSorted(brendId, pageNo, pageSize, woParametri, woPartnerSetting, cs);

        setTransAtrZaPro(proizvodi.getProizvodList(), cenovnik, woPartnerSetting, transortnaPakovanjaProizvoda, woParametri);
        return proizvodi;
    }

    public Proizvodi getProzivodiNaAkciji(String vrstaAkcije, Map<Integer, BigDecimal> cenovnik, int pageNo, int pageSize
            , WoParametri woParametri, List<WoPartnerSetting> woPartnerSetting, Map<Integer, BigDecimal> transortnaPakovanjaProizvoda) {
        Proizvodi proizvodi = ocpProizvodDAO.findProizvodiNaAkciji(vrstaAkcije, pageNo, pageSize, woParametri, woPartnerSetting);
        setTransAtrZaPro(proizvodi.getProizvodList(), cenovnik, woPartnerSetting, transortnaPakovanjaProizvoda, woParametri);

        return proizvodi;
    }

    public Proizvodi getProzivodiNaAkcijiSorted(String brandId, String vrstaAkcije, Map<Integer, BigDecimal> cenovnik, int pageNo, int pageSize
            , WoParametri woParametri, List<WoPartnerSetting> woPartnerSetting, Map<Integer, BigDecimal> transortnaPakovanjaProizvoda) {

        Proizvodi proizvodi = ocpProizvodDAO.findProizvodiNaAkcijiSorted(brandId, vrstaAkcije, pageNo, pageSize, woParametri, woPartnerSetting);
        setTransAtrZaPro(proizvodi.getProizvodList(), cenovnik, woPartnerSetting, transortnaPakovanjaProizvoda, woParametri);
        return proizvodi;
    }

    public Proizvodi getProizvoByName(String namePattern, Map<Integer, BigDecimal> cenovnik, int pageNo, int pageSize
            , WoParametri woParametri, List<WoPartnerSetting> woPartnerSetting, Map<Integer, BigDecimal> transortnaPakovanjaProizvoda) {
        Proizvodi proizvodi = ocpProizvodDAO.findProizvodiByName(namePattern, pageNo, pageSize
                , woParametri, woPartnerSetting);

        setTransAtrZaPro(proizvodi.getProizvodList(), cenovnik, woPartnerSetting, transortnaPakovanjaProizvoda, woParametri);

        return proizvodi;
    }


    public Proizvodi getFilterProizvodi(String brand, String namePattern, Map<Integer, BigDecimal> cenovnik, int pageNo, int pageSize
            , WoParametri woParametri, List<WoPartnerSetting> woPartnerSetting, Map<Integer, BigDecimal> transortnaPakovanjaProizvoda) {
        Proizvodi proizvodi = ocpProizvodDAO.findFilterProizvodi(brand, namePattern, pageNo, pageSize
                , woParametri, woPartnerSetting);
        setTransAtrZaPro(proizvodi.getProizvodList(), cenovnik, woPartnerSetting, transortnaPakovanjaProizvoda, woParametri);

        return proizvodi;
    }

    public Proizvodi getFilterProizvodiSorted(String brand, String namePattern, Map<Integer, BigDecimal> cenovnik, int pageNo, int pageSize
            , WoParametri woParametri, List<WoPartnerSetting> woPartnerSetting, Map<Integer, BigDecimal> transortnaPakovanjaProizvoda) {
        Proizvodi proizvodi = ocpProizvodDAO.findFilterProizvodiByNamePatternsSorted(brand, getTokens(namePattern), pageNo, pageSize
                , woParametri, woPartnerSetting);
        setTransAtrZaPro(proizvodi.getProizvodList(), cenovnik, woPartnerSetting, transortnaPakovanjaProizvoda, woParametri);

        return proizvodi;
    }

    public OcpProizvod getProizvodById(int id, Map<Integer, BigDecimal> cenovnik, WoParametri woParametri,
                                       List<WoPartnerSetting> woPartnerSetting, Map<Integer, BigDecimal> transortnaPakovanjaProizvoda) {
        OcpProizvod proizvod = ocpProizvodDAO.findById(id);
        List<OcpProizvod> lp = new ArrayList<OcpProizvod>(0);
        lp.add(proizvod);
        setTransAtrZaPro(lp, cenovnik, woPartnerSetting, transortnaPakovanjaProizvoda, woParametri);
        return lp.get(0);
    }

    public ProdPpRabatStavka getRabatZaProizvod(Integer proizvodId, int poslovniPartnerId, int oj) {
        List<ProdPpRabatStavka> listaRabata = (List<ProdPpRabatStavka>) prodPpRabatStavkaDAO.findRabatZaProizvod(proizvodId, poslovniPartnerId, oj);
        if (listaRabata.size() != 0)
            return listaRabata.get(0);
        ProdPpRabatStavka prodPpRabatStavka = new ProdPpRabatStavka();
        prodPpRabatStavka.setRabat(new BigDecimal(0));
        return prodPpRabatStavka;
    }


    public List<OcpProizvod> getProizvoByNameSorted(String namePattern, Map<Integer, BigDecimal> cenovnik
            , WoParametri woParametri, List<WoPartnerSetting> woPartnerSetting, Map<Integer, BigDecimal> transortnaPakovanjaProizvoda) {
        List<OcpProizvod> lp = ocpProizvodDAO.findProizvodiByNameSorted(namePattern, woParametri, woPartnerSetting);

        return lp;
    }


    public List<OcpProizvod> getProizvodiByNameAndByBrend(String namePattern, String brendId, WoParametri woParametri,
                                                          List<WoPartnerSetting> woPartnerSettings) {
        return ocpProizvodDAO.findProizvodiByNamePatternAndByBrand(getTokens(namePattern), brendId, woParametri, woPartnerSettings);
    }

    private List<String> getTokens(String namePattern) {
        List<String> searchTokens = new ArrayList<String>();
        String[] tokens = namePattern.trim().split("\\s+");
        for (String token : tokens) {
            searchTokens.add(token);
        }
        return searchTokens;
    }
}
