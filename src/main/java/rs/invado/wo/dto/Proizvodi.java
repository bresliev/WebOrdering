package rs.invado.wo.dto;


import rs.invado.wo.domain.ocp.OcpProizvod;

import java.util.List;

public class Proizvodi {
    int rowCount;
    //vrednost razlièite od 0 za sort je sortiranje po klasifikaciji. vrednost jednaka 0 je sortiranje po cenovniku
    int sort;
    List<OcpProizvod> proizvodiList;

    public Proizvodi(List<OcpProizvod> proizvodiList, int sort, int rowCount) {
        this.proizvodiList=proizvodiList;
        this.rowCount=rowCount;
        this.sort = sort;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public List<OcpProizvod> getProizvodList() {
        return proizvodiList;
    }

    public void setProizvodList(List<OcpProizvod> proizvodList) {
        this.proizvodiList = proizvodList;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
