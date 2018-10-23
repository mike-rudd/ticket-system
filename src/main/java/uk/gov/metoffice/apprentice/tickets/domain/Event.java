package uk.gov.metoffice.apprentice.tickets.domain;

import java.time.LocalDateTime;

public class Event {
    private Object eventId;
    private String name;
    private String location;
    private LocalDateTime dateTime;

    public Event(Object eventId, String name, String location, LocalDateTime dateTime) {
        this.eventId = eventId;
        this.name = name;
        this.location = location;
        this.dateTime = dateTime;
    }

    public Object getEventId() {
        return eventId;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
