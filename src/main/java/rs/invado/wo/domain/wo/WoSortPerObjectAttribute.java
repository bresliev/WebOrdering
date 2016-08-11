package rs.invado.wo.domain.wo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Nikola on 07/08/2016.
 */
@Entity
@Table(name = "WO_SORT_PER_OBJECT_ATTRIBUTE")
public class WoSortPerObjectAttribute {

    private Integer id;
    private Integer woSortperClassId;
    private String attributeOwner;
    private String attributeName;
    private Integer attributeId;
    private Integer sort;
    private String orderType;

    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "WO_SORT_PER_CLASS_ID")
    public Integer getWoSortperClassId() {
        return woSortperClassId;
    }

    public void setWoSortperClassId(Integer woSortperClassId) {
        this.woSortperClassId = woSortperClassId;
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
}
