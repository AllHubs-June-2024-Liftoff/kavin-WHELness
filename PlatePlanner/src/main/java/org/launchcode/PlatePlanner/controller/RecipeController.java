package org.launchcode.PlatePlanner.controller;


import jakarta.validation.Valid;
import org.launchcode.PlatePlanner.model.Recipe;
import org.launchcode.PlatePlanner.model.Tag;
import org.launchcode.PlatePlanner.repository.RecipeRepository;
import org.launchcode.PlatePlanner.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("recipe")
@CrossOrigin(origins = "http://localhost:5173")
public class RecipeController {

    Logger logger = LoggerFactory.getLogger(RecipeController.class);

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Recipe>> getAllSavedRecipes() {
        logger.info("In getAllSavedRecipes...");
        return ResponseEntity.ok(recipeRepository.findAll());
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<Optional<Recipe>> getSavedRecipe(@PathVariable("recipeId") Long recipeId) {
        logger.info("In getSavedRecipe...");
        if (recipeRepository.existsById(recipeId)) {
            logger.info("Recipe with ID {} found...", recipeId);
            return ResponseEntity.ok(recipeRepository.findById(recipeId));
        } else {
            logger.warn("Recipe with ID {} not found...", recipeId);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createRecipe(@RequestBody @Valid Recipe recipe, Errors errors) {
        logger.info("In createRecipe...");
        recipe.assignToIngredients();
        recipeRepository.save(recipe);
        if (errors.hasErrors()) {
            logger.error("Error creating recipe: {}", errors);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/update/{recipeId}")
    public ResponseEntity<Object> updateRecipe(@PathVariable("recipeId") Long recipeId, @RequestBody @Valid Recipe recipe) {
        logger.info("In updateRecipe...");
        if (recipeRepository.existsById(recipeId)) {
            logger.info("Recipe with ID {} found.  Updating...", recipeId);
            recipe.assignToIngredients();
            recipeRepository.save(recipe);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Recipe with ID {} not found...", recipeId);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{recipeId}")
    public ResponseEntity<Object> deleteRecipe(@PathVariable("recipeId") Long recipeId) {
        logger.info("In deleteRecipe...");
        if (recipeRepository.existsById(recipeId)) {
            logger.info("Recipe with ID {} found.  Deleting...", recipeId);
            recipeRepository.deleteById(recipeId);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Recipe with ID {} not found...", recipeId);
            return ResponseEntity.notFound().build();
        }
    }

    //Add tags to recipe by ID
    @PutMapping("/add-tags/{recipeId}")
    public ResponseEntity<Recipe> addTagsToRecipe(@PathVariable("recipeId") Long recipeId, @RequestBody @Valid List<Long> tagIds) {
        logger.info("In addTagsToRecipe...");
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if (optionalRecipe.isEmpty()) {
            logger.warn("Recipe with ID {} not found...", recipeId);
            return ResponseEntity.notFound().build();
        }

        Recipe recipe = optionalRecipe.get();

        List<Tag> tags = tagRepository.findAllById(tagIds);
        if (tags.isEmpty()) {
            logger.warn("Tag(s) with ID(s) {} not found...", tagIds);
            return ResponseEntity.notFound().build();
        }

        logger.info("Tag(s) with ID(s) {} found...", tagIds);

        for (Tag tag : tags) {
            recipe.addTag(tag);
        }

        Recipe updatedRecipe = recipeRepository.save(recipe);

        return ResponseEntity.ok(updatedRecipe);
    }

    //Add and remove tags to recipe by ID
    //Removes all tags currently on recipe that are not in submitted list.
    @PutMapping("/update-tags/{recipeId}")
    public ResponseEntity<Recipe> updateTagsToRecipe(@PathVariable("recipeId") Long recipeId, @RequestBody @Valid List<Long> tagIds, @AuthenticationPrincipal UserDetails userDetails) {
        logger.info("In updateTagsToRecipe...");
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if (optionalRecipe.isEmpty()) {
            logger.warn("Recipe with ID {} not found...", recipeId);
            return ResponseEntity.notFound().build();
        }

        Recipe recipe = optionalRecipe.get();

        //Remove all tags first.  Later add any submitted.
        recipe.setTags(new HashSet<>());

        if (tagIds != null && tagIds.size() > 0) {
            List<Tag> tags = tagRepository.findAllById(tagIds);
            if (tags.isEmpty()) {
                logger.warn("Tag(s) with ID(s) {} not found...", tagIds);
                return ResponseEntity.notFound().build();
            }
            //Add tags submitted.
            for (Tag tag : tags) {
                recipe.addTag(tag);
            }
        }

        Recipe updatedRecipe = recipeRepository.save(recipe);

        return ResponseEntity.ok(updatedRecipe);
    }

}
