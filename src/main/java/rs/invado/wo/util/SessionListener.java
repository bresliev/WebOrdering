package rs.invado.wo.util;


import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import rs.invado.wo.service.BasketBusinessProcessing;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {


    public void sessionCreated(HttpSessionEvent se) {
    }

    public void sessionDestroyed(HttpSessionEvent se) {

        HttpSession session = se.getSession();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
        BasketBusinessProcessing basketService = (BasketBusinessProcessing) ctx.getBean("basketBusinessProcessing");
        basketService.releaseReservation(session.getId());


    }


}