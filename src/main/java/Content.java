import helpers.DateHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Content {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = DateHelper.getSimpleDateFormat();

        Event recurringOpeningEvent = EventFactory.RecurringOpeningEvent(
                format.parse("2016 7 1 10 30"), // July 1st, 10:30
                format.parse("2016 7 1 14 00")  // July 1st, 14:00
        );

        Event openingEvent = EventFactory.NonRecurringOpeningEvent(
                format.parse("2016 7 3 16 00"), // July 3rd, 16:00
                format.parse("2016 7 3 18 00")  // July 3rd, 18:00
        );

        Event interventionEvent = EventFactory.BusyEvent(
                format.parse("2016 7 8 11 30"), // July 8th, 11:30
                format.parse("2016 7 8 12 30")  // July 8th, 12:30
        );

        Date fromDate = format.parse("2016 7 4 10 00"); // July 4th 10:00
        Date toDate = format.parse("2016 7 10 10 00"); // July 10th 10:00
        Interval askedInterval = new Interval(fromDate, toDate);

        Schedule schedule = new Schedule();
        schedule.addEvent(recurringOpeningEvent);
        schedule.addEvent(openingEvent);
        schedule.addEvent(interventionEvent);
        List<Interval> answer = schedule.availabilitiesOn(askedInterval);

        /*
         * Answer should be :
         * I'm available from July 8th, at 10:30, 11:00, 12:30, 13:00, and 13:30
         * I'm not available any other time !
         */
    }
}
