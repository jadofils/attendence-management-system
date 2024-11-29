import React from 'react';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { FaUser, FaEnvelope, FaPaperPlane } from 'react-icons/fa';

// Validation schema using Yup
const schema = yup
  .object({
    name: yup
      .string()
      .min(3, 'Name must be at least 3 characters')
      .required('Name is required'),
    email: yup
      .string()
      .email('Invalid email format')
      .required('Email is required'),
    message: yup
      .string()
      .min(10, 'Message must be at least 10 characters')
      .required('Message is required'),
  })
  .required();

type FormData = {
  name: string;
  email: string;
  message: string;
};

const ContactForm: React.FC = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm<FormData>({
    resolver: yupResolver(schema),
  });

  const onSubmit = (data: FormData) => {
    console.log(data);
    reset(); // Reset the form after submission
  };

  return (
    <section
      id="contact"
      className="p-6 sm:p-8 max-w-3xl mx-auto shadow-md rounded-lg bg-white border border-gray-200"
    >
      <h2 className="text-2xl font-semibold mb-6 text-center text-gray-800">
        Contact Us
      </h2>
      <form onSubmit={handleSubmit(onSubmit)} noValidate>
        {/* Name Field */}
        <div className="mb-5">
          <label
            htmlFor="name"
            className=" text-sm font-medium text-gray-700 mb-2 flex items-center"
          >
            <FaUser className="text-blue-600 mr-2" />
            Full Name
          </label>
          <input
            type="text"
            id="name"
            {...register('name')}
            className={`w-full px-4 py-2 border rounded-md text-sm focus:ring-2 focus:ring-blue-600 focus:outline-none ${
              errors.name ? 'border-red-500' : 'border-gray-300'
            }`}
          />
          {errors.name && (
            <p className="text-red-500 text-sm mt-1">{errors.name.message}</p>
          )}
        </div>

        {/* Email Field */}
        <div className="mb-5">
          <label
            htmlFor="email"
            className=" text-sm font-medium text-gray-700 mb-2 flex items-center"
          >
            <FaEnvelope className="text-blue-600 mr-2" />
            Email
          </label>
          <input
            type="email"
            id="email"
            {...register('email')}
            className={`w-full px-4 py-2 border rounded-md text-sm focus:ring-2 focus:ring-blue-600 focus:outline-none ${
              errors.email ? 'border-red-500' : 'border-gray-300'
            }`}
          />
          {errors.email && (
            <p className="text-red-500 text-sm mt-1">{errors.email.message}</p>
          )}
        </div>

        {/* Message Field */}
        <div className="mb-5">
          <label
            htmlFor="message"
            className=" text-sm font-medium text-gray-700 mb-2 flex items-center"
          >
            <FaPaperPlane className="text-blue-600 mr-2" />
            Message
          </label>
          <textarea
            id="message"
            {...register('message')}
            className={`w-full px-4 py-2 border rounded-md text-sm focus:ring-2 focus:ring-blue-600 focus:outline-none ${
              errors.message ? 'border-red-500' : 'border-gray-300'
            }`}
            rows={5}
          />
          {errors.message && (
            <p className="text-red-500 text-sm mt-1">{errors.message.message}</p>
          )}
        </div>

        {/* Submit Button */}
        <div className="text-center">
          <button
            type="submit"
            className="w-full bg-blue-600 text-white py-2 px-4 rounded-md text-sm font-medium hover:bg-blue-800 transition duration-300"
          >
            <FaPaperPlane className="inline mr-2" />
            Send Message
          </button>
        </div>
      </form>
    </section>
  );
};

export default ContactForm;
