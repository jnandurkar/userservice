package com.example.userservice.utils;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User id is not found" + id);
    }
}
