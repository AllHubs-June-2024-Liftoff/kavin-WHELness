package org.launchcode.PlatePlanner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Recipe extends AbstractEntity {

    @ManyToMany
    @JoinTable(
            name = "user_recipe",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> user = new HashSet<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"recipe"})
    private List<MealPlanRecipe> mealPlanRecipes = new ArrayList<>();

    @NotNull
    @Length(max=75, message="Recipe name cannot exceed 75 characters.")
    private String name;

    @NotNull
    @Length(max=255, message="Recipe description cannot exceed 255 characters.")
    private String description;

    @NotNull
    @Size(min=1, max=25, message="Number of ingredients must be between 1 and 25.")
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeIngredient> recipeIngredients = new HashSet<>();

    @NotNull
    @Lob
    private String instructions;

    @Max(value=5000, message="Calories cannot exceed 5000.")
    private int calories;

    @Max(value=240, message="Prep time cannot exceed 240 minutes.")
    private int prepTimeMinutes;

    @URL
    private String imageURL;

    @URL
    private String sourceURL;

    @ManyToMany
    @JsonIgnoreProperties({"recipes"})
    @JoinTable(
            name = "recipe_tag",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    public Recipe() {}

    // Constructor for required fields
    public Recipe(String name, String description, Set<RecipeIngredient> recipeIngredients, String instructions) {
        this.name = name;
        this.description = description;
        this.recipeIngredients = recipeIngredients;
        this.instructions = instructions;
    }

    // Constructor for all fields
    public Recipe(String name, String description, Set<RecipeIngredient> recipeIngredients, String instructions, int calories, int prepTimeMinutes, String imageURL, String sourceURL, Set<Tag> tags) {
        this.name = name;
        this.description = description;
        this.recipeIngredients = recipeIngredients;
        this.instructions = instructions;
        this.calories = calories;
        this.prepTimeMinutes = prepTimeMinutes;
        this.imageURL = imageURL;
        this.sourceURL = sourceURL;
        this.tags = tags;
    }

    public List<MealPlanRecipe> getMealPlanRecipes() {
        return mealPlanRecipes;
    }

    public void setMealPlanRecipes(List<MealPlanRecipe> mealPlanRecipes) {
        this.mealPlanRecipes = mealPlanRecipes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(Set<RecipeIngredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getPrepTimeMinutes() {
        return prepTimeMinutes;
    }

    public void setPrepTimeMinutes(int prepTimeMinutes) {
        this.prepTimeMinutes = prepTimeMinutes;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSourceURL() {
        return sourceURL;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void addRecipeIngredient(RecipeIngredient recipeIngredient) {
        recipeIngredients.add(recipeIngredient);
        recipeIngredient.setRecipe(this);
        System.out.println("Added recipeIngredient: " + recipeIngredient);
        System.out.println("Current Set of Ingredients: " + recipeIngredients);
    }

    public void removeRecipeIngredient(RecipeIngredient recipeIngredient) {
        recipeIngredients.remove(recipeIngredient);
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    public void assignToIngredients() {
        for (RecipeIngredient ingredient : this.recipeIngredients) {
            ingredient.setRecipe(this);
        }
    }

    @Override
    public String toString() {
        return "Recipe: " + this.name;
    }
}
