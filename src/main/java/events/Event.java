package events;

import interval.Interval;

public abstract class Event implements Opening {
    Interval interval;

    public Interval getInterval() {
        return interval;
    }
}

