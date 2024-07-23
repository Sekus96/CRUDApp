package com.example.CRUDApp.controllers;

import com.example.CRUDApp.dto.AuthResponseDto;
import com.example.CRUDApp.dto.LoginDto;
import com.example.CRUDApp.dto.RegisterDto;
import com.example.CRUDApp.entities.Role;
import com.example.CRUDApp.entities.UserEntity;
import com.example.CRUDApp.repositories.RoleRepository;
import com.example.CRUDApp.repositories.UserRepository;
import com.example.CRUDApp.security.CustomUserDetailsService;
import com.example.CRUDApp.security.JWTGenerator;
import com.example.CRUDApp.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/CRUDApp/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @Operation(summary = "Login user", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
    }

    @Operation(summary = "Register a new user", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if(userService.existsByUsername(registerDto.getUsername())){
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role roles = roleRepository.findByName("USER").orElseThrow(() -> new NoSuchElementException("Role 'USER' not found"));
        user.setRoles(Collections.singletonList(roles));

        userService.save(user);

        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }


    @Operation(summary = "Retrieve user roles", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/roles")
    public ResponseEntity<List<String>> getUserRoles(@RequestParam String username) {
        UserEntity user = customUserDetailsService.findByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
        System.out.println(user.getRoles());
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @Operation(summary = "Delete user by ID", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id){
        if(!userService.existsById(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            userService.deleteUserById(id);
            return ResponseEntity.ok("User with ID " + id + " has been deleted successfully");
        }
    }
}
