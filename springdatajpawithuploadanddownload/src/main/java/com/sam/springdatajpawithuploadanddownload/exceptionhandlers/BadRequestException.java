package com.sam.springdatajpawithuploadanddownload.exceptionhandlers;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String msg)
    {
        super(msg);
    }
}
