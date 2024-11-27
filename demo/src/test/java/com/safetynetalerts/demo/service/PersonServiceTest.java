package com.safetynetalerts.demo.service;

import com.safetynetalerts.demo.model.Firestation;
import com.safetynetalerts.demo.model.MedicalRecord;
import com.safetynetalerts.demo.model.Person;
import com.safetynetalerts.demo.repository.DataHandler;
import com.safetynetalerts.demo.repository.FirestationRepository;
import com.safetynetalerts.demo.repository.MedicalRecordsRepository;
import com.safetynetalerts.demo.repository.PersonRepository;
import com.safetynetalerts.demo.service.dto.FireDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PersonServiceTest {

    @MockBean
    private PersonRepository personRepository;
    @MockBean
    private FirestationRepository firestationRepository;
    @MockBean
    private MedicalRecordsRepository medicalRecordsRepository;

    @Autowired
    private PersonService personService;

    @BeforeEach
    void setUp() {
        List<Person> personListTest = Arrays.asList(
                new Person("John", "Doe", "2 rue de la tour", "Paris", "75", "6575892", "john.doe@gmail.com"),
                new Person("Frank", "Doe", "2 rue de la tour", "Paris", "75", "6575893", "frank.doe@gmail.com"),
                new Person("Alice", "Smith", "5 rue de la paix", "Paris", "75", "435563", "alice.smith@gmail.com"),
                new Person("Bob", "Johnson", "10 rue de la mer", "Lyon", "69", "876543", "bob.johnson@gmail.com")
        );

        List<Firestation> firestations = Arrays.asList(
                new Firestation("2 rue de la tour", "1"),
                new Firestation("5 rue de la paix", "1"),
                new Firestation("10 rue de la mer", "2")
        );

        List<MedicalRecord> medicalRecords = Arrays.asList(
                new MedicalRecord("John", "Doe", "04/06/1965",
                        new String[] {"aznol:200mg", "puree 100g"},
                        new String[] {"nillacilan", "pollens"}
                ),
                new MedicalRecord("Frank", "Doe", "09/06/1982",
                        new String[] {"aznol:200mg", "puree 100g"},  // Liste vide pour les médicaments et allergènes
                        new String[] {}
                ),
                new MedicalRecord("Alice", "Smith", "07/01/1998",
                        new String[] {"aznol:200mg"},
                        new String[] {"nillacilan"}
                ),
                new MedicalRecord("Bob", "Johnson", "02/03/1955",
                        new String[] {},  // Liste vide pour les médicaments
                        new String[] {"cats"}
                )
        );

        Mockito.when(personRepository.findAllPersons()).thenReturn(personListTest);

        Mockito.when(personRepository.findAllPersonsByAddress("2 rue de la tour")).thenReturn(Arrays.asList(
                new Person("John", "Doe", "2 rue de la tour", "Paris", "75", "6575892", "john.doe@gmail.com"),
                new Person("Frank", "Doe", "2 rue de la tour", "Paris", "75", "6575893", "frank.doe@gmail.com")
        ));
        Mockito.when(firestationRepository.findFireStationNumberByAddress("2 rue de la tour")).thenReturn(firestations.get(0));
        Mockito.when(medicalRecordsRepository.findAllMedicalRecords()).thenReturn(medicalRecords);


    }

    @Test
    void findAllEmailsByCity() {
        //Arrange (All In BeforeEach)
        //ACT
        List<String> emails = personService.findAllEmailsByCity("Paris");

        //ASSERT
        assert(emails.contains("john.doe@gmail.com"));

    }

    @Test
    void findAllEmailsByCityNot() {
        //Arrange (All In BeforeEach)
        //ACT
        List<String> emails = personService.findAllEmailsByCity("Paris");

        //ASSERT
        assertFalse(emails.contains("bob.johnson@gmail.com"));

    }

    @Test
    void findAllPersonsWithMedicalRecords() {
        //ASSERT

        //ACT
        List<FireDTO> fireDTOs = personService.findAllPersonsWithMedicalRecords("2 rue de la tour");


        //ASSERT
        assertNotNull(fireDTOs);
        assertEquals(2, fireDTOs.size());

        FireDTO johnFireDTO = fireDTOs.get(0);
        assertEquals("Doe", johnFireDTO.getLastName());  // Vérifier le nom de famille de John
        assertEquals("6575892", johnFireDTO.getPhoneNumber());  // Vérifier le numéro de téléphone de John
        assertEquals("1", johnFireDTO.getFirestation());  // Vérifier la station de pompier
        assertTrue(Arrays.asList("aznol:200mg", "puree 100g").containsAll(Arrays.asList(johnFireDTO.getMedications())));  // Vérifier les médicaments de John
        assertTrue(Arrays.asList("nillacilan", "pollens").containsAll(Arrays.asList(johnFireDTO.getAllergies())));  // Vérifier les allergies de John
        // Calcul de l'âge pour John, né le 04/06/1965
        assertEquals("59", johnFireDTO.getAge());  // Âge calculé basé sur la date actuelle




    }

    @Test
    void findAllPersonsWithMedicalRecordsNot() {
        //ASSERT

        //ACT
        List<FireDTO> fireDTOs = personService.findAllPersonsWithMedicalRecords("10 rue de la mer");


        //ASSERT
        assertNotEquals(2,fireDTOs.size());




    }

    @Test
    void medicalRecordsContainsPerson() {
        //ARRANGE
        Person personToFind = personRepository.findAllPersons().get(0);
        List<MedicalRecord> medicalRecords = medicalRecordsRepository.findAllMedicalRecords();

        //ACT
        MedicalRecord result = personService.medicalRecordsContainsPerson(medicalRecords, personToFind);

        //ASSERT
        assertNotNull(medicalRecords);
        assertEquals( "John" , result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertTrue(Arrays.asList(result.getMedications()).contains("aznol:200mg"));
        assertTrue(Arrays.asList(result.getAllergies()).contains("nillacilan"));

    }

    @Test
    void medicalRecordsContainsPersonNotFound() {
        // ARRANGE
        // Création d'une personne qui n'a pas de dossier médical
        Person personNotFound = new Person("Rosie", "Buds", "10 rue de la mer", "Colmar", "67", "887643", "rosie.buds@gmail.com");
        List<MedicalRecord> medicalRecords = medicalRecordsRepository.findAllMedicalRecords();

        // ACT
        // Appeler la méthode avec une personne qui n'existe pas dans les dossiers médicaux
        MedicalRecord result = personService.medicalRecordsContainsPerson(medicalRecords, personNotFound);

        // ASSERT
        assertNull(result);  // Vérifier que le résultat est bien null, car cette personne n'a pas de dossier médical
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