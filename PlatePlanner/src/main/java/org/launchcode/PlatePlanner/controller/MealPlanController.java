package org.launchcode.PlatePlanner.controller;

import jakarta.validation.Valid;
import org.launchcode.PlatePlanner.model.MealPlan;
import org.launchcode.PlatePlanner.model.Recipe;
import org.launchcode.PlatePlanner.model.User;
import org.launchcode.PlatePlanner.repository.MealPlanRepository;
import org.launchcode.PlatePlanner.repository.RecipeRepository;
import org.launchcode.PlatePlanner.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("meal-plan")
@CrossOrigin(origins = "http://localhost:5173")
public class MealPlanController {

    Logger logger = LoggerFactory.getLogger(MealPlanController.class);

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping("/all")
    public ResponseEntity<List<MealPlan>> getAllSavedMealPlans() {
        logger.info("In getAllSavedMealPlans...");
        return ResponseEntity.ok(mealPlanRepository.findAll());
    }

    @GetMapping("/{mealPlanId}")
    public ResponseEntity<Optional<MealPlan>> getSavedMealPlan(@PathVariable("mealPlanId") Long mealPlanId) {
        logger.info("In getSavedMealPlan...");
        if (mealPlanRepository.existsById(mealPlanId)) {
            logger.info("MealPlan with ID {} found...", mealPlanId);
            return ResponseEntity.ok(mealPlanRepository.findById(mealPlanId));
        } else {
            logger.warn("MealPlan with ID {} not found...", mealPlanId);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createMealPlan(@RequestBody @Valid MealPlan mealPlan, Errors errors) {
        logger.info("In createMealPlan...");
        mealPlanRepository.save(mealPlan);
        if (errors.hasErrors()) {
            logger.error("Error creating mealPlan: {}", errors);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/update/{mealPlanId}")
    public ResponseEntity<Object> updateMealPlan(@PathVariable("mealPlanId") Long mealPlanId, @RequestBody @Valid MealPlan mealPlan) {
        logger.info("In updateMealPlan...");
        if (mealPlanRepository.existsById(mealPlanId)) {
            logger.info("MealPlan with ID {} found.  Updating...", mealPlanId);
            mealPlanRepository.save(mealPlan);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("MealPlan with ID {} not found...", mealPlanId);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{mealPlanId}")
    public ResponseEntity<Object> deleteMealPlan(@PathVariable("mealPlanId") Long mealPlanId) {
        logger.info("In deleteMealPlan...");
        if (mealPlanRepository.existsById(mealPlanId)) {
            logger.info("MealPlan with ID {} found.  Deleting...", mealPlanId);
            mealPlanRepository.deleteById(mealPlanId);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("MealPlan with ID {} not found...", mealPlanId);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MealPlan>> getAllUserMealPlans(@PathVariable("userId") Long userId) {
        logger.info("In getAllUserMealPlans...");
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            logger.warn("User with ID {} not found...", userId);
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();

        Set<MealPlan> mealPlanSet = user.getMealPlans();
        if(mealPlanSet.isEmpty()) {
            logger.warn("No MealPlan(s) found for user with ID {}...", userId);
        }

        List<MealPlan> mealPlanList = new ArrayList<>(mealPlanSet);
        return ResponseEntity.ok(mealPlanList);
    }

    //POST Create new user meal plan
    @PostMapping("/user/{userId}")
    public ResponseEntity<MealPlan> createUserMealPlan(@PathVariable("userId") Long userId, @Valid @RequestBody MealPlan newMealPlan, Errors errors) {
        logger.info("In createUserMealPlan...");
        if (errors.hasErrors()) {
            logger.error("Error creating mealPlan: {}", errors);
            throw new IllegalArgumentException("Validation errors: " + errors.getAllErrors());
        }
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            logger.warn("User with ID {} not found...", userId);
            return ResponseEntity.notFound().build();
        }
        User user = optionalUser.get();
        newMealPlan.setUser(user);

        MealPlan savedMealPlan = mealPlanRepository.save(newMealPlan);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedMealPlan);
    }

    //Add recipe to meal plan by ID
    @PutMapping("/{mealPlanId}/add-recipe/{recipeId}")
    public ResponseEntity<MealPlan> addRecipeToMealPlan(@PathVariable("recipeId") Long recipeId, @PathVariable("mealPlanId") Long mealPlanId) {
        logger.info("In addRecipeToMealPlan...");
        Optional<MealPlan> optionalMealPlan = mealPlanRepository.findById(mealPlanId);
        if (optionalMealPlan.isEmpty()) {
            logger.warn("MealPlan with ID {} not found...", mealPlanId);
            return ResponseEntity.notFound().build();
        }

        logger.info("MealPlan with ID {} found...", mealPlanId);
        MealPlan mealPlan = optionalMealPlan.get();

        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if (optionalRecipe.isEmpty()) {
            logger.warn("Recipe with ID {} not found...", recipeId);
            return ResponseEntity.notFound().build();
        }

        logger.info("Recipe with ID {} found...", recipeId);
        Recipe recipe = optionalRecipe.get();

        mealPlan.addRecipe(recipe);

        MealPlan updatedMealPlan = mealPlanRepository.save(mealPlan);

        return ResponseEntity.ok(updatedMealPlan);
    }

    @GetMapping("/test-meal-plan")
    public ResponseEntity<MealPlan> getTestMealPlan() {
        logger.info("In getTestMealPlan...");

        Optional<MealPlan> optionalMealPlan = mealPlanRepository.findById(1L);
        if (optionalMealPlan.isEmpty()) {
            logger.warn("MealPlan with ID 1 not found.");
            return ResponseEntity.notFound().build();
        }

        MealPlan testMealPlan = optionalMealPlan.get();
        logger.info("Found Meal Plan 1: {}", testMealPlan.getName());

        return ResponseEntity.ok(testMealPlan);
    }

}
