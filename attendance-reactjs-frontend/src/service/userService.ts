import axios from "axios";

// Define the REST API URL
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
  formData.append("studentProfile", user.profile); // Ensure the field name matches what the backend expects

  // Send the POST request with form data
  return axios.post(REST_API_BASE_URL, formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  })
    .then((response) => {
      // Handle successful response
      console.log("User created successfully:", response.data);
      return response.data;
    })
    .catch((error) => {
      // Handle errors (e.g., validation, backend errors)
      console.error("Error creating user:", error.response?.data || error.message);
      throw error.response?.data || error.message; // Propagate the error
    });
};

export const updateUser = (
  userId: string, // The user ID to identify the user for updating
  user: { 
    username: string; 
    password?: string; // Optional field
    role: string; 
    profile?: File; // Optional field
  }
) => {
  // Create a FormData object to send the data as multipart/form-data
  const formData = new FormData();
  formData.append("username", user.username);
  formData.append("role", user.role);

  // Append optional fields only if they are provided
  if (user.password) {
    formData.append("password", user.password);
  }

  if (user.profile) {
    formData.append("studentProfile", user.profile); // Ensure the field name matches what the backend expects
  }

  // Send the PUT request to update the user
  return axios.put(`${REST_API_BASE_URL}/${userId}`, formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  })
    .then((response) => {
      // Handle successful response
      console.log("User updated successfully:", response.data);
      return response.data;
    })
    .catch((error) => {
      // Handle errors (e.g., validation, backend errors)
      console.error("Error updating user:", error.response?.data || error.message);
      throw error.response?.data || error.message; // Propagate the error
    });
};
