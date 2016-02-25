package rs.invado.wo.domain.admin;

import rs.invado.wo.domain.ocp.OcpKlasifikacijaId;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikola
 * Date: 18.1.13.
 * Time: 22.57
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "AS_RADNIK_CINPOS", schema = "DARSISTEM")
public class AdminUserCinpos {


    private AdminUserCinposId id;
    private String pravoUnosa;
    private String pravoPregleda;
    @Transient
    public static final String OBJEKAT_ANALITIKE = "ROJ";

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "radnik", column = @Column(name = "RADNIK#", nullable = false, precision = 6, scale = 0)),
            @AttributeOverride(name = "objekatAnalitike", column = @Column(name = "OBJEKAT_ANALITIKE#", nullable = false, length = 15)),
            @AttributeOverride(name = "idCinpos", column = @Column(name = "ID_CINPOS#", nullable = false, length = 15)) })
    public AdminUserCinposId getId() {
        return this.id;
    }

    public void setId(AdminUserCinposId id) {
        this.id = id;
    }


    @Column(name = "PRAVO_UNOSA")
    public String getPravoUnosa() {
        return pravoUnosa;
    }

    public void setPravoUnosa(String pravoUnosa) {
        this.pravoUnosa = pravoUnosa;
    }

    @Column(name = "PRAVO_PREGLEDA")
    public String getPravoPregleda() {
        return pravoPregleda;
    }

    public void setPravoPregleda(String pravoPregleda) {
        this.pravoPregleda = pravoPregleda;
    }
}
