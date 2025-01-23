package org.launchcode.PlatePlanner.controller;

import jakarta.validation.Valid;
import org.launchcode.PlatePlanner.model.MealPlan;
import org.launchcode.PlatePlanner.model.Recipe;
import org.launchcode.PlatePlanner.model.ShoppingList;
import org.launchcode.PlatePlanner.model.ShoppingListItem;
import org.launchcode.PlatePlanner.repository.MealPlanRepository;
import org.launchcode.PlatePlanner.repository.ShoppingListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("shopping-list")
@CrossOrigin(origins = "http://localhost:5173")
public class ShoppingListController {

    Logger logger = LoggerFactory.getLogger(ShoppingListController.class);

    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @GetMapping("/all")
    public ResponseEntity<List<ShoppingList>> getAllSavedShoppingLists() {
        logger.info("In getAllSavedShoppingLists...");
        return ResponseEntity.ok(shoppingListRepository.findAll());
    }

    @GetMapping("/{shoppingListId}")
    public ResponseEntity<Optional<ShoppingList>> getSavedShoppingList(@PathVariable("shoppingListId") Long shoppingListId) {
        logger.info("In getSavedShoppingList...");
        if (shoppingListRepository.existsById(shoppingListId)) {
            logger.info("ShoppingList with ID {} found...", shoppingListId);
            return ResponseEntity.ok(shoppingListRepository.findById(shoppingListId));
        } else {
            logger.warn("ShoppingList with ID {} not found...", shoppingListId);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createShoppingList(@RequestBody @Valid ShoppingList shoppingList, Errors errors) {
        logger.info("In createShoppingList...");
        shoppingListRepository.save(shoppingList);
        if (errors.hasErrors()) {
            logger.error("Error creating shoppingList: {}", errors);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/update/{shoppingListId}")
    public ResponseEntity<Object> updateShoppingList(@PathVariable("shoppingListId") Long shoppingListId, @RequestBody @Valid ShoppingList shoppingList) {
        logger.info("In updateShoppingList...");
        if (shoppingListRepository.existsById(shoppingListId)) {
            logger.info("ShoppingList with ID {} found.  Updating...", shoppingListId);
            shoppingListRepository.save(shoppingList);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("ShoppingList with ID {} not found...", shoppingListId);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{shoppingListId}")
    public ResponseEntity<Object> deleteShoppingList(@PathVariable("shoppingListId") Long shoppingListId) {
        logger.info("In deleteShoppingList...");
        if (shoppingListRepository.existsById(shoppingListId)) {
            logger.info("ShoppingList with ID {} found.  Deleting...", shoppingListId);
            shoppingListRepository.deleteById(shoppingListId);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("ShoppingList with ID {} not found...", shoppingListId);
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/shopping-list/{shoppingListId}/sync-with-meal-plan/{mealPlanId}")
    public ResponseEntity<ShoppingList> syncShoppingListWithMealPlan(@PathVariable("shoppingListId") Long shoppingListId, @PathVariable("mealPlanId") Long mealPlanId) {

        logger.info("In syncShoppingListWithMealPlan... shoppingListId={}, mealPlanId={}",
                shoppingListId, mealPlanId);

        Optional<ShoppingList> optionalShoppingList = shoppingListRepository.findById(shoppingListId);
        if (optionalShoppingList.isEmpty()) {
            logger.warn("ShoppingList with ID {} not found...", shoppingListId);
            return ResponseEntity.notFound().build();
        }
        ShoppingList shoppingList = optionalShoppingList.get();

        Optional<MealPlan> optionalMealPlan = mealPlanRepository.findById(mealPlanId);
        if (optionalMealPlan.isEmpty()) {
            logger.warn("MealPlan with ID {} not found...", mealPlanId);
            return ResponseEntity.notFound().build();
        }
        MealPlan mealPlan = optionalMealPlan.get();

        logger.info("Clearing existing items from ShoppingList ID {}", shoppingListId);
        shoppingList.getShoppingListItems().clear();

        logger.info("Aggregating items from MealPlan ID {}", mealPlanId);

        if (mealPlan.getMealPlanRecipes() != null) {
            mealPlan.getMealPlanRecipes().forEach(mpr -> {
                Recipe recipe = mpr.getRecipe();
                if (recipe != null && recipe.getRecipeIngredients() != null) {
                    recipe.getRecipeIngredients().forEach(recipeIngredient -> {

                        ShoppingListItem item = new ShoppingListItem();
                        item.setIngredient(recipeIngredient.getIngredient());
                        item.setQuantity(recipeIngredient.getQuantity());
                        item.setUnit(recipeIngredient.getUnit());

                        item.setShoppingList(shoppingList);
                        shoppingList.addShoppingListItem(item);

                    });
                }
            });
        }

        shoppingListRepository.save(shoppingList);
        logger.info("ShoppingList ID {} updated successfully from MealPlan ID {}.", shoppingListId, mealPlanId);

        return ResponseEntity.ok(shoppingList);
    }
}
