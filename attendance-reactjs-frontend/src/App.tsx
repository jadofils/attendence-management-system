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
      
      <Routes>

      <Route path="/dashboard" element={<Dashboard />} />
      </Routes>

        {/* Layout with Navbar and Footer */}
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
      </div>
    </Router>
  );
};

export default App;
