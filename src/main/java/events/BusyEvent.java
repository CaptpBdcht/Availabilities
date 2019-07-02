package events;

import interval.Interval;

import java.util.Date;

public class BusyEvent extends Event implements Opening {

    public BusyEvent(Date startDate, Date endDate) {
        this.interval = new Interval(startDate, endDate);
    }

    @Override
    public boolean isOpening() {
        return false;
    }
}
