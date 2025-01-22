import { useEffect, useState } from "react";
import Button from "./Button";
import RecipeAddTag from "./RecipeAddTag";
import AddRecipeToMealPlan from "./AddRecipeToMealPlan";
import recipeService from "../services/recipeService";

// function handleAddRecipeToMealPlan(recipeId) {
//     //open a modal, allow user to select meal plan or create new meal plan, call API
//     console.log("Recipe with ID (" + recipeId + ") saved to Meal Plan!" )
// }

// function RecipeCards(props) {
//     const [recipes, setRecipes] = useState(props.recipes);
//     const [title, setTitle] = useState(props.title);
//     const [stateCounter, setStateCounter] = useState(0);

//     const handleSaveRecipe = async (recipeId) => {
//             const recipeToSave = recipes.find(recipe => recipe.id === recipeId);
//             try {
//                 const response = await axios.post('http://localhost:8080/recipe/save', recipeToSave);
//                 console.log("Recipe with ID (" + recipeId + ") saved to My Recipes!:", response.data);
//             } catch (error) {
//                 console.error("Error saving recipe:", error);
//             }
//         };

//     if (recipes != undefined) {
//         return (
//          <div key="recipecards">
//             {recipes != null && title && 
//                 <h1>{recipes.length} {title}</h1>
//             }
//             {recipes.map((recipe) => (
//                 <div key={recipe.id != null ? recipe.id : recipe.name} className="card">
//                     <h2 className='card-title'>{recipe.name}</h2>
//                     <p>{recipe.description}</p>
//                     <img src={recipe.imageURL} alt={recipe.name + " image"} className='card-img-top mx-auto d-block w-25'/>
//                     {recipe.tags != null && recipe.tags.length > 0 &&
//                     <div className="container">
//                         <div className="row justify-content-around">
//                         <div className="col"></div><div className="col"><div className="row justify-content-around">
//                             {recipe.tags.map((tag) => (
//                                 <div className="col" key={tag.id != null ? tag.id : tag.name} style={{fontWeight: 600}}>
//                                 &nbsp;{tag.name}&nbsp;
//                                 </div>
//                             ))}
//                         </div></div><div className="col"></div>
//                         </div>
//                     </div>
//                     }
//                     <h3>Ingredients:</h3>
//                     <ul>
//                         {recipe.recipeIngredients.map((ingredient) => (
//                             <li key={ingredient.id != null ? ingredient.id : ingredient.name}>
//                                 {ingredient.quantity} {ingredient.unit} {ingredient.ingredient.name}
//                             </li>
//                         ))}
//                     </ul>
//                     <h3>Instructions:</h3>
//                     <p>{recipe.instructions}</p>
//                     <div className="container">
//                         <div className="row justify-content-around">
//                         {<div className="container">
//                                 <div className="row justify-content-around">
//                                     <div className="col-4">
//                                         {recipe.id == null ? (
//                                             <Button label="Save Recipe" onClick={() => handleSaveRecipe(recipe.id)}/>
//                                         ) : (
//                                             <AddRecipeToMealPlan recipeId={recipe.id} />
//                                         )}
//                                     </div>
//                                     {recipe.id != null && (
//                                         <div className="col-4">
//                                             <RecipeAddTag recipe={recipe} setStateCounter={setStateCounter} counter={stateCounter} />
//                                         </div>
//                                     )}
//                                 </div>
//                             </div>
//                         }
//                         {recipe.id != null &&
//                             <div className="col-4">
//                                 <AddRecipeToMealPlan recipeId={recipe.id} />
//                             </div>
//                         }
//                         {recipe.id != null &&
//                             <div className="col-4">
//                                 <RecipeAddTag recipe={recipe} setStateCounter={setStateCounter} counter={stateCounter} />
//                             </div>
//                         }
//                  </div>
//                     </div>
//                 </div>
//             ))}
//         </div>
//         );
//     } else {
//         return (<div key="recipecards"></div>);
//     }
// };

// export default RecipeCards;

function RecipeCards(props) {
    const [recipes, setRecipes] = useState(props.recipes);
    const [title, setTitle] = useState(props.title);
    const [stateCounter, setStateCounter] = useState(0);

    const handleSaveRecipe = async (recipe) => {
        try {
            const response = await recipeService.create (recipe);
            console.log("Recipe saved successfully:", response.data);
        } catch (error) {
            console.error("Error saving recipe:", error);
        }
    };

    if (recipes != undefined) {
        return (
            <div key="recipecards">
                {recipes != null && title && <h1>{recipes.length} {title}</h1>}
                {recipes.map((recipe) => (
                    <div key={recipe.id != null ? recipe.id : recipe.name} className="card">
                        <h2 className='card-title'>{recipe.name}</h2>
                        <p>{recipe.description}</p>
                        <img src={recipe.imageURL} alt={recipe.name + " image"} className='card-img-top mx-auto d-block w-25'/>
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
                                <div className="col-4">
                                    <Button label="Save Recipe" onClick={() => handleSaveRecipe(recipe)}/>
                                </div>
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