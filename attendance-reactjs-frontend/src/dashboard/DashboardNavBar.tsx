import React from 'react';

const DashboardNavbar: React.FC = () => {
  return (
    <nav className="bg-gray-100 px-6 py-4 shadow-md flex justify-between items-center fixed w-full z-10 ml-64">
      <h1 className="text-lg font-bold">Dashboard</h1>
      <div className="flex items-center space-x-4">
        <span className="text-gray-700">Welcome, User!</span>
        <img
          src="https://via.placeholder.com/40"
          alt="User Avatar"
          className="rounded-full w-10 h-10"
        />
      </div>
    </nav>
  );
};

export default DashboardNavbar;
