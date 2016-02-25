package rs.invado.wo.util;

/**
 * Created by IntelliJ IDEA.
 * User: nikola
 * Date: 29.12.12.
 * Time: 20.32
 * To change this template use File | Settings | File Templates.
 */
public class WoConfigSingleton {

    private static WoConfigSingleton ourInstance = new WoConfigSingleton();


    private String mailaPassword;
    private String mailAddress;
    private String woMailAddress;
    private String mailServer;
    private String idDocForCash;
    private String idDocForVirman;
    private String vrstaStavke;
    private String idDocGen;
    private String secondFilterlevel;
    private String idFilterTypeKlasification;
    private String idMenuTypeCLasification;
    private String[] atributs = new String[4];
    private  String neki;

    public static WoConfigSingleton getInstance() {
        return ourInstance;
    }

    private WoConfigSingleton() {

        this.idMenuTypeCLasification = "7";
        this.idFilterTypeKlasification = "2";
        this.secondFilterlevel = "2";
        this.idDocGen = "12";
        this.vrstaStavke = "25";
        this.idDocForVirman = "12";
        this.idDocForCash = "163";
        this.mailServer = "smtp.gmail.com";
        this.woMailAddress = "darex.rs@gmail.com";
        this.mailAddress = "webordering@darex.rs";
        this.mailaPassword = "Sto.Si.Gadjo.Konja";

        //DEZEN
        this.atributs[0] = "1600";
        //STRUKTURA
        this.atributs[1] = "1601";
        //PROIZVODJAÈ
        this.atributs[2] = "1541";
        //PRIMENIJSKLPAK
        this.atributs[3] = "1740";

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


    public String[] getAtributs() {
        return atributs;
    }

    public String getNeki() {
        return neki;
    }

    public void setNeki(String neki) {
        this.neki = neki;
    }
}
