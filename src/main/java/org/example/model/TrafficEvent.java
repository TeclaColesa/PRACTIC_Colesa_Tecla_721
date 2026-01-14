package org.example.model;

public class TrafficEvent implements Identifiable<Integer>{
    private int id;
    private int vehicleId;
    private EventType type;
    private int severity;
    private int timeSlot;

    protected TrafficEvent(){}

    public TrafficEvent(int id, int vechicleId, EventType eventType, int severity, int timeSlot) {
        this.id = id;
        this.vehicleId = vechicleId;
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

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
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
        return id + " | " + vehicleId + " | " + type + " | " + severity + " | " + timeSlot;
    }
}
