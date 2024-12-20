

const Nav = () => {
    return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <a className="nav-link" href="#">Home</a>
        <a className="nav-link" href="#">My Recipes</a>
        <a className="nav-link" href="#">My Meal Plans</a>
        <a className="nav-link" href="#">My Shopping Lists</a>
        <form class="form-inline">
    <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
  </form>
    </nav>
    );
}

export default Nav;