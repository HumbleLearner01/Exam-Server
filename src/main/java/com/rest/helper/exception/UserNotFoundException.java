package com.rest.helper.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("\n\t\t\t<<<<<<<--- User already exists in our DB --->>>>>>>\n");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}