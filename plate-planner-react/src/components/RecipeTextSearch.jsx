import { useEffect, useState } from "react";
import axios from "axios";
import RecipeCards from './RecipeCards.jsx';



const RecipeTextSearch = () => {
    const [query, setQuery] = useState('');
    const [searchRecipes, setSearchRecipes] = useState();
    const [loading, setLoading] = useState(null);
    const [error, setError] = useState(null);

    const handleInputChange = (e) => {
        setQuery(e.target.value);
    };

    //http://localhost:8080/search-recipes?q=${query}
    //http://localhost:8080/recipes

    const handleSubmit = (e) => {
        e.preventDefault();
        setLoading(true);
        fetch('http://localhost:8080/search-recipes?q=' + query, {
            credentials: 'include',
            mode: 'cors'
          })
        .then(response => response.json())
        .then(data => { 
            setSearchRecipes(data);
            setLoading(false);
        })
        .catch(err => {
            setError(err.message);
            setLoading(false);
        });
    };

    if (loading) {
        return <p>Loading recipes...</p>;
    }

    if (error) {
        return <p>Error: {error}</p>;
    }


    return (
        <div>
            <form onSubmit={handleSubmit}>
                <label>
                    Search:
                    <input type="text" value={query} onChange={handleInputChange} />
                </label>
                <button type="submit">Submit</button>
            </form>
            <RecipeCards recipes={searchRecipes} title="Search Results" />
            {/*
            {recipes.map((recipe) => (
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
            */}
        </div>
    );
};



/*
function SearchForm () {
    const [recipes, setRecipes] = useState([]);
    return (
        <div>
            <h1>Recipe Search</h1>
            <RecipeCards recipes = {recipes} />
        </div>
        );
}
*/


export default RecipeTextSearch;
