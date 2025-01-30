package org.launchcode.PlatePlanner.service;

import jakarta.mail.MessagingException;
import org.apache.commons.codec.binary.StringUtils;
import org.launchcode.PlatePlanner.exception.InvalidTokenException;
import org.launchcode.PlatePlanner.exception.UserAlreadyExistException;
import org.launchcode.PlatePlanner.mailing.AccountVerificationEmailContext;
import org.launchcode.PlatePlanner.mailing.EmailService;
import org.launchcode.PlatePlanner.model.SecureToken;
import org.launchcode.PlatePlanner.model.User;
import org.launchcode.PlatePlanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Value("${site.base.url.https}")
    private String baseUrl;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecureTokenService secureTokenService;

    @Autowired
    private EmailService emailService;

    //Get All Users
    public List<User> findAll() {
        return userRepository.findAll();
    }

    //Get User By id
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    //Delete User
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    //Update User
    public void register(User user) throws UserAlreadyExistException {
        if (checkIfUserExist(user.getEmail())) {
            throw new UserAlreadyExistException("This user already exist");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        sendRegistrationConfirmationEmail(user);
    }

    public boolean checkIfUserExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public void sendRegistrationConfirmationEmail(User user) {
        SecureToken secureToken = secureTokenService.createToken();
        secureToken.setUser(user);
        secureTokenService.saveSecureToken(secureToken);

        AccountVerificationEmailContext context = new AccountVerificationEmailContext();
        context.init(user);
        context.setToken(secureToken.getToken());
        context.buildVerificationUrl(baseUrl, secureToken.getToken());

        try {
            emailService.sendMail(context);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public boolean verifyUser(String token) throws InvalidTokenException {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if (Objects.isNull(token) || !StringUtils.equals(token, secureToken.getToken()) || secureToken.isExpired()) {
            throw new InvalidTokenException("Token is not valid");
        }

        User user = userRepository.getById(secureToken.getUser().getId());
        if (Objects.isNull(user)) {
            return false;
        }

        user.setAccountVerified(true);
        userRepository.save(user);

        secureTokenService.removeToken(secureToken);
        return true;
    }

}