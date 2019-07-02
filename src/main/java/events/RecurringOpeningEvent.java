package events;

import interval.Interval;

import java.util.Date;

public class RecurringOpeningEvent extends Event implements Recurring {

    public RecurringOpeningEvent(Date startDate, Date endDate) {
        this.interval = new Interval(startDate, endDate);
    }

    @Override
    public boolean isOpening() {
        return true;
    }

    @Override
    public boolean isRecurring() {
        return true;
    }
}
