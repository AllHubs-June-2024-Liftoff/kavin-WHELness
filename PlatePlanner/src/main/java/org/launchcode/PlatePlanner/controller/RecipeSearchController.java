package org.launchcode.PlatePlanner.controller;

import org.launchcode.PlatePlanner.api.RecipeSearch;
import org.launchcode.PlatePlanner.model.Recipe;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("search-recipes")
@CrossOrigin(origins = "http://localhost:5173")
public class RecipeSearchController {

    @GetMapping
    public List<Recipe> getRecipesByQuery(@RequestParam(name = "q", required = false) String query, @RequestParam(name = "cat", required = false) String category) {
        if (query != null && query.length() > 0) {
            return RecipeSearch.getRecipeObjectsByName(query);
        } else if (category != null && category.length() > 0) {
            return RecipeSearch.getRecipeObjectsByCategory(category);
        } else {
            return new ArrayList<Recipe>();
        }
    }


}
