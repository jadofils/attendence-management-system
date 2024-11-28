import React from "react";
import {
  FaCalendarCheck,
  FaEnvelope,
  FaChartLine,
  FaBell,
  FaUsers,
  FaUserShield,
  FaSync,
} from "react-icons/fa";
import ContactForm from "./ContactForm";

const Main: React.FC = () => {
  const services = [
    {
      icon: <i className="fas fa-clipboard-check text-3xl text-blue-500 mb-4"></i>,
      title: "Course Registration",
      description: "Easily register for courses and manage your academic schedule."
    },
    {
      icon: <i className="fas fa-calendar-alt text-3xl text-green-500 mb-4"></i>,
      title: "Student Timetable Management",
      description: "View and organize your timetable for smooth academic planning."
    },
    {
      icon: <i className="fas fa-chart-line text-3xl text-yellow-500 mb-4"></i>,
      title: "Grade Tracking",
      description: "Keep track of your academic performance and grades."
    },
    {
      icon: <i className="fas fa-book text-3xl text-purple-500 mb-4"></i>,
      title: "Student Resources",
      description: "Access study materials, library resources, and more."
    }
  ];

  const keyFeatures = [
    {
      icon: <FaCalendarCheck className="text-4xl" />,
      title: "Attendance Tracking",
      description: "Track student attendance effortlessly with automated records for each class or session."
    },
    {
      icon: <FaChartLine className="text-4xl" />,
      title: "Reports & Analytics",
      description: "Generate detailed attendance reports and analyze trends over any period of time."
    },
    {
      icon: <FaBell className="text-4xl" />,
      title: "Notifications",
      description: "Send instant notifications to students and staff about attendance updates or alerts."
    },
    {
      icon: <FaUsers className="text-4xl" />,
      title: "Student Management",
      description: "Easily manage student data, including attendance, grades, and personal information."
    },
    {
      icon: <FaUserShield className="text-4xl" />,
      title: "Role-based Access",
      description: "Manage different user roles (admin, teacher, student) with specific permissions and access."
    },
    {
      icon: <FaSync className="text-4xl" />,
      title: "Real-time Sync",
      description: "Sync attendance data in real-time across all devices for accurate and up-to-date records."
    }
  ];

  return (
    <div className="bg-secondary text-primary scroll-smooth">
      <main className="py-8 md:py-12">
        <div className="container mx-auto px-4">
          {/* Intro Section - Welcome Message */}
          <div className="text-center mb-12 md:mb-16">
            <h1 className="text-lg md:text-xl font-semibold mb-4 md:mb-8">
              Welcome to the College Management System
            </h1>
            <p className="text-sm md:text-base text-gray-700 max-w-2xl mx-auto">
              We provide a range of services to help students grow and succeed in their academic journey. 
              Explore our offerings and get the support you need to excel in your college life.
            </p>
          </div>

          {/* About Section */}
          <section id="about" className="mb-12 px-4">
            <div className="w-full max-w-3xl bg-primary p-4 md:p-6 rounded-lg shadow-lg mx-auto">
              <h2 className="text-lg md:text-xl font-semibold mb-3 md:mb-4 text-gray-800">
                About Us
              </h2>
              <p className="text-sm md:text-base text-gray-700">
                Our College Management System aims to streamline your academic experience. 
                From managing coursework to connecting with faculty and accessing resources, we've got you covered.
              </p>
            </div>
          </section>

          {/* Services Section */}
          <section id="services" className="mb-12 px-4">
            <h2 className="text-lg md:text-xl font-semibold mb-6 text-center text-gray-800">
              Our Services
            </h2>
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 md:gap-8">
              {services.map((service, index) => (
                <div key={index} className="text-center p-4 md:p-6 bg-white shadow-lg rounded-lg border border-gray-200">
                  {service.icon}
                  <h3 className="text-base md:text-xl font-semibold mb-2">
                    {service.title}
                  </h3>
                  <p className="text-xs md:text-sm text-gray-700">
                    {service.description}
                  </p>
                </div>
              ))}
            </div>
          </section>

          {/* Key Features Section */}
          <section id="key-features" className="mb-12 md:mb-16">
            <h2 className="text-lg md:text-xl font-semibold text-center mb-6 md:mb-8">
              Our Key Features
            </h2>
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 md:gap-8">
              {keyFeatures.map((feature, index) => (
                <div key={index} className="text-center p-4 md:p-6">
                  <div className="mb-3 md:mb-4 text-blue-600 flex justify-center">
                    {feature.icon}
                  </div>
                  <h3 className="text-base md:text-xl font-semibold mb-2">
                    {feature.title}
                  </h3>
                  <p className="text-xs md:text-sm text-gray-700">
                    {feature.description}
                  </p>
                </div>
              ))}
            </div>
          </section>

          {/* Get in Touch Section */}
          <section id="get-in-touch" className="mb-12 px-4">
            <div className="w-full max-w-3xl bg-secondary p-4 md:p-6 rounded-lg shadow-lg mx-auto">
              <h2 className="text-lg md:text-xl font-semibold mb-3 md:mb-4 text-gray-800">
                Get in Touch
              </h2>
              <p className="text-sm md:text-base text-gray-700 mb-4">
                Ready to take the next step? Reach out to us today, and let's make your college journey even more successful!
              </p>

              <div className="flex items-center space-x-2">
                <FaEnvelope size={20} className="text-blue-600" />
                <a
                  href="mailto:contact@college.edu"
                  className="text-sm md:text-base text-blue-600 hover:text-blue-800"
                >
                  Send us an Email
                </a>
              </div>
            </div>
          </section>
        </div>
      </main>
      <ContactForm  />
    </div>
  );
};

export default Main;