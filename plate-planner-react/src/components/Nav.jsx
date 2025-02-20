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
        <a className="nav-link" href="/profile">
          Hi {userName}
        </a>
        <a className="nav-link" href="/logout">
          Logout
        </a>
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
        <a className="nav-link" href="/login">
          Login/Register
        </a>
      </nav>
    );
  }
};

export default Nav;
