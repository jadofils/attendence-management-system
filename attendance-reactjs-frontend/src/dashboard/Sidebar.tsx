import React, { useState } from "react";
import {
  FaChartBar,
  FaCog,
  FaUser,
  FaSignOutAlt,
  FaBars,
  FaTimes,
  FaClipboardList,
  FaBookOpen,
  FaUsers,
} from "react-icons/fa";
import logo from '../assets/logo.jpeg'
const Sidebar: React.FC = () => {
  const [isOpen, setIsOpen] = useState(true);

  const toggleSidebar = () => {
    setIsOpen(!isOpen);
  };

  return (
    <div className="flex">
      {/* Sidebar */}
      <div
        className={`bg-secondary text-black  relative top-24 rounded left-4 mt- ${
          isOpen ? "w-32" : "w-16"
        } transition-all duration-300  z-20  `}
      >
        {/* Profile Section */}
        <div className="flex items-center p-4 border-b border-gray-700">
          <div className="relative overflow-hidden rounded-full w-12 h-12">
            <img
              src={logo} // Replace with actual profile picture URL
              alt="Profile"
              className="object-cover"
            />
          </div>
          {isOpen && (
            <div className="ml-3">
              <p className="text-lg font-semibold">John Doe</p>
              <p className="text-sm text-gray-500">Admin</p>
            </div>
          )}
        </div>

        {/* Navigation */}
        <ul className="mt-4 space-y-4 text-base">
          <li className="flex items-center space-x-3 hover:bg-gray-700 hover:text-white p-2 rounded cursor-pointer">
            <FaUser className="text-blue-600" />
            {isOpen && <span>Users</span>}
          </li>
          <li className="flex items-center space-x-3 hover:bg-gray-700 hover:text-white p-2 rounded cursor-pointer">
            <FaChartBar className="text-blue-600" />
            {isOpen && <span>Programs</span>}
          </li>
          <li className="flex items-center space-x-3 hover:bg-gray-700 hover:text-white p-2 rounded cursor-pointer">
            <FaUsers className="text-blue-600" />
            {isOpen && <span>Students</span>}
          </li>
          <li className="flex items-center space-x-3 hover:bg-gray-700 hover:text-white p-2 rounded cursor-pointer">
            <FaBookOpen className="text-blue-600" />
            {isOpen && <span>Courses</span>}
          </li>
          <li className="flex items-center space-x-3 hover:bg-gray-700 hover:text-white p-2 rounded cursor-pointer">
            <FaClipboardList className="text-blue-600" />
            {isOpen && <span>Classes</span>}
          </li>
          <li className="flex items-center space-x-3 hover:bg-gray-700 hover:text-white p-2 rounded cursor-pointer">
            <FaClipboardList className="text-blue-600" />
            {isOpen && <span>Attendance</span>}
          </li>
          <li className="flex items-center space-x-3 hover:bg-gray-700 hover:text-white p-2 rounded cursor-pointer">
            <FaChartBar className="text-blue-600" />
            {isOpen && <span>Statistics</span>}
          </li>
          <li className="flex items-center space-x-3 hover:bg-gray-700 hover:text-white p-2 rounded cursor-pointer">
            <FaCog className="text-blue-600" />
            {isOpen && <span>Settings</span>}
          </li>
          <li className="flex items-center space-x-3 hover:bg-gray-700 hover:text-white p-2 rounded cursor-pointer">
            <FaSignOutAlt className="text-blue-600" />
            {isOpen && <span>Logout</span>}
          </li>
        </ul>
      </div>

      {/* Toggle Button */}
      <button
        onClick={toggleSidebar}
        className="fixed top-4 left-4 z-30 bg-gray-700 text-white p-2 rounded-full md:hidden"
      >
        {isOpen ? <FaTimes /> : <FaBars />}
      </button>
    </div>
  );
};

export default Sidebar;
