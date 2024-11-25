
package com.safetynetalerts.demo.service;

import com.safetynetalerts.demo.model.Firestation;
import com.safetynetalerts.demo.model.MedicalRecord;
import com.safetynetalerts.demo.model.Person;
import com.safetynetalerts.demo.repository.FirestationRepository;
import com.safetynetalerts.demo.repository.MedicalRecordsRepository;
import com.safetynetalerts.demo.repository.PersonRepository;
import com.safetynetalerts.demo.service.dto.FirestationDTO;
import com.safetynetalerts.demo.service.dto.FirestationPersonDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.safetynetalerts.demo.model.Person.computeToAge;
import static com.safetynetalerts.demo.service.PersonService.medicalRecordsContainsPerson;

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
            if (personContainsFirestationAddress(firestations, person)) {
                result.add(person.getPhone());
            }
        }
        return result;
    }

    public boolean personContainsFirestationAddress(List<Firestation> firestations, Person person) {
        for (Firestation firestation : firestations) {
            if (firestation.getAddress().equals(person.getAddress())) {
                return true;
            }
        }
        return false;
    }

//    //test stream exemple
//    public String fireStationNumberAtThisAddress(String address) {
//        List <Firestation> fierstations = firestationRepository.findAllFireStations();
//        return fierstations.stream().filter(f -> f.getAddress().equals(address))
//                .map(Firestation :: getStation).findFirst().orElse(null);
//    }

    public FirestationDTO findPeopleCountByFirestation(int number) {
        FirestationDTO result = new FirestationDTO();
        List<FirestationPersonDTO> people = new ArrayList<>();
        result.setPeople(people);

        //get All stations by number + medical records for ages
        List<Firestation> firestationList = firestationRepository.findAllFireStationsByNumber(number);
        List<MedicalRecord> medicalRecords = medicalRecordsRepository.findAllMedicalRecords();

        //get all people
        List<Person> persons = personRepository.findAllPersons();

        for (Person person : persons) {
            if (personContainsFirestationAddress(firestationList, person)) {
                FirestationPersonDTO firestationPersonDTO = new FirestationPersonDTO();
                firestationPersonDTO.setFirstName(person.getFirstName());
                firestationPersonDTO.setLastName(person.getLastName());
                firestationPersonDTO.setAddress(person.getAddress());
                firestationPersonDTO.setPhoneNumber(person.getPhone());


                Integer childrenCount = 0;
                Integer adultsCount = 0;

                for (Person person2 : persons) {

                    MedicalRecord medicalRecord = medicalRecordsContainsPerson(medicalRecords, person2);

                    if (medicalRecord != null && personContainsFirestationAddress(firestationList, person2)) {
                        if (computeToAge(medicalRecord.getBirthdate()) < 18) {
                            result.setChildrenCount(childrenCount + 1);
                            childrenCount++;
                        } else {
                            result.setAdultsCount(adultsCount + 1);
                            adultsCount++;
                        }
                    }
                }
                result.getPeople().add(firestationPersonDTO);

            }

        }


        return result;

        //        return personRepository
//                .findAllPersons()
//                .stream()
//
//                //Filters data
//                .filter(person ->
//                        firestationList.stream()
//                                .allMatch(
//                                        person1 -> Objects.equals (person.getAddress(), firestationList.getFirst().getAddress())
//                        )
//
//                )
//                //flatmap ?
//                .map(mapper ->
//                {
//                         return new FirestationPersonDTO(
//                                mapper.getFirstName(),
//                                mapper.getLastName(),
//                                mapper.getAddress(),
//                                mapper.getPhone()
//                                ); }
//                        ).collect(Collectors.toCollection();
//    }
    }




}
