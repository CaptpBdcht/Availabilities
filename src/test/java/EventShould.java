import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertNotNull;

public class EventShould {

    @Test
    public void create() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("y M D H m");
        Date start = format.parse("2016 7 1 10 30"); // July 1st, 10:30
        Date end = format.parse("2016 7 1 14 00"); // July 1st, 14:00
        Interval interval = new Interval(start, end);
        Event event = new Event(false, false, interval);
        assertNotNull("Verify event is NOT null", event);
    }
}
