package org.launchcode.PlatePlanner.config;

import org.launchcode.PlatePlanner.model.*;
import org.launchcode.PlatePlanner.repository.*;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class DataSeeder {

    @Bean
    public ApplicationRunner seedData(
            UserRepository userRepository,
            RecipeRepository recipeRepository,
            IngredientRepository ingredientRepository,
            TagRepository tagRepository,
            MealPlanRepository mealPlanRepository,
            ShoppingListRepository shoppingListRepository) {
        return args -> {

            System.out.println("DataSeeder is running...");

            if (tagRepository.count() < 5) {
                System.out.println("Seeding tag data...");

                Tag tag1 = new Tag("Vegan", "Veganism is both the practice of abstaining from the use of animal products, particularly in diet.");
                Tag tag2 = new Tag("Vegetarian", "Vegetarianism is the practice of abstaining from the consumption of meat (red meat, poultry, seafood, and the flesh of any other animal).");
                Tag tag3 = new Tag("Dessert", "Dessert is a course that concludes a meal. The course usually consists of sweet foods, such as confections dishes or fruit.");
                Tag tag4 = new Tag("Side", "A side dish, sometimes referred to as a side order, side item, or simply a side, is a food item that accompanies the entrée or main course at a meal.");
                Tag tag5 = new Tag("Seafood", "Seafood is any form of sea life regarded as food by humans. Seafood prominently includes fish and shellfish.");

                tagRepository.save(tag1);
                tagRepository.save(tag2);
                tagRepository.save(tag3);
                tagRepository.save(tag4);
                tagRepository.save(tag5);

                System.out.println("Tags data saved.");
            }

            if (userRepository.count() == 0) {
                System.out.println("Seeding user data...");

                User user1 = new User("John Doe", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd2f6927f7d3b663f6", "john.doe@nomail.com", Role.USER);
                User user2 = new User("Jane Doe", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd2f6927f7d3b663f7", "jane.doe@nomail.com", Role.ADMIN);

                userRepository.save(user1);
                userRepository.save(user2);

                System.out.println("Users data saved.");
            }

            if(recipeRepository.count() == 0) {
                System.out.println("Seeding recipe data...");

                Ingredient spaghetti = new Ingredient("Spaghetti");
                Ingredient garlic = new Ingredient("Sliced Garlic");
                Ingredient oliveOil = new Ingredient("Olive Oil");
                Ingredient parmesan = new Ingredient("Parmesan");
                Ingredient parsley = new Ingredient("Parsley");

                //Recipe 1

                Recipe recipe1 = new Recipe(
                        "Spaghetti Aglio e Olio",
                        "Simple, yet classic pasta dish",
                        new HashSet<>(),
                        "1. Boil pasta in salted water according to package instructions. 2. Add 1/3 cup of oil to a small pan and heat over medium heat. Add sliced garlic and cook until just slightly darker in color, about 8 minutes. 3. When pasta is al dente, drain the pasta and return to the pot. Add garlic and oil. Toss with parmesan cheese and parsley. Enjoy.",
                        540,
                        15,
                        "https://img.ccnull.de/1100000/preview/1100543_f6104414c0835273e0fb5f6b62df80d5.jpg",
                        null,
                        null
                );

                RecipeIngredient recipeIngredient1 = new RecipeIngredient(recipe1, spaghetti, 1, "lb");
                RecipeIngredient recipeIngredient2 = new RecipeIngredient(recipe1, garlic, 1, "head");
                RecipeIngredient recipeIngredient3 = new RecipeIngredient(recipe1, oliveOil, .333, "cup");
                RecipeIngredient recipeIngredient4 = new RecipeIngredient(recipe1, parmesan, .25, "cup");
                RecipeIngredient recipeIngredient5 = new RecipeIngredient(recipe1, parsley, 3, "tbsp");

                recipe1.addRecipeIngredient(recipeIngredient1);
                recipe1.addRecipeIngredient(recipeIngredient2);
                recipe1.addRecipeIngredient(recipeIngredient3);
                recipe1.addRecipeIngredient(recipeIngredient4);
                recipe1.addRecipeIngredient(recipeIngredient5);

                System.out.println("Recipe 1 Ingredients:" + recipe1.getRecipeIngredients());

                System.out.println("Saving recipe 1 and ingredients");
                recipeRepository.save(recipe1);
                ingredientRepository.save(spaghetti);
                ingredientRepository.save(garlic);
                ingredientRepository.save(oliveOil);
                ingredientRepository.save(parmesan);
                ingredientRepository.save(parsley);
                System.out.println("Recipe 1 saved.");

                //Recipe 2

                Ingredient bread = new Ingredient("Bread");
                Ingredient butter = new Ingredient("Butter");
                Ingredient cheddarCheese = new Ingredient("Cheddar Cheese");

                Recipe recipe2 = new Recipe(
                        "Grilled Cheese",
                        "A simple grilled cheese sandwich",
                        new HashSet<>(),
                        "1. Heat a pan over medium heat. 2. Butter one side of each slice of bread. 3. Place one slice of bread, buttered side down, in the pan. 4. Add cheddar cheese slices on top. 5. Cover with the second slice of bread, buttered side up. 6. Grill until golden brown on both sides and the cheese is melted.",
                        450,
                        10,
                        "https://live.staticflickr.com/3637/3407778423_77f1512400_z.jpg",
                        null,
                        null
                );

                RecipeIngredient recipeIngredient6 = new RecipeIngredient(recipe2, bread, 2, "slices");
                RecipeIngredient recipeIngredient7 = new RecipeIngredient(recipe2, butter, 2, "tbsp");
                RecipeIngredient recipeIngredient8 = new RecipeIngredient(recipe2, cheddarCheese, 2, "slices");

                recipe2.addRecipeIngredient(recipeIngredient6);
                recipe2.addRecipeIngredient(recipeIngredient7);
                recipe2.addRecipeIngredient(recipeIngredient8);

                System.out.println("Saving recipe 2 and ingredients");
                recipeRepository.save(recipe2);
                ingredientRepository.save(bread);
                ingredientRepository.save(butter);
                ingredientRepository.save(cheddarCheese);
                System.out.println("Recipe 2 saved.");
            }

//            if (mealPlanRepository.count() == 0) {
//                System.out.println("Seeding meal plan");
//
//                Optional<User> optionalUser = userRepository.findById(1L);
//
//                if (optionalUser.isEmpty()) {
//                    throw new RuntimeException("User not found");
//                }
//
//                User user1 = optionalUser.get();
//
//                List<Recipe> recipeSet = new ArrayList<>(recipeRepository.findAll());
//
//                if (recipeSet.isEmpty()) {
//                    throw new RuntimeException("Recipes not found");
//                }
//
//                MealPlan testMealPlan = new MealPlan(user1, "Test Meal Plan");
//                System.out.println("Meal plan (" + testMealPlan.toString() + ") created and recipes added. Now saving meal plan to database.");
//
//                MealPlanRecipe mealPlanRecipe1 = new MealPlanRecipe(testMealPlan, recipeSet.get(0), DayOfTheWeek.SUNDAY);
//                MealPlanRecipe mealPlanRecipe2 = new MealPlanRecipe(testMealPlan, recipeSet.get(1), DayOfTheWeek.SUNDAY);
//                MealPlanRecipe mealPlanRecipe3 = new MealPlanRecipe(testMealPlan, recipeSet.get(1), DayOfTheWeek.MONDAY);
//                MealPlanRecipe mealPlanRecipe4 = new MealPlanRecipe(testMealPlan, recipeSet.get(1), DayOfTheWeek.TUESDAY);
//                MealPlanRecipe mealPlanRecipe5 = new MealPlanRecipe(testMealPlan, recipeSet.get(1), DayOfTheWeek.WEDNESDAY);
//                MealPlanRecipe mealPlanRecipe6 = new MealPlanRecipe(testMealPlan, recipeSet.get(1), DayOfTheWeek.THURSDAY);
//                MealPlanRecipe mealPlanRecipe7 = new MealPlanRecipe(testMealPlan, recipeSet.get(1), DayOfTheWeek.FRIDAY);
//                MealPlanRecipe mealPlanRecipe8 = new MealPlanRecipe(testMealPlan, recipeSet.get(1), DayOfTheWeek.SATURDAY);
//
//                testMealPlan.addMealPlanRecipe(mealPlanRecipe1);
//                testMealPlan.addMealPlanRecipe(mealPlanRecipe2);
//                testMealPlan.addMealPlanRecipe(mealPlanRecipe3);
//                testMealPlan.addMealPlanRecipe(mealPlanRecipe4);
//                testMealPlan.addMealPlanRecipe(mealPlanRecipe5);
//                testMealPlan.addMealPlanRecipe(mealPlanRecipe6);
//                testMealPlan.addMealPlanRecipe(mealPlanRecipe7);
//                testMealPlan.addMealPlanRecipe(mealPlanRecipe8);
//
//                System.out.println("MealPlanRecipe objects created and added to MealPlan");
//
//                mealPlanRepository.save(testMealPlan);
//
//                System.out.println("Meal plan saved.");
//            }


//            if (shoppingListRepository.count() == 0) {
//                System.out.println("Creating an empty shopping list...");
//
//                Optional<User> optionalUser = userRepository.findById(1L);
//
//                if (optionalUser.isEmpty()) {
//                    throw new RuntimeException("User not found");
//                }
//
//                User user1 = optionalUser.get();
//
//                Optional<MealPlan> optionalMealPlan = mealPlanRepository.findById(1L);
//
//                if (optionalMealPlan.isEmpty()) {
//                    throw new RuntimeException("Meal plan not found");
//                }
//
//                MealPlan mealPlan1 = optionalMealPlan.get();
//                Set<ShoppingListItem> shoppingListItems = new HashSet<>();
//
//                ShoppingList emptyList = new ShoppingList(mealPlan1, user1, shoppingListItems);
//
//                shoppingListRepository.save(emptyList);
//
//                System.out.println("Empty shopping list created for user: " + user1.getUsername() +
//                        ", linked to meal plan: " + mealPlan1.getName());
//            }

        };
    }
}
