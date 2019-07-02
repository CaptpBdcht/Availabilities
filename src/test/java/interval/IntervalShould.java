package interval;

import helpers.DateHelper;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
    private final Date seventhFebruaryAtTwenty = format.parse("2016 7 2 20 00");

    public IntervalShould() throws ParseException {
    }

    @Test(expected = IllegalArgumentException.class)
    public void
    not_create_if_start_is_null() {
        new Interval(null, seventhFebruaryAtSix);
    }

    @Test(expected = IllegalArgumentException.class)
    public void
    not_create_if_end_is_null() {
        new Interval(seventhFebruaryAtSix, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void
    not_create_if_start_after_end() {
        new Interval(
            seventhFebruaryAtSeventeen,
            seventhFebruaryAtSix
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void
    not_create_if_start_equals_end() {
        new Interval(
            seventhFebruaryAtFourteen,
            seventhFebruaryAtFourteen
        );
    }

    @Test()
    public void
    create_when_given_valid_dates() {
        new Interval(
            seventhFebruaryAtSix,
            seventhFebruaryAtTwelve
        );
    }

    @Test()
    public void
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

    @Test()
    public void
    return_true_for_intersecting_intervals() {
        Interval interval = new Interval(
            seventhFebruaryAtTen,
            seventhFebruaryAtTwelve
        );
        assertTrue(Interval.doesIntersect(interval, interval));
    }

    @Test()
    public void
    return_intersection_of_disjoint_intervals() {
        Interval first = new Interval(
            seventhFebruaryAtSix,
            seventhFebruaryAtTen
        );
        Interval second = new Interval(
            seventhFebruaryAtFourteen,
            seventhFebruaryAtSeventeen
        );
        assertNull(Interval.intersect(first, second));
    }

    @Test()
    public void
    return_intersection_of_same_interval() {
        Interval first = new Interval(
            seventhFebruaryAtTen,
            seventhFebruaryAtTwelve
        );

        Interval intersection = Interval.intersect(first, first);
        if (intersection != null) {
            assertEquals(intersection.getStart(), first.getStart());
            assertEquals(intersection.getEnd(), first.getEnd());
        }
    }

    @Test()
    public void
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

        Interval intersection = Interval.intersect(first, left);
        if (intersection != null) {
            assertEquals(intersection.getStart(), expected.getStart());
            assertEquals(intersection.getEnd(), expected.getEnd());
        }
    }

    @Test()
    public void
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

        Interval intersection = Interval.intersect(first, right);
        if (intersection != null) {
            assertEquals(intersection.getStart(), expected.getStart());
            assertEquals(intersection.getEnd(), expected.getEnd());
        }
    }

    @Test()
    public void
    return_intersection_of_intervals_intersecting_inside() {
        Interval first = new Interval(
            seventhFebruaryAtSix,
            seventhFebruaryAtSeventeen
        );
        Interval inside = new Interval(
            seventhFebruaryAtTen,
            seventhFebruaryAtFourteen
        );

        Interval intersection = Interval.intersect(first, inside);
        if (intersection != null) {
            assertEquals(intersection.getStart(), inside.getStart());
            assertEquals(intersection.getEnd(), inside.getEnd());
        }
    }

    @Test()
    public void
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

    @Test()
    public void
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

    @Test()
    public void
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

    @Test()
    public void
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

    @Test()
    public void
    remove_interval_list_from_interval_disjoint_left() {
        Interval first = new Interval(
            seventhFebruaryAtSix,
            seventhFebruaryAtTen
        );
        Interval second = new Interval(
            seventhFebruaryAtTen,
            seventhFebruaryAtTwelve
        );
        Interval interval = new Interval(
            seventhFebruaryAtFourteen,
            seventhFebruaryAtSeventeen
        );
        List<Interval> intervals = Arrays.asList(first, second);

        List<Interval> result = Interval.leftDisjunctiveUnionList(interval, intervals);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getStart(), interval.getStart());
        assertEquals(result.get(0).getEnd(), interval.getEnd());
    }

    @Test()
    public void
    remove_interval_list_from_interval_disjoint_right() {
        Interval interval = new Interval(
            seventhFebruaryAtSix,
            seventhFebruaryAtTen
        );
        Interval first = new Interval(
            seventhFebruaryAtTwelve,
            seventhFebruaryAtFourteen
        );
        Interval second = new Interval(
            seventhFebruaryAtFourteen,
            seventhFebruaryAtSeventeen
        );
        List<Interval> intervals = Arrays.asList(first, second);

        List<Interval> result = Interval.leftDisjunctiveUnionList(interval, intervals);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getStart(), interval.getStart());
        assertEquals(result.get(0).getEnd(), interval.getEnd());
    }

    @Test()
    public void
    remove_interval_list_from_interval_disjoint_inside() {
        Interval interval = new Interval(
            seventhFebruaryAtSix,
            seventhFebruaryAtTwenty
        );
        Interval first = new Interval(
            seventhFebruaryAtTen,
            seventhFebruaryAtTwelve
        );
        Interval second = new Interval(
            seventhFebruaryAtFourteen,
            seventhFebruaryAtSeventeen
        );
        Interval expectedFirst = new Interval(
            seventhFebruaryAtSix,
            seventhFebruaryAtTen
        );
        Interval expectedSecond = new Interval(
            seventhFebruaryAtTwelve,
            seventhFebruaryAtFourteen
        );
        Interval expectedThird = new Interval(
            seventhFebruaryAtSeventeen,
            seventhFebruaryAtTwenty
        );
        List<Interval> intervals = Arrays.asList(first, second);

        List<Interval> result = Interval.leftDisjunctiveUnionList(interval, intervals);
        assertEquals(result.size(), 3);
        assertEquals(result.get(0).getStart(), expectedFirst.getStart());
        assertEquals(result.get(0).getEnd(), expectedFirst.getEnd());
        assertEquals(result.get(1).getStart(), expectedSecond.getStart());
        assertEquals(result.get(1).getEnd(), expectedSecond.getEnd());
        assertEquals(result.get(2).getStart(), expectedThird.getStart());
        assertEquals(result.get(2).getEnd(), expectedThird.getEnd());
    }

    @Test()
    public void
    intersect_lists_disjoint_on_left() {
        Interval first = new Interval(
            seventhFebruaryAtSix,
            seventhFebruaryAtTen
        );
        Interval second = new Interval(
            seventhFebruaryAtTen,
            seventhFebruaryAtTwelve
        );
        Interval third = new Interval(
            seventhFebruaryAtTwelve,
            seventhFebruaryAtFourteen
        );
        Interval fourth = new Interval(
            seventhFebruaryAtFourteen,
            seventhFebruaryAtSeventeen
        );
        List<Interval> left = Arrays.asList(first, second);
        List<Interval> right = Arrays.asList(third, fourth);

        List<Interval> intersections = Interval.intersectList(left, right);
        assertEquals(intersections.size(), 0);
    }

    @Test()
    public void
    intersect_lists_disjoint_on_right() {
        Interval first = new Interval(
            seventhFebruaryAtSix,
            seventhFebruaryAtTen
        );
        Interval second = new Interval(
            seventhFebruaryAtTen,
            seventhFebruaryAtTwelve
        );
        Interval third = new Interval(
            seventhFebruaryAtTwelve,
            seventhFebruaryAtFourteen
        );
        Interval fourth = new Interval(
            seventhFebruaryAtFourteen,
            seventhFebruaryAtSeventeen
        );
        List<Interval> left = Arrays.asList(first, second);
        List<Interval> right = Arrays.asList(third, fourth);

        List<Interval> intersections = Interval.intersectList(right, left);
        assertEquals(intersections.size(), 0);
    }

    @Test()
    public void
    intersect_lists() {
        Interval sixTwelve = new Interval(
            seventhFebruaryAtSix,
            seventhFebruaryAtTwelve
        );
        Interval fourteenTwenty = new Interval(
            seventhFebruaryAtFourteen,
            seventhFebruaryAtTwenty
        );
        Interval tenFourteen = new Interval(
            seventhFebruaryAtTen,
            seventhFebruaryAtFourteen
        );
        Interval seventeenTwenty = new Interval(
            seventhFebruaryAtSeventeen,
            seventhFebruaryAtTwenty
        );
        List<Interval> first = Arrays.asList(sixTwelve, fourteenTwenty);
        List<Interval> second = Arrays.asList(tenFourteen, seventeenTwenty);

        Interval tenTwelve = new Interval(
            seventhFebruaryAtTen,
            seventhFebruaryAtTwelve
        );

        List<Interval> intersections = Interval.intersectList(first, second);
        assertEquals(intersections.size(), 2);
        assertEquals(intersections.get(0).getStart(), tenTwelve.getStart());
        assertEquals(intersections.get(0).getEnd(), tenTwelve.getEnd());
        assertEquals(intersections.get(1).getStart(), seventeenTwenty.getStart());
        assertEquals(intersections.get(1).getEnd(), seventeenTwenty.getEnd());
    }
}
