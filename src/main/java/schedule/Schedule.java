package schedule;

import events.Event;
import interval.Interval;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private final List<Event> events = new ArrayList<>();

    public void addEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("event cannot be null");
        }
        events.add(event);
    }

    public List<Interval> availabilitiesOn(Interval askedInterval) {
        events.stream()
            .filter(Event::isOpening)
            .forEach(System.out::println);

        return new ArrayList<>();
    }
}
