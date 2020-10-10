package com.drsf.polygon.model;

import org.springframework.http.HttpStatus;

import java.util.List;

public class GenericHTTPResponse<T> {

    HttpStatus status;
    String responseBody;
    List<String> errorMessages;


}
