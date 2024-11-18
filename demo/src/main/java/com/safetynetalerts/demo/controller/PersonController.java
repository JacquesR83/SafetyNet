
package com.safetynetalerts.demo.controller;

import com.safetynetalerts.demo.model.Person;
import com.safetynetalerts.demo.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {"/api/safetynet/"})
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(path = "{emailsByCity}")
    public List<String> emailsList(@RequestParam (name="city") String city) {
        return this.personService.findAllEmailsByCity(city);
    }


}
