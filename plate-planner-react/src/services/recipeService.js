import httpClient from "./axiosConfig.js";

/**
 * This is a service class that will handle all HTTP requests to the recipe endpoints.
 *
 * To use this service, you will need to import it into your component, and then you can
 * call any of the methods defined below.
 *
 * * `recipeService.getAll()` will return a list of all recipes.
 * * `recipeService.getById(recipeId)` will return the recipe with the specified ID.
 * * `recipeService.create(recipeObject)` will create a new recipe.
 * * `recipeService.update(recipeId, recipeObject)` will update an existing recipe.
 * * `recipeService.delete(recipeId)` will delete the recipe with the specified ID.
 * * `recipeService.addTagsToRecipe(recipeId, tagIds)` will add one or more tags to the recipe with the specified ID.
 */
export default {
  /**
   * This is the GET service call to retrieve all recipes.
   * It will return a 200 response with a list of all recipes.
   *
   * It hits the /recipe/all endpoint with a GET request.
   *
   * To use it in a component, you would do something like this:
   * @example recipeService.getAll().then((response) => console.log(response.data));
   * @returns A list of all Recipe objects.
   */
  getAll() {
    return httpClient.get("/recipe/all");
  },
  /**
   * This is the GET service call to retrieve a recipe by its ID.
   * It will return a 200 response with the recipe object.
   * If no recipe is found with the specified ID, it will return a 404.
   *
   * It hits the /recipe/{recipeId} endpoint with a GET request.
   *
   * To use it in a component, you would do something like this:
   * @example recipeService.getById(1).then((response) => console.log(response.data));
   * @param {number} recipeId The ID of the recipe to retrieve.
   * @returns The Recipe object with the specified ID.
   */
  getById(recipeId) {
    return httpClient.get(`/recipe/${recipeId}`);
  },
  /**
   * This is the POST service call to create a new recipe.
   * It will return a 204 (no content) response if the recipe is created successfully.
   *
   * It hits the /recipe/create endpoint with a POST request.
   *
   * @example recipeService.create(recipeObject).then((response) => console.log(response.status));
   * @param {object} recipeObject The Recipe object to create.
   */
  create(recipeObject) {
    return httpClient.post("/recipe/create", recipeObject);
  },
  /**
   * This is the POST service call to update an existing recipe.
   * It will return a 204 (no content) response if the recipe is updated successfully.
   * It will return a 404 if no recipe is found with the specified ID.
   *
   * It hits the /recipe/update/{recipeId} endpoint with a POST request.
   *
   * @example recipeService.update(1, recipeObject).then((response) => console.log(response.status));
   * @param {number} recipeId The ID of the recipe to update.
   * @param {object} recipeObject The Recipe object with the updated values.
   */
  update(recipeId, recipeObject) {
    return httpClient.post(`/recipe/update/${recipeId}`, recipeObject);
  },
  /**
   * This is the DELETE service call to delete a recipe by its ID.
   * It will return a 204 (no content) response if the recipe is deleted successfully.
   * It will return a 404 if no recipe is found with the specified ID.
   *
   * It hits the /recipe/delete/{recipeId} endpoint with a DELETE request.
   *
   * @example recipeService.delete(1).then((response) => console.log(response.status));
   * @param {number} recipeId The ID of the recipe to delete.
   */
  delete(recipeId) {
    return httpClient.delete(`/recipe/delete/${recipeId}`);
  },
  /**
   * This is the PUT service call to add one or more tags to a recipe.
   * It will return a 200 response with the updated recipe object.
   * It will return a 404 if no recipe is found with the specified ID.
   * It will return a 404 if no tags are found with the specified ID or IDs.
   * 
   * It hits the /recipe/add-tags/{recipeId} endpoint with a PUT request.
   * 
   * @example recipeService.addTagsToRecipe(1, [2, 3, 4]).then(response) => console.log(response.data));
   * @param {number} recipeId The ID of the recipe to add one or more tags to.
   * @param {array} tagIds The array with ID(s) of the tag(s) to be added to a recipe.
   * @returns The updated Recipe object.
   */
  addTagsToRecipe(recipeId, tagIds) {
    return httpClient.put(`/recipe/add-tags/${recipeId}`, tagIds);
  },
/**
   * This is the PUT service call to add one or more tags to a recipe and remove existing.
   * It will return a 200 response with the updated recipe object.
   * It will return a 404 if no recipe is found with the specified ID.
   * It will return a 404 if no tags are found with the specified ID or IDs.
   * 
   * It hits the /recipe/update-tags/{recipeId} endpoint with a PUT request.
   * 
   * @example recipeService.updateTagsToRecipe(1, [2, 3, 4]).then(response) => console.log(response.data));
   * @param {number} recipeId The ID of the recipe to add one or more tags to.
   * @param {array} tagIds The array with ID(s) of the tag(s) to be added to a recipe.
   * @returns The updated Recipe object.
   */
updateTagsToRecipe(recipeId, tagIds) {
  return httpClient.put(`/recipe/update-tags/${recipeId}`, tagIds);
},
/**
   * This is the GET service call to retrieve a user's recipes that have been filtered by tag.
   * It will return a 200 response with the list of filtered recipe objects.
   * It will return a 404 if no user is found with the specified ID.
   * It will return a 404 if no recipes are found belonging to the user.
   * It will return a 404 if no filtered recipe objects are found.
   * 
   * 
   * It hits the /recipe/filter-by-tag/{userId} endpoint with a GET request.
   * 
   * @example recipeService.filterRecipeByTag(1, [2, 3, 4]).then(response) => console.log(response.data));
   * @param {number} userId The ID of the user whose recipes are being filtered.
   * @param {array} tagIds The array with ID(s) of the tag(s) to filter recipes by.
   * @returns The list of filtered Recipe objects.
   */
filterRecipesByTag(userId, tagIds) {
  return httpClient.get(`/recipe/filter-by-tag/${userId}`, tagIds);
},
}