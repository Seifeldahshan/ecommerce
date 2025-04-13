package com.example.demo.service;

import com.example.demo.Exception.UserAlreadyExistException;
import com.example.demo.Model.Repo.UserRepository;
import com.example.demo.Model.User;
import com.example.demo.api.model.LoginBody;
import com.example.demo.api.model.RegistrationBody;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private EncryptionService encryptionService;
    private JWTService jwtService;

    public UserService(UserRepository userRepository, EncryptionService encryptionService, JWTService jwtService) {
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
    }




    public User register(RegistrationBody registrationbody)throws UserAlreadyExistException {
      if(  userRepository.findByEmailIgnoreCase(registrationbody.getEmail()).isPresent() ||
                userRepository.findByUsernameIgnoreCase(registrationbody.getUsername()).isPresent()){
          throw new UserAlreadyExistException();
      }
        User user1 = new User();
        user1.setUsername(registrationbody.getUsername());
        user1.setPassword(encryptionService.encryptPassword(registrationbody.getPassword()));
        user1.setEmail(registrationbody.getEmail());
        user1.setFirstName(registrationbody.getFirstName());
        user1.setLastName(registrationbody.getLastName());
        return userRepository.save(user1);
    }

    public String loginUser(LoginBody loginbody) {
        Optional<User> opUser = userRepository.findByUsernameIgnoreCase(loginbody.getUsername());
        if (opUser.isPresent() ){
            User user=opUser.get();
            if (encryptionService.checkPassword(loginbody.getPassword(), user.getPassword())) {
                return jwtService.generateToken(user);

            }
        }
    return null;
    }
}
