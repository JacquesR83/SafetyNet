package com.safetynetalerts.demo.service;

import com.safetynetalerts.demo.model.Firestation;
import com.safetynetalerts.demo.model.MedicalRecord;
import com.safetynetalerts.demo.model.Person;
import com.safetynetalerts.demo.repository.FirestationRepository;
import com.safetynetalerts.demo.repository.MedicalRecordsRepository;
import com.safetynetalerts.demo.repository.PersonRepository;
import com.safetynetalerts.demo.service.dto.FireDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final FirestationRepository firestationRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;

    private final FirestationService firestationService;


    public PersonService(PersonRepository personRepository, FirestationRepository firestationRepository, MedicalRecordsRepository medicalRecordsRepository, FirestationService firestationService) {
        this.personRepository = personRepository;
        this.firestationRepository = firestationRepository;
        this.medicalRecordsRepository = medicalRecordsRepository;
        this.firestationService = firestationService;
    }


    public List<String> findAllEmailsByCity(String city){
        return this.personRepository.findAllPersons().stream().filter(p -> p.getCity().equals(city)).map(p ->p.getEmail()).collect(Collectors.toList());

    }

    public List<FireDTO> findAllPersonsWithMedicalRecords(String address) {
        List<FireDTO> result = new ArrayList<>();
        // On veut retourner une liste de DTOS (dont tous les paramètres ont déjà été définis dans la classe DTO)
        // On doit comparer l'adresse de la liste de personnes et des firestations pour récupérer: le stationNumber, le tel et le lastName, puis le stocker dans le DTO
        // On compare le firstName et le lastName des personnes avec medicalRecords pour récupérer: medication et allergies, puis stocker dans le DTO
        // Pour l'âge il faut récupérer le birthdate et calculer avec la date d'aujourd'hui (résultat dans un String en années) puis le stocker dans le DTO
        // On retourne la liste des DTOS

        List<Person> persons = this.personRepository.findAllPersons();
        List <Firestation> firestations = this.firestationRepository.findAllFireStations();
        List <MedicalRecord> medicalRecords = this.medicalRecordsRepository.findAllMedicalRecords();

        Person person = new Person();
        FireDTO fireDTO = new FireDTO();

        for (Person persona : persons) {
            Firestation firestation = new Firestation();

            if(firestationService.personContainsFirestationAdress(firestations, persona)){
                fireDTO.setLastName(persona.getLastName());
                fireDTO.setFirestation(String.valueOf(equals(firestation.getStation())));
            }
        }
        result.add(fireDTO);
//
        return result;
    }

    public String ageCalculator(String birthdate){
        MedicalRecord medicalRecord = new MedicalRecord();
        Integer age;
        Integer today = (Integer) parse(LocalDate.now().toString());
        age = today - birthdate;
        medicalRecord.getBirthdate();
    }

}



//fireDTO.setFirestation(firestations.get);
