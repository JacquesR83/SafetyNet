package com.safetynetalerts.demo.service;

import com.safetynetalerts.demo.model.Firestation;
import com.safetynetalerts.demo.model.Person;
import com.safetynetalerts.demo.repository.FirestationRepository;
import com.safetynetalerts.demo.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FirestationServiceTest {


    @MockBean
    FirestationRepository firestationRepository;

    @MockBean
    PersonRepository personRepository;

    @Autowired
    FirestationService firestationService;


    List<Person> persons;
    List<Firestation> firestations;

    @BeforeEach
    void setUp() {

        Firestation firestation1 = new Firestation ();
        firestation1.setStation("1");
        firestation1.setAddress("123 rue daumesnil");
        Firestation firestation2 = new Firestation ();
        firestation2.setStation("2");
        firestation2.setAddress("456 avenue du maine");

        firestations = new ArrayList<>();
        firestations.add(firestation1);
        firestations.add(firestation2);

        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        person1.setPhone("123-456-7890");
        person1.setAddress("123 rue daumesnil");

        Person person2 = new Person();
        person2.setFirstName("Jane");
        person2.setLastName("Smith");
        person2.setPhone("243-456-7890");
        person2.setAddress("789 Main St");

        persons = new ArrayList<>();
        persons.add(person1);
        persons.add(person2);

        Mockito.when(firestationRepository.findAllFireStationsByNumber(1)).thenReturn(firestations);
        Mockito.when(personRepository.findAllPersons()).thenReturn(persons);
        Mockito.when(firestationRepository.findAllFireStations()).thenReturn(firestations);


    }

    @Test
    void findPhoneNumbersByStationNumber() {
        List<String> phoneNumbers = new ArrayList<>();


        //ACT
        phoneNumbers = firestationService.findPhoneNumbersByStationNumber(1);

        //ASSERT

        assertEquals(1,phoneNumbers.size());
    }

    @Test
    void personContainsFirestationAddress() {

        //ARRANGE
        Person person1 = persons.get(0);

        //ACT
        boolean exist = firestationService.personContainsFirestationAddress(firestations,person1);

        //ASSERT
        assertTrue(exist);
    }

    @Test
    void addressesDeliveredByFirestation() {
        //ARRANGE
        List<String> addresses = new ArrayList<>();

        //ACT
        addresses = firestationService.addressesDeliveredByFirestation(1);

        //ASSERT
        assertEquals(1,addresses.size());

    }

    @Test
    void findPeopleCountByFirestation() {
    }

    @Test
    void findPeopleByStation() {
    }

    @Test
    void getFirestation() {
    }

    @Test
    void addFirestation() {
    }

    @Test
    void deleteFirestation() {
    }

    @Test
    void updateFirestation() {
    }
}