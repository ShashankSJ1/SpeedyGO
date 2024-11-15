// HomePage.test.js
import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import { BrowserRouter as Router } from 'react-router-dom';
import HomePage from '../../pages/Home'; // Adjust the import path as needed
import { HOMEPAGE_SERVICES, HOMEPAGE_CONTENT_ARRAY, HOMEPAGE_UI_TEXT } from '../../contansts'; // Adjust import path

// Create a mock store for Redux
const mockStore = configureStore();
const store = mockStore({
  user: { role: 'CUSTOMER' } // You can change this to test different roles
});

// Mock the AOS library
jest.mock('aos', () => ({
  init: jest.fn(),
}));

describe('HomePage Component', () => {
  beforeEach(() => {
    render(
      <Provider store={store}>
        <Router>
          <HomePage />
        </Router>
      </Provider>
    );
  });

  it('should render without crashing', () => {
    // Check if the title is rendered
    expect(screen.getByText(HOMEPAGE_UI_TEXT.WELCOME_TITLE)).toBeInTheDocument();
    expect(screen.getByText(HOMEPAGE_UI_TEXT.WELCOME_DESCRIPTION)).toBeInTheDocument();
  });

  it('should toggle theme when button is clicked', () => {
    const toggleButton = screen.getByRole('button', { name: HOMEPAGE_UI_TEXT.SWITCH_TO_DARK_MODE });
    
    // Check initial dark mode status
    expect(screen.container.firstChild).toHaveClass('homepage-container');

    // Click the button to toggle theme
    fireEvent.click(toggleButton);
    
    // Verify the class has changed
    expect(screen.container.firstChild).toHaveClass('dark');
    expect(toggleButton).toHaveTextContent(HOMEPAGE_UI_TEXT.SWITCH_TO_LIGHT_MODE);
  });

  it('should navigate to the correct route on explore button click (CUSTOMER)', () => {
    const exploreButton = screen.getByRole('button', { name: HOMEPAGE_UI_TEXT.EXPLORE_NOW_BUTTON });
    fireEvent.click(exploreButton);
    
    // Verify the navigate function is called with the correct path
    expect(window.location.pathname).toBe('/user/logistics'); // Simulate navigation
  });

  it('should render services correctly', () => {
    const serviceTitles = HOMEPAGE_SERVICES.map(item => item.title);
    serviceTitles.forEach(title => {
      expect(screen.getByText(title)).toBeInTheDocument();
    });
  });

  it('should render features correctly', () => {
    const featureTitles = HOMEPAGE_CONTENT_ARRAY.map(item => item.title);
    featureTitles.forEach(title => {
      expect(screen.getByText(title)).toBeInTheDocument();
    });
  });

  it('should initialize AOS', () => {
    // Check if AOS.init was called
    expect(AOS.init).toHaveBeenCalledWith({ duration: 1000 });
  });
});
