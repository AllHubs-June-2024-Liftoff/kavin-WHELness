import { useEffect, useState } from "react";
import { BASE_URL } from '../globalvars';
import RecipeCards from "./RecipeCards.jsx";


const RecipeTextSearch = () => {
  const [query, setQuery] = useState("");
  const [searchRecipes, setSearchRecipes] = useState([]);
  const [loading, setLoading] = useState(null);
  const [error, setError] = useState(null);
  const [hasSearched, setHasSearched] = useState(false);

  const handleInputChange = (e) => {
    setQuery(e.target.value);
  };


  const handleSubmit = (e) => {
    e.preventDefault();
    setLoading(true);
    setHasSearched(true);
    fetch(BASE_URL + "/search-recipes?q=" + query, {
      credentials: "include",
      mode: "cors",
    })
      .then((response) => response.json())
      .then((data) => {
        setSearchRecipes(data);
        setLoading(false);
      })
      .catch((err) => {
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
        <div className="form-group">
          <label>
            Search:
            <input
              type="text"
              value={query}
              onChange={handleInputChange}
              className="form-control"
              placeholder="search for a delicious recipe"
            />
          </label>
        </div>
        <button type="submit" className="btn btn-primary">
          Submit
        </button>
      </form>
      <RecipeCards
        recipes={searchRecipes}
        title="Search Results"
        hasSearched={hasSearched}
      />
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
