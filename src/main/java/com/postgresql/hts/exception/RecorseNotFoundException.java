package com.postgresql.hts.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecorseNotFoundException extends RuntimeException{
    public RecorseNotFoundException(String message){
        super (message);
    }
}
