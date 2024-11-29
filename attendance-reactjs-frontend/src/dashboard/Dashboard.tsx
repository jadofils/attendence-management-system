import React from 'react';
import Sidebar from './Sidebar';
import DashboardMain from './DashboardMain';
import DashboardNavbar from './DashboardNavBar';

const Dashboard: React.FC = () => {
  return (
    <div className="flex">
      <Sidebar />
      <div className="flex-grow">
        <DashboardNavbar />
        <DashboardMain />
      </div>
    </div>
  );
};

export default Dashboard;
