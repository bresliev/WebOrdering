package rs.invado.wo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by .
 */

@Component
public class EtoConf {

    @Inject
    Environment env;

    @Value("${wo.mailaPassword}")
    private String mailaPassword;
    @Value("${wo.woMailAddress}")
    private String woMailAddress;
    @Value("${wo.idDocForCash}")
    private String idDocForCash;
    @Value("${wo.idDocForVirman}")
    private String idDocForVirman;
    @Value("${wo.vrstaStavke}")
    private String vrstaStavke;
    @Value("${wo.idDocGen}")
    private String idDocGen;
    @Value("${wo.secondFilterlevel}")
    private String secondFilterlevel;
    @Value("${wo.idFilterTypeKlasification}")
    private String idFilterTypeKlasification;
    @Value("${wo.idMenuTypeCLasification}")
    private String idMenuTypeCLasification;


    public String getMailaPassword() {
        return mailaPassword;
    }

    public void setMailaPassword(String mailaPassword) {
        this.mailaPassword = mailaPassword;
    }

    public String getWoMailAddress() {
        return woMailAddress;
    }

    public void setWoMailAddress(String woMailAddress) {
        this.woMailAddress = woMailAddress;
    }

    public String getIdDocForCash() {
        return idDocForCash;
    }

    public void setIdDocForCash(String idDocForCash) {
        this.idDocForCash = idDocForCash;
    }

    public String getIdDocForVirman() {
        return idDocForVirman;
    }

    public void setIdDocForVirman(String idDocForVirman) {
        this.idDocForVirman = idDocForVirman;
    }

    public String getVrstaStavke() {
        return vrstaStavke;
    }

    public void setVrstaStavke(String vrstaStavke) {
        this.vrstaStavke = vrstaStavke;
    }

    public String getIdDocGen() {
        return idDocGen;
    }

    public void setIdDocGen(String idDocGen) {
        this.idDocGen = idDocGen;
    }

    public String getSecondFilterlevel() {
        return secondFilterlevel;
    }

    public void setSecondFilterlevel(String secondFilterlevel) {
        this.secondFilterlevel = secondFilterlevel;
    }

    public String getIdFilterTypeKlasification() {
        return idFilterTypeKlasification;
    }

    public void setIdFilterTypeKlasification(String idFilterTypeKlasification) {
        this.idFilterTypeKlasification = idFilterTypeKlasification;
    }

    public String getIdMenuTypeCLasification() {
        return idMenuTypeCLasification;
    }

    public void setIdMenuTypeCLasification(String idMenuTypeCLasification) {
        this.idMenuTypeCLasification = idMenuTypeCLasification;
    }
}
