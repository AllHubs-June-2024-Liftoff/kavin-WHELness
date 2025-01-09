import React from 'react';
import "../styles/Profile.css";

//  Headers are hardcoded. They need to be dyanamic, changing with each Amin. 

const AdminProfile = () => {
  return (
    <div className="admin-profile">
        <h2>Welcome, Admin!</h2>
        <a href="/logout">Logout</a>
        <div className="profile-container">
            <div className="profile-header">
                <img src="headshot-placeholder.jpg" alt="Admin Headshot" />
                <h3>John Doe</h3>
            </div>
            <div className="profile-info">
                <label>Email:</label>
                <p></p>
            </div>
            <div className="profile-info">
                <label>Password:</label>
                <p></p>
            </div>
            <div className="profile-info">
                <label>Favorite Recipes:</label>
                <p></p>
            </div>
            <div className="profile-info">
                <label>Shopping List:</label>
                <p></p>
            </div>
            <div className="profile-info">
                <label>Account Management:</label>
                <p></p>
            </div>
        </div>
    </div>
);
}

export default AdminProfile;