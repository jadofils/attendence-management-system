import React from 'react';

const Sidebar: React.FC = () => {
  return (
    <div className="bg-gray-800 text-white h-full w-64 p-4 fixed">
      <h2 className="text-xl font-semibold mb-4">Dashboard</h2>
      <ul className="space-y-4">
        <li className="hover:bg-gray-700 p-2 rounded cursor-pointer">Home</li>
        <li className="hover:bg-gray-700 p-2 rounded cursor-pointer">Analytics</li>
        <li className="hover:bg-gray-700 p-2 rounded cursor-pointer">Settings</li>
        <li className="hover:bg-gray-700 p-2 rounded cursor-pointer">Profile</li>
        <li className="hover:bg-gray-700 p-2 rounded cursor-pointer">Logout</li>
      </ul>
    </div>
  );
};

export default Sidebar;
