package rs.invado.wo.domain.uz;
// Generated Jan 1, 2013 5:03:16 PM by Hibernate Tools 3.4.0.CR1


import org.omg.CORBA.PRIVATE_MEMBER;
import rs.invado.wo.domain.wo.WoMapKompanijskaSkladista;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * UzSkladiste generated by hbm2java
 */
@Entity
@Table(name="UZ_SKLADISTE"
    ,schema="DAREX"
)
public class UzSkladiste  implements java.io.Serializable {


     private int idSkladista;
     private int idJediniceSkladistenja;
     private int idNameneSkladista;
     private boolean idTipaSkladista;
     private Short grupaProizvoda;
     private Integer organizacionaJedinicaJe;
     private Integer organizacionaJedinicaZa;
     private Integer poslovniPartner;
     private String nazivSkladista;
     private String medjufazno;
     private String rentiranje;
     private Integer idMesta;
     private String adresa;
     private String imaFiskalni;
     private String virtuelno;
     private Set uzStanjeZalihaSkladistas = new HashSet(0);
     private Set uzDokuments = new HashSet(0);
     private List<WoMapKompanijskaSkladista> woMapKompanijskaSkladistaRaspolozivo = new ArrayList<WoMapKompanijskaSkladista>(0);
     private List<WoMapKompanijskaSkladista> woMapKompanijskaSkladistaRezervacija = new ArrayList<WoMapKompanijskaSkladista>(0);

    public UzSkladiste() {
    }

	
    public UzSkladiste(short idSkladista, int idJediniceSkladistenja, int idNameneSkladista, boolean idTipaSkladista) {
        this.idSkladista = idSkladista;
        this.idJediniceSkladistenja = idJediniceSkladistenja;
        this.idNameneSkladista = idNameneSkladista;
        this.idTipaSkladista = idTipaSkladista;
    }
    public UzSkladiste(short idSkladista, int idJediniceSkladistenja, int idNameneSkladista, boolean idTipaSkladista, Short grupaProizvoda, Integer organizacionaJedinicaJe, Integer organizacionaJedinicaZa, Integer poslovniPartner, String nazivSkladista, String medjufazno, String rentiranje, Integer idMesta, String adresa, String imaFiskalni, String virtuelno, Set uzStanjeZalihaSkladistas, Set uzDokuments) {
       this.idSkladista = idSkladista;
       this.idJediniceSkladistenja = idJediniceSkladistenja;
       this.idNameneSkladista = idNameneSkladista;
       this.idTipaSkladista = idTipaSkladista;
       this.grupaProizvoda = grupaProizvoda;
       this.organizacionaJedinicaJe = organizacionaJedinicaJe;
       this.organizacionaJedinicaZa = organizacionaJedinicaZa;
       this.poslovniPartner = poslovniPartner;
       this.nazivSkladista = nazivSkladista;
       this.medjufazno = medjufazno;
       this.rentiranje = rentiranje;
       this.idMesta = idMesta;
       this.adresa = adresa;
       this.imaFiskalni = imaFiskalni;
       this.virtuelno = virtuelno;
       this.uzStanjeZalihaSkladistas = uzStanjeZalihaSkladistas;
       this.uzDokuments = uzDokuments;
    }
   
     @Id 

    
    @Column(name="ID_SKLADISTA", unique=true, nullable=false, precision=3, scale=0)
    public int getIdSkladista() {
        return this.idSkladista;
    }
    
    public void setIdSkladista(int idSkladista) {
        this.idSkladista = idSkladista;
    }

    
    @Column(name="ID_JEDINICE_SKLADISTENJA", nullable=false, precision=6, scale=0)
    public int getIdJediniceSkladistenja() {
        return this.idJediniceSkladistenja;
    }
    
    public void setIdJediniceSkladistenja(int idJediniceSkladistenja) {
        this.idJediniceSkladistenja = idJediniceSkladistenja;
    }

    
    @Column(name="ID_NAMENE_SKLADISTA", nullable=false, precision=2, scale=0)
    public int getIdNameneSkladista() {
        return this.idNameneSkladista;
    }
    
    public void setIdNameneSkladista(int idNameneSkladista) {
        this.idNameneSkladista = idNameneSkladista;
    }

    
    @Column(name="ID_TIPA_SKLADISTA", nullable=false, precision=1, scale=0)
    public boolean isIdTipaSkladista() {
        return this.idTipaSkladista;
    }
    
