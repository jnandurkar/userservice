package com.example.userservice.service;

import com.example.userservice.entity.User;

import java.util.List;

public interface UserService {
    public User createUser(User user);
    public List<User> getAlluser();
    public User getUserById(Long id);
    public User updateUser(Long id, User user);
    public void deleteUser(Long id);
}
