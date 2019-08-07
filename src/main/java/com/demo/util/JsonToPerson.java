package com.demo.util;

import com.demo.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("jsonToPerson")
public class JsonToPerson implements Processor {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void process(Exchange exchange) throws Exception {
        String personJson = exchange.getIn().getBody(String.class);
        exchange.getOut().setBody(mapper.readValue(personJson, Person.class));
    }
}
