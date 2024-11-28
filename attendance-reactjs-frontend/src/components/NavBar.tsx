import React, { useState } from "react";
import logo from "../assets/logo.jpeg";
import {
  FaHome,
  FaUser,
  FaInfoCircle,
  FaServicestack,
  FaPhone,
  FaBars,
  FaTimes
} from "react-icons/fa";

const Navbar: React.FC = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  const navLinks = [
    { href: "#home", icon: FaHome, text: "Home" },
    { href: "#register", icon: FaUser, text: "Register/Login" },
    { href: "#about", icon: FaInfoCircle, text: "About" },
    { href: "#services", icon: FaServicestack, text: "Services" },
    { href: "#contact", icon: FaPhone, text: "Contact" }
  ];

  return (
    <nav className="bg-secondary p-4 border border-secondary sticky top-0 z-50">
      <div className="container mx-auto flex items-center justify-between">
        {/* Logo Section */}
        <div className="flex flex-col items-center">
          <div className="relative overflow-hidden rounded-full group">
            <img
              src={logo}
              alt="Logo"
              className="h-10 w-10 object-cover group-hover:scale-110 transition-transform duration-300 ease-in-out"
            />
            <div className="absolute inset-0 bg-black opacity-0 group-hover:opacity-25 transition-opacity duration-300"></div>
          </div>
          <p className="text-primary text-sm mt-2">College Management System</p>
        </div>

        {/* Mobile Menu Toggle */}
        <div className="md:hidden">
          <button 
            onClick={toggleMenu} 
            className="text-primary focus:outline-none"
          >
            {isMenuOpen ? <FaTimes size={24} /> : <FaBars size={24} />}
          </button>
        </div>

        {/* Desktop Navigation */}
        <ul className="hidden md:flex space-x-6">
          {navLinks.map((link, index) => (
            <li key={index}>
              <a
                href={link.href}
                className="flex items-center text-primary text-base hover:text-blue-500"
              >
                <link.icon className="mr-2 text-blue-600" />
                {link.text}
              </a>
            </li>
          ))}
        </ul>
      </div>

      {/* Mobile Menu Overlay */}
      {isMenuOpen && (
        <div className="fixed inset-0 bg-secondary bg-opacity-95 md:hidden">
          <ul className="flex flex-col mt-[-20px] items-center justify-center h-full space-y-6">
            {navLinks.map((link, index) => (
              <li key={index}>
                <a
                  href={link.href}
                  onClick={toggleMenu}
                  className="flex items-center text-primary text-xl hover:text-blue-500"
                >
                  <link.icon className="mr-4 text-blue-600" size={24} />
                  {link.text}
                </a>
              </li>
            ))}
          </ul>
        </div>
      )}
    </nav>
  );
};

export default Navbar;