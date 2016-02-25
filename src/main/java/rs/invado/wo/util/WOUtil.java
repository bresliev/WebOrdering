package rs.invado.wo.util;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Djordje
 * Date: 3/28/13
 * Time: 11:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class WOUtil {
    public static BigDecimal trimToZero(BigDecimal value) {
        if (value == null) return new BigDecimal(0);
        return value;
    }
}
