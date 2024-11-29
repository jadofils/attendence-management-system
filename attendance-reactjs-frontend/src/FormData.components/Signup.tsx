import React from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import { FaUser, FaLock, FaUpload, FaUsers } from 'react-icons/fa';
import { Link } from 'react-router-dom';

// Define the interface for form inputs
interface SignupFormInputs {
  username: string;
  password: string;
  profile: FileList;
  role: string;
}

const Signup: React.FC = () => {
  const { 
    register, 
    handleSubmit, 
    formState: { errors } 
  } = useForm<SignupFormInputs>({
    mode: 'onBlur', // Validate on blur
  });

  const onSubmit: SubmitHandler<SignupFormInputs> = (data) => {
    console.log(data); // Replace with form submission logic (e.g., API call)
  };

  // Custom validation rules
  const validateUsername = (value: string) => 
    value.length >= 3 || 'Username must be at least 3 characters long';

  const validatePassword = (value: string) => 
    value.length >= 6 || 'Password must be at least 6 characters long';

  const validateProfile = (value: FileList) => 
    value.length > 0 && ['image/jpeg', 'image/png'].includes(value[0].type) || 
    'Please upload a valid image (JPEG or PNG)';

  const validateRole = (value: string) => 
    ['student', 'administrator', 'instructor'].includes(value) || 
    'Please select a valid role';

  return (
    <section
      id="signup-form"
      className="p-6 sm:p-8 max-w-3xl mx-auto shadow-md rounded-lg bg-white border border-gray-200"
    >
      <h2 className="text-2xl font-semibold mb-6 text-center text-gray-800">
        Sign Up
      </h2>
      <form onSubmit={handleSubmit(onSubmit)} noValidate>
        {/* Username Field */}
        <div className="mb-5">
          <label htmlFor="username" className="flex items-center text-sm font-medium text-gray-700 mb-2">
            <FaUser className="text-blue-600 mr-2" />
            Username
          </label>
          <input
            type="text"
            id="username"
            {...register('username', { 
              required: 'Username is required', 
              validate: validateUsername 
            })}
            className={`w-full px-4 py-2 border rounded-md text-sm focus:ring-2 focus:ring-blue-600 focus:outline-none ${
              errors.username ? 'border-red-500' : 'border-gray-300'
            }`}
          />
          {errors.username && (
            <p className="text-red-500 text-sm mt-1">{errors.username.message}</p>
          )}
        </div>

        {/* Password Field */}
        <div className="mb-5">
          <label htmlFor="password" className="flex items-center text-sm font-medium text-gray-700 mb-2">
            <FaLock className="text-blue-600 mr-2" />
            Password
          </label>
          <input
            type="password"
            id="password"
            {...register('password', { 
              required: 'Password is required', 
              validate: validatePassword 
            })}
            className={`w-full px-4 py-2 border rounded-md text-sm focus:ring-2 focus:ring-blue-600 focus:outline-none ${
              errors.password ? 'border-red-500' : 'border-gray-300'
            }`}
          />
          {errors.password && (
            <p className="text-red-500 text-sm mt-1">{errors.password.message}</p>
          )}
        </div>

        {/* Profile Picture Upload */}
        <div className="mb-5">
          <label htmlFor="profile" className="flex items-center text-sm font-medium text-gray-700 mb-2">
            <FaUpload className="text-blue-600 mr-2" />
            Profile Picture
          </label>
          <input
            type="file"
            id="profile"
            {...register('profile', { 
              required: 'Profile picture is required', 
              validate: validateProfile 
            })}
            className="w-full px-4 py-2 border rounded-md text-sm focus:outline-none"
          />
          {errors.profile && (
            <p className="text-red-500 text-sm mt-1">{errors.profile.message}</p>
          )}
        </div>

        {/* Role Dropdown */}
        <div className="mb-5">
          <label htmlFor="role" className="flex items-center text-sm font-medium text-gray-700 mb-2">
            <FaUsers className="text-blue-600 mr-2" />
            Role
          </label>
          <select
            id="role"
            {...register('role', { 
              required: 'Role is required', 
              validate: validateRole 
            })}
            className={`w-full px-4 py-2 border rounded-md text-sm focus:ring-2 focus:ring-blue-600 focus:outline-none ${
              errors.role ? 'border-red-500' : 'border-gray-300'
            }`}
          >
            <option value="">Select a Role</option>
            <option value="student">Student</option>
            <option value="administrator">Administrator</option>
            <option value="instructor">Instructor</option>
          </select>
          {errors.role && (
            <p className="text-red-500 text-sm mt-1">{errors.role.message}</p>
          )}
        </div>

        {/* Submit Button */}
        <div className="text-center">
          <button
            type="submit"
            className="w-full bg-blue-600 text-white py-2 px-4 rounded-md text-sm font-medium hover:bg-blue-800 transition duration-300"
          >
            Sign Up
          </button>
          <div className="text-center">

          <p className="text-sm text-gray-600">
            Already have an account? {' '}
            <Link 
              to="/login" 
              className="text-blue-600 hover:underline"
            >
            Login
            </Link>
          </p>
          </div>
        </div>
      </form>
    </section>
  );
};

export default Signup;
