/* eslint-disable @typescript-eslint/no-explicit-any */
import React, { useState, useEffect } from "react";
import { useForm, SubmitHandler } from "react-hook-form";
import { FaUser, FaLock, FaUpload, FaUsers } from "react-icons/fa";
import { useNavigate, useLocation } from "react-router-dom";
import { createUser, updateUser } from "../service/userService";
import axios from "axios";

interface SignupFormInputs {
  username: string;
  password: string;
  profile: FileList;
  role: string;
}

const Signup: React.FC = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [isEditMode, setIsEditMode] = useState(false);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [userId, setUserId] = useState<string | null>(null);

  const {
    register,
    handleSubmit,
    setValue,
    reset,
    formState: { errors },
  } = useForm<SignupFormInputs>({ mode: "onBlur" });

  // Extract userId from URL query parameters
  useEffect(() => {
    const queryParams = new URLSearchParams(location.search);
    const userIdFromUrl = queryParams.get('userId');
    const username = queryParams.get('username');

    if (userIdFromUrl) {
      setIsEditMode(true);
      setUserId(userIdFromUrl);
      
      // Pre-fill username from URL
      if (username) {
        setValue('username', username);
      }
    } else {
      // Reset form if not in edit mode
      reset();
      setIsEditMode(false);
      setUserId(null);
    }
  }, [location.search]);

  // Fetch user data if editing
  useEffect(() => {
    const fetchUserData = async () => {
      if (!userId) return;

      try {
        setIsLoading(true);
        const response = await axios.get(`http://localhost:8080/api/users/${userId}`);
        const userData = response.data;

        // Populate form with fetched user data
        setValue('username', userData.username);
        setValue('role', userData.role);
        
        // Note: We don't pre-fill password for security reasons
        setIsLoading(false);
      } catch (error) {
        setErrorMessage('Failed to fetch user data');
        setIsLoading(false);
        console.error('Error fetching user data:', error);
      }
    };

    if (isEditMode) {
      fetchUserData();
    }
  }, [userId, isEditMode]);

  const onSubmit: SubmitHandler<SignupFormInputs> = async (data) => {
    setIsLoading(true);
    setErrorMessage(null);
  
    try {
      let user;
      if (isEditMode && userId) {
        // Prepare update payload with only username and role
        user = {
          username: data.username,
          role: data.role,
        };
        await updateUser(userId, user);
        setErrorMessage("User updated successfully!");
        setTimeout(() => navigate("/dashboard"), 2000);
      } else {
        // Prepare create payload with all fields
        user = {
          username: data.username,
          password: data.password,
          role: data.role,
          profile: data.profile[0], // First file
        };
        await createUser(user);
        setErrorMessage("User created successfully!");
        setTimeout(() => navigate("/login"), 2000);
      }
    } catch (error: any) {
      const errorMessage =
        error.response?.data?.message ||
        error.message ||
        "An unexpected error occurred";
      const rootCause =
        error.response?.data?.rootCause ||
        error.response?.data?.error ||
        "Unknown root cause";
  
      setErrorMessage(`${errorMessage} \nRoot Cause: ${rootCause}`);
      console.error("Error details:", error);
    } finally {
      setIsLoading(false);
    }
  };
  

  const validateUsername = (value: string) => {
    if (value.length < 3) {
      return "Username must be at least 3 characters long";
    }
    return true;
  };

  const validatePassword = (value: string) => {
    // Only validate password if not in edit mode or if a password is provided
    if (!isEditMode || value) {
      if (value.length < 6) {
        return "Password must be at least 6 characters long";
      }
    }
    return true;
  };

  const validateProfile = (value: FileList) => {
    // Only require profile in create mode
    if (!isEditMode && value.length === 0) {
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
      <h2 className="text-2xl font-semibold mb-6 text-center text-gray-800">
        {isEditMode ? "Update User" : "Sign Up"}
      </h2>

      {/* Display signup/update message */}
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
            {...register("username", { 
              required: "Username is required", 
              validate: validateUsername 
            })}
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
            {...register("password", { 
              validate: validatePassword 
            })}
            className={`w-full px-4 py-2 border rounded-md text-sm focus:ring-2 focus:ring-blue-600 focus:outline-none ${
              errors.password ? "border-red-500" : "border-gray-300"
            }`}
            placeholder={isEditMode ? "Leave blank to keep existing password" : ""}
          />
          {errors.password && <p className="text-red-500 text-sm mt-1">{errors.password.message}</p>}
          {isEditMode && <p className="text-sm text-gray-600 mt-1">Leave blank to keep existing password</p>}
        </div>

        <div className="mb-5">
          <label htmlFor="profile" className="flex items-center text-sm font-medium text-gray-700 mb-2">
            <FaUpload className="text-blue-600 mr-2" /> Profile Picture
          </label>
          <input
            type="file"
            id="profile"
            {...register("profile", { 
              validate: validateProfile 
            })}
            className="w-full px-4 py-2 border rounded-md text-sm focus:outline-none"
          />
          {errors.profile && <p className="text-red-500 text-sm mt-1">{errors.profile.message}</p>}
          {isEditMode && <p className="text-sm text-gray-600 mt-1">Leave blank to keep existing profile picture</p>}
        </div>

        <div className="mb-5">
          <label htmlFor="role" className="flex items-center text-sm font-medium text-gray-700 mb-2">
            <FaUsers className="text-blue-600 mr-2" /> Role
          </label>
          <select
            id="role"
            {...register("role", { 
              required: "Role is required", 
              validate: validateRole 
            })}
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

        <div className="mb-6 flex justify-center">
          <button
            type="submit"
            disabled={isLoading}
            className={`px-6 py-2 text-white rounded-md text-sm font-semibold focus:outline-none ${
              isLoading 
                ? "bg-gray-400 cursor-not-allowed" 
                : "bg-blue-600 hover:bg-blue-700"
            }`}
          >
            {isLoading 
              ? (isEditMode ? "Updating..." : "Signing Up...") 
              : (isEditMode ? "Update" : "Sign Up")
            }
          </button>
        </div>
      </form>
    </section>
  );
};

export default Signup;