package com.safetynetalerts.demo.repository;

import com.safetynetalerts.demo.model.Person;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonRepository {

    private DataHandler dataHandler;

    public PersonRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public List<Person> findAllPersons() {
        return dataHandler.getData().getPersons();
    }

    public List<Person> findAllPersonsByAddress(String address) {
        return dataHandler.getData().getPersons().stream().filter(p -> p.getAddress().equals(address)).collect(Collectors.toList());
    }

    public Person findPersonByFirstNameAndLastName(String firstName, String lastName) {
        return dataHandler.getData().getPersons().stream()
                .filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName))
                .findFirst().orElseGet(() -> new Person());
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

    public void addToPersonList(Person person) {
        dataHandler.getData().addPerson(person);
        try {
            dataHandler.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePerson(String firstName, String lastName) {
        dataHandler.getData().getPersons().removeIf(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName));
        try {
            dataHandler.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean exists(String firstName, String lastName) {
        if (dataHandler.getData().getPersons().contains(findPersonByFirstNameAndLastName(firstName, lastName))) {
            return true;
        } else {
            return false;
        }
    }
}
