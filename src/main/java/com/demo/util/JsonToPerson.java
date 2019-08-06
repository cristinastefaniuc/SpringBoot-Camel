package com.demo.util;

import com.demo.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("jsonToPerson")
public class JsonToPerson {

    private ObjectMapper mapper = new ObjectMapper();

    public Person convertToPerson(String jsonString) throws IOException {
        return mapper.readValue(jsonString, Person.class);
    }
}
