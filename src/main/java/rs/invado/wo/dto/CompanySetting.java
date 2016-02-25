package rs.invado.wo.dto;

import rs.invado.wo.domain.ocp.OcpKlasifikacija;
import rs.invado.wo.domain.wo.WoDownloadFiles;
import rs.invado.wo.domain.wo.WoDownloadTip;
import rs.invado.wo.domain.wo.WoKompanijaKorisnik;
import rs.invado.wo.domain.wo.WoParametri;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikola
 * Date: 2.1.13.
 * Time: 20.13
 * To change this template use File | Settings | File Templates.
 */

public class CompanySetting {


    //mapa objekata kompanija korisnika apliakcija. Key mape je id kompanije
    private Map<Integer, WoKompanijaKorisnik> kompanijaKorisnikMap = new HashMap<Integer, WoKompanijaKorisnik>(0);
    // Mapa setinga karakterističnih za svaku kompaniju korinsik. Key mape je id kompanije
    private Map<Integer, WoParametri> kompanijskiParametri = new HashMap<Integer, WoParametri>(0);
    //Mapa klasifikacija za meni po kompanijama korisnicima. Key je id kompanije
    private Map<String, List<OcpKlasifikacija>> meni = new HashMap<String, List<OcpKlasifikacija>>(0);
    //Klasifikacija za prvi nivo filtera. Key je id kompanije
    private Map<String, List<OcpKlasifikacija>> filterPrviNivo = new HashMap<String, List<OcpKlasifikacija>>(0);
    //Klasifikacija za drugi nivo filtera. Key je u obliku 'idKompanije/nadrdjenaklasa'. Npr 49/01
    private Map<String, List<OcpKlasifikacija>> filterDrugiNivo = new HashMap<String, List<OcpKlasifikacija>>(0);
    //Transportna pakovanja za sve proizvode koji imaju definisana pakovanja. Key je id proivzoda
    private Map<Integer, BigDecimal> trasportnaPakovanja = new HashMap<Integer, BigDecimal>(0);
    //Mapa tipova za download.Key je kompanija.
    private Map<String, List<WoDownloadTip>> downloadTip = new HashMap<String, List<WoDownloadTip>>(0);
    //Mapa fajlova za odgovarajući tip. Key je tip za download.
    private Map<String, Map<String, List<WoDownloadFiles>>> downloadFilesZaTip = new LinkedHashMap<String, Map<String, List<WoDownloadFiles>>>(0);
    //Map najprodavanijih artikala za menjajuću listu
    private Map<String, Proizvodi> listaNajprodavanijih= new HashMap<String, Proizvodi>();

    public Map<String, List<OcpKlasifikacija>> getMeni() {
        return meni;
    }

    public void setMeni(Map<String, List<OcpKlasifikacija>> meni) {
        this.meni = meni;
    }

    public Map<Integer, WoKompanijaKorisnik> getKompanijaKorisnikMap() {
        return kompanijaKorisnikMap;
    }

    public void setKompanijaKorisnikMap(Map<Integer, WoKompanijaKorisnik> kompanijaKorisnikMap) {
        this.kompanijaKorisnikMap = kompanijaKorisnikMap;
    }

    public Map<Integer, WoParametri> getKompanijskiParametri() {
        return kompanijskiParametri;
    }

    public void setKompanijskiParametri(Map<Integer, WoParametri> kompanijskiParametri) {
        this.kompanijskiParametri = kompanijskiParametri;
    }

    public Map<String, List<OcpKlasifikacija>> getFilterPrviNivo() {
        return filterPrviNivo;
    }

    public void setFilterPrviNivo(Map<String, List<OcpKlasifikacija>> filterPrviNivo) {
        this.filterPrviNivo = filterPrviNivo;
    }

    public Map<String, List<OcpKlasifikacija>> getFilterDrugiNivo() {
        return filterDrugiNivo;
    }

    public void setFilterDrugiNivo(Map<String, List<OcpKlasifikacija>> filterDrugiNivo) {
        this.filterDrugiNivo = filterDrugiNivo;
    }

    public Map<Integer, BigDecimal> getTrasportnaPakovanja() {
        return trasportnaPakovanja;
    }

    public void setTrasportnaPakovanja(Map<Integer, BigDecimal> trasportnaPakovanja) {
        this.trasportnaPakovanja = trasportnaPakovanja;
    }

    public Map<String, List<WoDownloadTip>> getDownloadTip() {
        return downloadTip;
    }

    public void setDownloadTip(Map<String, List<WoDownloadTip>> downloadTip) {
        this.downloadTip = downloadTip;
    }

    public Map<String, Map<String, List<WoDownloadFiles>>> getDownloadFilesZaTip() {
        return downloadFilesZaTip;
    }

    public void setDownloadFilesZaTip(Map<String, Map<String, List<WoDownloadFiles>>> downloadFilesZaTip) {
        this.downloadFilesZaTip = downloadFilesZaTip;
    }

    public Map<String, Proizvodi> getListaNajprodavanijih() {
        return listaNajprodavanijih;
    }

    public void setListaNajprodavanijih(Map<String, Proizvodi> listaNajprodavanijih) {
        this.listaNajprodavanijih = listaNajprodavanijih;
    }
}
