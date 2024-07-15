package com.example.CRUDApp.services;

import com.example.CRUDApp.entities.UserEntity;

public interface UserService {

        UserEntity findByUsername(String username);
        boolean existsByUsername(String username);
//        UserEntity saveUser(UserEntity user);

    boolean existsById(Integer id);

    boolean deleteUserById(int id);

    void save(UserEntity user);
}
