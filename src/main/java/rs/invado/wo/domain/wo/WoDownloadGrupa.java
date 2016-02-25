package rs.invado.wo.domain.wo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nikola
 * Date: 3/18/13
 * Time: 9:59 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="WO_DOWNLOAD_GRUPA"
        ,schema="DAREX"
)
@SequenceGenerator(   name = "rezervacija_seq", sequenceName = "WO_SEQ_ID", allocationSize = 1)
public class WoDownloadGrupa {

    private Integer id;
    private String naziv;
    private String opis;
    private boolean prikazatiNaziv;
    private Integer sort;
    private List<WoDownloadFiles> woDownloadFiles = new ArrayList<WoDownloadFiles>();

    @Id
    @Column(name="ID", unique=true, nullable=false, precision=22, scale=0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rezervacija_seq")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name="NAZIV")
    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Column(name="OPIS")
    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    @Column(name = "PRIKAZATI_NAZIV")
    public boolean isPrikazatiNaziv() {
        return prikazatiNaziv;
    }

    public void setPrikazatiNaziv(boolean prikazatiNaziv) {
        this.prikazatiNaziv = prikazatiNaziv;
    }

    @Column(name="SORT")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @OneToMany(targetEntity = WoDownloadFiles.class, fetch = FetchType.LAZY, mappedBy = "woDownloadGrupa")
    public List<WoDownloadFiles> getWoDownloadFiles() {
        return woDownloadFiles;
    }

    public void setWoDownloadFiles(List<WoDownloadFiles> woDownloadFiles) {
        this.woDownloadFiles = woDownloadFiles;
    }
}
