import React, { createContext, useState, useEffect, ReactNode } from 'react';

// Define the context type
interface DarkModeContextType {
  darkMode: boolean;
  toggleDarkMode: () => void;
}

export const DarkModeContext = createContext<DarkModeContextType | undefined>(undefined);

interface DarkModeProviderProps {
  children: ReactNode;
}

export const DarkModeProvider: React.FC<DarkModeProviderProps> = ({ children }) => {
  const [darkMode, setDarkMode] = useState<boolean>(false);

  useEffect(() => {
    const savedMode = localStorage.getItem('darkMode');
    if (savedMode === 'true' || (savedMode === null && window.matchMedia('(prefers-color-scheme: dark)').matches)) {
      setDarkMode(true);
      document.body.classList.add('dark'); // Apply the dark mode class on body
    } else {
      document.body.classList.remove('dark'); // Ensure light mode is applied
    }
  }, []);

  // Toggle dark mode and store the state in localStorage
  const toggleDarkMode = () => {
    setDarkMode((prevMode) => {
      const newMode = !prevMode;
      localStorage.setItem('darkMode', newMode.toString());
      if (newMode) {
        document.body.classList.add('dark'); // Apply dark class on body
      } else {
        document.body.classList.remove('dark'); // Remove dark class on body
      }
      return newMode;
    });
  };

  return (
    <DarkModeContext.Provider value={{ darkMode, toggleDarkMode }}>
      {children}
    </DarkModeContext.Provider>
  );
};
