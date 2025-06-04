package com.example.userservice;

import com.example.userservice.entity.Address;
import com.example.userservice.entity.CreditCard;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserServiceImpl;
import com.example.userservice.utils.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserRepository userRepository;
    private UserServiceImpl userService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void testCreateUser_setsUserOnAddressAndCreditCardAndSaves() {
        User user = new User();
        Address address = new Address();
        CreditCard card = new CreditCard();

        user.setAddresses(Collections.singletonList(address));
        user.setCreditCards(Collections.singletonList(card));

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertEquals(user, result);
        assertEquals(user, address.getUser());
        assertEquals(user, card.getUser());
        verify(userRepository).save(user);
    }

    @Test
    void testGetAllUser_returnsListOfUsers() {
        User user1 = new User();
        User user2 = new User();
        List<User> userList = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getAlluser();

        assertEquals(2, result.size());
        assertEquals(userList, result);
        verify(userRepository).findAll();
    }

    @Test
    void testGetUserById_returnsUserIfExists() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertEquals(user, result);
    }

    @Test
    void testGetUserById_throwsExceptionIfNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void testUpdateUser_success() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("Old Name");

        User updatedInfo = new User();
        updatedInfo.setName("New Name");
        updatedInfo.setAge(30);
        updatedInfo.setPassword("newPass");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User result = userService.updateUser(1L, updatedInfo);

        assertEquals("New Name", result.getName());
        assertEquals(30, result.getAge());
        assertEquals("newPass", result.getPassword());
    }

    @Test
    void testUpdateUser_throwsIfUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(1L, new User()));
    }

    @Test
    void testDeleteUser_success() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void testDeleteUser_throwsIfUserNotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));
    }
}
