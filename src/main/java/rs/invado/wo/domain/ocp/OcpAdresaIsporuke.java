package rs.invado.wo.domain.ocp;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Nikola on 17/09/2017.
 */
@Entity
@Table(name = "OCP_ADRESA_ISPORUKE"
        , schema = "DAREX"
)
public class OcpAdresaIsporuke {


    private int id;
    private OcpPoslovniPartner poslovniPartner;
    private String adresa;
    private int prioritet;
    private String aktivna;
    private String primalac;
    private int idMesta;
    private String nazivMesta;

    public OcpAdresaIsporuke() {
            }

    public OcpAdresaIsporuke(int id, OcpPoslovniPartner ocpPoslovniPartner, String adresa, int prioritet, String aktivna, String primalac, int idMesta, String nazivMesta) {
        this.id = id;
        this.poslovniPartner = ocpPoslovniPartner;
        this.adresa = adresa;
        this.prioritet = prioritet;
        this.aktivna = aktivna;
        this.primalac = primalac;
        this.idMesta = idMesta;
        this.nazivMesta = nazivMesta;
    }

    @Id
    @Column(name = "ID", length = 60)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "POSLOVNI_PARTNER#", referencedColumnName = "POSLOVNI_PARTNER#", insertable = true, updatable = true)
    public OcpPoslovniPartner getPoslovniPartner() {
        return poslovniPartner;
    }

    public void setPoslovniPartner(OcpPoslovniPartner poslovniPartner) {
        this.poslovniPartner = poslovniPartner;
    }

    @Column(name = "ADRESA", length = 60)
    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    @Column(name = "PRIORITET", length = 60)
    public int getPrioritet() {
        return prioritet;
    }

    public void setPrioritet(int prioritet) {
        this.prioritet = prioritet;
    }

    @Column(name = "AKTIVNA", length = 60)
    public String getAktivna() {
        return aktivna;
    }

    public void setAktivna(String aktivna) {
        this.aktivna = aktivna;
    }

    @Column(name = "PRIMALAC", length = 60)
    public String getPrimalac() {
        return primalac;
    }

    public void setPrimalac(String primalac) {
        this.primalac = primalac;
    }

    @Column(name = "ID_MESTA", length = 60)
    public int getIdMesta() {
        return idMesta;
    }

    public void setIdMesta(int idMesta) {
        this.idMesta = idMesta;
    }

    @Column(name = "NAZIV_MESTA", length = 60)
    public String getNazivMesta() {
        return nazivMesta;
    }

    public void setNazivMesta(String nazivMesta) {
        this.nazivMesta = nazivMesta;
    }
}
