package rs.invado.wo.util;

import java.util.HashMap;
import java.util.Map;

public class WOExceptionCodes {
    private static WOExceptionCodes ourInstance = new WOExceptionCodes();
    private Map<Integer, String> errorCodesMap;

    public static final int WO_LOGIN_EXCEPTION           = 100;
    public static final int USER_READ_EXCEPTION          = 101;
    public static final int USER_UPDATE_EXCEPTION        = 102;
    public static final int USER_DELETE_EXCEPTION        = 103;
    public static final int WO_INSUFFICIENT_SKU_QUANTITY = 104;  // nedovoljna koliƒçina na zalihama
    public static final int WO_UNAUTHORIZED_USER         = 105;  // ne postoji seting za poslovni partner
    public static final int WO_UNEXESTING_USER           = 106;  // ne postoji korisnik sa takvom kombinacijom user/password





    public static WOExceptionCodes getInstance() {
        return ourInstance;
    }

    private WOExceptionCodes() {
        errorCodesMap = new HashMap<Integer, String>();
        errorCodesMap.put(WO_INSUFFICIENT_SKU_QUANTITY, "Nedovljna koliËina za rezervaciju u skaldiötu!");
        errorCodesMap.put(WO_UNAUTHORIZED_USER, "Niste ovlaöÊeni za poruËivanje proizvoda u odabranoj kompaniji!");
        errorCodesMap.put(WO_UNEXESTING_USER, "Greöka u Vaöoj kombinaciji Korisnik/Password, Molimo pokuöajte ponovo!");



    }

    public String getErrorMessage(int errorCode) {
        return errorCodesMap.get(errorCode);
    }



}
