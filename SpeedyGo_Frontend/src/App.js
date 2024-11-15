// App.js
import React, { useState, useEffect } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';
import SplashScreen from './SplashScreen';
import LoginRegistration from './pages/LoginRegistration';
import ForgotPassword from './pages/ForgotPassword';
import RoleBasedRoute from './RoleBasedRoutes';
import UserDashBoard from './pages/Users/UserDashBoard';
import SearchResultPage from './pages/Users/SearchResultPage';
import TransporterProfilePage from './pages/Users/TransporterProfilePage';
import Contact from './pages/Contact';
import ProfileManagementPage from './pages/Transporter/ProfileManagementPage';
import TransporterDashBoard from './pages/Transporter/TransporterDashBoard';
import UserLayout from './pages/Layout/UserLayout';
import TransporterLayout from './pages/Layout/TransporterLayout';
import About from './pages/About';
import Home from './pages/Home';

function App() {
  const [showSplash, setShowSplash] = useState(true);

  useEffect(() => {
    const timer = setTimeout(() => {
      setShowSplash(false);
    }, 4000); 

    return () => clearTimeout(timer);
  }, []);

  if (showSplash) {
    return <SplashScreen />;
  }

  return (
    <BrowserRouter>
      <Routes>
        {/* Public Routes */}
        <Route path="/" element={<LoginRegistration />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />

        {/* User Routes with UserLayout */}
        <Route
          path="/user/*"
          element={
            <RoleBasedRoute allowedRoles={['CUSTOMER']} redirectTo="/">
              <UserLayout />
            </RoleBasedRoute>
          }
        >
          <Route path="home" element={<Home />} />
          <Route path="logistics" element={<SearchResultPage />} />
          <Route path="transporterprofile" element={<TransporterProfilePage />} />
          <Route path="dashboard" element={<UserDashBoard />} />
          <Route path="contact" element={<Contact />} />
          <Route path="about" element={<About />} />
        </Route>

        <Route
          path="/transporter/*"
          element={
            <RoleBasedRoute allowedRoles={['TRANSPORTER']} redirectTo="/">
              <TransporterLayout />
            </RoleBasedRoute>
          }
        >
          <Route path="home" element={<Home />} />
          <Route path="profile-management" element={<ProfileManagementPage />} />
          <Route path="dashboard" element={<TransporterDashBoard />} />
          <Route path="contact" element={<Contact />} />
          <Route path="about" element={<About />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
