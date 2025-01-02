package org.launchcode.PlatePlanner.controller;

import jakarta.validation.Valid;
import org.launchcode.PlatePlanner.dto.UserDto;
import org.launchcode.PlatePlanner.model.User;
import org.launchcode.PlatePlanner.repository.UserRepository;
import org.launchcode.PlatePlanner.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
//@RestController
//@RequestMapping("user")
//@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;


    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user") UserDto userDto) {
        return "register";
    }

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user") UserDto userDto, Model model) {
        userService.save(userDto);
        model.addAttribute("message", "Registered Successfuly!");
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("user-page")
    public String userPage(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "user";
    }

    @GetMapping("admin-page")
    public String adminPage(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "admin";
    }
}
//    Logger logger = LoggerFactory.getLogger(UserController.class);
//
//    @Autowired
//    private UserRepository userRepository;
//
//
//    @GetMapping("/all")
//    public ResponseEntity<List<User>> getAllSavedUsers() {
//        logger.info("In getAllSavedUsers...");
//        return ResponseEntity.ok(userRepository.findAll());
//    }
//
//    @GetMapping("/{userId}")
//    public ResponseEntity<Optional<User>> getSavedUser(@PathVariable("userId") Long userId) {
//        logger.info("In getSavedUser...");
//        if (userRepository.existsById(userId)) {
//            logger.info("User with ID {} found...", userId);
//            return ResponseEntity.ok(userRepository.findById(userId));
//        } else {
//            logger.warn("User with ID {} not found...", userId);
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PostMapping("/create")
//    public ResponseEntity<Object> createUser(@RequestBody @Valid User user, Errors errors) {
//        logger.info("In createUser...");
//        userRepository.save(user);
//        if (errors.hasErrors()) {
//            logger.error("Error creating user: {}", errors);
//        }
//        return ResponseEntity.noContent().build();
//    }
//
//    @PostMapping("/update/{userId}")
//    public ResponseEntity<Object> updateUser(@PathVariable("userId") Long userId, @RequestBody @Valid User user) {
//        logger.info("In updateUser...");
//        if (userRepository.existsById(userId)) {
//            logger.info("User with ID {} found.  Updating...", userId);
//            userRepository.save(user);
//            return ResponseEntity.noContent().build();
//        } else {
//            logger.warn("User with ID {} not found...", userId);
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/delete/{userId}")
//    public ResponseEntity<Object> deleteUser(@PathVariable("userId") Long userId) {
//        logger.info("In deleteUser...");
//        if (userRepository.existsById(userId)) {
//            logger.info("User with ID {} found.  Deleting...", userId);
//            userRepository.deleteById(userId);
//            return ResponseEntity.noContent().build();
//        } else {
//            logger.warn("User with ID {} not found...", userId);
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//}
