package com.safetynetalerts.demo.repository;
import com.jsoniter.JsonIterator;
import com.safetynetalerts.demo.model.Data;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class DataHandler {

    private final Data data;

    // Constructor DataHandler instanciates data. Input string gets sliced
    // get result from getFromResources specifying filepath and injects the whole (big String) data into a string temp
    // magic happens: instanciating data arg with JsonIterator.deserialize method
    // JsonIterator.deserialize analyzes content from 'temp' and associates it to predetermined objects/classes existing in the data model.


    public DataHandler() throws IOException {
        String temp = getFromResources( "data.json");
        this.data = JsonIterator.deserialize(temp, Data.class);
    }

    // Inject entry filePath in a stream , returns a String of the whole file
    // ClassPathResource comes from SpringBoot
    // First transforms Json into a stream using a path and the reading data method (getInputStream)
    // Then stream is turned into a big String

    private String getFromResources(String s) throws IOException {
        InputStream is = new ClassPathResource(s).getInputStream();
        return IOUtils.toString(is, StandardCharsets.UTF_8);
    }

    public Data getData(){
        return data;
    }


}
