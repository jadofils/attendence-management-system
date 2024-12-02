/* eslint-disable @typescript-eslint/no-explicit-any */
import axios from 'axios';

// Define the base URL for the API
const BASE_URL = 'http://localhost:8080/api/user/login';

// Define interfaces for the response and login data
export interface User {
  id?: number;
  username: string;
  email?: string;
  role: string;
}

export interface LoginResponse {
  message: string;
  user: User;
}

// Service to handle login requests
const loginUser = async (data: { username: string; password: string; role: string }): Promise<LoginResponse> => {
  try {
    // Log data being sent to the backend
    console.log('Sending login request with data:', data);

    // Send data in the body of the POST request
    const response = await axios.post(BASE_URL, {
      username: data.username,
      password: data.password,
      role: data.role,
    });

    // Log the backend response
    console.log('Login response:', response.data);

    // Return the response data
    return response.data;
  } catch (error: any) {
    // Log the error details
    console.log('Login failed:', error.response || error.message);
    throw error;
  }
};

export default loginUser;
