package org.launchcode.PlatePlanner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MealPlan extends AbstractEntity {

    @ManyToOne
    @NotNull
    @JsonIgnoreProperties({"mealPlans"})
    private User user;

    @OneToMany(mappedBy = "mealPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"mealPlan"})
    private List<MealPlanRecipe> mealPlanRecipes = new ArrayList<>();

    @OneToOne
    private ShoppingList shoppingList;

    @NotNull
    @Length(max = 50, message = "Meal Plan name cannot exceed 50 characters.")
    private String name;

    @NotNull
    private LocalDateTime createdAt;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public MealPlan() {}

    public MealPlan(User user, String name) {
        this.user = user;
        this.name = name;
        this.createdAt = LocalDateTime.now();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<MealPlanRecipe> getMealPlanRecipes() {
        return mealPlanRecipes;
    }

    public void setMealPlanRecipes(List<MealPlanRecipe> mealPlanRecipes) {
        this.mealPlanRecipes = mealPlanRecipes;
    }

    public void addMealPlanRecipe(MealPlanRecipe mealPlanRecipe) {
        mealPlanRecipes.add(mealPlanRecipe);
        mealPlanRecipe.setMealPlan(this);
    }

    public void addRecipe(Recipe recipe) {
        MealPlanRecipe mealPlanRecipe = new MealPlanRecipe(this, recipe);
        this.mealPlanRecipes.add(mealPlanRecipe);
        mealPlanRecipe.setMealPlan(this);
    }

    public void removeMealPlanRecipe(MealPlanRecipe mealPlanRecipe) {
        mealPlanRecipes.remove(mealPlanRecipe);
        mealPlanRecipe.setMealPlan(null);
    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Meal Plan: " + this.name + ", created by " + (this.user != null ? this.user.toString() : "Unknown User") + " on " + this.createdAt;
    }
}
