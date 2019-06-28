import exceptions.DatesDescendingException;

import java.util.Date;

class Interval {
    private final Date start;
    private final Date end;

    Interval(Date start, Date end) {
        if (start == null) {
            throw new IllegalArgumentException("start cannot be null");
        }
        if (end == null) {
            throw new IllegalArgumentException("end cannot be null");
        }
        if (end.compareTo(start) < 0) {
            throw new DatesDescendingException("end cannot be before start");
        }
        if (end.compareTo(start) == 0) {
            throw new IllegalArgumentException("start cannot equal end");
        }

        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }
}
