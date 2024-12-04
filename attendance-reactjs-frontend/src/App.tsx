 
import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/NavBar";
import Footer from "./components/Footer";
import Main from "./components/Main";
import Signup from "./FormData.components/Signup";
import Login from "./FormData.components/Login";
import ForgotPassword from "./FormData.components/ForgotPassword";
import Dashboard from "./dashboard/Dashboard";
import Users from "./dashboard/Users";

const App: React.FC = () => {
  return (
    <Router>
      <div className="bg-secondary text-primary min-h-screen flex flex-col">
        {/* Main Layout with Navbar and Footer */}
        <Routes>
          {/* For /dashboard, do not show Navbar or Footer */}
          <Route path="/users" element={<Users />} />
          <Route path="/dashboard/*" element={<DashboardWithoutNavbarFooter />} />

          {/* Layout with Navbar and Footer for other routes */}
          <Route path="/" element={<Layout />}>
            <Route path="/signup" element={<Signup />} />
            <Route path="/login" element={<Login />} />
            <Route path="/forgot-password" element={<ForgotPassword />} />
            <Route path="/" element={<Main />} />
          </Route>
        </Routes>
      </div>
    </Router>
  );
};

// Layout Component for Routes excluding /dashboard
const Layout: React.FC = () => {
  return (
    <>
      <Navbar />
      <main className="flex-grow p-6">
        <Routes>
          <Route path="/" element={<Main />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/login" element={<Login />} />
          <Route path="/forgot-password" element={<ForgotPassword />} />
        </Routes>
      </main>
      <Footer />
    </>
  );
};

// Component for /dashboard route without Navbar and Footer
const DashboardWithoutNavbarFooter: React.FC = () => {
  return <Dashboard />;
};

export default App;
