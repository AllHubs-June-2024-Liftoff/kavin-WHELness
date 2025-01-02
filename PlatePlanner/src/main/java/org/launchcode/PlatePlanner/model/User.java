package org.launchcode.PlatePlanner.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;
    private String password;
    private String role;
    private String fullname;

    public User() {
        super();
    }

    public User(String email, String password, String role, String fullname) {

        this.email = email;
        this.password = password;
        this.role = role;
        this.fullname = fullname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }


//    @NotNull
//    @Length(max = 25, message = "Username cannot exceed 25 characters.")
//    @Column(unique = true)
//    private String username;

//    @NotNull
//    @Length(min = 60, max = 120, message = "Password must be a valid hash.")
//    private String password;

//    @NotNull
//    @Email
//    @Column(unique = true)
//    private String email;

//    @NotNull
//    @Enumerated(EnumType.STRING)
//    private Role role;

    @NotNull
    private LocalDateTime createdAt;

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MealPlan> mealPlans = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Recipe> recipes = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ShoppingList> shoppingLists = new HashSet<>();

//    public User() {}

//    public User(String username, String password, String email, Role role) {
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.role = role;
//    }
//
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }

//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }

//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Set<MealPlan> getMealPlans() {
        return mealPlans;
    }

    public void setMealPlans(Set<MealPlan> mealPlans) {
        this.mealPlans = mealPlans;
    }

    public void addMealPlan(MealPlan mealPlan) {
        if (mealPlan != null) {
            mealPlans.add(mealPlan);
        }
    }

    public void removeMealPlan(MealPlan mealPlan) {
        mealPlans.remove(mealPlan);
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void addRecipe(Recipe recipe) {
        if (recipe != null) {
            recipes.add(recipe);
        }
    }

    public void removeRecipe(Recipe recipe) {
        recipes.remove(recipe);
    }

    public Set<ShoppingList> getShoppingLists() {
        return shoppingLists;
    }

    public void setShoppingLists(Set<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
    }

    public void addShoppingList(ShoppingList shoppingList) {
        if (shoppingList != null) {
            shoppingLists.add(shoppingList);
        }
    }

    public void removeShoppingList(ShoppingList shoppingList) {
        shoppingLists.remove(shoppingList);
    }

//    @Override
//    public String toString() {
//        return "Username: " + this.username + " | User ID: " + this.getId();
//    }
}
