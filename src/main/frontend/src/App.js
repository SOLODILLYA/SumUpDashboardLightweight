import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Dashboard from './components/Dashboard';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/sumup/dashboard" element={<Dashboard />} />
      </Routes>
    </Router>
  );
}

export default App;
