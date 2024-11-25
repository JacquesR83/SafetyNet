
package com.safetynetalerts.demo.service;

import com.safetynetalerts.demo.model.Firestation;
import com.safetynetalerts.demo.model.MedicalRecord;
import com.safetynetalerts.demo.model.Person;
import com.safetynetalerts.demo.repository.FirestationRepository;
import com.safetynetalerts.demo.repository.MedicalRecordsRepository;
import com.safetynetalerts.demo.repository.PersonRepository;
import com.safetynetalerts.demo.service.dto.FirestationDTO;
import com.safetynetalerts.demo.service.dto.FirestationPersonDTO;
import com.safetynetalerts.demo.service.dto.HearthDTO;
import com.safetynetalerts.demo.service.dto.PersonByHearth;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<String> addressesDeliveredByFirestation(int number) {
        List<Firestation> firestations = firestationRepository.findAllFireStations();
        List<Person> people = personRepository.findAllPersons();

        List<String> addresses = new ArrayList<>();

        // Récupérer les adresses des stations de pompiers en fonction de leur numéro
        for (Firestation firestation : firestations) {
            if (firestation.getStation().equals(String.valueOf(number)) ) {
                for (Person person : people) {
                    // Vérifiez si la personne a la même adresse que la station de pompiers et ajoutez cette adresse
                    if (person.getAddress().equals(firestation.getAddress()) && !addresses.contains(person.getAddress())) {
                        addresses.add(person.getAddress());
                    }
                }
            }
        }
        return addresses;
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

    public List <HearthDTO> findPeopleByStation(int number) {

        // Set List
        List<HearthDTO> result = new ArrayList<>();

        // Linking address to a list of people in hearthDTO
        Map<String, HearthDTO> addressMap = new HashMap<>();

        // Initialization
        List<Person> persons = personRepository.findAllPersons();
        List<Firestation> firestations = firestationRepository.findAllFireStationsByNumber(number);
        List<MedicalRecord> medicalRecords = medicalRecordsRepository.findAllMedicalRecords();

        // Lists all living places addresses to a firestation number
        List<String> firestationAddresses = addressesDeliveredByFirestation(number);

        for (Person person : persons) {

            // Checks if a person is linked to a firestation
            if (personContainsFirestationAddress(firestations, person)) {
                PersonByHearth personByHearth = new PersonByHearth();
                MedicalRecord medicalRecord = medicalRecordsContainsPerson(medicalRecords, person);


                // Set each personByHearth arguments
                personByHearth.setLastName(person.getLastName());
                personByHearth.setPhoneNumber(person.getPhone());
                personByHearth.setAge(computeToAge(medicalRecord.getBirthdate()));
                personByHearth.setMedications(medicalRecord.getMedications());
                personByHearth.setAllergies(medicalRecord.getAllergies());


                //  For each different address linked to a firestation
                for (String address : firestationAddresses) {

                    // If the person's address we just saved is the same as the one we're iterating on
                    if (person.getAddress().equals(address)) {

                        // if the address exists in the Map object
                        if(addressMap.containsKey(address)){

                            // we add the person under this label
                            addressMap.get(address).getPeople().add(personByHearth);
                        }

                        //If there's no address in the Map, hearthDTO is created with this address, a list of people is created
                        else {
                            HearthDTO hearthDTO = new HearthDTO();
                            hearthDTO.setAddress(address);
                            List <PersonByHearth> people = new ArrayList<>();

                            // person is added and hearthDTO people (list) is peopled
                            people.add(personByHearth);
                            hearthDTO.setPeople(people);

                            // addressMap is added with address and the new hearthDTO just created
                            addressMap.put(address, hearthDTO);

                        }
                        break;
                    }
                }


            }
        }
        result.addAll(addressMap.values());
        return result;
    }



}
