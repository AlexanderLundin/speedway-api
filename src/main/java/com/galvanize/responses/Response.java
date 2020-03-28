package com.galvanize.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.ResponseWrapper;
import java.lang.reflect.Array;


public class Response extends ResponseEntity {

    public Response(HttpStatus status) {
        super(status);
    }

    public Response(Object body, HttpStatus status) {
        super(body, status);
    }

    public Response(MultiValueMap headers, HttpStatus status) {
        super(headers, status);
    }

    public Response(Object body, MultiValueMap headers, HttpStatus status) {
        super(body, headers, status);
    }


}
