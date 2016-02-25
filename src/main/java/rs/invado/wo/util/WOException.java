package rs.invado.wo.util;

import java.util.Date;

public class WOException extends Exception {
    private Integer errorCode;


    public WOException() {
        this.errorCode = null;
    }

    public WOException(int errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public WOException(int errorCode) {
        this.errorCode = errorCode;
    }


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }


    public String getFaultDetails() {
        Throwable t = getCause();
        String faultDetails = getMessage();
        while (t != null) {
            if (t.getCause() != null && t.getCause().getMessage() != null) {
                faultDetails += " " + t.getCause().getMessage();
            }
            t = t.getCause();
        }
        if (faultDetails == null) return null;
        return "[WebOrdering " + new Date() + "] " + faultDetails;
    }
}
