package rs.invado.wo.domain.wo;

import javax.persistence.*;

/**
 * Created by Nikola on 07/08/2016.
 */
@Entity
@Table(name = "WO_SORT_PER_OBJECT_ATTRIBUTE")
@NamedNativeQueries({
        @NamedNativeQuery(name = "findWoAttributeSortByClass", query = " select distinct a.* "
                + "           from wo_sort_per_object_attribute a  "
                + "           where wo_sort_per_class_id in (select id from wo_sort_per_class"
                + "                                           where wo_vrsta_klasifikacije# = :vrstaKlasifikacije"
                + "                                           and wo_klasifikacija# = :brand)"
                + "               order by sort ", resultClass = WoSortPerObjectAttribute.class)})


public class WoSortPerObjectAttribute {

    private Integer id;
    private Integer woSortPerClassId;
    private String attributeOwner;
    private String attributeName;
    private Integer attributeId;
    private Integer sort;
    private String orderType;
    private Integer woClassOrderId;

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

    @Column(name = "ATTRIBUTE_OWNER")
    public String getAttributeOwner() {
        return attributeOwner;
    }

    public void setAttributeOwner(String attributeOwner) {
        this.attributeOwner = attributeOwner;
    }

    @Column(name = "ATTRIBUTE_NAME")
    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    @Column(name = "ATTRIBUTE_ID")
    public Integer getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    @Column(name = "SORT")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Column(name = "ORDER_TYPE")
    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Column(name = "WO_CLASS_ORDER_ID")
    public Integer getWoClassOrderId() {
        return woClassOrderId;
    }

    public void setWoClassOrderId(Integer woClassOrderId) {
        this.woClassOrderId = woClassOrderId;
    }
}
