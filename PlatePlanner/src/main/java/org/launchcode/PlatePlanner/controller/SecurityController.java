package org.launchcode.PlatePlanner.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.launchcode.PlatePlanner.api.RecipeSearch;
import org.launchcode.PlatePlanner.model.Recipe;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("secure-access")
@CrossOrigin(origins = "http://localhost:5173")
public class SecurityController {

    /*
    @GetMapping
    public String processLogin(CookieValue("JSESSIONID") String jSessionId)

    {
        if (query != null && query.length() > 0) {
            return RecipeSearch.getRecipeObjectsByName(query);
        } else if (category != null && category.length() > 0) {
            return RecipeSearch.getRecipeObjectsByCategory(category);
        } else {
            return new ArrayList<Recipe>();
        }
    }
     */

    @GetMapping("/process-login")
    public String processLogin(HttpServletRequest request, HttpServletResponse response, @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        UserDetails user = userDetails;
        String userName = userDetails.getUsername();
        // create a cookie
        Cookie cookie = new Cookie("username", userName);
        cookie.setPath("/");
        //add cookie to response
        response.addCookie(cookie);

        /*
        String rawCookie = request.getHeader("Cookie");
        /*
        String[] rawCookieParams = rawCookie.split(";");
        for(String rawCookieNameAndValue :rawCookieParams)
        {
            String[] rawCookieNameAndValuePair = rawCookieNameAndValue.split("=");
        }
        */


        response.sendRedirect("http://localhost:5173");

        return "Success";
    }

    @GetMapping("/process-logout")
    public String processLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Cookie cookie = new Cookie("username", ""); // Create a cookie with the same name
        cookie.setMaxAge(0); // Set the max age to 0 to delete the cookie
        cookie.setPath("/"); // Set the path to match the cookie you want to remove
        response.addCookie(cookie); // Add the cookie to the response
        response.sendRedirect("http://localhost:5173");

        return "Success";

    }


}


