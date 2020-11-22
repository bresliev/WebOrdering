package rs.invado.wo.service;


import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.dao.ocp.OcpKlasifikacijaProizvodaHome;
import rs.invado.wo.dao.ocp.OcpProizvodHome;
import rs.invado.wo.dao.ocp.OcpVrAtrProizvodHome;
import rs.invado.wo.dao.prod.ProdMaxRabatiHome;
import rs.invado.wo.dao.prod.ProdPpRabatStavkaHome;
import rs.invado.wo.dao.uz.UzSkladisteHome;
import rs.invado.wo.dao.uz.UzStanjeZalihaSkladistaHome;
import rs.invado.wo.dao.uz.UzZaliheJsklHome;
import rs.invado.wo.dao.wo.WoArtikliNaAkcijiHome;
import rs.invado.wo.dao.wo.WoMapKompanijskaSkladistaHome;
import rs.invado.wo.domain.ocp.OcpKlasifikacijaProizvoda;
import rs.invado.wo.domain.ocp.OcpProizvod;
import rs.invado.wo.domain.ocp.OcpSastavProizvoda;
import rs.invado.wo.domain.prod.ProdPpRabatStavka;
import rs.invado.wo.domain.uz.UzStanjeZalihaSkladista;
import rs.invado.wo.domain.uz.UzStanjeZalihaSkladistaId;
import rs.invado.wo.domain.wo.WoArtikliNaAkciji;
import rs.invado.wo.domain.wo.WoMapKompanijskaSkladista;
import rs.invado.wo.domain.wo.WoParametri;
import rs.invado.wo.domain.wo.WoPartnerSetting;
import rs.invado.wo.dto.CompanySetting;
import rs.invado.wo.dto.Proizvodi;
import rs.invado.wo.dto.VrAtrProizvod;
import rs.invado.wo.util.WoConfigSingleton;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.*;

@Service
@Transactional
public class ProductService {

    @Inject
    private OcpProizvodHome ocpProizvodDAO;
    @Inject
    private OcpVrAtrProizvodHome ocpVrAtrProizvodDAO;
    @Inject
    private OcpKlasifikacijaProizvodaHome ocpKlasifikacijaProizvodaHome;
    @Inject
    private WoArtikliNaAkcijiHome woArtikliNaAkcijiDAO;
    @Inject
    private ProdPpRabatStavkaHome prodPpRabatStavkaDAO;
    @Inject
    private UzZaliheJsklHome uzZaliheJsklDAO;
    @Inject
    private UzStanjeZalihaSkladistaHome uzStanjeZalihaSkladistaDAO;
    @Inject
    WoConfigSingleton woConfigSingleton;
    @Inject
    private UzSkladisteHome uzSkladisteDAO;
    @Inject
    private WoMapKompanijskaSkladistaHome woMapKompanijskaSkladistaDAO;
    @Inject
    private ProdMaxRabatiHome prodMaxRabatiDAO;



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
    public final static String NOVO_IZDVOJENO = "NOVO_IZDVOJENO";

