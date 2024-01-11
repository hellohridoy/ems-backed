package com.firstSpringapi.firstapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.NOT_FOUND)

public class ResouceNotFountExceptions extends RuntimeException{

    public ResouceNotFountExceptions(String message){
        super(message);
    }
}
