import React from 'react';
import { render } from '@testing-library/react';
import { MemoryRouter, Route, Routes } from 'react-router-dom';
import TransporterLayout from '../../pages/Layout/TransporterLayout';

// Utility to render with a given pathname
const renderWithRouter = (ui, { route = '/' } = {}) => {
  window.history.pushState({}, 'Test page', route);
  return render(ui, { wrapper: MemoryRouter });
};

describe('TransporterLayout', () => {
  it('should render Header and Footer on the home route', () => {
    const { getByText } = renderWithRouter(
      <TransporterLayout>
        <div>Home Page Content</div>
      </TransporterLayout>
    );

    expect(getByText('Home Page Content')).toBeInTheDocument();
    expect(getByText('Header')).toBeInTheDocument();
    expect(getByText('Footer')).toBeInTheDocument();
  });

  it('should render Header and Footer on another route', () => {
    const { getByText } = renderWithRouter(
      <TransporterLayout>
        <div>Other Page Content</div>
      </TransporterLayout>,
      { route: '/some-other-route' }
    );

    expect(getByText('Other Page Content')).toBeInTheDocument();
    expect(getByText('Header')).toBeInTheDocument();
    expect(getByText('Footer')).toBeInTheDocument();
  });

  it('should not render Header and Footer on the forgot password route', () => {
    const { queryByText } = renderWithRouter(
      <TransporterLayout>
        <div>Forgot Password Content</div>
      </TransporterLayout>,
      { route: '/forgot-password' }
    );

    expect(queryByText('Forgot Password Content')).toBeInTheDocument();
    expect(queryByText('Header')).not.toBeInTheDocument();
    expect(queryByText('Footer')).not.toBeInTheDocument();
  });

  it('should render children inside Outlet', () => {
    const { getByText } = renderWithRouter(
      <TransporterLayout>
        <div>Some Child Content</div>
      </TransporterLayout>,
      { route: '/some-route' }
    );

    expect(getByText('Some Child Content')).toBeInTheDocument();
  });

  it('should not render Header and Footer on the home route', () => {
    const { queryByText } = renderWithRouter(
      <TransporterLayout>
        <div>Home Content</div>
      </TransporterLayout>,
      { route: '/' }
    );

    expect(queryByText('Home Content')).toBeInTheDocument();
    expect(queryByText('Header')).not.toBeInTheDocument();
    expect(queryByText('Footer')).not.toBeInTheDocument();
  });
});
