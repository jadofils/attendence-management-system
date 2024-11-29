import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/NavBar";
import Footer from "./components/Footer";
import Main from "./components/Main";
import Signup from "./FormData.components/Signup";
import Login from "./FormData.components/Login";
import ForgotPassword from "./FormData.components/ForgotPassword";
import Dashboard from "./dashboard/Dashboard";

const App: React.FC = () => {
  return (
    <Router>
      <div className="bg-secondary text-primary min-h-screen flex flex-col">
        {/* Conditional rendering of Navbar */}
        <Routes>
          {/* Exclude Navbar on Dashboard route */}
          <Route path="/dashboard" element={<Dashboard />} />
          {/* Render Navbar for all other routes */}
          <Route path="/" element={<><Navbar /><Main /></>} />
          <Route path="/signup" element={<><Navbar /><Signup /></>} />
          <Route path="/login" element={<><Navbar /><Login /></>} />
          <Route path="/forgot-password" element={<><Navbar /><ForgotPassword /></>} />
        </Routes>

        {/* Footer is always visible */}
        <Footer />
      </div>
    </Router>
  );
};

export default App;
