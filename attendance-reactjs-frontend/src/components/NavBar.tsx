import React, { useState } from "react";
import { Link } from "react-router-dom";
import logo from "../assets/logo.jpeg";
import {
  FaHome,
  FaUser,
  FaInfoCircle,
  FaServicestack,
  FaPhone,
  FaBars,
  FaTimes,
} from "react-icons/fa";

const Navbar: React.FC = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  const navLinks = [
    { to: "/", icon: FaHome, text: "Home" },
    { to: "/signup", icon: FaUser, text: "Register" },
    { to: "/login", icon: FaUser, text: "Login" },
    { to: "/dashboard", icon: FaInfoCircle, text: "Dashboard" },
    { to: "/#about", icon: FaServicestack, text: "About" },
    { to: "/#contact", icon: FaPhone, text: "Contact" },
  ];

  return (
    <nav className="bg-secondary p-4 border border-secondary sticky m-h-[100vh] top-0 z-50">
      <div className="container mx-auto flex items-center justify-between">
        {/* Logo Section */}
        <div className="flex items-center space-x-3">
  {/* Logo Image */}
  <div className="relative overflow-hidden rounded-full group ">
    <img
      src={logo}
      alt="Logo"
      className="h-10 w-10ml-[-3sm]  object-cover group-hover:scale-110 transition-transform duration-300 ease-in-out"
    />
    <div className="absolute inset-0 bg-black opacity-0 group-hover:opacity-25 transition-opacity duration-300"></div>
  </div>

  {/* Text */}
  <p className="text-primary text-sm">College Management System</p>
</div>


        {/* Mobile Menu Toggle */}
        <div className="md:hidden">
          <button onClick={toggleMenu} className="text-primary focus:outline-none">
            {isMenuOpen ? <FaTimes size={24} /> : <FaBars size={24} />}
          </button>
        </div>

        {/* Desktop Navigation */}
        <ul className="hidden md:flex space-x-6">
          {navLinks.map(({ to, icon: Icon, text }, index) => (
            <li key={index}>
              <Link
                to={to}
                className="flex items-center text-primary text-base hover:text-blue-500"
              >
                <Icon className="mr-2 text-blue-600" />
                {text}
              </Link>
            </li>
          ))}
        </ul>
      </div>

      {/* Mobile Menu Overlay */}
      {isMenuOpen && (
        <div className="fixed inset-0 bg-secondary bg-opacity-95 md:hidden">
          <ul className="flex flex-col items-center justify-center h-full space-y-6">
            {navLinks.map(({ to, icon: Icon, text }, index) => (
              <li key={index}>
                <Link
                  to={to}
                  onClick={toggleMenu}
                  className="flex items-center text-primary text-xl hover:text-blue-500"
                >
                  <Icon className="mr-4 text-blue-600" size={24} />
                  {text}
                </Link>
              </li>
            ))}
          </ul>
        </div>
      )}
    </nav>
  );
};

export default Navbar;
