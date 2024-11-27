import React from 'react';
import { FaCalendarCheck, FaEnvelope,FaChartLine, FaBell, FaUsers, FaUserShield, FaSync } from 'react-icons/fa';
const Main: React.FC = () => {
  return (
    <div className="bg-secondary text-primary scroll-smooth">
      {/* Main Wrapper Div */}
      <main className="py-12">
        <div className="container mx-auto px-4">

          {/* Intro Section - Welcome Message */}
          <div className="text-center mb-16">
            <h1 className="text-xl font-semibold text-center mb-8">Welcome to the College Management System</h1>
            <p className="text-base text-gray-700">
              We provide a range of services to help students grow and succeed in their academic journey. Explore our offerings and get the support you need to excel in your college life.
            </p>
          </div>

{/* Section for About */}
<section id="about" className="flex mb-12 px-4">
  <div className="w-full max-w-3xl bg-primary p-6 rounded-lg shadow-lg  mx-auto">
    <h2 className="text-xl font-semibold mb-4 text-gray-800">About Us</h2>
    <p className="text-base text-gray-700">
      Our College Management System aims to streamline your academic experience. From managing coursework to connecting with faculty and accessing resources, we’ve got you covered.
    </p>
  </div>
</section>





          {/* Section for Our Services */}
<section id="services" className="mb-12 px-4">
  <h2 className="text-xl font-semibold mb-6 text-center text-gray-800">Our Services</h2>
  <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-8">
    <div className="text-center p-6 bg-white shadow-lg rounded-lg border border-gray-200">
      <i className="fas fa-clipboard-check text-3xl text-blue-500 mb-4"></i> {/* Course Registration Icon */}
      <h3 className="text-xl font-semibold mb-2">Course Registration</h3>
      <p className="text-base text-gray-700">Easily register for courses and manage your academic schedule.</p>
    </div>
    <div className="text-center p-6 bg-white shadow-lg rounded-lg border border-gray-200">
      <i className="fas fa-calendar-alt text-3xl text-green-500 mb-4"></i> {/* Timetable Management Icon */}
      <h3 className="text-xl font-semibold mb-2">Student Timetable Management</h3>
      <p className="text-base text-gray-700">View and organize your timetable for smooth academic planning.</p>
    </div>
    <div className="text-center p-6 bg-white shadow-lg rounded-lg border border-gray-200">
      <i className="fas fa-chart-line text-3xl text-yellow-500 mb-4"></i> {/* Grade Tracking Icon */}
      <h3 className="text-xl font-semibold mb-2">Grade Tracking</h3>
      <p className="text-base text-gray-700">Keep track of your academic performance and grades.</p>
    </div>
    <div className="text-center p-6 bg-white shadow-lg rounded-lg border border-gray-200">
      <i className="fas fa-book text-3xl text-purple-500 mb-4"></i> {/* Student Resources Icon */}
      <h3 className="text-xl font-semibold mb-2">Student Resources</h3>
      <p className="text-base text-gray-700">Access study materials, library resources, and more.</p>
    </div>
  </div>
</section>

<div>


<section id="key-features" className="mb-16">
      <h2 className="text-xl font-semibold text-center mb-8 ">Our Key Features</h2>
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-3 gap-8">

        {/* Attendance Tracking */}
        <div className="text-center ">
          <div className="mb-4 text-blue-600">
            <FaCalendarCheck className="text-4xl" />
          </div>
          <h3 className="text-xl font-semibold mb-2">Attendance Tracking</h3>
          <p className="text-base text-gray-700">
            Track student attendance effortlessly with automated records for each class or session.
          </p>
        </div>

        {/* Reports & Analytics */}
        <div className="text-center">
          <div className="mb-4 text-blue-600">
            <FaChartLine className="text-4xl" />
          </div>
          <h3 className="text-xl font-semibold mb-2">Reports & Analytics</h3>
          <p className="text-base text-gray-700">
            Generate detailed attendance reports and analyze trends over any period of time.
          </p>
        </div>

        {/* Notifications */}
        <div className="text-center">
          <div className="mb-4 text-blue-600">
            <FaBell className="text-4xl" />
          </div>
          <h3 className="text-xl font-semibold mb-2">Notifications</h3>
          <p className="text-base text-gray-700">
            Send instant notifications to students and staff about attendance updates or alerts.
          </p>
        </div>

        {/* Student Management */}
        <div className="text-center">
          <div className="mb-4 text-blue-600">
            <FaUsers className="text-4xl" />
          </div>
          <h3 className="text-xl font-semibold mb-2">Student Management</h3>
          <p className="text-base text-gray-700">
            Easily manage student data, including attendance, grades, and personal information.
          </p>
        </div>

        {/* Role-based Access */}
        <div className="text-center">
          <div className="mb-4 text-blue-600">
            <FaUserShield className="text-4xl" />
          </div>
          <h3 className="text-xl font-semibold mb-2">Role-based Access</h3>
          <p className="text-base text-gray-700">
            Manage different user roles (admin, teacher, student) with specific permissions and access.
          </p>
        </div>

        {/* Real-time Sync */}
        <div className="text-center">
          <div className="mb-4 text-blue-600">
            <FaSync className="text-4xl" />
          </div>
          <h3 className="text-xl font-semibold mb-2">Real-time Sync</h3>
          <p className="text-base text-gray-700">
            Sync attendance data in real-time across all devices for accurate and up-to-date records.
          </p>
        </div>

      </div>
    </section>
</div>
  
{/* Section for Get in Touch */}
<section id="get-in-touch" className="flex mb-12 px-4">
  <div className="w-full max-w-3xl bg-secondary p-6 rounded-lg shadow-lg  box-border mx-auto">
    <h2 className="text-xl font-semibold mb-4 text-gray-800">Get in Touch</h2>
    <p className="text-base text-gray-700">
      Ready to take the next step? Reach out to us today, and let’s make your college journey even more successful!
    </p>


    <div className="mt-4 flex items-center space-x-2">
  <FaEnvelope size={24} className="text-blue-600" /> {/* Email Icon */}
  
  <a href="mailto:contact@college.edu" className="text-blue-600 text-base hover:text-blue-800">
    Send us an Email
  </a>
</div>

  </div>
</section>


        </div>
      </main>
    </div>
  );
}

export default Main;
