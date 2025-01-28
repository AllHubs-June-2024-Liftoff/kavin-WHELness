import {useCallback, useEffect, useState} from "react";

import Cookies from 'js-cookie';
import RecipeCards from './RecipeCards.jsx';
import recipeService from "@/services/recipeService.js";

const RecipeList = () => {
  const [recipes, setRecipes] = useState([]);
  const [filteredRecipes, setFilteredRecipes] = useState([]);
  const [tags, setTags] = useState([]);
  const [selectedTags, setSelectedTags] = useState([]);
  const [showLoginNotice, setShowLoginNotice] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [stateCounter, setStateCounter] = useState(0);
  const userName = Cookies.get('username');

  // If the user is logged in, get all recipes saved by the user.  Otherwise, set the hook to show a login notice.
  useEffect(() => {
    if (userName !== null && userName !== undefined && userName.length > 0) {
      recipeService.getAllByUser()
        .then((response) => {
          setRecipes(response.data);
          setLoading(false);
        })
        .catch((err) => {
          console.log("Error retrieving saved recipes: ", err.message);
          setError("There was a problem retrieving your saved recipes.");
          setLoading(false);
        });
    } else {
      setShowLoginNotice(true);
      setLoading(false);
    }
  }, [userName]);

  // if stateCounter changes, reload the recipes (which reloads the tags).
  useEffect(() => {
    console.log('Reloading recipes because state counter changed.');
    recipeService.getAllByUser()
      .then((response) => {
        setRecipes(response.data);
      })
      .catch((err) => {
        console.log("Error retrieving saved recipes: ", err.message);
        setError("There was a problem retrieving your saved recipes.");
      });
  }, [stateCounter]);

  // Extract unique tags from recipes
  useEffect(() => {
    // We'll just return if there are no recipes.
    if (recipes.length === 0) return;
    // This will make an array of all the tags and flatten it so we don't get any nasty nested stuff
    const allTags = recipes.map(recipe => recipe.tags).flat();
    // This makes a list of just unique names that we will use to filter the tags...
    const uniqueTagNames = new Set(allTags.map(tag => tag.name));
    // ...here
    const uniqueTags = Array.from(uniqueTagNames)
      .map(tagName => allTags.find(tag => tag.name === tagName));
    setTags(uniqueTags);
  }, [recipes]);

  /**
   * This little helper function will filter recipes based on selected tags.
   */
  const filterRecipesByTags = useCallback((recipesToFilter) => {
    if (!selectedTags.length) return recipesToFilter;
    return recipesToFilter.filter((recipe) =>
      selectedTags.every((selectedTag) =>
        recipe.tags.some((recipeTag) => recipeTag.name === selectedTag.name)
      )
    );
  }, [selectedTags]);

  // Update filtered recipes whenever recipes or selected tags change
  useEffect(() => {
    setFilteredRecipes(filterRecipesByTags(recipes));
  }, [recipes, filterRecipesByTags]);

  /**
   * This function toggles a tag in the selectedTags state.
   * @param tag The tag to toggle.
   */
  const toggleTag = (tag) => {
    setSelectedTags((prevSelectedTags) => {
      const isSelected = prevSelectedTags.some((t) => t.name === tag.name);
      return isSelected
        ? prevSelectedTags.filter((t) => t.name !== tag.name)
        : [...prevSelectedTags, tag];
    });
  };

  // If the hook to show a login notice is set, display a message to the user to login.
  if (showLoginNotice) {
    return <p>Please login to see your saved recipes.</p>;
  }

  if (loading) {
    return <p>Loading recipes...</p>;
  }

  if (error) {
    return <p>Error: {error}</p>;
  }

  function handleSaveRecipe(recipeId) {
    //call API using axios here
  }

  function handleAddRecipeToMealPlan(recipeId) {
    //open a modal, allow user to select meal plan or create new meal plan, call API
  }

  return (
    <div>
      {tags.length > 0 && (
        <>
          <h2>Tags</h2>
          <div>
            {tags.map((tag) => (
              <button
                key={tag.name}
                onClick={() => toggleTag(tag)}
                style={{
                  backgroundColor: selectedTags.some((t) => t.name === tag.name)
                    ? "blue"
                    : "gray",
                  color: "white",
                  margin: "5px",
                  padding: "5px 10px",
                  border: "none",
                  borderRadius: "5px",
                  cursor: "pointer",
                }}
              >
                {tag.name}
              </button>
            ))}
          </div>
        </>
      )}
      <h2>Saved Recipes</h2>
      {recipes.length === 0 ? (
        <p>No recipes found.</p>
      ) : (
        <RecipeCards recipes={filteredRecipes} stateCounter={stateCounter} setStateCounter={setStateCounter}/>
      )}
    </div>
  );
};

export default RecipeList;