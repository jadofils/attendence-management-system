import React from 'react';
import Sidebar from './Sidebar';
import DashboardNavbar from './DashboardNavBar';
import DashboardMain from './DashboardMain';

const Dashboard: React.FC = () => {
  return (
    <div className="flex">
      <Sidebar />
      <DashboardNavbar />
   <DashboardMain/>
    </div>
  );
};

export default Dashboard;
