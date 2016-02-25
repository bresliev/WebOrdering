package rs.invado.wo.dto;

import rs.invado.wo.domain.ocp.OcpKlasifikacija;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikola
 * Date: 2.1.13.
 * Time: 20.18
 * To change this template use File | Settings | File Templates.
 */
public class Meni {
    
    private Map<Integer, List<OcpKlasifikacija>> meni = new HashMap<Integer, List<OcpKlasifikacija>>(0);

    public Map<Integer, List<OcpKlasifikacija>> getMeni() {
        return meni;
    }

    public void setMeni(Map<Integer, List<OcpKlasifikacija>> meni) {
        this.meni = meni;
    }
}
