package rs.invado.wo.domain.prod;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Nikola on 17/09/2016.
 */

@Entity
@Table(name = "PROD_MAX_RABATI", schema = "DAREX")
@NamedQueries({
        @NamedQuery(name = "findMaxRabatZaProizvod", query = "select m from ProdMaxRabati m where m.prodMaxRabatiId.organizacionaJedinica = :oj" +
                " and m.prodMaxRabatiId.vrstaKlasifikacije = :vk and :klasa like concat(m.prodMaxRabatiId.klasifikacija, '%')")
})
public class ProdMaxRabati {

    private ProdMaxRabatiId prodMaxRabatiId;
    private BigDecimal maxRabat;

    public ProdMaxRabati() {
    }

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "organizacionaJedinica#", column = @Column(name = "ORGANIZACIONA_JEDINICA#", nullable = false, precision = 5, scale = 0)),
            @AttributeOverride(name = "vrstaKlasifikacije", column = @Column(name = "VRSTA_KLASIFIKACIJE#", nullable = false, precision = 2, scale = 0)),
            @AttributeOverride(name = "klasifikacija", column = @Column(name = "KLASIFIKACIJA#", nullable = false, precision = 2, scale = 0))})
    public ProdMaxRabatiId getProdMaxRabatiId() {
        return prodMaxRabatiId;
    }

    public void setProdMaxRabatiId(ProdMaxRabatiId prodMaxRabatiId) {
        this.prodMaxRabatiId = prodMaxRabatiId;
    }

    @Column(name = "MAX_RABAT")
    public BigDecimal getMaxRabat() {
        return maxRabat;
    }

    public void setMaxRabat(BigDecimal maxRabat) {
        this.maxRabat = maxRabat;
    }
}
