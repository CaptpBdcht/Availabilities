import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class IntervalShould {
    private final SimpleDateFormat format = new SimpleDateFormat("y M D H m");
    private final String seventhFebruaryAtSixThirty = "2016 7 2 6 30";
    private final String seventhFebruaryAtTenThirty = "2016 7 2 10 30";
    private final String seventhFebruaryAtTwelve = "2016 7 2 12 00";
    private final String seventhFebruaryAtFourteen = "2016 7 2 14 00";
    private final String seventhFebruaryAtSeventeen = "2016 7 2 17 00";

    private Date startDate = null;
    private Date endDate = null;

    @Test(expected = IllegalArgumentException.class)
    public void
    not_create_if_start_is_null() throws ParseException {
        endDate = format.parse(seventhFebruaryAtSixThirty);
        Interval it = new Interval(null, endDate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void
    not_create_if_end_is_null() throws ParseException {
        startDate = format.parse(seventhFebruaryAtSixThirty);
        Interval it = new Interval(startDate, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void
    not_create_if_start_after_end() throws ParseException {
        startDate = format.parse(seventhFebruaryAtSeventeen);
        endDate = format.parse(seventhFebruaryAtSixThirty);
        Interval it = new Interval(startDate, endDate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void
    not_create_if_start_equals_end() throws ParseException {
        startDate = format.parse(seventhFebruaryAtFourteen);
        endDate = format.parse(seventhFebruaryAtFourteen);
        Interval it = new Interval(startDate, endDate);
    }

    @Test()
    public void
    create_when_given_valid_dates() throws ParseException {
        startDate = format.parse(seventhFebruaryAtSixThirty);
        endDate = format.parse(seventhFebruaryAtTwelve);
        Interval it = new Interval(startDate, endDate);
    }

    @Test()
    public void
    return_intersection_of_disjoint_intervals() throws ParseException {
        Interval first = new Interval(
                format.parse(seventhFebruaryAtSixThirty),
                format.parse(seventhFebruaryAtTenThirty)
        );
        Interval second = new Interval(
                format.parse(seventhFebruaryAtFourteen),
                format.parse(seventhFebruaryAtSeventeen)
        );
        assertNull(Interval.intersection(first, second));
    }

    @Test()
    public void
    return_intersection_of_same_interval() throws ParseException {
        Interval first = new Interval(
                format.parse(seventhFebruaryAtTenThirty),
                format.parse(seventhFebruaryAtTwelve)
        );
        Interval intersection = Interval.intersection(first, first);
        if (intersection != null) {
            assertEquals(intersection.getStart(), first.getStart());
            assertEquals(intersection.getEnd(), first.getEnd());
        }
    }

    @Test()
    public void
    return_intersection_of_intervals_intersecting_on_left() throws ParseException {
        Interval first = new Interval(
                format.parse(seventhFebruaryAtTenThirty),
                format.parse(seventhFebruaryAtFourteen)
        );
        Interval left = new Interval(
                format.parse(seventhFebruaryAtSixThirty),
                format.parse(seventhFebruaryAtTwelve)
        );
        Interval expected = new Interval(
                format.parse(seventhFebruaryAtTenThirty),
                format.parse(seventhFebruaryAtTwelve)
        );
        Interval intersection = Interval.intersection(first, left);
        if (intersection != null) {
            assertEquals(intersection.getStart(), expected.getStart());
            assertEquals(intersection.getEnd(), expected.getEnd());
        }
    }

    @Test()
    public void
    return_intersection_of_intervals_intersecting_on_right() throws ParseException {
        Interval first = new Interval(
                format.parse(seventhFebruaryAtTenThirty),
                format.parse(seventhFebruaryAtFourteen)
        );
        Interval right = new Interval(
                format.parse(seventhFebruaryAtTwelve),
                format.parse(seventhFebruaryAtSeventeen)
        );
        Interval expected = new Interval(
                format.parse(seventhFebruaryAtTwelve),
                format.parse(seventhFebruaryAtFourteen)
        );
        Interval intersection = Interval.intersection(first, right);
        if (intersection != null) {
            assertEquals(intersection.getStart(), expected.getStart());
            assertEquals(intersection.getEnd(), expected.getEnd());
        }
    }

    @Test()
    public void
    return_intersection_of_intervals_intersecting_inside() throws ParseException {
        Interval first = new Interval(
                format.parse(seventhFebruaryAtSixThirty),
                format.parse(seventhFebruaryAtSeventeen)
        );
        Interval inside = new Interval(
                format.parse(seventhFebruaryAtTenThirty),
                format.parse(seventhFebruaryAtFourteen)
        );
        Interval intersection = Interval.intersection(first, inside);
        if (intersection != null) {
            assertEquals(intersection.getStart(), inside.getStart());
            assertEquals(intersection.getEnd(), inside.getEnd());
        }
    }

    @Test()
    public void
    return_disjunctive_union_of_disjoint_intervals() throws ParseException {
        Interval first = new Interval(
                format.parse(seventhFebruaryAtSixThirty),
                format.parse(seventhFebruaryAtTenThirty)
        );
        Interval second = new Interval(
                format.parse(seventhFebruaryAtFourteen),
                format.parse(seventhFebruaryAtSeventeen)
        );
        List<Interval> intervals = Interval.leftDisjunctiveUnion(first, second);
        assertEquals(intervals.size(), 1);
        assertEquals(intervals.get(0).getStart(), format.parse(seventhFebruaryAtSixThirty));
        assertEquals(intervals.get(0).getEnd(), format.parse(seventhFebruaryAtTenThirty));
    }

    @Test()
    public void
    return_disjunctive_union_of_intervals_intersecting_left() throws ParseException {
        Interval first = new Interval(
                format.parse(seventhFebruaryAtTenThirty),
                format.parse(seventhFebruaryAtFourteen)
        );
        Interval left = new Interval(
                format.parse(seventhFebruaryAtSixThirty),
                format.parse(seventhFebruaryAtTwelve)
        );
        Interval expected = new Interval(
                format.parse(seventhFebruaryAtTwelve),
                format.parse(seventhFebruaryAtFourteen)
        );
        List<Interval> intervals = Interval.leftDisjunctiveUnion(first, left);
        assertEquals(intervals.size(), 1);
        assertEquals(intervals.get(0).getStart(), expected.getStart());
        assertEquals(intervals.get(0).getEnd(), expected.getEnd());
    }

    @Test()
    public void
    return_disjunctive_union_of_intervals_intersecting_right() throws ParseException {
        Interval first = new Interval(
                format.parse(seventhFebruaryAtTenThirty),
                format.parse(seventhFebruaryAtFourteen)
        );
        Interval right = new Interval(
                format.parse(seventhFebruaryAtTwelve),
                format.parse(seventhFebruaryAtSeventeen)
        );
        Interval expected = new Interval(
                format.parse(seventhFebruaryAtTenThirty),
                format.parse(seventhFebruaryAtTwelve)
        );
        List<Interval> intervals = Interval.leftDisjunctiveUnion(first, right);
        assertEquals(intervals.size(), 1);
        assertEquals(intervals.get(0).getStart(), expected.getStart());
        assertEquals(intervals.get(0).getEnd(), expected.getEnd());
    }

    @Test()
    public void
    return_disjunctive_union_of_intervals_intersecting_inside() throws ParseException {
        Interval first = new Interval(
                format.parse(seventhFebruaryAtSixThirty),
                format.parse(seventhFebruaryAtSeventeen)
        );
        Interval right = new Interval(
                format.parse(seventhFebruaryAtTenThirty),
                format.parse(seventhFebruaryAtFourteen)
        );
        Interval firstExpected = new Interval(
                format.parse(seventhFebruaryAtSixThirty),
                format.parse(seventhFebruaryAtTenThirty)
        );
        Interval secondExpected = new Interval(
                format.parse(seventhFebruaryAtFourteen),
                format.parse(seventhFebruaryAtSeventeen)
        );
        List<Interval> intervals = Interval.leftDisjunctiveUnion(first, right);
        assertEquals(intervals.size(), 2);
        assertEquals(intervals.get(0).getStart(), firstExpected.getStart());
        assertEquals(intervals.get(0).getEnd(), firstExpected.getEnd());
        assertEquals(intervals.get(1).getStart(), secondExpected.getStart());
        assertEquals(intervals.get(1).getEnd(), secondExpected.getEnd());
    }
}
