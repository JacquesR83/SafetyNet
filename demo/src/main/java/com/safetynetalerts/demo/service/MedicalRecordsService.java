package com.safetynetalerts.demo.service;

import com.safetynetalerts.demo.model.MedicalRecord;
import com.safetynetalerts.demo.model.Person;
import com.safetynetalerts.demo.repository.MedicalRecordsRepository;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordsService {

    MedicalRecordsRepository medicalRecordsRepository;

    public MedicalRecordsService(MedicalRecordsRepository medicalRecordsRepository) {
        this.medicalRecordsRepository = medicalRecordsRepository;
    }

    public MedicalRecord findAllMedicalRecordsByName(String firstName, String lastName) {
        return medicalRecordsRepository.findAllMedicalRecords().stream()
                .filter(medicalRecord -> medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName))
                .findFirst().get();
    }

    public void addMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecordsRepository.save(medicalRecord);
    }

    public void deleteMedicalRecord(String firstName, String lastName) {
        this.medicalRecordsRepository.delete(firstName, lastName);
    }

    public void updateMedicalRecord(String firstName, String lastName, MedicalRecord medicalRecord) {
        MedicalRecord updatedMedicalRecord = findAllMedicalRecordsByName(firstName,lastName);

        updatedMedicalRecord.setBirthdate(medicalRecord.getBirthdate());
        updatedMedicalRecord.setMedications(medicalRecord.getMedications());
        updatedMedicalRecord.setAllergies(medicalRecord.getAllergies());
        updatedMedicalRecord.setFirstName(firstName);
        updatedMedicalRecord.setLastName(lastName);
    }
}
