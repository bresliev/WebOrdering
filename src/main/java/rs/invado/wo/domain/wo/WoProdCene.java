package rs.invado.wo.domain.wo;

import rs.invado.wo.domain.ocp.OcpProizvod;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Nikola on 14/06/2016.
 */
@Entity
@Table(name = "WOPRODCENE", schema = "DAREX")
public class WoProdCene {


    private WoProdCeneId id;
    private OcpProizvod ocpProizvod;
    private BigDecimal cena;
    private Date datumDo;
    private Date datumOv;

    @EmbeddedId
    @AttributeOverrides( {
            @AttributeOverride(name="klasaCene", column=@Column(name="KLASACENE", nullable=false)),
            @AttributeOverride(name="organizacionaJedinica", column=@Column(name="ORGANIZACIONA_JEDINICA#", nullable=false, precision=5, scale=0) ),
            @AttributeOverride(name="idCenovnik", column=@Column(name="ID_CENOVNIK", nullable=false, precision=5, scale=0) ),
            @AttributeOverride(name="idKlasaCene", column=@Column(name="ID_KLASA_CENE", nullable=false, precision=2, scale=0) ) } )
    public WoProdCeneId getId() {
        return id;
    }

    public void setId(WoProdCeneId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROIZVOD#", referencedColumnName = "PROIZVOD#", nullable = false, insertable = false, updatable = false)
    public OcpProizvod getOcpProizvod() {
        return ocpProizvod;
    }

    public void setOcpProizvod(OcpProizvod ocpProizvod) {
        this.ocpProizvod = ocpProizvod;
    }

    @Column(name = "CENA")
    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }

    @Column(name = "DATUM_DO")
    public Date getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(Date datumDo) {
        this.datumDo = datumDo;
    }

    @Column(name = "DATUM_OV")
    public Date getDatumOv() {
        return datumOv;
    }

    public void setDatumOv(Date datumOv) {
        this.datumOv = datumOv;
    }
}
