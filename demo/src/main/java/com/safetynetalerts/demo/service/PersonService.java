package com.safetynetalerts.demo.service;

import com.safetynetalerts.demo.model.Firestation;
import com.safetynetalerts.demo.model.MedicalRecord;
import com.safetynetalerts.demo.model.Person;
import com.safetynetalerts.demo.repository.FirestationRepository;
import com.safetynetalerts.demo.repository.MedicalRecordsRepository;
import com.safetynetalerts.demo.repository.PersonRepository;
import com.safetynetalerts.demo.service.dto.ChildDTO;
import com.safetynetalerts.demo.service.dto.FireDTO;
import com.safetynetalerts.demo.service.dto.PersonDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.safetynetalerts.demo.model.Person.computeToAge;
import static com.safetynetalerts.demo.model.Person.isAdult;
import static java.util.stream.Collectors.toList;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final FirestationRepository firestationRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;


    public PersonService(PersonRepository personRepository, FirestationRepository firestationRepository, MedicalRecordsRepository medicalRecordsRepository, FirestationService firestationService) {
        this.personRepository = personRepository;
        this.firestationRepository = firestationRepository;
        this.medicalRecordsRepository = medicalRecordsRepository;
    }


    public List<String> findAllEmailsByCity(String city) {
        return this.personRepository.findAllPersons().stream().filter(p -> p.getCity().equals(city)).map(p -> p.getEmail()).collect(toList());

    }

    public List<FireDTO> findAllPersonsWithMedicalRecords(String address) {
        List<FireDTO> result = new ArrayList<>();
        // On veut retourner une liste de DTOS (dont tous les paramètres ont déjà été définis dans la classe DTO)
        // On doit comparer l'adresse de la liste de personnes et des firestations pour récupérer: le stationNumber, le tel et le lastName, puis le stocker dans le DTO
        // On compare le firstName et le lastName des personnes avec medicalRecords pour récupérer: medication et allergies, puis stocker dans le DTO
        // Pour l'âge il faut récupérer le birthdate et calculer avec la date d'aujourd'hui (résultat dans un String en années) puis le stocker dans le DTO
        // On retourne la liste des DTOS

        //Recupere les personnes
        List<Person> persons = this.personRepository.findAllPersonsByAddress(address);
        //Recupere les medicalRecords
        List<MedicalRecord> medicalRecords = this.medicalRecordsRepository.findAllMedicalRecords();

        //Cree une nouvelle liste et insere les noms correspondants
        for (Person person : persons) {
            MedicalRecord medicalRecord = medicalRecordsContainsPerson(medicalRecords, person); // Compare les champs (firstName & lastName) de medicalRecord et de person
            if (medicalRecord != null) {
                FireDTO fireDTO = new FireDTO();
                Firestation firestation = firestationRepository.findFireStationNumberByAddress(address);
                fireDTO.setFirestation(firestation.getStation());
                fireDTO.setLastName(person.getLastName());
                fireDTO.setPhoneNumber(person.getPhone());
                fireDTO.setAge(String.valueOf(computeToAge(medicalRecord.getBirthdate()))); // Calcul de l'âge dans ComputeToAge
                fireDTO.setMedications(medicalRecord.getMedications());
                fireDTO.setAllergies(medicalRecord.getAllergies());
                result.add(fireDTO);
            }
        }
        return result;
    }

//    public List<FireDTO> findAllPersonsWithMedicalRecordsVersionStream(String address) {
//        // Récupérer la liste des personnes à l'adresse donnée
//        List<Person> persons = this.personRepository.findAllPersonsByAddress(address);
//
//        // Récupérer la liste des enregistrements médicaux
//        List<MedicalRecord> medicalRecords = this.medicalRecordsRepository.findAllMedicalRecords();
//
//        Firestation firestation = firestationRepository.findFireStationNumberByAddress(address);
//
//        Person person = new Person();
//        return persons.stream().map(person2 -> {
//                MedicalRecord medicalRecord = medicalRecordsContainsPerson(medicalRecords, person);
//            if (medicalRecord != null){
//                FireDTO fireDTO = new FireDTO();
//                fireDTO.setFirestation(firestation.getStation());  // Numéro de la station
//                fireDTO.setLastName(person.getLastName());        // Nom de la personne
//                fireDTO.setPhoneNumber(person.getPhone());       // Numéro de téléphone
//                fireDTO.setAge(String.valueOf(computeToAge(medicalRecord.getBirthdate())));  // Calcul de l'âge
//                fireDTO.setMedications(medicalRecord.getMedications());  // Médications
//                fireDTO.setAllergies(medicalRecord.getAllergies());      // Allergies
//                return fireDTO;
//            }
//            return null;
//        })
//                .filter(Objects :: nonNull)
//                .collect(toList());
//    }




