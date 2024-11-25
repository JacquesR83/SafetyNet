package com.safetynetalerts.demo.service.dto;

import java.util.List;

public class FirestationDTO {
    int childrenCount;
    int adultsCount;
    List<FirestationPersonDTO> people;

    public FirestationDTO() {
    }

    public FirestationDTO(int childrenCount, int adultsCount, List<FirestationPersonDTO> people) {
        this.childrenCount = childrenCount;
        this.adultsCount = adultsCount;
        this.people = people;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }

    public int getAdultsCount() {
        return adultsCount;
    }

    public void setAdultsCount(int adultsCount) {
        this.adultsCount = adultsCount;
    }

    public List<FirestationPersonDTO> getPeople() {
        return people;
    }

    public void setPeople(List<FirestationPersonDTO> people) {
        this.people = people;
    }
}
