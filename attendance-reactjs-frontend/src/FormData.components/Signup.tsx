import React from "react";
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
        navigate("/login"); // Redirect to login page using useNavigate
      })
      .catch((error) => {
  // Extract backend-specific error details
  const errorMessage =
    error.response?.data?.message || 
    error.message || 
    "An unexpected error occurred";

  const rootCause = 
    error.response?.data?.rootCause || 
    error.response?.data?.error || // Fallback to a general 'error' field, if provided
    "Unknown root cause";

  // Alert the combined error message with the root cause
  alert(`${errorMessage} \nRoot Cause: ${rootCause}`);

  // Log the full error details for debugging
  console.error("Error details:", error);
});

  };
  
  const validateUsername = (value: string) =>
    value.length >= 3 || "Username must be at least 3 characters long";

  const validatePassword = (value: string) =>
    value.length >= 6 || "Password must be at least 6 characters long";

  const validateProfile = (value: FileList) => {
    const file = value[0];
    const maxSize = 100 * 1024 * 1024; // 100 MB in bytes

    if (file) {
      if (!["image/jpeg", "image/png"].includes(file.type)) {
        return "Please upload a valid image (JPEG or PNG)";
      }
      if (file.size > maxSize) {
        return "File size must be less than or equal to 100 MB";
      }
    }
    return true;
  };

  const validateRole = (value: string) =>
    ["SUPERADMIN", "MODERATOR", "GUEST", "STUDENT", "ADMINISTRATOR", "INSTRUCTOR"].includes(value) ||
    "Please select a valid role";

  return (
    <section
      id="signup-form"
      className="p-6 sm:p-8 max-w-3xl mx-auto shadow-md rounded-lg bg-white border border-gray-200"
    >
      <h2 className="text-2xl font-semibold mb-6 text-center text-gray-800">Sign Up</h2>
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
