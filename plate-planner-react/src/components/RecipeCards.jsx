import AddRecipeToMealPlan from "./AddRecipeToMealPlan";
import Button from "./Button";
import PropTypes from "prop-types";
import RecipeAddTag from "./RecipeAddTag";
import { useState } from "react";

function handleSaveRecipe(recipeId) {
    console.log("Recipe with ID (" + recipeId + ") saved to My Recipes!")
}

function handleAddRecipeToMealPlan(recipeId) {
    //open a modal, allow user to select meal plan or create new meal plan, call API
    console.log("Recipe with ID (" + recipeId + ") saved to Meal Plan!" )
}

// this will tell RecipeCards what props (and what the types are) so intellij doesn't freak out!
RecipeCards.propTypes = {
    recipes: PropTypes.array,
    title: PropTypes.string
};

function RecipeCards({ recipes = [], title }) {
    const [stateCounter, setStateCounter] = useState(0);

    if (recipes != undefined) {
        return (
        <div key="recipecards">
            {recipes != null && title && 
                <h1>{recipes.length} {title}</h1>
            }
            {recipes.map((recipe) => (
                <div key={recipe.id != null ? recipe.id : recipe.name} className="card">
                    <h2 className='card-title'>{recipe.name}</h2>
                    <p>{recipe.description}</p>
                    <img src={recipe.imageURL} alt={recipe.name + " image"} className='card-img-top mx-auto d-block w-25'/>
                    {recipe.tags != null && recipe.tags.length > 0 &&
                    <div className="container">
                        <div className="row justify-content-around">
                        <div className="col"></div><div className="col"><div className="row justify-content-around">
                            {recipe.tags.map((tag) => (
                                <div className="col" key={tag.id != null ? tag.id : tag.name} style={{fontWeight: 600}}>
                                &nbsp;{tag.name}&nbsp;
                                </div>
                            ))}
                        </div></div><div className="col"></div>
                        </div>
                    </div>
                    }
                    <h3>Ingredients:</h3>
                    <ul>
                        {recipe.recipeIngredients.map((ingredient) => (
                            <li key={ingredient.id != null ? ingredient.id : ingredient.name}>
                                {ingredient.quantity} {ingredient.unit} {ingredient.ingredient.name}
                            </li>
                        ))}
                    </ul>
                    <h3>Instructions:</h3>
                    <p>{recipe.instructions}</p>
                    <div className="container">
                        <div className="row justify-content-around">
                        {recipe.id == null &&
                            <div className="col-4">
                                <Button label="Save Recipe" onClick={() => handleSaveRecipe(recipe.id)}/>
                            </div>
                        }
                        {recipe.id != null &&
                            <div className="col-4">
                                <AddRecipeToMealPlan recipeId={recipe.id} />
                            </div>
                        }
                        {recipe.id != null &&
                            <div className="col-4">
                                <RecipeAddTag recipe={recipe} setStateCounter={setStateCounter} counter={stateCounter} />
                            </div>
                        }
                </div>
                    </div>
                </div>
            ))}
        </div>
        );
    } else {
        return (<div key="recipecards"></div>);
    }
};

export default RecipeCards;
