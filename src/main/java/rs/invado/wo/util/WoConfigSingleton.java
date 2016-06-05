package rs.invado.wo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by IntelliJ IDEA.
 * User:
 * Date: 29.12.12.
 * Time: 20.32
 * To change this template use File | Settings | File Templates.
 */
@Component
public class WoConfigSingleton {

    @Inject
    private Environment env;

    private static WoConfigSingleton ourInstance = new WoConfigSingleton();

    @Value("${wo.mailaPassword}")
    private String mailaPassword;
    private String mailAddress;
    @Value("${wo.woMailAddress}")
    private String woMailAddress;
    private String mailServer;
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
    @Value("#{'${wo.attributes}'.split(',')}")
    private String[] attributes = new String[5];
    private String neki;

    public static WoConfigSingleton getInstance() {
        return ourInstance;
    }

    private WoConfigSingleton() {

    }

    public static WoConfigSingleton getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(WoConfigSingleton ourInstance) {
        WoConfigSingleton.ourInstance = ourInstance;
    }

    public String getMailaPassword() {
        return mailaPassword;
    }


    public String getMailAddress() {
        return mailAddress;
    }


    public String getWoMailAddress() {
        return woMailAddress;
    }


    public String getMailServer() {
        return mailServer;
    }


    public String getIdDocForCash() {
        return idDocForCash;
    }


    public String getIdDocForVirman() {
        return idDocForVirman;
    }


    public String getVrstaStavke() {
        return vrstaStavke;
    }


    public String getIdDocGen() {
        return idDocGen;
    }


    public String getSecondFilterlevel() {
        return secondFilterlevel;
    }

    public String getIdFilterTypeKlasification() {
        return idFilterTypeKlasification;
    }


    public String getIdMenuTypeCLasification() {
        return idMenuTypeCLasification;
    }


    public String[] getAttributes() {
        return attributes;
    }

    public String getNeki() {
        return neki;
    }

    public void setNeki(String neki) {
        this.neki = neki;
    }
}
