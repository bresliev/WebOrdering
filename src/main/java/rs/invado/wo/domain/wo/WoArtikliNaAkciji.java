package rs.invado.wo.domain.wo;

import rs.invado.wo.domain.ocp.OcpProizvod;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name="WO_ARTIKLI_NA_AKCIJI", schema="DAREX")
@SequenceGenerator(   name = "etailerRaw_seq",
        sequenceName = "WO_ID_SEQ", allocationSize = 1)
public class WoArtikliNaAkciji {
	
	private BigDecimal id;
    private String tipAkcije;
	private Date datumOd;
	private Date datumDo;
    private OcpProizvod ocpProizvod;
    private int idKompanijeKorisnik;
    
	@Id
	@Column(name="ID", nullable=false, unique=true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "etailerRaw_seq")
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROIZVOD#", referencedColumnName = "PROIZVOD#", updatable = false, nullable = false)
	public OcpProizvod getOcpProizvod() {
		return ocpProizvod;
	}
	public void setOcpProizvod(OcpProizvod ocpProizvod) {
		this.ocpProizvod = ocpProizvod;
	}
	
	 @Column(name="DATUM_OD")
	public Date getDatumOd() {
		return datumOd;
	}
	public void setDatumOd(Date datumOd) {
		this.datumOd = datumOd;
	}
	
	 @Column(name="DATUM_DO")
	public Date getDatumDo() {
		return datumDo;
	}
	public void setDatumDo(Date datumDo) {
		this.datumDo = datumDo;
	}

    @Column(name="TIP_AKCIJE")
    public String getTipAkcije() {
        return tipAkcije;
    }

    public void setTipAkcije(String tipAkcije) {
        this.tipAkcije = tipAkcije;
    }

    @Column(name = "ID_KOMPANIJE_KORISNIK")
    public int getIdKompanijeKorisnik() {
        return idKompanijeKorisnik;
    }

    public void setIdKompanijeKorisnik(int idKompanijeKorisnik) {
        this.idKompanijeKorisnik = idKompanijeKorisnik;
    }
}
