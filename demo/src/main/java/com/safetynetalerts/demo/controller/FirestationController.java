package com.safetynetalerts.demo.controller;

import com.safetynetalerts.demo.model.Firestation;
import com.safetynetalerts.demo.model.Person;
import com.safetynetalerts.demo.service.FirestationService;
import com.safetynetalerts.demo.service.dto.FirestationDTO;
import com.safetynetalerts.demo.service.dto.HearthDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FirestationController {

    private final FirestationService firestationService;

    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    // CRUD

    @GetMapping(path= "firestation/get")
    public Firestation getFirestation (@RequestParam (name = "address") String address) {
        if(firestationService.getFirestation(address) != null){
            return firestationService.getFirestation(address);
        } else {
            throw new IllegalArgumentException ("No firestation at this address"); // Ne fonctionne pas
        }
    }

    @PostMapping(path = "firestation/add")
    public void addFirestation(@RequestBody Firestation firestation){
        firestationService.addFirestation(firestation);
    }

    @DeleteMapping(path = "firestation/delete")
    public void deleteFirestation(@RequestParam (name = "number") String number){
            firestationService.deleteFirestation(number);
    }


    @PutMapping(path = "firestation/update")
    public void updateFirestation (@RequestParam (name = "address") String address, @RequestBody Firestation firestation){
        firestationService.updateFirestation(address,firestation);
    }















    //URLS
    @GetMapping(path = "phoneAlert")
    public List<String> phoneNumbersList(@RequestParam(name = "firestation") int number) {
        return this.firestationService.findPhoneNumbersByStationNumber(number);
    }

    @GetMapping(path = "firestation")
    public FirestationDTO personListByFirestation(@RequestParam (name = "stationNumber") int number){
        return this.firestationService.findPeopleCountByFirestation(number);
    }

    @GetMapping(path = "flood")
    public List<HearthDTO> hearthListByFirestation(@RequestParam (name = "station") int number) {
        return this.firestationService.findPeopleByStation(number);
    }

//    @GetMapping(path = "test")
//    public String test(@RequestParam (name = "address") String address){
//        return this.firestationService.fireStationNumberAtThisAddress(address);
//    }



}
