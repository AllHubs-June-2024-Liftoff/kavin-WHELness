import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Header from "./components/Header.jsx";
import RecipeList from "./components/RecipeList.jsx";
import "./App.css";
import Nav from "./components/Nav.jsx";
import Home from "./components/Home.jsx";
import MealPlanUI from "./components/MealPlanUI";
import ShoppingList from "./components/ShoppingList.jsx";
import UserProfile from "./components/UserProfile.jsx";
import LogOut from "./components/LogOut.jsx";
import LogIn from "./components/LogIn.jsx";
import RandomImage from "./components/RandomImage.jsx";
import Footer from "./components/Footer.jsx";

function App() {
  return (
    <>
      <Router>
        <div className="App">
          <Header />
          <Nav />
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/saved-recipes" element={<RecipeList />} />
            <Route path="/meal-plans" element={<MealPlanUI />} />
            <Route path="/shopping-lists" element={<ShoppingList />} />
          </Routes>
          <div className="content-wrapper"></div>
          <RandomImage />

          <hr />

          <Footer />
          <small>
            <a
              href="https://www.flaticon.com/free-stickers/breakfast"
              title="breakfast stickers"
            >
              Artwork by Stickers - Flaticon
            </a>
          </small>
        </div>
      </Router>
    </>
  );
}

export default App;
