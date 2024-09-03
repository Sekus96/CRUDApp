package com.example.CRUDApp.services;

import com.example.CRUDApp.dto.AuthResponseDto;
import com.example.CRUDApp.dto.LoginDto;
import com.example.CRUDApp.dto.RegisterDto;
import com.example.CRUDApp.entities.Role;
import com.example.CRUDApp.entities.UserEntity;
import com.example.CRUDApp.repositories.RoleRepository;
import com.example.CRUDApp.repositories.UserRepository;
import com.example.CRUDApp.security.JWTGenerator;
import com.example.CRUDApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Autowired
    @Lazy
    private JWTGenerator jwtGenerator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<String> register(RegisterDto registerDto) {
        if (existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role roles = roleRepository.findByName("USER")
                .orElseThrow(() -> new NoSuchElementException("Role 'USER' not found"));
        user.setRoles(Collections.singletonList(roles));

        save(user);

        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AuthResponseDto> login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteUser(int id) {
        if (!existsById(id)) {
            return new ResponseEntity<>("User with ID " + id + " not found", HttpStatus.NOT_FOUND);
        } else {
            boolean isRemoved = deleteUserById(id);
            if (isRemoved) {
                return ResponseEntity.ok("User with ID " + id + " has been deleted successfully");
            } else {
                return new ResponseEntity<>("Failed to delete user with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Override
    public ResponseEntity<List<String>> getUserRoles(String username) {
        UserEntity user = findByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        return new ResponseEntity<>(roles, HttpStatus.OK);
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