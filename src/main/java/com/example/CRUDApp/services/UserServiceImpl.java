package com.example.CRUDApp.services;

import com.example.CRUDApp.entities.UserEntity;
import com.example.CRUDApp.repositories.UserRepository;
import com.example.CRUDApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsById(Integer id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean deleteUserById(int id) {
        boolean isRemoved = false;

        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            isRemoved = true;
        }
        return isRemoved;
    }

    @Override
    public void save(UserEntity updatedUser) {
        userRepository.save(updatedUser);
    }
}