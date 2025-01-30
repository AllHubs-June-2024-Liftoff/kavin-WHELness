package org.launchcode.PlatePlanner.controller;

import jakarta.validation.Valid;
import org.launchcode.PlatePlanner.exception.UserAlreadyExistException;
import org.launchcode.PlatePlanner.model.User;
import org.launchcode.PlatePlanner.repository.UserRepository;
import org.launchcode.PlatePlanner.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllSavedUsers() {
        logger.info("In getAllSavedUsers...");
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Optional<User>> getSavedUser(@PathVariable("userId") Long userId) {
        logger.info("In getSavedUser...");
        if (userRepository.existsById(userId)) {
            logger.info("User with ID {} found...", userId);
            return ResponseEntity.ok(userRepository.findById(userId));
        } else {
            logger.warn("User with ID {} not found...", userId);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody @Valid User user, Errors errors) {
        logger.info("In createUser...");
        userRepository.save(user);
        if (errors.hasErrors()) {
            logger.error("Error creating user: {}", errors);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/update/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable("userId") Long userId, @RequestBody @Valid User user) {
        logger.info("In updateUser...");
        if (userRepository.existsById(userId)) {
            logger.info("User with ID {} found.  Updating...", userId);
            userRepository.save(user);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("User with ID {} not found...", userId);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/AddNew")
    public String addNew(User user, Model model) throws UserAlreadyExistException {
        userService.register(user);

        model.addAttribute("registrationSuccess",
                "You can check your email to complete your registration");
        return "security/registrationSuccessful";
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable("userId") Long userId) {
        logger.info("In deleteUser...");
        if (userRepository.existsById(userId)) {
            logger.info("User with ID {} found.  Deleting...", userId);
            userRepository.deleteById(userId);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("User with ID {} not found...", userId);
            return ResponseEntity.notFound().build();
        }
    }

}
