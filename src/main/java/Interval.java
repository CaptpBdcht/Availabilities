import java.util.*;

class Interval {
    private Date start;
    private Date end;

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

    Date getEnd() {
        return end;
    }

    static boolean doesIntersect(Interval first, Interval second) {
        return !DateUtil.after(second.getStart(), first.getEnd())
            && !DateUtil.after(first.getStart(), second.getEnd());
    }

    static Interval intersection(Interval first, Interval second) {
        Date firstStart = first.getStart();
        Date firstEnd = first.getEnd();
        Date secondStart = second.getStart();
        Date secondEnd = second.getEnd();

        if (DateUtil.after(secondStart, firstEnd) || DateUtil.after(firstStart, secondEnd)) {
            return null;
        }

        Date start = DateUtil.max(firstStart, secondStart);
        Date end = DateUtil.min(firstEnd, secondEnd);
        return new Interval(start, end);
    }

    static List<Interval> disjunctiveUnion(Interval first, Interval second) {
        Interval intersection = Interval.intersection(first, second);
        // no intersection
        if (intersection == null) {
            return Collections.singletonList(first);
        }
        // intersects on right
        if (DateUtil.equals(intersection.getEnd(), first.getEnd())) {
            Interval newInterval = new Interval(first.getStart(), intersection.getStart());
            return Collections.singletonList(newInterval);
        }
        // intersects on left
        if (DateUtil.equals(intersection.getStart(), first.getStart())) {
            Interval newInterval = new Interval(intersection.getEnd(), first.getEnd());
            return Collections.singletonList(newInterval);
        }
        // intersects in middle
        Interval firstInterval = new Interval(first.getStart(), intersection.getStart());
        Interval secondInterval = new Interval(intersection.getEnd(), first.getEnd());
        return Arrays.asList(firstInterval, secondInterval);
    }
}

class DateUtil {
    static Date max(Date d1, Date d2) {
        if (d1.compareTo(d2) < 0) {
            return d2;
        }
        return d1;
    }

    static Date min(Date d1, Date d2) {
        if (d1.compareTo(d2) < 0) {
            return d1;
        }
        return d2;
    }

    static boolean before(Date d1, Date d2) {
        return d1.compareTo(d2) < 0;
    }

    static boolean after(Date d1, Date d2) {
        return d1.compareTo(d2) > 0;
    }

    static boolean equals(Date d1, Date d2) {
        return d1.compareTo(d2) == 0;
    }
}
