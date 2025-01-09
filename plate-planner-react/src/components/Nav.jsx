import NavSearch from "./NavSearch";
import { Link } from 'react-router-dom';

const Nav = () => {
    return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <Link className="nav-link" to="/">Find Recipes</Link>
        <Link className="nav-link" to="/my-recipes">Saved Recipes</Link>
        <Link className="nav-link" to="/meal-plans">Meal Plans</Link>
        <Link className="nav-link" to="/shopping-lists">Shopping Lists</Link>
        <Link className="nav-link" to="/profile">User Profile</Link>
        <Link className="nav-link" to="/admin">Admin Profile</Link>
        <Link className="nav-link" to="/logout">Log Out</Link>
    </nav>
    );
}

export default Nav;