package org.launchcode.PlatePlanner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class MealPlanRecipe extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "mealplan_id")
    @JsonIgnoreProperties({"mealPlanRecipes"})
    private MealPlan mealPlan;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Enumerated(EnumType.STRING)
    private DayOfTheWeek dayOfTheWeek;


    public MealPlanRecipe() {
    }

    public MealPlanRecipe(MealPlan mealPlan, Recipe recipe) {
        this.mealPlan = mealPlan;
        this.recipe = recipe;
    }

    public MealPlanRecipe(MealPlan mealPlan, Recipe recipe, DayOfTheWeek dayOfTheWeek) {
        this.mealPlan = mealPlan;
        this.recipe = recipe;
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public MealPlan getMealPlan() {
        return mealPlan;
    }

    public void setMealPlan(MealPlan mealPlan) {
        this.mealPlan = mealPlan;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public @NotNull DayOfTheWeek getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayOfTheWeek(@NotNull DayOfTheWeek dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    @Override
    public String toString() {
        return "Meal Plan Recipe: " + this.recipe + " for " + this.dayOfTheWeek + " of Meal Plan -" + this.mealPlan;
    }
}
