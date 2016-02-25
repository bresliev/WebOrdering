package rs.invado.wo.util;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SessionTimeoutFilter implements Filter {


    private static final String TIMEOUT_PAGE = "login";
    private int customSessionExpiredErrorCode = 901;

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
            ServletException {
        if ((request instanceof HttpServletRequest) && (response instanceof HttpServletResponse)) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            //HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            String timeoutUrl = httpServletRequest.getContextPath() + "/" + TIMEOUT_PAGE;
            String ajaxHeader = httpServletRequest.getHeader("X-Requested-With");
            //System.out.println(((HttpServletRequest) request).getRequestURL());

            // is session expire control required for this request?
            if (isSessionControlRequiredForThisResource(httpServletRequest)) {
                if (isSessionInvalid(httpServletRequest)) {
                    //System.out.println("session is invalid! redirecting to timeoutpage : " + timeoutUrl);
                    if ("XMLHttpRequest".equals(ajaxHeader)) {
                        //System.out.println("Ajax call detected, send {} error code " + customSessionExpiredErrorCode);
                        HttpServletResponse resp = (HttpServletResponse) response;
                        resp.sendError(customSessionExpiredErrorCode);
                        return;
                    } else {
                        request.setAttribute("errorMsg","Vaša sesija je istekla. Ukoliko ste imali nepotvrðene artikle u korpi sve stavke æe biti poništene.");
                        request.getRequestDispatcher(TIMEOUT_PAGE).forward(request, response);
                        //httpServletResponse.sendRedirect(timeoutUrl);
                        return;
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * session shouldn't be checked for some pages. For example: for timeout page..
     * Since we're redirecting to timeout page from this filter,
     * if we don't disable session control for it, filter will again redirect to it
     * and this will be result with an infinite loop...
     */
    private boolean isSessionControlRequiredForThisResource(HttpServletRequest httpServletRequest) {
        String requestPath = httpServletRequest.getRequestURI();
        if (requestPath.contains("login") || requestPath.contains("predracun") || requestPath.endsWith(".pdf") || requestPath.endsWith(".css") || requestPath.endsWith(".png")) return false;
        boolean controlRequired = !requestPath.contains(TIMEOUT_PAGE);
        return controlRequired;
    }

    private boolean isSessionInvalid(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute("loginUser") == null) return true;
        boolean sessionInValid = (httpServletRequest.getRequestedSessionId() != null) && !httpServletRequest.isRequestedSessionIdValid();
        return sessionInValid;
    }

    public void destroy() {
    }


}
