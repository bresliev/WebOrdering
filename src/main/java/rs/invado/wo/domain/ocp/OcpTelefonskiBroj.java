package rs.invado.wo.domain.ocp;

import javax.persistence.*;

/**
 * Created by Nikola on 23/09/2017.
 */
@Entity
@Table(name = "OCP_TELEFONSKI_BROJ", schema = "DAREX")
public class OcpTelefonskiBroj {

    private OcpPoslovniPartner poslovniPartner;
    private String telefonskiBroj;
    private String napomena;
    private int idVrsteTelefonskogBroja;

    public OcpTelefonskiBroj() {
    }

    public OcpTelefonskiBroj(OcpPoslovniPartner poslovniPartner, String telefonskiBroj, String napomena, int idVrsteTelefonskogBroja) {
        this.poslovniPartner = poslovniPartner;
        this.telefonskiBroj = telefonskiBroj;
        this.napomena = napomena;
        this.idVrsteTelefonskogBroja = idVrsteTelefonskogBroja;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "POSLOVNI_PARTNER#", referencedColumnName = "POSLOVNI_PARTNER#", insertable = true, updatable = true)
    public OcpPoslovniPartner getPoslovniPartner() {
        return poslovniPartner;
    }

    public void setPoslovniPartner(OcpPoslovniPartner poslovniPartner) {
        this.poslovniPartner = poslovniPartner;
    }


    @Id
    @Column(name = "TELEFONSKI_BROJ", nullable = false, length = 60)
    public String getTelefonskiBroj() {
        return telefonskiBroj;
    }

    public void setTelefonskiBroj(String telefonskiBroj) {
        this.telefonskiBroj = telefonskiBroj;
    }

    @Column(name = "NAPOMENA", nullable = false, length = 60)
    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }


    @Column(name = "ID_VRSTE_TELEFONSKOG_BROJA", nullable = false, length = 60)
    public int getIdVrsteTelefonskogBroja() {
        return idVrsteTelefonskogBroja;
    }

    public void setIdVrsteTelefonskogBroja(int idVrsteTelefonskogBroja) {
        this.idVrsteTelefonskogBroja = idVrsteTelefonskogBroja;
    }
}
