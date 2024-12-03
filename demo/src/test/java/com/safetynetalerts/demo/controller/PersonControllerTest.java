package com.safetynetalerts.demo.controller;

import com.safetynetalerts.demo.model.Firestation;
import com.safetynetalerts.demo.model.Person;
import com.safetynetalerts.demo.service.PersonService;
import com.safetynetalerts.demo.service.dto.ChildDTO;
import com.safetynetalerts.demo.service.dto.FireDTO;
import com.safetynetalerts.demo.service.dto.FirestationPersonDTO;
import com.safetynetalerts.demo.service.dto.PersonDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PersonControllerTest {

    @MockBean
    PersonService personService;

    @Autowired
    PersonController personController;

    List<Person> people = new ArrayList<>();

    @BeforeEach
    void setUp() {

        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        person1.setAddress("123 rue de la paix");
        person1.setCity("Marseille");
        person1.setZip("13000");
        person1.setPhone("514-52-2040");
        person1.setEmail("johndoe@gmail.com");

        Person person2 = new Person();
        person2.setFirstName("Santa");
        person2.setLastName("Doe");
        person2.setAddress("123 rue de la paix");
        person2.setCity("Marseille");
        person2.setZip("13000");
        person2.setPhone("514-52-2045");
        person2.setEmail("santadoe@gmail.com");

        Person person3 = new Person();
        person3.setFirstName("Jane");
        person3.setLastName("Smith");
        person3.setAddress("456 rue des mines");
        person3.setCity("Marseille");
        person3.setZip("13000");
        person3.setPhone("514-999-9999");
        person3.setEmail("janesmith@gmail.com");

        people.add(person1);
        people.add(person2);
        people.add(person3);

    }

    @Test
    void getPeople() {

        //ARRANGE
        List<Person> peopleGet = new ArrayList<>();
        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        person1.setAddress("123 rue de la paix");

        Person person2 = new Person();
        person2.setFirstName("Santa");
        person2.setLastName("Doe");
        person2.setAddress("123 rue de la paix");

//        Person person3 = new Person();
//        person3.setFirstName("Jane");
//        person3.setLastName("Smith");
//        person3.setAddress("456 rue des mines");

        peopleGet.add(person1);
        peopleGet.add(person2);
//        people.add(person3);

        Mockito.when(personService.findAllPersonsWithLastName("Doe")).thenReturn(peopleGet);

        //ACT
        List<Person> result = personController.getPeople ("Doe");

        //ASSERT
        assertEquals(2,result.size());
    }

    @Test
    void addPerson() {
        //ARRANGE
        Person person = new Person();
        Mockito.when(personService.addPerson(person)).thenReturn(person);

        //ACT
        Person newPerson = personController.addPerson(person);

        //ASSERT
        assertEquals(person,newPerson);
    }

    @Test
    void deletePerson() {

        //ACT
        String result = personController.deletePerson("John", "Doe");

        //ASSERT
        Mockito.verify(personService, Mockito.times(1)).deletePersonByFirstNameAndLastName("John", "Doe");
    }

    @Test
    void updatePerson() {

        Person updatedPerson = new Person();
        updatedPerson.setFirstName("John");
        updatedPerson.setLastName("Doe");
        updatedPerson.setAddress("456 rue de la paix");

        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAddress("123 rue de la paix");

        Mockito.when(personService.updatePerson("John", "Doe", updatedPerson)).thenReturn(updatedPerson);

        //ACT
        Person result = personController.updatePerson("John", "Doe", updatedPerson);


        //ASSERT
        assertNotEquals(person.getAddress(),result.getAddress());
    }

    @Test
    void emailsList() {
        //ARRANGE
        List<String> emails = new ArrayList<>();

        String email1 = people.get(0).getEmail();
        String email2 = people.get(1).getEmail();
        String email3 = people.get(2).getEmail();

        emails.add(email1);
        emails.add(email2);
        emails.add(email3);

        Mockito.when(personService.findAllEmailsByCity("Marseille")).thenReturn(emails);

        //ACT
        List <String> foundEmails = personController.emailsList("Marseille");

        //ASSERT

        assertEquals(3,foundEmails.size());
    }

    @Test
    void listOfPersonsByAdress() {
        //ARRANGE
        FireDTO fireDTO1 = new FireDTO();
        FireDTO fireDTO2 = new FireDTO();

        List <FireDTO> chosenPeople = new ArrayList<>();

        fireDTO1.setLastName("Doe");
        fireDTO1.setFirestation("1");
        fireDTO1.setAge("20");
        fireDTO1.setPhoneNumber("514-6652-2040");
        fireDTO1.setMedications(new String[]{"prozac"});
        fireDTO1.setAllergies(new String[]{"chihuahuas"});

        fireDTO2.setLastName("Doe");
        fireDTO2.setFirestation("1");
        fireDTO2.setAge("5");
        fireDTO2.setPhoneNumber("514-6652-2045");
        fireDTO2.setMedications(new String[]{""});
        fireDTO2.setAllergies(new String[]{"peanut butter"});

        chosenPeople.add(fireDTO1);
        chosenPeople.add(fireDTO2);


        Mockito.when(personService.findAllPersonsWithMedicalRecords("123 rue de la paix")).thenReturn(chosenPeople);

        //ACT
        List<FireDTO> peopleByAddress = personController.listOfPersonsByAdress("123 rue de la paix");

        //ASSERT
        assertEquals(chosenPeople.size(),peopleByAddress.size());

    }

    @Test
    void personInfoAndFamily() {

        //ASSERT
        List <PersonDTO> peopleDTO = new ArrayList<>();

        PersonDTO personDTO1 = new PersonDTO();
        PersonDTO personDTO2 = new PersonDTO();

        personDTO1.setLastName("Doe");
        personDTO1.setEmail("johndoe@gmail.com");
        personDTO1.setAddress("123 rue de la paix");
        personDTO1.setAge("20");
        personDTO1.setMedications(new String[]{"prozac"});
        personDTO1.setAllergies(new String[]{"chihuahuas"});

        personDTO2.setLastName("Doe");
        personDTO2.setEmail("santadoe@gmail.com");
        personDTO2.setAddress("123 rue de la paix");
        personDTO2.setAge("5");
        personDTO2.setMedications(new String[]{""});
        personDTO2.setAllergies(new String[]{"peanut butter"});

        peopleDTO.add(personDTO1);
        peopleDTO.add(personDTO2);

        Mockito.when(personService.familyInformation("John", "Doe")).thenReturn(peopleDTO);

        //ACT
        List<PersonDTO> infoAndRelatives =  personController.PersonInfoAndFamily("John","Doe");

        //ASSERT

        assertEquals(peopleDTO.size(),infoAndRelatives.size());
        assertEquals(peopleDTO.get(0).getAge(), infoAndRelatives.get(0).getAge());

    }

    @Test
    void childAlertFamily() {

        //ARRANGE
        List<ChildDTO> childDTOList = new ArrayList<>();
        List<Person> familyMembers = new ArrayList<>();

        Person member1 = people.get(0);
        Person member2 = people.get(1);

        familyMembers.add(member1);
        familyMembers.add(member2);

        ChildDTO childDTO1 = new ChildDTO();
        ChildDTO childDTO2 = new ChildDTO();

        childDTO1.setFirstName("John");
        childDTO1.setLastName("Doe");
        childDTO1.setAge(20);
        childDTO1.setFamilyMembers(familyMembers);

        childDTO2.setFirstName("Santa");
        childDTO2.setLastName("Doe");
        childDTO2.setAge(5);
        childDTO2.setFamilyMembers(familyMembers);

        childDTOList.add(childDTO1);
        childDTOList.add(childDTO2);

        Mockito.when(personService.childListAtThisAddress("123 rue de la paix")).thenReturn(childDTOList);

        //ACT
        List<ChildDTO> childrenAndFamily = personController.ChildAlertFamily ("123 rue de la paix");

        //ASSERT
        assertEquals(childDTOList.size(),childrenAndFamily.size());
    }
}