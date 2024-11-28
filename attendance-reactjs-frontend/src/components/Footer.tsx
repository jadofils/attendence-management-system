import React from 'react';
import { FaEnvelope, FaTwitter, FaFacebook, FaInstagram } from 'react-icons/fa';

const Footer: React.FC = () => {
  return (
    <footer className="bg-secondary text-primary py-6 sticky bottom-0 left-0 right-0 mt-[80vh]">
      <div className="container mx-auto">
        {/* Communication Channels */}
        <div className="mb-4 text-center">
          <h3 className="text-xl font-semibold">Connect With Us</h3>
          <div className="flex justify-center items-center space-x-6 mt-2">
            <a href="mailto:contact@example.com" className="text-blue-600 hover:text-blue-800">
              <FaEnvelope size={24} />
            </a>
            <a href="https://twitter.com" target="_blank" rel="noopener noreferrer" className="text-blue-600 hover:text-blue-800">
              <FaTwitter size={24} />
            </a>
            <a href="https://facebook.com" target="_blank" rel="noopener noreferrer" className="text-blue-600 hover:text-blue-800">
              <FaFacebook size={24} />
            </a>
            <a href="https://instagram.com" target="_blank" rel="noopener noreferrer" className="text-blue-600 hover:text-blue-800">
              <FaInstagram size={24} />
            </a>
          </div>
        </div>
        {/* Other Information */}
        <div className="text-center">
          <p className="text-sm mt-4">© 2024 Your Company. All rights reserved.</p>
          <p className="text-xs mt-2">Privacy Policy | Terms of Service</p>
        </div>
      </div>
    </footer>
  );
};

export default Footer;