import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IntervalShould {
    private final SimpleDateFormat format = new SimpleDateFormat("y M D H m");
    private final String seventhJanuaryAtTenThirty = "2016 7 1 10 30";
    private final String seventhJanuaryAtFourteen = "2016 7 1 14 00";

    private Date startDate = null;
    private Date endDate = null;

    @Test(expected = IllegalArgumentException.class)
    public void not_create_if_start_is_null() throws ParseException {
        endDate = format.parse(seventhJanuaryAtFourteen);
        Interval it = new Interval(null, endDate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void not_create_if_end_is_null() throws ParseException {
        startDate = format.parse(seventhJanuaryAtTenThirty);
        Interval it = new Interval(startDate, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void not_create_if_start_after_end() throws ParseException {
        startDate = format.parse(seventhJanuaryAtFourteen);
        endDate = format.parse(seventhJanuaryAtTenThirty);
        Interval it = new Interval(startDate, endDate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void not_create_if_start_equals_end() throws ParseException {
        startDate = format.parse(seventhJanuaryAtTenThirty);
        endDate = format.parse(seventhJanuaryAtTenThirty);
        Interval it = new Interval(startDate, endDate);
    }

    @Test()
    public void create_when_given_valid_dates() throws ParseException {
        startDate = format.parse(seventhJanuaryAtTenThirty);
        endDate = format.parse(seventhJanuaryAtFourteen);
        Interval it = new Interval(startDate, endDate);
    }
}
