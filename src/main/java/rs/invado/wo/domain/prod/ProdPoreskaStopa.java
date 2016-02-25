package rs.invado.wo.domain.prod;


import org.omg.CORBA.PRIVATE_MEMBER;
import rs.invado.wo.domain.ocp.OcpKlasifikacija;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikola
 * Date: 29.1.13.
 * Time: 22.21
 * To change this template use File | Settings | File Templates.
 */
@SqlResultSetMapping(name = "porez", entities = { @EntityResult(entityClass = rs.invado.wo.domain.prod.ProdPoreskaStopa.class,
        fields = {
        @FieldResult(name = "vrstaPoreza", column = "VRSTA_POREZA#"),
        @FieldResult(name = "datumOd", column = "DATUM_OD#"),
        @FieldResult(name = "datumDo", column = "DATUM_DO"),
        @FieldResult(name = "stopaPoreza", column = "STOPA_POREZA"),
        @FieldResult(name = "iznosPoreza", column = "IZNOS_POREZA")})})
@NamedNativeQueries({
        @NamedNativeQuery(name = "findPorezPerProizvod",
                query = " SELECT ps.vrsta_poreza#, ps.datum_od#, ps.datum_do, ps.stopa_poreza , ps.iznos_poreza" +
                "        FROM prod_proizvod_pk p, prod_poreska_stopa ps, prod_por_klasa_vrsta_por pkvp, prod_vrsta_poreza vp" +
                "        WHERE p.proizvod#         = :proizvod" +
                "        AND p.organizaciona_jedinica# = :oj" +
                "        AND pkvp.poreska_klasa# = p.poreska_klasa#" +
                "        AND pkvp.datum_do       IS NULL" +
                "        AND vp.vrsta_poreza#    = pkvp.vrsta_poreza#" +
                "        AND ps.vrsta_poreza#    = vp.vrsta_poreza#" +
                "        AND ps.datum_od# <= :datum " +
                "        and nvl(ps.datum_do, sysdate) >= trunc(:datum) ", resultSetMapping = "porez")})
@Entity
@Table(name = "PROD_PORESKA_STOPA", schema = "DAREX")
// dodao komentar...eto tako...
public class ProdPoreskaStopa {

    private int vrstaPoreza;
    private Date datumOd;
    private Date datumDo;
    private BigDecimal stopaPoreza;
    private BigDecimal iznosPoreza;

    @Id
    @Column(name = "VRSTA_POREZA#")
    public int getVrstaPoreza() {
        return vrstaPoreza;
    }

    public void setVrstaPoreza(int vrstaPoreza) {
        this.vrstaPoreza = vrstaPoreza;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATUM_OD#")
    public Date getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(Date datumOd) {
        this.datumOd = datumOd;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATUM_DO")
    public Date getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(Date datumDo) {
        this.datumDo = datumDo;
    }

    @Column(name = "STOPA_POREZA")
    public BigDecimal getStopaPoreza() {
        return stopaPoreza;
    }

    public void setStopaPoreza(BigDecimal stopaPoreza) {
        this.stopaPoreza = stopaPoreza;
    }

    @Column(name = "IZNOS_POREZA")
    public BigDecimal getIznosPoreza() {
        return iznosPoreza;
    }

    public void setIznosPoreza(BigDecimal iznosPoreza) {
        this.iznosPoreza = iznosPoreza;
    }
}
