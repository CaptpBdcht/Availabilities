
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

        Builder withRecurring(boolean recurring) {
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

    private boolean isOpening() {
        return opening;
    }

    private boolean isRecurring() {
        return recurring;
    }

    Interval getInterval() {
        return interval;
    }

    @Override
    public String toString() {
        String carriageReturn = "\n";
        String header = "---> Event <---";
        String isOpeningLine = isOpening() ? "-> opening" : "-> not opening";
        String isRecurringLine = isRecurring() ? "-> recurring" : "-> not recurring";
        String startLine = "-> start [" + getInterval().getStart().toString() + "]";
        String endLine = "-> end   [" + getInterval().getEnd().toString() + "]";

        return header + carriageReturn +
                isOpeningLine + carriageReturn +
                isRecurringLine + carriageReturn +
                startLine + carriageReturn +
                endLine + carriageReturn;
    }
}

class EventFactory {
    static Event RecurringOpeningEvent(Date startDate, Date endDate) {
        return new Event.Builder()
                .withOpening(true)
                .withRecurring(true)
                .withStartDate(startDate)
                .withEndDate(endDate)
                .build();
    }

    static Event NonRecurringOpeningEvent(Date startDate, Date endDate) {
        return new Event.Builder()
                .withOpening(true)
                .withRecurring(false)
                .withStartDate(startDate)
                .withEndDate(endDate)
                .build();
    }

    static Event BusyEvent(Date startDate, Date endDate) {
        return new Event.Builder()
                .withOpening(false)
                .withRecurring(false)
                .withStartDate(startDate)
                .withEndDate(endDate)
                .build();
    }
}
