import React, { useState } from "react";
import { useForm, SubmitHandler } from "react-hook-form";
import { FaUser, FaLock, FaUpload, FaUsers } from "react-icons/fa";
import { Link } from "react-router-dom";
import { createUser } from "../service/userService";
import { useNavigate } from "react-router-dom";

interface SignupFormInputs {
  username: string;
  password: string;
  profile: FileList;
  role: string;
}

const Signup: React.FC = () => {
  const navigate = useNavigate();
  const [errorMessage, setErrorMessage] = useState<string | null>(null); // Add state to handle error message

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<SignupFormInputs>({ mode: "onBlur" });

  const onSubmit: SubmitHandler<SignupFormInputs> = (data) => {
    const user = {
      username: data.username,
      password: data.password,
      profile: data.profile[0], // Sending the first file
      role: data.role,
    };
  
    console.log("Profile File:", data.profile[0]); // Log file to verify
    createUser(user)
      .then((response) => {
        alert("User Created Successfully!!");
        console.log(response.data);
        setErrorMessage("User created successfully!"); // Success message
        setTimeout(() => {
          navigate("/login"); // Redirect to login page using useNavigate
        }, 5000); // Redirect after 5 seconds
      })
      .catch((error) => {
        const errorMessage =
          error.response?.data?.message || 
          error.message || 
          "An unexpected error occurred";

        const rootCause = 
          error.response?.data?.rootCause || 
          error.response?.data?.error || 
          "Unknown root cause";

        setErrorMessage(`${errorMessage} \nRoot Cause: ${rootCause}`); // Set error message
        console.error("Error details:", error);
      });
  };
  const validateUsername = (value: string) => {
    if (value.length < 3) {
      return "Username must be at least 3 characters long";
    }
    return true; // means validation passed
  };
  
  const validatePassword = (value: string) => {
    if (value.length < 6) {
      return "Password must be at least 6 characters long";
    }
    return true;
  };
  
  const validateProfile = (value: FileList) => {
    if (value.length === 0) {
      return "Profile picture is required";
    }
    return true;
  };
  
  const validateRole = (value: string) => {
    if (value === "") {
      return "Role is required";
    }
    return true;
  };
  
  return (
    <section
      id="signup-form"
      className="p-6 sm:p-8 max-w-3xl mx-auto shadow-md rounded-lg bg-white border border-gray-200"
    >
      <h2 className="text-2xl font-semibold mb-6 text-center text-gray-800">Sign Up</h2>

      {/* Display signup message */}
      {errorMessage && (
        <div
          className={`text-center py-2 px-4 mb-4 rounded-md ${
            errorMessage.includes('failed') ? 'bg-red-200 text-red-700' : 'bg-green-200 text-green-700'
          }`}
        >
          {errorMessage}
        </div>
      )}

      <form onSubmit={handleSubmit(onSubmit)} noValidate>
        <div className="mb-5">
          <label htmlFor="username" className="flex items-center text-sm font-medium text-gray-700 mb-2">
            <FaUser className="text-blue-600 mr-2" /> Username
          </label>
          <input
            type="text"
            id="username"
            {...register("username", { required: "Username is required", validate: validateUsername })}
            className={`w-full px-4 py-2 border rounded-md text-sm focus:ring-2 focus:ring-blue-600 focus:outline-none ${
              errors.username ? "border-red-500" : "border-gray-300"
            }`}
          />
          {errors.username && <p className="text-red-500 text-sm mt-1">{errors.username.message}</p>}
        </div>

        <div className="mb-5">
          <label htmlFor="password" className="flex items-center text-sm font-medium text-gray-700 mb-2">
            <FaLock className="text-blue-600 mr-2" /> Password
          </label>
          <input
            type="password"
            id="password"
            {...register("password", { required: "Password is required", validate: validatePassword })}
            className={`w-full px-4 py-2 border rounded-md text-sm focus:ring-2 focus:ring-blue-600 focus:outline-none ${
              errors.password ? "border-red-500" : "border-gray-300"
            }`}
          />
          {errors.password && <p className="text-red-500 text-sm mt-1">{errors.password.message}</p>}
        </div>

        <div className="mb-5">
          <label htmlFor="profile" className="flex items-center text-sm font-medium text-gray-700 mb-2">
            <FaUpload className="text-blue-600 mr-2" /> Profile Picture
          </label>
          <input
            type="file"
            id="profile"
            {...register("profile", { required: "Profile picture is required", validate: validateProfile })}
            className="w-full px-4 py-2 border rounded-md text-sm focus:outline-none"
          />
          {errors.profile && <p className="text-red-500 text-sm mt-1">{errors.profile.message}</p>}
        </div>

        <div className="mb-5">
          <label htmlFor="role" className="flex items-center text-sm font-medium text-gray-700 mb-2">
            <FaUsers className="text-blue-600 mr-2" /> Role
          </label>
          <select
            id="role"
            {...register("role", { required: "Role is required", validate: validateRole })}
            className={`w-full px-4 py-2 border rounded-md text-sm focus:ring-2 focus:ring-blue-600 focus:outline-none ${
              errors.role ? "border-red-500" : "border-gray-300"
            }`}
          >
            <option value="">Select a Role</option>
            <option value="SUPERADMIN">Superadmin</option>
            <option value="MODERATOR">Moderator</option>
            <option value="GUEST">Guest</option>
            <option value="STUDENT">Student</option>
            <option value="ADMINISTRATOR">Administrator</option>
            <option value="INSTRUCTOR">Instructor</option>
          </select>
          {errors.role && <p className="text-red-500 text-sm mt-1">{errors.role.message}</p>}
        </div>

        <div className="text-center">
          <button
            type="submit"
            className="w-full bg-blue-600 text-white py-2 px-4 rounded-md text-sm font-medium hover:bg-blue-800 transition duration-300"
          >
            Sign Up
          </button>
          <p className="text-sm text-gray-600 mt-4">
            Already have an account? <Link to="/login" className="text-blue-600 hover:underline">Login</Link>
          </p>
        </div>
      </form>
    </section>
  );
};

export default Signup;
