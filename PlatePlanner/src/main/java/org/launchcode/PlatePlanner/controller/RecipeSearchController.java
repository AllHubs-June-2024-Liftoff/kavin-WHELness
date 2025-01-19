package org.launchcode.PlatePlanner.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.launchcode.PlatePlanner.api.RecipeSearch;
import org.launchcode.PlatePlanner.model.Recipe;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("search-recipes")
@CrossOrigin(origins = "http://localhost:5173")
public class RecipeSearchController {

    @GetMapping
    public List<Recipe> getRecipesByQuery(@RequestParam(name = "q", required = false) String query, @RequestParam(name = "cat", required = false) String category, @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        String rawCookie = request.getHeader("Cookie");
        if (query != null && query.length() > 0) {
            return RecipeSearch.getRecipeObjectsByName(query);
        } else if (category != null && category.length() > 0) {
            return RecipeSearch.getRecipeObjectsByCategory(category);
        } else {
            return new ArrayList<Recipe>();
        }
    }


}
