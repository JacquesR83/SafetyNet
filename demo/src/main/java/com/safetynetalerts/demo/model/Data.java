package com.safetynetalerts.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


// Sert de base de données en mémoire, elle a les collections pour stocker les objets


public class Data {

//    //Initialize data - method 2
//    private static final Data INSTANCE = new Data();
    //        // method 2
//    public static Data getInstance(){
//        return INSTANCE;
//    }

//    @JsonProperty("persons") method 2
    private List<Person> persons;
//    @JsonProperty("firestations") method 2
    private List<Firestation> firestations;
//    @JsonProperty("medicalrecords") method 2
    private List<MedicalRecord> medicalrecords;



    private Data(){
    }


    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Firestation> getFirestations() {
        return firestations;
    }

    public void setFirestations(List<Firestation> firestations) {
        this.firestations = firestations;
    }

    public List<MedicalRecord> getMedicalrecords() {
        return medicalrecords;
    }

    public void setMedicalrecords(List<MedicalRecord> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }

    public void addPerson(Person person) {
        persons.add(person);
    }
}
