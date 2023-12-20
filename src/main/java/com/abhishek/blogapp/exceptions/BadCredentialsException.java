package com.abhishek.blogapp.exceptions;

public class BadCredentialsException extends   RuntimeException {

    public BadCredentialsException(String message) {
        super(message);
    }

    public BadCredentialsException() {
        super();
    }
}
