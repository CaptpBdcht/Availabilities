import helpers.DateHelper;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class IntervalShould {
    private final SimpleDateFormat format = DateHelper.getSimpleDateFormat();
    private final Date seventhFebruaryAtSix = format.parse("2016 7 2 6 00");
    private final Date seventhFebruaryAtTen = format.parse("2016 7 2 10 00");
    private final Date seventhFebruaryAtTwelve = format.parse("2016 7 2 12 00");
    private final Date seventhFebruaryAtFourteen = format.parse("2016 7 2 14 00");
    private final Date seventhFebruaryAtSeventeen = format.parse("2016 7 2 17 00");

    public IntervalShould() throws ParseException {
    }

    @Test(expected = IllegalArgumentException.class) public void
    not_create_if_start_is_null() {
        new Interval(null, seventhFebruaryAtSix);
    }

    @Test(expected = IllegalArgumentException.class) public void
    not_create_if_end_is_null() {
        new Interval(seventhFebruaryAtSix, null);
    }

    @Test(expected = IllegalArgumentException.class) public void
    not_create_if_start_after_end() {
        new Interval(
            seventhFebruaryAtSeventeen,
            seventhFebruaryAtSix
        );
    }

    @Test(expected = IllegalArgumentException.class) public void
    not_create_if_start_equals_end() {
        new Interval(
            seventhFebruaryAtFourteen,
            seventhFebruaryAtFourteen
        );
    }

    @Test() public void
    create_when_given_valid_dates() {
        new Interval(
            seventhFebruaryAtSix,
            seventhFebruaryAtTwelve
        );
    }

    @Test() public void
    return_false_for_disjoint_intervals() {
        Interval first = new Interval(
            seventhFebruaryAtSix,
            seventhFebruaryAtTen
        );
        Interval second = new Interval(
            seventhFebruaryAtFourteen,
            seventhFebruaryAtSeventeen
        );
        assertFalse(Interval.doesIntersect(first, second));
    }

    @Test() public void
    return_true_for_intersecting_intervals() {
        Interval interval = new Interval(
            seventhFebruaryAtTen,
            seventhFebruaryAtTwelve
        );
        assertTrue(Interval.doesIntersect(interval, interval));
    }

    @Test() public void
    return_intersection_of_disjoint_intervals() {
        Interval first = new Interval(
            seventhFebruaryAtSix,
            seventhFebruaryAtTen
        );
        Interval second = new Interval(
            seventhFebruaryAtFourteen,
            seventhFebruaryAtSeventeen
        );
        assertNull(Interval.intersection(first, second));
    }

    @Test() public void
    return_intersection_of_same_interval() {
        Interval first = new Interval(
            seventhFebruaryAtTen,
            seventhFebruaryAtTwelve
        );
        Interval intersection = Interval.intersection(first, first);
        if (intersection != null) {
            assertEquals(intersection.getStart(), first.getStart());
            assertEquals(intersection.getEnd(), first.getEnd());
        }
    }

    @Test() public void
    return_intersection_of_intervals_intersecting_on_left() {
        Interval first = new Interval(
            seventhFebruaryAtTen,
            seventhFebruaryAtFourteen
        );
        Interval left = new Interval(
            seventhFebruaryAtSix,
            seventhFebruaryAtTwelve
        );
        Interval expected = new Interval(
            seventhFebruaryAtTen,
            seventhFebruaryAtTwelve
        );
        Interval intersection = Interval.intersection(first, left);
        if (intersection != null) {
            assertEquals(intersection.getStart(), expected.getStart());
            assertEquals(intersection.getEnd(), expected.getEnd());
        }
    }

    @Test() public void
    return_intersection_of_intervals_intersecting_on_right() {
        Interval first = new Interval(
            seventhFebruaryAtTen,
            seventhFebruaryAtFourteen
        );
        Interval right = new Interval(
            seventhFebruaryAtTwelve,
            seventhFebruaryAtSeventeen
        );
        Interval expected = new Interval(
            seventhFebruaryAtTwelve,
            seventhFebruaryAtFourteen
        );
        Interval intersection = Interval.intersection(first, right);
        if (intersection != null) {
            assertEquals(intersection.getStart(), expected.getStart());
            assertEquals(intersection.getEnd(), expected.getEnd());
        }
    }

    @Test() public void
    return_intersection_of_intervals_intersecting_inside() {
        Interval first = new Interval(
            seventhFebruaryAtSix,
            seventhFebruaryAtSeventeen
        );
        Interval inside = new Interval(
            seventhFebruaryAtTen,
            seventhFebruaryAtFourteen
        );
        Interval intersection = Interval.intersection(first, inside);
        if (intersection != null) {
            assertEquals(intersection.getStart(), inside.getStart());
            assertEquals(intersection.getEnd(), inside.getEnd());
        }
    }

    @Test() public void
    return_disjunctive_union_of_disjoint_intervals() {
        Interval first = new Interval(
            seventhFebruaryAtSix,
            seventhFebruaryAtTen
        );
        Interval second = new Interval(
            seventhFebruaryAtFourteen,
            seventhFebruaryAtSeventeen
        );
        List<Interval> intervals = Interval.leftDisjunctiveUnion(first, second);
        assertEquals(intervals.size(), 1);
        assertEquals(intervals.get(0).getStart(), seventhFebruaryAtSix);
        assertEquals(intervals.get(0).getEnd(), seventhFebruaryAtTen);
    }

    @Test() public void
    return_disjunctive_union_of_intervals_intersecting_left() {
        Interval first = new Interval(
            seventhFebruaryAtTen,
            seventhFebruaryAtFourteen
        );
        Interval left = new Interval(
            seventhFebruaryAtSix,
            seventhFebruaryAtTwelve
        );
        Interval expected = new Interval(
            seventhFebruaryAtTwelve,
            seventhFebruaryAtFourteen
        );
        List<Interval> intervals = Interval.leftDisjunctiveUnion(first, left);
        assertEquals(intervals.size(), 1);
        assertEquals(intervals.get(0).getStart(), expected.getStart());
        assertEquals(intervals.get(0).getEnd(), expected.getEnd());
    }

    @Test() public void
    return_disjunctive_union_of_intervals_intersecting_right() {
        Interval first = new Interval(
            seventhFebruaryAtTen,
            seventhFebruaryAtFourteen
        );
        Interval right = new Interval(
            seventhFebruaryAtTwelve,
            seventhFebruaryAtSeventeen
        );
        Interval expected = new Interval(
            seventhFebruaryAtTen,
            seventhFebruaryAtTwelve
        );
        List<Interval> intervals = Interval.leftDisjunctiveUnion(first, right);
        assertEquals(intervals.size(), 1);
        assertEquals(intervals.get(0).getStart(), expected.getStart());
        assertEquals(intervals.get(0).getEnd(), expected.getEnd());
    }

    @Test() public void
    return_disjunctive_union_of_intervals_intersecting_inside() {
        Interval first = new Interval(
            seventhFebruaryAtSix,
            seventhFebruaryAtSeventeen
        );
        Interval right = new Interval(
            seventhFebruaryAtTen,
            seventhFebruaryAtFourteen
        );
        Interval firstExpected = new Interval(
            seventhFebruaryAtSix,
            seventhFebruaryAtTen
        );
        Interval secondExpected = new Interval(
            seventhFebruaryAtFourteen,
            seventhFebruaryAtSeventeen
        );
        List<Interval> intervals = Interval.leftDisjunctiveUnion(first, right);
        assertEquals(intervals.size(), 2);
        assertEquals(intervals.get(0).getStart(), firstExpected.getStart());
        assertEquals(intervals.get(0).getEnd(), firstExpected.getEnd());
        assertEquals(intervals.get(1).getStart(), secondExpected.getStart());
        assertEquals(intervals.get(1).getEnd(), secondExpected.getEnd());
    }
}
