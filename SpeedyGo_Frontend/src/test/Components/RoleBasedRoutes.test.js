import React from 'react';
import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { createStore } from 'redux';
import { MemoryRouter, Route, Routes } from 'react-router-dom';
import RoleBasedRoute from '../../RoleBasedRoutes';

// Mock Redux store
const mockStore = (role) => createStore(() => ({ user: { role } }));

describe('RoleBasedRoute', () => {
  it('renders children when the user has an allowed role', () => {
    const store = mockStore('admin');
    const { getByText } = render(
      <Provider store={store}>
        <MemoryRouter initialEntries={['/']}>
          <Routes>
            <Route
              path="/"
              element={
                <RoleBasedRoute allowedRoles={['admin']} redirectTo="/login">
                  <div>Admin Content</div>
                </RoleBasedRoute>
              }
            />
          </Routes>
        </MemoryRouter>
      </Provider>
    );

    expect(getByText('Admin Content')).toBeInTheDocument();
  });

  it('redirects when the user does not have an allowed role', () => {
    const store = mockStore('user');
    const { queryByText } = render(
      <Provider store={store}>
        <MemoryRouter initialEntries={['/']}>
          <Routes>
            <Route
              path="/"
              element={
                <RoleBasedRoute allowedRoles={['admin']} redirectTo="/login">
                  <div>Admin Content</div>
                </RoleBasedRoute>
              }
            />
            <Route path="/login" element={<div>Login Page</div>} />
          </Routes>
        </MemoryRouter>
      </Provider>
    );

    expect(queryByText('Admin Content')).not.toBeInTheDocument();
    expect(queryByText('Login Page')).toBeInTheDocument();
  });

  it('handles missing role gracefully', () => {
    const store = mockStore(null);
    const { queryByText } = render(
      <Provider store={store}>
        <MemoryRouter initialEntries={['/']}>
          <Routes>
            <Route
              path="/"
              element={
                <RoleBasedRoute allowedRoles={['admin']} redirectTo="/login">
                  <div>Admin Content</div>
                </RoleBasedRoute>
              }
            />
            <Route path="/login" element={<div>Login Page</div>} />
          </Routes>
        </MemoryRouter>
      </Provider>
    );

    expect(queryByText('Admin Content')).not.toBeInTheDocument();
    expect(queryByText('Login Page')).toBeInTheDocument();
  });
});
