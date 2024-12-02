package com.safetynetalerts.demo.controller;

import com.safetynetalerts.demo.model.Firestation;
import com.safetynetalerts.demo.model.Person;
import com.safetynetalerts.demo.repository.FirestationRepository;
import com.safetynetalerts.demo.repository.PersonRepository;
import com.safetynetalerts.demo.service.FirestationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FirestationControllerTest {

    @MockBean
    FirestationService firestationService;

    @MockBean
    FirestationRepository firestationRepository;

    @MockBean
    PersonRepository personRepository;

    @Autowired
    FirestationController firestationController;

    Firestation firestation1 = new Firestation();
    Firestation firestation2 = new Firestation();


    List<Person> people = new ArrayList<>();
    List <Firestation> firestations = new ArrayList<>();

    @BeforeEach
    void setUp() {


        firestation1.setStation("1");
        firestation1.setAddress("123 rue de la paix");


        firestation2.setStation("2");
        firestation2.setAddress("456 rue des mines");

        firestations.add(firestation1);
        firestations.add(firestation2);

        Person person1 = new Person(
                "John",
                "Doe",
                "123 rue de la paix",
                "Marseille",
                "13000",
                "514-652-2040",
                "johndoe@gmail.com"
        );

        Person person2 = new Person(
                "Santa",
                "Doe",
                "123 rue de la paix",
                "Marseille",
                "13000",
                "514-652-2045",
                "santadoe@gmail.com"
        );

        Person person3 = new Person(
                "Jane",
                "Smith",
                "456 rue des mines",
                "Marseille",
                "13000",
                "2222225",
                "janesmith@gmail.com"
        );

        people.add(person1);
        people.add(person2);
        people.add(person3);


        Mockito.when(firestationRepository.findAllFireStationsByNumber(1)).thenReturn(firestations);
        Mockito.when(personRepository.findAllPersons()).thenReturn(people);

    }

    @Test
    void getFirestation() {

        //ARRANGE
        Mockito.when(firestationService.getFirestation("123 rue de la paix")).thenReturn(firestation1);

        //ACT
        Firestation resultat = firestationController.getFirestation ("123 rue de la paix");

        //ASSERT

        assertEquals(firestation1,resultat);

    }

    @Test
    void getFirestationException() {

        //ARRANGE
        Mockito.when(firestationService.getFirestation("123 rue de la paix")).thenReturn(firestation1);

        //ACT

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            firestationController.getFirestation(null);
        });

        //ASSERT
        assertEquals("No firestation at this address", exception.getMessage());

    }

    @Test
    void addFirestation() {

        //ARRANGE
        Mockito.when(firestationService.addFirestation(firestation1)).thenReturn(firestation1);

        //ACT
        Firestation result = firestationController.addFirestation(firestation1);

        //ASSERT
        assertEquals(result.getAddress(), firestation1.getAddress());

    }

    @Test
    void deleteFirestation() {
        //ARRANGE

        //ACT
        firestationController.deleteFirestation("1");


        //ASSERT
        Mockito.verify(firestationService, Mockito.times(1)).deleteFirestation("1");
    }

    @Test
    void updateFirestation() {

        //ARRANGE
        Firestation updatedFirestation = new Firestation();
        updatedFirestation.setAddress("456 rue charlemagne");
        updatedFirestation.setStation("2");

        Mockito.when(firestationService.updateFirestation("123 rue de la paix", updatedFirestation)).thenReturn(updatedFirestation);

        //ACT
        Firestation result = firestationController.updateFirestation ("123 rue de la paix", updatedFirestation);

        //ASSERT
        assertEquals(result.getAddress(), updatedFirestation.getAddress());

    }

    @Test
    void phoneNumbersList() {

        //ARRANGE
        List<String> expectedPhoneNumbers = new ArrayList<>();
        expectedPhoneNumbers.add("514-652-2040");
        expectedPhoneNumbers.add("514-652-2045");


        Mockito.when(firestationService.findPhoneNumbersByStationNumber(1)).thenReturn(expectedPhoneNumbers);

        //ACT
        List<String> result = firestationController.phoneNumbersList(1);

        //ASSERT
        assertEquals(expectedPhoneNumbers,result);
    }

    @Test
    void personListByFirestation() {
    }

    @Test
    void hearthListByFirestation() {
    }
}