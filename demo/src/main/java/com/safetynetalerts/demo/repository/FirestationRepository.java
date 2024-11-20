package com.safetynetalerts.demo.repository;

import com.safetynetalerts.demo.model.Firestation;
import com.safetynetalerts.demo.service.FirestationService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
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

    public Firestation findFireStationNumberByAddress(String address){
        // Même méthode en forEach
//        for(Firestation firestation : dataHandler.getData().getFirestations()){
//            if(firestation.getAddress().equals(address)){
//                return firestation;
//            }
//        }
//        return null;
      return dataHandler.getData().getFirestations().stream().filter(p -> p.getAddress().equals(address)).findFirst().orElseGet(()->new Firestation());
    }

    public List<Firestation> findAllFireStations(){
        return dataHandler.getData().getFirestations();
    }


}
