package com.demo.util;

import com.demo.model.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component("personToJson")
public class PersonToJson {

    private ObjectMapper mapper = new ObjectMapper();

    public String convertToJson(Person person) throws JsonProcessingException {
        return mapper.writeValueAsString(person);
    }
}
