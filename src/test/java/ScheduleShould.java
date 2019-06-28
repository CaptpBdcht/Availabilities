import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ScheduleShould {
    private final static SimpleDateFormat format = new SimpleDateFormat("y M D H m");
    private final String seventhJanuaryAtTenThirty = "2016 7 1 10 30"; // July 1st 10:30
    private final String seventhJanuaryAtFourteen = "2016 7 1 14 00";  // July 1st 14:00
    private final String seventhJanuaryAtFifteen = "2016 7 1 15 00";   // July 1st 15:00

    private Schedule schedule;

    private static Interval askedInterval;

    @BeforeClass
    public static void onlyOnce() throws ParseException {
        Date intervalStart = format.parse("2016 7 4 10 00"); // July 4th 10:00
        Date intervalEnd = format.parse("2016 7 10 10 00");  // July 10th 10:00
        askedInterval = new Interval(intervalStart, intervalEnd);
    }

    @Before
    public void beforeAll() {
        schedule = new Schedule();
    }

    @Test()
    public void add_new_events() throws ParseException {
        Event event = EventFactory.RecurringOpeningEvent(
                format.parse(seventhJanuaryAtTenThirty),
                format.parse(seventhJanuaryAtFourteen)
        );
        schedule.addEvent(event);
    }

    @Test()
    public void get_availabilities_when_free_on_a_simple_hour() throws ParseException {
        Event openingEvent = EventFactory.NonRecurringOpeningEvent(
                format.parse(seventhJanuaryAtFourteen),
                format.parse(seventhJanuaryAtFifteen)
        );
        schedule.addEvent(openingEvent);
        String availabilities = schedule.availabilitiesOn(askedInterval);
        assertEquals(availabilities, "");
    }
}
