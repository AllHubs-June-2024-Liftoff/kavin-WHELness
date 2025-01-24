package org.launchcode.PlatePlanner.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
public class ShoppingListItem extends AbstractEntity {

    @ManyToOne
    @NotNull
    private ShoppingList shoppingList;

    @ManyToOne
    @NotNull
    private Ingredient ingredient;

    @NotNull
    @Max(value = 1000, message = "Quantity cannot exceed 1000.")
    private double quantity;

    @NotNull
    @Length(max=20, message = "Unit cannot exceed 20 characters.")
    private String unit;

    public ShoppingListItem() {}

    public ShoppingListItem(Ingredient ingredient, double quantity, String unit) {
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.unit = unit;
    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "ShoppingListItem: " + this.quantity + " " + this.unit + " " + this.ingredient.getName();
    }
}
