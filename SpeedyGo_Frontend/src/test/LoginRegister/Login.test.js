import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import Login from './Login';
import { ToastContainer } from 'react-toastify';
import axios from 'axios';
import Cookies from 'js-cookie';
import { setUser } from '../../Redux/Reducer';
import { useNavigate } from 'react-router-dom';

jest.mock('axios');
jest.mock('js-cookie');
jest.mock('react-toastify');
jest.mock('react-router-dom', () => ({
  useNavigate: jest.fn(),
}));

const mockStore = configureStore([]);
const store = mockStore({});

describe('Login Component', () => {
  let navigate;

  beforeEach(() => {
    navigate = jest.fn();
    useNavigate.mockReturnValue(navigate);
    render(
      <Provider store={store}>
        <Login toggleSignUp={jest.fn()} />
        <ToastContainer />
      </Provider>
    );
  });

  test('renders the login form with correct labels', () => {
    expect(screen.getByPlaceholderText(/email/i)).toBeInTheDocument();
    expect(screen.getByPlaceholderText(/password/i)).toBeInTheDocument();
    expect(screen.getByText(/sign in/i)).toBeInTheDocument();
  });

  test('validates email and password fields', async () => {
    fireEvent.click(screen.getByText(/sign in/i));
    
    expect(await screen.findAllByText(/required/i)).toHaveLength(2);
  });

  test('toggles password visibility', () => {
    const eyeIcon = screen.getByRole('button');
    expect(screen.getByPlaceholderText(/password/i).type).toBe('password');
    
    fireEvent.click(eyeIcon);
    expect(screen.getByPlaceholderText(/password/i).type).toBe('text');
    
    fireEvent.click(eyeIcon);
    expect(screen.getByPlaceholderText(/password/i).type).toBe('password');
  });

  test('handles email not found error', async () => {
    axios.post.mockResolvedValueOnce({ data: 'Email not found' });

    fireEvent.change(screen.getByPlaceholderText(/email/i), { target: { value: 'test@example.com' } });
    fireEvent.change(screen.getByPlaceholderText(/password/i), { target: { value: 'password123' } });
    fireEvent.click(screen.getByText(/sign in/i));

    await waitFor(() => {
      expect(axios.post).toHaveBeenCalledWith(expect.any(String), null, { params: { email: 'test@example.com' } });
      expect(screen.getByText(/login error/i)).toBeInTheDocument();
    });
  });

  test('successful login and navigation', async () => {
    const mockResponse = {
      data: {
        email: 'test@example.com',
        roles: 'customer',
        username: 'Test User',
        token: 'mocked-token',
        phoneNumber: '1234567890',
      },
    };

    axios.post.mockResolvedValueOnce({ data: 'Email found' });
    axios.post.mockResolvedValueOnce(mockResponse);

    fireEvent.change(screen.getByPlaceholderText(/email/i), { target: { value: 'test@example.com' } });
    fireEvent.change(screen.getByPlaceholderText(/password/i), { target: { value: 'password123' } });
    fireEvent.click(screen.getByText(/sign in/i));

    await waitFor(() => {
      expect(axios.post).toHaveBeenCalledTimes(2);
      expect(Cookies.set).toHaveBeenCalledWith('token', 'mocked-token', { expires: 7 });
      expect(store.dispatch).toHaveBeenCalledWith(setUser(mockResponse.data));
      expect(navigate).toHaveBeenCalledWith('/customer'); // Adjust based on your routing
    });
  });

  test('handles login error', async () => {
    axios.post.mockRejectedValue(new Error('Login failed'));

    fireEvent.change(screen.getByPlaceholderText(/email/i), { target: { value: 'test@example.com' } });
    fireEvent.change(screen.getByPlaceholderText(/password/i), { target: { value: 'password123' } });
    fireEvent.click(screen.getByText(/sign in/i));

    await waitFor(() => {
      expect(screen.getByText(/login error/i)).toBeInTheDocument();
    });
  });
});
            