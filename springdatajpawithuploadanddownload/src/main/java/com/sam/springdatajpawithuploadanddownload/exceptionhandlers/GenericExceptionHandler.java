package com.sam.springdatajpawithuploadanddownload.exceptionhandlers;

import com.sam.springdatajpawithuploadanddownload.DTO.APIResponse;
import com.sam.springdatajpawithuploadanddownload.constants.CommonConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;

public class GenericExceptionHandler {
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<APIResponse> handleFileNotFound(FileNotFoundException e) {
        return new ResponseEntity<>(new APIResponse(CommonConstants.FAILED, 400, "File is not there in the system"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<APIResponse> handleFileNotFound(BadRequestException e) {
        return new ResponseEntity<>(new APIResponse(CommonConstants.FAILED, 400, e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
