package com.example.demo.Model.Repo;

import com.example.demo.Model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsernameIgnoreCase(String username);
    Optional <User> findByEmailIgnoreCase(String email);
 }
