import React from 'react';

const DashboardMain: React.FC = () => {
  return (
    <div className="flex-1 ml-16 md:ml-64 mt-16 p-6">
      <h2 className="text-2xl font-semibold mb-4">Welcome to the Dashboard</h2>
      <p className="text-gray-700">
        Here you can manage your analytics, settings, and more!
      </p>
      {/* Add more sections or components here */}
    </div>
  );
};

export default DashboardMain;
