// Contact.test.js
import React from 'react';
import { render } from '@testing-library/react';
import Contact from '../../pages/Contact'; // Adjust the import path as needed
import ContactForm from '../../Components/ContactForm';; // Adjust the import path as needed

jest.mock('../../Components/ContactForm', () => () => <div>Mocked Contact Form</div>);

describe('Contact Component', () => {
  it('should render the ContactForm component', () => {
    const { getByText } = render(<Contact />);
    
    // Check if ContactForm is rendered
    expect(getByText('Mocked Contact Form')).toBeInTheDocument();
  });

  it('should match the snapshot', () => {
    const { asFragment } = render(<Contact />);
    
    // Snapshot test
    expect(asFragment()).toMatchSnapshot();
  });

  it('should render the correct structure', () => {
    const { container } = render(<Contact />);
    
    // Check if the main div is present
    expect(container.querySelector('div')).toBeInTheDocument();
    expect(container.firstChild).toBeInTheDocument();
  });
});
