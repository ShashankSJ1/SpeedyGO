// src/test/Header.test.js
import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router-dom';
import { configureStore } from 'redux-mock-store';
import Header from '../../pages/Layout/Header.jsx';// Adjust the path as needed
import { HEADER_ROUTES,HEADER_TEXT } from '../../contansts';

const mockStore = configureStore([]);

describe('Header Component', () => {
  let store;

  test('renders header component', () => {
    store = mockStore({
      user: {
        role: 'CUSTOMER', // Default role for testing
      },
    });

    render(
      <Provider store={store}>
        <MemoryRouter>
          <Header />
        </MemoryRouter>
      </Provider>
    );

    expect(screen.getByAltText('Speedy Go Icon')).toBeInTheDocument();
    expect(screen.getByText(HEADER_TEXT.SPEEDY_GO)).toBeInTheDocument();
  });

  test('displays correct links for CUSTOMER role', () => {
    store = mockStore({
      user: {
        role: 'CUSTOMER',
      },
    });

    render(
      <Provider store={store}>
        <MemoryRouter>
          <Header />
        </MemoryRouter>
      </Provider>
    );

    expect(screen.getByText(HEADER_TEXT.HOME)).toBeInTheDocument();
    expect(screen.getByText(HEADER_TEXT.LOGISTICS)).toBeInTheDocument();
    expect(screen.getByText(HEADER_TEXT.ABOUT_US)).toBeInTheDocument();
    expect(screen.getByText(HEADER_TEXT.CONTACT)).toBeInTheDocument();
    expect(screen.getByText(HEADER_TEXT.DASHBOARD)).toBeInTheDocument();
  });

  test('displays correct links for TRANSPORTER role', () => {
    store = mockStore({
      user: {
        role: 'TRANSPORTER',
      },
    });

    render(
      <Provider store={store}>
        <MemoryRouter>
          <Header />
        </MemoryRouter>
      </Provider>
    );

    expect(screen.getByText(HEADER_TEXT.HOME)).toBeInTheDocument();
    expect(screen.getByText(HEADER_TEXT.QUOTATIONS)).toBeInTheDocument();
    expect(screen.getByText(HEADER_TEXT.ABOUT_US)).toBeInTheDocument();
    expect(screen.getByText(HEADER_TEXT.CONTACT)).toBeInTheDocument();
    expect(screen.getByText(HEADER_TEXT.DASHBOARD)).toBeInTheDocument();
  });

  test('toggles menu open/close on click', () => {
    store = mockStore({
      user: {
        role: 'CUSTOMER', // Default role for testing
      },
    });

    render(
      <Provider store={store}>
        <MemoryRouter>
          <Header />
        </MemoryRouter>
      </Provider>
    );

    // Check that menu is closed initially
    expect(screen.queryByText(HEADER_TEXT.HOME)).not.toBeVisible();

    // Click to open the menu
    fireEvent.click(screen.getByRole('button', { name: /menu/i }));

    // Check that menu is now open
    expect(screen.getByText(HEADER_TEXT.HOME)).toBeVisible();

    // Click again to close the menu
    fireEvent.click(screen.getByRole('button', { name: /menu/i }));

    // Check that menu is closed again
    expect(screen.queryByText(HEADER_TEXT.HOME)).not.toBeVisible();
  });
});