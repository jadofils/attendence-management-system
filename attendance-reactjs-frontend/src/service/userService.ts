import axios from "axios";

const REST_API_BASE_URL = "http://localhost:8080/api/users";

// Update the parameter type to match the user object structure
export const createUser = (user: { 
  username: string; 
  password: string; 
  role: string; 
  profile: File; 
}) => {
  // Create a FormData object to send the data as multipart/form-data
  const formData = new FormData();
  formData.append("username", user.username);
  formData.append("password", user.password);
  formData.append("role", user.role);
  formData.append("studentProfile", user.profile); // Make sure the field name matches

  // Send the POST request with form data
  return axios.post(REST_API_BASE_URL, formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
};
