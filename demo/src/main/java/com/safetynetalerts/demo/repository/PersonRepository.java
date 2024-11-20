package com.safetynetalerts.demo.repository;

import com.safetynetalerts.demo.model.MedicalRecord;
import com.safetynetalerts.demo.model.Person;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
public class PersonRepository {

    private DataHandler dataHandler;

    public PersonRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public List<Person> findAllPersons(){
        return dataHandler.getData().getPersons();
    }

    public List<Person> findAllPersonsByAddress(String address) {
        return dataHandler.getData().getPersons().stream().filter(p -> p.getAddress().equals(address)).collect(Collectors.toList());
    }

    public Person findPersonByFirstNameAndLastName(String firstName, String lastName){
        return dataHandler.getData().getPersons().stream()
                .filter(p ->p.getFirstName().equals(firstName) && p.getLastName().equals(lastName))
                        .findFirst().orElseGet(()-> new Person());
    }

    public String findPersonEmail(String firstName, String lastName) {
        return dataHandler.getData().getPersons().stream()
                .filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)).findFirst().map(p -> p.getEmail()).orElse(null);
    }

    public String findPersonAddress(String lastName) {
        return dataHandler.getData().getPersons().stream()
                .filter(person -> person.getLastName().equals(lastName)).findFirst().map(p -> p.getAddress()).orElse(null);
    }

    public String[] getPersonMedication(String lastName) {
        return dataHandler.getData().getMedicalrecords().stream()
                .filter(medicalRecord -> medicalRecord.getLastName().equals(lastName)).findFirst().map(medicalRecord -> medicalRecord.getMedications()).orElse(null);
    }

    public List<Person> findAllPersonsWithLastName(String lastName) {
        return dataHandler.getData().getPersons().stream()
                .filter(person -> person.getLastName().equals(lastName)).collect(Collectors.toList());
    }
}
