package rs.invado.wo.domain.wo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Nikola on 07/08/2016.
 */
@Entity
@Table(name = "WO_CLASS_ORDER", schema = "DAREX")
public class WoClassOrder {

    private Integer id;
    private Integer woSortPerClassId;
    private Integer ocpVrstaKlasifikacije;
    private String ocpKlasifikacija;
    private Integer sort;
    private String ocpKlasifikacijaSuperior;


    @Id
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "WO_SORT_PER_CLASS_ID")
    public Integer getWoSortPerClassId() {
        return woSortPerClassId;
    }

    public void setWoSortPerClassId(Integer woSortPerClassId) {
        this.woSortPerClassId = woSortPerClassId;
    }

    @Column(name = "OCP_VRSTA_KLASIFIKACIJE#")
    public Integer getOcpVrstaKlasifikacije() {
        return ocpVrstaKlasifikacije;
    }

    public void setOcpVrstaKlasifikacije(Integer ocpVrstaKlasifikacije) {
        this.ocpVrstaKlasifikacije = ocpVrstaKlasifikacije;
    }

    @Column(name = "OCP_KLASIFIKACIJA#")
    public String getOcpKlasifikacija() {
        return ocpKlasifikacija;
    }

    public void setOcpKlasifikacija(String ocpKlasifikacija) {
        this.ocpKlasifikacija = ocpKlasifikacija;
    }

    @Column(name = "SORT")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Column(name = "OCP_KLASIFIKACIJA_SUPERIOR")
    public String getOcpKlasifikacijaSuperior() {
        return ocpKlasifikacijaSuperior;
    }

    public void setOcpKlasifikacijaSuperior(String ocpKlasifikacijaSuperior) {
        this.ocpKlasifikacijaSuperior = ocpKlasifikacijaSuperior;
    }
}
