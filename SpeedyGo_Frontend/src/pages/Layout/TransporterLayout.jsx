import React from 'react';
import { Outlet, useLocation } from 'react-router-dom';
import Header from './Header';
import Footer from './Footer';
function TransporterLayout({ children }) {
  const location = useLocation();

  const hideHeaderFooterRoutes = ['/', '/forgot-password'];

  const shouldHideHeaderFooter = hideHeaderFooterRoutes.includes(location.pathname);

  return (
    <div>
      {!shouldHideHeaderFooter && <Header/>}
      <main><Outlet/></main>
      {!shouldHideHeaderFooter && <Footer />}
    </div>
  );
}

export default TransporterLayout;
