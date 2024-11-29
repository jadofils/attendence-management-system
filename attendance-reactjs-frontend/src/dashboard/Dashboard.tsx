import React from 'react';
import Sidebar from './Sidebar';
import DashboardMain from './DashboardMain';
import DashboardNavbar from './DashboardNavBar';

const Dashboard: React.FC = () => {
  return (
    <div className="flex">
      <Sidebar />
      <DashboardNavbar />

      <div className="flex-grow">
        <DashboardMain />
      </div>
    </div>
  );
};

export default Dashboard;
