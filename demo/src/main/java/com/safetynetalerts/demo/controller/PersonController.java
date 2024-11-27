
package com.safetynetalerts.demo.controller;

import com.safetynetalerts.demo.model.Person;
import com.safetynetalerts.demo.service.PersonService;
import com.safetynetalerts.demo.service.dto.ChildDTO;
import com.safetynetalerts.demo.service.dto.FireDTO;
import com.safetynetalerts.demo.service.dto.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(path = "person/get")
    public List <Person> getPeople (@RequestParam (name = "lastName") String lastName){
        return personService.findAllPersonsWithLastName(lastName);
    }

    @PostMapping(path = "person/add")
    public Person addPerson(@RequestBody Person person){
        return personService.addPerson(person);
    }

    @DeleteMapping(path = "person/delete")
    public String deletePerson(@RequestParam (name = "firstName") String firstName, @RequestParam (name = "lastName") String lastName){
        personService.deletePersonByFirstNameAndLastName(firstName, lastName);
        return ("Personne effacée");
    }

    @PutMapping(path = "person/update")
    public Person updatePerson (@RequestParam (name = "firstName") String firstName, @RequestParam (name = "lastName") String lastName, @RequestBody Person person){
        return (personService.updatePerson(firstName, lastName, person));
    }
























    //URLS
    @GetMapping(path = {"communityEmail"})
    public List<String> emailsList(@RequestParam (name="city") String city) {
        return this.personService.findAllEmailsByCity(city);
    }

    @GetMapping(path ={"fire"})
    public List<FireDTO> listOfPersonsByAdress(@RequestParam (name = "address") String address) {
        return this.personService.findAllPersonsWithMedicalRecords(address);
    }

    @GetMapping(path={"personInfo"})
    public List<PersonDTO> PersonInfoAndFamily(@RequestParam (name="firstName") String firstName, @RequestParam (name="lastName") String lastName) {
        return this.personService.familyInformation(firstName,lastName);
    }

    @GetMapping(path ={"childAlert"})
    public List<ChildDTO> ChildAlertFamily (@RequestParam (name="address") String address) {
        return this.personService.childListAtThisAddress(address);
    }



}
