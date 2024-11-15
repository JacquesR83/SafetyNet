package com.safetynetalerts.demo.model;

public class firestation {
    private String address;
    private int stationNumber;

    public firestation() {
    }

    public firestation(String address, int stationNumber) {
        this.address = address;
        this.stationNumber = stationNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber;
    }
}
