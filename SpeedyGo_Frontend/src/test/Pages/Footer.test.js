import React from 'react';
import { render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import { BrowserRouter as Router } from 'react-router-dom';
import configureStore from 'redux-mock-store';
import Footer from '../../pages/Layout/Footer.jsx';
import { FOOTER_TEXT, FOOTER_LINKS } from '../../constants';

const mockStore = configureStore([]);

describe('Footer Component', () => {
  let store;

  beforeEach(() => {
    store = mockStore({
      user: {
        role: 'CUSTOMER',
      },
    });
  });

  test('renders footer logo and description', () => {
    render(
      <Provider store={store}>
        <Router>
          <Footer />
        </Router>
      </Provider>
    );

    const logo = screen.getByText(FOOTER_TEXT.LOGO);
    expect(logo).toBeInTheDocument();

    const description = screen.getByText(FOOTER_TEXT.DESCRIPTION);
    expect(description).toBeInTheDocument();
  });

  test('renders social media icons', () => {
    render(
      <Provider store={store}>
        <Router>
          <Footer />
        </Router>
      </Provider>
    );

    const facebookIcon = screen.getByText(FOOTER_TEXT.FACEBOOK);
    const twitterIcon = screen.getByText(FOOTER_TEXT.TWITTER);
    const instagramIcon = screen.getByText(FOOTER_TEXT.INSTAGRAM);
    const linkedinIcon = screen.getByText(FOOTER_TEXT.LINKEDIN);

    expect(facebookIcon).toBeInTheDocument();
    expect(twitterIcon).toBeInTheDocument();
    expect(instagramIcon).toBeInTheDocument();
    expect(linkedinIcon).toBeInTheDocument();
  });

  test('renders quick links for customer', () => {
    render(
      <Provider store={store}>
        <Router>
          <Footer />
        </Router>
      </Provider>
    );

    const logisticsLink = screen.getByText('LOGISTICS');
    expect(logisticsLink).toBeInTheDocument();
    expect(logisticsLink).toHaveAttribute('href', FOOTER_LINKS.LOGISTICS);

    const contactLink = screen.getByText('Contact Us');
    expect(contactLink).toBeInTheDocument();
    expect(contactLink).toHaveAttribute('href', FOOTER_LINKS.CONTACT);

    const aboutLink = screen.getByText('About Us');
    expect(aboutLink).toBeInTheDocument();
    expect(aboutLink).toHaveAttribute('href', FOOTER_LINKS.ABOUT);

    const dashboardLink = screen.getByText('Dashboard');
    expect(dashboardLink).toBeInTheDocument();
    expect(dashboardLink).toHaveAttribute('href', FOOTER_LINKS.DASHBOARD);
  });

  test('renders quick links for transporter', () => {
    store = mockStore({
      user: {
        role: 'TRANSPORTER',
      },
    });

    render(
      <Provider store={store}>
        <Router>
          <Footer />
        </Router>
      </Provider>
    );

    const quotationsLink = screen.getByText('QUOTATIONS');
    expect(quotationsLink).toBeInTheDocument();
    expect(quotationsLink).toHaveAttribute('href', FOOTER_LINKS.QUOTATIONS);

    const contactLink = screen.getByText('Contact Us');
    expect(contactLink).toBeInTheDocument();
    expect(contactLink).toHaveAttribute('href', FOOTER_LINKS.CONTACT);

    const aboutLink = screen.getByText('About Us');
    expect(aboutLink).toBeInTheDocument();
    expect(aboutLink).toHaveAttribute('href', FOOTER_LINKS.ABOUT);

    const dashboardLink = screen.getByText('Dashboard');
    expect(dashboardLink).toBeInTheDocument();
    expect(dashboardLink).toHaveAttribute('href', FOOTER_LINKS.DASHBOARD);
  });

  test('renders contact information', () => {
    render(
      <Provider store={store}>
        <Router>
          <Footer />
        </Router>
      </Provider>
    );

    const phone = screen.getByText(FOOTER_TEXT.PHONE);
    expect(phone).toBeInTheDocument();

    const email = screen.getByText(FOOTER_TEXT.EMAIL);
    expect(email).toBeInTheDocument();

    const address = screen.getByText(FOOTER_TEXT.ADDRESS);
    expect(address).toBeInTheDocument();
  });

  test('scrolls to top on route change', () => {
    window.scrollTo = jest.fn();

    render(
      <Provider store={store}>
        <Router>
          <Footer />
        </Router>
      </Provider>
    );

    expect(window.scrollTo).toHaveBeenCalledWith(0, 0);
  });
});
