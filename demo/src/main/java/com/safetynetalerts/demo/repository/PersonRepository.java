package com.safetynetalerts.demo.repository;

import com.safetynetalerts.demo.model.MedicalRecord;
import com.safetynetalerts.demo.model.Person;
import com.safetynetalerts.demo.service.PersonService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonRepository {
    private static MedicalRecord[] medicalRecords;
    private DataHandler dataHandler;

    public PersonRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public List<Person> findAllPersons(){
        return dataHandler.getData().getPersons();
    }

    public static MedicalRecord findMedicalRecordByPerson(Person person) {
        for (MedicalRecord record : medicalRecords) {
            if (record.getFirstName().equals(person.getFirstName()) && record.getLastName().equals(person.getLastName())) {
                return record;
            }
        }
        return null;
    }

}
