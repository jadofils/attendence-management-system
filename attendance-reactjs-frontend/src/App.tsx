// App.tsx
import React from 'react';
import Navbar from './components/NavBar';
import Footer from './components/Footer';
import Main from './components/Main';

const App: React.FC = () => {
  return (
    <div className="bg-secondary text-primary">
      <Navbar />
      
      <main className="p-6">
        <h1 className="text-2xl font-bold">Welcome to My App</h1>
        <p>This is where the main content goes.</p>
        <Main/>
      </main>

      <Footer />
    </div>
  );
}

export default App;
