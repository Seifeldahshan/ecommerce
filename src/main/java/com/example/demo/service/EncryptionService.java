package com.example.demo.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

    @Value("${encryption.salt.round}")
    private int saltRounds;

    @PostConstruct
    public void postConstruct() {
        if (saltRounds < 4 || saltRounds > 31) {
            throw new IllegalArgumentException("Invalid saltRounds value: " + saltRounds);
        }
    }

    public String encryptPassword(String password) {
        String salt = BCrypt.gensalt(saltRounds);
        return BCrypt.hashpw(password, salt);
    }

    public boolean checkPassword(String password, String encryptedPassword) {
        if (encryptedPassword == null || encryptedPassword.isEmpty()) {
            throw new IllegalArgumentException("Invalid encrypted password");
        }

        return BCrypt.checkpw(password, encryptedPassword);
    }
}
