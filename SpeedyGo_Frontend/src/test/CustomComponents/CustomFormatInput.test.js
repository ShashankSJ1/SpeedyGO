import React from 'react';
import { render, screen } from '@testing-library/react';
import CustomFormInput from '../../Components/CustomComponents/CustomFormInput';

describe('CustomFormInput', () => {
  test('renders input element correctly', () => {
    render(<CustomFormInput tag="input" type="text" placeholder="Enter text" />);
    
    const inputElement = screen.getByPlaceholderText(/enter text/i);
    expect(inputElement).toBeInTheDocument();
    expect(inputElement.tagName).toBe('INPUT');
    expect(inputElement).toHaveAttribute('type', 'text');
  });

  test('renders select element with options', () => {
    const options = [
      { value: 'option1', label: 'Option 1' },
      { value: 'option2', label: 'Option 2' },
    ];
    
    render(<CustomFormInput tag="select" options={options} />);
    
    const selectElement = screen.getByRole('combobox');
    expect(selectElement).toBeInTheDocument();
    expect(selectElement.tagName).toBe('SELECT');

    // Check if options are rendered correctly
    expect(screen.getByText(/option 1/i)).toBeInTheDocument();
    expect(screen.getByText(/option 2/i)).toBeInTheDocument();
  });

  test('renders textarea element correctly', () => {
    render(<CustomFormInput tag="textarea" placeholder="Enter message" />);
    
    const textareaElement = screen.getByPlaceholderText(/enter message/i);
    expect(textareaElement).toBeInTheDocument();
    expect(textareaElement.tagName).toBe('TEXTAREA');
  });

  test('renders label correctly when provided', () => {
    render(<CustomFormInput label="My Label" tag="input" />);
    
    const labelElement = screen.getByText(/my label/i);
    expect(labelElement).toBeInTheDocument();
    expect(labelElement).toHaveClass('custom-label'); // Check if it has the correct class
  });

  test('handles default props', () => {
    render(<CustomFormInput tag="input" />);
    
    const inputElement = screen.getByRole('textbox'); // Default type is 'text'
    expect(inputElement).toHaveAttribute('type', 'text');
  });

  test('renders unsupported tag with custom class', () => {
    render(<CustomFormInput tag="div" />);
    
    const divElement = screen.getByText(/custom-element/i);
    expect(divElement).toBeInTheDocument();
    expect(divElement).toHaveClass('custom-element');
  });
});
