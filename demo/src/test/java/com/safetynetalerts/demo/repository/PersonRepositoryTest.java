package com.safetynetalerts.demo.repository;

import com.safetynetalerts.demo.model.Data;
import com.safetynetalerts.demo.model.Person;
import com.safetynetalerts.demo.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PersonRepositoryTest {

    @MockBean
    DataHandler dataHandler;

    @Autowired
    PersonRepository personRepository;

    List <Person> people = new ArrayList<>();

    @BeforeEach
            void setup(){

        Person person1 = new Person();
        Person person2 = new Person();
        Person person3 = new Person();
        people.add(person1);
        people.add(person2);
        people.add(person3);

        person1.setFirstName("John");
        person1.setLastName("Doe");
        person1.setAddress("123 rue de la paix");
        person1.setCity("Paris");
        person1.setZip("75");
        person1.setPhone("0102030405");
        person1.setEmail("johndoe@gmail.com");

        person2.setFirstName("Santa");
        person2.setLastName("Doe");
        person2.setAddress("123 rue de la paix");
        person2.setCity("Paris");
        person2.setZip("75");
        person2.setPhone("0102030499");
        person2.setEmail("santadoe@gmail.com");

        person3.setFirstName("Jane");
        person3.setLastName("Smith");
        person3.setAddress("456 rue de la paix");
        person3.setCity("Paris");
        person3.setZip("75");
        person3.setPhone("02030405");
        person3.setEmail("janesmith@gmail.com");

        //IMPORTANT DE MOCKER getData()
        // puis ensuite seulement
        // getPersons()
        Data mockData = Mockito.mock(Data.class);
        Mockito.when(dataHandler.getData()).thenReturn(mockData);
        Mockito.when(mockData.getPersons()).thenReturn(people);

    }


    @Test
    void findAllPersons() {

        //ARRANGE

        //ACT

        List <Person> foundPeople = personRepository.findAllPersons();

        //ASSERT
        assertEquals(people,foundPeople);
    }

    @Test
    void findAllPersonsByAddress() {

        //ARRANGE

        //ACT
        List<Person> foundpeople = personRepository.findAllPersonsByAddress("123 rue de la paix");

        //ASSERT
        assertEquals(2,foundpeople.size());
    }

    @Test
    void findPersonByFirstNameAndLastName() {
        //ARRANGE


        //ACT
        Person result = personRepository.findPersonByFirstNameAndLastName("John", "Doe");

        //ASSERT
        assertEquals(people.get(0).getFirstName(), result.getFirstName());
    }

    @Test
    void findAllPersonsWithLastName() {

        //ARRANGE


        //ACT
        List<Person> guysLastName = personRepository.findAllPersonsWithLastName("Doe");

        //ASSERT
        assertEquals(2,guysLastName.size());
    }

    @Test
    void addToPersonList() {

        //ARRANGE
        Person guy = new Person();
        guy.setFirstName("John");
        guy.setLastName("Smith");
        guy.setAddress("456 rue de la paix");
        guy.setCity("Paris");
        guy.setZip("75");
        guy.setPhone("02030405");
        guy.setEmail("johnsmith@gmail.com");

        //ACT
        Person newPerson = personRepository.addToPersonList(guy);

        //ASSERT
        assertEquals(newPerson,guy);
    }

    @Test
    void addToPersonListException() throws IOException {

        Person person1 = new Person();
        //ACT
        Mockito.doThrow(new IOException("Save failed")).when(dataHandler).save();

        //ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            personRepository.addToPersonList(person1);
        });

        // Vérifie que l'exception contient le bon message
        assertEquals("java.io.IOException: Save failed", exception.getMessage());

        Mockito.verify(dataHandler, Mockito.times(1)).save();
    }


    @Test
    void deletePerson(){

        //ARRANGE

        //ACT
        personRepository.deletePerson("John", "Doe");

        //ASSERT
        try {
            Mockito.verify(dataHandler, Mockito.times(1)).save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

// EXEMPLE DE ASSERT POUR EXCEPTION
    @Test
    void deletePersonException() throws IOException {

        //ARRANGE

        //ACT
        Mockito.doThrow(new IOException("Save failed")).when(dataHandler).save();

        //ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            personRepository.deletePerson("John", "Doe");
        });

        // Vérifie que l'exception contient le bon message
        assertEquals("java.io.IOException: Save failed", exception.getMessage());

        Mockito.verify(dataHandler, Mockito.times(1)).save();
    }

    @Test
    void existsTrue() {
        //ARRANGE
        Data mockData = Mockito.mock(Data.class);
        Mockito.when(dataHandler.getData()).thenReturn(mockData);
        Mockito.when(mockData.getPersons()).thenReturn(people);

        Person person = new Person();
        person.setFirstName("Larry");
        person.setLastName("Coper");

        people.add(person);

        //ACT
        boolean isHere = personRepository.exists("Larry", "Coper");

        //ASSERT
        assertEquals (true, isHere);
    }

    @Test
    void existsFalse() {
        //ARRANGE
        Data mockData = Mockito.mock(Data.class);
        Mockito.when(dataHandler.getData()).thenReturn(mockData);
        Mockito.when(mockData.getPersons()).thenReturn(people);

        //ACT
        boolean isHere = personRepository.exists("Larry", "Coper");

        //ASSERT
        assertEquals (false, isHere);
    }
}