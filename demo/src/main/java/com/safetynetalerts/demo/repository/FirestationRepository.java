package com.safetynetalerts.demo.repository;

import com.safetynetalerts.demo.model.Firestation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FirestationRepository {

    private DataHandler dataHandler;

    public FirestationRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public List<Firestation> findAllFireStationsByNumber(Integer number){
        return dataHandler.getData().getFirestations().stream().filter(f -> f.getStation().equals(number.toString())).collect(Collectors.toList());
    }

    public List<Firestation> findAllFireStations(){
        return dataHandler.getData().getFirestations();
    }


}
