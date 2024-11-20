
package com.safetynetalerts.demo.service;

import com.safetynetalerts.demo.model.Firestation;
import com.safetynetalerts.demo.model.Person;
import com.safetynetalerts.demo.repository.FirestationRepository;
import com.safetynetalerts.demo.repository.MedicalRecordsRepository;
import com.safetynetalerts.demo.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FirestationService {

    private final FirestationRepository firestationRepository;
    private final PersonRepository personRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;

    public FirestationService(FirestationRepository firestationRepository, PersonRepository personRepository, MedicalRecordsRepository medicalRecordsRepository) {
        this.firestationRepository = firestationRepository;
        this.personRepository = personRepository;
        this.medicalRecordsRepository = medicalRecordsRepository;
    }

    public List<String> findPhoneNumbersByStationNumber(int number) {
        List<String> result = new ArrayList<>();
        List<Firestation> firestations = firestationRepository.findAllFireStationsByNumber(number);
        List<Person> persons = personRepository.findAllPersons();

        for (Person person : persons) {
            if (personContainsFirestationAdress(firestations, person)) {
                result.add(person.getPhone());
            }
        }
        return result;
    }

    public boolean personContainsFirestationAdress(List<Firestation> firestations, Person person) {
        for(Firestation firestation : firestations){
            if(firestation.getAddress().equals(person.getAddress())){
                return true;
            }
        }
        return false;
    }

}
