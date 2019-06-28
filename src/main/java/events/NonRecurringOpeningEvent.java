package events;

import interval.Interval;

import java.util.Date;

public class NonRecurringOpeningEvent extends Event implements Recurring {

    public NonRecurringOpeningEvent(Date startDate, Date endDate) {
        this.interval = new Interval(startDate, endDate);
    }

    @Override
    public boolean isOpening() {
        return true;
    }

    @Override
    public boolean isRecurring() {
        return false;
    }
}
