/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './src/**/*.{js,jsx,ts,tsx}', // Ensure Tailwind applies styles to your React components
  ],
  theme: {
    extend: {
      scrollBehavior: {
        smooth: 'smooth',
      },
      colors: {
        secondary: '#D3D3D3', // Light grey for background (Primary)
        primary: '#FFFFFF', // White for navbar, footer, dashboard, and cards (Secondary)
        button: '#1E40AF', // Strong blue for buttons (can be adjusted to a different shade if needed)
      },
      fontSize: {
        base: '15px', // Set the default font size to 14px
      },
      textColor: {
        primary: '#000000', // Black text color for primary color
        secondary: '#000000', // Black text color for secondary color
        button: '#FFFFFF', // White text color for buttons for better contrast
      },
    },
  },
  plugins: [],
}
