import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Content {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("y M D H m");

        Event openingEvent = new Event.Builder()
                .withOpening(true)
                .withRecuring(true)
                .withStartDate(format.parse("2016 7 1 10 30"))
                .withEndDate(format.parse("2016 7 1 14 00"))
                .build();

        Event interventionEvent = new Event.Builder()
                .withOpening(false)
                .withRecuring(false)
                .withStartDate(format.parse("2016 7 8 11 30"))
                .withEndDate(format.parse("2016 7 8 12 30"))
                .build();

        Date fromDate = format.parse("2016 7 4 10 00"); // July 4th 10:00
        Date toDate = format.parse("2016 7 10 10 00"); // July 10th 10:00
        Interval askedInterval = new Interval(fromDate, toDate);
        String answer = Event.availabilitiesOn(askedInterval); // When are you available between these dates ?

        /*
         * Answer should be :
         * I'm available from July 8th, at 10:30, 11:00, 12:30, 13:00, and 13:30
         * I'm not available any other time !
         */
    }
}
