package rs.invado.wo.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Djordje
 * Date: 1/21/13
 * Time: 4:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class IncreaseReservation {
    String errorMsg;
    int brojStavki;
    BigDecimal ukupnaCena;
    BigDecimal stanjeZaliha;
    Integer stanjeZalihaAlt;
    String jm;
    String jmAlt;
    private Boolean primeniJsklPakovanje;
    private List<BigDecimal> jsklPakovanja = new ArrayList<BigDecimal>(0);
    private Map<BigDecimal, ZalihaPoPakovanjima> brojPakovanja;

    public IncreaseReservation(int brojStavki, BigDecimal ukupnaCena, BigDecimal stanjeZaliha, String jm, Integer stanjeZalihaAlt, String jmAlt) {
        this.brojStavki=brojStavki;
        this.ukupnaCena=ukupnaCena;
        this.stanjeZaliha=stanjeZaliha;
        this.jm=jm;
        this.stanjeZalihaAlt=stanjeZalihaAlt;
        this.jmAlt=jmAlt;
    }
    public IncreaseReservation(String errorMsg,int brojStavki, BigDecimal ukupnaCena, BigDecimal stanjeZaliha, String jm, Integer stanjeZalihaAlt, String jmAlt) {
        this.errorMsg=errorMsg;
        this.brojStavki=brojStavki;
        this.ukupnaCena=ukupnaCena;
        this.stanjeZaliha=stanjeZaliha;
        this.jm=jm;
        this.stanjeZalihaAlt=stanjeZalihaAlt;
        this.jmAlt=jmAlt;
    }

    public IncreaseReservation(int brojStavki, BigDecimal ukupnaCena, BigDecimal stanjeZaliha, String jm, Integer stanjeZalihaAlt, String jmAlt, Boolean primeniJsklPakovanje,
                               List<BigDecimal> jsklPakovanja, Map<BigDecimal, ZalihaPoPakovanjima> brojPakovanja) {
        this.brojStavki=brojStavki;
        this.ukupnaCena=ukupnaCena;
        this.stanjeZaliha=stanjeZaliha;
        this.jm=jm;
        this.stanjeZalihaAlt=stanjeZalihaAlt;
        this.jmAlt=jmAlt;
        this.primeniJsklPakovanje = primeniJsklPakovanje;
        this.jsklPakovanja = jsklPakovanja;
        this.brojPakovanja = brojPakovanja;

        if (brojPakovanja != null) {
            Iterator it = brojPakovanja.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();
            }
        }
    }
    public IncreaseReservation(String errorMsg) {
        this.errorMsg=errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getBrojStavki() {
        return brojStavki;
    }

    public void setBrojStavki(int brojStavki) {
        this.brojStavki = brojStavki;
    }

    public BigDecimal getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(BigDecimal ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    public BigDecimal getStanjeZaliha() {
        return stanjeZaliha;
    }

    public void setStanjeZaliha(BigDecimal stanjeZaliha) {
        this.stanjeZaliha = stanjeZaliha;
    }

    public Integer getStanjeZalihaAlt() {
        return stanjeZalihaAlt;
    }

    public void setStanjeZalihaAlt(Integer stanjeZalihaAlt) {
        this.stanjeZalihaAlt = stanjeZalihaAlt;
    }

    public String getJm() {
        return jm;
    }

    public void setJm(String jm) {
        this.jm = jm;
    }

    public String getJmAlt() {
        return jmAlt;
    }

    public void setJmAlt(String jmAlt) {
        this.jmAlt = jmAlt;
    }

    public Boolean getPrimeniJsklPakovanje() {
        return primeniJsklPakovanje;
    }

    public void setPrimeniJsklPakovanje(Boolean primeniJsklPakovanje) {
        this.primeniJsklPakovanje = primeniJsklPakovanje;
    }

    public List<BigDecimal> getJsklPakovanja() {
        return jsklPakovanja;
    }

    public void setJsklPakovanja(List<BigDecimal> jsklPakovanja) {
        this.jsklPakovanja = jsklPakovanja;
    }

    public Map<BigDecimal, ZalihaPoPakovanjima> getBrojPakovanja() {
        return brojPakovanja;
    }

    public void setBrojPakovanja(Map<BigDecimal, ZalihaPoPakovanjima> brojPakovanja) {
        this.brojPakovanja = brojPakovanja;
    }
}
