import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Header from './components/Header.jsx';
import RecipeList from './components/RecipeList.jsx';
import RecipeTextSearch from './components/RecipeTextSearch.jsx';
import './App.css'
import Nav from './components/Nav.jsx';
import Home from './components/Home.jsx';
import MealPlanUI from './components/MealPlanUI';
import ShoppingList from './components/ShoppingList.jsx';
import UserProfile from './components/UserProfile.jsx';
import LogOut from './components/LogOut.jsx';
import AdminProfile from './components/AdminProfile.jsx';

function App() {
  return (
    <Router>
    <div className='App'>
      <Header />
      <Nav />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/my-recipes" element={<RecipeList />} />
        <Route path="/meal-plans" element={<MealPlanUI />} />
        <Route path="/shopping-lists" element={<ShoppingList />} />
        <Route path="/profile" element={<UserProfile />} />
        <Route path="/admin" element={<AdminProfile />} />
        <Route path="/logout" element={<LogOut />} />
      </Routes>
      {/* <RecipeTextSearch /> */}
    </div>
    </Router>
  )
}

export default App;