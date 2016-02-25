package rs.invado.wo.mvc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rs.invado.wo.dto.User;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ReportingController {
    private static final Log log = LogFactory.getLog(ReportingController.class);
    //DataSource injected in the Reporting Service.
    @Autowired
    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    //Reporting mehtod in the controller
    @RequestMapping(value = "/kartica", method = RequestMethod.GET)
    public String printMyReport(HttpSession session, ModelMap modelMap) throws Exception {
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String datumDo=sdf.format(new Date());
            //Put the dataSource object against the  key as mentioned in jasperCfg.properties
            User user = (User) session.getAttribute("loginUser");
            log.info("datumDo=" + datumDo + " partner=" + user.getWoUser().getOcpPoslovniPartner().getPoslovniPartner());
            modelMap.put("dataSource", dataSource);
            log.info("set dataSource");
            //Add the type of report that needs to be generated. Right now it is hard coded
            //to pdf. But this can be easily fetched as a parameter from the request.
              modelMap.addAttribute("partner", user.getWoUser().getOcpPoslovniPartner().getPoslovniPartner() + "");
            modelMap.addAttribute("format", "pdf");
            modelMap.addAttribute("datumDo", datumDo);
            modelMap.addAttribute("oj",session.getAttribute("oj"));
            log.info("set partner");
        } catch (Exception e) {
            log.error(e.getMessage(), e);

        }

        //Return the view key as mentioned in jasperCfg.properties.
        return "kartica";
    }

    @RequestMapping(value = "/predracun", method = RequestMethod.POST)
    public String printPredracun(HttpSession session, ModelMap modelMap, String idDokumenta) throws Exception {
        try {
            log.info("idDokumenta="+idDokumenta);
            String idFinDok=idDokumenta.split("/")[0];
            String oj=idDokumenta.split("/")[1];
            String godina=idDokumenta.split("/")[2];
            String idVd=idDokumenta.split("/")[3];

            modelMap.addAttribute("format", "pdf");
            modelMap.put("idvd",idVd);
            modelMap.put("oj", oj);
            modelMap.put("godina", godina);
            modelMap.put("iddokumenta",idFinDok);
            modelMap.put("dataSource", dataSource);
            //Return the view key as mentioned in jasperCfg.properties.
            if ("30".equals(oj)) return "predracun30";
            return "predracun";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "predracun";
    }
}