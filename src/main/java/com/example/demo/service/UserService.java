package com.example.demo.service;

import com.example.demo.Exception.EmailFailureException;
import com.example.demo.Exception.UserAlreadyExistException;
import com.example.demo.Exception.UserNotVerifiedException;
import com.example.demo.Model.Repo.UserRepository;
import com.example.demo.Model.Repo.VerificationTokenRepository;
import com.example.demo.Model.User;
import com.example.demo.Model.VerificationToken;
import com.example.demo.api.model.LoginBody;
import com.example.demo.api.model.RegistrationBody;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private EncryptionService encryptionService;
    private JWTService jwtService;
    private EmailService emailService;
    private VerificationTokenRepository verificationTokenRepository;



    public UserService(UserRepository userRepository, EncryptionService encryptionService, JWTService jwtService, EmailService emailService, VerificationTokenRepository verificationTokenRepository) {
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.verificationTokenRepository = verificationTokenRepository;
    }




    public User register(RegistrationBody registrationbody) throws UserAlreadyExistException, EmailFailureException {
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
        user1.setRole("USER");

        // saving user before send email verification
        userRepository.save(user1);

        VerificationToken verificationToken = createVerificationToken(user1);
        verificationTokenRepository.save(verificationToken);

        emailService.sendVerificationEmail(verificationToken);

        return user1 ;
    }

    public String loginUser(LoginBody loginbody) throws UserNotVerifiedException, EmailFailureException {
        Optional<User> opUser = userRepository.findByUsernameIgnoreCase(loginbody.getUsername());
        if (opUser.isPresent() ) {
            User user = opUser.get();
            if (encryptionService.checkPassword(loginbody.getPassword(), user.getPassword())) {
                if (user.isEmailVerified()) {
                    return jwtService.generateToken(user);

                } else {
                    List <VerificationToken> verificationTokens = user.getVerificationTokens();
                    boolean resend = verificationTokens.isEmpty() ||
                            verificationTokens.get(0).getCreateTimestamp().before(new Timestamp(System.currentTimeMillis()- (60 * 60 * 1000)));
                   if(resend){
                       VerificationToken verificationToken = createVerificationToken(user);
                       verificationTokenRepository.save(verificationToken);
                       emailService.sendVerificationEmail(verificationToken);
                   }
                    throw new UserNotVerifiedException(resend);
                }
            }
        }
    return null;
    }

    private VerificationToken createVerificationToken(User user) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateToken(user));
        verificationToken.setUser(user);
        verificationToken.setCreateTimestamp(new Timestamp(System.currentTimeMillis()));
        user.getVerificationTokens().add(verificationToken);
        return verificationToken;
    }
@Transactional
    public boolean verifyUser(String token){
        Optional<VerificationToken> opToken = verificationTokenRepository.findByToken(token);
        if(opToken.isPresent()){
            VerificationToken verificationToken = opToken.get();
            User user = verificationToken.getUser();
        if (!user.isEmailVerified()) {
            user.setEmailVerified(true);
            userRepository.save(user);
            verificationTokenRepository.deleteByUser(user);
            return true;
        }

        }
        return false ;
    }

    public boolean userHasPermisson(User user , long userId) {
        if (user == null) {
            return false;
        }
        return user.getId() == userId;
    }
}