    public void setTransAtrZaPro(List<OcpProizvod> lp, Map<Integer, BigDecimal> mapaCena, List<WoPartnerSetting> woPartnerSetting,
                                 Map<Integer, BigDecimal> transportnaPakovanjaProizvoda, WoParametri woParametri, Integer OJ) {
        //Izvuci količinu po pakovanju

        Map<Integer, BigDecimal> m = mapaCena;
        //List<WoArtikliNaAkciji> laa = woArtikliNaAkcijiDAO.findArtikliNaAkcijiAktivno(DateUtils.truncate(new Date(), Calendar.DATE),  woParametri);
        /*Map<Integer, String> laaMap = new HashMap<Integer, String>();
        for (WoArtikliNaAkciji o : laa) {
            laaMap.put((Integer) o.getOcpProizvod().getProizvod(), (String) o.getTipAkcije());
        }*/


        int i = 0;
        int obradjenoSkl = 0;

        for (OcpProizvod item : lp) {
            obradjenoSkl = 0;
            WoArtikliNaAkciji wana = woArtikliNaAkcijiDAO.findAkcijiZaArtikal(DateUtils.truncate(new Date(), Calendar.DATE), woParametri, item);
            item.setTipAkcije(wana == null ? null : wana.getTipAkcije());
            //Setuj porez
            item.setStopaPoreza(ocpProizvodDAO.findStopaPorezaZaProizvod(woPartnerSetting.get(0).getOrganizacionaJedinica(), item.getProizvod()));
            // setuj cene
            item.setCena((BigDecimal) mapaCena.get(item.getProizvod()));

            //setuj atribute
            String dezenIStruktura = "";
            String vrAtr = null;
            List<VrAtrProizvod> vatpList = ocpVrAtrProizvodDAO.findByNivoAtributa(item.getProizvod(), woConfigSingleton.getAttributes());
            for (OcpKlasifikacijaProizvoda klasifikacijaProizvoda : item.getOcpKlasifikacijaProizvoda()) {
                for (VrAtrProizvod ocpVrAtrProizvod : vatpList) {
                    if (klasifikacijaProizvoda.getId().getVrstaKlasifikacije() == 1
                            && ocpVrAtrProizvod.getVrstaKlasifikacije() == 1) {
                        if (ocpVrAtrProizvod.getAtribut() == Integer.valueOf(woConfigSingleton.getAttributes()[0]) && ocpVrAtrProizvod.getVrednost() != null) {
                            dezenIStruktura = dezenIStruktura + " " + ocpVrAtrProizvod.getVrednost();
                        } else if (ocpVrAtrProizvod.getAtribut() == Integer.valueOf(woConfigSingleton.getAttributes()[1]) && ocpVrAtrProizvod.getVrednost() != null) {
                            dezenIStruktura = dezenIStruktura + " " + ocpVrAtrProizvod.getVrednost();
                        } else if (ocpVrAtrProizvod.getAtribut() == Integer.valueOf(woConfigSingleton.getAttributes()[2]) && ocpVrAtrProizvod.getVrednost() != null) {
                            item.setProizvodjac(ocpVrAtrProizvod.getVrednost());
                        }
                    } else if (klasifikacijaProizvoda.getId().getVrstaKlasifikacije() == woParametri.getVrstaKlasifikacijeMeni()
                            && ocpVrAtrProizvod.getVrstaKlasifikacije() == woParametri.getVrstaKlasifikacijeMeni()) {
                        if (ocpVrAtrProizvod.getAtribut() == Integer.valueOf(woConfigSingleton.getAttributes()[3]) && ocpVrAtrProizvod.getVrednost() != null
                                && ocpVrAtrProizvod.getVrednost().equals("DA")) {
                            item.setPrimeniJsklPakovanje(true);
                        } else if (ocpVrAtrProizvod.getAtribut() == Integer.valueOf(woConfigSingleton.getAttributes()[3]) && ocpVrAtrProizvod.getVrednost() != null
                                && !ocpVrAtrProizvod.getVrednost().equals("DA")) {
                            item.setPrimeniJsklPakovanje(false);
                        }
                        if (ocpVrAtrProizvod.getAtribut() == Integer.valueOf(woConfigSingleton.getAttributes()[4]) && ocpVrAtrProizvod.getVrednost() != null) {
                            item.setProveraZaliha(ocpVrAtrProizvod.getVrednost());
                        }
                    }
                }
            }

            item.setDezenIstruktira(dezenIStruktura);
            item.setJedinicaMereRezervacije(woParametri.getJedinicaMereRezervacije());
            for (OcpKlasifikacijaProizvoda ocpKlasifikacija : item.getOcpKlasifikacijaProizvoda()) {
                if (ocpKlasifikacija.getId().getVrstaKlasifikacije() != 1) {
                    item.setMaxRabat(prodMaxRabatiDAO.findByKlasa(OJ, ocpKlasifikacija.getId().getVrstaKlasifikacije(), ocpKlasifikacija.getId().getKlasifikacija()).getMaxRabat());
                    if (item.getMaxRabat().compareTo(new BigDecimal(-1)) != 0)
                        break;
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
                //skladista.add(new Integer(woMapKompanijskaSkladistaDAO.findActualSklRaspolozivo(wps.getIdSkladista()).getUzSkladisteRezervacija()));
            }
            for (Integer wps : skladista) {
                if (item.getProveraZaliha().equals("DA")) {
                    for (UzStanjeZalihaSkladista uzskl : item.getUzStanjeZalihaSkladistas()) {
                        if (uzskl.getId().getIdSkladista() == wps.intValue() &&
                                wps.intValue() != obradjenoSkl &&
                                uzskl.getKolicinaPoStanjuZ().compareTo(new BigDecimal(0.0)) == 1) {
                            raspolozivaKolicina = raspolozivaKolicina.add(uzskl.getKolicinaPoStanjuZ().subtract(uzskl.getRezervisanaKol()));
                            if (uzskl.getId().getIdSkladista() == 18) {
                                item.setMaticnoSkladiste(10);
                            }else{
                                item.setMaticnoSkladiste(uzskl.getId().getIdSkladista());
                            }

                            obradjenoSkl = wps.intValue();
                            WoMapKompanijskaSkladista woMapKompanijskaSkladista = woMapKompanijskaSkladistaDAO.findActualSklRaspolozivo(uzSkladisteDAO.findById(wps.intValue()));
                            if (!(woMapKompanijskaSkladista == null) && woMapKompanijskaSkladista.getRaspolozivaKolicinaUSkl().equals("OBASKLADISTA")) {
                                UzStanjeZalihaSkladistaId uzStanjeZalihaSkladistaId = new UzStanjeZalihaSkladistaId(item.getProizvod(), (short) woMapKompanijskaSkladista.getUzSkladisteRaspolozivo().getIdSkladista());
                                UzStanjeZalihaSkladista uzStanjeZalihaSkladista = uzStanjeZalihaSkladistaDAO.findById(uzStanjeZalihaSkladistaId);
                                raspolozivaKolicina = raspolozivaKolicina.add(uzStanjeZalihaSkladista.getKolicinaPoStanjuZ().subtract(uzStanjeZalihaSkladista.getRezervisanaKol()));

                            }
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

                    item.setRaspolozivoPakovanja();

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

                    item.setRaspolozivoPakovanja();

                    /*setuj cenu za sastav */
                    item.setCena(new BigDecimal("0.0"));
                    for (OcpSastavProizvoda proizvodSastav : item.getSastavProizvoda()) {
                        proizvodSastav.getProizvodIzlaz().setCena((BigDecimal) mapaCena.get(proizvodSastav.getProizvodIzlaz().getProizvod()));
                        item.setCena(item.getCena().add(proizvodSastav.getProizvodIzlaz().getCena().multiply(proizvodSastav.getKolicinaUgradnje())));
                    }

                }
            }

            //setuj tip akcije
            /*if (laaMap.containsKey(item.getProizvod())) {
                item.setTipAkcije(laaMap.get(item.getProizvod()).toString());

            }*/
        }
    }

    public Proizvodi getProizvodiZaBrendSorted(String brendId, Map<Integer, BigDecimal> cenovnik, int pageNo, int pageSize,
                                               WoParametri woParametri, List<WoPartnerSetting> woPartnerSetting,
                                               Map<Integer, BigDecimal> transortnaPakovanjaProizvoda, CompanySetting cs, Integer oj) {

        Proizvodi proizvodi = null;
        try {
            proizvodi = ocpProizvodDAO.findProizvodiZaBrendSorted(brendId, pageNo, pageSize, woParametri, woPartnerSetting, cs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        setTransAtrZaPro(proizvodi.getProizvodList(), cenovnik, woPartnerSetting, transortnaPakovanjaProizvoda, woParametri, oj);
        return proizvodi;
    }

    public Proizvodi getProzivodiNaAkciji(String vrstaAkcije, Map<Integer, BigDecimal> cenovnik, int pageNo, int pageSize
            , WoParametri woParametri, List<WoPartnerSetting> woPartnerSetting, Map<Integer, BigDecimal> transortnaPakovanjaProizvoda, Integer oj) {
        Proizvodi proizvodi = ocpProizvodDAO.findProizvodiNaAkciji(vrstaAkcije, pageNo, pageSize, woParametri, woPartnerSetting);
        setTransAtrZaPro(proizvodi.getProizvodList(), cenovnik, woPartnerSetting, transortnaPakovanjaProizvoda, woParametri, oj);
        return proizvodi;
    }


    public Proizvodi getProzivodiNaAkcijiSorted(String brandId, String vrstaAkcije, Map<Integer, BigDecimal> cenovnik, int pageNo, int pageSize
            , WoParametri woParametri, List<WoPartnerSetting> woPartnerSetting, Map<Integer, BigDecimal> transortnaPakovanjaProizvoda, Integer OJ) {

        Proizvodi proizvodi = null;
        try {
            proizvodi = ocpProizvodDAO.findProizvodiNaAkcijiSorted(brandId, vrstaAkcije, pageNo, pageSize, woParametri, woPartnerSetting);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setTransAtrZaPro(proizvodi.getProizvodList(), cenovnik, woPartnerSetting, transortnaPakovanjaProizvoda, woParametri, OJ);
        return proizvodi;
    }

    public Proizvodi getFilterProizvodiSorted(String brand, String namePattern, Map<Integer, BigDecimal> cenovnik, int pageNo, int pageSize
            , WoParametri woParametri, List<WoPartnerSetting> woPartnerSetting, Map<Integer, BigDecimal> transortnaPakovanjaProizvoda, Integer OJ) {
        Proizvodi proizvodi = ocpProizvodDAO.findFilterProizvodiByNamePatternsSorted(brand, getTokens(namePattern), pageNo, pageSize
                , woParametri, woPartnerSetting);
        setTransAtrZaPro(proizvodi.getProizvodList(), cenovnik, woPartnerSetting, transortnaPakovanjaProizvoda, woParametri, OJ);

        return proizvodi;
    }

    public OcpProizvod getProizvodById(int id, Map<Integer, BigDecimal> cenovnik, WoParametri woParametri, int pageNo, int pageSize,
                                       List<WoPartnerSetting> woPartnerSetting, Map<Integer, BigDecimal> transortnaPakovanjaProizvoda, Integer OJ) {

        //OcpProizvod proizvod = ocpProizvodDAO.findById(id);
        OcpProizvod proizvod = ocpProizvodDAO.findFilterProizvodiById(new Integer(id), pageNo, pageSize, woParametri, woPartnerSetting);


        List<OcpProizvod> lp = new ArrayList<OcpProizvod>();
        if (proizvod != null) {
            lp.add(proizvod);

            setTransAtrZaPro(lp, cenovnik, woPartnerSetting, transortnaPakovanjaProizvoda, woParametri, OJ);

            return lp.get(0);
        }
        return null;
    }

    public OcpProizvod getProizvodByIdAll(int id, Map<Integer, BigDecimal> cenovnik, WoParametri woParametri, int pageNo, int pageSize,
                                          List<WoPartnerSetting> woPartnerSetting, Map<Integer, BigDecimal> transortnaPakovanjaProizvoda, Integer OJ) {

        //OcpProizvod proizvod = ocpProizvodDAO.findById(id);
        OcpProizvod proizvod = ocpProizvodDAO.findFilterProizvodiByIdAll(new Integer(id), pageNo, pageSize, woParametri, woPartnerSetting);


        List<OcpProizvod> lp = new ArrayList<OcpProizvod>();
        if (proizvod != null) {
            lp.add(proizvod);
            setTransAtrZaPro(lp, cenovnik, woPartnerSetting, transortnaPakovanjaProizvoda, woParametri, OJ);

            return lp.get(0);
        }
        return null;
    }

    public OcpProizvod getProizvodByIdR(int id, Map<Integer, BigDecimal> cenovnik, WoParametri woParametri, int pageNo, int pageSize,
                                        List<WoPartnerSetting> woPartnerSetting, Map<Integer, BigDecimal> transortnaPakovanjaProizvoda, Integer OJ) {

        //OcpProizvod proizvod = ocpProizvodDAO.findById(id);
        OcpProizvod proizvod = ocpProizvodDAO.findProizvodiByIdR(new Integer(id), pageNo, pageSize, woParametri, woPartnerSetting);
        List<OcpProizvod> lp = new ArrayList<OcpProizvod>();
        if (proizvod != null) {
            lp.add(proizvod);
            setTransAtrZaPro(lp, cenovnik, woPartnerSetting, transortnaPakovanjaProizvoda, woParametri, OJ);
            return lp.get(0);
        }
        return null;
    }

    public ProdPpRabatStavka getRabatZaProizvod(Integer proizvodId, int poslovniPartnerId, int oj) {
        List<ProdPpRabatStavka> listaRabata = (List<ProdPpRabatStavka>) prodPpRabatStavkaDAO.findRabatZaProizvod(proizvodId, poslovniPartnerId, oj);
        if (listaRabata.size() != 0)
            return listaRabata.get(0);
        ProdPpRabatStavka prodPpRabatStavka = new ProdPpRabatStavka();
        prodPpRabatStavka.setRabat(new BigDecimal(0));
        return prodPpRabatStavka;
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
