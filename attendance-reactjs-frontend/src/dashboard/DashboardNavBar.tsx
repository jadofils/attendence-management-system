import React, { useState, useEffect } from "react";
import { FaBell, FaPhoneAlt } from "react-icons/fa";
import logo from '../assets/logo.jpeg'
const DashboardNavbar: React.FC = () => {
  const [time, setTime] = useState<string>("00:00:00");

  // Timer function to update time every second
  useEffect(() => {
    const timer = setInterval(() => {
      const currentTime = new Date();
      const hours = String(currentTime.getHours()).padStart(2, "0");
      const minutes = String(currentTime.getMinutes()).padStart(2, "0");
      const seconds = String(currentTime.getSeconds()).padStart(2, "0"); // Add seconds
      setTime(`${hours}:${minutes}:${seconds}`); // Update time with seconds
    }, 1000);

    return () => clearInterval(timer); // Cleanup on unmount
  }, []);

  return (
    <nav className="bg-secondary text-black px-6 py-4 shadow-md flex 
    justify-between items-center fixed w-full z-10 mx-4 mt-4 rounded-lg ">


      {/* Left Section with Logo and College Name */}
      <div className="flex items-center space-x-4">
        {/* Logo (You can replace the img with your actual logo file) */}
        <img src={logo} alt="Logo" className="h-8 w-8" />
        <span className="text-xl font-bold text-base">College Management System</span>
      </div>

      {/* Search Bar */}
      <div className="relative flex-1 max-w-lg">
        <input
          type="text"
          placeholder="Search..."
          className="pl-10 pr-4 py-2 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500 text-sm w-full"
        />
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="h-5 w-5 text-gray-400 absolute left-3 top-1/2 transform -translate-y-1/2"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth="2"
            d="M11 19a8 8 0 100-16 8 8 0 000 16zm6-10l4 4"
          />
        </svg>
      </div>

      {/* Timer */}
      <div className="text-lg font-semibold">{time}</div>

      {/* Right Section */}
      <div className="flex items-center space-x-6">
        {/* Notifications Icon */}
        <div className="relative">
          <FaBell className="text-blue-600 text-2xl cursor-pointer" />
          <span className="absolute top-0 right-0 bg-red-600 text-white text-xs rounded-full w-5 h-5 flex items-center justify-center">
            3
          </span>
        </div>

        {/* Contacts Icon Link */}
        <a href="/contacts" className="text-blue-600">
          <FaPhoneAlt className="text-2xl cursor-pointer" />
        </a>
      </div>
    </nav>
  );
};

export default DashboardNavbar;
