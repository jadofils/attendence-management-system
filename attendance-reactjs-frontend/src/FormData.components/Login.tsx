import React, { useEffect, useState } from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import { FaUser, FaLock } from 'react-icons/fa';
import { Link, useNavigate } from 'react-router-dom';

// Define UserRole enum to reflect the available roles
// eslint-disable-next-line react-refresh/only-export-components
export enum UserRole {
  ADMINISTRATOR = 'ADMINISTRATOR',
  INSTRUCTOR = 'INSTRUCTOR',
  STUDENT = 'STUDENT',
  MODERATOR = 'MODERATOR',
  GUEST = 'GUEST',
  SUPERADMIN = 'SUPERADMIN',
}

// Define interface for login form inputs
interface LoginFormInputs {
  username: string;
  password: string;
  role: UserRole; // Use UserRole enum for the role field
}

const Login: React.FC = () => {
  const navigate = useNavigate(); // Initialize the navigate function
  const [roles, setRoles] = useState<UserRole[]>([]); // State for storing available roles
  const { 
    register, 
    handleSubmit, 
    formState: { errors } 
  } = useForm<LoginFormInputs>({
    mode: 'onBlur'
  });

  useEffect(() => {
    // Fetch roles from the signup process or a mock data source
    const fetchedRoles: UserRole[] = [
      UserRole.ADMINISTRATOR,
      UserRole.INSTRUCTOR,
      UserRole.STUDENT,
      UserRole.MODERATOR,
      UserRole.GUEST,
      UserRole.SUPERADMIN,
    ]; // This could come from an API or other source
    setRoles(fetchedRoles);
  }, []);

  const onSubmit: SubmitHandler<LoginFormInputs> = async (data) => {
    console.log('Login data:', data);
    
    // Navigate based on selected role
    if (data.role === UserRole.ADMINISTRATOR) {
      navigate('/admin-dashboard');
    } else if (data.role === UserRole.INSTRUCTOR) {
      navigate('/instructor-dashboard');
    } else if (data.role === UserRole.STUDENT) {
      navigate('/student-dashboard');
    } else if (data.role === UserRole.MODERATOR) {
      navigate('/moderator-dashboard');
    } else if (data.role === UserRole.GUEST) {
      navigate('/guest-dashboard');
    } else if (data.role === UserRole.SUPERADMIN) {
      navigate('/superadmin-dashboard');
    }
  };

  return (
    <section
      id="login-form"
      className="p-6 sm:p-8 max-w-3xl mx-auto shadow-md rounded-lg bg-white border border-gray-200"
    >
      <h2 className="text-2xl font-semibold mb-6 text-center text-gray-800">
        Login
      </h2>
      <form onSubmit={handleSubmit(onSubmit)} noValidate>
        {/* Username Field */}
        <div className="mb-5">
          <label
            htmlFor="username"
            className="text-sm font-medium text-gray-700 mb-2 flex items-center"
          >
            <FaUser className="text-blue-600 mr-2" />
            Username
          </label>
          <input
            type="text"
            id="username"
            {...register('username', { 
              required: 'Username is required'
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
          <label
            htmlFor="password"
            className="text-sm font-medium text-gray-700 mb-2 flex items-center"
          >
            <FaLock className="text-blue-600 mr-2" />
            Password
          </label>
          <input
            type="password"
            id="password"
            {...register('password', { 
              required: 'Password is required'
            })}
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
          <label
            htmlFor="role"
            className="text-sm font-medium text-gray-700 mb-2 flex items-center"
          >
            Role
          </label>
          <select
            id="role"
            {...register('role', { 
              required: 'Role is required'
            })}
            className={`w-full px-4 py-2 border rounded-md text-sm focus:ring-2 focus:ring-blue-600 focus:outline-none ${
              errors.role ? 'border-red-500' : 'border-gray-300'
            }`}
          >
            <option value="">Select Role</option>
            {roles.map((role) => (
              <option key={role} value={role}>
                {role.charAt(0).toUpperCase() + role.slice(1).toLowerCase()} {/* Capitalize first letter */}
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
            Forgot your password? {' '}
            <Link 
              to="/forgot-password" 
              className="text-blue-600 hover:underline"
            >
              Forgot Password
            </Link>
          </p>
        </div>

        {/* Sign Up Link */}
        <div className="text-center">
          <p className="text-sm text-gray-600">
            Don't have an account? {' '}
            <Link 
              to="/signup" 
              className="text-blue-600 hover:underline"
            >
              Sign Up
            </Link>
          </p>
        </div>
      </form>
    </section>
  );
};

export default Login;
