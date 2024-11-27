// Navbar.tsx
import React from 'react';

const Navbar: React.FC = () => {
  return (
    <nav className="bg-primary p-4 flex items-center justify-between">

      {/* Right side - Logo */}
      <div className="flex items-center">
        <img src="/path-to-your-logo.png" alt="Logo" className="h-10" />
      </div>
      {/* Left side - Navigation Links */}
      <ul className="flex space-x-6">
        <li><a href="#home" className="text-primary text-base hover:text-blue-500">Home</a></li>
        <li><a href="#about" className="text-primary text-base hover:text-blue-500">About</a></li>
        <li><a href="#services" className="text-primary text-base hover:text-blue-500">Services</a></li>
        <li><a href="#contact" className="text-primary text-base hover:text-blue-500">Contact</a></li>
      </ul>
      
      
    </nav>
  );
}

export default Navbar;
