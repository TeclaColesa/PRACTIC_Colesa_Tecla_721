package org.example.model;

public class TrafficEvent implements Identifiable<Integer>{
    private int id;
    private int vechicleId;
    private EventType type;
    private int severity;
    private int timeSlot;

    public TrafficEvent(int id, int vechicleId, EventType eventType, int severity, int timeSlot) {
        this.id = id;
        this.vechicleId = vechicleId;
        this.type = eventType;
        this.severity = severity;
        this.timeSlot = timeSlot;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVechicleId() {
        return vechicleId;
    }

    public void setVechicleId(int vechicleId) {
        this.vechicleId = vechicleId;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public int getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    @Override
    public String toString() {
        return id + " | " + vechicleId + " | " + type + " | " + severity + " | " + timeSlot;
    }
}
