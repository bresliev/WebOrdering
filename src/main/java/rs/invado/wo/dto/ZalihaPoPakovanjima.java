package rs.invado.wo.dto;

import java.math.BigDecimal;

/**
 * Created by NikolaB on 10/18/2014.
 */
public class ZalihaPoPakovanjima {

    private BigDecimal kolPoPovezu;
    private BigDecimal kolPoPakovanju;
    private BigDecimal brojPakovanja;


    public BigDecimal getKolPoPovezu() {
        return kolPoPovezu;
    }

    public void setKolPoPovezu(BigDecimal kolPoPovezu) {
        this.kolPoPovezu = kolPoPovezu;
    }

    public BigDecimal getKolPoPakovanju() {
        return kolPoPakovanju;
    }

    public void setKolPoPakovanju(BigDecimal kolPoPakovanju) {
        this.kolPoPakovanju = kolPoPakovanju;
    }

    public BigDecimal getBrojPakovanja() {
        return brojPakovanja;
    }

    public void setBrojPakovanja(BigDecimal brojPakovanja) {
        this.brojPakovanja = brojPakovanja;
    }
}
