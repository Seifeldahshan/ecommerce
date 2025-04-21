package com.example.demo.api.controllers.Authentication;

import com.example.demo.Exception.EmailFailureException;
import com.example.demo.Exception.UserAlreadyExistException;
import com.example.demo.Exception.UserNotVerifiedException;
import com.example.demo.Model.User;
import com.example.demo.api.model.LoginBody;
import com.example.demo.api.model.LoginResponse;
import com.example.demo.api.model.RegistrationBody;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;

    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegistrationBody registrationbody) {
      try {
          userService.register(registrationbody);
          return ResponseEntity.ok().build();
      } catch (UserAlreadyExistException | EmailFailureException ex) {
          return ResponseEntity.status(HttpStatus.CONFLICT).build();
      }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginBody loginBody) throws EmailFailureException {

        String token;
        try {
            token = userService.loginUser(loginBody);
        } catch (UserNotVerifiedException ex) {

            LoginResponse response = new LoginResponse();
            response.setSuccess(false);
            response.setFailureReason("User Not Verified");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        if (token == null) {
            LoginResponse response = new LoginResponse();
            response.setSuccess(false);
            response.setFailureReason("Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setSuccess(true);
        loginResponse.setToken(token);
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/me")
    public User getLoggedInUserProfile(@AuthenticationPrincipal User user) {
        return user;
    }
    @GetMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam String token) {
        if (userService.verifyUser(token)) {
            return ResponseEntity.ok("✅ Email verified");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Invalid or expired token");
    }

}
