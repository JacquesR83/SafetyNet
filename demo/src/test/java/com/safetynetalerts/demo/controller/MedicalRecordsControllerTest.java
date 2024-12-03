package com.safetynetalerts.demo.controller;

import com.safetynetalerts.demo.model.MedicalRecord;
import com.safetynetalerts.demo.service.MedicalRecordsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MedicalRecordsControllerTest {

    @Autowired
    MedicalRecordsController medicalRecordsController;

    @MockBean
    MedicalRecordsService medicalRecordsService;

    MedicalRecord personMedicalRecord = new MedicalRecord();

    @BeforeEach
    void setUp() {


        personMedicalRecord.setFirstName("John");
        personMedicalRecord.setLastName("Doe");
        personMedicalRecord.setBirthdate("01/01/2000");
        personMedicalRecord.setMedications(new String[]{"prozac :20 mg"});
        personMedicalRecord.setAllergies(new String[]{"bees"});

        Mockito.when(medicalRecordsService.findMedicalRecordByName("John", "Doe")).thenReturn(personMedicalRecord);
    }


    @Test
    void getMedicalRecords() {
        //ARRANGE


        //ACT
        MedicalRecord records = medicalRecordsController.getMedicalRecords("John", "Doe");

        //ASSERT
        assertEquals(personMedicalRecord, records);

    }

    @Test
    void addPerson() {

        //ACT
        medicalRecordsController.addPerson(personMedicalRecord);

        //ASSERT
        Mockito.verify(medicalRecordsService, Mockito.times(1)).addMedicalRecord(personMedicalRecord);

    }

    @Test
    void deletePerson() {

        //ACT
        medicalRecordsController.deletePerson("John", "Doe");

        //ASSERT
        Mockito.verify(medicalRecordsService, Mockito.times(1)).deleteMedicalRecord("John", "Doe");

    }

    @Test
    void updatePerson() {

        //ARRANGE

        //ACT
        medicalRecordsController.updatePerson("John", "Doe", personMedicalRecord);

        //ASSERT
        Mockito.verify(medicalRecordsService, Mockito.times(1)).updateMedicalRecord("John", "Doe", personMedicalRecord);

    }
}