package com.example.CRUDApp.services;

import com.example.CRUDApp.dto.AuthResponseDto;
import com.example.CRUDApp.dto.LoginDto;
import com.example.CRUDApp.dto.RegisterDto;
import com.example.CRUDApp.entities.UserEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

        UserEntity findByUsername(String username);
        boolean existsByUsername(String username);
//        UserEntity saveUser(UserEntity user);

    boolean existsById(Integer id);
    boolean deleteUserById(int id);
    void save(UserEntity user);
    ResponseEntity<AuthResponseDto> login(LoginDto loginDto);
    ResponseEntity<String> register(RegisterDto registerDto);
    ResponseEntity<String> deleteUser(int id);
    ResponseEntity<List<String>> getUserRoles(String username);
}
