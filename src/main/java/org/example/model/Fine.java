package org.example.model;

public class Fine implements Identifiable<Integer>{
    private int id;
    private int vechicleId;
    private int amount;
    private FineReason reason;
    private int timeSlot;

    public Fine(int id, int vechicleId, int amount, FineReason reason, int timeSlot) {
        this.id = id;
        this.vechicleId = vechicleId;
        this.amount = amount;
        this.reason = reason;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public FineReason getReason() {
        return reason;
    }

    public void setReason(FineReason reason) {
        this.reason = reason;
    }

    public int getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    @Override
    public String toString() {
        return id + " | " + vechicleId + " | " + reason + " | " + amount + " | " + timeSlot;
    }
}
