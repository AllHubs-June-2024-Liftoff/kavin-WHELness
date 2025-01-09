package org.launchcode.PlatePlanner.service;

import org.launchcode.PlatePlanner.dto.UserDto;
import org.launchcode.PlatePlanner.model.User;

public interface UserService {

    User save (UserDto userDto);
}