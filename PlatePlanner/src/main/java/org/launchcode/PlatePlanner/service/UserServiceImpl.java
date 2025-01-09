package org.launchcode.PlatePlanner.service;

import org.launchcode.PlatePlanner.dto.UserDto;
import org.launchcode.PlatePlanner.model.Role;
import org.launchcode.PlatePlanner.model.User;
import org.launchcode.PlatePlanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

//    @Override
//    public User save(UserDto userDto) {
//        User user = new User(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()) , userDto.getRole(), userDto.getUsername());
//        return userRepository.save(user);
//    }

    @Override
    public User save(UserDto userDto) {
        Role role = Role.valueOf(userDto.getRole()); // Ensure this matches your enum
        User user = new User(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()), userDto.getUsername(), role);
        return userRepository.save(user);
    }


}