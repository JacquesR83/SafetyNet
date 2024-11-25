package com.safetynetalerts.demo.service.dto;


import java.util.List;

public class HearthDTO {
    String address;
    List<PersonByHearth> people;


    public HearthDTO(String address, List<PersonByHearth> people) {
        this.people = people;
    }

    public HearthDTO() {
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<PersonByHearth> getPeople() {
        return people;
    }

    public void setPeople(List<PersonByHearth> people) {
        this.people = people;
    }
}
