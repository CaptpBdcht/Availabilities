package schedule;

import events.BusyEvent;
import events.Event;
import events.NonRecurringOpeningEvent;
import interval.Interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Schedule {

    private final List<Event> events = new ArrayList<>();

    public void addEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("event cannot be null");
        }
        events.add(event);
    }

    public List<Interval> availabilitiesOn(Interval askedInterval) {
        boolean hasOpening = events.stream()
            .filter(Event::isOpening)
            .anyMatch(e -> true);

        if (!hasOpening) {
            return Collections.emptyList();
        }

        List<Interval> nonRecurringOpeningEvents = events.stream()
            .filter(e -> e.getClass() == NonRecurringOpeningEvent.class)
            .map(Event::getInterval)
            .collect(Collectors.toList());

        List<Interval> busyEvents = events.stream()
            .filter(e -> e.getClass() == BusyEvent.class)
            .map(Event::getInterval)
            .collect(Collectors.toList());

        List<Interval> askedLessBusy = Interval.leftDisjunctiveUnionList(
            askedInterval, busyEvents
        );

        return Interval.intersectList(
            askedLessBusy, nonRecurringOpeningEvents
        );
    }
}
