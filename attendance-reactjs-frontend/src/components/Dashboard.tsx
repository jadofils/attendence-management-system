import React, { useContext } from "react";
import { DarkModeContext } from "../context/DarkModeContext";

const Dashboard: React.FC = () => {
  const { darkMode } = useContext(DarkModeContext)!; // Access dark mode state

  return (
    <div className={`dashboard ${darkMode ? 'dark' : ''}`}>
      <h1 className="text-4xl">Welcome to the Dashboard</h1>
      <p className="text-lg">Manage your settings, users, and more.</p>
      {/* Your dashboard content */}
    </div>
  );
};

export default Dashboard;
