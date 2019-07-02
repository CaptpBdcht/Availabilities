package interval;

import helpers.DateHelper;

import java.util.*;

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

    static boolean doesIntersect(Interval first, Interval second) {
        return !DateHelper.after(second.getStart(), first.getEnd())
            && !DateHelper.after(first.getStart(), second.getEnd());
    }

    static Interval intersect(Interval first, Interval second) {
        Date firstStart = first.getStart();
        Date firstEnd = first.getEnd();
        Date secondStart = second.getStart();
        Date secondEnd = second.getEnd();

        if (DateHelper.beforeOrEquals(firstEnd, secondStart) ||
            DateHelper.beforeOrEquals(secondEnd, firstStart)) {
            return null;
        }

        Date start = DateHelper.max(firstStart, secondStart);
        Date end = DateHelper.min(firstEnd, secondEnd);
        return new Interval(start, end);
    }

    public static List<Interval> intersectList(List<Interval> left, List<Interval> right) {
        Date leftStart = left.get(0).getStart();
        Date leftEnd = left.get(left.size() - 1).getEnd();
        Date rightStart = right.get(right.size() - 1).getStart();
        Date rightEnd = right.get(right.size() - 1).getEnd();

        if (DateHelper.beforeOrEquals(leftEnd, rightStart)) {
            return Collections.emptyList();
        }
        if (DateHelper.beforeOrEquals(rightEnd, leftStart)) {
            return Collections.emptyList();
        }

        List<Interval> intersections = new ArrayList<>();
        for (int i = 0, j = 0; i < left.size() && j < right.size();) {
            Interval intersect = Interval.intersect(left.get(i), right.get(j));
            if (intersect != null) {
                intersections.add(intersect);
            }

            // right goes further
            if (DateHelper.before(left.get(i).getEnd(), right.get(j).getEnd())) {
                i += 1;
            }
            // left goes further
            else if (DateHelper.after(left.get(i).getEnd(), right.get(j).getEnd())) {
                j += 1;
            }
            // right ends at left
            else {
                i += 1;
                j += 1;
            }
        }
        return intersections;
    }

    static List<Interval> leftDisjunctiveUnion(Interval first, Interval second) {
        Interval intersection = Interval.intersect(first, second);
        // no intersect
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

    public static List<Interval> leftDisjunctiveUnionList(Interval interval, List<Interval> intervals) {
        Date beginOfIntervals = intervals.get(0).getStart();
        Date endOfIntervals = intervals.get(intervals.size() - 1).getEnd();

        if (DateHelper.before(interval.getEnd(), beginOfIntervals)) {
            return Collections.singletonList(interval);
        }
        if (DateHelper.before(endOfIntervals, interval.getStart())) {
            return Collections.singletonList(interval);
        }

        List<Interval> disjunctiveUnion = new ArrayList<>();
        if (DateHelper.before(interval.getStart(), beginOfIntervals)) {
            Interval remainingLeft = new Interval(interval.getStart(), beginOfIntervals);
            disjunctiveUnion.add(remainingLeft);
        }
        for (int i = 0; i < intervals.size() - 1; i++) {
            Interval betweenNext = new Interval(intervals.get(i).getEnd(), intervals.get(i + 1).getStart());
            Interval intersectionWithReference = Interval.intersect(interval, betweenNext);
            disjunctiveUnion.add(intersectionWithReference);
        }
        if (DateHelper.before(endOfIntervals, interval.getEnd())) {
            Interval remainingRight = new Interval(endOfIntervals, interval.getEnd());
            disjunctiveUnion.add(remainingRight);
        }
        return disjunctiveUnion;
    }
}

