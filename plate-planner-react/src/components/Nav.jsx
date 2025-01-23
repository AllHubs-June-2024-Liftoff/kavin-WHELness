import {useEffect, useState} from "react";

import Cookies from 'js-cookie';
import { Link } from 'react-router-dom';
import NavSearch from "./NavSearch";

const Nav = () => {
    const [userDetails, setUserDetails] = useState(null);

    useEffect(() => {
        const userDetails = Cookies.get('userDetails');
        if (userDetails) {
            setUserDetails(JSON.parse(userDetails));
        } else {
            setUserDetails(null);
        }
    }, []);
    return (
    <nav className="nav nav-pills nav-fill">
        <Link className="nav-link" to="/">Find Recipes</Link>
        <Link className="nav-link" to="/saved-recipes">Saved Recipes</Link>
        <Link className="nav-link" to="/meal-plans">Meal Plans</Link>
        <Link className="nav-link" to="/shopping-lists">Shopping Lists</Link>
        {!userDetails &&
        <Link className="nav-link" to="http://localhost:8080/login">Login/Register</Link>
        }
        {userDetails && userDetails.username &&
        <Link className="nav-link" to="/profile">Hi {userDetails.username}</Link>
        }
        {userDetails && userDetails.username &&
        <Link className="nav-link" to="http://localhost:8080/logout">Logout</Link>
        }
    </nav>
    );
}

export default Nav;