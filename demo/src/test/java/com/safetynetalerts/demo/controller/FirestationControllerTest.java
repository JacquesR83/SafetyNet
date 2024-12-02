package com.safetynetalerts.demo.controller;

import com.safetynetalerts.demo.model.Firestation;
import com.safetynetalerts.demo.model.MedicalRecord;
import com.safetynetalerts.demo.model.Person;
import com.safetynetalerts.demo.repository.FirestationRepository;
import com.safetynetalerts.demo.repository.MedicalRecordsRepository;
import com.safetynetalerts.demo.repository.PersonRepository;
import com.safetynetalerts.demo.service.FirestationService;
import com.safetynetalerts.demo.service.MedicalRecordsService;
import com.safetynetalerts.demo.service.PersonService;
import com.safetynetalerts.demo.service.dto.FirestationDTO;
import com.safetynetalerts.demo.service.dto.FirestationPersonDTO;
import com.safetynetalerts.demo.service.dto.HearthDTO;
import com.safetynetalerts.demo.service.dto.PersonByHearth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.bind.annotation.RequestParam;

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
        //ARRANGE
        FirestationDTO firestationDTO = new FirestationDTO();

        List<FirestationPersonDTO> persons = new ArrayList<>();

        FirestationPersonDTO personDTO1 = new FirestationPersonDTO();
        personDTO1.setFirstName("John");
        personDTO1.setLastName("Doe");
        personDTO1.setAddress("123 rue de la paix");
        personDTO1.setPhoneNumber("514-652-2040");

        FirestationPersonDTO personDTO2 = new FirestationPersonDTO();
        personDTO2.setFirstName("Santa");
        personDTO2.setLastName("Doe");
        personDTO2.setAddress("123 rue de la paix");
        personDTO2.setPhoneNumber("514-652-2045");

        FirestationPersonDTO personDTO3 = new FirestationPersonDTO();
        personDTO3.setFirstName("Jane");
        personDTO3.setLastName("Smith");
        personDTO3.setAddress("456 rue des mines");
        personDTO3.setPhoneNumber("2222225");

        persons.add(personDTO1);
        persons.add(personDTO2);
        persons.add(personDTO3);

        firestationDTO.setPeople(persons);
        firestationDTO.setChildrenCount(1);
        firestationDTO.setAdultsCount(1);

        Mockito.when(firestationService.findPeopleCountByFirestation(1)).thenReturn(firestationDTO);

        //ACT
        FirestationDTO result = firestationController.personListByFirestation(1);

        //ASSERT
        assertEquals(firestationDTO, result);
    }

    @Test
    void hearthListByFirestation() {

        List<PersonByHearth> peopleByHearth = new ArrayList<>();
        PersonByHearth person1 = new PersonByHearth();
        person1.setLastName("Doe");
        person1.setPhoneNumber("514-652-2040");
        person1.setAge(19);
        person1.setMedications(new String[]{"amoxicillin : 20mg"});
        person1.setAllergies( new String[]{"peanut butter" });

        PersonByHearth person2 = new PersonByHearth();
        person2.setLastName("Doe");
        person2.setPhoneNumber("514-652-2045");
        person2.setAge(5);
        person2.setMedications(new String[]{""});
        person2.setAllergies( new String[]{" " });

        peopleByHearth.add(person1);
        peopleByHearth.add(person2);


        List<HearthDTO> hearthsList = new ArrayList<>();

        HearthDTO hearth1 = new HearthDTO();
        hearth1.setAddress("123 rue de la paix");
        hearth1.setPeople(peopleByHearth);

        hearthsList.add(hearth1);

        //ARRANGE

        Mockito.when(firestationController.hearthListByFirestation(1)).thenReturn(hearthsList);
        Mockito.when((firestationService.findPeopleByStation(1))).thenReturn(hearthsList);


        //ACT
        List<HearthDTO> relatives = firestationController.hearthListByFirestation(1);


        //ASSERT

        assertEquals(hearthsList,relatives);
    }
}