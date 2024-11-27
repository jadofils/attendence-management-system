import image from '../assets/react.svg';

function NavBar() {
  return (
    <div className="bg-primary text-secondary">
      <nav className="flex justify-between items-center px-6 py-4">
        {/* Logo Section */}
        <div className="logo flex items-center space-x-3">
          <img src={image} alt="logo" className="h-10 w-10" />
          <span className="text-accent font-semibold text-lg">BrandName</span>
        </div>

        {/* Navigation Links */}
        <div className="navLinks">
          <ul className="flex space-x-6 text-sm font-medium">
            <li>
              <a
                href="#"
                className="text-secondary hover:text-accent transition-colors duration-300"
              >
                About Us
              </a>
            </li>
            <li>
              <a
                href="#"
                className="text-secondary hover:text-accent transition-colors duration-300"
              >
                Our Services
              </a>
            </li>
            <li>
              <a
                href="#"
                className="text-secondary hover:text-accent transition-colors duration-300"
              >
                FAQ's
              </a>
            </li>
            <li>
              <a
                href="#"
                className="text-secondary hover:text-accent transition-colors duration-300"
              >
                Contact Us
              </a>
            </li>
          </ul>
        </div>
      </nav>
    </div>
  );
}

export default NavBar;
