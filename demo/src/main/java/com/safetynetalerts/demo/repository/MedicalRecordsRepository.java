package com.safetynetalerts.demo.repository;

import com.safetynetalerts.demo.model.MedicalRecord;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public class MedicalRecordsRepository {

    private DataHandler dataHandler;

    public MedicalRecordsRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public List<MedicalRecord> findAllMedicalRecords(){
        return dataHandler.getData().getMedicalrecords();
    }


}
