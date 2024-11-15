// src/test/UserLayout.test.js
import React from 'react';
import { render, screen } from '@testing-library/react';
import { MemoryRouter, Routes, Route } from 'react-router-dom';
import UserLayout from "../../pages/Layout/UserLayout";
import Header from '../../pages/Layout/Header';
import Footer from '../../pages/Layout/Footer'; // Adjust path as necessary

jest.mock('../../pages/Layout/Header', () => () => <div>Header</div>);
jest.mock('../../pages/Layout/Footer', () => () => <div>Footer</div>);

describe('UserLayout Component', () => {
  test('renders Header and Footer for non-hidden routes', () => {
    render(
      <MemoryRouter initialEntries={['/dashboard']}>
        <Routes>
          <Route path="*" element={<UserLayout />}>
            <Route path="dashboard" element={<div>Dashboard Component</div>} />
          </Route>
        </Routes>
      </MemoryRouter>
    );

    // Check if Header and Footer are rendered
    expect(screen.getByText('Header')).toBeInTheDocument();
    expect(screen.getByText('Footer')).toBeInTheDocument();
    // Check if the child component is rendered
    expect(screen.getByText('Dashboard Component')).toBeInTheDocument();
  });

  test('does not render Header and Footer for hidden routes', () => {
    render(
      <MemoryRouter initialEntries={['/']}>
        <Routes>
          <Route path="*" element={<UserLayout />}>
            <Route path="/" element={<div>Home Component</div>} />
          </Route>
        </Routes>
      </MemoryRouter>
    );

    // Check if Header and Footer are not rendered
    expect(screen.queryByText('Header')).not.toBeInTheDocument();
    expect(screen.queryByText('Footer')).not.toBeInTheDocument();
    // Check if the child component is rendered
    expect(screen.getByText('Home Component')).toBeInTheDocument();
  });

  test('does not render Header and Footer for forgot-password route', () => {
    render(
      <MemoryRouter initialEntries={['/forgot-password']}>
        <Routes>
          <Route path="*" element={<UserLayout />}>
            <Route path="forgot-password" element={<div>Forgot Password Component</div>} />
          </Route>
        </Routes>
      </MemoryRouter>
    );

    // Check if Header and Footer are not rendered
    expect(screen.queryByText('Header')).not.toBeInTheDocument();
    expect(screen.queryByText('Footer')).not.toBeInTheDocument();
    // Check if the child component is rendered
    expect(screen.getByText('Forgot Password Component')).toBeInTheDocument();
  });
});
