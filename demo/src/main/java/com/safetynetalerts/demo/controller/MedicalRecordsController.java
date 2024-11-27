package com.safetynetalerts.demo.controller;

import com.safetynetalerts.demo.model.MedicalRecord;
import com.safetynetalerts.demo.model.Person;
import com.safetynetalerts.demo.service.MedicalRecordsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class MedicalRecordsController {

    MedicalRecordsService medicalRecordsService;

    public MedicalRecordsController(MedicalRecordsService medicalRecordsService) {
        this.medicalRecordsService = medicalRecordsService;
    }

    @GetMapping(path = "medicalRecord/get")
    public MedicalRecord getMedicalRecords (@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName){
        return medicalRecordsService.findAllMedicalRecordsByName(firstName,lastName);
    }

    @PostMapping(path = "medicalRecord/add")
    public void addPerson(@RequestBody MedicalRecord medicalRecord){
        medicalRecordsService.addMedicalRecord(medicalRecord);
    }

    @DeleteMapping(path = "medicalRecord/delete")
    public void deletePerson(@RequestParam (name = "firstName") String firstName, @RequestParam (name = "lastName") String lastName){
        medicalRecordsService.deleteMedicalRecord(firstName, lastName);
    }

    @PutMapping(path = "medicalRecord/update")
    public void updatePerson (@RequestParam (name = "firstName") String firstName, @RequestParam (name = "lastName") String lastName, @RequestBody MedicalRecord medicalRecord){
        medicalRecordsService.updateMedicalRecord(firstName, lastName, medicalRecord);
    }


}
