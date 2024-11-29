/* eslint-disable @typescript-eslint/no-explicit-any */
import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/user/login'; // Adjust URL

const loginUser = async (data: { username: string, password: string, role: string }) => {
  try {
    const response = await axios.post(BASE_URL, data, {
      headers: {
        'Content-Type': 'application/json', // Assuming you're sending JSON data
      },
    });

    console.log('Response from backend:', response.data); // Log the full response
    return response.data; // Return response data to frontend for further processing
  } catch (error: any) {
    console.error('Login failed:', error);
    throw error;
  }
};

export default loginUser;
