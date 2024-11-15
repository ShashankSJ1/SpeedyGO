import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import ContactForm from '../../Components/ContactForm.jsx';

// Mock data for CONTACT_INFO and CONTACT_UI_TEXT
const CONTACT_INFO = {
  ADDRESS: {
    TOPIC: "Address",
    LINE1: "1234 Street",
    LINE2: "City, State"
  },
  PHONE: {
    TOPIC: "Phone",
    NUMBER1: "123-456-7890",
    NUMBER2: "098-765-4321"
  },
  EMAIL: {
    TOPIC: "Email",
    EMAIL1: "test@example.com",
    EMAIL2: "contact@example.com"
  }
};

const CONTACT_UI_TEXT = {
  SEND_MESSAGE: "Send us a message",
  MESSAGE_PROMPT: "We'd love to hear from you",
  NAME_PLACEHOLDER: "Your Name",
  EMAIL_PLACEHOLDER: "Your Email",
  MESSAGE_PLACEHOLDER: "Your Message",
  NAME_LABEL: "Name",
  EMAIL_LABEL: "Email",
  MESSAGE_LABEL: "Message",
  SUBMIT_BUTTON: "Submit",
  OR_TEXT: "Or connect with us on social media",
  FORGOT_PASSWORD: "Forgot Password?",
};

describe('ContactForm', () => {
  test('renders without crashing', () => {
    render(<ContactForm />);
  });

  test('displays the initial form correctly', () => {
    render(<ContactForm />);
    
    expect(screen.getByPlaceholderText(CONTACT_UI_TEXT.NAME_PLACEHOLDER)).toBeInTheDocument();
    expect(screen.getByPlaceholderText(CONTACT_UI_TEXT.EMAIL_PLACEHOLDER)).toBeInTheDocument();
    expect(screen.getByPlaceholderText(CONTACT_UI_TEXT.MESSAGE_PLACEHOLDER)).toBeInTheDocument();
    expect(screen.getByRole('button', { name: CONTACT_UI_TEXT.SUBMIT_BUTTON })).toBeInTheDocument();
  });

  test('shows validation errors for empty fields', async () => {
    render(<ContactForm />);

    fireEvent.submit(screen.getByRole('button', { name: CONTACT_UI_TEXT.SUBMIT_BUTTON }));

    await screen.findAllByText(/required/i).then((errors) => {
      expect(errors.length).toBe(3); // Assuming there are 3 fields: name, email, message
    });
  });

  test('submits the form with correct values', async () => {
    render(<ContactForm />);

    fireEvent.change(screen.getByPlaceholderText(CONTACT_UI_TEXT.NAME_PLACEHOLDER), {
      target: { value: 'Test Name' },
    });
    fireEvent.change(screen.getByPlaceholderText(CONTACT_UI_TEXT.EMAIL_PLACEHOLDER), {
      target: { value: 'test@example.com' },
    });
    fireEvent.change(screen.getByPlaceholderText(CONTACT_UI_TEXT.MESSAGE_PLACEHOLDER), {
      target: { value: 'This is a test message' },
    });

    fireEvent.submit(screen.getByRole('button', { name: CONTACT_UI_TEXT.SUBMIT_BUTTON }));

    await screen.findByText('Test Name');
    await screen.findByText('test@example.com');
    await screen.findByText('This is a test message');
  });
});
