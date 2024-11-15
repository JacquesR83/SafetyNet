package com.safetynetalerts.demo.model;

public class persons {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;

    public persons() {
    }

    public persons(Integer id, String firstName, String lastName, String address, String city, String zip, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }
}