//


    public static MedicalRecord medicalRecordsContainsPerson(List<MedicalRecord> medicalRecords, Person person) {
        return medicalRecords.stream()
                .filter(mr -> mr.getFirstName().equals(person.getFirstName()) && mr.getLastName().equals(person.getLastName()))
                .findFirst().orElse(null);
    }


    public List<PersonDTO> familyInformation(String firstName, String lastName) {
        List<PersonDTO> result = new ArrayList<>();
        List<Person> persons = this.personRepository.findAllPersonsWithLastName(lastName);
        List<MedicalRecord> medicalRecords = this.medicalRecordsRepository.findAllMedicalRecords();

        for (Person person : persons) {
            MedicalRecord medicalRecord = medicalRecordsContainsPerson(medicalRecords, person);
            if (medicalRecord != null) {
                PersonDTO personDTO = new PersonDTO();

                personDTO.setLastName(person.getLastName());
                personDTO.setAddress(person.getAddress());
                personDTO.setAge(String.valueOf(computeToAge(medicalRecord.getBirthdate())));
                personDTO.setEmail(person.getEmail());
                personDTO.setMedications(medicalRecord.getMedications());
                personDTO.setAllergies(medicalRecord.getAllergies());

                result.add(personDTO);
            }
        }
        return result;
    }


    // Méthode avec des streams
    public List<ChildDTO> childListAtThisAddress(String address) {
        List<Person> personList = personRepository.findAllPersonsByAddress(address);
        // Cette ligne remplace l'instanciation de l'objet et le return en fin de méthode, le return se déclare directement au début
        return medicalRecordsRepository
                .findAllMedicalRecords()
                .stream()

                // Filter All persons match with lastName
                .filter(medicalRecord ->
                        personList.stream()
                                .allMatch(
                                        person -> Objects.equals(person.getLastName(), medicalRecord.getLastName())
                                )
                )

                // Filter Match isAdult return false to select a minorPerson
                .filter(medicalRecord -> !isAdult(medicalRecord.getBirthdate()))

                // MAPPING DATA TO DTO MODEL ChildAlertDTO
                .map(mapper ->
                        new ChildDTO(
                                mapper.getFirstName(),
                                mapper.getLastName(),
                                computeToAge((mapper.getBirthdate())),
                                personList
                                        .stream()
                                        .filter(person -> Objects.equals(person.getLastName(), mapper.getLastName()) != Objects.equals(person.getFirstName(), mapper.getFirstName()))
                                        .toList()
                        )
                )
                // Collect to ChildAlertDTO
                .collect(toList());
    }


    // CRUD
    public List<Person> findAllPersonsWithLastName(String lastName) {
            List<Person> result = new ArrayList();
            result = personRepository.findAllPersons().stream().filter(p -> p.getLastName().equals(lastName)).toList();
            return result;
    }

    public void addPerson(Person person) {
        this.personRepository.save(person);
    }

    public void deletePersonByFirstNameAndLastName(String firstName, String lastName) {
        boolean available = personRepository.exists(firstName, lastName);
        if(!available) {
            throw new IllegalArgumentException();
        }
        personRepository.deletePerson(firstName, lastName);
    }

    public void updatePerson(String firstName,String lastName , Person person) {
        Person updatedPerson = personRepository.findPersonByFirstNameAndLastName(firstName, lastName);

        if(person.getFirstName() !=  null){
            updatedPerson.setFirstName(firstName);
        }
        if (person.getLastName() != null) {
            updatedPerson.setLastName(lastName);
        }
        if (person.getEmail() != null) {
            updatedPerson.setEmail(person.getEmail());
        }
        if (person.getAddress() != null) {
            updatedPerson.setAddress(person.getAddress());
        }
        if (person.getPhone() != null) {
            updatedPerson.setPhone(person.getPhone());
        }
        if (person.getCity() != null) {
            updatedPerson.setCity(person.getCity());
        }
        if (person.getZip() != null) {
            updatedPerson.setZip(person.getZip());
        }
        else {
            throw new IllegalArgumentException ( firstName + " " + lastName + " doesn't exist");
        }

    }
}

//    public List<ChildDTO> childListAtThisAddress(String address) {
//        List <ChildDTO> childrenAndRelatives = new ArrayList<>();
//        List<Person> persons = this.personRepository.findAllPersonsByAddress(address);
//        List<MedicalRecord> medicalRecords = this.medicalRecordsRepository.findAllMedicalRecords();
//
//
//            for (Person person : persons) {
//                // Doit retourner nom, prenom, age des enfants et une liste des autres membres du foyer
//                medicalRecordsContainsPerson(medicalRecords, person);
//                int age = ComputeToAge(joinBirthdate(medicalRecords, person));
//                if(age < 18 && childrenAndRelatives.contains()) {
//                    ChildDTO childDTO = new ChildDTO();
//                    childDTO.setFirstName(person.getFirstName());
//                    childDTO.setLastName(person.getLastName());
//                    childDTO.setAge(String.valueOf(ComputeToAge(joinBirthdate(medicalRecords, person))));
//                    childDTO.getFamilyMembers().add(person);
//                    childrenAndRelatives.add(childDTO);
//                }
//            }
//            return childrenAndRelatives;
//    }
//}
