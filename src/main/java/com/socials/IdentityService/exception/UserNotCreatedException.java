package com.socials.IdentityService.exception;

public class UserNotCreatedException extends RuntimeException{

    private String message;
    public UserNotCreatedException(String message){
        this.message=message;
    }

}
