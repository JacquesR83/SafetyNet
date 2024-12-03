package com.safetynetalerts.demo.model;

import com.safetynetalerts.demo.repository.FirestationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FirestationTest {



    @Test
    public void testFireStationConstructorAndGetters() {
        // Arrangement
        String address = "1509 Culver St";
        String station = "3";

        // Action
        Firestation fireStation = new Firestation(address, station);

        // Assertion
        assertEquals(address, fireStation.getAddress(), "L'adresse devrait correspondre à celle fournie au constructeur");
        assertEquals(station, fireStation.getStation(), "Le numéro de station devrait correspondre à celui fourni au constructeur");
    }

    @Test
    public void testFireStationSetters() {
        // Arrangement
        Firestation fireStation = new Firestation();
        String address = "1509 Culver St";
        String station = "3";

        // Action
        fireStation.setAddress(address);
        fireStation.setStation(station);

        // Assertion
        assertEquals(address, fireStation.getAddress(), "L'adresse devrait être correctement mise à jour");
        assertEquals(station, fireStation.getStation(), "Le numéro de station devrait être correctement mis à jour");
    }

    @Test
    public void testDefaultConstructor() {
        // Action
        Firestation fireStation = new Firestation();

        // Assertion
        assertNull(fireStation.getAddress(), "L'adresse devrait être null par défaut");
        assertNull(fireStation.getStation(), "Le numéro de station devrait être null par défaut");
    }

    @Test
    public void testAddressModification() {
        // Arrangement
        Firestation fireStation = new Firestation("Ancienne adresse", "3");
        String newAddress = "Nouvelle adresse";

        // Action
        fireStation.setAddress(newAddress);

        // Assertion
        assertEquals(newAddress, fireStation.getAddress(), "L'adresse devrait pouvoir être modifiée");
    }
}