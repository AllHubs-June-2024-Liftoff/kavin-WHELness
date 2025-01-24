import httpClient from "./axiosConfig.js";

/**
 * This is a service class that will handle all HTTP requests to the meal-plan endpoints.
 *
 * To use this service, you will need to import it into your component, and then you can
 * call any of the methods defined below.
 *
 * * `mealPlanService.getAll()` will return a list of all meal plans.
 * * `mealPlanService.getById(mealPlanId)` will return the meal plan with the specified ID.
 * * `mealPlanService.create(mealPlanObject)` will create a new meal plan.
 * * `mealPlanService.update(mealPlanId, mealPlanObject)` will update an existing meal plan.
 * * `mealPlanService.delete(mealPlanId)` will delete the meal plan with the specified ID.
 * * `mealPlanService.getAllUserMealPlans(userId)` will return a list of all meal plans belonging to the user with the specified ID.
 * * `mealPlanService.createUserMealPlan(userId, mealPlanObject)` will create a new meal plan and add it to the user with the specified ID.
 * * `mealPlanService.addRecipeToMealPlan(recipeId, mealPlanId)` will add the recipe with the specified ID to the meal plan with the specified ID.
 */
export default {
  /**
   * This is the GET service call to retrieve all meal plans.
   * It will return a 200 response with a list of all meal plans.
   *
   * It hits the /meal-plan/all endpoint with a GET request.
   *
   * To use it in a component, you would do something like this:
   * @example mealPlanService.getAll().then((response) => console.log(response.data));
   * @returns A list of all MealPlan objects.
   */
  getAll() {
    return httpClient.get("/meal-plan/all");
  },
  /**
   * This is the GET service call to retrieve a meal plan by its ID.
   * It will return a 200 response with the meal plan object.
   * If no meal plan is found with the specified ID, it will return a 404.
   *
   * It hits the /meal-plan/{mealPlanId} endpoint with a GET request.
   *
   * To use it in a component, you would do something like this:
   * @example mealPlanService.getById(1).then((response) => console.log(response.data));
   * @param {number} mealPlanId The ID of the meal plan to retrieve.
   * @returns The MealPlan object with the specified ID.
   */
  getById(mealPlanId) {
    return httpClient.get(`/meal-plan/${mealPlanId}`);
  },
  /**
   * This is the POST service call to create a new meal plan.
   * It will return a 204 (no content) response if the meal plan is created successfully.
   *
   * It hits the /meal-plan/create endpoint with a POST request.
   *
   * @example mealPlanService.create(mealPlanObject).then((response) => console.log(response.status));
   * @param {object} mealPlanObject The MealPlan object to create.
   */
  create(mealPlanObject) {
    return httpClient.post("/meal-plan/create", mealPlanObject);
  },
  /**
   * This is the POST service call to update an existing meal plan.
   * It will return a 204 (no content) response if the meal plan is updated successfully.
   * It will return a 404 if no meal plan is found with the specified ID.
   *
   * It hits the /meal-plan/update/{mealPlanId} endpoint with a POST request.
   *
   * @example mealPlanService.update(1, mealPlanObject).then((response) => console.log(response.status));
   * @param {number} mealPlanId The ID of the meal plan to update.
   * @param {object} mealPlanObject The MealPlan object to update.
   */
  update(mealPlanId, mealPlanObject) {
    return httpClient.post(`/meal-plan/update/${mealPlanId}`, mealPlanObject);
  },
  /**
   * This is the DELETE service call to delete an existing meal plan.
   * It will return a 204 (no content) response if the meal plan is deleted successfully.
   * It will return a 404 if no meal plan is found with the specified ID.
   *
   * It hits the /meal-plan/delete/{mealPlanId} endpoint with a DELETE request.
   *
   * @example mealPlanService.delete(1).then((response) => console.log(response.status));
   * @param {number} mealPlanId The ID of the meal plan to delete.
   */
  delete(mealPlanId) {
    return httpClient.delete(`/meal-plan/delete/${mealPlanId}`);
  },
  /**
   * This is the GET service call to retrieve all meal plans associated with a user.
   * It will return a 200 response with a list of all user meal plans. 
   * It will return a 404 if no user is found with the specified ID.
   * It will return a 404 if no meal plans are found for a user with the specified ID.
   * 
   * It hits the /meal-plan/user/{userId} endpoint with a GET request.
   * 
   * @example mealPlanService.getAllUserMealPlans(1).then((response) => console.log(response.data));
   * @param {number} userId The ID of the user to get all meal plans from.
   * @returns A list of all MealPlan objects belonging to the user with the specified ID.
   */
  getAllUserMealPlans(userId) {
    return httpClient.get(`/meal-plan/user/${userId}`)
  },
    /**
   * This is the POST service call to create a meal plan associated with a user.
   * It will return a 201 (created) response if the user's meal plan is created successfully.
   * It will return a 404 if no user is found with the specified ID.
   * 
   * It hits the /meal-plan/user/{userId} endpoint with a POST request.
   * 
   * @example mealPlanService.createUserMealPlan(1, mealPlanObject).then((response) => console.log(response.data));
   * @param {number} userId The ID of the user to get all meal plans from.
   * @param {object} mealPlanObject The new MealPlan object.
   * @returns The saved new MealPlan object.
   */
  createUserMealPlan(userId, mealPlanObject) {
    return httpClient.post(`/meal-plan/user/${userId}`, mealPlanObject);
  },
    /**
     * This is the PUT service call to add a recipe to a meal plan.
     * It will return a 200 response with the updated meal plan object.
     * It will return a 404 if no meal plan is found with the specified ID.
     * It will return a 404 if no recipe is found with the specified ID.
     * 
     * It hits the /meal-plan/{mealPlanId}/add-recipe/{recipeId} endpoint with a PUT request.
     * 
     * @example mealPlanService.addRecipeToMealPlan(1, 1).then((response) => console.log(response.data));
     * @param {number} recipeId The ID of the recipe to be added to a meal plan.
     * @param {number} mealPlanId The ID of the meal plan to add a recipe to.
     * @returns The updated MealPlan object with the recipe added to it.
     */
  addRecipeToMealPlan(recipeId, mealPlanId) {
    return httpClient.put(`/meal-plan/${mealPlanId}/add-recipe/${recipeId}`);
  },

  addRecipeOnDay(mealPlanId, recipeId, selectedDay) {
    return httpClient.put(`/meal-plan/${mealPlanId}/add-recipe/${recipeId}/add-to-day/${selectedDay}`);
  },

  getUserMealPlan() {
    return httpClient.get("/meal-plan");
  }
}