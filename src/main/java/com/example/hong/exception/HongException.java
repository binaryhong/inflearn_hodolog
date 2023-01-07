package com.example.hong.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class HongException extends RuntimeException{

    // abstract로 만든 이유

    public final Map<String, String> validation = new HashMap<>();

    public HongException(String message) {
        super(message);
    }

    public HongException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message){
        validation.put(fieldName,message);
    }
}