    public void setIdTipaSkladista(boolean idTipaSkladista) {
        this.idTipaSkladista = idTipaSkladista;
    }

    
    @Column(name="GRUPA_PROIZVODA#", precision=3, scale=0)
    public Short getGrupaProizvoda() {
        return this.grupaProizvoda;
    }
    
    public void setGrupaProizvoda(Short grupaProizvoda) {
        this.grupaProizvoda = grupaProizvoda;
    }

    
    @Column(name="ORGANIZACIONA_JEDINICA#_JE", precision=5, scale=0)
    public Integer getOrganizacionaJedinicaJe() {
        return this.organizacionaJedinicaJe;
    }
    
    public void setOrganizacionaJedinicaJe(Integer organizacionaJedinicaJe) {
        this.organizacionaJedinicaJe = organizacionaJedinicaJe;
    }

    
    @Column(name="ORGANIZACIONA_JEDINICA#_ZA", precision=5, scale=0)
    public Integer getOrganizacionaJedinicaZa() {
        return this.organizacionaJedinicaZa;
    }
    
    public void setOrganizacionaJedinicaZa(Integer organizacionaJedinicaZa) {
        this.organizacionaJedinicaZa = organizacionaJedinicaZa;
    }

    
    @Column(name="POSLOVNI_PARTNER#", precision=7, scale=0)
    public Integer getPoslovniPartner() {
        return this.poslovniPartner;
    }
    
    public void setPoslovniPartner(Integer poslovniPartner) {
        this.poslovniPartner = poslovniPartner;
    }

    
    @Column(name="NAZIV_SKLADISTA", length=30)
    public String getNazivSkladista() {
        return this.nazivSkladista;
    }
    
    public void setNazivSkladista(String nazivSkladista) {
        this.nazivSkladista = nazivSkladista;
    }

    
    @Column(name="MEDJUFAZNO", length=1)
    public String getMedjufazno() {
        return this.medjufazno;
    }
    
    public void setMedjufazno(String medjufazno) {
        this.medjufazno = medjufazno;
    }

    
    @Column(name="RENTIRANJE", length=2)
    public String getRentiranje() {
        return this.rentiranje;
    }
    
    public void setRentiranje(String rentiranje) {
        this.rentiranje = rentiranje;
    }

    
    @Column(name="ID_MESTA", precision=6, scale=0)
    public Integer getIdMesta() {
        return this.idMesta;
    }
    
    public void setIdMesta(Integer idMesta) {
        this.idMesta = idMesta;
    }

    
    @Column(name="ADRESA", length=100)
    public String getAdresa() {
        return this.adresa;
    }
    
    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    
    @Column(name="IMA_FISKALNI", length=2)
    public String getImaFiskalni() {
        return this.imaFiskalni;
    }
    
    public void setImaFiskalni(String imaFiskalni) {
        this.imaFiskalni = imaFiskalni;
    }

    
    @Column(name="VIRTUELNO", length=2)
    public String getVirtuelno() {
        return this.virtuelno;
    }
    
    public void setVirtuelno(String virtuelno) {
        this.virtuelno = virtuelno;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="uzSkladiste")
    public Set<UzStanjeZalihaSkladista> getUzStanjeZalihaSkladistas() {
        return this.uzStanjeZalihaSkladistas;
    }
    
    public void setUzStanjeZalihaSkladistas(Set uzStanjeZalihaSkladistas) {
        this.uzStanjeZalihaSkladistas = uzStanjeZalihaSkladistas;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="uzSkladiste")
    public Set<UzDokument> getUzDokuments() {
        return this.uzDokuments;
    }
    
    public void setUzDokuments(Set uzDokuments) {
        this.uzDokuments = uzDokuments;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="uzSkladisteRaspolozivo")
    public List<WoMapKompanijskaSkladista> getWoMapKompanijskaSkladistaRaspolozivo() {
        return woMapKompanijskaSkladistaRaspolozivo;
    }

    public void setWoMapKompanijskaSkladistaRaspolozivo(List<WoMapKompanijskaSkladista> woMapKompanijskaSkladistaRaspolozivo) {
        this.woMapKompanijskaSkladistaRaspolozivo = woMapKompanijskaSkladistaRaspolozivo;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="uzSkladisteRezervacija")
    public List<WoMapKompanijskaSkladista> getWoMapKompanijskaSkladistaRezervacija() {
        return woMapKompanijskaSkladistaRezervacija;
    }

    public void setWoMapKompanijskaSkladistaRezervacija(List<WoMapKompanijskaSkladista> woMapKompanijskaSkladistaRezervacija) {
        this.woMapKompanijskaSkladistaRezervacija = woMapKompanijskaSkladistaRezervacija;
    }
}

