package org.launchcode.PlatePlanner.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.launchcode.PlatePlanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User extends AbstractEntity {

    @NotNull
    @Length(max = 25, message = "Username cannot exceed 25 characters.")
    @Column(unique = true)
    private String username;

    @NotNull
    @Length(min = 60, max = 120, message = "Password must be a valid hash.")
    private String pwHash;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MealPlan> mealPlans = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Recipe> recipes = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ShoppingList> shoppingLists = new HashSet<>();

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User() {}

    public User(String username, String password, String email, Role role) {
        this.username = username;
        this.pwHash = encoder.encode(password);
        this.email = email;
        this.role = role;
    }

    public User(String username, String password) {
        super();
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public Role getRole() { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Set<MealPlan> getMealPlans() { return mealPlans; }
    public Set<Recipe> getRecipes() { return recipes; }
    public Set<ShoppingList> getShoppingLists() { return shoppingLists; }

    public void addMealPlan(MealPlan mealPlan) { if (mealPlan != null) mealPlans.add(mealPlan); }
    public void addRecipe(Recipe recipe) { if (recipe != null) recipes.add(recipe); }
    public void addShoppingList(ShoppingList shoppingList) { if (shoppingList != null) shoppingLists.add(shoppingList); }

    @Override
    public String toString() {
        return "Username: " + this.username + " | User ID: " + this.getId();
    }
}