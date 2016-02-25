package rs.invado.wo.dto;

import rs.invado.wo.domain.ocp.OcpValuta;
import rs.invado.wo.domain.prod.ProdCenovnik;
import rs.invado.wo.domain.wo.WoPartnerSetting;
import rs.invado.wo.domain.wo.WoRezervacija;
import rs.invado.wo.domain.wo.WoUser;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikola
 * Date: 2.1.13.
 * Time: 17.10
 * To change this template use File | Settings | File Templates.
 */
public class User {

    private WoUser woUser;
    private List<WoPartnerSetting> woPartnerSetting = new ArrayList<WoPartnerSetting>(0);
    private Map<Integer, BigDecimal> ceneProizvoda = new HashMap<Integer, BigDecimal>(0);
    private Map<String, WoRezervacija> basket = new LinkedHashMap<String, WoRezervacija> ();
    private List<WoRezervacija> basketList = new ArrayList<WoRezervacija>(0);
    private BigDecimal orderValue = BigDecimal.valueOf(0.0d);
    private BigDecimal orderValueWithTax = BigDecimal.valueOf(0.0d);
    private ProdCenovnik prodCenovnik = new ProdCenovnik();
    private OcpValuta valuta = new OcpValuta();
    private Map<String, Boolean> woUserHasRights = new HashMap<String, Boolean>();

    public WoUser getWoUser() {
        return woUser;
    }

    public void setWoUser(WoUser woUser) {
        this.woUser = woUser;
    }

    public List<WoPartnerSetting> getWoPartnerSetting() {
        return woPartnerSetting;
    }

    public void setWoPartnerSetting(List<WoPartnerSetting> woPartnerSetting) {
        this.woPartnerSetting = woPartnerSetting;
    }

    public Map<Integer, BigDecimal> getCeneProizvoda() {
        return ceneProizvoda;
    }

    public void setCeneProizvoda(Map<Integer, BigDecimal> ceneProizvoda) {
        this.ceneProizvoda = ceneProizvoda;
    }

    public Map<String, WoRezervacija> getBasket() {
        return basket;
    }

    public void setBasket(Map<String, WoRezervacija> basket) {
        this.basket = basket;
    }

    public ProdCenovnik getProdCenovnik() {
        return prodCenovnik;
    }

    public void setProdCenovnik(ProdCenovnik prodCenovnik) {
        this.prodCenovnik = prodCenovnik;
    }

    public OcpValuta getValuta() {
        return valuta;
    }

    public void setValuta(OcpValuta valuta) {
        this.valuta = valuta;
    }

    public BigDecimal getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(BigDecimal orderValue) {
        this.orderValue = orderValue;
    }

    public List<WoRezervacija> getBasketList() {
        return basketList;
    }

    public void setBasketList(List<WoRezervacija> basketList) {
        this.basketList = basketList;
    }

    public BigDecimal getOrderValueWithTax() {
        return orderValueWithTax;
    }

    public void setOrderValueWithTax(BigDecimal orderValueWithTax) {
        this.orderValueWithTax = orderValueWithTax;
    }

    public Map<String, Boolean> getWoUserHasRights() {
        return woUserHasRights;
    }

    public void setWoUserHasRights(Map<String, Boolean> woUserHasRights) {
        this.woUserHasRights = woUserHasRights;
    }
}
