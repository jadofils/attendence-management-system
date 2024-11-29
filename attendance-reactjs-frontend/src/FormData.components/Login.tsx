import React, { useEffect, useState } from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import { FaUser, FaLock } from 'react-icons/fa';
import { Link, useNavigate } from 'react-router-dom';
import loginUser from '../service/loginService';

// Define interface for login form inputs
interface LoginFormInputs {
  username: string;
  password: string;
  role: string; // No need to use UserRole enum here, just string
}

const Login: React.FC = () => {
  const navigate = useNavigate();
  const [roles, setRoles] = useState<string[]>([]); // State for storing available roles
  const { 
    register, 
    handleSubmit, 
    formState: { errors } 
  } = useForm<LoginFormInputs>({
    mode: 'onBlur'
  });

  useEffect(() => {
    // Fetch roles from the signup process or a mock data source
    const fetchedRoles: string[] = [
      'ADMINISTRATOR',
      'INSTRUCTOR',
      'STUDENT',
      'MODERATOR',
      'GUEST',
      'SUPERADMIN',
    ]; // This could come from an API or other source
    setRoles(fetchedRoles);
  }, []);

  const onSubmit: SubmitHandler<LoginFormInputs> = async (data) => {
    try {
      console.log('Login data:', data);

      // Call the login service to send the data to the backend
      const response = await loginUser(data);

      // Check for success response message
      if (response.message === 'Login successful') {
        console.log('Login successful:', response.user);

        // Based on the role, navigate to the appropriate dashboard
        if (data.role === 'ADMINISTRATOR') {
          alert("User Logged In Successfully");
          navigate('/dashboard');
        } else if (data.role === 'INSTRUCTOR') {
          alert("User Logged In Successfully");

          navigate('/instrdashboard');
        } else if (data.role === 'STUDENT') {
          alert("User Logged In Successfully");

          navigate('/dashboard');
        } else if (data.role === 'MODERATOR') {
          alert("User Logged In Successfully");

          navigate('/dashboard');
        } else if (data.role === 'GUEST') {
          alert("User Logged In Successfully");

          navigate('/dashboard');
        } else if (data.role === 'SUPERADMIN') {
          alert("User Logged In Successfully");

          navigate('/dashboard');
        }
      } else {
        // Handle failed login
        console.log('Login failed:', response.message);
        alert(`Login failed: ${response.message}`);
      }
    } catch (error) {
      console.log('Error during login:', error);
      alert('Error during login');
    }
  };

  return (
    <section id="login-form" className="p-6 sm:p-8 max-w-3xl mx-auto shadow-md rounded-lg bg-white border border-gray-200">
      <h2 className="text-2xl font-semibold mb-6 text-center text-gray-800">Login</h2>
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
            className={`w-full px-4 py-2 border rounded-md text-sm focus:ring-2 focus:ring-blue-600 focus:outline-none ${errors.username ? 'border-red-500' : 'border-gray-300'}`}
          />
          {errors.username && <p className="text-red-500 text-sm mt-1">{errors.username.message}</p>}
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
            className={`w-full px-4 py-2 border rounded-md text-sm focus:ring-2 focus:ring-blue-600 focus:outline-none ${errors.password ? 'border-red-500' : 'border-gray-300'}`}
          />
          {errors.password && <p className="text-red-500 text-sm mt-1">{errors.password.message}</p>}
        </div>

        {/* Role Dropdown */}
        <div className="mb-5">
          <label htmlFor="role" className="text-sm font-medium text-gray-700 mb-2 flex items-center">
            Role
          </label>
          <select
            id="role"
            {...register('role', { required: 'Role is required' })}
            className={`w-full px-4 py-2 border rounded-md text-sm focus:ring-2 focus:ring-blue-600 focus:outline-none ${errors.role ? 'border-red-500' : 'border-gray-300'}`}
          >
            <option value="">Select Role</option>
            {roles.map((role) => (
              <option key={role} value={role}>
                {role.charAt(0).toUpperCase() + role.slice(1).toLowerCase()}
              </option>
            ))}
          </select>
          {errors.role && <p className="text-red-500 text-sm mt-1">{errors.role.message}</p>}
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
            Forgot your password? {' '}
            <Link to="/forgot-password" className="text-blue-600 hover:underline">Forgot Password</Link>
          </p>
        </div>

        {/* Sign Up Link */}
        <div className="text-center">
          <p className="text-sm text-gray-600">
            Don't have an account? {' '}
            <Link to="/signup" className="text-blue-600 hover:underline">Sign Up</Link>
          </p>
        </div>
      </form>
    </section>
  );
};

export default Login;
