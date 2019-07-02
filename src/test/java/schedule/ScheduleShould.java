package schedule;

import events.BusyEvent;
import events.Event;
import events.NonRecurringOpeningEvent;
import events.RecurringOpeningEvent;
import interval.Interval;
import helpers.DateHelper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ScheduleShould {
    private static SimpleDateFormat format = DateHelper.getSimpleDateFormat();
    private static Interval askedInterval;

    private static final String seventhJanuaryAtTen = "2016 1 7 10 00";
    private static final String tenthMarchAtTen = "2016 3 10 10 00";


    private final Date firstFebruaryAtSix = format.parse("2016 2 1 6 00");
    private final Date firstFebruaryAtTwelve = format.parse("2016 2 1 12 00");
    private final Date seventhFebruaryAtTwo = format.parse("2016 2 7 2 00");
    private final Date seventhFebruaryAtSix = format.parse("2016 2 7 6 00");
    private final Date seventhFebruaryAtNine = format.parse("2016 2 7 9 00");
    private final Date seventhFebruaryAtTen = format.parse("2016 2 7 10 00");
    private final Date seventhFebruaryAtTwelve = format.parse("2016 2 7 12 00");
    private final Date seventhFebruaryAtFourteen = format.parse("2016 2 7 14 00");
    private final Date seventhFebruaryAtSixteen = format.parse("2016 2 7 16 00");
    private final Date seventhFebruaryAtEighteen = format.parse("2016 2 7 18 00");
    private final Date seventhFebruaryAtTwenty = format.parse("2016 2 7 20 00");
    private final Date seventhFebruaryAtTwentyTwo = format.parse("2016 2 7 22 00");

    private Schedule schedule;

    public ScheduleShould() throws ParseException {
    }

    @BeforeClass
    public static void onlyOnce() throws ParseException {
        Date intervalStart = format.parse(seventhJanuaryAtTen);
        Date intervalEnd = format.parse(tenthMarchAtTen);
        askedInterval = new Interval(intervalStart, intervalEnd);
    }

    @Before
    public void beforeAll() {
        schedule = new Schedule();
    }

    @Test()
    public void
    add_new_events() {
        Event event = new NonRecurringOpeningEvent(
            seventhFebruaryAtTen,
            seventhFebruaryAtFourteen
        );
        schedule.addEvent(event);
    }

    @Test()
    public void
    return_nothing_when_no_opening_event() {
        List<Interval> availabilities = schedule.availabilitiesOn(askedInterval);
        assertEquals(availabilities.size(), 0);
    }

    @Test()
    public void
    consider_non_recurring_opening_events() {
        Interval askedInterval = new Interval(
            seventhFebruaryAtTwo, seventhFebruaryAtTwenty
        );

        NonRecurringOpeningEvent morningOpening = new NonRecurringOpeningEvent(
            seventhFebruaryAtSix, seventhFebruaryAtTen
        );

        NonRecurringOpeningEvent afternoonOpening = new NonRecurringOpeningEvent(
            seventhFebruaryAtFourteen, seventhFebruaryAtSixteen
        );

        Schedule schedule = new Schedule();
        schedule.addEvent(morningOpening);
        schedule.addEvent(afternoonOpening);

        List<Interval> availabilities = schedule.availabilitiesOn(askedInterval);
        assertEquals(availabilities.size(), 2);
        assertEquals(availabilities.get(0).getStart(), seventhFebruaryAtSix);
        assertEquals(availabilities.get(0).getEnd(), seventhFebruaryAtTen);
        assertEquals(availabilities.get(1).getStart(), seventhFebruaryAtFourteen);
        assertEquals(availabilities.get(1).getEnd(), seventhFebruaryAtSixteen);
    }

    @Test()
    public void
    consider_also_busy_events() {
        Interval askedInterval = new Interval(
            seventhFebruaryAtTwo, seventhFebruaryAtTwenty
        );

        NonRecurringOpeningEvent morningOpening = new NonRecurringOpeningEvent(
            seventhFebruaryAtSix, seventhFebruaryAtTen
        );

        NonRecurringOpeningEvent afternoonOpening = new NonRecurringOpeningEvent(
            seventhFebruaryAtFourteen, seventhFebruaryAtEighteen
        );

        BusyEvent morningBusy = new BusyEvent(
            seventhFebruaryAtSix, seventhFebruaryAtNine
        );

        BusyEvent afternoonBusy = new BusyEvent(
            seventhFebruaryAtTwelve, seventhFebruaryAtSixteen
        );

        Schedule schedule = new Schedule();
        schedule.addEvent(morningOpening);
        schedule.addEvent(afternoonOpening);
        schedule.addEvent(morningBusy);
        schedule.addEvent(afternoonBusy);

        List<Interval> availabilities = schedule.availabilitiesOn(askedInterval);
        assertEquals(availabilities.size(), 2);
        assertEquals(availabilities.get(0).getStart(), seventhFebruaryAtNine);
        assertEquals(availabilities.get(0).getEnd(), seventhFebruaryAtTen);
        assertEquals(availabilities.get(1).getStart(), seventhFebruaryAtSixteen);
        assertEquals(availabilities.get(1).getEnd(), seventhFebruaryAtEighteen);
    }

    @Test()
    public void
    consider_also_recurring_opening_events() {
        Interval askedInterval = new Interval(
            seventhFebruaryAtTwo, seventhFebruaryAtTwenty
        );

        RecurringOpeningEvent openingEvent = new RecurringOpeningEvent(
            firstFebruaryAtSix, firstFebruaryAtTwelve
        );

        BusyEvent morningBusy = new BusyEvent(
            seventhFebruaryAtNine, seventhFebruaryAtTen
        );

        Schedule schedule = new Schedule();
        schedule.addEvent(openingEvent);
        schedule.addEvent(morningBusy);

        List<Interval> availabilities = schedule.availabilitiesOn(askedInterval);
        assertEquals(availabilities.size(), 2);
        assertEquals(availabilities.get(0).getStart(), seventhFebruaryAtSix);
        assertEquals(availabilities.get(0).getEnd(), seventhFebruaryAtNine);
        assertEquals(availabilities.get(1).getStart(), seventhFebruaryAtTen);
        assertEquals(availabilities.get(1).getEnd(), seventhFebruaryAtTwelve);
    }
}
