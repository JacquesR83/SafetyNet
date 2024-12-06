package com.safetynetalerts.demo.repository;

import com.safetynetalerts.demo.model.Data;
import com.safetynetalerts.demo.model.MedicalRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


@SpringBootTest
class MedicalRecordsRepositoryTest {

    @Autowired
    MedicalRecordsRepository medicalRecordsRepository;

    @MockBean
    DataHandler dataHandler;

    List<MedicalRecord> medicalRecordsTemp = new ArrayList<>();

    @BeforeEach
    void setUp() {

        MedicalRecord medicalRecordGuy = new MedicalRecord();
        medicalRecordGuy.setFirstName("John");
        medicalRecordGuy.setLastName("Doe");
        medicalRecordGuy.setBirthdate("01/01/2000");
        medicalRecordGuy.setMedications(new String[]{"paprika"});
        medicalRecordGuy.setAllergies(new String[]{"shellfish"});
        medicalRecordsTemp.add(medicalRecordGuy);

        Data mockData = Mockito.mock(Data.class);
        Mockito.when(dataHandler.getData()).thenReturn(mockData);
        Mockito.when(mockData.getMedicalrecords()).thenReturn(medicalRecordsTemp);
    }

    @Test
    void findAllMedicalRecords() {
        //ARRANGE

        //ACT
        List<MedicalRecord> medicalRecords = medicalRecordsRepository.findAllMedicalRecords();

        //ASSERT
        Assertions.assertEquals(medicalRecordsTemp.size(), medicalRecords.size());

    }

    @Test
    void save() {

        //ARRANGE
        MedicalRecord thatGuy = new MedicalRecord();
        thatGuy.setFirstName("John");
        thatGuy.setLastName("Doe");
        thatGuy.setBirthdate("01/01/2000");
        thatGuy.setMedications(new String[]{"paprika"});
        thatGuy.setAllergies(new String[]{"shellfish"});


        //ACT
        MedicalRecord newMedicalRecord = medicalRecordsRepository.save(thatGuy);

        //ASSERT
        Assertions.assertEquals(newMedicalRecord,thatGuy);
    }

    @Test
    void delete() {

        //ARRANGE

        //ACT
        medicalRecordsRepository.delete("John","Doe");

        //ASSERT
        Mockito.verify(dataHandler,Mockito.times(1)).getData();

    }
}