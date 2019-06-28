import interval.Interval;
import events.NonRecurringOpeningEvent;
import schedule.Schedule;
import helpers.DateHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Content {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = DateHelper.getSimpleDateFormat();

        final Date seventhFebruaryAtSix = format.parse("2016 7 2 6 00");
        final Date seventhFebruaryAtSeventeen = format.parse("2016 7 2 17 00");

        NonRecurringOpeningEvent nonRecurringOpening = new NonRecurringOpeningEvent(
            seventhFebruaryAtSix,
            seventhFebruaryAtSeventeen
        );

        final Date firstJanuaryAtTen = format.parse("2016 1 1 10 00");
        final Date firstMarchAtTen = format.parse("2016 1 3 10 00");
        Interval requestedInterval = new Interval(firstJanuaryAtTen, firstMarchAtTen);

        Schedule schedule = new Schedule();
        schedule.addEvent(nonRecurringOpening);

        List<Interval> availabilities = schedule.availabilitiesOn(requestedInterval);
    }
}
