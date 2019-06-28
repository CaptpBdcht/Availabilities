import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {
    private boolean opening;
    private boolean recurring;

    private Interval interval;

    public static class Builder {
        private boolean opening;
        private boolean recurring;

        private Date startDate;
        private Date endDate;

        Builder withOpening(boolean opening) {
            this.opening = opening;
            return this;
        }

        Builder withRecuring(boolean recurring) {
            this.recurring = recurring;
            return this;
        }

        Builder withStartDate(Date start) {
            this.startDate = start;
            return this;
        }

        Builder withEndDate(Date end) {
            this.endDate = end;
            return this;
        }

        Event build() {
            Interval interval = new Interval(startDate, endDate);
            return new Event(opening, recurring, interval);
        }
    }

    Event(boolean opening, boolean recurring, Interval interval) {
        this.opening = opening;
        this.recurring = recurring;
        this.interval = interval;
    }

    static String availabilitiesOn(Interval interval){
        return "";
    }
}
