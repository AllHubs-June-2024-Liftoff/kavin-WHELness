import { useEffect, useState } from "react";
import RecipeCards from './RecipeCards.jsx';
import recipeService from "@/services/recipeService.js";

const RecipeList = () => {
    const [recipes, setRecipes] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        recipeService.getAll()
            .then((response) => {
                setRecipes(response.data);
                setLoading(false);
            })
            .catch((err) => {
                setError(err.message);
                setLoading(false);
            });
    }, []);

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
            <h2>Saved Recipes</h2>
            <RecipeCards recipes={recipes} />
         {/*
            <h1>Recipes</h1>
            {recipes.map((recipe) => (
                <div key={recipe.id} className="card">
                    <h2 className='card-title'>{recipe.name}</h2>
                    <p>{recipe.description}</p>
                    <img src={recipe.imageURL} alt={recipe.name + " image"} className='card-img mx-auto d-block w-25'/>
                    <h3>Ingredients:</h3>
                    <ul className="list-unstyled">
                        {recipe.recipeIngredients.map((ingredient) => (
                            <li key={ingredient.id}>
                                {ingredient.quantity} {ingredient.unit} {ingredient.ingredient.name}
                            </li>
                        ))}
                    </ul>
                    <h3>Instructions:</h3>
                    <p>{recipe.instructions}</p>
                    <div className="container">
                        <div className="row justify-content-around">
                            <div className="col-4">
                                <Button label="Save Recipe" onClick={handleSaveRecipe(recipe.id)}/>
                            </div>
                            <div className="col-4">
                                <Button label="Add to Meal Plan" onClick={handleAddRecipeToMealPlan(recipe.id)}/>
                            </div>
                        </div>
                    </div>
                </div>
            ))}
        */}
        </div>
    );
};

export default RecipeList;