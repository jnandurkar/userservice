package com.example.userservice.service;

import com.example.userservice.entity.Address;
import com.example.userservice.entity.CreditCard;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.utils.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {

        if (user.getAddresses() != null) {
            for (Address address : user.getAddresses()) {
                address.setUser(user);
            }
        }

        if (user.getCreditCards() != null) {
            for (CreditCard card : user.getCreditCards()) {
                card.setUser(user);
            }
        }


        return userRepository.save(user);
    }

    @Override
    public List<User> getAlluser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User updateUser(Long id, User user) {
       User existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        existingUser.setAge(user.getAge());
        existingUser.setName(user.getName());
        existingUser.setPassword(user.getPassword());
       return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)){
         throw  new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
