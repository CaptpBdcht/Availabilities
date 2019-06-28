
import java.util.Date;

class Event {
    private boolean opening;
    private boolean recurring;
    private Interval interval;

    static class Builder {
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

    private Event(boolean opening, boolean recurring, Interval interval) {
        this.opening = opening;
        this.recurring = recurring;
        this.interval = interval;
    }

    public boolean isOpening() {
        return opening;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public Interval getInterval() {
        return interval;
    }

    static String availabilitiesOn(Interval interval){
        return "";
    }
}

class EventFactory {
    static Event RecurringOpeningEvent(Date startDate, Date endDate) {
        return new Event.Builder()
                .withOpening(true)
                .withRecuring(true)
                .withStartDate(startDate)
                .withEndDate(endDate)
                .build();
    }

    static Event NonRecurringOpeningEvent(Date startDate, Date endDate) {
        return new Event.Builder()
                .withOpening(true)
                .withRecuring(false)
                .withStartDate(startDate)
                .withEndDate(endDate)
                .build();
    }

    static Event BusyEvent(Date startDate, Date endDate) {
        return new Event.Builder()
                .withOpening(false)
                .withRecuring(false)
                .withStartDate(startDate)
                .withEndDate(endDate)
                .build();
    }
}
