import axios from "axios";

const REST_API_BASE_URL = "http://localhost:8080/api/users";

// Update the parameter type to match the user object structure
export const createUser = (user: { 
  username: string; 
  password: string; 
  role: string; 
  profile: File; 

}) => {
  return axios.post(REST_API_BASE_URL, user);
};
