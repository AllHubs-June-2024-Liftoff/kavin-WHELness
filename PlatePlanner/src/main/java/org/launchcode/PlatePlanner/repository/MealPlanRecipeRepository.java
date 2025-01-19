package org.launchcode.PlatePlanner.repository;

import org.launchcode.PlatePlanner.model.MealPlanRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealPlanRecipeRepository extends JpaRepository<MealPlanRecipe, Long> {
}
