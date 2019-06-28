package events;

import interval.Interval;

import java.util.Date;

class BusyEvent extends Event implements Opening {

    BusyEvent(Date startDate, Date endDate) {
        this.interval = new Interval(startDate, endDate);
    }

    @Override
    public boolean isOpening() {
        return false;
    }
}
