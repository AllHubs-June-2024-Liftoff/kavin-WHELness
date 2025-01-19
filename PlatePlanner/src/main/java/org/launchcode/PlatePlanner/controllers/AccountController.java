package org.launchcode.PlatePlanner.controllers;

import jakarta.validation.Valid;
import org.launchcode.PlatePlanner.model.RegisterDto;
import org.launchcode.PlatePlanner.model.Role;
import org.launchcode.PlatePlanner.model.User;
import org.launchcode.PlatePlanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class AccountController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public String profile(Authentication auth, Model model) {
        Optional<User> optUser = userRepository.findByEmail(((User) auth.getPrincipal()).getEmail());
        User user = optUser.get();
        model.addAttribute("appUser", user);

        return "profile";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        RegisterDto registerDto = new RegisterDto();
        model.addAttribute(registerDto);
        model.addAttribute("success", false);
        return "register";
    }

    @PostMapping("/register")
    public String register (
            Model model,
            @Valid @ModelAttribute RegisterDto registerDto,
            BindingResult result
    ) {

        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            result.addError(
                    new FieldError("registerDto", "confirmPassword"
                            , "Password and Confirm password do not match")
            );
        }

        Optional<User> user = userRepository.findByEmail(registerDto.getEmail());
        if (user.isPresent()) {
            result.addError(
                    new FieldError("registerDto", "email"
                            , "Email address is already used")
            );
        }

        if (result.hasErrors()) {
            return "register";
        }

        try {

            var bCryptEncoder = new BCryptPasswordEncoder();

            User newUser = new User();
            newUser.setFirstName(registerDto.getFirstName());
            newUser.setLastName(registerDto.getLastName());
            newUser.setEmail(registerDto.getEmail());
            newUser.setPhone(registerDto.getPhone());
            newUser.setAddress(registerDto.getAddress());
            newUser.setUsername(registerDto.getUsername());

            newUser.setRole(Role.USER);
            newUser.setPassword(bCryptEncoder.encode(registerDto.getPassword()));

            userRepository.save(newUser);

            model.addAttribute("registerDto", new RegisterDto());
            model.addAttribute("success", true);

        }
        catch (Exception ex) {
            result.addError(
                    new FieldError("registerDto", "firstName"
                            , ex.getMessage())
            );
        }

        return "register";
    }
}
