import { useEffect, useState } from "react";
import axios from "axios";

function RecipeCards(props) {

    if (props.recipes != undefined) {
        return (
         <div>
            <h1>{props.title}</h1>
            {props.recipes.map((recipe) => (
                <div key={recipe.id} className="card">
                    <h2 className='card-title'>{recipe.name}</h2>
                    <p>{recipe.description}</p>
                    <img src={recipe.imageURL} alt={recipe.name + " image"} className='card-img-top mx-auto d-block w-25'/>
                    <h3>Ingredients:</h3>
                    <ul>
                        {recipe.recipeIngredients.map((ingredient) => (
                            <li key={ingredient.id}>
                                {ingredient.quantity} {ingredient.unit} {ingredient.ingredient.name}
                            </li>
                        ))}
                    </ul>
                    <h3>Instructions:</h3>
                    <p>{recipe.instructions}</p>
                    </div>
            ))}
        </div>
        );
    } else {
        return (<div></div>);
    }
};

export default RecipeCards;
