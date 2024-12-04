// userService.ts

import axios from "axios";

const API_URL = "http://localhost:8080/api/users";

// Function to fetch users from the API
export const fetchUsers = async () => {
  try {
    const response = await axios.get(API_URL);
    return response.data;
  } catch (error) {
    console.error("Error fetching users:", error);
    throw error;
  }
};
