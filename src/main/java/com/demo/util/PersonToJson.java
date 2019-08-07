package com.demo.util;

import com.demo.model.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component("personToJson")
public class PersonToJson implements Processor {

    private ObjectMapper mapper = new ObjectMapper();

//    public String convertToJson(Person person) throws JsonProcessingException {
//        return ;
//    }

    @Override
    public void process(Exchange exchange) throws Exception {
        Person person = exchange.getIn().getBody(Person.class);
        exchange.getOut().setBody(mapper.writeValueAsString(person));
    }
}
