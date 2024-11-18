package com.safetynetalerts.demo.repository;

import com.safetynetalerts.demo.model.Person;
import com.safetynetalerts.demo.service.PersonService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonRepository {
    private DataHandler dataHandler;

    public PersonRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public List<Person> findAllPersons(){
        return dataHandler.getData().getPersons();
    }
}
