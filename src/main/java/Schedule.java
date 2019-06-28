import java.util.ArrayList;
import java.util.List;

class Schedule {
    private final List<Event> events = new ArrayList<Event>();

    void addEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("event cannot be null");
        }
        events.add(event);
    }

    List<Interval> availabilitiesOn(Interval askedInterval) {
        return new ArrayList<>();
    }
}
