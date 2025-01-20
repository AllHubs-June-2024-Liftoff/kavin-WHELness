import NavSearch from "./NavSearch";
import { Link } from 'react-router-dom';
import Cookies from 'js-cookie';


const Nav = () => {
    const userName = Cookies.get('username');
    const allCookies = Cookies.get();
    console.log("all Cookies: ", allCookies);

    return (
    <nav className="nav nav-pills nav-fill">
        <Link className="nav-link" to="/">Find Recipes</Link>
        <Link className="nav-link" to="/saved-recipes">Saved Recipes</Link>
        <Link className="nav-link" to="/meal-plans">Meal Plans</Link>
        <Link className="nav-link" to="/shopping-lists">Shopping Lists</Link>
        {userName == null && 
        <Link className="nav-link" to="http://localhost:8080/login">Login/Register</Link>
        }
        {userName != null && 
        <Link className="nav-link" to="http://localhost:8080/profile">Hi {userName}</Link>
        }
        {userName != null && 
        <Link className="nav-link" to="http://localhost:8080/logout">Logout</Link>
        }
    </nav>
    );
}

export default Nav;