package com.example.demo.api.controllers;

import com.example.demo.Exception.UserAlreadyExistException;
import com.example.demo.Model.Repo.UserRepository;
import com.example.demo.Model.User;
import com.example.demo.api.model.LoginBody;
import com.example.demo.api.model.LoginResponse;
import com.example.demo.api.model.RegistrationBody;
import com.example.demo.service.EncryptionService;
import com.example.demo.service.JWTService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {


    private final EncryptionService encryptionService;
    private UserService userService;
    private RegistrationBody registrationBody;
    private UserRepository userRepository;

    public AuthenticationController(UserService userService, EncryptionService encryptionService) {
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegistrationBody registrationbody) {
      try {
          userService.register(registrationbody);
          return ResponseEntity.ok().build();
      } catch (UserAlreadyExistException ex) {
          return ResponseEntity.status(HttpStatus.CONFLICT).build();
      }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginBody loginbody) {
        String Token = userService.loginUser(loginbody);
        if(Token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }else {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(Token);
            return ResponseEntity.ok().body(loginResponse);
        }
    }


    @GetMapping("/me")
    public User getLoggedInUserProfile(@AuthenticationPrincipal User user) {
        return user;
    }
}
