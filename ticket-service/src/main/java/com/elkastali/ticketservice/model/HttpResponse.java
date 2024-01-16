package com.elkastali.ticketservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.util.Map;


@Data
@SuperBuilder
@AllArgsConstructor @NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HttpResponse {

    protected String timeStamp;
    protected int statusCode;
    protected HttpStatus status;
    protected String message;
    protected String DeveloperMesssage;
    protected  String path;
    protected String requestMethod;
    protected Map<?,?> data;

}
