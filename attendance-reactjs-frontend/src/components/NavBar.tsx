import React from "react";
import logo from "../assets/logo.jpeg";
import {
  FaHome,
  FaUser,
  FaInfoCircle,
  FaServicestack,
  FaPhone,
} from "react-icons/fa";

const Navbar: React.FC = () => {
  return (
    <nav className="bg-secondary p-4 border border-secondary flex items-center justify-between sticky top-0 z-50 pb-4">
      {/* Right side - Logo and Company Name */}
      <div className="flex flex-col items-center">
        <div className="relative overflow-hidden rounded-full group">
          {/* Logo Image */}
          <img
            src={logo}
            alt="Logo"
            className="h-10 w-10 object-cover group-hover:scale-110 transition-transform duration-300 ease-in-out"
          />
          {/* Optional Overlay */}
          <div className="absolute inset-0 bg-black opacity-0 group-hover:opacity-25 transition-opacity duration-300"></div>
        </div>
        {/* Company Name */}
        <p className="text-primary text-sm mt-2">College Management System</p>
      </div>

      {/* Left side - Navigation Links with Icons */}
      <ul className="flex space-x-6 ">
        <li>
          <a
            href="#home"
            className="flex items-center text-primary text-base hover:text-blue-500"
          >
            <FaHome className="mr-2 text-blue-600" />
            Home
          </a>
        </li>
        <li>
          <a
            href="#register"
            className="flex items-center text-primary text-base hover:text-blue-500"
          >
            <FaUser className="mr-2 text-blue-600" />
            Register/Login
          </a>
        </li>
        <li>
          <a
            href="#about"
            className="flex items-center text-primary text-base hover:text-blue-500"
          >
            <FaInfoCircle className="mr-2 text-blue-600" />
            About
          </a>
        </li>
        <li>
          <a
            href="#services"
            className="flex items-center text-primary text-base hover:text-blue-500"
          >
            <FaServicestack className="mr-2 text-blue-600" />
            Services
          </a>
        </li>
        <li>
          <a
            href="#contact"
            className="flex items-center text-primary text-base hover:text-blue-500"
          >
            <FaPhone className="mr-2 text-blue-600" />
            Contact
          </a>
        </li>
      </ul>
    </nav>
  );
};

export default Navbar;
