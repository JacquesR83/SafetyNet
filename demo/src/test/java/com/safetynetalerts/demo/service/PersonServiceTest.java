package com.safetynetalerts.demo.service;

import com.safetynetalerts.demo.model.Person;
import com.safetynetalerts.demo.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class PersonServiceTest {

    PersonRepository personRepository;
    Person person = new Person();


    @BeforeEach
    void setUp() {
    }

    @Test
    void findAllEmailsByCity() {
//        person.setCity("Paris");
//        assertSame();

    }

    @Test
    void findAllPersonsWithMedicalRecords() {
    }

    @Test
    void medicalRecordsContainsPerson() {
    }

    @Test
    void familyInformation() {
    }

    @Test
    void childListAtThisAddress() {
    }

    @Test
    void findAllPersonsWithLastName() {
    }

    @Test
    void addPerson() {
    }

    @Test
    void deletePersonByFirstNameAndLastName() {
    }

    @Test
    void updatePerson() {
    }
}