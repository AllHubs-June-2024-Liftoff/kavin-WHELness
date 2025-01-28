import NavSearch from "./NavSearch";
import { Link } from "react-router-dom";
import Cookies from "js-cookie";

const Nav = () => {
  const userName = Cookies.get("username");
  const allCookies = Cookies.get();
  console.log("all Cookies: ", allCookies);

  if (userName != null) {
    return (
      <nav className="nav nav-pills nav-fill">
        <Link className="nav-link" to="/">
          Find Recipes
        </Link>
        <Link className="nav-link" to="/saved-recipes">
          Saved Recipes
        </Link>
        <Link className="nav-link" to="/meal-plans">
          Meal Plan
        </Link>
        <Link className="nav-link" to="/shopping-lists">
          Shopping List
        </Link>
        <Link className="nav-link" to="http://localhost:8080/profile">
          Hi {userName}
        </Link>
        <Link className="nav-link" to="http://localhost:8080/logout">
          Logout
        </Link>
      </nav>
    );
  } else {
    return (
      <nav className="nav nav-pills nav-fill">
        <Link className="nav-link" to="/">
          Find Recipes
        </Link>
        <Link className="nav-link disabled" to="#">
          Saved Recipes
        </Link>
        <Link className="nav-link disabled" to="#">
          Meal Plans
        </Link>
        <Link className="nav-link disabled" to="#">
          Shopping Lists
        </Link>
        <Link className="nav-link" to="http://localhost:8080/login">
          Login/Register
        </Link>
      </nav>
    );
  }
};

export default Nav;
