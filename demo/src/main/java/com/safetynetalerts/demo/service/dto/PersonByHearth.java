package com.safetynetalerts.demo.service.dto;

public class PersonByHearth {
    String lastName;
    String phoneNumber;
    int age;
    String[] medications;
    String[] allergies;


    public PersonByHearth() {
    }

    public PersonByHearth(String lastName, String phoneNumber, int age, String[] medications, String[] allergies) {
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.medications = medications;
        this.allergies = allergies;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String[] getMedications() {
        return medications;
    }

    public void setMedications(String[] medications) {
        this.medications = medications;
    }

    public String[] getAllergies() {
        return allergies;
    }

    public void setAllergies(String[] allergies) {
        this.allergies = allergies;
    }
}
