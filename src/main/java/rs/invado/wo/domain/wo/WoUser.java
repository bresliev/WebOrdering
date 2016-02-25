package rs.invado.wo.domain.wo;

import rs.invado.wo.domain.hr.KeRadnici;
import rs.invado.wo.domain.ocp.OcpPoslovniPartner;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="WO_USER", schema="DAREX")
@SequenceGenerator(   name = "woparametri_seq",
        sequenceName = "WO_ID_SEQ", allocationSize = 1)
public class WoUser {
    private BigDecimal id;
	private String userName;
	private String password;
    private Date datumRegistracije;
    private String email;
    private String telefonMobilni;
    private String telefon;
    private String nickname;
    private String userType;
    private String ime;
    private String prezime;
    private OcpPoslovniPartner ocpPoslovniPartner;
    private KeRadnici radnik;

    private List<WoRezervacija> woRezervacijas = new ArrayList<WoRezervacija>();
    private List<WoUserHasRight> woUserHasRights = new ArrayList<WoUserHasRight>();
    
    public final static String USER_INTERNAL = "INTERNI";
    public final static String USER_EXTERNAL = "EXTERNI";

    @Id
    @Column(name = "ID", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "woparametri_seq")
    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }


	@Column(name="USERNAME", nullable=false, unique=true)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name="PASSWORD", nullable=false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


    @Column(name = "DATUMREGISTRACIJE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDatumRegistracije() {
        return datumRegistracije;
    }

    public void setDatumRegistracije(Date datumRegistracije) {
        this.datumRegistracije = datumRegistracije;
    }

    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "TELEFONMOBILNI")
    public String getTelefonMobilni() {
        return telefonMobilni;
    }

    public void setTelefonMobilni(String telefonMobilni) {
        this.telefonMobilni = telefonMobilni;
    }

    @Column(name = "TELEFON")
    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @Column(name = "NICKNAME")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "POSLOVNI_PARTNER#", referencedColumnName = "POSLOVNI_PARTNER#", nullable = true, insertable = true, updatable = true)
    public OcpPoslovniPartner getOcpPoslovniPartner() {
        return ocpPoslovniPartner;
    }

    public void setOcpPoslovniPartner(OcpPoslovniPartner ocpPoslovniPartner) {
        this.ocpPoslovniPartner = ocpPoslovniPartner;
    }

    @OneToOne(targetEntity = KeRadnici.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "RADBR", referencedColumnName = "RADBR", nullable = true, insertable = true, updatable = true)
    public KeRadnici getRadnik() {
        return radnik;
    }

    public void setRadnik(KeRadnici radnik) {
        this.radnik = radnik;
    }

    @Column(name = "USER_TYPE")
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @OneToMany(targetEntity = WoUserHasRight.class, fetch = FetchType.LAZY, mappedBy = "woUser")
    public List<WoUserHasRight> getWoUserHasRights() {
        return woUserHasRights;
    }

    public void setWoUserHasRights(List<WoUserHasRight> woUserHasRights) {
        this.woUserHasRights = woUserHasRights;
    }

    @OneToMany(targetEntity = WoRezervacija.class, fetch = FetchType.LAZY, mappedBy = "woUser")
    public List<WoRezervacija> getWoRezervacijas() {
        return woRezervacijas;
    }

    public void setWoRezervacijas(List<WoRezervacija> woRezervacijas) {
        this.woRezervacijas = woRezervacijas;
    }

    @Column(name="IME")
    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    @Column(name="PREZIME")
    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
}
