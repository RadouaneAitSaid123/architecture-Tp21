import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import ReservationList from './components/ReservationList';
import ReservationForm from './components/ReservationForm';

function App() {
  return (
    <BrowserRouter>
      <div style={{ minHeight: '100vh', paddingBottom: '2rem' }}>
        <Navbar />
        <Routes>
          <Route path="/" element={<ReservationList />} />
          <Route path="/create" element={<ReservationForm />} />
          <Route path="/edit/:id" element={<ReservationForm />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
