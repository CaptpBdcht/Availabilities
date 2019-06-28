import helpers.DateHelper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ScheduleShould {
    private static SimpleDateFormat format = DateHelper.getSimpleDateFormat();
    private static Interval askedInterval;

    private static final String seventhJanuaryAtTen = "2016 7 1 10 00";
    private static final String seventhMarchAtTen = "2016 7 3 10 00";

    // private final Date seventhFebruaryAtSix = format.parse("2016 7 2 6 00");
    private final Date seventhFebruaryAtTen = format.parse("2016 7 2 10 00");
    // private final Date seventhFebruaryAtTwelve = format.parse("2016 7 2 12 00");
    private final Date seventhFebruaryAtFourteen = format.parse("2016 7 2 14 00");
    // private final Date seventhFebruaryAtSeventeen = format.parse("2016 7 2 17 00");

    private Schedule schedule;

    public ScheduleShould() throws ParseException {
    }

    @BeforeClass
    public static void onlyOnce() throws ParseException {
        Date intervalStart = format.parse(seventhJanuaryAtTen);
        Date intervalEnd = format.parse(seventhMarchAtTen);
        askedInterval = new Interval(intervalStart, intervalEnd);
    }

    @Before
    public void beforeAll() {
        schedule = new Schedule();
    }

    @Test()
    public void add_new_events() {
        Event event = EventFactory.RecurringOpeningEvent(
            seventhFebruaryAtTen,
            seventhFebruaryAtFourteen
        );
        schedule.addEvent(event);
    }

    @Test()
    public void get_availabilities_when_free_on_one_interval() {
        Event openingEvent = EventFactory.NonRecurringOpeningEvent(
            seventhFebruaryAtTen,
            seventhFebruaryAtFourteen
        );
        schedule.addEvent(openingEvent);
        List<Interval> availabilities = schedule.availabilitiesOn(askedInterval);
        assertEquals(availabilities, Collections.emptyList());
    }
}
