
package com.safetynetalerts.demo.controller;

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
    public List<PersonDTO> PersonInfoAndFamily(@RequestParam (name="firstName") String firstName, @RequestParam (name="lastName") String lastName) {
        return this.personService.familyInformation(firstName,lastName);
    }

//    @GetMapping(path = "{childAlert}")
//    public List<PersonDTO> childAlerts(@RequestParam (name= "address") String address){
//        return this.personService.childListAtThisAddress(address);
//    }

}
