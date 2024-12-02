/* eslint-disable @typescript-eslint/no-explicit-any */
import React, { useEffect, useState } from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import { FaUser, FaLock } from 'react-icons/fa';
import { Link, useNavigate } from 'react-router-dom';
import loginUser, { User } from '../service/loginService';

// Define interface for login form inputs
interface LoginFormInputs {
  username: string;
  password: string;
  role: string;
}

const Login: React.FC = () => {
  const navigate = useNavigate();
  const [roles, setRoles] = useState<string[]>([]); // Role state
  const [message, setMessage] = useState<string>(''); // Message state
  const [loggedInUser, setLoggedInUser] = useState<User | null>(null);

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginFormInputs>({
    mode: 'onBlur',
  });

  useEffect(() => {
    // Simulate fetching roles from API or static list
    setRoles([
      'ADMINISTRATOR',
      'INSTRUCTOR',
      'STUDENT',
      'MODERATOR',
      'GUEST',
      'SUPERADMIN',
    ]);
  }, []);

  const onSubmit: SubmitHandler<LoginFormInputs> = async (data) => {
    try {
      // Log form data to ensure correctness
      console.log('Login data:', data);

      // Send data to the backend service
      const response = await loginUser(data);

      // Check response message
      if (response.message === 'Login successful') {
        setLoggedInUser(response.user);
        setMessage(`You are logged in successfully, ${response.user.username}!`);

        // Redirect after a delay based on user role
        setTimeout(() => {
          const redirectPath =
            response.user.role === 'STUDENT' ? '/dashboard' : '/dashboard';
          navigate(redirectPath);
        }, 5000); // Redirect after 5 seconds
      } else {
        setMessage(`Login failed: ${response.message}`);
      }
    } catch (error: any) {
      // Handle any errors from the API
      const errorMessage =
        error.response?.data?.message || error.message || 'An unexpected error occurred';
      setMessage(`Error: ${errorMessage}`);
      console.log('Error details:', error);
    }
  };

  return (
    <section className="p-6 sm:p-8 max-w-3xl mx-auto shadow-md rounded-lg bg-white border border-gray-200">
      <h2 className="text-2xl font-semibold mb-6 text-center text-gray-800">Login</h2>

      {/* Display message */}
      {message && (
        <div
          className={`text-center py-2 px-4 mb-4 rounded-md ${
            message.includes('success') ? 'bg-green-200 text-green-700' : 'bg-red-200 text-red-700'
          }`}
        >
          {message}
        </div>
      )}

      {/* Display logged-in user details */}
      {loggedInUser && (
        <div className="bg-blue-100 text-blue-800 p-4 rounded-md mb-4">
          <h3 className="font-semibold mb-2">Logged In User Details:</h3>
          <p><strong>Username:</strong> {loggedInUser.username}</p>
          <p><strong>Role:</strong> {loggedInUser.role}</p>
        </div>
      )}

      <form onSubmit={handleSubmit(onSubmit)} noValidate>
        {/* Username Field */}
        <div className="mb-5">
          <label htmlFor="username" className="text-sm font-medium text-gray-700 mb-2 flex items-center">
            <FaUser className="text-blue-600 mr-2" />
            Username
          </label>
          <input
            type="text"
            id="username"
            {...register('username', { required: 'Username is required' })}
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
          <label htmlFor="password" className="text-sm font-medium text-gray-700 mb-2 flex items-center">
            <FaLock className="text-blue-600 mr-2" />
            Password
          </label>
          <input
            type="password"
            id="password"
            {...register('password', { required: 'Password is required' })}
            className={`w-full px-4 py-2 border rounded-md text-sm focus:ring-2 focus:ring-blue-600 focus:outline-none ${
              errors.password ? 'border-red-500' : 'border-gray-300'
            }`}
          />
          {errors.password && (
            <p className="text-red-500 text-sm mt-1">{errors.password.message}</p>
          )}
        </div>

        {/* Role Dropdown */}
        <div className="mb-5">
          <label htmlFor="role" className="text-sm font-medium text-gray-700 mb-2 flex items-center">
            Role
          </label>
          <select
            id="role"
            {...register('role', { required: 'Role is required' })}
            className={`w-full px-4 py-2 border rounded-md text-sm focus:ring-2 focus:ring-blue-600 focus:outline-none ${
              errors.role ? 'border-red-500' : 'border-gray-300'
            }`}
          >
            <option value="">Select Role</option>
            {roles.map((role) => (
              <option key={role} value={role}>
                {role.charAt(0).toUpperCase() + role.slice(1).toLowerCase()}
              </option>
            ))}
          </select>
          {errors.role && (
            <p className="text-red-500 text-sm mt-1">{errors.role.message}</p>
          )}
        </div>

        {/* Submit Button */}
        <div className="text-center mb-4">
          <button
            type="submit"
            className="w-full bg-blue-600 text-white py-2 px-4 rounded-md text-sm font-medium hover:bg-blue-800 transition duration-300"
          >
            Login
          </button>
        </div>

        {/* Forgot Password Link */}
        <div className="text-center">
          <p className="text-sm text-gray-600">
            Forgot your password?{' '}
            <Link to="/forgot-password" className="text-blue-600 hover:underline">
              Forgot Password
            </Link>
          </p>
        </div>

        {/* Sign Up Link */}
        <div className="text-center mt-4">
          <p className="text-sm text-gray-600">
            Don't have an account?{' '}
            <Link to="/register" className="text-blue-600 hover:underline">
              Sign Up
            </Link>
          </p>
        </div>
      </form>
    </section>
  );
};

export default Login;
