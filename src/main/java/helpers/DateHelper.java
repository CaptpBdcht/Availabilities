package helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

    private static final String DateFormatString = "y M D H m";

    public static SimpleDateFormat getSimpleDateFormat() {
        return new SimpleDateFormat(DateHelper.DateFormatString);
    }

    public static Date max(Date d1, Date d2) {
        if (d1.compareTo(d2) < 0) {
            return d2;
        }
        return d1;
    }

    public static Date min(Date d1, Date d2) {
        if (d1.compareTo(d2) < 0) {
            return d1;
        }
        return d2;
    }

    public static boolean before(Date d1, Date d2) {
        return d1.compareTo(d2) < 0;
    }

    public static boolean after(Date d1, Date d2) {
        return d1.compareTo(d2) > 0;
    }

    public static boolean equals(Date d1, Date d2) {
        return d1.compareTo(d2) == 0;
    }
}
