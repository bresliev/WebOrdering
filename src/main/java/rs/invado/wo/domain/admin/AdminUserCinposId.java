package rs.invado.wo.domain.admin;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: nikola
 * Date: 18.1.13.
 * Time: 23.16
 * To change this template use File | Settings | File Templates.
 */
@Embeddable
public class AdminUserCinposId implements Serializable {

    private Integer radnik;
    private String objekatAnalitike;
    private String idCinpos;

    public AdminUserCinposId(){

    }

    public AdminUserCinposId(Integer radnik, String objekatAnalitike, String idCinpos){

        this.radnik =  radnik;
        this.objekatAnalitike = objekatAnalitike;
        this.idCinpos = idCinpos;
    }
    @Column(name = "RADNIK#", nullable = false)
    public Integer getRadnik() {
        return radnik;
    }

    public void setRadnik(Integer radnik) {
        this.radnik = radnik;
    }

    @Column(name = "OBJEKAT_ANALITIKE", nullable = false)
    public String getObjekatAnalitike() {
        return objekatAnalitike;
    }

    public void setObjekatAnalitike(String objekatAnalitike) {
        this.objekatAnalitike = objekatAnalitike;
    }

    @Column(name = "ID_CINPOS", nullable = false)
    public String getIdCinpos() {
        return idCinpos;
    }

    public void setIdCinpos(String idCinpos) {
        this.idCinpos = idCinpos;
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( (other == null ) ) return false;
        if ( !(other instanceof AdminUserCinposId) ) return false;
        AdminUserCinposId castOther = ( AdminUserCinposId ) other;

        return (this.getRadnik()==castOther.getRadnik())
                && ( (this.getObjekatAnalitike().equals(castOther.getObjekatAnalitike()) ||
                ( this.getObjekatAnalitike()!=null && castOther.getObjekatAnalitike()!=null &&
                        this.getObjekatAnalitike().equals(castOther.getObjekatAnalitike()) ) )
                && (this.getIdCinpos()==castOther.getIdCinpos() ||
        ( this.getIdCinpos()!=null && castOther.getIdCinpos()!=null &&
                this.getIdCinpos().equals(castOther.getIdCinpos()) ) ));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getRadnik();
        result = 37 * result + ( getIdCinpos() == null ? 0 : this.getIdCinpos().hashCode() );
        result = 37 * result + this.getObjekatAnalitike().length();
        return result;
    }
}
