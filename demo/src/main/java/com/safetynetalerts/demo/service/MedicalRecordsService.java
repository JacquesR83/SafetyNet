package com.safetynetalerts.demo.service;

import com.safetynetalerts.demo.model.MedicalRecord;
import com.safetynetalerts.demo.repository.MedicalRecordsRepository;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordsService {

    MedicalRecordsRepository medicalRecordsRepository;

    public MedicalRecordsService(MedicalRecordsRepository medicalRecordsRepository) {
        this.medicalRecordsRepository = medicalRecordsRepository;
    }

    public MedicalRecord findMedicalRecordByName(String firstName, String lastName) {
        return medicalRecordsRepository.findAllMedicalRecords().stream()
                .filter(medicalRecord -> medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName))
                .findFirst()
                .orElse(null);
    }

    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecordsRepository.save(medicalRecord);
        return medicalRecord;
    }

    public void deleteMedicalRecord(String firstName, String lastName) {
        medicalRecordsRepository.delete(firstName, lastName);
    }

    public MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecord medicalRecord) {
        MedicalRecord updatedMedicalRecord = findMedicalRecordByName(firstName,lastName);

        updatedMedicalRecord.setBirthdate(medicalRecord.getBirthdate());
        updatedMedicalRecord.setMedications(medicalRecord.getMedications());
        updatedMedicalRecord.setAllergies(medicalRecord.getAllergies());
        updatedMedicalRecord.setFirstName(firstName);
        updatedMedicalRecord.setLastName(lastName);
        return updatedMedicalRecord;
    }
}
