import "@/styles/UserProfile.css"; // Import the external CSS file

import { useEffect, useState } from "react";

import Cookies from "js-cookie";
import { Link } from "react-router-dom";

const UserProfile = () => {
  const [userDetails, setUserDetails] = useState(null);

  useEffect(() => {
    const userDetails = Cookies.get("userDetails");
    if (userDetails) {
      setUserDetails(JSON.parse(userDetails));
    } else {
      setUserDetails(null);
    }
  }, []);

  if (!userDetails) {
    return (
      <div className="user-profile">
        <h2>Please <Link to="http://localhost:8080/login">log in</Link>!</h2>
      </div>
    );
  }

  return (
    <div className="user-profile">
      <h2>User Profile</h2>
      <table className="user-profile-table">
        <thead>
        <tr>
          <th>Field</th>
          <th>Value</th>
        </tr>
        </thead>
        <tbody>
        <tr>
          <td className="field-name">Username</td>
          <td>{userDetails.username}</td>
        </tr>
        <tr>
          <td className="field-name">ID</td>
          <td>{userDetails.id}</td>
        </tr>
        <tr>
          <td className="field-name">First Name</td>
          <td>{userDetails.firstName}</td>
        </tr>
        <tr>
          <td className="field-name">Last Name</td>
          <td>{userDetails.lastName}</td>
        </tr>
        <tr>
          <td className="field-name">Email</td>
          <td>{userDetails.email}</td>
        </tr>
        <tr>
          <td className="field-name">Phone</td>
          <td>{userDetails.phone}</td>
        </tr>
        <tr>
          <td className="field-name">Address</td>
          <td>{userDetails.address}</td>
        </tr>
        <tr>
          <td className="field-name">Role</td>
          <td>{userDetails.role}</td>
        </tr>
        <tr>
          <td className="field-name">Created At</td>
          <td>{userDetails.createdAt}</td>
        </tr>
        </tbody>
      </table>
    </div>
  );
};

export default UserProfile;