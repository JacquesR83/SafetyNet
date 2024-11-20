
package com.safetynetalerts.demo.controller;

import com.safetynetalerts.demo.model.Firestation;
import com.safetynetalerts.demo.model.Person;
import com.safetynetalerts.demo.repository.FirestationRepository;
import com.safetynetalerts.demo.service.PersonService;
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

    @GetMapping(path = "{communityEmail}")
    public List<String> emailsList(@RequestParam (name="city") String city) {
        return this.personService.findAllEmailsByCity(city);
    }

    @GetMapping(path ={"fire"})
    public List<FireDTO> listOfPersonsByAdress(@RequestParam (name = "address") String address) {
        return this.personService.findAllPersonsWithMedicalRecords(address);
    }

    @GetMapping(path={"personInfo"})
    public PersonDTO PersonInfo(@RequestParam (name="firstName") String firstName, @RequestParam (name="lastName") String lastName) {
        return this.personService.personInformationsAndMedicalRecords(firstName,lastName);
    }


}
