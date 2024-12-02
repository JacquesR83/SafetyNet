package com.safetynetalerts.demo.service;

import com.safetynetalerts.demo.model.Firestation;
import com.safetynetalerts.demo.model.MedicalRecord;
import com.safetynetalerts.demo.model.Person;
import com.safetynetalerts.demo.repository.FirestationRepository;
import com.safetynetalerts.demo.repository.MedicalRecordsRepository;
import com.safetynetalerts.demo.repository.PersonRepository;
import com.safetynetalerts.demo.service.dto.FirestationDTO;
import com.safetynetalerts.demo.service.dto.FirestationPersonDTO;
import com.safetynetalerts.demo.service.dto.HearthDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FirestationServiceTest {


    @MockBean
    FirestationRepository firestationRepository;

    @MockBean
    PersonRepository personRepository;

    @MockBean
    MedicalRecordsRepository medicalRecordsRepository;

    @Autowired
    FirestationService firestationService;


    List<Person> persons;
    List<Firestation> firestations;
    List <MedicalRecord> medicalRecords;

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

        Person person3 = new Person();
        person3.setFirstName("Santa");
        person3.setLastName("Doe");
        person3.setPhone("243-456-7891");
        person3.setAddress("123 rue daumesnil");



        persons = new ArrayList<>();
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);


        MedicalRecord medicalRecords1 = new MedicalRecord("John",
                "Doe",
                "03/06/1984",
                new String[]{"[aznol:350mg, hydrapermazol:100mg]"},
                new String[]{"[nillacilan]"}
        );

        MedicalRecord medicalRecords2 = new MedicalRecord("Jane",
                "Smith",
                "03/04/1994",
                new String[]{"[]"},
                new String[]{"[flowers]"}
        );

        MedicalRecord medicalRecords3 = new MedicalRecord("Santa",
                "Doe",
                "06/06/2017",
                new String[]{"[]"},
                new String[]{"[]"}
        );

        medicalRecords = new ArrayList<>();
        medicalRecords.add(medicalRecords1);
        medicalRecords.add(medicalRecords2);
        medicalRecords.add(medicalRecords3);



        Mockito.when(firestationRepository.findAllFireStationsByNumber(1)).thenReturn(firestations);
        Mockito.when(personRepository.findAllPersons()).thenReturn(persons);
        Mockito.when(firestationRepository.findAllFireStations()).thenReturn(firestations);
        Mockito.when((medicalRecordsRepository.findAllMedicalRecords())).thenReturn(medicalRecords);


    }

    @Test
    void findPhoneNumbersByStationNumber() {
        List<String> phoneNumbers = new ArrayList<>();


        //ACT
        phoneNumbers = firestationService.findPhoneNumbersByStationNumber(1);

        //ASSERT

        assertEquals(2,phoneNumbers.size());
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

        //ARRANGE
        FirestationDTO result = new FirestationDTO();
        List <FirestationPersonDTO> people = new ArrayList<>();
        result.setPeople(people);

        //ACT
        result = firestationService.findPeopleCountByFirestation(1);


        //ASSERT
        assertEquals(1,result.getAdultsCount());
        assertEquals(1,result.getChildrenCount());

    }

    @Test
    void findPeopleByStation() {
        //ARRANGE
        List <HearthDTO> result = new ArrayList<>();
        Map<String, HearthDTO> addressMap = new HashMap<>();

        //ACT
        result = firestationService.findPeopleByStation(1);


        //ASSERT
        assertEquals(1,result.size());


    }

    @Test
    void getFirestation() {

        // ARRANGE


        // ACT
        Firestation result = firestationService.getFirestation("123 rue daumesnil");


        // ASSERT
        assertEquals("1",result.getStation());
    }

    @Test
    void addFirestation() {

        Firestation newFirestation = new Firestation(
                "1 rue de la paix",
                "1"
        );

        //ACT
        Firestation result = firestationService.addFirestation(newFirestation);

        //ASSERT
       assertNotNull(result);

    }

    @Test
    void deleteFirestation() {
    }

    @Test
    void updateFirestation() {
    }
}