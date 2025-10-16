package com.contactmanager.helper;

public class ResourseNotFoundException extends RuntimeException{
    public ResourseNotFoundException(String message){
        super(message);
    }
    public ResourseNotFoundException(){
        super("Resourse Not Found Exception...");
    }
}
