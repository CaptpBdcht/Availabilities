import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Content {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("y M D H m");
        Date date = format.parse("2016 7 1 10 30"); // July 1st, 10:30
        Date endDate = format.parse("2016 7 1 14 00"); // July 1st, 14:00
        new Event(true, true, date, endDate); // weekly recurring opening in calendar

        date = format.parse("2016 7 8 11 30"); // July 8th 11:30
        endDate = format.parse("2016 7 8 12 30"); // July 8th 12:30
        new Event(false, false, date, endDate); // intervention scheduled

        Date fromDate = format.parse("2016 7 4 10 00"); // July 4th 10:00
        Date toDate = format.parse("2016 7 10 10 00"); // July 10th 10:00
        String answer = Event.availabilities(fromDate, toDate); // When are you available between these dates ?

        /*
         * Answer should be :
         * I'm available from July 8th, at 10:30, 11:00, 12:30, 13:00, and 13:30
         * I'm not available any other time !
         */
    }
}
