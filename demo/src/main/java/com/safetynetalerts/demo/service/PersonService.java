package com.safetynetalerts.demo.service;

import com.safetynetalerts.demo.model.Person;
import com.safetynetalerts.demo.repository.FirestationRepository;
import com.safetynetalerts.demo.repository.MedicalRecordsRepository;
import com.safetynetalerts.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final FirestationRepository firestationRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;


    public PersonService(PersonRepository personRepository, FirestationRepository firestationRepository, MedicalRecordsRepository medicalRecordsRepository) {
        this.personRepository = personRepository;
        this.firestationRepository = firestationRepository;
        this.medicalRecordsRepository = medicalRecordsRepository;
    }


    public List<String> findAllEmailsByCity(String city){
        return this.personRepository.findAllPersons().stream().filter(p -> p.getCity().equals(city)).map(p ->p.getEmail()).collect(Collectors.toList());

    }
}
