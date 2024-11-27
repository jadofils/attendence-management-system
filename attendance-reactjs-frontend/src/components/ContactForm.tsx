import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { FaUser, FaEnvelope, FaPaperPlane } from 'react-icons/fa';

// Validation schema using Yup
const schema = yup.object({
  name: yup.string().min(3, 'Name must be at least 3 characters').required('Name is required'),
  email: yup.string().email('Invalid email format').required('Email is required'),
  message: yup.string().min(10, 'Message must be at least 10 characters').required('Message is required'),
}).required();

type FormData = {
  name: string;
  email: string;
  message: string;
};

const ContactForm = () => {
  const { register, handleSubmit, formState: { errors }, reset } = useForm<FormData>({
    resolver: yupResolver(schema),
  });

  const onSubmit = (data: FormData) => {
    console.log(data);
    // You can make API calls here or further handle the form data
    reset(); // Reset the form after submission
  };

  return (
    <section id="contact-us" className="p-8 max-w-lg mx-auto shadow-lg rounded-lg bg-white">
      <h2 className="text-xl font-semibold mb-4 text-center text-gray-800">Contact Us</h2>
      <form onSubmit={handleSubmit(onSubmit)} noValidate>
        {/* Name */}
        <div className="mb-4">
          <label htmlFor="name" className="block text-base text-gray-700 flex items-center">
            <FaUser className="text-blue-600 mr-2" /> Full Name
          </label>
          <input
            type="text"
            id="name"
            {...register('name')}
            className={`w-full px-4 py-2 text-base border rounded-md focus:outline-none ${errors.name ? 'border-red-500' : 'border-gray-300'}`}
          />
          {errors.name && <p className="text-red-500 text-sm">{errors.name.message}</p>}
        </div>

        {/* Email */}
        <div className="mb-4">
          <label htmlFor="email" className="block text-base text-gray-700 flex items-center">
            <FaEnvelope className="text-blue-600 mr-2" /> Email
          </label>
          <input
            type="email"
            id="email"
            {...register('email')}
            className={`w-full px-4 py-2 border text-base rounded-md focus:outline-none ${errors.email ? 'border-red-500' : 'border-gray-300'}`}
          />
          {errors.email && <p className="text-red-500 text-sm">{errors.email.message}</p>}
        </div>

        {/* Message */}
        <div className="mb-4">
          <label htmlFor="message" className="block text-base text-gray-700 flex items-center">
            <FaPaperPlane className="text-blue-600 mr-2" /> Message
          </label>
          <textarea
            id="message"
            {...register('message')}
            className={`w-full px-4 py-2 border rounded-md text-base focus:outline-none ${errors.message ? 'border-red-500' : 'border-gray-300'}`}
          />
          {errors.message && <p className="text-red-500 text-sm">{errors.message.message}</p>}
        </div>

        {/* Submit Button */}
        <div className="text-center">
          <button
            type="submit"
            className="w-full bg-blue-600 text-white py-2 text-base rounded-md hover:bg-blue-800"
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
