package com.example.userservice;

import com.example.userservice.entity.Address;
import com.example.userservice.entity.CreditCard;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserServiceImpl;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImpl1Test {
    private UserRepository userRepository;
    private UserServiceImpl userService;
    @BeforeEach
    void setup(){
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);

    }
    @Test
    void testCreateUser(){
       User user = new User();
        Address address = new Address();
        CreditCard creditCard = new CreditCard();

        user.setAddresses(Collections.singletonList(address));
        user.setCreditCards(Collections.singletonList(creditCard));

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);
        assertEquals(user, result);
        assertEquals(user, address.getUser());
        verify(userRepository).save(user);


    }
    @Test
    void testGetAlluser(){
        User user1 = new User();
        User user2 = new User();
        List<User> list = java.util.Arrays.asList(user1,user2);

        when(userRepository.findAll()).thenReturn(list);
        List<User> result = userService.getAlluser();
        assertEquals(2, result.size());
        assertEquals(result, list);
        verify(userRepository).findAll();

    }

    @Test
    void testGetUserById(){
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);
        assertEquals(user, result);
        assertEquals(user.getId(),result.getId());
    }

    @Test
    void testUpdateUser(){
        User user = new User();
        user.setId(1L);
        user.setName("Alex");
        user.setAge(20);
        user.setPassword("xyz");
        
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User result = userService.updateUser(1L, user);

    }

}
