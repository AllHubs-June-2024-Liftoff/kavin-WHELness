package org.launchcode.PlatePlanner.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @GetMapping({"", "/"})
    public String home() {
        return "index";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/store/user")
    public String userPage() {
        return "user";
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/store/admin")
    public String adminPage() {
        return "admin";
    }

}