package com.safetynetalerts.demo.controller;

import com.safetynetalerts.demo.service.FirestationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FirestationController {

    private final FirestationService firestationService;

    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    @GetMapping(path = "phoneAlert")
    public List<String> phoneNumbersList(@RequestParam(name = "firestation") int number) {
        return this.firestationService.findPhoneNumbersByStationNumber(number);
    }

//    @GetMapping(path = "firestation")
//    public List<FireStationPersonDTO> peopleCountByFirestation(@RequestParam (name = "firestation") int number){
//        return this.firestationService.findPeopleCountByFirestation(number);
//    }


}
