// Main.tsx
import React from 'react';

const Main: React.FC = () => {
  return (
    <div className="bg-secondary text-primary scroll-smooth">
      {/* Main Wrapper Div */}
      <main className="py-12">
        <div className="container mx-auto px-4">
          
          {/* Intro Section - Talking about our Website */}
          <div className="text-center mb-16">
            <h1 className="text-4xl font-bold mb-4">Welcome to Our Website</h1>
            <p className="text-lg text-gray-700">
              We provide top-notch services to help you grow and succeed. Explore what we offer and get in touch with us.
            </p>
            <div className="mt-6">
              <a href="#about" className="text-blue-600 hover:text-blue-800">Learn More</a>
            </div>
          </div>

          {/* Section for About */}
          <section id="about" className="mb-12">
            <h2 className="text-3xl font-semibold mb-4">About Us</h2>
            <p className="text-lg text-gray-700">
              We are a leading company specializing in providing outstanding solutions to our clients. Our mission is to help businesses achieve success through innovative strategies and dedicated services.
            </p>
          </section>

          {/* Section for Our Services */}
          <section id="services" className="mb-12">
            <h2 className="text-3xl font-semibold mb-4">Our Services</h2>
            <ul className="space-y-4 text-lg text-gray-700">
              <li>Web Development</li>
              <li>Mobile App Development</li>
              <li>UI/UX Design</li>
              <li>Digital Marketing</li>
            </ul>
          </section>

          {/* Section for Contact Us */}
          <section id="contact" className="mb-12">
            <h2 className="text-3xl font-semibold mb-4">Contact Us</h2>
            <p className="text-lg text-gray-700">
              Have any questions? Reach out to us through our email or phone. We're here to help!
            </p>
            <div className="mt-4">
              <p>Email: contact@example.com</p>
              <p>Phone: +1 234 567 890</p>
            </div>
          </section>

          {/* Section for FAQ */}
          <section id="faq" className="mb-12">
            <h2 className="text-3xl font-semibold mb-4">Frequently Asked Questions</h2>
            <div className="space-y-6 text-lg text-gray-700">
              <div>
                <h3 className="font-semibold">What services do you offer?</h3>
                <p>We offer web and mobile app development, UI/UX design, and digital marketing services.</p>
              </div>
              <div>
                <h3 className="font-semibold">How can I contact you?</h3>
                <p>You can contact us via email at contact@example.com or by calling +1 234 567 890.</p>
              </div>
              <div>
                <h3 className="font-semibold">Do you offer custom solutions?</h3>
                <p>Yes, we offer custom solutions tailored to your business needs. Get in touch with us for more details.</p>
              </div>
            </div>
          </section>

          {/* Section for Get in Touch */}
          <section id="get-in-touch" className="mb-12">
            <h2 className="text-3xl font-semibold mb-4">Get in Touch</h2>
            <p className="text-lg text-gray-700">
              Ready to take the next step? Reach out to us today, and let's discuss how we can work together.
            </p>
            <div className="mt-4">
              <a href="mailto:contact@example.com" className="text-blue-600 hover:text-blue-800">Send us an Email</a>
            </div>
          </section>

        </div>
      </main>
    </div>
  );
}

export default Main;
