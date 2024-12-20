package org.launchcode.PlatePlanner.repository;

import org.launchcode.PlatePlanner.model.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}
