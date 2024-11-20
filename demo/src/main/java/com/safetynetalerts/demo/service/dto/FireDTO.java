package com.safetynetalerts.demo.service.dto;

public class FireDTO {

    private String firestation;
    private String lastName;
    private String phoneNumber;
    private String age;
    private String[] medications;
    private String[] allergies;

    public String getFirestation() {
        return firestation;
    }

    public void setFirestation(String firestation) {
        this.firestation = firestation;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
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
