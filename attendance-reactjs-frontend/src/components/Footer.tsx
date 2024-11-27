// Footer.tsx
import React from 'react';
import { FaEnvelope, FaTwitter, FaFacebook, FaInstagram } from 'react-icons/fa'; // Import icons

const Footer: React.FC = () => {
  return (
    <footer className="bg-secondary text-primary py-6 mt-10 sticky bottom-0 z-50">
      <div className="container mx-auto text-center">
        {/* Communication Channels */}
        <div className="mb-4">
          <h3 className="text-xl font-semibold">Connect With Us</h3>
          <ul className="flex justify-center space-x-6 mt-2">
            <li>
              <a href="mailto:contact@example.com" className="text-blue-600 hover:text-blue-800">
                <FaEnvelope size={24} /> {/* Email Icon */}
              </a>
            </li>
            <li>
              <a href="https://twitter.com" target="_blank" rel="noopener noreferrer" className="text-blue-600 hover:text-blue-800">
                <FaTwitter size={24} /> {/* Twitter Icon */}
              </a>
            </li>
            <li>
              <a href="https://facebook.com" target="_blank" rel="noopener noreferrer" className="text-blue-600 hover:text-blue-800">
                <FaFacebook size={24} /> {/* Facebook Icon */}
              </a>
            </li>
            <li>
              <a href="https://instagram.com" target="_blank" rel="noopener noreferrer" className="text-blue-600 hover:text-blue-800">
                <FaInstagram size={24} /> {/* Instagram Icon */}
              </a>
            </li>
          </ul>
        </div>

        {/* Other Information */}
        <div>
          <p className="text-sm mt-4">© 2024 Your Company. All rights reserved.</p>
          <p className="text-xs mt-2">Privacy Policy | Terms of Service</p>
        </div>
      </div>
    </footer>
  );
}

export default Footer;
