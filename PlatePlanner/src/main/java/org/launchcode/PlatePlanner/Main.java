package org.launchcode.PlatePlanner;

import org.launchcode.PlatePlanner.api.RecipeSearch;
import org.launchcode.PlatePlanner.controller.RecipeController;
import org.launchcode.PlatePlanner.model.Recipe;
import org.launchcode.PlatePlanner.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("I am Groot!");

        /*
        String json = RecipeSearch.getRecipesByCategory("Seafood");
        System.out.println(json);

        json = RecipeSearch.getRecipesByName("Arrabiata");
        System.out.println(json);

        List<Recipe> recipes = RecipeSearch.getRecipeObjectsByCategory("Seafood");
        System.out.println(recipes);

        recipes = RecipeSearch.getRecipeObjectsByName("Arrabiata");
        System.out.println(recipes);
         */

        //RecipeController controller = new RecipeController();
        //ResponseEntity<List<Recipe>> recipes = controller.getAllSavedRecipes();
        //System.out.println(recipes);
    }

}
