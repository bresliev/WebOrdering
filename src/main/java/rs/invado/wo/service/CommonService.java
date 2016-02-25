package rs.invado.wo.service;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.ByteArrayDataSource;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.invado.wo.dao.ocp.OcpKlasifikacijaHome;
import rs.invado.wo.dao.ocp.OcpPoslovniPartnerHome;
import rs.invado.wo.dao.wo.WoDownloadFilesHome;
import rs.invado.wo.dao.wo.WoKompanijaKorisnikHome;
import rs.invado.wo.domain.ocp.OcpKlasifikacija;
import rs.invado.wo.domain.ocp.OcpPoslovniPartner;
import rs.invado.wo.domain.wo.WoDownloadFiles;
import rs.invado.wo.domain.wo.WoKompanijaKorisnik;
import rs.invado.wo.domain.wo.WoParametri;
import rs.invado.wo.dto.CompanySetting;
import rs.invado.wo.dto.User;

import javax.mail.internet.InternetAddress;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class CommonService {
    private static final Log log = LogFactory.getLog(CommonService.class);
    @Autowired
    private OcpPoslovniPartnerHome ocpPoslovniPartnerHome;
    @Autowired
    WoDownloadFilesHome woDownloadFilesDAO;
    @Autowired
    WoKompanijaKorisnikHome woKompanijaKorisnikHome;
    @Autowired
    OcpKlasifikacijaHome ocpKlasifikacijaDAO;

    public List<OcpPoslovniPartner> findPartnerByName(String namePattern) {
        List<OcpPoslovniPartner> partners=ocpPoslovniPartnerHome.findPartnerByName(namePattern);

        return partners;
    }

    public List<WoDownloadFiles> getVestiAktuelno(int oj) {
        return woDownloadFilesDAO.findAktuelnoByCompany(oj);
    }

    public WoKompanijaKorisnik getFlashSize(int oj) {
        return woKompanijaKorisnikHome.findByCoresponingOJ(oj);
    }

    public void sendEmailConfirmation(String reportUrl, WoParametri woParametri, User user, List<String> fakture) {
        try {
            List<String> emails=new ArrayList<String>();
            if (user.getWoPartnerSetting() != null && user.getWoPartnerSetting().get(0) != null && user.getWoPartnerSetting().get(0).getReceiveConfirm() == 1 && user.getWoPartnerSetting().get(0).getEmailAddress() != null) {
                emails.add(user.getWoPartnerSetting().get(0).getEmailAddress());
            }
            if (user.getWoUserHasRights()!=null && user.getWoUserHasRights().get("EMP")!= null && user.getWoUserHasRights().get("EMP") && user.getWoUser()!=null && user.getWoUser().getEmail() != null) {
                if (emails.size() == 0 || (emails.size()>0 && !emails.get(0).equals(user.getWoUser().getEmail()))) emails.add(user.getWoUser().getEmail());
            }

            if ("INTERNI".equals(user.getWoUser().getUserType()) && user.getWoUser().getEmail() != null ) {
                emails.add(user.getWoUser().getEmail());
            }

            if (emails.size() > 0) {
                String emailContent = woParametri.getConfirmMailContent().substring(1, Integer.valueOf(woParametri.getConfirmMailContent().length() + ""));
                log.info("Prametri su " + woParametri.getMailServerPort() + " " + woParametri.getMailaddress() + " " + woParametri.getMailserver() + " " + woParametri.getPassword());
                HtmlEmail email = new HtmlEmail();
                email.setHostName(woParametri.getMailserver());
                email.setSmtpPort(woParametri.getMailServerPort());
                email.setCharset("Windows-1250");
                //email.setDebug(true);
                if (woParametri.getWomailaddress() != null && woParametri.getPassword() != null) {
                    email.setStartTLSRequired(true);      // obavezno u slucaju gmail-a
                    email.setAuthenticator(new DefaultAuthenticator(woParametri.getWomailaddress(), woParametri.getPassword()));
                }
                String [] sendTo=new String[emails.size()];
                for (int i=0;i<emails.size();i++) {
                    sendTo[i]=emails.get(i);
                }
                email.addTo(sendTo);
                email.setFrom(woParametri.getMailaddress(), "WebOrdering");
                email.setSubject("Potvrda porudžbine...");


                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(reportUrl);
                for (String idDokumenta : fakture) {
                    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                    parameters.add(new BasicNameValuePair("idDokumenta", idDokumenta));
                    UrlEncodedFormEntity sendentity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);
                    post.setEntity(sendentity);
                    HttpResponse postResponse = client.execute(post);
                    email.attach(new ByteArrayDataSource(postResponse.getEntity().getContent(), "application/pdf"), idDokumenta + ".pdf", "Document description", EmailAttachment.ATTACHMENT);
                }
                // embed the image and get the content id
                //String cid = email.embed("http://www.apache.org/images/asf_logo_wide.gif", "Apache logo");
                //set the html message
                //email.setHtmlMsg("<html> Bla bla truc truc <br><br><br> <img src=\"cid:" + cid + "\"></html>");
                //email.setHtmlMsg("<html><font face='verdana' color='#e26f16'><b>" + emailContent + "</b></font></html>");
                email.setHtmlMsg(emailContent);
                email.send();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    public List<OcpKlasifikacija> findKlasifikacijaWithAction(CompanySetting cs, String tipAkcije, int oj,User user){
        return ocpKlasifikacijaDAO.findKlasifikacijaWithAction(tipAkcije, cs, oj, user);
    }
}