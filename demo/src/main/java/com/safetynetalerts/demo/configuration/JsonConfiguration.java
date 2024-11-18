//package com.safetynetalerts.demo.configuration;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.safetynetalerts.demo.model.Data;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//
//import java.io.IOException;
//
//
//// Methode 2 pour la lecture du Json et stockage dans les objets
//
//@Configuration
//
//public class JsonConfiguration {
//
//    @Bean
//    public CommandLineRunner ShowData() throws IOException {
//        return args -> {
//            try {
//                ObjectMapper mapper = new ObjectMapper();
//                Resource resource = new ClassPathResource("datatest.json");
//
//                //Read in Instance Data
//                Data data = mapper.readValue(resource.getFile(), Data.class);
//
//                //Inject in singleton
//                Data.getInstance().setPersons(data.getPersons());
////                Data.getInstance().setFirestations(data.getFirestations());
////                Data.getInstance().setMedicalrecords(data.getMedicalrecords());
//
//                System.out.println("Data loading success !");
//                System.out.println("Persons : " + Data.getInstance().getPersons().size());
////                System.out.println("Fire Stations : " + Data.getInstance().getFirestations().size());
////                System.out.println("Medical Records : " + Data.getInstance().getMedicalrecords().size());
//
//            }catch (IOException e){
//                System.err.println("Error while loading some data : " + e.getMessage());
//            }
//        };
//    }
////
////    public void Show() throws IOException {
////        Data data = readJsonFile();
////        System.out.println(data);
////    }
////
////    ObjectMapper mapper = new ObjectMapper();
////    InputStream is = Data.class.getResourceAsStream("/datatest.json");
////    Data testObj = mapper.readValue(is, Data.class);
////                return testObj;
//
//}
