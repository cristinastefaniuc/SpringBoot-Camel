package com.demo.route;

import com.demo.model.PersonCsvRecord;
import com.demo.service.PersonServiceImpl;
import com.demo.util.CsvRecordToPersonMapper;
import com.demo.util.JsonToPerson;
import com.demo.util.PersonToJson;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringCamelRoute extends RouteBuilder {

    private static final String QUESTION_MARK = "?";
    private static final String AMPERSAND = "&";
    private static final String COLON = ":";

    private final CsvRecordToPersonMapper mapper;

    private final PersonServiceImpl personService;

    @Value("${source.type}")
    private String sourceType;

    @Value("${source.location}")
    private String sourceLocation;

    @Value("${file.type}")
    private String fileType;

    @Value("${noop.flag}")
    private boolean isNoop;

    @Value("${recursive.flag}")
    private boolean isRecursive;

    @Override
    public void configure() throws Exception {

        final BindyCsvDataFormat bindyCsvDataFormat = new BindyCsvDataFormat(PersonCsvRecord.class);
        bindyCsvDataFormat.setLocale("default");

        from(buildFileUrl())
                .unmarshal(bindyCsvDataFormat)
                .split(body())
                .bean(mapper, "convertAndTransform")
                .process(new PersonToJson())
                .log("sending...")
                .to("jms:queue:p_aq"); 

        from("jms:queue:p_aq")
                .process(new JsonToPerson())
                .bean(personService, "saveOrUpdate")
                .end();
    }

    private String buildFileUrl() {
        return sourceType + COLON + sourceLocation +
                QUESTION_MARK + "noop=" + isNoop +
                AMPERSAND + "recursive=" + isRecursive +
                AMPERSAND + "include=" + fileType +
                AMPERSAND + "idempotentKey=$simple{file:name}-$simple{file:modified}";
    }
}