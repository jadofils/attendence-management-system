import React from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import { FaUser, FaLock } from 'react-icons/fa';
import { Link } from 'react-router-dom';


// Define interface for login form inputs
interface LoginFormInputs {
  username: string;
  password: string;
}

const Login: React.FC = () => {
  const { 
    register, 
    handleSubmit, 
    formState: { errors } 
  } = useForm<LoginFormInputs>({
    mode: 'onBlur'
  });

  const onSubmit: SubmitHandler<LoginFormInputs> = (data) => {
    // Handle login logic here
    console.log(data);
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

        {/* Submit Button */}
        <div className="text-center mb-4">
          <button
            type="submit"
            className="w-full bg-blue-600 text-white py-2 px-4 rounded-md text-sm font-medium hover:bg-blue-800 transition duration-300"
          >
            Login
          </button>
        </div>

 {/* Sign Up Link */}
 <div className="text-center">
          <p className="text-sm text-gray-600">
           Don't you Remember your Password? {' '}
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