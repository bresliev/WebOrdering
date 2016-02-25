package rs.invado.wo.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rs.invado.wo.domain.wo.WoUser;
import rs.invado.wo.dto.CompanySetting;
import rs.invado.wo.dto.User;
import rs.invado.wo.service.AppInitService;
import rs.invado.wo.service.LogOnService;
import rs.invado.wo.util.WOException;
import rs.invado.wo.util.WOExceptionCodes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    LogOnService logOnService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin() {
        return "loginView";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String postLogin(HttpSession session, HttpServletRequest req, String oj, String userName, String password) {
        if (!"40".equals(oj) && !"30".equals(oj) && !"19".equals(oj) && !"20".equals(oj)) {
            if (oj == null) req.setAttribute("errorMsg", "Obavezan izbor kompanije!");
            else req.setAttribute("errorMsg", "U izradi....");
            return "loginView";
        } else {
            ServletContext ctx = AppInitService.getServletConfig();
            CompanySetting companySetting = (CompanySetting) ctx.getAttribute(AppInitService.CompanySetting);
            try {
                User user = logOnService.logOn(userName, password, companySetting, Integer.parseInt(oj));
                if (user == null) {
                    req.setAttribute("errorMsg", "Neispravno korisnièko ime ili lozinka!");
                    return "loginView";
                } else {
                    session.setAttribute("loginUser", user);
                    session.setAttribute("perPage", 15);
                    session.setAttribute("oj", oj);
                    if (user.getWoUser().getUserType().equals(WoUser.USER_INTERNAL)) session.setMaxInactiveInterval(60*60*2);
                    else session.setMaxInactiveInterval(60*15);
                    return "redirect:/home";
                }

            } catch (WOException e) {
                req.setAttribute("errorMsg", WOExceptionCodes.getInstance().getErrorMessage(e.getErrorCode()));
                return "loginView";
            }

        }

    }



    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logOut(HttpSession session) {
        if (session != null) session.invalidate();
        return "redirect:login";
    }

    @RequestMapping(value = "changePartner", method = RequestMethod.POST)
    public String changePartner(HttpSession session, int partnerId) {
        ServletContext ctx = AppInitService.getServletConfig();
        CompanySetting companySetting = (CompanySetting) ctx.getAttribute(AppInitService.CompanySetting);
        User user = (User) session.getAttribute("loginUser");
        Integer oj= Integer.parseInt((String) session.getAttribute("oj"));
        logOnService.changePartner(partnerId,companySetting,oj,user);
        session.setAttribute("loginUser",user);
        return "redirect:/home";
    }




}
