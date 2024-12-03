import axios from 'axios';

const fetchLoggedInUser = async () => {
  try {
    // Making a GET request to check if the user is logged in
    const response = await axios.get('http://localhost:8080/api/dashboard/check-session', { withCredentials: true });

    console.log(response.data); // Check the response structure

    // Check if the user is logged in by inspecting the response
    if (response.data.message === "User is still logged in") {
      // Return the username and role (or use it as needed)
      return {
        username: response.data.username,
        role: response.data.role
      };
    } else {
      return {
        username: "Guest",
        role: "None"
      };
    }
  } catch (error) {
    console.error('Error fetching logged-in user:', error);
    return {
      username: "Guest",
      role: "None"
    };
  }
};

export default fetchLoggedInUser;
