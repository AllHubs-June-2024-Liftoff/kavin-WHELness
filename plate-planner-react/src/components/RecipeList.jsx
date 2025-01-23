import { useCallback, useEffect, useState } from "react";

import Cookies from "js-cookie";
import RecipeCards from "./RecipeCards.jsx";
import recipeService from "@/services/recipeService.js";

const RecipeList = () => {
    const [userDetails, setUserDetails] = useState(null);
    const [recipes, setRecipes] = useState([]);
    const [filteredRecipes, setFilteredRecipes] = useState([]);
    const [loading, setLoading] = useState(true);
    const [retrievedUserRecipes, setRetrievedUserRecipes] = useState([]);
    const [filteredUserRecipes, setFilteredUserRecipes] = useState([]);
    const [tags, setTags] = useState([]);
    const [selectedTags, setSelectedTags] = useState([]);

  // Populate user details from cookie
    useEffect(() => {
        const userDetails = Cookies.get("userDetails");
        if (userDetails) {
        setUserDetails(JSON.parse(userDetails));
        }
    }, []);

  // Fetch all recipes
    useEffect(() => {
        recipeService.getAll()
        .then((response) => {
            setRecipes(response.data);
            // This will initialize filteredRecipes with all recipes.
            setFilteredRecipes(response.data);
            setLoading(false);
        })
        .catch(() => {
            // We'll probably just want to just show an empty list instead of an error.
        });
    }, []);

    // Fetch user-specific recipes
    useEffect(() => {
        if (userDetails) {
        recipeService.getUserRecipes(userDetails.id)
            .then((response) => {
            setRetrievedUserRecipes(response.data);
            // This will initialize filteredUserRecipes with all user recipes.
            setFilteredUserRecipes(response.data);
            })
            .catch(() => {
            // We'll probably just want to just show an empty list instead of an error.
            });
        }
    }, [userDetails]);

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

    // Update filtered user recipes whenever user recipes or selected tags change
    useEffect(() => {
        setFilteredUserRecipes(filterRecipesByTags(retrievedUserRecipes));
    }, [retrievedUserRecipes, filterRecipesByTags]);

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

    if (loading) {
        return <p>Loading recipes...</p>;
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
        {retrievedUserRecipes.length === 0 ? (
            <p>No user recipes found.</p>
        ) : (
            <RecipeCards recipes={filteredUserRecipes} />
        )}

        <h3>All Recipes</h3>
        {recipes.length === 0 ? (
            <p>No recipes found.</p>
        ) : (
            <RecipeCards recipes={filteredRecipes} />
        )}
        </div>
    );
};

export default RecipeList;