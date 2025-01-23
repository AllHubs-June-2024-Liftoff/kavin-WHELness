package org.launchcode.PlatePlanner.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
public class ShoppingList extends AbstractEntity {

    //Logic needed for aggregating quantities.

    @OneToOne(mappedBy = "shoppingList", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotNull
    private MealPlan mealPlan;

    @ManyToOne
    @NotNull
    private User user;

    private String name;

    @NotNull
    @OneToMany(mappedBy = "shoppingList", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ShoppingListItem> shoppingListItems = new HashSet<>();

    public ShoppingList() {}

    public ShoppingList(MealPlan mealPlan, User user, Set<ShoppingListItem> shoppingListItems) {
        this.mealPlan = mealPlan;
        this.user = user;
        this.shoppingListItems = shoppingListItems;
    }

    @PrePersist
    public void onCreate() {
        if (this.mealPlan != null) {
            this.name = "Shopping List for " + this.mealPlan.getName();
        }
    }

    public MealPlan getMealPlan() {
        return mealPlan;
    }

    public void setMealPlan(MealPlan mealPlan) {
        this.mealPlan = mealPlan;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public Set<ShoppingListItem> getShoppingListItems() {
        return shoppingListItems;
    }

    public void setShoppingListItems(Set<ShoppingListItem> shoppingListItems) {
        this.shoppingListItems = shoppingListItems;
    }

    public void addShoppingListItem(ShoppingListItem shoppingListItem) {
        if (shoppingListItem != null) {
            shoppingListItems.add(shoppingListItem);
        }
    }

    public void removeShoppingListItem(ShoppingListItem shoppingListItem) {
        if (shoppingListItem != null) {
            shoppingListItems.remove(shoppingListItem);
        }
    }

    @Override
    public String toString() {
        return "Shopping List for " + (this.mealPlan != null ? this.mealPlan.getName() : "Unknown Meal Plan");
    }
}
