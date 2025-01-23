package org.launchcode.PlatePlanner.controller;

import org.json.JSONException;
import org.json.JSONObject;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.launchcode.PlatePlanner.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("secure-access")
@CrossOrigin(origins = "http://localhost:5173")
public class SecurityController {

    @GetMapping("/process-login")
    public String processLogin(HttpServletResponse response, @AuthenticationPrincipal User userDetails) throws IOException, JSONException {

        // packs all the user information into a JSON object so we don't have to return a big concatenated string
        String userJson = new JSONObject()
                .put("username", userDetails.getUsername())
                .put("id", userDetails.getId().toString())
                .put("firstName", userDetails.getFirstName())
                .put("lastName", userDetails.getLastName())
                .put("email", userDetails.getEmail())
                .put("phone", userDetails.getPhone())
                .put("address", userDetails.getAddress())
                .put("role", userDetails.getRole().toString())
                .put("createdAt", userDetails.getCreatedAt().toString())
                .toString();

        // encodes the string so any special characters are handled properly
        String encodedUserJson = URLEncoder.encode(userJson, StandardCharsets.UTF_8);
        // create a cookie with the user details and add it to the response
        Cookie cookie = new Cookie("userDetails", encodedUserJson);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.sendRedirect("http://localhost:5173");

        return "Success";
    }

    @GetMapping("/process-logout")
    public String processLogout(HttpServletResponse response) throws IOException {
        Cookie cookie = new Cookie("userDetails", null);
        cookie.setMaxAge(0); // Set the max age to 0 to delete the cookie
        cookie.setPath("/"); // Set the path to match the cookie you want to remove
        response.addCookie(cookie); // Add the cookie to the response
        response.sendRedirect("http://localhost:5173");

        return "Success";
    }
}

