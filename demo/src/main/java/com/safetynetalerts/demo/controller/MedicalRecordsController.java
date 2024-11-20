package com.safetynetalerts.demo.controller;

import com.safetynetalerts.demo.service.MedicalRecordsService;

public class MedicalRecordsController {
    MedicalRecordsService medicalRecordsService;

    public MedicalRecordsController(MedicalRecordsService medicalRecordsService) {
        this.medicalRecordsService = medicalRecordsService;
    }


}
