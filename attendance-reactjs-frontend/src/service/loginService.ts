// loginService.ts
import axios from 'axios';

// Define the base URL for the API
const BASE_URL = 'http://localhost:8080/api/user/login'; // Adjust this if necessary

// Function to send the login data to the backend
const loginUser = async (data: { username: string, password: string, role: string }) => {
  try {
    // Send the login request
    const response = await axios.post(BASE_URL, data, {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded', // Ensure the content type is form data
      },
    });

    // Handle success - can return the response data or handle the response accordingly
    return response.data;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    // Handle error
    console.error('Login failed:', error.response || error.message);
    throw error;
  }
};

export default loginUser;
