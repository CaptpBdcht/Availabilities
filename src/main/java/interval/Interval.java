package interval;

import helpers.DateHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Interval {
    private Date start;
    private Date end;

    public Interval(Date start, Date end) {
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

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public static boolean doesIntersect(Interval first, Interval second) {
        return !DateHelper.after(second.getStart(), first.getEnd())
            && !DateHelper.after(first.getStart(), second.getEnd());
    }

    public static Interval intersection(Interval first, Interval second) {
        Date firstStart = first.getStart();
        Date firstEnd = first.getEnd();
        Date secondStart = second.getStart();
        Date secondEnd = second.getEnd();

        if (DateHelper.after(secondStart, firstEnd) || DateHelper.after(firstStart, secondEnd)) {
            return null;
        }

        Date start = DateHelper.max(firstStart, secondStart);
        Date end = DateHelper.min(firstEnd, secondEnd);
        return new Interval(start, end);
    }

    public static List<Interval> leftDisjunctiveUnion(Interval first, Interval second) {
        Interval intersection = Interval.intersection(first, second);
        // no intersection
        if (intersection == null) {
            return Collections.singletonList(first);
        }
        // intersects inside left
        if (DateHelper.equals(intersection.getStart(), first.getStart())) {
            Interval newInterval = new Interval(intersection.getEnd(), first.getEnd());
            return Collections.singletonList(newInterval);
        }
        // intersects inside right
        if (DateHelper.equals(intersection.getEnd(), first.getEnd())) {
            Interval newInterval = new Interval(first.getStart(), intersection.getStart());
            return Collections.singletonList(newInterval);
        }
        // intersects in middle
        Interval firstInterval = new Interval(first.getStart(), intersection.getStart());
        Interval secondInterval = new Interval(intersection.getEnd(), first.getEnd());
        return Arrays.asList(firstInterval, secondInterval);
    }
}

