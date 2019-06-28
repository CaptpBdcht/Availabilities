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
            throw new IllegalArgumentException("end cannot be before start");
        }
        if (end.compareTo(start) == 0) {
            throw new IllegalArgumentException("start cannot equal end");
        }

        // Immutable since Java 8, shouldn't copy to protect
        this.start = start;
        this.end = end;
    }

    Date getStart() {
        return start;
    }

    Date getEnd() { return end; }
}
