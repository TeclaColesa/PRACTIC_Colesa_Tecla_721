package org.example.model;

public class Vechicle implements Identifiable<Integer>{
    private int id;
    private String licensePlate;
    private VehicleType type;
    private String ownerCity;
    private VechicleStatus status;

    protected Vechicle(){}

    public Vechicle(int id, String licensePlate, VehicleType type, String ownerCity, VechicleStatus status) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.type = type;
        this.ownerCity = ownerCity;
        this.status = status;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public String getOwnerCity() {
        return ownerCity;
    }

    public void setOwnerCity(String ownerCity) {
        this.ownerCity = ownerCity;
    }

    public VechicleStatus getStatus() {
        return status;
    }

    public void setStatus(VechicleStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "[" +id + "] | " + licensePlate + " | " + type + " | " + ownerCity + " | city=" + status;
    }
}
