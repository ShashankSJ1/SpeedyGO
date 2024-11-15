import React from 'react';
import { useLocation, Outlet } from 'react-router-dom'; // Import Outlet
import Header from './Header';
import Footer from './Footer';

function UserLayout() {
  const location = useLocation();

  const hideHeaderFooterRoutes = ['/', '/forgot-password'];

  const shouldHideHeaderFooter = hideHeaderFooterRoutes.includes(location.pathname);

  return (
    <div>
      {!shouldHideHeaderFooter && <Header />}
      <main>
        <Outlet /> {/* This will render child routes here */}
      </main>
      {!shouldHideHeaderFooter && <Footer />}
    </div>
  );
}

export default UserLayout;
