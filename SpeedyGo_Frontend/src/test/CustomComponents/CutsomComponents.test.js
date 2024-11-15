// src/components/CustomButton.test.js

import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import CustomButton from '../../Components/CustomComponents/CustomButton';

describe('CustomButton', () => {
  test('renders with default props', () => {
    render(<CustomButton />);

    const buttonElement = screen.getByText(/submit/i);
    expect(buttonElement).toBeInTheDocument();
    expect(buttonElement).toHaveClass('custom-button-text');
  });

  test('renders with custom button title', () => {
    render(<CustomButton buttonTitle="Click Me" />);

    const buttonElement = screen.getByText(/click me/i);
    expect(buttonElement).toBeInTheDocument();
  });

  test('applies custom styles to the button', () => {
    const customButtonStyle = { backgroundColor: 'red' };
    render(<CustomButton buttonStyle={customButtonStyle} />);

    const buttonContainer = screen.getByRole('button').parentElement; // Get the parent div
    expect(buttonContainer).toHaveStyle('background-color: red');
  });

  test('applies custom text styles', () => {
    const customTextStyle = { color: 'blue' };
    render(<CustomButton buttonTitle="Styled Text" textStyle={customTextStyle} />);

    const textElement = screen.getByText(/styled text/i);
    expect(textElement).toHaveStyle('color: blue');
  });

  test('calls the onClick handler when clicked', () => {
    const handleClick = jest.fn(); // Mock function
    render(<CustomButton buttonTitle="Click Me" onClick={handleClick} />);

    const buttonElement = screen.getByText(/click me/i);
    fireEvent.click(buttonElement);

    expect(handleClick).toHaveBeenCalledTimes(1); // Ensure the click handler was called
  });

  test('button has accessibility attributes', () => {
    render(<CustomButton buttonTitle="Accessible Button" aria-label="Accessible Button" />);
    
    const buttonElement = screen.getByLabelText(/accessible button/i);
    expect(buttonElement).toBeInTheDocument();
  });

  test('button is disabled when the disabled prop is passed', () => {
    render(<CustomButton buttonTitle="Disabled Button" disabled />);

    const buttonElement = screen.getByText(/disabled button/i);
    expect(buttonElement).toBeDisabled(); // Ensure the button is disabled
  });
});
