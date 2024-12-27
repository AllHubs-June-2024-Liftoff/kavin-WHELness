package org.launchcode.PlatePlanner.api;

import org.launchcode.PlatePlanner.model.Recipe;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RecipeSearch {

    private static RestTemplate restTemplate = new RestTemplate();

    public static String getRecipesByCategory(String category) {
        String url = "https://www.themealdb.com/api/json/v1/1/filter.php?c={category}";
        return restTemplate.getForObject(url, String.class, category);
    }

    public static List<Recipe> getRecipeObjectsByCategory(String category) {
        String url = "https://www.themealdb.com/api/json/v1/1/filter.php?c={category}";
        List recipes = new ArrayList<Recipe>();

        MealDbMeals meals = restTemplate.getForObject(url, MealDbMeals.class, category);
        if (meals.getMeals() != null && meals.getMeals().length > 0) {
            recipes = Arrays.stream(meals.getMeals()).map(meal -> meal.convertToRecipe()).toList();
        }

        return recipes;
    }

    public static String getRecipesByName(String name) {
        String url = "https://www.themealdb.com/api/json/v1/1/search.php?s={name}";
        return restTemplate.getForObject(url, String.class, name);
    }

    public static List<Recipe> getRecipeObjectsByName(String name) {
        String url = "https://www.themealdb.com/api/json/v1/1/search.php?s={name}";
        List recipes = new ArrayList<Recipe>();

        MealDbMeals meals = restTemplate.getForObject(url, MealDbMeals.class, name);
        if (meals.getMeals() != null && meals.getMeals().length > 0) {
            recipes = Arrays.stream(meals.getMeals()).map(meal -> meal.convertToRecipe()).toList();
        }
        
        return recipes;
    }
}
