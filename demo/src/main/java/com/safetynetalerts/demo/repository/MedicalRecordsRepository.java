package com.safetynetalerts.demo.repository;

import com.safetynetalerts.demo.model.MedicalRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicalRecordsRepository {

    private final DataHandler dataHandler;

    public MedicalRecordsRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public List<MedicalRecord> findAllMedicalRecords(){
        return dataHandler.getData().getMedicalrecords();
    }

    public MedicalRecord save(MedicalRecord medicalRecord) {
        dataHandler.getData().getMedicalrecords().add(medicalRecord);
        return medicalRecord;
    }

    public void delete(String firstName, String lastName) {
        dataHandler.getData().getMedicalrecords().removeIf(medicalRecord ->
                medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName));
    }

}
