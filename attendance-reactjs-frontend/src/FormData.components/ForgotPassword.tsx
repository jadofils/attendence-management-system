import React from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import { FaEnvelope } from 'react-icons/fa';
import { Link } from 'react-router-dom';

// Define interface for form inputs
interface ForgotPasswordInputs {
  email: string;
}

const ForgotPassword: React.FC = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<ForgotPasswordInputs>({
    mode: 'onBlur',
  });

  const onSubmit: SubmitHandler<ForgotPasswordInputs> = (data) => {
    // Handle forgot password submission logic
    console.log('Password reset request for:', data.email);
  };

  return (
    <section
      id="forgot-password-form"
      className="p-6 sm:p-8 max-w-lg mx-auto shadow-md rounded-lg bg-white border border-gray-200"
    >
      <h2 className="text-2xl font-semibold mb-6 text-center text-gray-800">
        Forgot Password
      </h2>
      <form onSubmit={handleSubmit(onSubmit)} noValidate>
        {/* Email Field */}
        <div className="mb-5">
          <label
            htmlFor="email"
            className="text-sm font-medium text-gray-700 mb-2 flex items-center"
          >
            <FaEnvelope className="text-blue-600 mr-2" />
            Email Address
          </label>
          <input
            type="email"
            id="email"
            {...register('email', {
              required: 'Email is required',
              pattern: {
                value: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
                message: 'Enter a valid email address',
              },
            })}
            className={`w-full px-4 py-2 border rounded-md text-sm focus:ring-2 focus:ring-blue-600 focus:outline-none ${
              errors.email ? 'border-red-500' : 'border-gray-300'
            }`}
          />
          {errors.email && (
            <p className="text-red-500 text-sm mt-1">{errors.email.message}</p>
          )}
        </div>

        {/* Submit Button */}
        <div className="text-center">
          <button
            type="submit"
            className="w-full bg-blue-600 text-white py-2 px-4 rounded-md text-sm font-medium hover:bg-blue-800 transition duration-300"
          >
            Reset Password
          </button>
        </div>

        {/* Login Link */}
        <div className="text-center mt-4">
          <p className="text-sm text-gray-600">
            Remembered your password?{' '}
            <Link to="/login" className="text-blue-600 hover:underline">
              Login
            </Link>
          </p>
        </div>
      </form>
    </section>
  );
};

export default ForgotPassword;
