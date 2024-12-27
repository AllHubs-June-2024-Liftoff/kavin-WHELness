package org.launchcode.PlatePlanner.api;

import java.util.Arrays;

public class MealDbMeals {

    MealDbMeal[] meals = null;

    public MealDbMeal[] getMeals() {
        return meals;
    }

    public void setMeals(MealDbMeal[] meals) {
        this.meals = meals;
    }

    @Override
    public String toString() {
        return "MealDbMeals{" +
                "meals=" + Arrays.toString(meals) +
                '}';
    }
}
