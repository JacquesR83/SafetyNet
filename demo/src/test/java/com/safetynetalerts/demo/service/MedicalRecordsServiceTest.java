package com.safetynetalerts.demo.service;

import com.safetynetalerts.demo.model.Data;
import com.safetynetalerts.demo.model.MedicalRecord;
import com.safetynetalerts.demo.model.Person;
import com.safetynetalerts.demo.repository.DataHandler;
import com.safetynetalerts.demo.repository.MedicalRecordsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class MedicalRecordsServiceTest {

    @MockBean
    private DataHandler dataHandler;

    @MockBean
    private MedicalRecordsRepository medicalRecordsRepository;

    @Autowired
    private MedicalRecordsService medicalRecordsService;


    @BeforeEach
    void setUp() {


    }

    @Test
    void findMedicalRecordByNameTest() {

        //ARRANGE
        List<MedicalRecord> medicalRecords = new ArrayList<>( Arrays.asList(
                new MedicalRecord("John", "Doe", "04/06/1965",
                        new String[] {"aznol:200mg", "puree 100g"},
                        new String[] {"nillacilan", "pollens"}
                ),
                new MedicalRecord("Frank", "Doe", "09/06/2018",
                        new String[] {"aznol:200mg", "puree 100g"},  // Liste vide pour les médicaments et allergènes
                        new String[] {}
                ),
                new MedicalRecord("Alice", "Smith", "07/01/1998",
                        new String[] {"aznol:200mg"},
                        new String[] {"nillacilan"}
                ),
                new MedicalRecord("Bob", "Johnson", "02/03/1955",
                        new String[] {},  // Liste vide pour les médicaments
                        new String[] {"cats"}
                )

        ));
        when(medicalRecordsRepository.findAllMedicalRecords()).thenReturn(medicalRecords);

        //ACT

        MedicalRecord mr = medicalRecordsService.findMedicalRecordByName("John", "Doe");

        //ASSERT

        assertNotNull(mr);

        verify(medicalRecordsRepository, times(1)).findAllMedicalRecords();
    }

    @Test
    void findMedicalRecordByNameNullTest() {

        //ARRANGE

        //ACT

        MedicalRecord mr = medicalRecordsService.findMedicalRecordByName("John", "Travolta");

        //ASSERT

        assertNull(mr);

    }

    @Test
    void addMedicalRecord() {

        //ARRANGE
        MedicalRecord newMedicalRecord = new MedicalRecord(
                "John", "Doe", "04/06/1965",
                new String[] {"aznol:200mg", "puree 100g"},
                new String[] {"nillacilan", "pollens"}
        );

        //ACT

        MedicalRecord result = medicalRecordsService.addMedicalRecord(newMedicalRecord);

        //Assert
        assertNotNull(result);

    }

    @Test
    void deleteMedicalRecord() {

        //ARRANGE


        //ACT
        medicalRecordsService.deleteMedicalRecord("John","Doe");

        //ASSERT
        Mockito.verify(medicalRecordsRepository, Mockito.times(1)).delete("John", "Doe");

    }

    @Test
    void updateMedicalRecord() {

        MedicalRecord newMedicalRecord = new MedicalRecord(
                "John", "Doe", "04/06/1965",
                new String[] {"aznol:200mg", "puree 100g"},
                new String[] {"nillacilan", "pollens"}
        );

        MedicalRecord updatedMedicalRecord = new MedicalRecord(
                "John", "Doe", "04/06/1965",
                new String[] {},
                new String[] {"carbon14"}
        );

        when(medicalRecordsRepository.findAllMedicalRecords()).thenReturn(Arrays.asList(newMedicalRecord));
        when(medicalRecordsRepository.save(any(MedicalRecord.class))).thenReturn(updatedMedicalRecord);

        //ACT
        MedicalRecord result = medicalRecordsService.updateMedicalRecord(newMedicalRecord.getFirstName(), newMedicalRecord.getLastName(), updatedMedicalRecord);

        //ASSERT
        assertEquals(updatedMedicalRecord.getAllergies(), result.getAllergies());
    }
}